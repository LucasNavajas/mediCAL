package com.example.medical.retrofit;

import com.example.medical.model.Reporte;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface TipoReporteApi {

    @GET("/tipoReporte/get-all")
    Call<List<Reporte>> getAllReportes();

    @POST("/tipoReporte/save")
    Call<Reporte> save(@Body Reporte reporte);


    // Agregar
    @GET("/tipoReporte/reporte/{nroReporte}")
    Call<Reporte>getByNroReporte(@Path("nroReporte") int nroReporte);

}
