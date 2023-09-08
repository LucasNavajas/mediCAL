package com.example.medical;


import com.example.medical.model.Calendario;
import com.example.medical.model.CalendarioSintoma;
import com.example.medical.model.Sintoma; // Asegúrate de importar la clase Sintoma correctamente

import android.content.Intent;
import android.os.Bundle;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.AutoCompleteTextView;

import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import com.example.medical.model.Sintoma;
import com.example.medical.retrofit.CalendarioApi;
import com.example.medical.retrofit.CalendarioSintomaApi;
import com.example.medical.retrofit.RetrofitService;
import com.example.medical.retrofit.SintomaApi;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AnadirSintomaActivity extends AppCompatActivity {

    private SintomaApi sintomaApi;
    private CalendarioApi calendarioApi;
    private CalendarioSintomaApi calendarioSintomaApi;
    // Declarar una variable para llevar el registro del último código utilizado
    private int lastCalendarioSintomaCode = 0;
    private LinearLayout lineargrande;
    private Calendar selectedDate = Calendar.getInstance();
    private List<Sintoma> sintomas = new ArrayList<>(); // Lista de síntomas
    private Map<Integer, CheckBox> checkBoxMap = new HashMap<>();
    private Map<Integer, Boolean> savedSelections = new HashMap<>(); // Guarda el estado de selección

    private Set<Sintoma> sintomasSeleccionados = new HashSet<>(); // Síntomas seleccionados

    private Calendario calendarioSeleccionado;
    private List<Integer> calendarioSintomaIds = new ArrayList<>();

    private int codCalendario ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.n77_anadir_sintomas);


        // Configura tus APIs y otros elementos necesarios

        lineargrande = findViewById(R.id.lineargrande);

        RetrofitService retrofitService = new RetrofitService();
        sintomaApi = retrofitService.getRetrofit().create(SintomaApi.class);
        calendarioSintomaApi =retrofitService.getRetrofit().create(CalendarioSintomaApi.class);

        // Recuperar el objeto calendarioSeleccionado del Intent
        Intent intent1 = getIntent();
        codCalendario = intent1.getIntExtra("calendarioSeleccionadoid", 0);
        Log.d("MiApp", "codCalendario en AnadirSintomaActivity: " + codCalendario);

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

        obtenerSintomasDesdeApi();

        // Configura la flecha hacia atrás y otros elementos

        AutoCompleteTextView autoCompleteTextView = findViewById(R.id.editText_buscar);
        autoCompleteTextView = findViewById(R.id.editText_buscar);
        ImageView lupaImageView = findViewById(R.id.lupa_buscar);

        autoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String searchText = charSequence.toString().trim();
                buscarSintomas(searchText);
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });



        Button buttonaceptar = findViewById(R.id.button_guardar);
        buttonaceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupCambiarFechaHora();
            }
        });

        findViewById(R.id.boton_volver).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Vuelve a la pantalla anterior
                onBackPressed();
            }
        });

        autoCompleteTextView = findViewById(R.id.editText_buscar); // Reemplaza con el ID correcto
        autoCompleteTextView.setOnItemClickListener((parent, view, position, id) -> {
            Sintoma selectedSintoma = (Sintoma) parent.getItemAtPosition(position);
            boolean isSelected = !sintomasSeleccionados.contains(selectedSintoma);

            if (isSelected) {
                sintomasSeleccionados.add(selectedSintoma);
            } else {
                sintomasSeleccionados.remove(selectedSintoma);
            }

            // Actualizar la interfaz de usuario inmediatamente
            actualizarInterfazUsuario();
        });

        // Almacena el estado de selección actual en savedSelections
        for (Sintoma sintoma : sintomas) {
            savedSelections.put(sintoma.getCodSintoma(), sintoma.isSelected());
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        // Actualizar la interfaz de usuario al volver a la actividad
        actualizarInterfazUsuario();
    }



    private void obtenerSintomasDesdeApi() {
        Call<List<Sintoma>> call = sintomaApi.getAllSintomas();
        call.enqueue(new Callback<List<Sintoma>>() {
            @Override
            public void onResponse(Call<List<Sintoma>> call, Response<List<Sintoma>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    sintomas = response.body();
                    Log.d("MiApp", "Síntomas obtenidos desde la API: " + sintomas.size());

                    // Actualizar la interfaz después de obtener los síntomas
                    actualizarInterfazUsuario();
                }
            }

            @Override
            public void onFailure(Call<List<Sintoma>> call, Throwable t) {
                // Manejar error de la API
                Toast.makeText(AnadirSintomaActivity.this, "Error al obtener los síntomas. Por favor, inténtalo nuevamente.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void actualizarInterfazUsuario() {
        // Limpiar el layout antes de agregar los checkboxes actualizados
        lineargrande.removeAllViews();

        // Agregar los checkboxes actualizados en función de la lista sintomas
        for (Sintoma sintoma : sintomas) {
            agregarCheckBoxYSintoma(sintoma);
        }
    }

    private void agregarCheckBoxYSintoma(Sintoma sintoma) {
        LinearLayout linearLayout = new LinearLayout(AnadirSintomaActivity.this);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);

        // Crear una nueva instancia de CheckBox y agregarla al mapa checkBoxMap
        CheckBox checkBox = new CheckBox(AnadirSintomaActivity.this);
        Integer codSintoma = sintoma.getCodSintoma();
        checkBoxMap.put(codSintoma, checkBox);

        // Configurar los atributos del CheckBox
        checkBox.setTag(codSintoma);

        Boolean isSelected = savedSelections.get(codSintoma);
        if (isSelected != null) {
            checkBox.setChecked(isSelected);
        }

        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            sintoma.setSelected(isChecked);
            savedSelections.put(codSintoma, isChecked);

            if (isChecked) {
                sintomasSeleccionados.add(sintoma);
            } else {
                sintomasSeleccionados.remove(sintoma);
            }

            Log.d("MiApp", "Síntoma " + codSintoma + " isChecked: " + isChecked);
        });

        LinearLayout.LayoutParams checkBoxParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        checkBoxParams.leftMargin = getResources().getDimensionPixelSize(R.dimen.checkbox_left_margin);
        checkBox.setLayoutParams(checkBoxParams);

        TextView textView = new TextView(AnadirSintomaActivity.this);
        textView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        textView.setText(sintoma.getNombreSintoma());

        LinearLayout.LayoutParams textViewParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        textViewParams.leftMargin = getResources().getDimensionPixelSize(R.dimen.textview_left_margin);
        textView.setLayoutParams(textViewParams);

        textView.setText(sintoma.getNombreSintoma());
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        textView.setTextColor(ContextCompat.getColor(AnadirSintomaActivity.this, R.color.black));
        textView.setLinkTextColor(ContextCompat.getColor(AnadirSintomaActivity.this, R.color.verdeTextos));
        textView.setTypeface(ResourcesCompat.getFont(AnadirSintomaActivity.this, R.font.inter_regular));

        linearLayout.addView(checkBox);
        linearLayout.addView(textView);

        lineargrande.addView(linearLayout);
    }



    private void buscarSintomas(String searchText) {
        // Filtrar la lista de síntomas basada en el texto de búsqueda
        List<Sintoma> sintomasFiltrados = new ArrayList<>();
        for (Sintoma sintoma : sintomas) {
            if (sintoma.getNombreSintoma().toLowerCase().contains(searchText.toLowerCase())) {
                sintomasFiltrados.add(sintoma);
            }
        }

        // Limpiar la vista antes de agregar los checkboxes filtrados
        lineargrande.removeAllViews();

        for (Sintoma sintoma : sintomasFiltrados) {
            agregarCheckBoxYSintoma(sintoma);
        }

        // Mostrar u ocultar mensaje de no encontrado
        if (sintomasFiltrados.isEmpty()) {
            mostrarNoEncontrado();
        } else {
            ocultarNoEncontrado();
        }
    }


    private void mostrarNoEncontrado() {
        TextView textViewNoEncontrado = findViewById(R.id.textView_no_encontrado);
        textViewNoEncontrado.setVisibility(View.VISIBLE); // Mostrar el mensaje
    }

    private void ocultarNoEncontrado() {
        TextView textViewNoEncontrado = findViewById(R.id.textView_no_encontrado);
        textViewNoEncontrado.setVisibility(View.GONE); // Ocultar el mensaje
    }

    private boolean isSintomaSelected(int codSintoma) {
        CheckBox checkBox = checkBoxMap.get(codSintoma);
        if (checkBox != null) {
            return checkBox.isChecked();
        }
        return false;
    }




    private void mostrarError() {
        Toast.makeText(this, "Error al obtener los síntomas", Toast.LENGTH_SHORT).show();
    }



    private void popupCambiarFechaHora() {
        View popupView = getLayoutInflater().inflate(R.layout.n76_1_pop_up_cambiar_fecha, null);

        // Acceder al TextView con id "titulo"
        TextView tituloTextView = popupView.findViewById(R.id.titulo);

        // Actualizar el texto del TextView
        tituloTextView.setText("¿Qué día y hora presentó los síntomas seleccionados?");

        // Accede al TextView con id "aceptar"
        TextView aceptarTextView = popupView.findViewById(R.id.aceptar);

        // Actualiza el texto del TextView
        aceptarTextView.setText("GUARDAR");


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

        TimePicker timePicker = popupView.findViewById(R.id.timePicker);
        TextView cancelar = popupView.findViewById(R.id.cancelar);


        // Obtener las referencias a las vistas dentro del popup
        TextView textFecha = popupView.findViewById(R.id.textFecha);
        ImageView imageMayor = popupView.findViewById(R.id.imageMayor);
        ImageView imageMenor = popupView.findViewById(R.id.imageMenor);

        // Configurar la fecha seleccionada como la fecha actual
        selectedDate = Calendar.getInstance();
        updateDateTextView(textFecha);

        // Obtener la fecha actual
        Calendar currentDate = Calendar.getInstance();

        // Comparar con la fecha seleccionada
        if (selectedDate.get(Calendar.YEAR) == currentDate.get(Calendar.YEAR) &&
                selectedDate.get(Calendar.MONTH) == currentDate.get(Calendar.MONTH) &&
                selectedDate.get(Calendar.DAY_OF_MONTH) == currentDate.get(Calendar.DAY_OF_MONTH)) {
            // Si es la fecha actual, ajustar la opacidad de la flecha
            imageMayor.setAlpha(0.5f); // Cambiar alpha según sea necesario
        } else {
            // Si no es la fecha actual, dejar la opacidad por defecto
            imageMayor.setAlpha(1.0f);
        }

        // Cambiar la opacidad de la imagenMayor si la fecha seleccionada no es la fecha actual
        if (!selectedDate.equals(currentDate)) {
            imageMayor.setAlpha(0.5f); // Cambiar alpha según sea necesario
        }

        // Configurar la lógica para retroceder y avanzar en las fechas
        imageMenor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Restar un día a la fecha seleccionada
                selectedDate.add(Calendar.DAY_OF_MONTH, -1);
                updateDateTextView(textFecha);
            }
        });

        imageMayor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Obtener la fecha actual
                Calendar currentDate = Calendar.getInstance();

                // Comparar con la fecha seleccionada
                if (selectedDate.before(currentDate) ||
                        (selectedDate.get(Calendar.YEAR) == currentDate.get(Calendar.YEAR) &&
                                selectedDate.get(Calendar.MONTH) == currentDate.get(Calendar.MONTH) &&
                                selectedDate.get(Calendar.DAY_OF_MONTH) < currentDate.get(Calendar.DAY_OF_MONTH))) {

                    selectedDate.add(Calendar.DAY_OF_MONTH, 1); // Avanzar un día
                    updateDateTextView(textFecha);

                    if (selectedDate.after(currentDate)) {
                        // Si la fecha seleccionada es después de la fecha actual,
                        // ajustarla a la fecha actual
                        selectedDate = (Calendar) currentDate.clone();
                        updateDateTextView(textFecha);
                    }

                    // Cambiar la opacidad de la imagenMayor si la fecha seleccionada es igual a la fecha actual
                    if (selectedDate.get(Calendar.YEAR) == currentDate.get(Calendar.YEAR) &&
                            selectedDate.get(Calendar.MONTH) == currentDate.get(Calendar.MONTH) &&
                            selectedDate.get(Calendar.DAY_OF_MONTH) == currentDate.get(Calendar.DAY_OF_MONTH)) {
                        imageMayor.setAlpha(0.5f); // Cambiar alpha según sea necesario
                    } else {
                        imageMayor.setAlpha(1.0f); // Restaurar la opacidad por defecto
                    }
                }
            }
        });


        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ocultar el PopupWindow
                popupWindow.dismiss();

                // Ocultar el fondo oscurecido
                dimView.setVisibility(View.GONE);
            }
        });

        aceptarTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (calendarioSeleccionado != null) {
                    // Obtener los valores de fecha y hora seleccionados
                    int hour = timePicker.getCurrentHour();
                    int minute = timePicker.getCurrentMinute();

                    int year = selectedDate.get(Calendar.YEAR);
                    int month = selectedDate.get(Calendar.MONTH) + 1;
                    int day = selectedDate.get(Calendar.DAY_OF_MONTH);

                    // Crear un objeto LocalDateTime
                    LocalDateTime selectedLocalDateTime = LocalDateTime.of(year, month, day, hour, minute);

                    Log.d("MiApp", "Tamaño de la lista de síntomas seleccionados: " + sintomasSeleccionados.size());

                    // Crear una lista para almacenar los objetos CalendarioSintoma
                    List<CalendarioSintoma> calendarioSintomas = new ArrayList<>();

                    // Iterar sobre los síntomas seleccionados y crear objetos CalendarioSintoma
                    for (Sintoma sintoma : sintomasSeleccionados) {
                        if (isSintomaSelected(sintoma.getCodSintoma())) {
                            Log.d("MiApp", "Creando CalendarioSintoma para síntoma " + sintoma.getCodSintoma());
                            CalendarioSintoma nuevoCalendarioSintoma = new CalendarioSintoma();


                            // Establecer los valores para el nuevo CalendarioSintoma
                            nuevoCalendarioSintoma.setFechaCalendarioSintoma(selectedLocalDateTime);
                            nuevoCalendarioSintoma.setFechaFinVigenciaCS(null);
                            nuevoCalendarioSintoma.setSintoma(sintoma);
                            nuevoCalendarioSintoma.setCalendario(calendarioSeleccionado);

                            // Agregar el ID a la lista
                            calendarioSintomaIds.add(nuevoCalendarioSintoma.getCodCalendarioSintoma());

                            // Agregar el nuevo CalendarioSintoma a la lista
                            calendarioSintomas.add(nuevoCalendarioSintoma);
                        } else {
                            Log.d("MiApp", "Síntoma " + sintoma.getCodSintoma() + " no está seleccionado.");
                        }
                    }

                    // Llamada a la API para guardar los nuevos CalendarioSintoma
                    for (CalendarioSintoma nuevoCalendarioSintoma : calendarioSintomas) {
                        Call<CalendarioSintoma> call1 = calendarioSintomaApi.saveCalendarioSintoma(nuevoCalendarioSintoma);

                        call1.enqueue(new Callback<CalendarioSintoma>() {
                            @Override
                            public void onResponse(Call<CalendarioSintoma> call1, Response<CalendarioSintoma> response) {
                                if (response.isSuccessful() && response.body() != null) {
                                    // Éxito: Manejar el éxito de la creación del CalendarioSintoma
                                } else {
                                    // Fallo: Manejar la respuesta no exitosa de la API
                                    Toast.makeText(AnadirSintomaActivity.this, "Error al crear los CalendarioSintoma. Por favor, inténtalo nuevamente.", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<CalendarioSintoma> call1, Throwable t) {
                                // Fallo: Manejar el fallo de la llamada API
                                Toast.makeText(AnadirSintomaActivity.this, "Error al crear los CalendarioSintoma. Por favor, inténtalo nuevamente.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    // Crear un Intent y pasar datos
                    Intent intent = new Intent(AnadirSintomaActivity.this, AgregarSeguimientoActivity.class);
                    intent.putExtra("selectedDateTime", selectedLocalDateTime);
                    intent.putExtra("calendarioSeleccionadoid", codCalendario);
                    startActivity(intent);

                    // Cerrar el popup y ocultar el fondo oscurecido
                    popupWindow.dismiss();
                    dimView.setVisibility(View.GONE);
                } else {
                    Log.d("MiApp", "Calendario seleccionado es nulo.");
                }
            }
        });


        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                dimView.setVisibility(View.GONE);
            }
        });
    }


    private void updateDateTextView(TextView textFecha) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, d MMM", Locale.getDefault());
        String formattedDate = dateFormat.format(selectedDate.getTime());
        textFecha.setText(formattedDate);
    }
    private String getMonthName(int month) {
        String[] months = getResources().getStringArray(R.array.months_array);
        return months[month];
    }

}

