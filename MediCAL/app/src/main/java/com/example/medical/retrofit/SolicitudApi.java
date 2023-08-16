package com.example.medical.retrofit;
import com.example.medical.model.EstadoSolicitud;
import com.example.medical.model.Solicitud;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface SolicitudApi {
    @GET("/solicitud/get-all")
    Call<List<Solicitud>> getAllSolicitudes();

    @POST("/solicitud/save")
    Call<Solicitud> save(@Body Solicitud solicitud);

    @GET("/solicitud/pendiente/{codUsuario}")
    Call<Solicitud> obtenerSolicitudPendiente(@Path("codUsuario") int codUsuario);

    @PUT("/solicitud/modificar-estado/{codSolicitud}")
    Call<Solicitud> actualizarEstadoSolicitud(@Path("codSolicitud") int codSolicitud, @Body EstadoSolicitud nuevoEstadoSolicitud);

    @GET("/solicitud/respuestas/{codUsuario}")
    Call <List<Solicitud>> obtenerRespuestasSolicitud(@Path("codUsuario") int codUsuario);
}
