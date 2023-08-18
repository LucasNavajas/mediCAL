package com.example.medical.retrofit;

import com.example.medical.model.Consejo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ConsejoApi {

    @GET("/consejo/get-all")
    Call<List<Consejo>> getAllConsejos();

    @POST("/consejo/save")
    Call<Consejo> save(@Body Consejo consejo);

}
