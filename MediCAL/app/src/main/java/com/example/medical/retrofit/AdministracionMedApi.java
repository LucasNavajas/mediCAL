package com.example.medical.retrofit;

import com.example.medical.model.AdministracionMed;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AdministracionMedApi {

    @GET("/administracionmed/get-all")
    Call<List<AdministracionMed>> getAllAdministracionMeds();

    @POST("/administracionmed/save")
    Call<AdministracionMed> save(@Body AdministracionMed administracionMed);

    @GET("/administracionmed/{codAdministracionMed}")
    Call <AdministracionMed> getByCodAdministracionMed(@Path("codAdministracionMed") int codAdministracionMed);
}
