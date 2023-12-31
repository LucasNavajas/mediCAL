package com.example.medical.retrofit;

import com.example.medical.model.Usuario;

import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.GET;

import java.time.LocalDate;
import java.util.*;
import retrofit2.Call;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface UsuarioApi {

    @GET("/usuario/get-all")
    Call<List<Usuario>> getAllUsuarios();

    @POST("/usuario/save")
    Call<Usuario> save(@Body Usuario usuario);

    @POST("/usuario/saveSinHash")
    Call<Usuario> saveSinHash(@Body Usuario usuario);

    @POST("/usuario/modificar")
    Call<Usuario> modificarUsuario(@Body Usuario usuario);

    @GET("/usuario/get-all-usuarios-unicos")
    Call<List<String>> obtenerUsuariosUnicos();

    @GET("/usuario/get-all-mails-unicos")
    Call<List<String>> obtenerMailsUnicos();

    @GET("/usuario/get-all-mails-unicos-cuentas")
    Call<List<String>> obtenerMailsUnicosCuentas();//Obtener todos los mails de cuentas que ya fueron creadas y no estan en periodo de creacion

    @GET("/usuario/cod/{codUsuario}")
    Call<Usuario> getByCodUsuario(@Path("codUsuario") int codUsuario);

    @GET("/usuario/mail/{mailUsuario}")
    Call<Usuario> getByMailUsuario(@Path("mailUsuario") String mailUsuario);

    @DELETE("/usuario/delete/{codUsuario}")
    Call<Void> deleteUsuario(@Path("codUsuario") int codUsuario);

    @POST("/usuario/set-cod-verificacion/{codUsuario}")
    Call<Usuario> setCodigoVerificacion(@Path("codUsuario") int codUsuario);

    @POST("/usuario/modificarContrasenia/{codUsuario}")
    Call<Void> modificarContrasenia(@Path("codUsuario") int codUsuario, @Body String nuevaContrasenia);

    @POST("/usuario/misma-contrasenia/{codUsuario}/{nuevaContrasenia}")
    Call<Boolean> verificarMismaContrasenia(@Path("codUsuario") int codUsuario, @Path("nuevaContrasenia") String nuevaContrasenia);

    @GET("/usuario/buscar-mail-y-usuario")
    Call<Usuario> buscarUsuariosPorMailYUser(@Query("usuarioTexto") String usuarioTexto);

    @POST("/usuario/eliminar/{codUsuario}")
    Call<Void> eliminarUsuario(@Path("codUsuario") int codUsuario, @Body String motivoFinVigencia);

    @PUT("/usuario/token/{codUsuario}")
    Call<Usuario> modificarToken(@Path("codUsuario") int codUsuario, @Body String token);

    @PUT("/usuario/verificar-mail-existente/{mail}")
    Call<Void> verificarMailExistente(@Path("mail") String mail);

}
