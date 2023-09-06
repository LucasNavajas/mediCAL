package com.example.medical.retrofit;

import com.example.medical.model.Dosis;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface DosisApi {
    @GET("/dosis/get-all")
    Call<List<Dosis>> getAllDosis();

    @POST("/dosis/save")
    Call<Dosis> save(@Body Dosis dosis);
}
