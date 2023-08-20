package com.example.medical;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.example.medical.adapter.MedicionAdapter;
import android.widget.Toast;

import com.example.medical.model.Medicion;
import com.example.medical.retrofit.MedicionApi;
import com.example.medical.retrofit.RetrofitService;
import android.widget.ArrayAdapter;

import java.util.List;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnadirMedicionActivity extends AppCompatActivity {

    private MedicionApi medicionApi;
    private ListView listaMedicionesListView;
    private ListView listaTodasMedicionesListView;

    private TextView noEncontradoTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.n75_mediciones);

        RetrofitService retrofitService = new RetrofitService();
        medicionApi = retrofitService.getRetrofit().create(MedicionApi.class);

        listaMedicionesListView = findViewById(R.id.lista_mediciones_comunes);

        MedicionAdapter adapter = new MedicionAdapter(this, new ArrayList<>());
        listaMedicionesListView.setAdapter(adapter);

        // Configura el clic de la flecha hacia atrás
        findViewById(R.id.boton_volver).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Vuelve a la pantalla anterior (Puede ser tu pantalla 73)
                onBackPressed();
            }
        });

        // Después de configurar el adaptador para las mediciones comunes
        MedicionAdapter todasMedicionesAdapter = new MedicionAdapter(this, new ArrayList<>());

        listaTodasMedicionesListView = findViewById(R.id.lista_todas_mediciones);
        listaTodasMedicionesListView.setAdapter(todasMedicionesAdapter);


        noEncontradoTextView = findViewById(R.id.textView_no_encontrado);

        AutoCompleteTextView autoCompleteTextView = findViewById(R.id.editText_buscar);
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

                    MedicionAdapter adapter = (MedicionAdapter) listaMedicionesListView.getAdapter();
                    adapter.clear(); // Limpia la lista actual antes de agregar las nuevas mediciones

                    boolean medicionEncontrada = false;

                    for (Medicion medicion : mediciones) {
                        if (medicion.getNombreMedicion().toLowerCase().contains(searchText.toLowerCase())) {
                            adapter.addMedicion(medicion); // Agrega la medicion al adaptador
                            medicionEncontrada = true; // Marca la variable como verdadera
                        }
                    }

                    // Configurar el adaptador para todas las mediciones aquí
                    MedicionAdapter todasMedicionesAdapter = (MedicionAdapter) listaTodasMedicionesListView.getAdapter();
                    todasMedicionesAdapter.clear(); // Limpia la lista actual antes de agregar las nuevas mediciones

                    for (Medicion medicion : mediciones) {
                        todasMedicionesAdapter.addMedicion(medicion); // Agregar todas las mediciones al adaptador
                    }

                    if (!medicionEncontrada) {
                        mostrarNoEncontrado();
                    } else {
                        ocultarNoEncontrado();
                    }
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


    private void agregarNombreMedicion(String nombreMedicion) {
        MedicionAdapter adapter = (MedicionAdapter) listaMedicionesListView.getAdapter();
        if (adapter != null) {
            Medicion medicion = new Medicion(); // Crea una nueva instancia de Medicion con el nombre
            adapter.add(medicion); // Agrega la medicion al adaptador
        }
    }



    private void mostrarNoEncontrado() {
        noEncontradoTextView.setVisibility(View.VISIBLE);
    }

    private void ocultarNoEncontrado() {
        noEncontradoTextView.setVisibility(View.GONE);
    }

    private void mostrarError() {
        Toast.makeText(this, "Error al obtener las mediciones", Toast.LENGTH_SHORT).show();
    }

}
