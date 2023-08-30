package com.example.medical;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
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

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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

                        // Crear una instancia de la interfaz de la API utilizando Retrofit
                        calendarioSintomaApi = retrofitService.getRetrofit().create(CalendarioSintomaApi.class);

                        // Llamada para obtener todos los CalendarioSintomas que corresponden al codCalendario
                        Call<List<CalendarioSintoma>> call = calendarioSintomaApi.getByCodCalendarioSintoma(codCalendario);

                        call.enqueue(new Callback<List<CalendarioSintoma>>() {
                            @Override
                            public void onResponse(Call<List<CalendarioSintoma>> call, Response<List<CalendarioSintoma>> response) {
                                if (response.isSuccessful()) {
                                    List<CalendarioSintoma> calendarioSintomas = response.body();
                                    Log.d("MiApp", "Tamaño de la lista calendarioSintomas: " + calendarioSintomas.size());


                                    ImageView paraSacar1 = findViewById(R.id.parasacar1);
                                    TextView paraSacar2 = findViewById(R.id.parasacar2);



                                    // Después de obtener la lista de calendarioSintomas
                                    if (calendarioSintomas != null && !calendarioSintomas.isEmpty()) {
                                        Set<Integer> codigosSintomasProcesados = new HashSet<>();

                                        paraSacar1.setVisibility(View.GONE);
                                        paraSacar2.setVisibility(View.GONE);

                                        for (CalendarioSintoma unCalsintoma : calendarioSintomas) {
                                            int codSintoma = unCalsintoma.getSintoma().getCodSintoma();

                                            if (!codigosSintomasProcesados.contains(codSintoma)) {
                                                Log.d("MiApp", "Creando RelativeLayout para el Sintoma: " + codSintoma);
                                                createRelativeLayoutForSintoma(calendarioSintomas); // Pasar la lista completa

                                                // Agregar el código del síntoma a la lista de códigos procesados
                                                codigosSintomasProcesados.add(codSintoma);
                                            }
                                        }
                                    } else {
                                        Log.d("MiApp", "No se encontraron CalendarioSintomas");
                                        paraSacar1.setVisibility(View.VISIBLE);
                                        paraSacar2.setVisibility(View.VISIBLE);
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
                } else {
                    Log.d("MiApp", "Error en la solicitud: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Calendario> call2, Throwable t) {
                Log.e("MiApp", "Error en la solicitud: " + t.getMessage());
            }
        });




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
                // Iniciar la actividad principal (o la actividad deseada)
                Intent intent = new Intent(AgregarSeguimientoActivity.this, MasActivity.class);
                intent.putExtra("calendarioSeleccionadoid", codCalendario);
                startActivity(intent);
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

    // Método para crear el RelativeLayout para un Sintoma único
    private void createRelativeLayoutForSintoma(List<CalendarioSintoma> calendarioSintomas) {

        RelativeLayout mainLayout = findViewById(R.id.seguimientos);
        RelativeLayout prevLayout = null; // Mantén un seguimiento del último layout agregado
        Set<String> nombresSintomasProcesados = new HashSet<>(); // Utiliza un conjunto para almacenar los nombres de los síntomas procesados
        // Declarar una variable para almacenar la referencia al último RelativeLayout creado
        RelativeLayout ultimoRelativeLayout = null;

        for (CalendarioSintoma unCalsintoma : calendarioSintomas) {

            Log.d("MiApp", "entra al metodo create");

            String nombreSintoma = unCalsintoma.getSintoma().getNombreSintoma();


            if (!nombresSintomasProcesados.contains(nombreSintoma)) {
                // Marca el nombre del síntoma como procesado para que no se cree nuevamente
                nombresSintomasProcesados.add(nombreSintoma);


                RelativeLayout newSintomaLayout = new RelativeLayout(this);
                newSintomaLayout.setId(View.generateViewId());
                newSintomaLayout.setLayoutParams(new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.MATCH_PARENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT
                ));
                newSintomaLayout.setPadding(10, 15, 10, 10);
                newSintomaLayout.setBackgroundResource(R.drawable.background_rounded_blanco);

                // Actualiza la variable "ultimoRelativeLayout" con la referencia al último RelativeLayout creado
                ultimoRelativeLayout = newSintomaLayout;

                ImageView imagenSintoma = new ImageView(this);
                imagenSintoma.setId(View.generateViewId()); // Asignar un ID único a la ImageView
                imagenSintoma.setImageResource(R.drawable.persona_sintoma);
                RelativeLayout.LayoutParams imagenParams = new RelativeLayout.LayoutParams(60, 60);
                imagenParams.setMargins(20, 20, 20, 20);
                imagenParams.addRule(RelativeLayout.ALIGN_PARENT_START, RelativeLayout.TRUE);
                imagenSintoma.setLayoutParams(imagenParams);

                TextView textoTituloSintoma = new TextView(this);
                textoTituloSintoma.setId(View.generateViewId()); // Asignar un ID único al TextView
                textoTituloSintoma.setText(unCalsintoma.getSintoma().getNombreSintoma());
                RelativeLayout.LayoutParams tituloParams = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.MATCH_PARENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT
                );
                tituloParams.setMargins(10, 15, 10, 10);
                tituloParams.addRule(RelativeLayout.END_OF, imagenSintoma.getId());
                textoTituloSintoma.setLayoutParams(tituloParams);
                textoTituloSintoma.setTextSize(25);
                textoTituloSintoma.setTypeface(null, Typeface.BOLD);

                TextView textoSintoma = new TextView(this);
                textoSintoma.setId(View.generateViewId());
                List<CalendarioSintoma> mismosSintomas = obtenerCalendarioSintomasMismoSintoma(calendarioSintomas, unCalsintoma);
                String ultimaFecha = obtenerUltimaFechaSeguimiento(mismosSintomas);
                textoSintoma.setText("Último seguimiento: " + ultimaFecha);
                RelativeLayout.LayoutParams sintomaParams = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.MATCH_PARENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT
                );
                sintomaParams.addRule(RelativeLayout.BELOW, textoTituloSintoma.getId());
                sintomaParams.addRule(RelativeLayout.END_OF, imagenSintoma.getId());
                sintomaParams.setMargins(20, 1, 20, 20);
                textoSintoma.setLayoutParams(sintomaParams);
                textoSintoma.setTextColor(Color.BLACK);
                textoSintoma.setTextSize(21);

                Button buttonMasInfo = new Button(this);
                buttonMasInfo.setId(View.generateViewId()); // Asignar un ID único al Button
                buttonMasInfo.setText("Más información");
                RelativeLayout.LayoutParams buttonParams = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT
                );
                buttonParams.addRule(RelativeLayout.BELOW, textoSintoma.getId());
                buttonParams.addRule(RelativeLayout.END_OF, imagenSintoma.getId());
                buttonParams.setMargins(0, 0, 0, 15);
                buttonMasInfo.setLayoutParams(buttonParams);
                buttonMasInfo.setBackgroundResource(R.drawable.boton_redondo_verde_oscuro);
                buttonMasInfo.setFocusable(false);
                buttonMasInfo.setTypeface(Typeface.DEFAULT_BOLD);
                buttonMasInfo.setPadding(20, 0, 20, 0);
                buttonMasInfo.setTextColor(Color.WHITE);
                buttonMasInfo.setTextSize(20);

                // Agregar las vistas al RelativeLayout
                newSintomaLayout.addView(imagenSintoma);
                newSintomaLayout.addView(textoTituloSintoma);
                newSintomaLayout.addView(textoSintoma);
                newSintomaLayout.addView(buttonMasInfo);

                // Configurar el OnClickListener para el botón "Más información"
                buttonMasInfo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Crear un Intent para abrir la actividad MasInfoSintomaActivity
                        Intent intent = new Intent(AgregarSeguimientoActivity.this, MasInfoSintomaActivity.class);


                        // Iniciar la actividad MasInfoSintomaActivity
                        startActivity(intent);
                    }
                });

                // Configurar reglas de posicionamiento para el nuevo layout
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.MATCH_PARENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT
                );
                layoutParams.setMargins(0, 0, 0, 20); // Agregar margen inferior entre los layouts

                if (prevLayout != null) {
                    layoutParams.addRule(RelativeLayout.BELOW, prevLayout.getId());
                }

                newSintomaLayout.setLayoutParams(layoutParams);

                // Agregar el nuevo RelativeLayout al layout principal
                mainLayout.addView(newSintomaLayout);

                // Mantén el nuevo layout como el anterior para la siguiente iteración
                prevLayout = newSintomaLayout;



                // Después de crear todos los RelativeLayouts
                Button buttonAgregarSeguimiento = findViewById(R.id.button_agregarseguimiento);
                if (ultimoRelativeLayout != null) {
                    RelativeLayout.LayoutParams buttonParamss = (RelativeLayout.LayoutParams) buttonAgregarSeguimiento.getLayoutParams();
                    buttonParamss.addRule(RelativeLayout.BELOW, ultimoRelativeLayout.getId());
                    buttonAgregarSeguimiento.setLayoutParams(buttonParamss);
                }
            }


        }
    }

    private String obtenerUltimaFechaSeguimiento(List<CalendarioSintoma> calendarioSintomas) {
        String ultimaFecha = "";
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd 'de' MMM, HH:mm", Locale.getDefault());

        for (CalendarioSintoma cs : calendarioSintomas) {
            LocalDateTime fechaSeguimiento = cs.getFechaCalendarioSintoma();
            if (fechaSeguimiento != null) {
                if (ultimaFecha.isEmpty()) {
                    ultimaFecha = fechaSeguimiento.format(dateFormat);
                } else {
                    LocalDateTime ultimaFechaDate = LocalDateTime.parse(ultimaFecha, dateFormat);
                    if (fechaSeguimiento.isAfter(ultimaFechaDate)) {
                        ultimaFecha = fechaSeguimiento.format(dateFormat);
                    }
                }
            }
        }

        return ultimaFecha;
    }

    private List<CalendarioSintoma> obtenerCalendarioSintomasMismoSintoma(List<CalendarioSintoma> calendarioSintomas, CalendarioSintoma sintoma) {
        return calendarioSintomas.stream()
                .filter(cs -> cs.getSintoma().equals(sintoma.getSintoma()))
                .collect(Collectors.toList());
    }




}



