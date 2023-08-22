package com.example.medical.retrofit;

import com.example.medical.model.HistorialFinVigencia;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface HistorialFinVigenciaApi {
    @GET("/historialfinvigencia/get-all")
    Call<List<HistorialFinVigencia>> getAllHistorialFinVigencias();

    @POST("/historialfinvigencia/save")
    Call<HistorialFinVigencia> saveHistorialFinVigencia(@Body HistorialFinVigencia historialfinvigencia);

    @GET("/historialfinvigencia/{codUsuario}")
    Call<List<HistorialFinVigencia>> findByCodUsuario(@Path("codUsuario") int codUsuario);
}
