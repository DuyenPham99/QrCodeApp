package vn.hust.edu.qrcodeapp.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import vn.hust.edu.qrcodeapp.ItemClickListenerRecycle;
import vn.hust.edu.qrcodeapp.R;
import vn.hust.edu.qrcodeapp.Retrofit.RetrofitClient;
import vn.hust.edu.qrcodeapp.adapter.ItemAdapter;
import vn.hust.edu.qrcodeapp.service.ProductService;
import vn.hust.edu.qrcodeapp.model.Product;
import vn.hust.edu.qrcodeapp.response.ProductResponse;

public class ListProductActivity extends AppCompatActivity implements ItemClickListenerRecycle {
    List<Product> listProduct;
    ItemAdapter adapter;
    ProductService productService;
    Toolbar toolbar;
    View footerview;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_listview);
        toolbar = findViewById(R.id.toolbardssanpham);
        recyclerView = findViewById(R.id.recycler_view);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerview = inflater.inflate(R.layout.processbar, null);
        ActionToolBar();

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

                        recyclerView.setHasFixedSize(true);

                        //Khai báo kiểu layout manage
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ListProductActivity.this);
                        recyclerView.setLayoutManager(layoutManager);

                        //Khai báo Adapter
                        adapter = new ItemAdapter(listProduct, ListProductActivity.this::OnItemClick);
                        recyclerView.setAdapter(adapter);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(ListProductActivity.this, e.toString(), Toast.LENGTH_LONG).show();
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


    private void ActionToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    private String getToken() {
        SharedPreferences sharedPreferences = getSharedPreferences("WFH", MODE_PRIVATE);
        String token = sharedPreferences.getString("accessToken", "");
        if (!token.equals("")) {
            return token;
        } else return null;
    }

    @Override
    public void OnItemClick(int position) {
        Intent intent = new Intent(ListProductActivity.this, DetailProductActivity.class);
        intent.putExtra("detailProduct", listProduct.get(position));
        startActivity(intent);
    }
}
