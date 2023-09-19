package com.example.medical;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.medical.model.Calendario;
import com.example.medical.model.CalendarioMedicion;
import com.example.medical.retrofit.CalendarioApi;
import com.example.medical.retrofit.CalendarioMedicionApi;
import com.example.medical.retrofit.RetrofitService;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MasInfoMedicionActivity extends AppCompatActivity {
    private int codCalendario;
    private Calendario calendarioSeleccionado;
    private int codCalendarioMedicion;
    private int codMedicion;
    private CalendarioApi calendarioApi;
    private CalendarioMedicionApi calendarioMedicionApi;
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
    List<CalendarioMedicion> calendarioMedicionesSelec ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.n79_0_mas_info_medicion);

        RetrofitService retrofitService = new RetrofitService();

        // Recuperar el objeto calendarioSeleccionado del Intent
        Intent intent1 = getIntent();
        codCalendario = intent1.getIntExtra("calendarioSeleccionadoid", 0);
        Log.d("MiApp", "codCalendario en AgregarSeguimientoActivity: " + codCalendario); // Agregar este log
        codCalendarioMedicion = intent1.getIntExtra("codCalendarioMedicionid", 0);
        codMedicion = intent1.getIntExtra("medicionid", 0);

        // Inicializar las APIs utilizando Retrofit
        calendarioApi = retrofitService.getRetrofit().create(CalendarioApi.class);
        calendarioMedicionApi = retrofitService.getRetrofit().create(CalendarioMedicionApi.class);

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
        final Calendar[] fechaActualSemana = {Calendar.getInstance()};
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
                    CrearRelativeLayout(calendarioMedicionesSelec);
                } else if (mostrar3Meses ==true) {
                    mesActual -= 3;
                    if (mesActual < 0) {
                        mesActual += 12;
                        añoActual[0]--;
                    }
                    if (mesActual >= 0 && mesActual < mesesAbreviados.length) {
                        int mesFinal = (mesActual) % 12; // Obtener el mes final del rango
                        int mesInicial = mesActual - 2;
                        Resources resources = getResources(); // Obtener el objeto Resources
                        String[] meses_Abreviados = resources.getStringArray(R.array.meses_abreviados);

                        String mesInicialAbreviado = meses_Abreviados[mesInicial];
                        String mesFinalAbreviado = meses_Abreviados[mesFinal];

                        updateFechaHoy(añoActual[0], mesInicialAbreviado + " - " + mesFinalAbreviado);
                    }
                    CrearRelativeLayout(calendarioMedicionesSelec);
                } else if (mostrarSemana) {
                    fechaActualSemana2.add(Calendar.DAY_OF_MONTH, -7);
                    fechaActualSemana2.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
                    int primerDiaSemana = fechaActualSemana2.get(Calendar.DAY_OF_MONTH);
                    int mesActualSemana = fechaActualSemana2.get(Calendar.MONTH);
                    Resources resources = getResources();
                    String[] meses_Abreviados = resources.getStringArray(R.array.meses_abreviados);
                    String[] meses_Completos = resources.getStringArray(R.array.months_array);
                    fechaActualSemana2.add(Calendar.DAY_OF_MONTH, 6);
                    int ultimoDiaSemana = fechaActualSemana2.get(Calendar.DAY_OF_MONTH);
                    if (mesActualSemana != fechaActualSemana2.get(Calendar.MONTH)) {
                        // Los días están en meses diferentes
                        int mesSiguiente = fechaActualSemana2.get(Calendar.MONTH);
                        String nombreMesSiguiente = meses_Abreviados[mesSiguiente];
                        String nombreMesActual = meses_Abreviados[mesActualSemana];
                        updateFechaHoy(0, primerDiaSemana + " de " + nombreMesActual + " - " + ultimoDiaSemana + " de " + nombreMesSiguiente);
                        CrearRelativeLayout(calendarioMedicionesSelec);
                    } else {
                        String nombreMesActual = meses_Completos[mesActualSemana];
                        updateFechaHoy(0, primerDiaSemana + " - " + ultimoDiaSemana + " de " + nombreMesActual);
                        CrearRelativeLayout(calendarioMedicionesSelec);
                    }
                } else {
                    añoActual[0]--; // Disminuir el año
                    updateFechaHoy(añoActual[0], ""); // Deja el mes en blanco
                    CrearRelativeLayout(calendarioMedicionesSelec);

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
                        CrearRelativeLayout(calendarioMedicionesSelec);
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
                        CrearRelativeLayout(calendarioMedicionesSelec);
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
                        CrearRelativeLayout(calendarioMedicionesSelec);
                    }
                } else if (mostrarSemana) {
                    fechaActualSemana2.add(Calendar.DAY_OF_MONTH, +7);
                    fechaActualSemana2.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
                    int primerDiaSemana = fechaActualSemana2.get(Calendar.DAY_OF_MONTH);
                    int mesActualSemana = fechaActualSemana2.get(Calendar.MONTH);
                    Resources resources = getResources();
                    String[] meses_Abreviados = resources.getStringArray(R.array.meses_abreviados);
                    String[] meses_Completos = resources.getStringArray(R.array.months_array);
                    fechaActualSemana2.add(Calendar.DAY_OF_MONTH, 6);
                    int ultimoDiaSemana = fechaActualSemana2.get(Calendar.DAY_OF_MONTH);
                    if (mesActualSemana != fechaActualSemana2.get(Calendar.MONTH)) {
                        // Los días están en meses diferentes
                        int mesSiguiente = fechaActualSemana2.get(Calendar.MONTH);
                        String nombreMesSiguiente = meses_Abreviados[mesSiguiente];
                        String nombreMesActual = meses_Abreviados[mesActualSemana];
                        updateFechaHoy(0, primerDiaSemana + " de " + nombreMesActual + " - " + ultimoDiaSemana + " de " + nombreMesSiguiente);
                        CrearRelativeLayout(calendarioMedicionesSelec);
                    } else {
                        String nombreMesActual = meses_Completos[mesActualSemana];
                        updateFechaHoy(0, primerDiaSemana + " - " + ultimoDiaSemana + " de " + nombreMesActual);
                        CrearRelativeLayout(calendarioMedicionesSelec);
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
                        CrearRelativeLayout(calendarioMedicionesSelec);
                    } else {
                        updateFechaHoy(añoActual[0], ""); // Dejar el mes en blanco
                        CrearRelativeLayout(calendarioMedicionesSelec);
                    }
            }
        }
        });

        // Variables para los años actuales en cada modo
        int añoActualMes = Calendar.getInstance().get(Calendar.YEAR);
        int añoActual3Meses = Calendar.getInstance().get(Calendar.YEAR);
        int añoActualAnio = Calendar.getInstance().get(Calendar.YEAR);
        int añoActualSemana = Calendar.getInstance().get(Calendar.YEAR);
        Calendar calendar = Calendar.getInstance();
        int mesActualMes = calendar.get(Calendar.MONTH);
        int mesActual3Meses = calendar.get(Calendar.MONTH);
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
                        añoActual[0] = añoActualSemana;
                        fechaActualSemana[0] = Calendar.getInstance();
                        break;
                    case R.id.btnMes:
                        selectButton(btnMes);
                        mostrarMes = true;
                        mostrar3Meses = false;
                        mostrarSemana = false;
                        añoActual[0] = añoActualMes;
                        mesActual =  mesActualMes;
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
                    updateFechaHoy(añoActual[0], mesesAbreviados[mesSeleccionado]);
                    CrearRelativeLayout(calendarioMedicionesSelec);
                } else if (mostrar3Meses) {
                    int mesActual = Calendar.getInstance().get(Calendar.MONTH);
                    int mesInicial = (mesActual - 2 + 12) % 12; // Restar 2 meses
                    int mesFinal = mesActual;
                    añoActual[0] = añoActual3Meses;
                    String mesInicialAbreviado = mesesAbreviados[mesInicial];
                    String mesFinalAbreviado = mesesAbreviados[mesFinal];
                    updateFechaHoy(añoActual[0], mesInicialAbreviado + " - " + mesFinalAbreviado);
                    CrearRelativeLayout(calendarioMedicionesSelec);
                } else  if (mostrarSemana) {
                    // Obtener la fecha actual
                    Calendar calendar = Calendar.getInstance();
                    int añoActual = calendar.get(Calendar.YEAR); // Obtener el año actual
                    int mesActual = calendar.get(Calendar.MONTH);
                    int diaActual = calendar.get(Calendar.DAY_OF_MONTH);

                    // Ajustar el día actual al primer día de la semana (domingo)
                    int primerDiaSemana = diaActual;
                    while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
                        calendar.add(Calendar.DAY_OF_MONTH, -1);
                        primerDiaSemana = calendar.get(Calendar.DAY_OF_MONTH);
                    }

                    // Calcular el último día de la semana (sábado)
                    calendar.add(Calendar.DAY_OF_MONTH, 6);
                    int ultimoDiaSemana = calendar.get(Calendar.DAY_OF_MONTH);

                    // Obtener el nombre del mes actual
                    Resources resources = getResources(); // Obtener el objeto Resources
                    String[] meses_Completos = resources.getStringArray(R.array.months_array);
                    String nombreMesActual = meses_Completos[mesActual];

                    // Actualizar la fecha en la interfaz
                    String fechaInicio = primerDiaSemana + "";
                    String fechaFin = ultimoDiaSemana + " de " + nombreMesActual;
                    updateFechaHoy(0, fechaInicio + " - " + fechaFin);
                    CrearRelativeLayout(calendarioMedicionesSelec);
                }
                else {
                    añoActual[0] = añoActualAnio;
                    updateFechaHoy(añoActual[0], "");
                    CrearRelativeLayout(calendarioMedicionesSelec);
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
        Call<List<CalendarioMedicion>> call = calendarioMedicionApi.getByCodCalendarioMedicion(codCalendario);
        call.enqueue(new Callback<List<CalendarioMedicion>>() {
            @Override
            public void onResponse(Call<List<CalendarioMedicion>> call, Response<List<CalendarioMedicion>> response) {
                if (response.isSuccessful()) {
                    List<CalendarioMedicion> calendarioMediciones = response.body();
                    Log.d("MiApp", "Tamaño de la lista calendarioMediciones en MasInfoMedicion: " + calendarioMediciones.size());

                    obtenerCalendarioMedicionConMedicion();

                } else {
                    Log.d("MiApp", "Error en la solicitud 1: 1 " + response.message());
                }
            }
            @Override
            public void onFailure(Call<List<CalendarioMedicion>> call, Throwable t) {
                Log.e("MiApp", "Error en la solicitud 2: 2 " + t.getMessage());
            }
        });
    }
    private void obtenerCalendarioMedicionConMedicion() {
        Call<List<CalendarioMedicion>> call5 = calendarioMedicionApi.getByCodMedicion(codMedicion);
        Log.d("MiApp", "Codigo medicion MasInfoMedicion: " + codMedicion);
        call5.enqueue(new Callback<List<CalendarioMedicion>>() {
            @Override
            public void onResponse(Call<List<CalendarioMedicion>> call5, Response<List<CalendarioMedicion>> response) {
                if (response.isSuccessful()) {
                    List<CalendarioMedicion> calendarioMediciones = response.body();
                    Log.d("MiApp", "Tamaño de la lista calendarioMediciones en MasInfoMedicion: " + calendarioMediciones.size());
                    ObtenerFechaYValor(calendarioMediciones);
                    calendarioMedicionesSelec = calendarioMediciones;
                    CrearRelativeLayout(calendarioMedicionesSelec);
                } else {
                    int statusCode = response.code();
                    Log.d("MiApp", "Código de estado HTTP: " + statusCode);

                    Log.d("MiApp", "Error en la solicitud este: " + response.message());
                }
            }
            @Override
            public void onFailure(Call<List<CalendarioMedicion>> call5, Throwable t) {
                Log.e("MiApp", "Error en la solicitud 52: " + t.getMessage());
            }
        });
    }
    private void ObtenerFechaYValor(List<CalendarioMedicion> calendarioMediciones) {
        Log.d("MiApp", "entra: " + calendarioMediciones.size());
        // Recorrer la lista de CalendarioMedicion
        for (CalendarioMedicion calendarioMedicion : calendarioMediciones) {
            // Supongamos que tienes una instancia de Medicion llamada medicion
            TextView textViewNombreSeguimiento = findViewById(R.id.texto_seguimiento);
            textViewNombreSeguimiento.setText(calendarioMedicion.getMedicion().getNombreMedicion());
            TextView textView2NombreSeguimiento = findViewById(R.id.texto_titulo_grafico);
            textView2NombreSeguimiento.setText("Medición");
        }
    }

    private void CrearRelativeLayout(List<CalendarioMedicion> calendarioMediciones) {
        Log.d("MiApp", "entra: " + calendarioMediciones.size());
        String fechaHoyTexto = fechaHoy.getText().toString();
        Log.d("MiApp", "efechaHoy: " +fechaHoyTexto);
        LinearLayout layoutPrincipal = findViewById(R.id.contenido2);
        layoutPrincipal.removeAllViews();
        // Lista para almacenar los nombres de los meses con datos
        List<String> mesesConDatos = new ArrayList<>();
        // Recorrer la lista de CalendarioMedicion

        // Recorrer la lista de CalendarioMedicion
        for (CalendarioMedicion calendarioMedicion : calendarioMediciones) {

            if (mostrarMes ==true) {
                Log.d("MiApp", "entra al mostrarMes: " + calendarioMediciones.size());
                // Obtener el año de la fechaCalendarioMedicion
                String añoFecha = obtenerAñoEnFormatoDeseado(calendarioMedicion.getFechaCalendarioMedicion());
                String mesFecha = obtenerMesEnFormatoDeseado(calendarioMedicion.getFechaCalendarioMedicion());
                if (fechaHoyTexto.contains(añoFecha)) {
                    Log.d("MiApp", "entra al añofecha: " +añoFecha);
                    Log.d("MiApp", "mesfecha: " +mesFecha);
                    if(fechaHoyTexto.contains(mesFecha)){
                        Log.d("MiApp", "entra al mesfecha: " +mesFecha);
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
                    int dia = calendarioMedicion.getFechaCalendarioMedicion().getDayOfMonth();
                    String nombreMes = calendarioMedicion.getFechaCalendarioMedicion().getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault());
                    int año = calendarioMedicion.getFechaCalendarioMedicion().getYear();
                    // Crear la cadena con el formato deseado
                    String fechaFormateada = dia + " de " + nombreMes + " de " + año;
                    textViewFechaHora.setText(fechaFormateada);
                    textViewFechaHora.setLayoutParams(paramsFechaHora);
                    textViewFechaHora.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24); // Tamaño del texto en sp
                    textViewFechaHora.setTypeface(null, Typeface.BOLD); // Establece el estilo del texto como negrita
                    // Agregar el TextView de fecha y hora al RelativeLayout
                    relativeLayoutGrafico.addView(textViewFechaHora);
                    // Crear el TextView para mostrar el valor y la unidad de medida
                    TextView textViewValor = new TextView(this);
                    RelativeLayout.LayoutParams paramsValor = new RelativeLayout.LayoutParams(
                            RelativeLayout.LayoutParams.MATCH_PARENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT
                    );
                    paramsValor.addRule(RelativeLayout.BELOW, R.id.texto_titulo_medicion1);
                    paramsValor.setMargins(
                            getResources().getDimensionPixelSize(R.dimen.valor_margin_start),
                            getResources().getDimensionPixelSize(R.dimen.valor_margin_top),
                            getResources().getDimensionPixelSize(R.dimen.valor_margin_end),
                            getResources().getDimensionPixelSize(R.dimen.valor_margin_bottom)
                    );
                    textViewValor.setLayoutParams(paramsValor);
                    textViewValor.setTextColor(ContextCompat.getColor(this, R.color.black));
                    textViewValor.setTextSize(TypedValue.COMPLEX_UNIT_SP, 21);
                    // Obtener el valor y la unidad de medida de calendarioMedicion
                    String valorMedicion = "Valor: " + calendarioMedicion.getValorCalendarioMedicion() + " " + calendarioMedicion.getMedicion().getUnidadMedidaMedicion();
                    textViewValor.setText(valorMedicion);
                    // Agregar el TextView al RelativeLayout
                    relativeLayoutGrafico.addView(textViewValor);
                    // Crear el ImageView para editar dentro del RelativeLayout
                    ImageView imageViewEditar = new ImageView(this);
                    imageViewEditar.setLayoutParams(new RelativeLayout.LayoutParams(
                            getResources().getDimensionPixelSize(R.dimen.editar_width),
                            getResources().getDimensionPixelSize(R.dimen.editar_height)
                    ));
                    // Configurar los márgenes
                    RelativeLayout.LayoutParams paramsEditar = (RelativeLayout.LayoutParams) imageViewEditar.getLayoutParams();
                    paramsEditar.setMargins(
                            getResources().getDimensionPixelSize(R.dimen.editar_margin_start),
                            getResources().getDimensionPixelSize(R.dimen.editar_margin_top),
                            getResources().getDimensionPixelSize(R.dimen.editar_margin_end),
                            getResources().getDimensionPixelSize(R.dimen.editar_margin_bottom)
                    );
                    // Establecer la alineación con respecto al padre (end = derecha)
                    paramsEditar.addRule(RelativeLayout.ALIGN_PARENT_END, RelativeLayout.TRUE);
                    paramsEditar.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
                    imageViewEditar.setLayoutParams(paramsEditar);
                    imageViewEditar.setImageResource(R.drawable.lapiz_editar);
                    // Agregar el ImageView de editar al RelativeLayout
                    relativeLayoutGrafico.addView(imageViewEditar);
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
                    layoutPrincipal.addView(relativeLayoutGrafico);
                }
            }
            } else if (mostrarSemana == true) {
                Log.d("MiApp", "entra al mostrarSemana: " + calendarioMediciones.size());

            }  else {
                Log.d("MiApp", "entra al mostrarMes: " + calendarioMediciones.size());
                // Obtener el año de la fechaCalendarioMedicion
                String añoFecha = obtenerAñoEnFormatoDeseado(calendarioMedicion.getFechaCalendarioMedicion());

                if (fechaHoyTexto.contains(añoFecha)) {
                    Log.d("MiApp", "entra al añofecha: " + añoFecha);
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
                        int dia = calendarioMedicion.getFechaCalendarioMedicion().getDayOfMonth();
                        String nombreMes = calendarioMedicion.getFechaCalendarioMedicion().getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault());
                        int año = calendarioMedicion.getFechaCalendarioMedicion().getYear();
                        // Crear la cadena con el formato deseado
                        String fechaFormateada = dia + " de " + nombreMes + " de " + año;
                        textViewFechaHora.setText(fechaFormateada);
                        textViewFechaHora.setLayoutParams(paramsFechaHora);
                        textViewFechaHora.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24); // Tamaño del texto en sp
                        textViewFechaHora.setTypeface(null, Typeface.BOLD); // Establece el estilo del texto como negrita
                        // Agregar el TextView de fecha y hora al RelativeLayout
                        relativeLayoutGrafico.addView(textViewFechaHora);
                        // Crear el TextView para mostrar el valor y la unidad de medida
                        TextView textViewValor = new TextView(this);
                        RelativeLayout.LayoutParams paramsValor = new RelativeLayout.LayoutParams(
                                RelativeLayout.LayoutParams.MATCH_PARENT,
                                RelativeLayout.LayoutParams.WRAP_CONTENT
                        );
                        paramsValor.addRule(RelativeLayout.BELOW, R.id.texto_titulo_medicion1);
                        paramsValor.setMargins(
                                getResources().getDimensionPixelSize(R.dimen.valor_margin_start),
                                getResources().getDimensionPixelSize(R.dimen.valor_margin_top),
                                getResources().getDimensionPixelSize(R.dimen.valor_margin_end),
                                getResources().getDimensionPixelSize(R.dimen.valor_margin_bottom)
                        );
                        textViewValor.setLayoutParams(paramsValor);
                        textViewValor.setTextColor(ContextCompat.getColor(this, R.color.black));
                        textViewValor.setTextSize(TypedValue.COMPLEX_UNIT_SP, 21);
                        // Obtener el valor y la unidad de medida de calendarioMedicion
                        String valorMedicion = "Valor: " + calendarioMedicion.getValorCalendarioMedicion() + " " + calendarioMedicion.getMedicion().getUnidadMedidaMedicion();
                        textViewValor.setText(valorMedicion);
                        // Agregar el TextView al RelativeLayout
                        relativeLayoutGrafico.addView(textViewValor);
                        // Crear el ImageView para editar dentro del RelativeLayout
                        ImageView imageViewEditar = new ImageView(this);
                        imageViewEditar.setLayoutParams(new RelativeLayout.LayoutParams(
                                getResources().getDimensionPixelSize(R.dimen.editar_width),
                                getResources().getDimensionPixelSize(R.dimen.editar_height)
                        ));
                        // Configurar los márgenes
                        RelativeLayout.LayoutParams paramsEditar = (RelativeLayout.LayoutParams) imageViewEditar.getLayoutParams();
                        paramsEditar.setMargins(
                                getResources().getDimensionPixelSize(R.dimen.editar_margin_start),
                                getResources().getDimensionPixelSize(R.dimen.editar_margin_top),
                                getResources().getDimensionPixelSize(R.dimen.editar_margin_end),
                                getResources().getDimensionPixelSize(R.dimen.editar_margin_bottom)
                        );
                        // Establecer la alineación con respecto al padre (end = derecha)
                        paramsEditar.addRule(RelativeLayout.ALIGN_PARENT_END, RelativeLayout.TRUE);
                        paramsEditar.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
                        imageViewEditar.setLayoutParams(paramsEditar);
                        imageViewEditar.setImageResource(R.drawable.lapiz_editar);
                        // Agregar el ImageView de editar al RelativeLayout
                        relativeLayoutGrafico.addView(imageViewEditar);
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
                        layoutPrincipal.addView(relativeLayoutGrafico);
                    }
                }

            }
        }

    private int obtenerDiaEnFormatoDeseado(String fecha) {
        // Implementa la lógica para extraer el día de la fecha en el formato deseado
        // Por ejemplo, si la fecha es "2023-09-17", puedes hacer algo como esto:
        String[] partesFecha = fecha.split("-");
        if (partesFecha.length == 3) {
            try {
                return Integer.parseInt(partesFecha[2]);
            } catch (NumberFormatException e) {
                // Maneja la excepción si la conversión a entero falla
                e.printStackTrace();
            }
        }
        // Si no se puede obtener el día, devuelve un valor predeterminado o maneja el error de otra manera
        return -1;
    }
    private String obtenerMesEnFormatoDeseado(LocalDateTime fecha) {
        // Crea un formateador que genere la abreviatura del nombre del mes
        DateTimeFormatter formatoMesAbreviado = DateTimeFormatter.ofPattern("MMM");

        // Formatea la fecha para obtener la abreviatura del nombre del mes
        String mesAbreviado = fecha.format(formatoMesAbreviado);

        // Convierte la primera letra a mayúscula y elimina el punto
        mesAbreviado = mesAbreviado.substring(0, 1).toUpperCase() + mesAbreviado.substring(1).replace(".", "");

        return mesAbreviado;
    }


    private String obtenerAñoEnFormatoDeseado(LocalDateTime fecha) {
        // Obtener el año de la fecha
        int año = fecha.getYear();
        // Devolver el año como una cadena
        return String.valueOf(año);
    }





}

