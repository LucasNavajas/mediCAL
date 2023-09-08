package com.example.medical;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.medical.model.Calendario;
import com.example.medical.model.CalendarioMedicion;
import com.example.medical.model.Medicion;
import com.example.medical.retrofit.CalendarioApi;
import com.example.medical.retrofit.CalendarioMedicionApi;
import com.example.medical.retrofit.MedicionApi;
import com.example.medical.retrofit.RetrofitService;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EstablecerMedicionActivity extends AppCompatActivity {

    private Calendar selectedDate = Calendar.getInstance();
    private int lastCalendarioMedicionCode = 0;

    private LocalDateTime fechaSeleccionada; // Declarar la variable a nivel de clase

    private int codCalendario;
    private CalendarioApi calendarioApi;
    private MedicionApi medicionApi;
    private CalendarioMedicionApi calendarioMedicionApi;
    private Calendario calendarioSeleccionado;
    private Medicion medicionSeleccionada;
    private int codMedicion;
    private boolean isFormatting = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.n76_0_establecer_medicion);


        RetrofitService retrofitService = new RetrofitService();

        // Crear una instancia de la interfaz de la API utilizando Retrofit
        calendarioMedicionApi = retrofitService.getRetrofit().create(CalendarioMedicionApi.class);

        Intent intent1 = getIntent();
        codCalendario = intent1.getIntExtra("calendarioSeleccionadoid", 0);
        Log.d("MiApp", "codCalendario en EstablecerMedicionActivity: " + codCalendario);

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

        LinearLayout layoutHora = findViewById(R.id.layout_hora);
        layoutHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupCambiarFechaHora();
            }
        });

        // Configura el clic de la flecha hacia atrás
        findViewById(R.id.boton_volver).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Vuelve a la pantalla anterior (Puede ser tu pantalla 73)
                onBackPressed();
            }
        });

        EditText textoLineaEditText = findViewById(R.id.texto_linea);
        textoLineaEditText.addTextChangedListener(new TextWatcher() {
            private boolean isFormatting = false;
            private DecimalFormat decimalFormat = new DecimalFormat("000.00");
            private DecimalFormatSymbols symbols = decimalFormat.getDecimalFormatSymbols();
            private char zeroDigit = symbols.getZeroDigit();
            private char decimalSeparator = symbols.getDecimalSeparator();

            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
                // No action needed before text changed
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                // No action needed as the text is changing
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (isFormatting) {
                    return;
                }

                isFormatting = true;

                String inputText = editable.toString();
                if (!inputText.isEmpty()) {
                    String strippedText = inputText.replaceAll("[^0-9]", "");

                    if (strippedText.isEmpty()) {
                        textoLineaEditText.setText("");
                        textoLineaEditText.setSelection(0);
                    } else {
                        int intValue = Integer.parseInt(strippedText);
                        double doubleValue = intValue / 100.0;

                        String formattedText = decimalFormat.format(doubleValue);
                        char zeroDigitFormatted = decimalFormat.format(0).charAt(0);
                        formattedText = formattedText.replace(zeroDigitFormatted, zeroDigit);

                        textoLineaEditText.setText(formattedText);
                        textoLineaEditText.setSelection(formattedText.length());
                    }
                }

                isFormatting = false;
            }
        });



        // Obtener los datos pasados desde la actividad anterior
        Intent intent = getIntent();
        if (intent != null) {
            String nombreMedicion = intent.getStringExtra("nombreMedicion");
            String unidadMedida = intent.getStringExtra("unidadMedida");


            // Actualizar el nombre de la medición en el TextView correspondiente
            TextView nombreMedicionTextView = findViewById(R.id.texto_editar);
            nombreMedicionTextView.setText(nombreMedicion);

            // Actualizar la unidad de medida en el TextView correspondiente
            TextView unidadMedidaTextView = findViewById(R.id.texto_unidad);
            // Actualizar la unidad de medida en el TextView correspondiente
            unidadMedidaTextView.setText("(" + unidadMedida + ")");

            // Crear una instancia de la interfaz de la API utilizando Retrofit
            medicionApi = retrofitService.getRetrofit().create(MedicionApi.class);

            // Obtener el código de la medición desde el Intent o donde sea necesario
            codMedicion = getIntent().getIntExtra("codMedicion", 0);

            // Hacer la llamada a la API para obtener la medición seleccionada
            Call<Medicion> call3 = medicionApi.getByCodMedicion(codMedicion);

            call3.enqueue(new Callback<Medicion>() {
                @Override
                public void onResponse(Call<Medicion> call3, Response<Medicion> response) {
                    if (response.isSuccessful()) {
                        medicionSeleccionada = response.body();
                        if (medicionSeleccionada != null) {
                            Log.d("MiApp", "Medición seleccionada encontrada: " + medicionSeleccionada.getCodMedicion());
                            // Aquí puedes hacer lo que necesites con la medición seleccionada
                        } else {
                            Log.d("MiApp", "No se encontró la Medición con codMedicion: " + codMedicion);
                        }
                    } else {
                        Log.d("MiApp", "Error en la solicitud: " + response.message());
                    }
                }

                @Override
                public void onFailure(Call<Medicion> call3, Throwable t) {
                    Log.e("MiApp", "Error en la solicitud: " + t.getMessage());
                }
            });
        }



        // Obtener la fecha y hora actual
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM", Locale.getDefault());
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        Date currentDate = new Date();

        // Configurar los TextView con la fecha y hora actual
        TextView fechaTextView = findViewById(R.id.texto_fecha);
        fechaTextView.setText(dateFormat.format(currentDate));

        TextView horaTextView = findViewById(R.id.texto_hora);
        horaTextView.setText(timeFormat.format(currentDate));


        Button buttonAgregar = findViewById(R.id.button_siguiente);

        buttonAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText textoLineaEditText = findViewById(R.id.texto_linea);
                String inputText = textoLineaEditText.getText().toString();

                String formattedNumber = formatNumberWithZeros(inputText);

                try {
                    float inputValue = Float.parseFloat(formattedNumber);

                    if (inputValue == 0) {
                        Toast.makeText(EstablecerMedicionActivity.this, "Ingrese un valor mayor a 0", Toast.LENGTH_SHORT).show();
                    } else {
                        CalendarioMedicion nuevaMedicion = new CalendarioMedicion();

                        if (fechaSeleccionada == null) {
                            fechaSeleccionada = LocalDateTime.now();
                        }
                        nuevaMedicion.setFechaCalendarioMedicion(fechaSeleccionada);
                        nuevaMedicion.setFechaFinVigenciaCM(null);
                        nuevaMedicion.setValorCalendarioMedicion(inputValue);

                        nuevaMedicion.setMedicion(medicionSeleccionada);
                        nuevaMedicion.setCalendario(calendarioSeleccionado);

                        Call<CalendarioMedicion> call = calendarioMedicionApi.saveCalendarioMedicion(nuevaMedicion);
                        call.enqueue(new Callback<CalendarioMedicion>() {
                            @Override
                            public void onResponse(Call<CalendarioMedicion> call, Response<CalendarioMedicion> response) {
                                if (response.isSuccessful()) {
                                    Log.d("MiApp", "CalendarioMedicion guardado exitosamente");
                                } else {
                                    Log.d("MiApp", "Error en la solicitud: " + response.message());
                                }
                            }

                            @Override
                            public void onFailure(Call<CalendarioMedicion> call, Throwable t) {
                                Log.e("MiApp", "Error en la solicitud: " + t.getMessage());
                            }
                        });

                        Intent intent = new Intent(EstablecerMedicionActivity.this, AgregarSeguimientoActivity.class);
                        intent.putExtra("calendarioSeleccionadoid", codCalendario);
                        startActivity(intent);
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(EstablecerMedicionActivity.this, "Ingrese un número válido", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

    private String formatNumberWithZeros(String input) {
        String[] parts = input.split("[,.]");
        String wholePart = parts[0];
        String decimalPart = parts.length > 1 ? parts[1] : "";

        while (wholePart.length() < 3) {
            wholePart = "0" + wholePart;
        }

        while (decimalPart.length() < 2) {
            decimalPart += "0";
        }

        return wholePart + "." + decimalPart;
    }



    // Dentro de EstablecerMedicionActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            String selectedDate = data.getStringExtra("selectedDate");
            String selectedTime = data.getStringExtra("selectedTime");

            Log.d("MiApp", "selectedDate: " + selectedDate);
            Log.d("MiApp", "selectedTime: " + selectedTime);

            // Actualizar los TextView con la fecha y hora seleccionadas
            TextView fechaTextView = findViewById(R.id.texto_fecha);
            fechaTextView.setText(selectedDate);

            TextView horaTextView = findViewById(R.id.texto_hora);
            horaTextView.setText(selectedTime);

            // Parsear la fecha y la hora seleccionadas
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

            try {
                Date parsedDate = dateFormat.parse(selectedDate);
                Date parsedTime = timeFormat.parse(selectedTime);

                // Crear una instancia de LocalDateTime usando la fecha y hora seleccionadas
                LocalDateTime dateTime = LocalDateTime.ofInstant(parsedDate.toInstant(), ZoneId.systemDefault())
                        .withHour(parsedTime.getHours())
                        .withMinute(parsedTime.getMinutes());

                fechaSeleccionada = dateTime;
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
    }

    private void popupCambiarFechaHora() {
        View popupView = getLayoutInflater().inflate(R.layout.n76_1_pop_up_cambiar_fecha, null);

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
        TextView aceptarTextView = popupView.findViewById(R.id.aceptar);

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
                // Obtener la fecha y hora actual
                int hour = timePicker.getCurrentHour();
                int minute = timePicker.getCurrentMinute();

                // Obtener la fecha y hora seleccionadas
                int year = selectedDate.get(Calendar.YEAR);
                int month = selectedDate.get(Calendar.MONTH);
                int day = selectedDate.get(Calendar.DAY_OF_MONTH);

                // Crear una instancia de LocalDateTime usando la fecha y hora seleccionadas
                fechaSeleccionada = LocalDateTime.of(year, month + 1, day, hour, minute); // Aquí asignamos el valor

                // Formatear la fecha y la hora como cadenas
                String formattedDate = day + " " + getMonthName(month) + ", " + year;
                String formattedTime = hour + ":" + minute;

                // Enviar la fecha y hora formateadas a EstablecerMedicionActivity
                Intent resultIntent = new Intent();
                resultIntent.putExtra("selectedDate", formattedDate);
                resultIntent.putExtra("selectedTime", formattedTime);
                setResult(RESULT_OK, resultIntent);

                // Actualizar manualmente los TextView con los nuevos datos
                TextView fechaTextView = findViewById(R.id.texto_fecha);
                fechaTextView.setText(formattedDate);

                TextView horaTextView = findViewById(R.id.texto_hora);
                horaTextView.setText(formattedTime);

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

