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
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.medical.model.Calendario;
import com.example.medical.model.CalendarioMedicion;
import com.example.medical.model.CalendarioSintoma;
import com.example.medical.retrofit.CalendarioApi;
import com.example.medical.retrofit.CalendarioMedicionApi;
import com.example.medical.retrofit.CalendarioSintomaApi;
import com.example.medical.retrofit.RetrofitService;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
    private ImageView paraSacar1;
    private TextView paraSacar2;
    private CalendarioApi calendarioApi;
    private CalendarioSintomaApi calendarioSintomaApi;
    private CalendarioMedicionApi calendarioMedicionApi;
    private RelativeLayout ultimaRelativeLayoutSintoma=null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.n78_seguimiento);
        paraSacar1 = findViewById(R.id.parasacar1);
        paraSacar2 = findViewById(R.id.parasacar2);
        paraSacar1.setVisibility(View.VISIBLE);
        paraSacar2.setVisibility(View.VISIBLE);


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

                                    // Ordenar calendarioSintomas por fecha de seguimiento (más reciente primero)
                                    Collections.sort(calendarioSintomas, new Comparator<CalendarioSintoma>() {
                                        @Override
                                        public int compare(CalendarioSintoma cs1, CalendarioSintoma cs2) {
                                            return cs2.getFechaCalendarioSintoma().compareTo(cs1.getFechaCalendarioSintoma());
                                        }
                                    });


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
                                                codigosSintomasProcesados.add(codSintoma);
                                            }
                                        }
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

                        // Crear una instancia de la interfaz de la API utilizando Retrofit
                        calendarioMedicionApi = retrofitService.getRetrofit().create(CalendarioMedicionApi.class);

                        // Llamada para obtener todos los CalendarioMedicion que corresponden al codCalendario
                        Call<List<CalendarioMedicion>> call3 = calendarioMedicionApi.getByCodCalendarioMedicion(codCalendario);

                        call3.enqueue(new Callback<List<CalendarioMedicion>>() {
                            @Override
                            public void onResponse(Call<List<CalendarioMedicion>> call3, Response<List<CalendarioMedicion>> response) {
                                if (response.isSuccessful()) {
                                    List<CalendarioMedicion> calendarioMediciones = response.body();
                                    Log.d("MiApp", "Tamaño de la lista calendarioMediciones: " + calendarioMediciones.size());

                                    // Ordenar calendarioMediciones por fecha de seguimiento (más reciente primero)
                                    Collections.sort(calendarioMediciones, new Comparator<CalendarioMedicion>() {
                                        @Override
                                        public int compare(CalendarioMedicion cm1, CalendarioMedicion cm2) {
                                            return cm2.getFechaCalendarioMedicion().compareTo(cm1.getFechaCalendarioMedicion());
                                        }
                                    });


                                    // Después de obtener la lista de calendarioMediciones
                                    if (calendarioMediciones != null && !calendarioMediciones.isEmpty()) {
                                        Set<Integer> codigosMedicionesProcesadas = new HashSet<>();
                                        paraSacar1.setVisibility(View.GONE);
                                        paraSacar2.setVisibility(View.GONE);

                                        for (CalendarioMedicion unaCalMedicion : calendarioMediciones) {
                                            int codMedicion = unaCalMedicion.getMedicion().getCodMedicion();

                                            if (!codigosMedicionesProcesadas.contains(codMedicion)) {
                                                Log.d("MiApp", "Creando RelativeLayout para la Medición: " + codMedicion);
                                                createRelativeLayoutForMedicion(calendarioMediciones); // Pasar la lista completa
                                                codigosMedicionesProcesadas.add(codMedicion);
                                            }
                                        }
                                    } else {
                                        Log.d("MiApp", "No se encontraron CalendarioMediciones");
                                    }

                                } else {
                                    Log.d("MiApp", "Error en la solicitud: " + response.message());
                                }
                            }

                            @Override
                            public void onFailure(Call<List<CalendarioMedicion>> call3, Throwable t) {
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
                intent.putExtra("codUsuario", getIntent().getIntExtra("codUsuario",0));
                intent.putExtra("calendarioSeleccionadoid", getIntent().getIntExtra("calendarioSeleccionadoid",0));
                startActivity(intent);
            }
        });

        findViewById(R.id.boton_volver).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Iniciar la actividad principal (o la actividad deseada)
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
            intent.putExtra("codUsuario", getIntent().getIntExtra("codUsuario",0));
            intent.putExtra("calendarioSeleccionadoid", getIntent().getIntExtra("calendarioSeleccionadoid",0));
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
                newSintomaLayout.setPadding(20, 15, 20, 10);
                newSintomaLayout.setBackgroundResource(R.drawable.background_rounded_blanco);


                // Actualiza la variable "ultimoRelativeLayout" con la referencia al último RelativeLayout creado
                ultimoRelativeLayout = newSintomaLayout;

                ImageView imagenSintoma = new ImageView(this);
                imagenSintoma.setId(View.generateViewId()); // Asignar un ID único a la ImageView
                imagenSintoma.setImageResource(R.drawable.persona_sintoma);
                RelativeLayout.LayoutParams imagenParams = new RelativeLayout.LayoutParams(
                        getResources().getDimensionPixelSize(R.dimen.imagen_sintoma_width),
                        getResources().getDimensionPixelSize(R.dimen.imagen_sintoma_height)
                );
                imagenParams.setMargins(
                        getResources().getDimensionPixelSize(R.dimen.imagen_sintoma_margin_start),
                        getResources().getDimensionPixelSize(R.dimen.imagen_sintoma_margin_top),
                        getResources().getDimensionPixelSize(R.dimen.imagen_sintoma_margin_end),
                        getResources().getDimensionPixelSize(R.dimen.imagen_sintoma_margin_bottom)
                );
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
                buttonMasInfo.setAllCaps(false);
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
                buttonMasInfo.setPadding(getResources().getDimensionPixelSize(R.dimen.button_padding_start),
                        0,
                        getResources().getDimensionPixelSize(R.dimen.button_padding_end),
                        0);
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
                        intent.putExtra("codUsuario", getIntent().getIntExtra("codUsuario",0));
                        intent.putExtra("calendarioSeleccionadoid", getIntent().getIntExtra("calendarioSeleccionadoid",0));

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


                    ultimaRelativeLayoutSintoma = ultimoRelativeLayout;
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

    private void createRelativeLayoutForMedicion(List<CalendarioMedicion> calendarioMediciones) {

        RelativeLayout mainLayout = findViewById(R.id.seguimientos); // Cambiar por el ID correcto
        RelativeLayout prevLayout = ultimaRelativeLayoutSintoma;
        Set<String> nombresMedicionesProcesados = new HashSet<>();
        RelativeLayout ultimoRelativeLayout = ultimaRelativeLayoutSintoma;

        for (CalendarioMedicion unaCalMedicion : calendarioMediciones) {
            String nombreMedicion = unaCalMedicion.getMedicion().getNombreMedicion();

            if (!nombresMedicionesProcesados.contains(nombreMedicion)) {
                nombresMedicionesProcesados.add(nombreMedicion);

                RelativeLayout newMedicionLayout = new RelativeLayout(this);
                newMedicionLayout.setId(View.generateViewId());
                newMedicionLayout.setLayoutParams(new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.MATCH_PARENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT
                ));
                newMedicionLayout.setPadding(20, 15, 20, 10);
                newMedicionLayout.setBackgroundResource(R.drawable.background_rounded_blanco);

                ultimoRelativeLayout = newMedicionLayout;

                ImageView imagenMedicion = new ImageView(this);
                imagenMedicion.setId(View.generateViewId());
                imagenMedicion.setImageResource(R.drawable.mano_corazon); // Aquí cambia a la imagen que quieras
                RelativeLayout.LayoutParams imagenParams = new RelativeLayout.LayoutParams(
                        getResources().getDimensionPixelSize(R.dimen.imagen_medicion_width),
                        getResources().getDimensionPixelSize(R.dimen.imagen_medicion_height)
                );
                imagenParams.setMargins(
                        getResources().getDimensionPixelSize(R.dimen.imagen_medicion_margin_start),
                        getResources().getDimensionPixelSize(R.dimen.imagen_medicion_margin_top),
                        getResources().getDimensionPixelSize(R.dimen.imagen_medicion_margin_end),
                        getResources().getDimensionPixelSize(R.dimen.imagen_medicion_margin_bottom)
                );
                imagenParams.addRule(RelativeLayout.ALIGN_PARENT_START, RelativeLayout.TRUE);
                imagenMedicion.setLayoutParams(imagenParams);


                TextView textoTituloMedicion = new TextView(this);
                textoTituloMedicion.setId(View.generateViewId()); // Asignar un ID único al TextView
                textoTituloMedicion.setText(unaCalMedicion.getMedicion().getNombreMedicion());
                RelativeLayout.LayoutParams tituloParams = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.MATCH_PARENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT
                );
                tituloParams.setMargins(10, 15, 10, 10);
                tituloParams.addRule(RelativeLayout.END_OF, imagenMedicion.getId());
                textoTituloMedicion.setLayoutParams(tituloParams);
                textoTituloMedicion.setTextSize(25);
                textoTituloMedicion.setTypeface(null, Typeface.BOLD);

                TextView textoMedicion = new TextView(this);
                textoMedicion.setId(View.generateViewId());
                List<CalendarioMedicion> mismasMediciones = obtenerCalendarioMedicionesMismaMedicion(calendarioMediciones, unaCalMedicion);
                String ultimaFechaMedicion = obtenerUltimaFechaSeguimientoMedicion(mismasMediciones);
                textoMedicion.setText("Último seguimiento: " + ultimaFechaMedicion);
                RelativeLayout.LayoutParams medicionParams = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.MATCH_PARENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT
                );
                medicionParams.addRule(RelativeLayout.BELOW, textoTituloMedicion.getId());
                medicionParams.addRule(RelativeLayout.END_OF, imagenMedicion.getId());
                medicionParams.setMargins(20, 1, 20, 20);
                textoMedicion.setLayoutParams(medicionParams); // Aplicar los parámetros al TextView
                textoMedicion.setTextColor(Color.BLACK);
                textoMedicion.setTextSize(21);

                Button buttonMasInfo = new Button(this);
                buttonMasInfo.setId(View.generateViewId()); // Asignar un ID único al Button
                buttonMasInfo.setText("Más información");
                buttonMasInfo.setAllCaps(false);
                RelativeLayout.LayoutParams buttonParamsm = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT
                );
                buttonParamsm.addRule(RelativeLayout.BELOW, textoMedicion.getId());
                buttonParamsm.addRule(RelativeLayout.END_OF, imagenMedicion.getId());
                buttonParamsm.setMargins(0, 0, 0, 15);
                buttonMasInfo.setLayoutParams(buttonParamsm);
                buttonMasInfo.setBackgroundResource(R.drawable.boton_redondo_verde_oscuro);
                buttonMasInfo.setFocusable(false);
                buttonMasInfo.setTypeface(Typeface.DEFAULT_BOLD);
                buttonMasInfo.setPadding(getResources().getDimensionPixelSize(R.dimen.button_padding_start),
                        0,
                        getResources().getDimensionPixelSize(R.dimen.button_padding_end),
                        0);
                buttonMasInfo.setTextColor(Color.WHITE);
                buttonMasInfo.setTextSize(20);

                // Agregar las vistas al RelativeLayout
                newMedicionLayout.addView(imagenMedicion);
                newMedicionLayout.addView(textoTituloMedicion);
                newMedicionLayout.addView(textoMedicion);
                newMedicionLayout.addView(buttonMasInfo);

                // Configurar el OnClickListener para el botón "Más información"
                buttonMasInfo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Crear un Intent para abrir la actividad MasInfoSintomaActivity
                        Intent intent = new Intent(AgregarSeguimientoActivity.this, MasInfoMedicionActivity.class);
                        intent.putExtra("codUsuario", getIntent().getIntExtra("codUsuario",0));
                        intent.putExtra("calendarioSeleccionadoid", getIntent().getIntExtra("calendarioSeleccionadoid",0));

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

                newMedicionLayout.setLayoutParams(layoutParams);

                // Agregar el nuevo RelativeLayout al layout principal
                mainLayout.addView(newMedicionLayout);

                // Mantén el nuevo layout como el anterior para la siguiente iteración
                prevLayout = newMedicionLayout;

                // Después de crear todos los RelativeLayouts
                Button buttonAgregarSeguimiento = findViewById(R.id.button_agregarseguimiento);
                if (ultimoRelativeLayout != null) {
                    RelativeLayout.LayoutParams buttonParams = (RelativeLayout.LayoutParams) buttonAgregarSeguimiento.getLayoutParams();
                    buttonParams.addRule(RelativeLayout.BELOW, ultimoRelativeLayout.getId());
                    buttonAgregarSeguimiento.setLayoutParams(buttonParams);
                }
            }
        }
    }

    private String obtenerUltimaFechaSeguimientoMedicion(List<CalendarioMedicion> calendarioMediciones) {
        String ultimaFecha = "";
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd 'de' MMM, HH:mm", Locale.getDefault());

        for (CalendarioMedicion cm : calendarioMediciones) {
            LocalDateTime fechaSeguimiento = cm.getFechaCalendarioMedicion();
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

    private List<CalendarioMedicion> obtenerCalendarioMedicionesMismaMedicion(List<CalendarioMedicion> calendarioMediciones, CalendarioMedicion medicion) {
        return calendarioMediciones.stream()
                .filter(cm -> cm != null && medicion != null && cm.getMedicion() != null && cm.getMedicion().equals(medicion.getMedicion()))
                .collect(Collectors.toList());
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AgregarSeguimientoActivity.this, MasActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("codUsuario", getIntent().getIntExtra("codUsuario",0));
        intent.putExtra("calendarioSeleccionadoid", getIntent().getIntExtra("calendarioSeleccionadoid",0));
        startActivity(intent);
    }
}



