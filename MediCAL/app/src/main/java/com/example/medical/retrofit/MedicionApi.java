package com.example.medical.retrofit;

import com.example.medical.model.Medicion;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface MedicionApi {
    @GET("/medicion/get-all")
    Call<List<Medicion>> getAllMediciones();

    @GET("/medicion/get-unidad-medida")
    Call<String> getUnidadDeMedida(@Query("nombreMedicion") String nombreMedicion);

    @POST("/medicion/save")
    Call<Medicion> saveMedicion(@Body Medicion medicion);

}

