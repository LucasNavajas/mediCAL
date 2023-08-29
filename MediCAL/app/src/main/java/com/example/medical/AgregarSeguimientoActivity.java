package com.example.medical;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.medical.model.Calendario;
import com.example.medical.model.CalendarioSintoma;
import com.example.medical.retrofit.CalendarioApi;
import com.example.medical.retrofit.CalendarioSintomaApi;
import com.example.medical.retrofit.RetrofitService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AgregarSeguimientoActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "MyPrefs"; // Nombre de las preferencias compartidas
    private static final String FIRST_TIME_KEY = "isFirstTime"; // Clave para indicar si es la primera vez
    private Calendario calendarioSeleccionado;
    private int codCalendario;
    private CalendarioApi calendarioApi;
    private CalendarioSintomaApi calendarioSintomaApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.n78_seguimiento);


        RetrofitService retrofitService = new RetrofitService();
        // Recuperar el objeto calendarioSeleccionado del Intent
        Intent intent1 = getIntent();
        codCalendario = intent1.getIntExtra("calendarioSeleccionadoid", 0);
        Log.d("MiApp", "codCalendario en AgregarSeguimientoActivity: " + codCalendario); // Agregar este log

        // Crear una instancia de la interfaz de la API utilizando Retrofit
        calendarioApi = retrofitService.getRetrofit().create(CalendarioApi.class);

        // Hacer la llamada a la API para obtener el calendario seleccionado
        Call<Calendario> call2 = calendarioApi.getByCodCalendario(codCalendario);

        call2.enqueue(new Callback<Calendario>() {
            @Override
            public void onResponse(Call<Calendario> call2, Response<Calendario> response) {
                if (response.isSuccessful()) {
                    calendarioSeleccionado = response.body();
                    if (calendarioSeleccionado != null) {
                        Log.d("MiApp", "Calendario seleccionado encontrado: " + calendarioSeleccionado.getCodCalendario());
                    } else {
                        Log.d("MiApp", "No se encontró el Calendario con codCalendario: " + codCalendario);
                    }
                } else {
                    Log.d("MiApp", "Error en la solicitud: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Calendario> call2, Throwable t) {
                Log.e("MiApp", "Error en la solicitud: " + t.getMessage());
            }
        });

        // Después de obtener el objeto `calendarioSeleccionado` de la respuesta

        if (calendarioSeleccionado != null) {
            Log.d("MiApp", "Calendario seleccionado encontrado: " + calendarioSeleccionado.getCodCalendario());

            // Llamada para obtener todos los CalendarioSintomas
            Call<List<CalendarioSintoma>> call = calendarioSintomaApi.getAllCalendarioSintomas();
            call.enqueue(new Callback<List<CalendarioSintoma>>() {
                @Override
                public void onResponse(Call<List<CalendarioSintoma>> call, Response<List<CalendarioSintoma>> response) {
                    if (response.isSuccessful()) {
                        List<CalendarioSintoma> calendarioSintomas = response.body();
                        if (calendarioSintomas != null && !calendarioSintomas.isEmpty()) {
                            // Filtrar los CalendarioSintomas que corresponden al calendarioSeleccionado
                            List<CalendarioSintoma> calendarioSintomasSeleccionados = new ArrayList<>();
                            for (CalendarioSintoma unCalsintoma : calendarioSintomas) {
                                if (unCalsintoma.getCalendario().getCodCalendario() == calendarioSeleccionado.getCodCalendario()) {
                                    calendarioSintomasSeleccionados.add(unCalsintoma);

                                    RelativeLayout parasacarLayout = findViewById(R.id.parasacar);
                                    parasacarLayout.setVisibility(View.GONE); // Ocultar el RelativeLayout

                                    // Crear un nuevo RelativeLayout para mostrar información del síntoma
                                    RelativeLayout newSintomaLayout = new RelativeLayout(AgregarSeguimientoActivity.this);
                                    newSintomaLayout.setId(View.generateViewId()); // Asignar un ID único


                                    // Agregar propiedades de diseño al nuevo RelativeLayout
                                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                                            RelativeLayout.LayoutParams.MATCH_PARENT,
                                            RelativeLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    params.addRule(RelativeLayout.BELOW, R.id.rectangle_medicion2);
                                    params.setMargins(10, 15, 10, 0);
                                    newSintomaLayout.setLayoutParams(params);
                                    newSintomaLayout.setBackgroundResource(R.drawable.background_rounded_blanco);

                                    // Agregar una ImageView al nuevo RelativeLayout
                                    ImageView imagenSintoma = new ImageView(AgregarSeguimientoActivity.this);
                                    imagenSintoma.setId(View.generateViewId()); // Asignar un ID único
                                    imagenSintoma.setImageResource(R.drawable.persona_sintoma);


                                    // Agregar un TextView al nuevo RelativeLayout para el título del síntoma
                                    TextView textoTituloSintoma = new TextView(AgregarSeguimientoActivity.this);
                                    textoTituloSintoma.setId(View.generateViewId()); // Asignar un ID único
                                    textoTituloSintoma.setText(unCalsintoma.getSintoma().getNombreSintoma());

                                    // Agregar otras propiedades de diseño al TextView si es necesario

                                    // Agregar la imagen y el TextView al nuevo RelativeLayout
                                    newSintomaLayout.addView(imagenSintoma);
                                    newSintomaLayout.addView(textoTituloSintoma);

                                    // Agregar el nuevo RelativeLayout al RelativeLayout principal
                                    RelativeLayout mainLayout = findViewById(R.id.seguimientos);
                                    mainLayout.addView(newSintomaLayout);

                                }
                            }
                            // Ahora tienes la lista de CalendarioSintomas correspondientes al calendarioSeleccionado
                            Log.d("MiApp", "Cantidad de CalendarioSintomas encontrados: " + calendarioSintomasSeleccionados.size());
                        } else {
                            Log.d("MiApp", "No se encontraron CalendarioSintomas");
                        }
                    } else {
                        Log.d("MiApp", "Error en la solicitud: " + response.message());
                    }
                }

                @Override
                public void onFailure(Call<List<CalendarioSintoma>> call, Throwable t) {
                    Log.e("MiApp", "Error en la solicitud: " + t.getMessage());
                }
            });
        } else {
            Log.d("MiApp", "No se encontró el Calendario con codCalendario: " + codCalendario);
        }

        Button agregarSeguimientoButton = findViewById(R.id.button_agregarseguimiento);
        agregarSeguimientoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Abrir la nueva pantalla de Agregar Seguimiento
                Intent intent = new Intent(AgregarSeguimientoActivity.this, ElegirSeguimientoActivity.class);
                intent.putExtra("calendarioSeleccionadoid", codCalendario);
                startActivity(intent);
            }
        });

        findViewById(R.id.boton_volver).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Vuelve a la pantalla anterior
                onBackPressed();
            }
        });


        // Verificar si es la primera vez
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean isFirstTime = preferences.getBoolean(FIRST_TIME_KEY, true);

        // Redirigir según si es la primera vez o no y si existe CalendarioSintoma asociado
        if (isFirstTime) {
            // Redirigir a la actividad n72_inicio_mediciones_sintomas
            Intent intent = new Intent(AgregarSeguimientoActivity.this, InicioMedicionesSintomasActivity.class);
            startActivity(intent);

            // Marcar que ya no es la primera vez
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(FIRST_TIME_KEY, false);
            editor.apply();
        }

    }
}



