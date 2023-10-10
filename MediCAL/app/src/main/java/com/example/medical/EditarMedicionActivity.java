package com.example.medical;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
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
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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

    private LocalDateTime fechaSeleccionada; // Declarar la variable a nivel de clase

    private MedicionApi medicionApi;
    private Medicion medicionSeleccionada;
    private int idMedicionCal;

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
        codMedicion = intent1.getIntExtra("medicionid", 0);

        // Inicializar las APIs utilizando Retrofit
        calendarioApi = retrofitService.getRetrofit().create(CalendarioApi.class);
        calendarioMedicionApi = retrofitService.getRetrofit().create(CalendarioMedicionApi.class);

        // Obtener el calendario seleccionado
        obtenerCalendarioSeleccionado();

        // Configura el clic de la flecha hacia atrás
        findViewById(R.id.boton_volver).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Vuelve a la pantalla anterior (Puede ser tu pantalla 73)
                onBackPressed();
            }
        });
        LinearLayout layoutHora = findViewById(R.id.layout_hora);
        layoutHora.setVisibility(View.GONE);

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

        Button buttonModificar = findViewById(R.id.button_siguiente);
        buttonModificar.setText("Modificar medición");

        buttonModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText textoLineaEditText = findViewById(R.id.texto_linea);
                String inputText = textoLineaEditText.getText().toString();

                String formattedNumber = formatNumberWithZeros(inputText);

                try {
                    float newValue = Float.parseFloat(formattedNumber);

                    if (newValue == 0) {
                        Toast.makeText(EditarMedicionActivity.this, "Ingrese un valor mayor a 0", Toast.LENGTH_SHORT).show();
                    } else {
                        // Obtén el ID de la medición que deseas modificar (reemplaza con tu lógica)
                        int idMedicionAModificar = idMedicionCal;// Reemplaza con tu lógica para obtener el ID

                        CalendarioMedicion calendarioMedicion = new CalendarioMedicion();
                        calendarioMedicion.setCodCalendarioMedicion(idMedicionAModificar); // Reemplaza con el código correcto
                        calendarioMedicion.setValorCalendarioMedicion(newValue); // Reemplaza con el nuevo valor

                        Call<CalendarioMedicion> call = calendarioMedicionApi.modificarCalendarioMedicion(calendarioMedicion);

                        call.enqueue(new Callback<CalendarioMedicion>() {
                            @Override
                            public void onResponse(Call<CalendarioMedicion> call, Response<CalendarioMedicion> response) {
                                if (response.isSuccessful()) {
                                    // La modificación se realizó con éxito
                                    CalendarioMedicion modifiedCalendarioMedicion = response.body();
                                    // Maneja la respuesta según tus necesidades
                                } else {
                                    // Ocurrió un error en la solicitud HTTP
                                    // Maneja el error según tus necesidades
                                }
                            }

                            @Override
                            public void onFailure(Call<CalendarioMedicion> call, Throwable t) {
                                // Maneja el error de la solicitud HTTP
                            }
                        });


                        // Puedes mantener el código existente para iniciar la nueva actividad después de la modificación
                        Intent intent2 = new Intent(EditarMedicionActivity.this, AgregarSeguimientoActivity.class);
                        intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent2.putExtra("codUsuario", getIntent().getIntExtra("codUsuario", 0));
                        intent2.putExtra("calendarioSeleccionadoid", getIntent().getIntExtra("calendarioSeleccionadoid", 0));
                        startActivity(intent2);
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(EditarMedicionActivity.this, "Ingrese un número válido", Toast.LENGTH_SHORT).show();
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
        Call<CalendarioMedicion> call = calendarioMedicionApi.getByCodCalendarioM(codCalendarioMedicion);
        Log.d("MiApp", "Codigo medicion MasInfoMedicion: " + codCalendarioMedicion);
        call.enqueue(new Callback<CalendarioMedicion>() {

            @Override
            public void onResponse(Call<CalendarioMedicion> call, Response<CalendarioMedicion> response) {
                if (response.isSuccessful()) {
                    CalendarioMedicion calendarioMedicion = response.body();
                    Log.d("MiApp", "Fecha de calendarioMedicion: " + calendarioMedicion.getFechaCalendarioMedicion());

                    idMedicionCal = calendarioMedicion.getCodCalendarioMedicion();

                    // Actualizar el nombre de la medición en el TextView correspondiente
                    TextView nombreMedicionTextView = findViewById(R.id.texto_editar);
                    nombreMedicionTextView.setText(calendarioMedicion.getMedicion().getNombreMedicion());

                    // Obtener el nombre de la unidad desde el objeto calendarioMedicion
                    String nombreUnidad = calendarioMedicion.getMedicion().getUnidadMedidaMedicion();

                    // Actualizar el nombre de la unidad en el TextView correspondiente
                    TextView nombreUnidadTextView = findViewById(R.id.texto_unidad);
                    nombreUnidadTextView.setText("(" + nombreUnidad + ")");
                } else {
                    int statusCode = response.code();
                    Log.d("MiApp", "Código de estado HTTP: " + statusCode);
                    Log.d("MiApp", "Error en la solicitud: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<CalendarioMedicion> call, Throwable t) {
                Log.e("MiApp", "Error en la solicitud: " + t.getMessage());
            }
        });
    }

}

