package com.example.appporkys.repositories.remote.request;

import com.example.appporkys.entity.FooRequest;
import com.example.appporkys.entity.Porcino;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface PorcinoService {
    @GET("listar")
    Call<List<Porcino>> getPorcino();

    @POST("registrar")
    Call<ResponseBody> updateUserImage(
            @Body FooRequest body);
}
