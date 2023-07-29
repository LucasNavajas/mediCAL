package com.example.medical.retrofit;

import com.example.medical.model.FAQ;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface FAQApi {

    @GET("/faq/get-all")
    Call<List<FAQ>> getAllFAQ();

    @POST("/faq/save")
    Call<FAQ> save(@Body FAQ faq);
}
