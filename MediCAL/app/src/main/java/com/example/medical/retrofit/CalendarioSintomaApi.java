package com.example.medical.retrofit;

import com.example.medical.model.CalendarioSintoma;
import com.example.medical.model.Sintoma;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface CalendarioSintomaApi {

    @GET("/calendariosintoma/get-all")
    Call<List<CalendarioSintoma>> getAllCalendarioSintomas();

    @POST("/calendariosintoma/save")
    Call<CalendarioSintoma> saveCalendarioSintoma(@Body CalendarioSintoma calendarioSintoma);


}
