package vn.hust.edu.qrcodeapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.rengwuxian.materialedittext.MaterialEditText;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import vn.hust.edu.qrcodeapp.R;
import vn.hust.edu.qrcodeapp.Retrofit.RetrofitClient;
import vn.hust.edu.qrcodeapp.service.AuthService;
import vn.hust.edu.qrcodeapp.response.LoginResponse;

public class LoginActivity extends AppCompatActivity {

    MaterialEditText username, password;
    Button btn_login;

    AuthService authService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        if (getToken() != null) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }

        //init service
        Retrofit retrofit = RetrofitClient.getInstance();
        authService = retrofit.create(AuthService.class);

        //init_view
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        btn_login = findViewById(R.id.btnLogin);

        btn_login.setOnClickListener(v -> loginUser(username.getText().toString(), password.getText().toString()));
    }

    private String getToken() {
        SharedPreferences sharedPreferences = getSharedPreferences("WFH", MODE_PRIVATE);
        String token = sharedPreferences.getString("accessToken", "");
        if (!token.equals("")) {
            return token;
        } else return null;
    }

    private void loginUser(String username, String password) {
        if (TextUtils.isEmpty(username)) {
            Toast.makeText(this, "Username cannot be null or empty", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Password cannot be null or empty", Toast.LENGTH_LONG).show();
            return;
        }


        Call<ResponseBody> login = authService.login(username, password);
        login.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String ResponseJson = response.body().string();
                        Gson gson = new Gson();
                        LoginResponse loginResponse = gson.fromJson(ResponseJson, LoginResponse.class);
                        getSharedPreferences("WFH", MODE_PRIVATE).edit().putString("accessToken", loginResponse.getAccessToken()).apply();
                        Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(LoginActivity.this, e.toString(), Toast.LENGTH_LONG).show();
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

}
