package com.example.medical;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.medical.model.Calendario;
import com.example.medical.model.CalendarioMedicion;
import com.example.medical.model.CalendarioSintoma;
import com.example.medical.retrofit.CalendarioApi;
import com.example.medical.retrofit.CalendarioMedicionApi;
import com.example.medical.retrofit.CalendarioSintomaApi;
import com.example.medical.retrofit.RetrofitService;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MasInfoSintomaActivity extends AppCompatActivity {
    private int codCalendario;
    private Calendario calendarioSeleccionado;
    private int codCalendarioSintoma;
    private int codSintoma;
    private CalendarioApi calendarioApi;
    private CalendarioSintomaApi calendarioSintomaApi;
    // Declarar los botones
    Button btnSemana, btnMes, btn3Mes, btnAnio;
    private int mesActual;
    private TextView fechaHoy;
    private ImageView imageMenor;
    private ImageView imageMayor;
    private int mesSeleccionado;
    private int diaActual;
    private int añoActual;
    private int añoSeleccionado;
    private Calendar currentDate;
    private Calendar selectedDate;
    boolean mostrarMes;
    boolean mostrar3Meses;
    boolean mostrarSemana;
    private int mesActualMes;
    private int añoActualMes;
    private int mesActual3Meses;
    private int añoActualAnio;
    private Calendar fechaActualSemanaa ;
    private Calendar[] fechaActualSemana = new Calendar[1];
    private int primerDiaSemana;
    private int ultimoDiaSemana;
    private String nombreMesActual;
    private String nombreMesActualIn;
    private String fechaInicioSem;
    private String fechaFinSem;
    private String fechaInicioSemana ="1";
    private String fechaFinSemana= "1";
    List<CalendarioSintoma> calendarioSintomasSelec ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.n79_0_mas_info_medicion);

        RetrofitService retrofitService = new RetrofitService();

        // Recuperar el objeto calendarioSeleccionado del Intent
        Intent intent1 = getIntent();
        codCalendario = intent1.getIntExtra("calendarioSeleccionadoid", 0);
        Log.d("MiApp", "codCalendario en AgregarSeguimientoActivity: " + codCalendario); // Agregar este log
        codCalendarioSintoma = intent1.getIntExtra("codCalendarioSintomaid", 0);
        codSintoma = intent1.getIntExtra("sintomaid", 0);

        // Inicializar las APIs utilizando Retrofit
        calendarioApi = retrofitService.getRetrofit().create(CalendarioApi.class);
        calendarioSintomaApi = retrofitService.getRetrofit().create(CalendarioSintomaApi.class);

        // Obtener el calendario seleccionado
        obtenerCalendarioSeleccionado();
        findViewById(R.id.boton_volver).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        // Inicializar los botones
        btnSemana = findViewById(R.id.btnSemana);
        btnMes = findViewById(R.id.btnMes);
        btn3Mes = findViewById(R.id.btn3Mes);
        btnAnio = findViewById(R.id.btnAnio);

        // Inicializar las imágenes de flecha
        imageMenor = findViewById(R.id.imageMenor);
        imageMayor = findViewById(R.id.imageMayor);

        // Inicializar currentDate en onCreate
        currentDate = Calendar.getInstance();
        selectedDate = Calendar.getInstance();

        mostrarMes = true;
        mostrar3Meses = false;
        mostrarSemana = false;

        // Declarar y definir el array de meses abreviados
        String[] mesesAbreviados = {"Ene", "Feb", "Mar", "Abr", "May", "Jun", "Jul", "Ago", "Sep", "Oct", "Nov", "Dic"};
        // Declarar y definir el array de meses abreviados
        String[] mesesAbreviados3meses = {"Ene", "Feb", "Mar", "Abr", "May", "Jun", "Jul", "Ago", "Sep", "Oct", "Nov", "Dic"};

        // Actualizar la fecha al mes actual
        updateFechaHoy(añoActual, mesesAbreviados[mesActual]);

        // Declarar e inicializar la variable añoActual como un arreglo de un solo elemento
        final int[] añoActual = {Calendar.getInstance().get(Calendar.YEAR)};
        Calendar fechaActual = Calendar.getInstance();

        // Declarar una variable para rastrear la fecha actual de la semana
        Calendar fechaActualSemana2 = Calendar.getInstance();

