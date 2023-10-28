package com.example.medical.retrofit;

import com.example.medical.model.CalendarioMedicion;
import com.example.medical.model.CalendarioSintoma;
import com.example.medical.model.Usuario;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CalendarioMedicionApi {

    @GET("/calendariomedicion/medicion/{codMedicion}/calendario/{codCalendario}")
    Call<List<CalendarioMedicion>> getByCodMedicionAndCodCalendario(
            @Path("codMedicion") int codMedicion,
            @Path("codCalendario") int codCalendario
    );


    @GET("/calendariomedicion/get-all")
    Call<List<CalendarioMedicion>> getAllCalendarioMedicion();

    @GET("/calendariomedicion/calendario/{codCalendario}")
    Call<List<CalendarioMedicion>> getByCodCalendarioMedicion(@Path("codCalendario") int codCalendario);
    @POST("/calendariomedicion/eliminar")
    Call<CalendarioMedicion> eliminarCalendarioMedicion(@Body CalendarioMedicion calendarioMedicion);
    @POST("/calendariomedicion/modificar")
    Call<CalendarioMedicion> modificarCalendarioMedicion(@Body CalendarioMedicion calendarioMedicion);
    @GET("/calendariomedicion/medicion/{codMedicion}")
    Call<List<CalendarioMedicion>> getByCodMedicion(@Path("codMedicion") int codMedicion);

    @GET("/calendariomedicion/{codCalendarioMedicion}")
    Call<CalendarioMedicion> getByCodCalendarioM(@Path("codCalendarioMedicion") int codCalendarioMedicion);
    @POST("/calendariomedicion/save")
    Call<CalendarioMedicion> saveCalendarioMedicion(@Body CalendarioMedicion calendarioMedicion);



}
