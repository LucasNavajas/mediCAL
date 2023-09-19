package com.example.medical.retrofit;

import com.example.medical.model.Calendario;
import com.example.medical.model.CalendarioMedicion;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CalendarioMedicionApi {

    @GET("/calendariomedicion/get-all")
    Call<List<CalendarioMedicion>> getAllCalendarioMedicion();

    @GET("/calendariomedicion/calendario/{codCalendario}")
    Call<List<CalendarioMedicion>> getByCodCalendarioMedicion(@Path("codCalendario") int codCalendario);

    @GET("/calendariomedicion/medicion/{codMedicion}")
    Call<List<CalendarioMedicion>> getByCodMedicion(@Path("codMedicion") int codMedicion);

    @GET("/calendariomedicion/{codCalendarioMedicion}")
    Call<CalendarioMedicion> getByCodCalendarioM(@Path("codCalendarioMedicion") int codCalendarioMedicion);
    @POST("/calendariomedicion/save")
    Call<CalendarioMedicion> saveCalendarioMedicion(@Body CalendarioMedicion calendarioMedicion);


}
