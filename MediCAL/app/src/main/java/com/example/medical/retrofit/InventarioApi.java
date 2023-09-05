package com.example.medical.retrofit;

import com.example.medical.model.Calendario;
import com.example.medical.model.Inventario;
import com.example.medical.model.Recordatorio;

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
    Call<List<Inventario>> getByCodRecordatorio(@Path("codRecordatorio") int codRecordatorio);

}
