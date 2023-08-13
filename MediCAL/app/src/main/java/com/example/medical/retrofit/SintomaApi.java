package com.example.medical.retrofit;

import com.example.medical.model.Sintoma;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import java.util.*;
import retrofit2.http.Query;

public interface SintomaApi {
    @GET("/sintoma/get-all")
    Call<List<Sintoma>> getAllSintomas();
    @GET("/sintoma/getNombresSintomasSinFechaAlta")
    Call<List<String>> obtenerNombresSintomasSinFechaAlta();

    @POST("/sintoma/save")
    Call<Sintoma> saveSintoma(@Body Sintoma sintoma);


}
