package com.example.medical.retrofit;
import com.example.medical.model.Solicitud;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface SolicitudApi {
    @GET("/solicitud/get-all")
    Call<List<Solicitud>> getAllFAQ();

    @POST("/solicitud/save")
    Call<Solicitud> save(@Body Solicitud solicitud);
}
