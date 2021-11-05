package com.example.appporkys.repositories.remote;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceFactory {

    private static final String BASE_URL_DESARROLLO = "https://s5p14jzn03.execute-api.us-east-1.amazonaws.com/DEV/";

    public static Retrofit retrofit =
            new Retrofit.Builder()
                    .baseUrl(BASE_URL_DESARROLLO)
                    .addConverterFactory(GsonConverterFactory.create()).build();
}