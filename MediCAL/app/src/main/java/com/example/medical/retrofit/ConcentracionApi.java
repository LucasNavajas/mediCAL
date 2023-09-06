package com.example.medical.retrofit;

import com.example.medical.model.Concentracion;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ConcentracionApi {

    @GET("/concentracion/get-all")
    Call<List<Concentracion>> getAllConcentracion();

    @POST("/concentracion/save")
    Call<Concentracion> save(@Body Concentracion concentracion);
}
