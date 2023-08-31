package com.example.medical.retrofit;

import com.example.medical.model.Recordatorio;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RecordatorioApi {
    @GET("/recordatorio/get-all")
    Call<List<Recordatorio>> getAllRecordatorios();

    @POST("/recordatorio/save")
    Call<Recordatorio> save(@Body Recordatorio recordatorio);

    @GET("/recordatorio/{codRecordatorio}")
    Call<Recordatorio> getByCodRecordatorio(@Path("codRecordatorio") int codRecordatorio);

    @DELETE("/recordatorio/eliminar/{codRecordatorio}")
    Call<Void> eliminarRecordatorio(@Path("codRecordatorio") int codRecordatorio);
}
