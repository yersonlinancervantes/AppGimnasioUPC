package com.example.appporkys.repositories.remote.request;

import com.example.appporkys.entity.Reporte;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ReporteService {
    @GET("reporte")
    Call<List<Reporte>> getReporte();
}
