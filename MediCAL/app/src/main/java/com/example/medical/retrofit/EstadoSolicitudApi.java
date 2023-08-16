package com.example.medical.retrofit;

import com.example.medical.model.EstadoSolicitud;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface EstadoSolicitudApi {
    @GET("/estadoSolicitud/get-all")
    Call<List<EstadoSolicitud>> getAllEstadoSolicitud();

    @POST("/estadoSolicitud/save")
    Call<EstadoSolicitud> save(@Body EstadoSolicitud estadoSolicitud);

    @GET("/estadoSolicitud/{codEstadoSolicitud}")
    Call<EstadoSolicitud> findByCodEstadoSolicitud(@Path("codEstadoSolicitud") int codEstadoSolicitud);
}
