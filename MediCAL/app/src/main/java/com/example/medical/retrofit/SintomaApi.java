package com.example.medical.retrofit;

import com.example.medical.model.Sintoma;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import java.util.*;

public interface SintomaApi {
    @GET("/api/sintomas")
    Call<List<Sintoma>> getAllSintomas();

    @POST("/api/sintomas")
    Call<Sintoma> saveSintoma(@Body Sintoma sintoma);
}
