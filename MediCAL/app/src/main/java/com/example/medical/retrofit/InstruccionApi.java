package com.example.medical.retrofit;

import com.example.medical.model.Instruccion;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface InstruccionApi {
    @GET("/instruccion/get-all")
    Call<List<Instruccion>> getAllInstrucciones();

    @POST("/instruccion/save")
    Call<Instruccion> save(@Body Instruccion instruccion);
}
