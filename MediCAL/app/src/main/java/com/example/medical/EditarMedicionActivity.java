package com.example.medical;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.medical.model.Calendario;
import com.example.medical.model.CalendarioMedicion;
import com.example.medical.retrofit.CalendarioApi;
import com.example.medical.retrofit.CalendarioMedicionApi;
import com.example.medical.retrofit.RetrofitService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditarMedicionActivity extends AppCompatActivity {

    private int codCalendario;
    private int codCalendarioMedicion;
    private int codMedicion;
    private Calendario calendarioSeleccionado;
    private CalendarioApi calendarioApi;
    private CalendarioMedicionApi calendarioMedicionApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.n76_0_establecer_medicion);

        RetrofitService retrofitService = new RetrofitService();

        // Recuperar el objeto calendarioSeleccionado del Intent
        Intent intent1 = getIntent();
        codCalendario = intent1.getIntExtra("calendarioSeleccionadoid", 0);
        Log.d("MiApp", "codCalendario en AgregarSeguimientoActivity: " + codCalendario); // Agregar este log
        codCalendarioMedicion = intent1.getIntExtra("codCalendarioMedicionid", 0);
        codMedicion = intent1.getIntExtra("medicionid",0);

        // Inicializar las APIs utilizando Retrofit
        calendarioApi = retrofitService.getRetrofit().create(CalendarioApi.class);
        calendarioMedicionApi = retrofitService.getRetrofit().create(CalendarioMedicionApi.class);

        // Obtener el calendario seleccionado
        obtenerCalendarioSeleccionado();
        findViewById(R.id.boton_volver).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Vuelve a la pantalla anterior (Puede ser tu pantalla 73)
                onBackPressed();
            }
        });
    }

    private void obtenerCalendarioSeleccionado() {
        Call<Calendario> call = calendarioApi.getByCodCalendario(codCalendario);
        call.enqueue(new Callback<Calendario>() {
            @Override
            public void onResponse(Call<Calendario> call, Response<Calendario> response) {
                if (response.isSuccessful()) {
                    calendarioSeleccionado = response.body();
                    if (calendarioSeleccionado != null) {
                        Log.d("MiApp", "Calendario seleccionado encontrado: " + calendarioSeleccionado.getCodCalendario());
                        obtenerCalendarioMediciones();
                    } else {
                        Log.d("MiApp", "No se encontró el Calendario con codCalendario: " + codCalendario);
                    }
                } else {
                    Log.d("MiApp", "Error en la solicitud: " + response.message());
                }
            }
            @Override
            public void onFailure(Call<Calendario> call, Throwable t) {
                Log.e("MiApp", "Error en la solicitud: " + t.getMessage());
            }
        });
    }

    private void obtenerCalendarioMediciones() {
        Call<List<CalendarioMedicion>> call = calendarioMedicionApi.getByCodCalendarioMedicion(codCalendario);
        call.enqueue(new Callback<List<CalendarioMedicion>>() {
            @Override
            public void onResponse(Call<List<CalendarioMedicion>> call, Response<List<CalendarioMedicion>> response) {
                if (response.isSuccessful()) {
                    List<CalendarioMedicion> calendarioMediciones = response.body();
                    Log.d("MiApp", "Tamaño de la lista calendarioMediciones: " + calendarioMediciones.size());

                    obtenerCalendarioMedicionConMedicion();

                } else {
                    Log.d("MiApp", "Error en la solicitud: " + response.message());
                }
            }
            @Override
            public void onFailure(Call<List<CalendarioMedicion>> call, Throwable t) {
                Log.e("MiApp", "Error en la solicitud: " + t.getMessage());
            }
        });
    }
    private void obtenerCalendarioMedicionConMedicion() {
        Call<List<CalendarioMedicion>> call5 = calendarioMedicionApi.getByCodMedicion(codMedicion);
        call5.enqueue(new Callback<List<CalendarioMedicion>>() {
            @Override
            public void onResponse(Call<List<CalendarioMedicion>> call5, Response<List<CalendarioMedicion>> response) {
                if (response.isSuccessful()) {
                    List<CalendarioMedicion> calendarioMediciones = response.body();
                    Log.d("MiApp", "Tamaño de la lista calendarioMediciones: " + calendarioMediciones.size());



                } else {
                    Log.d("MiApp", "Error en la solicitud: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<CalendarioMedicion>> call5, Throwable t) {
                Log.e("MiApp", "Error en la solicitud: " + t.getMessage());
            }
        });
    }


}