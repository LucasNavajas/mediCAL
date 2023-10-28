package com.example.medical.retrofit;

import com.example.medical.model.Calendario;
import com.example.medical.model.CalendarioMedicion;
import com.example.medical.model.CalendarioSintoma;
import com.example.medical.model.Sintoma;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CalendarioSintomaApi {

    @GET("/calendariosintoma/sintoma/{codSintoma}/calendario/{codCalendario}")
    Call<List<CalendarioSintoma>> getByCodSintomaAndCodCalendario(
            @Path("codSintoma") int codSintoma,
            @Path("codCalendario") int codCalendario
    );

    @GET("/calendariosintoma/get-all")
    Call<List<CalendarioSintoma>> getAllCalendarioSintomas();

    @GET("/calendariosintoma/calendario/{codCalendario}")
    Call<List<CalendarioSintoma>> getByCodCalendarioSintoma(@Path("codCalendario") int codCalendario);
    @POST("/calendariosintoma/eliminar")
    Call<CalendarioSintoma> eliminarCalendarioSintoma(@Body CalendarioSintoma calendarioSintoma);

    @GET("/calendariosintoma/sintoma/{codSintoma}")
    Call<List<CalendarioSintoma>> getByCodSintoma(@Path("codSintoma") int codSintoma);


    @POST("/calendariosintoma/save")
    Call<CalendarioSintoma> saveCalendarioSintoma(@Body CalendarioSintoma calendarioSintoma);


}
