package com.example.medical.retrofit;

import com.example.medical.model.PresentacionMed;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PresentacionMedApi {

    @GET("/presentacionMed/get-all")
    Call<List<PresentacionMed>> getAllPresentacionesMed();

    @GET("/presentacionmed/{codPresentacionMed}")
    Call<PresentacionMed> getPresentacionMedByCod(@Path("codPresentacionMed") int codPresentacionMed);

    @GET("/presentacionmed/administracion/{codAdministracionMed}")
    Call<List<PresentacionMed>> getPresentacionesMedByCodAdministracionMed(@Path("codAdministracionMed") int codAdministracionMed);

    @POST("/presentacionMed/save")
    Call<PresentacionMed> save(@Body PresentacionMed presentacionMed);
}
