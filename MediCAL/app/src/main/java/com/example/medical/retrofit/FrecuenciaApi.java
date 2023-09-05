package com.example.medical.retrofit;

import com.example.medical.model.Frecuencia;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface FrecuenciaApi {

    @GET("/frecuencia/get-all")
    Call<List<Frecuencia>> getAllFrecuencias();

    @POST("/frecuencia/save")
    Call<Frecuencia> save(@Body Frecuencia frecuencia);
}
