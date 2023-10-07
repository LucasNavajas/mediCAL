package com.example.medical.retrofit;

import com.example.medical.model.RegistroRecordatorio;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RegistroRecordatorioApi {

    @GET("/registroRecordatorio/get-all")
    Call<List<RegistroRecordatorio>> getAllRegistrosRecordatorio();

    @POST("/registroRecordatorio/save")
    Call<RegistroRecordatorio> save(@Body RegistroRecordatorio recordatorio);

    @GET("/registroRecordatorio/calendario/{codCalendario}")
    Call<List<RegistroRecordatorio>> obtenerRegistrosCalendario(@Path("codCalendario") int codCalendario);

    @GET("/registroRecordatorio/notificacion/{codCalendario}")
    Call<List<RegistroRecordatorio>> obtenerRegistrosCalendarioNotificacion(@Path("codCalendario") int codCalendario);

    @PUT("/registroRecordatorio/baja/{codRegistroRecordatorio}")
    Call<Void> bajaRegistro(@Path("codRegistroRecordatorio") int codRegistroRecordatorio);

    @GET("/registroRecordatorio/{codRegistroRecordatorio}")
    Call<RegistroRecordatorio> getByCodRegistroRecordatorio(@Path("codRegistroRecordatorio") int codRegistroRecordatorio);

    @GET("/registroRecordatorio/recordatorio/{codRecordatorio}")
    Call<List<RegistroRecordatorio>> getByCodRecordatorio(@Path("codRecordatorio") int codRecordatorio);

}
