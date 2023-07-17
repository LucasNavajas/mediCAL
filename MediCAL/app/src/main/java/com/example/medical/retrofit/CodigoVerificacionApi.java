package com.example.medical.retrofit;

import com.example.medical.model.CodigoVerificacion;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.Call;
import retrofit2.http.POST;

public interface CodigoVerificacionApi {

    @GET("/codigoverificacion/get-all")
    Call<List<CodigoVerificacion>> getAllCodigosVerificacion();

    @POST("/codigoverificacion/save")
    Call<CodigoVerificacion> save(@Body CodigoVerificacion codigoverificacion);
}
