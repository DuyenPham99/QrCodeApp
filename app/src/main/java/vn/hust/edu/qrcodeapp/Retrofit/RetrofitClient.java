package vn.hust.edu.qrcodeapp.Retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit instance;
    private static Gson gson;

    public static Retrofit getInstance() {
        if (instance == null) {
            gson = new GsonBuilder().setLenient().create();
            instance = new Retrofit.Builder()
                    .baseUrl("http://192.168.85.116:8080/api/")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return instance;
    }
}
