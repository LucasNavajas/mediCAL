package com.example.medical.retrofit;

import com.example.medical.model.Usuario;

import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;

import java.time.LocalDate;
import java.util.*;
import retrofit2.Call;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;


public interface UsuarioApi {

    @GET("/usuario/get-all")
    Call<List<Usuario>> getAllUsuarios();

    @POST("/usuario/save")
    Call<Usuario> save(@Body Usuario usuario);

    @POST("/usuario/modificar")
    Call<Usuario> modificarUsuario(@Body Usuario usuario);

    @GET("/usuario/get-all-usuarios-unicos")
    Call<List<String>> obtenerUsuariosUnicos();

    @GET("/usuario/get-all-mails-unicos")
    Call<List<String>> obtenerMailsUnicos();

    @GET("/usuario/cod/{codUsuario}")
    Call<Usuario> getByCodUsuario(@Path("codUsuario") int codUsuario);

    @GET("/usuario/mail/{mailUsuario}")
    Call<Usuario> getByMailUsuario(@Path("mailUsuario") String mailUsuario);

}
