package com.example.medical.retrofit;

import com.example.medical.model.Medicion;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;


public interface MedicionApi {
    @GET("/medicion/get-all")
    Call<List<Medicion>> getAllMediciones();

    @POST("/medicion/save")
    Call<Medicion> saveMedicion(@Body Medicion medicion);

}

