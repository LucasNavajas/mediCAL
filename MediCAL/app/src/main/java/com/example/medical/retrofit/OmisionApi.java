package com.example.medical.retrofit;

import com.example.medical.model.Omision;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface OmisionApi {
    @GET("/omision/get-all")
    Call<List<Omision>> getAllOmisiones();

    @POST("/omision/save")
    Call<Omision> save(@Body Omision omision);
}
