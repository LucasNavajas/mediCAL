package com.example.medical.retrofit;

import com.example.medical.model.Medicamento;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface MedicamentoApi {
    @GET("/medicamento/get-all")
    Call<List<Medicamento>> getAllMedicamentos();

    @POST("/medicamento/save")
    Call<Medicamento> saveMedicamento(@Body Medicamento medicamento);

    @GET("/medicamento/get-all-genericos")
    Call<List<Medicamento>> getAllMedicamentosGenericos();
}
