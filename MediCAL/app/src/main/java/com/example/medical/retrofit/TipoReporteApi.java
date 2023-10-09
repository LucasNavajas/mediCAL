package com.example.medical.retrofit;

import com.example.medical.model.Reporte;
import com.example.medical.model.TipoReporte;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface TipoReporteApi {

    @GET("/tipoReporte/get-all")
    Call<List<TipoReporte>> getAllReportes();

    @POST("/tipoReporte/save")
    Call<TipoReporte> save(@Body Reporte reporte);


    // Agregar
    @GET("/tipoReporte/reporte/{nroReporte}")
    Call<TipoReporte>getByNroReporte(@Path("nroReporte") int nroReporte);

}
