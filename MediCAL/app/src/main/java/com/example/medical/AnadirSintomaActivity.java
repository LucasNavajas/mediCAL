package com.example.medical;


import com.example.medical.model.Sintoma; // Asegúrate de importar la clase Sintoma correctamente

import android.os.Bundle;
import java.time.LocalDate;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.AutoCompleteTextView;

import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import com.example.medical.model.Sintoma;
import com.example.medical.retrofit.RetrofitService;
import com.example.medical.retrofit.SintomaApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AnadirSintomaActivity extends AppCompatActivity {

    private SintomaApi sintomaApi;
    private LinearLayout lineargrande;

    // Variables para guardar los IDs generados dinámicamente
    private List<Integer> checkBoxIds = new ArrayList<>();
    private List<Integer> textViewIds = new ArrayList<>();

    private int codigoSintomaCounter = 1; // Inicializar el contador de códigos únicos
    private List<Integer> codigosSintomasExistentes = new ArrayList<>(); // Lista de códigos existentes


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.n77_anadir_sintomas);

        RetrofitService retrofitService = new RetrofitService();
        sintomaApi = retrofitService.getRetrofit().create(SintomaApi.class);

        lineargrande = findViewById(R.id.lineargrande);


        obtenerSintomasDesdeApi();


        AutoCompleteTextView autoCompleteTextView = findViewById(R.id.editText_buscar);
        ImageView lupaImageView = findViewById(R.id.lupa_buscar);

        autoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String searchText = charSequence.toString().trim();
                buscarSintomas(searchText);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        lupaImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchText = autoCompleteTextView.getText().toString().trim();
                buscarSintomas(searchText);
            }
        });

        Button buttonGuardar = findViewById(R.id.button_guardar);
        buttonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarSintomasSeleccionados();
            }
        });

    }


    private void obtenerSintomasDesdeApi() {
        Call<List<Sintoma>> call = sintomaApi.getAllSintomas();

        call.enqueue(new Callback<List<Sintoma>>() {
            @Override
            public void onResponse(Call<List<Sintoma>> call, Response<List<Sintoma>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Sintoma> sintomas = response.body();

                    for (Sintoma sintoma : sintomas) {
                        agregarCheckBoxYSintoma(sintoma); // No es necesario pasar el linearLayout aquí
                        codigosSintomasExistentes.add(sintoma.getCodSintoma()); // Agregar códigos existentes a la lista
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Sintoma>> call, Throwable t) {
                // Manejar error de la API
                Toast.makeText(AnadirSintomaActivity.this, "Error al obtener los síntomas. Por favor, inténtalo nuevamente.", Toast.LENGTH_SHORT).show();
            }

        });
    }


    private void buscarSintomas(String searchText) {
        Call<List<Sintoma>> call = sintomaApi.getAllSintomas();

        call.enqueue(new Callback<List<Sintoma>>() {
            @Override
            public void onResponse(Call<List<Sintoma>> call, Response<List<Sintoma>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Sintoma> sintomas = response.body();

                    // Limpiar la lista de checkboxes antes de agregar los nuevos
                    lineargrande.removeAllViews();

                    boolean sintomaEncontrado = false; // Variable para rastrear si se encontró un síntoma

                    for (Sintoma sintoma : sintomas) {
                        if (!sintoma.tieneFechaAlta() && sintoma.getNombreSintoma().toLowerCase().contains(searchText.toLowerCase())) {
                            agregarCheckBoxYSintoma(sintoma);
                            sintomaEncontrado = true; // Se encontró al menos un síntoma
                        }
                    }

                    if (!sintomaEncontrado) {
                        mostrarNoEncontrado(); // Mostrar mensaje solo si no se encontró ningún síntoma
                    } else {
                        ocultarNoEncontrado(); // Ocultar el mensaje si se encontró algún síntoma
                    }
                } else {
                    mostrarError();
                }
            }

            @Override
            public void onFailure(Call<List<Sintoma>> call, Throwable t) {
                mostrarError();
            }
        });
    }


    private void mostrarNoEncontrado() {
        TextView textViewNoEncontrado = findViewById(R.id.textView_no_encontrado);
        textViewNoEncontrado.setVisibility(View.VISIBLE); // Mostrar el mensaje
    }

    private void ocultarNoEncontrado() {
        TextView textViewNoEncontrado = findViewById(R.id.textView_no_encontrado);
        textViewNoEncontrado.setVisibility(View.GONE); // Ocultar el mensaje
    }



    private void agregarCheckBoxYSintoma(Sintoma sintoma) {
        LinearLayout linearLayout = new LinearLayout(AnadirSintomaActivity.this);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);

        CheckBox checkBox = new CheckBox(AnadirSintomaActivity.this);

        // Añadir margen izquierdo al CheckBox para moverlo a la derecha
        LinearLayout.LayoutParams checkBoxParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        checkBoxParams.leftMargin = getResources().getDimensionPixelSize(R.dimen.checkbox_left_margin);
        checkBox.setLayoutParams(checkBoxParams);

        int checkBoxId = View.generateViewId(); // Generar un ID único para el CheckBox
        checkBox.setId(checkBoxId);
        checkBoxIds.add(checkBoxId); // Agregar el ID a la lista

        linearLayout.addView(checkBox);

        // Crear un TextView para mostrar el nombre del síntoma
        TextView textView = new TextView(AnadirSintomaActivity.this);
        textView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        textView.setText(sintoma.getNombreSintoma());

        int textViewId = View.generateViewId(); // Generar un ID único para el TextView
        textView.setId(textViewId);
        textViewIds.add(textViewId); // Agregar el ID a la lista

        // Añadir margen izquierdo al TextView para moverlo a la derecha
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

        linearLayout.addView(textView);

        lineargrande.addView(linearLayout);
    }


    private void mostrarError() {
        Toast.makeText(this, "Error al obtener los síntomas", Toast.LENGTH_SHORT).show();
    }


    private void guardarSintomasSeleccionados() {
        List<String> sintomasSeleccionados = obtenerSintomasSeleccionados();
        LocalDate fechaAltaSintoma = obtenerFechaActual();

        for (String nombreSintoma : sintomasSeleccionados) {
            int nuevoCodigoSintoma = generarNuevoCodigoSintoma(); // Generar un nuevo código único

            Sintoma sintoma = new Sintoma();
            sintoma.setCodSintoma(nuevoCodigoSintoma);
            sintoma.setNombreSintoma(nombreSintoma);
            sintoma.setFechaAltaSintoma(fechaAltaSintoma);

            Call<Sintoma> call = sintomaApi.saveSintoma(sintoma);
            call.enqueue(new Callback<Sintoma>() {
                @Override
                public void onResponse(Call<Sintoma> call, Response<Sintoma> response) {
                    if (response.isSuccessful()) {
                        // Síntoma guardado exitosamente
                        mostrarMensajeExitoso("Síntoma guardado exitosamente");
                    } else {
                        // Manejar error de la API
                        mostrarError();
                    }
                }

                @Override
                public void onFailure(Call<Sintoma> call, Throwable t) {
                    // Manejar error de conexión
                    mostrarError();
                    t.printStackTrace(); // Agregar esta línea para imprimir la traza de excepción en el registro
                }

            });
        }
    }


    private void mostrarMensajeExitoso(String mensaje) {
        Toast.makeText(AnadirSintomaActivity.this, mensaje, Toast.LENGTH_SHORT).show();

    }

    private int generarNuevoCodigoSintoma() {
        while (codigosSintomasExistentes.contains(codigoSintomaCounter)) {
            codigoSintomaCounter++; // Incrementar el contador hasta encontrar un código único
        }
        return codigoSintomaCounter;
    }

    private List<String> obtenerSintomasSeleccionados() {
        List<String> sintomas = new ArrayList<>();

        for (int i = 0; i < lineargrande.getChildCount(); i++) {
            View view = lineargrande.getChildAt(i);
            if (view instanceof LinearLayout) {
                LinearLayout linearLayout = (LinearLayout) view;
                CheckBox checkBox = linearLayout.findViewById(checkBoxIds.get(i));

                if (checkBox.isChecked()) {
                    TextView textView = linearLayout.findViewById(textViewIds.get(i));
                    sintomas.add(textView.getText().toString());
                }
            }
        }

        return sintomas;
    }

    private LocalDate obtenerFechaActual() {
        return LocalDate.now();
    }

}
