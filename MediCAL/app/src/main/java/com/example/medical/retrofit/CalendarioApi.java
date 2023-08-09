package com.example.medical.retrofit;

import com.example.medical.model.Calendario;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CalendarioApi {

    @GET("/calendario/get-all")
    Call<List<Calendario>> getAllCalendarios();

    @POST("/calendario/save")
    Call<Calendario> save(@Body Calendario calendario);

    @GET("/calendario/usuario/{codUsuario}")
    Call<List<Calendario>> getByCodUsuario(@Path("codUsuario") int codUsuario);

    @GET("/calendario/{codCalendario}")
    Call<Calendario> getByCodCalendario(@Path("codCalendario") int codCalendario);
}
