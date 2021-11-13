package vn.hust.edu.qrcodeapp.service;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ProductService {
    @GET("product/getListProductForMobile")
    Call<ResponseBody> getListProduct(@Header("x-access-token") String token);

    @GET("product/detailProduct?")
    Call<ResponseBody> getDetailProduct(@Query("id") String id);
}
