package com.maidanhdung.ecommerce.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.maidanhdung.ecommerce.models.Province;
import com.maidanhdung.ecommerce.models.ResultWrapper;
import com.maidanhdung.ecommerce.models.ResultWrapper1;
import com.maidanhdung.ecommerce.models.ResultWrapper2;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    //https://vapi.vnappmob.com/api/province
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();
    ApiService apiservice = new Retrofit.Builder()
            .baseUrl("https://vapi.vnappmob.com/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);
    @GET("api/province")
    Call<ResultWrapper> loadProvince();
    @GET("api/province/district/{province_id}")
    Call<ResultWrapper1> loadDistrict(@Path("province_id") String province_id);
    @GET("api/province/ward/{district_id}")
    Call<ResultWrapper2> loadWard(@Path("district_id") String district_id);
}
