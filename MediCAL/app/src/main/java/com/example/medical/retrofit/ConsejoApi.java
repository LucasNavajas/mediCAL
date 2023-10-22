package com.example.medical.retrofit;

import com.example.medical.model.Consejo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ConsejoApi {

    @GET("/consejo/get-all")
    Call<List<Consejo>> getAllConsejos();

    @GET("/consejo/{nroConsejo}")
    Call<Consejo> getByNroConsejo(@Path("nroConsejo") int nroConsejo);

    @POST("/consejo/save")
    Call<Consejo> save(@Body Consejo consejo);

}
