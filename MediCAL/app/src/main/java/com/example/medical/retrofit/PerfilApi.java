package com.example.medical.retrofit;

import com.example.medical.model.Perfil;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PerfilApi {
    @GET("/perfil/get-all")
    Call<List<Perfil>> getAllPerfiles();

    @POST("/perfil/save")
    Call<Perfil> save(@Body Perfil perfil);

    @GET("/perfil/{codPerfil}")
    Call<Perfil> findByCodPerfil(@Path("codPerfil") int codPerfil);

}