// Lugar donde se inicializa 'fechaActualSemana' (fuera del método)
        fechaActualSemana2.setFirstDayOfWeek(Calendar.SUNDAY);

        imageMenor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mostrarMes == true) {
                    if (mesActual > 0) {
                        mesActual--;
                    } else {
                        mesActual = 11;
                        añoActual[0]--; // Disminuir el año
                    }
                    updateFechaHoy(añoActual[0], mesesAbreviados3meses[mesActual]);
                    CrearRelativeLayout(calendarioSintomasSelec);
                } else if (mostrar3Meses) {
                    String fechaTexto = fechaHoy.getText().toString();
                    String[] partesFecha = fechaTexto.split(" ");
                    String mesInicial = partesFecha[0];
                    String mesFinal = partesFecha[2];
                    int año = Integer.parseInt(partesFecha[3]);
                    int mesInicialIndex = Arrays.asList(mesesAbreviados).indexOf(mesInicial);
                    int mesFinalIndex = Arrays.asList(mesesAbreviados).indexOf(mesFinal);
                    mesInicialIndex -= 3;
                    mesFinalIndex -= 3;
                    if (mesInicialIndex < 0) {
                        mesInicialIndex += 12;
                    }
                    if (mesFinalIndex < 0) {
                        mesFinalIndex += 12;
                        año--;
                    }
                    String nuevoMesInicial = mesesAbreviados[mesInicialIndex];
                    String nuevoMesFinal = mesesAbreviados[mesFinalIndex];
                    updateFechaHoy(año, nuevoMesInicial + " - " + nuevoMesFinal);
                    CrearRelativeLayout(calendarioSintomasSelec);
                }
                else if (mostrarSemana) {
                    String fechaTexto = fechaHoy.getText().toString();
                    Calendar fechaActualSemana2 = Calendar.getInstance();

                    if (!fechaTexto.isEmpty()) {
                        // Si hay un texto en fechaTexto, entonces disminuir la fecha actual
                        fechaActualSemanaa.add(Calendar.DAY_OF_MONTH, -7);
                    }

                    fechaActualSemanaa.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
                    int primerDiaSemana = fechaActualSemanaa.get(Calendar.DAY_OF_MONTH);
                    int mesActualSemana = fechaActualSemanaa.get(Calendar.MONTH);
                    Resources resources = getResources();
                    String[] meses_Abreviados = resources.getStringArray(R.array.meses_abreviados);
                    String[] meses_Completos = resources.getStringArray(R.array.months_array);
                    fechaActualSemanaa.add(Calendar.DAY_OF_MONTH, 6);
                    int ultimoDiaSemana = fechaActualSemanaa.get(Calendar.DAY_OF_MONTH);

                    if (mesActualSemana != fechaActualSemanaa.get(Calendar.MONTH)) {
                        // Los días están en meses diferentes
                        int mesSiguiente = fechaActualSemanaa.get(Calendar.MONTH);
                        String nombreMesSiguiente = meses_Abreviados[mesSiguiente];
                        String nombreMesActual = meses_Abreviados[mesActualSemana];
                        updateFechaHoy(0, primerDiaSemana + " de " + nombreMesActual + " - " + ultimoDiaSemana + " de " + nombreMesSiguiente);
                        CrearRelativeLayout(calendarioSintomasSelec);
                    } else {
                        String nombreMesActual = meses_Completos[mesActualSemana];
                        updateFechaHoy(0, primerDiaSemana + " - " + ultimoDiaSemana + " de " + nombreMesActual);
                        CrearRelativeLayout(calendarioSintomasSelec);
                    }

                } else {
                    añoActual[0]--; // Disminuir el año
                    updateFechaHoy(añoActual[0], ""); // Deja el mes en blanco
                    CrearRelativeLayout(calendarioSintomasSelec);
                }

            }
        });

        imageMayor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mostrarMes) {
                    String fechaTexto = fechaHoy.getText().toString();
                    String[] partesFecha = fechaTexto.split(" ");
                    String mesAbreviado = partesFecha[0];
                    int año = Integer.parseInt(partesFecha[1]);
                    int mesIndex = Arrays.asList(mesesAbreviados3meses).indexOf(mesAbreviado);
                    Calendar selectedDate = Calendar.getInstance();
                    selectedDate.set(Calendar.YEAR, año);
                    selectedDate.set(Calendar.MONTH, mesIndex);
                    selectedDate.add(Calendar.MONTH, 1);
                    if (selectedDate.get(Calendar.MONTH) == Calendar.JANUARY) {
                        selectedDate.set(Calendar.MONTH, Calendar.JANUARY); // Establece el mes a enero
                    }
                    Calendar currentDate = Calendar.getInstance();
                    if (!selectedDate.after(currentDate)) {
                        añoActual[0] = selectedDate.get(Calendar.YEAR); // Actualiza el año actual en el arreglo
                        updateFechaHoy(añoActual[0], mesesAbreviados3meses[selectedDate.get(Calendar.MONTH)]);
                        CrearRelativeLayout(calendarioSintomasSelec);
                    }
                } else if (mostrar3Meses) {
                    String fechaTexto = fechaHoy.getText().toString();
                    String[] partesFecha = fechaTexto.split(" ");
                    String mesInicial = partesFecha[0];
                    String mesFinal = partesFecha[2];
                    int año = Integer.parseInt(partesFecha[3]);
                    int mesInicialIndex = Arrays.asList(mesesAbreviados).indexOf(mesInicial);
                    int mesFinalIndex = Arrays.asList(mesesAbreviados).indexOf(mesFinal);
                    LocalDate fechaActual = LocalDate.now();
                    int mesAc = fechaActual.getMonthValue() -1;
                    int añoAc = fechaActual.getYear();
                    if (año != añoAc) {
                        mesInicialIndex += 3;
                        if (mesInicialIndex > 11) {
                            mesInicialIndex -= 12;
                            año++;
                        }
                        mesFinalIndex += 3;
                        if (mesFinalIndex > 11) {
                            mesFinalIndex -= 12;
                        }
                        updateFechaHoy(año, mesesAbreviados[mesInicialIndex] + " - " + mesesAbreviados[mesFinalIndex]);
                        CrearRelativeLayout(calendarioSintomasSelec);
                    } else if (mesFinalIndex != mesAc){
                        mesInicialIndex += 3;
                        if (mesInicialIndex > 11) {
                            mesInicialIndex -= 12;
                            año++;
                        }
                        mesFinalIndex += 3;
                        if (mesFinalIndex > 11) {
                            mesFinalIndex -= 12;
                        }
                        updateFechaHoy(año, mesesAbreviados[mesInicialIndex] + " - " + mesesAbreviados[mesFinalIndex]);
                        CrearRelativeLayout(calendarioSintomasSelec);
                    }
                } else if (mostrarSemana) {
                    fechaActualSemanaa.add(Calendar.DAY_OF_MONTH, +7);
                    fechaActualSemanaa.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
                    int primerDiaSemana = fechaActualSemanaa.get(Calendar.DAY_OF_MONTH);
                    int mesActualSemana = fechaActualSemanaa.get(Calendar.MONTH);
                    Resources resources = getResources();
                    String[] meses_Abreviados = resources.getStringArray(R.array.meses_abreviados);
                    String[] meses_Completos = resources.getStringArray(R.array.months_array);
                    fechaActualSemanaa.add(Calendar.DAY_OF_MONTH, 6);
                    int ultimoDiaSemana = fechaActualSemanaa.get(Calendar.DAY_OF_MONTH);
                    if (mesActualSemana != fechaActualSemanaa.get(Calendar.MONTH)) {
                        // Los días están en meses diferentes
                        int mesSiguiente = fechaActualSemanaa.get(Calendar.MONTH);
                        String nombreMesSiguiente = meses_Abreviados[mesSiguiente];
                        String nombreMesActual = meses_Abreviados[mesActualSemana];
                        updateFechaHoy(0, primerDiaSemana + " de " + nombreMesActual + " - " + ultimoDiaSemana + " de " + nombreMesSiguiente);
                        CrearRelativeLayout(calendarioSintomasSelec);
                    } else {
                        String nombreMesActual = meses_Completos[mesActualSemana];
                        updateFechaHoy(0, primerDiaSemana + " - " + ultimoDiaSemana + " de " + nombreMesActual);
                        CrearRelativeLayout(calendarioSintomasSelec);
                    }

                } else {
                    // Si mostrarMes es falso, aumentar el año solo si no es el año actual
                    int añoActualValor = Calendar.getInstance().get(Calendar.YEAR);
                    Log.e("MiApp", "añoaValorctual" + añoActualValor);
                    Log.e("MiApp", "añoactual" + añoActual);
                    // Establecer las variables de control apropiadas
                    mostrarMes = false;
                    mostrar3Meses = false;
                    if (añoActualValor != añoActual[0]) {
                        añoActual[0]++; // Aumentar el año
                        updateFechaHoy(añoActual[0], ""); // Dejar el mes en blanco
                        CrearRelativeLayout(calendarioSintomasSelec);
                    } else {
                        updateFechaHoy(añoActual[0], ""); // Dejar el mes en blanco
                        CrearRelativeLayout(calendarioSintomasSelec);
                    }
                }
            }
        });

        // Variables para los años actuales en cada modo
        añoActualMes = Calendar.getInstance().get(Calendar.YEAR);
        int añoActual3Meses = Calendar.getInstance().get(Calendar.YEAR);
        añoActualAnio = Calendar.getInstance().get(Calendar.YEAR);
        int añoActualSemana = Calendar.getInstance().get(Calendar.YEAR);
        Calendar calendar = Calendar.getInstance();
        mesActualMes = Calendar.getInstance().get(Calendar.MONTH);
        mesActual3Meses = calendar.get(Calendar.MONTH);
        fechaActualSemanaa = calendar.getInstance();
        String[] fechaInicioSem = {null};
        String[] fechaFinSem = {null};


        View.OnClickListener buttonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Deseleccionar todos los botones
                deselectAllButtons();
                // Según el botón presionado, seleccionarlo y realizar acciones correspondientes
                switch (v.getId()) {
                    case R.id.btnSemana:
                        selectButton(btnSemana);
                        mostrarSemana = true;
                        mostrarMes = false;
                        mostrar3Meses = false;
                        añoActual[0] = Integer.parseInt(String.valueOf(añoActualSemana));
                        fechaActualSemana[0] = fechaActualSemanaa;
                        fechaInicioSem[0]= String.valueOf(fechaInicioSemana);
                        fechaFinSem[0]=String.valueOf(fechaFinSemana);
                        if (fechaInicioSemana.equals("1") && fechaFinSemana.equals("1")){
                            Calendar calendar = Calendar.getInstance();
                            int primerDiaSemana = calendar.get(Calendar.DAY_OF_MONTH);
                            while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
                                calendar.add(Calendar.DAY_OF_MONTH, -1);
                                primerDiaSemana = calendar.get(Calendar.DAY_OF_MONTH);
                            }
                            int mesPrimerDia = calendar.get(Calendar.MONTH);
                            Resources resources = getResources();
                            String[] mesesCompletos = resources.getStringArray(R.array.months_array);
                            String nombreMesPrimerDia = mesesCompletos[mesPrimerDia];
                            calendar.add(Calendar.DAY_OF_MONTH, 6);
                            int ultimoDiaSemana = calendar.get(Calendar.DAY_OF_MONTH);
                            int mesUltimoDia = calendar.get(Calendar.MONTH);
                            String nombreMesUltimoDia = mesesCompletos[mesUltimoDia];
                            if (nombreMesPrimerDia == nombreMesUltimoDia) {
                                fechaInicioSemana = primerDiaSemana + "";
                                fechaFinSemana = ultimoDiaSemana + " de " + nombreMesPrimerDia;
                                fechaInicioSem[0] = String.valueOf(fechaInicioSemana);
                                fechaFinSem[0] = String.valueOf(fechaFinSemana);
                                updateFechaHoy(0, fechaInicioSem[0] + " - " + fechaFinSem[0]);
                                CrearRelativeLayout(calendarioSintomasSelec);
                            } else {
                                fechaInicioSemana = primerDiaSemana + " de "+ nombreMesPrimerDia;
                                fechaFinSemana = ultimoDiaSemana + " de " + nombreMesUltimoDia;
                                Log.d("Miapp", "primerDia" + primerDiaSemana);
                                fechaInicioSem[0] = String.valueOf(fechaInicioSemana);
                                fechaFinSem[0] = String.valueOf(fechaFinSemana);
                                updateFechaHoy(0, fechaInicioSem[0] + " - " + fechaFinSem[0]);
                                CrearRelativeLayout(calendarioSintomasSelec);
                            }
                        } else {
                            updateFechaHoy(0, fechaInicioSem[0] + " - " + fechaFinSem[0]);
                            CrearRelativeLayout(calendarioSintomasSelec);
                        }
                        break;
                    case R.id.btnMes:
                        selectButton(btnMes);
                        mostrarMes = true;
                        mostrar3Meses = false;
                        mostrarSemana = false;
                        añoActual[0] = añoActualMes;
                        mesActual = mesActualMes;
                        updateFechaHoy(añoActual[0], mesesAbreviados[mesActual]);
                        CrearRelativeLayout(calendarioSintomasSelec);
                        break;
                    case R.id.btn3Mes:
                        selectButton(btn3Mes);
                        mostrarMes = false;
                        mostrar3Meses = true;
                        mostrarSemana = false;
                        añoActual[0] = añoActual3Meses;
                        mesActual = mesActual3Meses;
                        break;
                    case R.id.btnAnio:
                        selectButton(btnAnio);
                        mostrarMes = false;
                        mostrar3Meses = false;
                        mostrarSemana = false;
                        añoActual[0] = añoActualAnio;
                        updateFechaHoy(añoActual[0], "");
                        break;
                }

                if (mostrarMes) {
                    mesSeleccionado = Calendar.getInstance().get(Calendar.MONTH);
                    añoSeleccionado = Calendar.getInstance().get(Calendar.YEAR);
                    añoActual[0] = añoActualMes;

                    CrearRelativeLayout(calendarioSintomasSelec);
                } else if (mostrar3Meses) {
                    int mesActual = Calendar.getInstance().get(Calendar.MONTH);
                    int mesInicial = (mesActual - 2 + 12) % 12; // Restar 2 meses
                    int mesFinal = mesActual;
                    añoActual[0] = añoActual3Meses;
                    String mesInicialAbreviado = mesesAbreviados[mesInicial];
                    String mesFinalAbreviado = mesesAbreviados[mesFinal];
                    updateFechaHoy(añoActual[0], mesInicialAbreviado + " - " + mesFinalAbreviado);
                    CrearRelativeLayout(calendarioSintomasSelec);
                } else  if (mostrarSemana) {

                    if (fechaInicioSemana.equals("1") && fechaFinSemana.equals("1")){
                        Calendar calendar = Calendar.getInstance();
                        int primerDiaSemana = calendar.get(Calendar.DAY_OF_MONTH);
                        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
                            calendar.add(Calendar.DAY_OF_MONTH, -1);
                            primerDiaSemana = calendar.get(Calendar.DAY_OF_MONTH);
                        }
                        calendar.add(Calendar.DAY_OF_MONTH, 6);
                        int ultimoDiaSemana = calendar.get(Calendar.DAY_OF_MONTH);
                        int mesActual = calendar.get(Calendar.MONTH);
                        Resources resources = getResources();
                        String[] mesesCompletos = resources.getStringArray(R.array.months_array);
                        String nombreMesActual = mesesCompletos[mesActual];
                        fechaInicioSemana = primerDiaSemana + " de " + nombreMesActual;
                        fechaFinSemana = ultimoDiaSemana + " de " + nombreMesActual;
                        updateFechaHoy(0, fechaInicioSem[0] + " - " + fechaFinSem[0]);
                        CrearRelativeLayout(calendarioSintomasSelec);
                    }
                }
                else {
                    añoActual[0] = añoActualAnio;
                    updateFechaHoy(añoActual[0], "");
                    CrearRelativeLayout(calendarioSintomasSelec);
                }
            }
        };

        // Asignar el OnClickListener a todos los botones
        btnSemana.setOnClickListener(buttonClickListener);
        btnMes.setOnClickListener(buttonClickListener);
        btn3Mes.setOnClickListener(buttonClickListener);
        btnAnio.setOnClickListener(buttonClickListener);

        deselectAllButtons();
        selectButton(btnMes);
        mesActual = Calendar.getInstance().get(Calendar.MONTH);
        updateFechaHoy(añoActual[0],mesesAbreviados[mesActual]);
    }

    // Función para seleccionar un botón y cambiar su estado
    private void selectButton(Button button) {
        button.setBackgroundResource(R.drawable.background_rounded_celeste);
        button.setTextColor(Color.WHITE);
    }

    // Función para deseleccionar todos los botones
    private void deselectAllButtons() {
        Button[] buttons = {btnSemana, btnMes, btn3Mes, btnAnio};
        for (Button button : buttons) {
            button.setBackgroundResource(R.drawable.background_blanco_recto);
            button.setTextColor(Color.BLACK);
        }
    }

    // Función para actualizar la vista de fecha con el mes abreviado y el año actual
    private void updateFechaHoy(int añoActual, String mesAbreviado) {
        // Obtener la vista de fecha
        fechaHoy = findViewById(R.id.fecha_hoy);

        // Asegúrate de que fechaHoy se haya inicializado correctamente
        if (fechaHoy != null) {
            if (añoActual!=0) {
                // Crear el texto con el mes abreviado y el año actual
                String fecha = mesAbreviado + " " + añoActual;

                // Actualizar el texto de la vista de fecha
                fechaHoy.setText(fecha);
                Log.e("MiApp", "fecha" + fecha);
            } else {
                Log.e("MiApp", "entra en igual a 0");
                // Construye la cadena de fecha sin incluir el año
                String fecha = mesAbreviado;
                // Actualizar el texto de la vista de fecha
                fechaHoy.setText(fecha);
            }
        } else {
            Log.e("MiApp", "fechaHoy es null");
        }
    }

    private void obtenerCalendarioSeleccionado() {
        Call<Calendario> call = calendarioApi.getByCodCalendario(codCalendario);
        call.enqueue(new Callback<Calendario>() {
            @Override
            public void onResponse(Call<Calendario> call, Response<Calendario> response) {
                if (response.isSuccessful()) {
                    calendarioSeleccionado = response.body();
                    if (calendarioSeleccionado != null) {
                        Log.d("MiApp", "Calendario seleccionado encontrado en MasInfoMedicion: " + calendarioSeleccionado.getCodCalendario());
                        obtenerCalendarioMediciones();
                    } else {
                        Log.d("MiApp", "No se encontró el Calendario con codCalendario: " + codCalendario);
                    }
                } else {
                    Log.d("MiApp", "Error en la solicitud en MasInfoMedicion: " + response.message());
                }
            }
            @Override
            public void onFailure(Call<Calendario> call, Throwable t) {
                Log.e("MiApp", "Error en la solicitud3: " + t.getMessage());
            }
        });
    }

    private void obtenerCalendarioMediciones() {
        Call<List<CalendarioSintoma>> call = calendarioSintomaApi.getByCodCalendarioSintoma(codCalendario);
        call.enqueue(new Callback<List<CalendarioSintoma>>() {
            @Override
            public void onResponse(Call<List<CalendarioSintoma>> call, Response<List<CalendarioSintoma>> response) {
                if (response.isSuccessful()) {
                    List<CalendarioSintoma> calendarioSintomas = response.body();
                    Log.d("MiApp", "Tamaño de la lista calendarioSintomas en MasInfoSintoma: " + calendarioSintomas.size());

                    obtenerCalendarioMedicionConMedicion();

                } else {
                    Log.d("MiApp", "Error en la solicitud 1: 1 " + response.message());
                }
            }
            @Override
            public void onFailure(Call<List<CalendarioSintoma>> call, Throwable t) {
                Log.e("MiApp", "Error en la solicitud 2: 2 " + t.getMessage());
            }
        });
    }
    private void obtenerCalendarioMedicionConMedicion() {
        Call<List<CalendarioSintoma>> call5 = calendarioSintomaApi.getByCodSintoma(codSintoma);
        Log.d("MiApp", "Codigo medicion MasInfoSintoma: " + codSintoma);
        call5.enqueue(new Callback<List<CalendarioSintoma>>() {
            @Override
            public void onResponse(Call<List<CalendarioSintoma>> call5, Response<List<CalendarioSintoma>> response) {
                if (response.isSuccessful()) {
                    List<CalendarioSintoma> calendarioSintomas = response.body();
                    Log.d("MiApp", "Tamaño de la lista calendarioSintomas en MasInfoSintoma: " + calendarioSintomas.size());
                    ObtenerFechaYValor(calendarioSintomas);
                    calendarioSintomasSelec = calendarioSintomas;
                    CrearRelativeLayout(calendarioSintomasSelec);
                } else {
                    int statusCode = response.code();
                    Log.d("MiApp", "Código de estado HTTP: " + statusCode);

                    Log.d("MiApp", "Error en la solicitud este: " + response.message());
                }
            }
            @Override
            public void onFailure(Call<List<CalendarioSintoma>> call5, Throwable t) {
                Log.e("MiApp", "Error en la solicitud 52: " + t.getMessage());
            }
        });
    }

    private void crearGrafico(List<CalendarioSintoma> sintomas) {
        LineChart lineChart = findViewById(R.id.lineChart);
        lineChart.clear();
        lineChart.getDescription().setEnabled(false);
        if (sintomas.isEmpty()) {
            // Si la lista de síntomas está vacía, muestra un mensaje en el gráfico
            lineChart.setNoDataText("No hay registros para la fecha");
            lineChart.setNoDataTextColor(ContextCompat.getColor(this, R.color.black));
            lineChart.invalidate(); // Actualiza el gráfico
            return; // Sale de la función
        } else {
            ArrayList<Entry> entries = new ArrayList<>();

            Collections.sort(sintomas, new Comparator<CalendarioSintoma>() {
                @Override
                public int compare(CalendarioSintoma sintoma1, CalendarioSintoma sintoma2) {
                    return sintoma1.getFechaCalendarioSintoma().compareTo(sintoma2.getFechaCalendarioSintoma());
                }
            });

            LocalDateTime primeraFecha = sintomas.get(0).getFechaCalendarioSintoma();
            LocalDateTime fechaActual = primeraFecha;
            int cantidadSintomas = 0;
            int indice = 0;

            for (int i = 0; i < sintomas.size(); i++) {
                CalendarioSintoma sintoma = sintomas.get(i);
                LocalDateTime fechaSintoma = sintoma.getFechaCalendarioSintoma();

                if (fechaActual.toLocalDate().isEqual(fechaSintoma.toLocalDate())) {
                    cantidadSintomas++;
                } else {
                    entries.add(new Entry(indice, cantidadSintomas));
                    fechaActual = fechaSintoma;
                    cantidadSintomas = 1;
                    indice++; // Incrementar el índice para la nueva fecha
                }

                // Si es el último síntoma o estamos en el último ciclo, agregar el punto para ese día
                if (i == sintomas.size() - 1) {
                    entries.add(new Entry(indice, cantidadSintomas));
                }
            }

            LineDataSet dataSet = new LineDataSet(entries, "Cantidad de registros por día");
            dataSet.setColor(ContextCompat.getColor(this, R.color.verdeTextos));
            dataSet.setValueTextSize(12f);
            dataSet.setLineWidth(3f);

            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(dataSet);

            LineData lineData = new LineData(dataSets);

            lineChart.setData(lineData);

            XAxis xAxis = lineChart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setGranularity(1f); // Muestra todos los valores en el eje X

            xAxis.setValueFormatter(new IndexAxisValueFormatter() {
                @Override
                public String getFormattedValue(float value) {
                    LocalDateTime fecha = primeraFecha.plusDays((int) value);
                    int dia = fecha.getDayOfMonth();
                    int mes = fecha.getMonthValue();
                    return dia + "/" + mes;
                }
            });

            YAxis yAxisLeft = lineChart.getAxisLeft();
            yAxisLeft.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
            yAxisLeft.setAxisMinimum(0f);
            yAxisLeft.setGranularity(1f);

            lineChart.getAxisRight().setEnabled(false);

            lineChart.invalidate();
        }
    }



    private void ObtenerFechaYValor(List<CalendarioSintoma> calendarioSintomas) {
        Log.d("MiApp", "entra: " + calendarioSintomas.size());
        // Recorrer la lista de CalendarioMedicion
        for (CalendarioSintoma calendarioSintoma : calendarioSintomas) {
            TextView textViewNombreSeguimiento = findViewById(R.id.texto_seguimiento);
            textViewNombreSeguimiento.setText(calendarioSintoma.getSintoma().getNombreSintoma());
            TextView textView2NombreSeguimiento = findViewById(R.id.texto_titulo_grafico);
            textView2NombreSeguimiento.setText("Síntoma");
        }
    }

    private void CrearRelativeLayout(List<CalendarioSintoma> calendarioSintomas) {
        Log.d("MiApp", "entra en crear Relative: " + calendarioSintomas.size());
        String fechaHoyTexto = fechaHoy.getText().toString();
        Log.d("MiApp", "efechaHoy: " +fechaHoyTexto);

        boolean seCreoRelativeLayout = false;
        LinearLayout layoutPrincipal = findViewById(R.id.contenido2);
        layoutPrincipal.removeAllViews();
        // Mapeo personalizado de nombres de meses abreviados a Month
        Map<String, Month> mesAbreviadoAMonth = new HashMap<>();
        mesAbreviadoAMonth.put("ENE", Month.JANUARY);
        mesAbreviadoAMonth.put("FEB", Month.FEBRUARY);
        mesAbreviadoAMonth.put("MAR", Month.MARCH);
        mesAbreviadoAMonth.put("ABR", Month.APRIL);
        mesAbreviadoAMonth.put("MAY", Month.MAY);
        mesAbreviadoAMonth.put("JUN", Month.JUNE);
        mesAbreviadoAMonth.put("JUL", Month.JULY);
        mesAbreviadoAMonth.put("AGO", Month.AUGUST);
        mesAbreviadoAMonth.put("SEP", Month.SEPTEMBER);
        mesAbreviadoAMonth.put("OCT", Month.OCTOBER);
        mesAbreviadoAMonth.put("NOV", Month.NOVEMBER);
        mesAbreviadoAMonth.put("DIC", Month.DECEMBER);
        if (mostrar3Meses) {
            // btn3Meses
            // Mapa para almacenar los TextView y sus IDs generados
            Map<TextView, Integer> textViewIds = new HashMap<>();
            if (fechaHoyTexto.contains(" - ")) {
                String[] partesFechaFormato = fechaHoyTexto.split(" - ");
                if (partesFechaFormato.length == 2) {
                    String nombreMesAbreviado = partesFechaFormato[0].trim().toUpperCase(); // Convierte a mayúsculas y elimina espacios en blanco
                    String[] partesMeses = partesFechaFormato[1].trim().split(" ");
                    if (partesMeses.length == 2) {
                        String año = partesMeses[1].trim(); // Obtiene el año
                        int numeroMes = obtenerNumeroMesDesdeAbreviatura(nombreMesAbreviado);
                        List<CalendarioSintoma> calendarioSintomasFiltrados = new ArrayList<>();
                        for (int i = 0; i < 3; i++) {
                            String nombreMesCompleto = obtenerNombreMesCompleto(numeroMes);
                            String textoMes = nombreMesCompleto + " de " + año;

                            for (CalendarioSintoma calendarioSintomaActual : calendarioSintomas) {
                                LocalDateTime fechaSintoma = calendarioSintomaActual.getFechaCalendarioSintoma();
                                int mesSintoma = fechaSintoma.getMonthValue();
                                int añoSintoma = fechaSintoma.getYear();
                                if (mesSintoma == numeroMes && añoSintoma == Integer.parseInt(año)) {
                                    calendarioSintomasFiltrados.add(calendarioSintomaActual);
                                }
                            }


                            RelativeLayout relativeLayoutGrafico = new RelativeLayout(this);
                            int relativeLayoutId = View.generateViewId(); // Generar un ID único para el RelativeLayout
                            relativeLayoutGrafico.setId(relativeLayoutId);
                            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                                    RelativeLayout.LayoutParams.MATCH_PARENT,
                                    RelativeLayout.LayoutParams.WRAP_CONTENT
                            );
                            int marginInDp = getResources().getDimensionPixelSize(R.dimen.layout_margin_horizontal);
                            layoutParams.setMargins(marginInDp, 0, marginInDp, marginInDp); // Agregar margen en la parte inferior
                            relativeLayoutGrafico.setLayoutParams(layoutParams);
                            relativeLayoutGrafico.setBackgroundResource(R.drawable.background_rounded_blanco);
                            TextView textViewFechaHora = new TextView(this);
                            int textViewId = View.generateViewId(); // Generar un ID único
                            textViewFechaHora.setId(textViewId); // Asignar el ID al TextView
                            RelativeLayout.LayoutParams paramsFechaHora = new RelativeLayout.LayoutParams(
                                    RelativeLayout.LayoutParams.MATCH_PARENT,
                                    RelativeLayout.LayoutParams.WRAP_CONTENT
                            );
                            paramsFechaHora.addRule(RelativeLayout.BELOW, R.id.texto_titulo_medicion1); // Alinea el TextView debajo de otro elemento, en este caso, texto_titulo_medicion1
                            paramsFechaHora.setMargins(
                                    getResources().getDimensionPixelSize(R.dimen.editar_margin_start),
                                    getResources().getDimensionPixelSize(R.dimen.editar_margin_top),
                                    getResources().getDimensionPixelSize(R.dimen.editar_margin_end),
                                    getResources().getDimensionPixelSize(R.dimen.editar_margin_bottom)
                            );
                            textViewFechaHora.setText(textoMes);
                            textViewFechaHora.setLayoutParams(paramsFechaHora);
                            textViewFechaHora.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
                            textViewFechaHora.setTypeface(null, Typeface.BOLD);
                            relativeLayoutGrafico.addView(textViewFechaHora);

                            int tiene = 0;
                            for (CalendarioSintoma calendarioSintomaActual : calendarioSintomas) {
                                LocalDateTime fechaSintoma = calendarioSintomaActual.getFechaCalendarioSintoma();
                                int mesSintoma = fechaSintoma.getMonthValue();
                                int añoSintoma = fechaSintoma.getYear();
                                if (mesSintoma == numeroMes) {
                                    if (añoSintoma == Integer.parseInt(año)) {
                                        tiene =1;
                                    }
                                }
                            }
                            TextView textViewValores = new TextView(this);
                            RelativeLayout.LayoutParams paramsValores = new RelativeLayout.LayoutParams(
                                    RelativeLayout.LayoutParams.MATCH_PARENT,
                                    RelativeLayout.LayoutParams.WRAP_CONTENT
                            );
                            paramsValores.addRule(RelativeLayout.BELOW, textViewFechaHora.getId()); // Alinea debajo del TextView de fecha y hora
                            paramsValores.setMargins(
                                    getResources().getDimensionPixelSize(R.dimen.valor_margin_start),
                                    getResources().getDimensionPixelSize(R.dimen.valor_margin_top2),
                                    getResources().getDimensionPixelSize(R.dimen.valor_margin_end),
                                    getResources().getDimensionPixelSize(R.dimen.valor_margin_bottom)
                            );

                            String sitiene = "Registro del síntoma";
                            if (tiene == 0) {
                                textViewValores.setText("No hay datos");
                            } else {
                                textViewValores.setText(sitiene);
                            }
                            textViewValores.setLayoutParams(paramsValores);
                            textViewValores.setTextColor(ContextCompat.getColor(this, R.color.black));
                            textViewValores.setTextSize(TypedValue.COMPLEX_UNIT_SP, 21);
                            relativeLayoutGrafico.addView(textViewValores);

                            layoutPrincipal.addView(relativeLayoutGrafico);
                            seCreoRelativeLayout = true;

                            // Guardar el TextView y su ID en el mapa
                            textViewIds.put(textViewFechaHora, textViewId);
                            final TextView fechaHoyTexto2 = findViewById(R.id.fecha_hoy);
                            if (fechaHoyTexto2 != null) {
                                Log.d("Miapp", "Valor actual de fechaHoyTexto2: " + fechaHoyTexto2.getText().toString());
                            } else {
                                Log.e("Miapp", "fechaHoyTexto2 es null");
                            }

                            relativeLayoutGrafico.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    // Obtener el texto del TextView en el RelativeLayout seleccionado
                                    TextView textViewFecha = v.findViewById(textViewFechaHora.getId());

                                    if (textViewFecha != null) {
                                        String fechaSeleccionada = textViewFecha.getText().toString();

                                        // Analizar el texto para obtener el mes y el año
                                        String mesSeleccionado = obtenerMesDesdeTexto(fechaSeleccionada);
                                        String añoSeleccionado = obtenerAñoDesdeTexto(fechaSeleccionada);

                                        if (!mesSeleccionado.isEmpty() && !añoSeleccionado.isEmpty()) {
                                            // Actualizar mesActualMes con el mes seleccionado
                                            mesActualMes = obtenerNumeroDeMes(mesSeleccionado);
                                            añoActualMes = Integer.parseInt(añoSeleccionado);;
                                        }
                                    }

                                    // Simular un clic en el botón btnMes
                                    btnMes.performClick();
                                }
                            });


                            // Avanza al siguiente mes
                            numeroMes++;
                            if (numeroMes > 12) {
                                numeroMes = 1; // Si el siguiente mes es mayor que 12, reinicia en enero y aumenta el año
                                año = Integer.toString(Integer.parseInt(año) + 1);
                            }
                        }
                        crearGrafico(calendarioSintomasFiltrados);

                    } else {
                        Log.e("MiApp", "Formato de fecha incorrecto: " + fechaHoyTexto);
                    }
                } else {
                    Log.e("MiApp", "Formato de fecha incorrecto: " + fechaHoyTexto);
                }
            } else {
                Log.e("MiApp", "Formato de fecha incorrecto: " + fechaHoyTexto);
            }
        }
        else if (mostrarMes == true) {
            //btnMes
            Map<TextView, Integer> textViewIds = new HashMap<>();
            String[] partesFecha = fechaHoyTexto.split(" ");
            String nombreMesAbreviado = partesFecha[0].toUpperCase(); // Convertir a mayúsculas
            int año = Integer.parseInt(partesFecha[1]);
            Month mes = mesAbreviadoAMonth.get(nombreMesAbreviado);

            LocalDate primerDiaDelMes = LocalDate.of(año, mes, 1);
            LocalDate ultimoDiaDelMes = primerDiaDelMes.with(TemporalAdjusters.lastDayOfMonth());
            LocalDate inicioSemana = primerDiaDelMes.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
            LocalDate finSemana = inicioSemana.plusDays(6); // Calcula el fin de semana

            List<CalendarioSintoma> calendarioSintomasFiltradosPorMes = new ArrayList<>();
            while (inicioSemana.isBefore(ultimoDiaDelMes) || inicioSemana.isEqual(ultimoDiaDelMes)) {
                Log.d("MiApp", "entra en el while ");
                List<CalendarioSintoma> calendarioSintomasSemana = new ArrayList<>();

                for (CalendarioSintoma calendarioSintoma : calendarioSintomas) {
                    Log.d("MiApp", "entra en el for calendarioMedicion ");
                    LocalDateTime fechaCalendarioSintoma = calendarioSintoma.getFechaCalendarioSintoma();

                    if (!fechaCalendarioSintoma.toLocalDate().isBefore(inicioSemana) &&
                            !fechaCalendarioSintoma.toLocalDate().isAfter(finSemana)) {
                        Log.d("MiApp", "entra en el if");
                        calendarioSintomasSemana.add(calendarioSintoma);
                    }
                }

                for (CalendarioSintoma calendarioSintoma : calendarioSintomasSemana) {
                    LocalDateTime fechaCalendarioSintoma = calendarioSintoma.getFechaCalendarioSintoma();
                    if (fechaCalendarioSintoma.getMonth() == mes) {
                        calendarioSintomasFiltradosPorMes.add(calendarioSintoma);
                    }
                }
                // Si hay al menos una CalendarioMedicion en esta semana, crea un RelativeLayout
                if (!calendarioSintomasSemana.isEmpty()) {
                    Log.d("MiApp", "entra en el isEmpty");
                    String fechaFormateada = inicioSemana.format(DateTimeFormatter.ofPattern("d 'de' MMM", Locale.getDefault())) +
                            " - " +
                            finSemana.format(DateTimeFormatter.ofPattern("d 'de' MMM", Locale.getDefault()));
                    RelativeLayout relativeLayoutGrafico = new RelativeLayout(this);
                    int relativeLayoutId = View.generateViewId(); // Generar un ID único para el RelativeLayout
                    relativeLayoutGrafico.setId(relativeLayoutId);
                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                            RelativeLayout.LayoutParams.MATCH_PARENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT
                    );
                    int marginInDp = getResources().getDimensionPixelSize(R.dimen.layout_margin_horizontal);
                    layoutParams.setMargins(marginInDp, 0, marginInDp, marginInDp); // Agregar margen en la parte inferior
                    relativeLayoutGrafico.setLayoutParams(layoutParams);
                    relativeLayoutGrafico.setBackgroundResource(R.drawable.background_rounded_blanco);
                    TextView textViewFechaHora = new TextView(this);
                    int textViewId = View.generateViewId(); // Generar un ID único
                    textViewFechaHora.setId(textViewId);
                    RelativeLayout.LayoutParams paramsFechaHora = new RelativeLayout.LayoutParams(
                            RelativeLayout.LayoutParams.MATCH_PARENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT
                    );
                    paramsFechaHora.addRule(RelativeLayout.BELOW, R.id.texto_titulo_medicion1); // Alinea el TextView debajo de otro elemento, en este caso, texto_titulo_medicion1
                    paramsFechaHora.setMargins(
                            getResources().getDimensionPixelSize(R.dimen.editar_margin_start),
                            getResources().getDimensionPixelSize(R.dimen.editar_margin_top),
                            getResources().getDimensionPixelSize(R.dimen.editar_margin_end),
                            getResources().getDimensionPixelSize(R.dimen.editar_margin_bottom)
                    );
                    textViewFechaHora.setText(fechaFormateada);
                    textViewFechaHora.setLayoutParams(paramsFechaHora);
                    textViewFechaHora.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
                    textViewFechaHora.setTypeface(null, Typeface.BOLD);
                    relativeLayoutGrafico.addView(textViewFechaHora);
                    // Crear un TextView para mostrar los valores
                    TextView textViewValores = new TextView(this);
                    RelativeLayout.LayoutParams paramsValores = new RelativeLayout.LayoutParams(
                            RelativeLayout.LayoutParams.MATCH_PARENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT
                    );
                    paramsValores.addRule(RelativeLayout.BELOW, textViewFechaHora.getId()); // Alinea debajo del TextView de fecha y hora
                    paramsValores.setMargins(
                            getResources().getDimensionPixelSize(R.dimen.valor_margin_start),
                            getResources().getDimensionPixelSize(R.dimen.valor_margin_top2),
                            getResources().getDimensionPixelSize(R.dimen.valor_margin_end),
                            getResources().getDimensionPixelSize(R.dimen.valor_margin_bottom)
                    );
                    textViewValores.setLayoutParams(paramsValores);
                    textViewValores.setTextColor(ContextCompat.getColor(this, R.color.black));
                    textViewValores.setTextSize(TypedValue.COMPLEX_UNIT_SP, 21);

                    int tiene =0;
                     for (CalendarioSintoma calendarioSintoma : calendarioSintomasSemana) {
                        tiene =1;
                    }

                    String sitiene = "Registro del síntoma";

                    if (calendarioSintomasSemana.isEmpty()) {
                        textViewValores.setText("No hay datos");
                    } else {
                        textViewValores.setText(sitiene);
                    }
                    relativeLayoutGrafico.addView(textViewValores);
                    layoutPrincipal.addView(relativeLayoutGrafico);
                    seCreoRelativeLayout = true;

                    textViewIds.put(textViewFechaHora, textViewId);
                    final TextView fechaHoyTexto2 = findViewById(R.id.fecha_hoy);
                    relativeLayoutGrafico.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            TextView textViewFecha = v.findViewById(textViewFechaHora.getId());
                            if (textViewFecha != null) {
                                String fechaSeleccionada = textViewFecha.getText().toString();

                                // Elimina los puntos y espacios de la cadena
                                fechaSeleccionada = fechaSeleccionada.replace(".", "").replace(" ", "");

                                Log.d("Miapp", "fechaSeleccionado: " + fechaSeleccionada);

                                // Divide la cadena en dos partes usando "-" como separador
                                String[] partes = fechaSeleccionada.split("-");

                                if (partes.length == 2) {
                                    // Obtiene la primera parte como primerDiaSemana y nombreMesActualIn
                                    String primeraParte = partes[0].trim();
                                    String[] datosPrimeraParte = primeraParte.split("de");
                                    if (datosPrimeraParte.length == 2) {
                                        // Obtiene el primer día de la semana (primerDiaSemana) como número
                                        int primerDiaSemana = Integer.parseInt(datosPrimeraParte[0].trim());
                                        // Obtiene el nombre del mes correspondiente a la primera parte (nombreMesActualIn)
                                        String nombreMesActualIn = datosPrimeraParte[1].trim().substring(0, 1).toUpperCase() + datosPrimeraParte[1].trim().substring(1);

                                        // Obtiene la segunda parte como últimoDiaSemana y nombreMesActual
                                        String segundaParte = partes[1].trim();
                                        String[] datosSegundaParte = segundaParte.split("de");
                                        if (datosSegundaParte.length == 2) {
                                            // Obtiene el último día de la semana (ultimoDiaSemana) como número
                                            int ultimoDiaSemana = Integer.parseInt(datosSegundaParte[0].trim());
                                            // Obtiene el nombre del mes correspondiente a la segunda parte (nombreMesActual)
                                            String nombreMesActual = datosSegundaParte[1].trim();
                                            if (!nombreMesActual.isEmpty()) {
                                                // Obtener la primera letra
                                                String primeraLetra = nombreMesActual.substring(0, 1);
                                                // Convertir la primera letra a mayúscula
                                                primeraLetra = primeraLetra.toUpperCase();
                                                // Obtener el resto de la cadena (sin la primera letra)
                                                String restoDeLaCadena = nombreMesActual.substring(1);
                                                // Concatenar la primera letra mayúscula con el resto de la cadena
                                                nombreMesActual = primeraLetra + restoDeLaCadena;
                                            }
                                            String fechaInicioStr = convertirFechaAlNuevoFormato(partes[0]);
                                            Log.d("Miapp", "fechaInicioStr: " + fechaInicioStr);
                                            // Ahora, parsea las cadenas de fecha en objetos Calendar
                                            Calendar fechaInicioParaPasar = parsearFechaComoCalendar(fechaInicioStr);
                                            fechaActualSemanaa = fechaInicioParaPasar;
                                            if (nombreMesActualIn.equals(nombreMesActual)) {
                                                Log.d("Miapp","entra en la comparacion");
                                                if (!nombreMesActual.isEmpty()) {
                                                    if (nombreMesActual.equals("Ene")) {
                                                        nombreMesActual = "Enero";
                                                    } else if (nombreMesActual.equals("Feb")) {
                                                        nombreMesActual = "Febrero";
                                                    } else if (nombreMesActual.equals("Mar")) {
                                                        nombreMesActual = "Marzo";
                                                    } else if (nombreMesActual.equals("Abr")) {
                                                        nombreMesActual = "Abril";
                                                    } else if (nombreMesActual.equals("May")) {
                                                        nombreMesActual = "Mayo";
                                                    } else if (nombreMesActual.equals("Jun")) {
                                                        nombreMesActual = "Junio";
                                                    } else if (nombreMesActual.equals("Jul")) {
                                                        nombreMesActual = "Julio";
                                                    } else if (nombreMesActual.equals("Ago")) {
                                                        nombreMesActual = "Agosto";
                                                    } else if (nombreMesActual.equals("Sep")) {
                                                        nombreMesActual = "Septiembre";
                                                    } else if (nombreMesActual.equals("Oct")) {
                                                        nombreMesActual = "Octubre";
                                                    } else if (nombreMesActual.equals("Nov")) {
                                                        nombreMesActual = "Noviembre";
                                                    } else if (nombreMesActual.equals("Dic")) {
                                                        nombreMesActual = "Diciembre";
                                                    }
                                                }
                                                nombreMesActualIn = ""; // Establece en vacío si son iguales
                                                // Actualizar la fecha en la interfaz
                                                fechaInicioSemana = primerDiaSemana + "";
                                                fechaFinSemana = ultimoDiaSemana + " de " + nombreMesActual;
                                            } else {
                                                nombreMesActual = nombreMesActual.substring(0, 1).toUpperCase() + nombreMesActual.substring(1); // Convierte la primera letra a mayúscula
                                                nombreMesActualIn = datosPrimeraParte[1].trim().substring(0, 1).toUpperCase() + datosPrimeraParte[1].trim().substring(1);
                                                // Actualizar la fecha en la interfaz
                                                fechaInicioSemana = primerDiaSemana + " de " + nombreMesActualIn;
                                                fechaFinSemana = ultimoDiaSemana + " de " + nombreMesActual;
                                            }
                                        }
                                    }
                                } else {
                                    // Manejo de error si el formato no es válido
                                    Log.e("Miapp", "Formato de fecha no válido: " + fechaSeleccionada);
                                }
                            }
                            btnSemana.performClick();
                        }
                    });
                } else {
                    Log.d("MiApp", "entra en el isEmpty");
                    String fechaFormateada = inicioSemana.format(DateTimeFormatter.ofPattern("d 'de' MMM", Locale.getDefault())) +
                            " - " +
                            finSemana.format(DateTimeFormatter.ofPattern("d 'de' MMM", Locale.getDefault()));
                    RelativeLayout relativeLayoutGrafico = new RelativeLayout(this);
                    int relativeLayoutId = View.generateViewId(); // Generar un ID único para el RelativeLayout
                    relativeLayoutGrafico.setId(relativeLayoutId);
                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                            RelativeLayout.LayoutParams.MATCH_PARENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT
                    );
                    int marginInDp = getResources().getDimensionPixelSize(R.dimen.layout_margin_horizontal);
                    layoutParams.setMargins(marginInDp, 0, marginInDp, marginInDp); // Agregar margen en la parte inferior
                    relativeLayoutGrafico.setLayoutParams(layoutParams);
                    relativeLayoutGrafico.setBackgroundResource(R.drawable.background_rounded_blanco);
                    TextView textViewFechaHora = new TextView(this);
                    int textViewId = View.generateViewId(); // Generar un ID único
                    textViewFechaHora.setId(textViewId);
                    RelativeLayout.LayoutParams paramsFechaHora = new RelativeLayout.LayoutParams(
                            RelativeLayout.LayoutParams.MATCH_PARENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT
                    );
                    paramsFechaHora.addRule(RelativeLayout.BELOW, R.id.texto_titulo_medicion1); // Alinea el TextView debajo de otro elemento, en este caso, texto_titulo_medicion1
                    paramsFechaHora.setMargins(
                            getResources().getDimensionPixelSize(R.dimen.editar_margin_start),
                            getResources().getDimensionPixelSize(R.dimen.editar_margin_top),
                            getResources().getDimensionPixelSize(R.dimen.editar_margin_end),
                            getResources().getDimensionPixelSize(R.dimen.editar_margin_bottom)
                    );
                    textViewFechaHora.setText(fechaFormateada);
                    textViewFechaHora.setLayoutParams(paramsFechaHora);
                    textViewFechaHora.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
                    textViewFechaHora.setTypeface(null, Typeface.BOLD);
                    relativeLayoutGrafico.addView(textViewFechaHora);
                    layoutPrincipal.addView(relativeLayoutGrafico);
                    seCreoRelativeLayout = true;
                    // Crear un TextView para mostrar los valores
                    TextView textViewValores = new TextView(this);
                    RelativeLayout.LayoutParams paramsValores = new RelativeLayout.LayoutParams(
                            RelativeLayout.LayoutParams.MATCH_PARENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT
                    );
                    paramsValores.addRule(RelativeLayout.BELOW, textViewFechaHora.getId()); // Alinea debajo del TextView de fecha y hora
                    paramsValores.setMargins(
                            getResources().getDimensionPixelSize(R.dimen.valor_margin_start),
                            getResources().getDimensionPixelSize(R.dimen.valor_margin_top2),
                            getResources().getDimensionPixelSize(R.dimen.valor_margin_end),
                            getResources().getDimensionPixelSize(R.dimen.valor_margin_bottom)
                    );
                    textViewValores.setLayoutParams(paramsValores);
                    textViewValores.setTextColor(ContextCompat.getColor(this, R.color.black));
                    textViewValores.setTextSize(TypedValue.COMPLEX_UNIT_SP, 21);
                    textViewValores.setText("No hay datos");
                    relativeLayoutGrafico.addView(textViewValores);

                    textViewIds.put(textViewFechaHora, textViewId);
                    final TextView fechaHoyTexto2 = findViewById(R.id.fecha_hoy);
                    relativeLayoutGrafico.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            TextView textViewFecha = v.findViewById(textViewFechaHora.getId());
                            if (textViewFecha != null) {
                                String fechaSeleccionada = textViewFecha.getText().toString();

                                // Elimina los puntos y espacios de la cadena
                                fechaSeleccionada = fechaSeleccionada.replace(".", "").replace(" ", "");

                                Log.d("Miapp", "fechaSeleccionado: " + fechaSeleccionada);

                                // Divide la cadena en dos partes usando "-" como separador
                                String[] partes = fechaSeleccionada.split("-");

                                if (partes.length == 2) {
                                    // Obtiene la primera parte como primerDiaSemana y nombreMesActualIn
                                    String primeraParte = partes[0].trim();
                                    String[] datosPrimeraParte = primeraParte.split("de");
                                    if (datosPrimeraParte.length == 2) {
                                        // Obtiene el primer día de la semana (primerDiaSemana) como número
                                        int primerDiaSemana = Integer.parseInt(datosPrimeraParte[0].trim());
                                        // Obtiene el nombre del mes correspondiente a la primera parte (nombreMesActualIn)
                                        String nombreMesActualIn = datosPrimeraParte[1].trim().substring(0, 1).toUpperCase() + datosPrimeraParte[1].trim().substring(1);

                                        // Obtiene la segunda parte como últimoDiaSemana y nombreMesActual
                                        String segundaParte = partes[1].trim();
                                        String[] datosSegundaParte = segundaParte.split("de");
                                        if (datosSegundaParte.length == 2) {
                                            // Obtiene el último día de la semana (ultimoDiaSemana) como número
                                            int ultimoDiaSemana = Integer.parseInt(datosSegundaParte[0].trim());
                                            // Obtiene el nombre del mes correspondiente a la segunda parte (nombreMesActual)
                                            String nombreMesActual = datosSegundaParte[1].trim();
                                            if (!nombreMesActual.isEmpty()) {
                                                // Obtener la primera letra
                                                String primeraLetra = nombreMesActual.substring(0, 1);
                                                // Convertir la primera letra a mayúscula
                                                primeraLetra = primeraLetra.toUpperCase();
                                                // Obtener el resto de la cadena (sin la primera letra)
                                                String restoDeLaCadena = nombreMesActual.substring(1);
                                                // Concatenar la primera letra mayúscula con el resto de la cadena
                                                nombreMesActual = primeraLetra + restoDeLaCadena;
                                            }
                                            String fechaInicioStr = convertirFechaAlNuevoFormato(partes[0]);
                                            // Ahora, parsea las cadenas de fecha en objetos Calendar
                                            Calendar fechaInicioParaPasar = parsearFechaComoCalendar(fechaInicioStr);
                                            fechaActualSemanaa = fechaInicioParaPasar;
                                            if (nombreMesActualIn.equals(nombreMesActual)) {
                                                if (!nombreMesActual.isEmpty()) {
                                                    if (nombreMesActual.equals("Ene")) {
                                                        nombreMesActual = "Enero";
                                                    } else if (nombreMesActual.equals("Feb")) {
                                                        nombreMesActual = "Febrero";
                                                    } else if (nombreMesActual.equals("Mar")) {
                                                        nombreMesActual = "Marzo";
                                                    } else if (nombreMesActual.equals("Abr")) {
                                                        nombreMesActual = "Abril";
                                                    } else if (nombreMesActual.equals("May")) {
                                                        nombreMesActual = "Mayo";
                                                    } else if (nombreMesActual.equals("Jun")) {
                                                        nombreMesActual = "Junio";
                                                    } else if (nombreMesActual.equals("Jul")) {
                                                        nombreMesActual = "Julio";
                                                    } else if (nombreMesActual.equals("Ago")) {
                                                        nombreMesActual = "Agosto";
                                                    } else if (nombreMesActual.equals("Sep")) {
                                                        nombreMesActual = "Septiembre";
                                                    } else if (nombreMesActual.equals("Oct")) {
                                                        nombreMesActual = "Octubre";
                                                    } else if (nombreMesActual.equals("Nov")) {
                                                        nombreMesActual = "Noviembre";
                                                    } else if (nombreMesActual.equals("Dic")) {
                                                        nombreMesActual = "Diciembre";
                                                    }
                                                }

                                                nombreMesActualIn = ""; // Establece en vacío si son iguales
                                                // Actualizar la fecha en la interfaz
                                                fechaInicioSemana = primerDiaSemana + "";
                                                fechaFinSemana = ultimoDiaSemana + " de " + nombreMesActual;
                                            } else {
                                                Log.d("Miapp", "entra en la comparacion else");
                                                nombreMesActual = nombreMesActual.substring(0, 1).toUpperCase() + nombreMesActual.substring(1); // Convierte la primera letra a mayúscula
                                                nombreMesActualIn = datosPrimeraParte[1].trim().substring(0, 1).toUpperCase() + datosPrimeraParte[1].trim().substring(1);
                                                // Actualizar la fecha en la interfaz
                                                fechaInicioSemana = primerDiaSemana + " de " + nombreMesActualIn;
                                                fechaFinSemana = ultimoDiaSemana + " de " + nombreMesActual;
                                            }
                                        }
                                    }
                                } else {
                                    // Manejo de error si el formato no es válido
                                    Log.e("Miapp", "Formato de fecha no válido: " + fechaSeleccionada);
                                }
                            }
                            btnSemana.performClick();
                        }
                    });
                }
                inicioSemana = finSemana.plusDays(1);
                finSemana = inicioSemana.plusDays(6);
            }
            crearGrafico(calendarioSintomasFiltradosPorMes);
        }
        else if (mostrarSemana == false) {
            //btn Año
            // Mapa para almacenar los TextView y sus IDs generados
            Map<TextView, Integer> textViewIds = new HashMap<>();
            fechaHoyTexto = fechaHoyTexto.trim();
            int año = Integer.parseInt(fechaHoyTexto);
            List<CalendarioSintoma> calendarioSintomasFiltradosPorAño = new ArrayList<>();

            for (CalendarioSintoma calendarioSintomaActual : calendarioSintomas) {
                LocalDateTime fechaSintoma = calendarioSintomaActual.getFechaCalendarioSintoma();
                int añoSintoma = fechaSintoma.getYear();

                // Verifica si el año del síntoma coincide con el año actual
                if (añoSintoma == año) {
                    calendarioSintomasFiltradosPorAño.add(calendarioSintomaActual);
                }
            }
            for (int numeroMes = 1; numeroMes <= 12; numeroMes++) {
                String nombreMesCompleto = obtenerNombreMesCompleto(numeroMes); // Función para obtener el nombre completo del mes
                String textoMes = nombreMesCompleto;
                RelativeLayout relativeLayoutGrafico = new RelativeLayout(this);
                int relativeLayoutId = View.generateViewId(); // Generar un ID único para el RelativeLayout
                relativeLayoutGrafico.setId(relativeLayoutId);
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.MATCH_PARENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT
                );
                int marginInDp = getResources().getDimensionPixelSize(R.dimen.layout_margin_horizontal);
                layoutParams.setMargins(marginInDp, 0, marginInDp, marginInDp); // Agregar margen en la parte inferior
                relativeLayoutGrafico.setLayoutParams(layoutParams);
                relativeLayoutGrafico.setBackgroundResource(R.drawable.background_rounded_blanco);
                TextView textViewFechaHora = new TextView(this);
                int textViewId = View.generateViewId(); // Generar un ID único
                textViewFechaHora.setId(textViewId);
                RelativeLayout.LayoutParams paramsFechaHora = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.MATCH_PARENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT
                );
                paramsFechaHora.setMargins(
                        getResources().getDimensionPixelSize(R.dimen.editar_margin_start),
                        getResources().getDimensionPixelSize(R.dimen.editar_margin_top),
                        getResources().getDimensionPixelSize(R.dimen.editar_margin_end),
                        getResources().getDimensionPixelSize(R.dimen.editar_margin_bottom)
                );
                textViewFechaHora.setText(textoMes);
                textViewFechaHora.setLayoutParams(paramsFechaHora);
                textViewFechaHora.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
                textViewFechaHora.setTypeface(null, Typeface.BOLD);
                relativeLayoutGrafico.addView(textViewFechaHora);


                int tiene = 0;
                int añoSintoma = 0;
                for (CalendarioSintoma calendarioSintomaActual : calendarioSintomas) {

                    LocalDateTime fechaSintoma = calendarioSintomaActual.getFechaCalendarioSintoma();
                    int año2 = Calendar.getInstance().get(Calendar.YEAR);
                    int mesSintoma = fechaSintoma.getMonthValue(); // Obtiene el número del mes
                    añoSintoma = fechaSintoma.getYear();
                    // Verifica si el mes de la medición coincide con el mes actual
                    if (mesSintoma == numeroMes) {
                        if (añoSintoma == Integer.parseInt(String.valueOf(año2))) {
                            tiene =1;
                        }
                    }
                }

                TextView textViewValores = new TextView(this);
                RelativeLayout.LayoutParams paramsValores = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.MATCH_PARENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT
                );
                paramsValores.addRule(RelativeLayout.BELOW, textViewFechaHora.getId()); // Alinea debajo del TextView de fecha y hora
                paramsValores.setMargins(
                        getResources().getDimensionPixelSize(R.dimen.valor_margin_start),
                        getResources().getDimensionPixelSize(R.dimen.valor_margin_top2),
                        getResources().getDimensionPixelSize(R.dimen.valor_margin_end),
                        getResources().getDimensionPixelSize(R.dimen.valor_margin_bottom)
                );

                String sitiene = "Registro del síntoma";
                if (tiene ==0) {
                    textViewValores.setText("No hay datos");
                } else {
                    if(año == añoSintoma) {
                        textViewValores.setText(sitiene);
                    } else {
                        textViewValores.setText("No hay datos");
                    }
                }
                textViewValores.setLayoutParams(paramsValores);
                textViewValores.setTextColor(ContextCompat.getColor(this, R.color.black));
                textViewValores.setTextSize(TypedValue.COMPLEX_UNIT_SP, 21);
                relativeLayoutGrafico.addView(textViewValores);

                layoutPrincipal.addView(relativeLayoutGrafico);
                seCreoRelativeLayout = true;
                textViewIds.put(textViewFechaHora, textViewId);
                final TextView fechaHoyTexto2 = findViewById(R.id.fecha_hoy);
                relativeLayoutGrafico.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TextView textViewFecha = v.findViewById(textViewFechaHora.getId());
                        if (textViewFecha != null) {
                            String fechaSeleccionada = textViewFecha.getText().toString();
                            String mesSeleccionado = obtenerMes(fechaSeleccionada);
                            String añoSeleccionado = fechaHoyTexto2.getText().toString();
                            if (!mesSeleccionado.isEmpty() && !añoSeleccionado.isEmpty()) {
                                añoSeleccionado = añoSeleccionado.trim();
                                mesActualMes = obtenerNumeroDeMess(mesSeleccionado);
                                try {
                                    añoActualMes = Integer.parseInt(añoSeleccionado);
                                } catch (NumberFormatException e) {
                                    Log.e("Miapp", "Error al convertir el año a un número: " + e.getMessage());
                                }
                            }

                        }
                        btnMes.performClick();
                    }
                });
            }
            crearGrafico(calendarioSintomasFiltradosPorAño);
        }

        List<CalendarioSintoma> calendarioSintomasFiltradosPorSemana = new ArrayList<>();
        // Recorrer la lista de CalendarioMedicion
        for (CalendarioSintoma calendarioSintoma : calendarioSintomas) {
            // btnSemana
            if (mostrarSemana == true) {

                Log.d("MiApp", "entra al mostrarSemana: " + calendarioSintomas.size());
                Log.d("MiApp", "fechaHoyTexto: " + fechaHoyTexto);
                if (fechaHoyTexto.contains(" - ")) {
                    // Procesar el primer formato 10 - 16 de Septiembre
                    String[] partesFechaHoy = fechaHoyTexto.split(" - ");
                    if (partesFechaHoy.length == 2) {
                        Log.d("MiApp", "entra al primer formato");
                        String fechaInicio = partesFechaHoy[0].trim();
                        String fechaFin = partesFechaHoy[1].trim();
                        int diaSintoma = calendarioSintoma.getFechaCalendarioSintoma().getDayOfMonth();
                        // Verificar si las fechas son no nulas ni vacías antes de convertirlas en enteros
                        if (!fechaInicio.isEmpty() && !fechaFin.isEmpty()) {
                            // Obtener los días de inicio y fin del rango
                            int diaInicio = Integer.parseInt(fechaInicio.split(" de ")[0]);
                            int diaFin = Integer.parseInt(fechaFin.split(" de ")[0]);

                            // Verificar si el día actual está dentro del rango
                            if (diaSintoma >= diaInicio && diaSintoma <= diaFin) {
                                calendarioSintomasFiltradosPorSemana.add(calendarioSintoma);
                                String mesInicio = fechaFin.split(" de ")[1];
                                Log.d("MiApp", "mesInicio: " + mesInicio);
                                String mesSintoma = calendarioSintoma.getFechaCalendarioSintoma().getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault());
                                LocalDateTime calendarioSifecha = calendarioSintoma.getFechaCalendarioSintoma();
                                // Verificar si el mes actual coincide con el mes en fechaHoyTexto
                                if (mesSintoma.equalsIgnoreCase(mesInicio)) {
                                    // Crear un nuevo RelativeLayout con márgenes en los lados izquierdo y derecho
                                    RelativeLayout relativeLayoutGrafico = new RelativeLayout(this);
                                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                                            RelativeLayout.LayoutParams.MATCH_PARENT,
                                            RelativeLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    // Establecer márgenes en los lados izquierdo y derecho (en este caso, 20dp)
                                    int marginInDp = getResources().getDimensionPixelSize(R.dimen.layout_margin_horizontal);
                                    layoutParams.setMargins(marginInDp, 0, marginInDp, marginInDp); // Agregar margen en la parte inferior
                                    relativeLayoutGrafico.setLayoutParams(layoutParams);
                                    relativeLayoutGrafico.setBackgroundResource(R.drawable.background_rounded_blanco);
                                    // Crear un nuevo TextView para la fecha y hora dentro del RelativeLayout
                                    TextView textViewFechaHora = new TextView(this);
                                    RelativeLayout.LayoutParams paramsFechaHora = new RelativeLayout.LayoutParams(
                                            RelativeLayout.LayoutParams.MATCH_PARENT,
                                            RelativeLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    paramsFechaHora.addRule(RelativeLayout.BELOW, R.id.texto_titulo_medicion1); // Alinea el TextView debajo de otro elemento, en este caso, texto_titulo_medicion1
                                    paramsFechaHora.setMargins(
                                            getResources().getDimensionPixelSize(R.dimen.editar_margin_start),
                                            getResources().getDimensionPixelSize(R.dimen.editar_margin_top),
                                            getResources().getDimensionPixelSize(R.dimen.editar_margin_end),
                                            getResources().getDimensionPixelSize(R.dimen.editar_margin_bottom)
                                    );
                                    // Obtener el día, mes y año de la fechaCalendarioMedicion
                                    int dia = calendarioSintoma.getFechaCalendarioSintoma().getDayOfMonth();
                                    String nombreMes = calendarioSintoma.getFechaCalendarioSintoma().getMonth().getDisplayName(TextStyle.SHORT, Locale.getDefault());
                                    int hora = calendarioSintoma.getFechaCalendarioSintoma().getHour();
                                    int minutos = calendarioSintoma.getFechaCalendarioSintoma().getMinute();
                                    String fechaFormateada = dia + " de " + nombreMes + " " + hora + ":" + minutos;

                                    textViewFechaHora.setText(fechaFormateada);
                                    textViewFechaHora.setLayoutParams(paramsFechaHora);
                                    textViewFechaHora.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24); // Tamaño del texto en sp
                                    textViewFechaHora.setTypeface(null, Typeface.BOLD); // Establece el estilo del texto como negrita
                                    // Agregar el TextView de fecha y hora al RelativeLayout
                                    relativeLayoutGrafico.addView(textViewFechaHora);

                                    TextView textViewValor = new TextView(this);
                                    RelativeLayout.LayoutParams paramsValor = new RelativeLayout.LayoutParams(
                                            RelativeLayout.LayoutParams.MATCH_PARENT,
                                            RelativeLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    paramsValor.addRule(RelativeLayout.BELOW, R.id.texto_titulo_medicion1);
                                    paramsValor.setMargins(
                                            getResources().getDimensionPixelSize(R.dimen.valor_margin_start),
                                            getResources().getDimensionPixelSize(R.dimen.valor_margin_top3),
                                            getResources().getDimensionPixelSize(R.dimen.valor_margin_end),
                                            getResources().getDimensionPixelSize(R.dimen.valor_margin_bottom)
                                    );
                                    textViewValor.setLayoutParams(paramsValor);
                                    textViewValor.setTextColor(ContextCompat.getColor(this, R.color.black));
                                    textViewValor.setTextSize(TypedValue.COMPLEX_UNIT_SP, 21);
                                    String valorSintoma = "Registro del síntoma";
                                    textViewValor.setText(valorSintoma);
                                    // Agregar el TextView al RelativeLayout
                                    relativeLayoutGrafico.addView(textViewValor);

                                    // Crear el ImageView para eliminar dentro del RelativeLayout
                                    ImageView imageViewEliminar = new ImageView(this);
                                    imageViewEliminar.setId(R.id.imagen_medicion1_2); // Establecer el ID
                                    imageViewEliminar.setLayoutParams(new RelativeLayout.LayoutParams(
                                            getResources().getDimensionPixelSize(R.dimen.eliminar_width),
                                            getResources().getDimensionPixelSize(R.dimen.eliminar_height)
                                    ));
                                    // Configurar los márgenes
                                    RelativeLayout.LayoutParams paramsEliminar = (RelativeLayout.LayoutParams) imageViewEliminar.getLayoutParams();
                                    paramsEliminar.setMargins(
                                            getResources().getDimensionPixelSize(R.dimen.eliminar_margin_start),
                                            getResources().getDimensionPixelSize(R.dimen.eliminar_margin_top),
                                            getResources().getDimensionPixelSize(R.dimen.eliminar_margin_end),
                                            getResources().getDimensionPixelSize(R.dimen.eliminar_margin_bottom)
                                    );
                                    // Establecer la alineación con respecto al padre (end = derecha)
                                    paramsEliminar.addRule(RelativeLayout.ALIGN_PARENT_END, RelativeLayout.TRUE);
                                    paramsEliminar.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
                                    imageViewEliminar.setLayoutParams(paramsEliminar);
                                    imageViewEliminar.setImageResource(R.drawable.eliminar_tacho_basura);
                                    // Agregar el ImageView de eliminar al RelativeLayout
                                    relativeLayoutGrafico.addView(imageViewEliminar);
                                    imageViewEliminar.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            // Pasa el codCalendarioMedicion como parámetro a la función popupBorrarMedicion
                                            popupBorrarSintoma(calendarioSintoma.getCodCalendarioSintoma());
                                            btnSemana.performClick();
                                        }
                                    });

                                    layoutPrincipal.addView(relativeLayoutGrafico);

                                    seCreoRelativeLayout = true;

                                }
                            }

                        }
                    } else {
                        // Manejo de error si las fechas son nulas o vacías
                        Log.e("MiApp", "Las fechas de inicio o fin son nulas o vacías");
                    }
                }
                if (fechaHoyTexto.matches("\\d{1,2} de \\w+ - \\d{1,2} de \\w+")) {
                    Log.d("MiApp", "entra al segundo formato");
                    // Procesar el segundo formato (por ejemplo, "27 de Ago - 2 de Sep")
                    String[] partesFechaHoy = fechaHoyTexto.split(" - ");
                    if (partesFechaHoy.length == 2) {
                        String[] partesInicio = partesFechaHoy[0].split(" de ");
                        String[] partesFin = partesFechaHoy[1].split(" de ");
                        if (partesInicio.length == 2 && partesFin.length == 2) {
                            int diaInicio = Integer.parseInt(partesInicio[0].trim());
                            String mesInicio = partesInicio[1].trim();
                            int diaFin = Integer.parseInt(partesFin[0].trim());
                            String mesFin = partesFin[1].trim();

                            int diaSintoma = calendarioSintoma.getFechaCalendarioSintoma().getDayOfMonth();
                            if ((diaSintoma >= diaInicio && diaSintoma <= diaFin) || (diaSintoma >= diaInicio && diaSintoma <= diaFin + 30) || (diaSintoma <= diaInicio && diaSintoma <= diaFin)) {
                                String mesSintoma = calendarioSintoma.getFechaCalendarioSintoma().getMonth().getDisplayName(TextStyle.SHORT, Locale.getDefault());
                                mesSintoma = mesSintoma.substring(0, mesSintoma.length() - 1); // Elimina el último carácter (el punto)
                                mesSintoma = mesSintoma.substring(0, 1).toUpperCase() + mesSintoma.substring(1); // Convierte la primera letra en mayúscula
                                if (mesSintoma.equalsIgnoreCase(mesInicio) || mesSintoma.equalsIgnoreCase(mesFin) && (diaSintoma >= diaFin + 30)) {

                                    // Tanto el día como el mes coinciden con el rango de fechas
                                    // Crear un nuevo RelativeLayout con márgenes en los lados izquierdo y derecho
                                    RelativeLayout relativeLayoutGrafico = new RelativeLayout(this);
                                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                                            RelativeLayout.LayoutParams.MATCH_PARENT,
                                            RelativeLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    // Establecer márgenes en los lados izquierdo y derecho (en este caso, 20dp)
                                    int marginInDp = getResources().getDimensionPixelSize(R.dimen.layout_margin_horizontal);
                                    layoutParams.setMargins(marginInDp, 0, marginInDp, marginInDp); // Agregar margen en la parte inferior
                                    relativeLayoutGrafico.setLayoutParams(layoutParams);
                                    relativeLayoutGrafico.setBackgroundResource(R.drawable.background_rounded_blanco);
                                    // Crear un nuevo TextView para la fecha y hora dentro del RelativeLayout
                                    TextView textViewFechaHora = new TextView(this);
                                    RelativeLayout.LayoutParams paramsFechaHora = new RelativeLayout.LayoutParams(
                                            RelativeLayout.LayoutParams.MATCH_PARENT,
                                            RelativeLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    paramsFechaHora.addRule(RelativeLayout.BELOW, R.id.texto_titulo_medicion1); // Alinea el TextView debajo de otro elemento, en este caso, texto_titulo_medicion1
                                    paramsFechaHora.setMargins(
                                            getResources().getDimensionPixelSize(R.dimen.editar_margin_start),
                                            getResources().getDimensionPixelSize(R.dimen.editar_margin_top),
                                            getResources().getDimensionPixelSize(R.dimen.editar_margin_end),
                                            getResources().getDimensionPixelSize(R.dimen.editar_margin_bottom)
                                    );
                                    // Obtener el día, mes y año de la fechaCalendarioMedicion
                                    int dia = calendarioSintoma.getFechaCalendarioSintoma().getDayOfMonth();
                                    String nombreMes = calendarioSintoma.getFechaCalendarioSintoma().getMonth().getDisplayName(TextStyle.SHORT, Locale.getDefault());
                                    int hora = calendarioSintoma.getFechaCalendarioSintoma().getHour();
                                    int minutos = calendarioSintoma.getFechaCalendarioSintoma().getMinute();
                                    String fechaFormateada = dia + " de " + nombreMes + " " + hora + ":" + minutos;

                                    textViewFechaHora.setText(fechaFormateada);
                                    textViewFechaHora.setLayoutParams(paramsFechaHora);
                                    textViewFechaHora.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24); // Tamaño del texto en sp
                                    textViewFechaHora.setTypeface(null, Typeface.BOLD); // Establece el estilo del texto como negrita
                                    // Agregar el TextView de fecha y hora al RelativeLayout
                                    relativeLayoutGrafico.addView(textViewFechaHora);
                                    TextView textViewValor = new TextView(this);
                                    RelativeLayout.LayoutParams paramsValor = new RelativeLayout.LayoutParams(
                                            RelativeLayout.LayoutParams.MATCH_PARENT,
                                            RelativeLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    paramsValor.addRule(RelativeLayout.BELOW, R.id.texto_titulo_medicion1);
                                    paramsValor.setMargins(
                                            getResources().getDimensionPixelSize(R.dimen.valor_margin_start),
                                            getResources().getDimensionPixelSize(R.dimen.valor_margin_top3),
                                            getResources().getDimensionPixelSize(R.dimen.valor_margin_end),
                                            getResources().getDimensionPixelSize(R.dimen.valor_margin_bottom)
                                    );
                                    textViewValor.setLayoutParams(paramsValor);
                                    textViewValor.setTextColor(ContextCompat.getColor(this, R.color.black));
                                    textViewValor.setTextSize(TypedValue.COMPLEX_UNIT_SP, 21);
                                    // Obtener el valor y la unidad de medida de calendarioMedicion
                                    String valorSintoma = "Registro del síntoma";
                                    textViewValor.setText(valorSintoma);
                                    // Agregar el TextView al RelativeLayout
                                    relativeLayoutGrafico.addView(textViewValor);
                                    // Crear el ImageView para eliminar dentro del RelativeLayout
                                    ImageView imageViewEliminar = new ImageView(this);
                                    imageViewEliminar.setId(R.id.imagen_medicion1_2); // Establecer el ID
                                    imageViewEliminar.setLayoutParams(new RelativeLayout.LayoutParams(
                                            getResources().getDimensionPixelSize(R.dimen.eliminar_width),
                                            getResources().getDimensionPixelSize(R.dimen.eliminar_height)
                                    ));
                                    // Configurar los márgenes
                                    RelativeLayout.LayoutParams paramsEliminar = (RelativeLayout.LayoutParams) imageViewEliminar.getLayoutParams();
                                    paramsEliminar.setMargins(
                                            getResources().getDimensionPixelSize(R.dimen.eliminar_margin_start),
                                            getResources().getDimensionPixelSize(R.dimen.eliminar_margin_top),
                                            getResources().getDimensionPixelSize(R.dimen.eliminar_margin_end),
                                            getResources().getDimensionPixelSize(R.dimen.eliminar_margin_bottom)
                                    );
                                    // Establecer la alineación con respecto al padre (end = derecha)
                                    paramsEliminar.addRule(RelativeLayout.ALIGN_PARENT_END, RelativeLayout.TRUE);
                                    paramsEliminar.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
                                    imageViewEliminar.setLayoutParams(paramsEliminar);
                                    imageViewEliminar.setImageResource(R.drawable.eliminar_tacho_basura);
                                    // Agregar el ImageView de eliminar al RelativeLayout
                                    relativeLayoutGrafico.addView(imageViewEliminar);
                                    imageViewEliminar.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            popupBorrarSintoma(calendarioSintoma.getCodCalendarioSintoma());
                                            btnSemana.performClick();
                                        }
                                    });


                                    layoutPrincipal.addView(relativeLayoutGrafico);

                                    seCreoRelativeLayout = true;

                                }
                            }
                        }
                    }
                }

            }
        }
        if(mostrarSemana==true) {
            crearGrafico(calendarioSintomasFiltradosPorSemana);
        }
        // Verificar si se creó algún RelativeLayout
        if (seCreoRelativeLayout == false) {
            RelativeLayout relativeLayoutGrafico = new RelativeLayout(this);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            );
            int marginInDp = getResources().getDimensionPixelSize(R.dimen.layout_margin_horizontal);
            layoutParams.setMargins(marginInDp, 0, marginInDp, marginInDp); // Agregar margen en la parte inferior
            relativeLayoutGrafico.setLayoutParams(layoutParams);
            relativeLayoutGrafico.setBackgroundResource(R.drawable.background_rounded_blanco);
            TextView textViewFechaHora = new TextView(this);
            RelativeLayout.LayoutParams paramsFechaHora = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            );
            paramsFechaHora.addRule(RelativeLayout.BELOW, R.id.texto_titulo_medicion1); // Alinea el TextView debajo de otro elemento, en este caso, texto_titulo_medicion1
            paramsFechaHora.setMargins(
                    getResources().getDimensionPixelSize(R.dimen.editar_margin_start),
                    getResources().getDimensionPixelSize(R.dimen.editar_margin_top),
                    getResources().getDimensionPixelSize(R.dimen.editar_margin_end),
                    getResources().getDimensionPixelSize(R.dimen.editar_margin_bottom)
            );
            textViewFechaHora.setText("No hay datos");
            textViewFechaHora.setLayoutParams(paramsFechaHora);
            textViewFechaHora.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
            textViewFechaHora.setTypeface(null, Typeface.BOLD);
            relativeLayoutGrafico.addView(textViewFechaHora);
            layoutPrincipal.addView(relativeLayoutGrafico);

        }
    }


    private int obtenerNumeroMesDesdeAbreviatura(String abreviatura) {
        switch (abreviatura.toUpperCase()) {
            case "ENE":
                return 1; // Enero
            case "FEB":
                return 2; // Febrero
            case "MAR":
                return 3; // Marzo
            case "ABR":
                return 4; // Abril
            case "MAY":
                return 5; // Mayo
            case "JUN":
                return 6; // Junio
            case "JUL":
                return 7; // Julio
            case "AGO":
                return 8; // Agosto
            case "SEP":
                return 9; // Septiembre
            case "OCT":
                return 10; // Octubre
            case "NOV":
                return 11; // Noviembre
            case "DIC":
                return 12; // Diciembre
            default:
                return 1; // Valor predeterminado en caso de que la abreviatura sea desconocida
        }
    }

    // Función para obtener el nombre completo del mes a partir del número del mes
    private String obtenerNombreMesCompleto(int numeroMes) {
        Month mes = Month.of(numeroMes);
        return mes.getDisplayName(TextStyle.FULL, Locale.getDefault());
    }
    // Implementa esta función para obtener el mes desde el texto ("julio de 2023")
    private String obtenerMesDesdeTexto(String fechaTexto) {
        // Tu lógica para extraer el mes aquí
        // Por ejemplo, puedes dividir el texto en palabras y tomar la primera palabra.
        // Luego, puedes mapear esa palabra a la abreviatura del mes.

        // Ejemplo simplificado:
        String[] palabras = fechaTexto.split(" ");
        if (palabras.length >= 2) {
            String mes = palabras[0].toLowerCase(); // Obtener la primera palabra y convertirla a minúsculas

            // Mapear nombres de meses a abreviaturas
            switch (mes) {
                case "enero":
                    return "Ene";
                case "febrero":
                    return "Feb";
                case "marzo":
                    return "Mar";
                case "abril":
                    return "Abr";
                case "mayo":
                    return "May";
                case "junio":
                    return "Jun";
                case "julio":
                    return "Jul";
                case "agosto":
                    return "Ago";
                case "septiembre":
                    return "Sep";
                case "octubre":
                    return "Oct";
                case "noviembre":
                    return "Nov";
                case "diciembre":
                    return "Dic";
                default:
                    return "";
            }
        }

        return "";
    }

    // Implementa esta función para obtener el año desde el texto ("julio de 2023")
    private String obtenerAñoDesdeTexto(String fechaTexto) {
        // Tu lógica para extraer el año aquí
        // Por ejemplo, puedes dividir el texto en palabras y tomar la última palabra.

        // Ejemplo simplificado:
        String[] palabras = fechaTexto.split(" ");
        if (palabras.length >= 3) {
            return palabras[palabras.length - 1]; // Obtener la última palabra
        }

        return "";
    }
    private int obtenerNumeroDeMes(String nombreMesAbreviado) {
        int numeroMes = 0;

        switch (nombreMesAbreviado) {
            case "Ene":
                numeroMes = Calendar.JANUARY;
                break;
            case "Feb":
                numeroMes = Calendar.FEBRUARY;
                break;
            case "Mar":
                numeroMes = Calendar.MARCH;
                break;
            case "Abr":
                numeroMes = Calendar.APRIL;
                break;
            case "May":
                numeroMes = Calendar.MAY;
                break;
            case "Jun":
                numeroMes = Calendar.JUNE;
                break;
            case "Jul":
                numeroMes = Calendar.JULY;
                break;
            case "Ago":
                numeroMes = Calendar.AUGUST;
                break;
            case "Sep":
                numeroMes = Calendar.SEPTEMBER;
                break;
            case "Oct":
                numeroMes = Calendar.OCTOBER;
                break;
            case "Nov":
                numeroMes = Calendar.NOVEMBER;
                break;
            case "Dic":
                numeroMes = Calendar.DECEMBER;
                break;
        }

        return numeroMes;
    }
    private String obtenerMes(String fecha) {
        // Dividir el texto por espacios
        String[] partes = fecha.split(" ");

        if (partes.length >= 1) {
            // El primer elemento debe ser el nombre del mes
            return partes[0];
        } else {
            return ""; // Devolver una cadena vacía si no se puede analizar
        }
    }
    private int obtenerNumeroDeMess(String nombreMes) {
        // Aquí defines un array con los nombres de los meses en español
        String[] meses = {"enero", "febrero", "marzo", "abril", "mayo", "junio", "julio", "agosto", "septiembre", "octubre", "noviembre", "diciembre"};

        // Busca el nombre del mes en el array y devuelve su índice (que es el número de mes + 1)
        for (int i = 0; i < meses.length; i++) {
            if (meses[i].equalsIgnoreCase(nombreMes)) {
                return i ; // Suma 1 porque los índices de arrays comienzan en 0
            }
        }

        return 0; // Devuelve 0 si el nombre del mes no se encuentra en la lista
    }
    private String convertirFechaAlNuevoFormato(String fecha) {
        // Divide la cadena en palabras
        String[] palabras = fecha.split(" ");

        // Verifica cada palabra y capitaliza la primera letra, manteniendo las demás en minúscula
        StringBuilder fechaFormateada = new StringBuilder();
        for (int i = 0; i < palabras.length; i++) {
            String palabra = palabras[i];
            if (!palabra.isEmpty()) {
                if (i > 0) {
                    fechaFormateada.append(" ");
                }
                if (i == 1 || i == 4) { // Las posiciones 1 y 4 corresponden a las palabras "de" entre números
                    fechaFormateada.append("de");
                } else if (i == 2) { // Si es el nombre del mes, capitaliza la primera letra
                    fechaFormateada.append(Character.toUpperCase(palabra.charAt(0)))
                            .append(palabra.substring(1).toLowerCase());
                } else {
                    fechaFormateada.append(palabra.toLowerCase());
                }
            }
        }

        return fechaFormateada.toString();
    }

    private Calendar parsearFechaComoCalendar(String fechaStr) {
        try {
            int index = 0;
            while (index < fechaStr.length() && Character.isDigit(fechaStr.charAt(index))) {
                index++;
            }
            String diaStr = fechaStr.substring(0, index);
            int dia = Integer.parseInt(diaStr);
            String mesStr = fechaStr.substring(index).toLowerCase();
            Map<String, Integer> meses = new HashMap<>();
            meses.put("dene", Calendar.JANUARY);
            meses.put("defeb", Calendar.FEBRUARY);
            meses.put("demar", Calendar.MARCH);
            meses.put("deabr", Calendar.APRIL);
            meses.put("demay", Calendar.MAY);
            meses.put("dejun", Calendar.JUNE);
            meses.put("dejul", Calendar.JULY);
            meses.put("deago", Calendar.AUGUST);
            meses.put("desep", Calendar.SEPTEMBER);
            meses.put("deoct", Calendar.OCTOBER);
            meses.put("denov", Calendar.NOVEMBER);
            meses.put("dedic", Calendar.DECEMBER);
            int mes = meses.get(mesStr);
            int año = Calendar.getInstance().get(Calendar.YEAR);
            Calendar calendar = Calendar.getInstance();
            calendar.clear();
            calendar.set(año, mes, dia);
            return calendar;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    private void popupBorrarSintoma(int codCalendarioSintoma) {
        View popupView = getLayoutInflater().inflate(R.layout.n79_1_popup_borrar_medicion, null);

        // Crear la instancia de PopupWindow
        PopupWindow popupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setElevation(10.0f); // Agrega elevación para el efecto de sombra

        // Hacer que el popup sea enfocable
        popupWindow.setFocusable(true);

        // Configurar animación para oscurecer el fondo
        View dimView = findViewById(R.id.dim_view);
        dimView.setVisibility(View.VISIBLE);

        // Configurar la animación de entrada del popup
        Animation scaleAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.popup);
        popupView.startAnimation(scaleAnimation);

        // Mostrar el popup en la ubicación deseada
        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);

        TextView cancelar = popupView.findViewById(R.id.cancelar);
        TextView eliminar = popupView.findViewById(R.id.eliminar);
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ocultar el PopupWindow
                popupWindow.dismiss();

                // Ocultar el fondo oscurecido
                dimView.setVisibility(View.GONE);
            }
        });
        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eliminarCalendarioSintoma(codCalendarioSintoma);

                // Ocultar el PopupWindow
                popupWindow.dismiss();

                // Ocultar el fondo oscurecido
                dimView.setVisibility(View.GONE);
            }
        });

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                dimView.setVisibility(View.GONE);
            }
        });
    }

    private void eliminarCalendarioSintoma(int codCalendarioSintoma) {

        Log.d("Miapp","codCalendarioSintoma: "+codCalendarioSintoma);

        CalendarioSintoma sintomaModificada = new CalendarioSintoma();
        sintomaModificada.setCodCalendarioSintoma(codCalendarioSintoma); // Establece el ID de la medición
        LocalDate fechaActual = LocalDate.now();
        sintomaModificada.setFechaFinVigenciaCS(fechaActual);
        Call<CalendarioSintoma> call10 = calendarioSintomaApi.eliminarCalendarioSintoma(sintomaModificada);

        call10.enqueue(new Callback<CalendarioSintoma>() {
            @Override
            public void onResponse(Call<CalendarioSintoma> call10, Response<CalendarioSintoma> response) {
                if (response.isSuccessful()) {
                    // Puedes mantener el código existente para iniciar la nueva actividad después de la modificación
                    Intent intent2 = new Intent(MasInfoSintomaActivity.this, AgregarSeguimientoActivity.class);
                    intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent2.putExtra("codUsuario", getIntent().getIntExtra("codUsuario", 0));
                    intent2.putExtra("calendarioSeleccionadoid", getIntent().getIntExtra("calendarioSeleccionadoid", 0));
                    startActivity(intent2);
                } else {
                    Toast.makeText(getApplicationContext(), "Error al eliminar el CalendarioSintoma", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CalendarioSintoma> call10, Throwable t) {
                // Maneja el error de la llamada de red, por ejemplo, mostrando un mensaje de error.
                Toast.makeText(getApplicationContext(), "Error de red al eliminar el CalendarioSintoma", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
