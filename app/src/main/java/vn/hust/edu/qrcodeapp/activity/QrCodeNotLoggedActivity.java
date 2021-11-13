package vn.hust.edu.qrcodeapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import vn.hust.edu.qrcodeapp.R;
import vn.hust.edu.qrcodeapp.Retrofit.RetrofitClient;
import vn.hust.edu.qrcodeapp.model.Product;
import vn.hust.edu.qrcodeapp.response.ProductDetailResponse;
import vn.hust.edu.qrcodeapp.service.ProductService;

public class QrCodeNotLoggedActivity extends AppCompatActivity {

    Toolbar toolbarNotLogged;
    Button btnCLick;
    ImageView imageView;
    ProductService productService;
    List<Product> listProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code_not_logged);
        btnCLick = findViewById(R.id.btnScanCode);
        imageView = findViewById(R.id.imageHinhAnh);
        toolbarNotLogged = findViewById(R.id.toolbarmannotlogged);
        setSupportActionBar(toolbarNotLogged);
        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        btnCLick.setOnClickListener(v -> intentIntegrator.initiateScan());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        listProduct = new ArrayList<>();
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Intent intent = new Intent(this, DetailProductActivity.class);
                Retrofit retrofit = RetrofitClient.getInstance();
                productService = retrofit.create(ProductService.class);
                Call<ResponseBody> call = productService.getDetailProduct(result.getContents());
                try {
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful()) {
                                try {
                                    String ResponseJson = response.body().string();
                                    Gson gson = new Gson();
                                    ProductDetailResponse product = gson.fromJson(ResponseJson, ProductDetailResponse.class);
                                    listProduct.addAll(product.getProduct());
                                    intent.putExtra("detailProduct", listProduct.get(0));
                                    startActivity(intent);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
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
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private String getToken() {
        SharedPreferences sharedPreferences = getSharedPreferences("WFH", MODE_PRIVATE);
        String token = sharedPreferences.getString("accessToken", "");
        if (!token.equals("")) {
            return token;
        } else return null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (getToken() != null)
            getMenuInflater().inflate(R.menu.menu_home, menu);
        else
            getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuLogin) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.menuHome) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}