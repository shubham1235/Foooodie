package com.example.shubham_v.foodie.RestApis;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by shubham_v on 06-12-2016.
 */

public class ApiClient {

    public static final String BASE_URL = "https://developers.zomato.com";
    private static Retrofit retrofit = null;


    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }






}
