package com.example.medical.retrofit;

import com.example.medical.model.Reporte;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ReporteApi {

    @GET("/reporte/get-all")
    Call<List<Reporte>> getAllReportes();

    @POST("/reporte/save")
    Call<Reporte> save(@Body Reporte reporte);

    @GET("/reporte/usuario/{codUsuario}")
    Call<List<Reporte>>getByCodUsuario(@Path("codUsuario") int codUsuario);

}
