package vn.hust.edu.qrcodeapp.activity;

import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import vn.hust.edu.qrcodeapp.CheckConnection;
import vn.hust.edu.qrcodeapp.ItemClickListenerRecycle;
import vn.hust.edu.qrcodeapp.R;
import vn.hust.edu.qrcodeapp.Retrofit.RetrofitClient;
import vn.hust.edu.qrcodeapp.adapter.HotProductAdapter;
import vn.hust.edu.qrcodeapp.adapter.ItemAdapter;
import vn.hust.edu.qrcodeapp.adapter.MenuAdapter;
import vn.hust.edu.qrcodeapp.model.MenuItem;
import vn.hust.edu.qrcodeapp.model.Product;
import vn.hust.edu.qrcodeapp.response.ProductResponse;
import vn.hust.edu.qrcodeapp.service.ProductService;

public class MainActivity extends AppCompatActivity implements ItemClickListenerRecycle {

    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerView;
    NavigationView navigationView;
    ListView listView;
    DrawerLayout drawerLayout;
    ArrayList<MenuItem> menuItems;
    MenuAdapter menuAdapter;
    HotProductAdapter hotProductAdapter;
    List<Product> listProduct;
    ProductService productService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Anhxa();
        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
            ActionBar();
            ActionViewFlipper();
            addData();
            getListNewProduct();
            catchOnItemMenu();
        } else {
            CheckConnection.showToast_short(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối");
            finish();
        }

    }

    private void catchOnItemMenu() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(MainActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else
                            CheckConnection.showToast_short(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối");
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 1:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(MainActivity.this, ListProductActivity.class);
                            startActivity(intent);
                        } else
                            CheckConnection.showToast_short(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối");
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 2:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(MainActivity.this, QrCodeActivity.class);
                            startActivity(intent);
                        } else
                            CheckConnection.showToast_short(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối");
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 3:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(MainActivity.this, QrCodeNotLoggedActivity.class);
                            startActivity(intent);
                            deleteToken();
                        } else
                            CheckConnection.showToast_short(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối");
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }
            }
        });
    }

    private void getListNewProduct() {
        Retrofit retrofit = RetrofitClient.getInstance();
        productService = retrofit.create(ProductService.class);
        listProduct = new ArrayList<>();
        Call<ResponseBody> call = productService.getListProduct(getToken());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String ResponseJson = response.body().string();
                        Gson gson = new Gson();
                        ProductResponse productResponse = gson.fromJson(ResponseJson, ProductResponse.class);
                        listProduct = productResponse.getProducts();
                        //Khai báo RecycleView
                        final RecyclerView recyclerView = findViewById(R.id.recyclerview);
                        recyclerView.setHasFixedSize(true);

                        //Khai báo kiểu layout manage
                        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(MainActivity.this, 2);
                        recyclerView.setLayoutManager(layoutManager);

                        //Khai báo Adapter
                        hotProductAdapter = new HotProductAdapter(listProduct, MainActivity.this::OnItemClick);
                        recyclerView.setAdapter(hotProductAdapter);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Call API error", Toast.LENGTH_LONG).show();
                Log.e("TAG", t.toString());
                t.printStackTrace();
            }
        });

    }

    @Override
    public void OnItemClick(int position) {
        Intent intent = new Intent(getApplicationContext(), DetailProductActivity.class);
        intent.putExtra("detailProduct", listProduct.get(position));
        startActivity(intent);
    }

    private void addData() {
        menuItems.add(new MenuItem("Trang chính", "http://www.lugiaquan.com/_/rsrc/1539046371405/home/icontrangchi.png"));
        menuItems.add(new MenuItem("Sản phẩm", "https://img.favpng.com/15/15/21/vector-graphics-illustration-stock-photography-computer-icons-royalty-free-png-favpng-b7XKxBqi4U6jvzELeB32qz0wi.jpg"));
        menuItems.add(new MenuItem("Check Qr", "https://cdns.iconmonstr.com/wp-content/assets/preview/2013/240/iconmonstr-qr-code-2.png"));
        menuItems.add(new MenuItem("Đăng xuất", "https://banner2.cleanpng.com/20180403/akw/kisspng-computer-icons-login-download-login-button-5ac42ddce5a580.6913276115228062369406.jpg"));
    }

    private void ActionViewFlipper() {
        ArrayList<String> mangQuangCao = new ArrayList<>();
        mangQuangCao.add("https://9mobi.vn/cf/images/2015/03/nkk/hinh-anh-dep-2.jpg");
        mangQuangCao.add("https://9mobi.vn/cf/images/2015/03/nkk/hinh-anh-dep-1.jpg");
        mangQuangCao.add("https://9mobi.vn/cf/images/2015/03/nkk/hinh-anh-dep-3.jpg");
        for (int i = 0; i < mangQuangCao.size(); i++) {
            ImageView imageView = new ImageView(getApplicationContext());

            Picasso.get().load(mangQuangCao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }

        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);
        Animation animation_slide_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
        Animation animation_slide_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.aslide_out_right);
        viewFlipper.setInAnimation(animation_slide_in);
        viewFlipper.setOutAnimation(animation_slide_out);
    }

    private String getToken() {
        SharedPreferences sharedPreferences = getSharedPreferences("WFH", MODE_PRIVATE);
        String token = sharedPreferences.getString("accessToken", "");
        if (!token.equals("")) {
            return token;
        } else return null;
    }

    private void deleteToken() {
        SharedPreferences sharedPreferences = getSharedPreferences("WFH", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("accessToken");
        editor.apply();
    }

    private void ActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(view -> drawerLayout.openDrawer(GravityCompat.START));
    }

    private void Anhxa() {
        toolbar = findViewById(R.id.toolbarmanhinhchinh);
        viewFlipper = findViewById(R.id.viewflipper);
        recyclerView = findViewById(R.id.recyclerview);
        navigationView = findViewById(R.id.nagivationview);
        listView = findViewById(R.id.listviewmanhinhchinh);
        drawerLayout = findViewById(R.id.drawerlayout);
        menuItems = new ArrayList<>();
        menuAdapter = new MenuAdapter(menuItems, getApplicationContext());
        listView.setAdapter(menuAdapter);
    }
}