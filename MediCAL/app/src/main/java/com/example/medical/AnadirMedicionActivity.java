package com.example.medical;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medical.adapter.MedicionComunAdapter;
import com.example.medical.adapter.TodasMedicionesAdapter;
import com.example.medical.model.Calendario;
import com.example.medical.model.Medicion;
import com.example.medical.retrofit.CalendarioApi;
import com.example.medical.retrofit.MedicionApi;
import com.example.medical.retrofit.RetrofitService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnadirMedicionActivity extends AppCompatActivity {

    private MedicionApi medicionApi;

    private ListView listaMedicionesListView;
    private ListView listaTodasMedicionesListView;
    private ArrayAdapter<String> autoCompleteAdapter;

    private List<Medicion> mediciones = new ArrayList<>();


    private TextView noEncontradoTextView;

    private AutoCompleteTextView autoCompleteTextView;
    private int codCalendario ;

    private static final List<String> COMMON_MEASUREMENT_NAMES = Arrays.asList(
            "Peso", "Frecuencia respiratoria", "Presion arterial", "Glucosa en sangre"
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.n75_mediciones);

        RetrofitService retrofitService = new RetrofitService();
        medicionApi = retrofitService.getRetrofit().create(MedicionApi.class);

        // Recuperar el objeto calendarioSeleccionado del Intent
        Intent intent1 = getIntent();
        codCalendario = intent1.getIntExtra("calendarioSeleccionadoid", 0);
        Log.d("MiApp", "codCalendario en AnadirMedicionActivity: " + codCalendario);


        listaMedicionesListView = findViewById(R.id.lista_mediciones_comunes);

        MedicionComunAdapter commonMedicionesAdapter = new MedicionComunAdapter(this);
        commonMedicionesAdapter.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Medicion medicionSeleccionada = (Medicion) view.getTag();
                Intent intent = new Intent(AnadirMedicionActivity.this, EstablecerMedicionActivity.class);
                intent.putExtra("nombreMedicion", medicionSeleccionada.getNombreMedicion());
                intent.putExtra("codMedicion",medicionSeleccionada.getCodMedicion());
                intent.putExtra("unidadMedida", medicionSeleccionada.getUnidadMedidaMedicion());
                intent.putExtra("calendarioSeleccionadoid", codCalendario);
                startActivity(intent);
            }
        });
        listaMedicionesListView.setAdapter(commonMedicionesAdapter);

        // Configura el adaptador para "Todas las mediciones"
        listaTodasMedicionesListView = findViewById(R.id.lista_todas_mediciones);
        // Crear una instancia de TodasMedicionesAdapter y pasarle el valor de codCalendario
        TodasMedicionesAdapter todasMedicionesAdapter = new TodasMedicionesAdapter(this, new ArrayList<>(), codCalendario);
        listaTodasMedicionesListView.setAdapter(todasMedicionesAdapter);



        // Llama al método buscarMediciones para cargar todas las mediciones
        buscarMediciones("");

        // Configura el clic de la flecha hacia atrás
        findViewById(R.id.boton_volver).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        noEncontradoTextView = findViewById(R.id.textView_no_encontrado);

        autoCompleteTextView = findViewById(R.id.editText_buscar);

        // Inicializa el adaptador de autocompletado
        autoCompleteAdapter = new ArrayAdapter<>(AnadirMedicionActivity.this, android.R.layout.simple_dropdown_item_1line);
        autoCompleteTextView.setAdapter(autoCompleteAdapter);

        // Dentro del listener del autocompletado en AnadirMedicionActivity
        autoCompleteTextView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedMedicionName = autoCompleteAdapter.getItem(position);
            Medicion selectedMedicion = findMedicionByName(selectedMedicionName);


            if (selectedMedicion != null) {
                String unidadMedida = selectedMedicion.getUnidadMedidaMedicion();
                Intent intent = new Intent(AnadirMedicionActivity.this, EstablecerMedicionActivity.class);
                intent.putExtra("nombreMedicion", selectedMedicionName);
                intent.putExtra("unidadMedida", unidadMedida);
                intent.putExtra("codMedicion",selectedMedicion.getCodMedicion());
                intent.putExtra("calendarioSeleccionadoid", codCalendario);
                startActivity(intent);
            }
        });


        ImageView lupaImageView = findViewById(R.id.lupa_buscar);

        autoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String searchText = charSequence.toString().trim();
                buscarMediciones(searchText);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        lupaImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchText = autoCompleteTextView.getText().toString().trim();
                buscarMediciones(searchText);
            }
        });
    }

    private void buscarMediciones(String searchText) {
        Call<List<Medicion>> call = medicionApi.getAllMediciones();

        call.enqueue(new Callback<List<Medicion>>() {
            @Override
            public void onResponse(Call<List<Medicion>> call, Response<List<Medicion>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Medicion> mediciones = response.body();

                    List<String> suggestions = new ArrayList<>();

                    AnadirMedicionActivity.this.mediciones = mediciones; // Asignar la lista obtenida a la variable de instancia


                    for (Medicion medicion : mediciones) {
                        if (medicion.getNombreMedicion().toLowerCase().contains(searchText.toLowerCase())) {
                            suggestions.add(medicion.getNombreMedicion());
                        }
                    }

                    // Mostrar u ocultar el mensaje de no encontrado
                    if (suggestions.isEmpty()) {
                        mostrarNoEncontrado();
                    } else {
                        ocultarNoEncontrado();
                    }

                    // Actualiza las sugerencias del adaptador de autocompletado
                    autoCompleteAdapter.clear();
                    autoCompleteAdapter.addAll(suggestions);
                    autoCompleteAdapter.notifyDataSetChanged();

                    // Filtrar las mediciones comunes
                    List<Medicion> commonMediciones = new ArrayList<>();
                    for (Medicion medicion : mediciones) {
                        if (COMMON_MEASUREMENT_NAMES.contains(medicion.getNombreMedicion())) {
                            commonMediciones.add(medicion);
                        }
                    }

                    // Actualizar el adaptador de mediciones comunes
                    MedicionComunAdapter commonMedicionesAdapter = (MedicionComunAdapter) listaMedicionesListView.getAdapter();
                    commonMedicionesAdapter.setCommonMedicionesData(commonMediciones);
                    commonMedicionesAdapter.notifyDataSetChanged();

                    // Actualizar las mediciones en el adaptador de todas las mediciones
                    TodasMedicionesAdapter todasMedicionesAdapter = (TodasMedicionesAdapter) listaTodasMedicionesListView.getAdapter();
                    todasMedicionesAdapter.clear();
                    todasMedicionesAdapter.addAll(mediciones);
                    todasMedicionesAdapter.notifyDataSetChanged();
                } else {
                    mostrarError();
                }
            }

            @Override
            public void onFailure(Call<List<Medicion>> call, Throwable t) {
                mostrarError();
                Log.e("AnadirMedicionActivity", "Error en la petición HTTP: " + t.getMessage());
            }
        });
    }


    private void mostrarNoEncontrado() {
        noEncontradoTextView.setVisibility(View.VISIBLE);
        listaMedicionesListView.setVisibility(View.GONE);
        listaTodasMedicionesListView.setVisibility(View.GONE);
        findViewById(R.id.texto_medicionescomunes).setVisibility(View.GONE); // Ocultar "Mediciones más comunes"
        findViewById(R.id.texto_todasmediciones).setVisibility(View.GONE); // Ocultar "Todas las mediciones"
    }

    private void ocultarNoEncontrado() {
        noEncontradoTextView.setVisibility(View.GONE);
        listaMedicionesListView.setVisibility(View.VISIBLE);
        listaTodasMedicionesListView.setVisibility(View.VISIBLE);
        findViewById(R.id.texto_medicionescomunes).setVisibility(View.VISIBLE); // Mostrar "Mediciones más comunes"
        findViewById(R.id.texto_todasmediciones).setVisibility(View.VISIBLE); // Mostrar "Todas las mediciones"
    }

    private void mostrarError() {
        Toast.makeText(this, "Error al obtener las mediciones", Toast.LENGTH_SHORT).show();
    }

    private Medicion findMedicionByName(String nombreMedicion) {
        Medicion selectedMedicion = null;
        for (Medicion medicion : mediciones) {
            if (medicion.getNombreMedicion().equalsIgnoreCase(nombreMedicion)) {
                selectedMedicion = medicion;
                break;
            }
        }
        return selectedMedicion;
    }

}
