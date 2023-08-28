package com.example.medical.retrofit;

import com.example.medical.model.TipoConsejo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface TipoConsejoApi {

    @GET("/tipoConsejo/get-all")
    Call<List<TipoConsejo>> getAllTipoConsejos();

    // Se puede hacer POST de un TipoConsejo desde la app??
    @POST("/tipoConsejo/save")
    Call<TipoConsejo> save(@Body TipoConsejo tipoConsejo);

}
