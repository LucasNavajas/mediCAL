package com.example.medical.retrofit;

import com.example.medical.model.Inventario;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface InventarioApi {

    @GET("/inventario/get-all")
    Call<List<Inventario>> getAllInventarios();

    @POST("/inventario/save")
    Call<Inventario> save(@Body Inventario inventario);

    @GET("/inventario/recordatorio/{codRecordatorio}")
    Call<Inventario>getByCodRecordatorio(@Path("codRecordatorio") int codRecordatorio);

    @POST("inventario/actualizarInventario/{codInventario}")
    Call<Void>actualizarInventario(@Path("codInventario") int codInventario, @Path("nuevaCantidadReal") int cantReal);

}
