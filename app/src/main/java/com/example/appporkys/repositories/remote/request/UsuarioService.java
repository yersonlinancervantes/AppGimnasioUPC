package com.example.appporkys.repositories.remote.request;

import com.example.appporkys.entity.FooRequest;
import com.example.appporkys.entity.Usuario;
import com.example.appporkys.entity.UsuarioRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UsuarioService {

    @POST("login")
    Call<Usuario> getUsuario(@Body UsuarioRequest body);

}