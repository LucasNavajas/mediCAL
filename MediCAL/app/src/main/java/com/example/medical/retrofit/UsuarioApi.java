package com.example.medical.retrofit;

import com.example.medical.model.Usuario;

import retrofit2.http.Body;
import retrofit2.http.GET;
import java.util.*;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.PUT;


public interface UsuarioApi {

    @GET("/usuario/get-all")
    Call<List<Usuario>> getAllUsuarios();

    @POST("/usuario/save")
    Call<Usuario> save(@Body Usuario usuario);

    @PUT("ruta_del_endpoint/modificarUsuario")
    Call<Usuario> modificarUsuario(@Body Usuario usuario);

}
