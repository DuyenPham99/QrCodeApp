package vn.hust.edu.qrcodeapp.service;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AuthService {
    @FormUrlEncoded
    @POST("auth/signin")
    Call<ResponseBody> login(@Field("username") String username, @Field("password") String password);
}
