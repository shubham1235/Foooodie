package com.example.shubham_v.foodie.RestApis;

import com.example.shubham_v.foodie.ModelClass.NearResponse;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Query;

/**
 * Created by shubham_v on 06-12-2016.
 */

public interface ApiInterface {

    @Headers("user-key: 0abef42aab6571335374d24b2a1df6c1")
    @GET("/api/v2.1/geocode")
    Call<NearResponse> getRestaurants(@Query("lat") String lat, @Query("lon") String lon)  ;



}
