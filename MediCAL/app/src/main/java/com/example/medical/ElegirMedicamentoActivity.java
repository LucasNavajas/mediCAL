package com.example.medical;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.medical.adapter.MedicamentoAdapter;
import com.example.medical.model.Medicamento;
import com.example.medical.retrofit.MedicamentoApi;
import com.example.medical.retrofit.RetrofitService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ElegirMedicamentoActivity extends AppCompatActivity {
    private RetrofitService retrofitService = new RetrofitService();
    private MedicamentoApi medicamentoApi = retrofitService.getRetrofit().create(MedicamentoApi.class);
    private EditText buscar;
    private List<Medicamento> medicamentosEntidades;
    private List<String> medicamentos = new ArrayList<>();
    private ListView listViewMedicamentos;
    private TextView sugerenciaBuscar;
    private ImageView botonCerrar;
    private TextView nuevoMedicamento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.n31_seleccionar_medicina);//cambiar esta linea por el nombre del layout a probar
        inicializarVariables();
        sugerenciaBuscar.setVisibility(View.VISIBLE);
        listViewMedicamentos.setVisibility(View.GONE);
        botonCerrar.setOnClickListener(view ->{onBackPressed();});
        nuevoMedicamento.setOnClickListener(view ->{
            Intent intent = new Intent(ElegirMedicamentoActivity.this, NuevoMedicamentoActivity.class);
            startActivity(intent);
        });
        buscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // No se necesita implementar
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                sugerenciaBuscar.setVisibility(View.GONE);
                String searchText = charSequence.toString().toLowerCase();
                List<String> filteredMedicamentos = new ArrayList<>();

                for (String medicamento : medicamentos) {
                    if (medicamento.toLowerCase().contains(searchText)) {
                        filteredMedicamentos.add(medicamento);
                    }
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(ElegirMedicamentoActivity.this, android.R.layout.simple_list_item_1, filteredMedicamentos);
                listViewMedicamentos.setAdapter(adapter);
                if (filteredMedicamentos.isEmpty()) {
                    listViewMedicamentos.setVisibility(View.GONE);
                } else {
                    listViewMedicamentos.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // No se necesita implementar
            }
        });

        buscar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // Hide the keyboard
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(buscar.getWindowToken(), 0);
                    return true; // Consume the event
                }
                return false;
            }
        });

        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buscar.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(buscar, InputMethodManager.SHOW_IMPLICIT);
            }
        });

    }


    private void inicializarVariables() {
        buscar = findViewById(R.id.buscar);
        sugerenciaBuscar = findViewById(R.id.text_buscar_medicamento);
        buscarMedicamentos(this);
        listViewMedicamentos = findViewById(R.id.listViewMedicamentos);
        botonCerrar = findViewById(R.id.boton_cerrar);
        nuevoMedicamento = findViewById(R.id.text_agregar_medicamento);

    }

    private void buscarMedicamentos(ElegirMedicamentoActivity elegirMedicamentoActivity) {
        medicamentoApi.getAllMedicamentosGenericos().enqueue(new Callback<List<Medicamento>>() {
            @Override
            public void onResponse(Call<List<Medicamento>> call, Response<List<Medicamento>> response) {
                medicamentosEntidades = response.body();
                for(Medicamento medicamento : medicamentosEntidades){
                    medicamentos.add(medicamento.getNombreMedicamento());
                }
                ArrayAdapter<String> adapter = new MedicamentoAdapter(elegirMedicamentoActivity, medicamentos, medicamentosEntidades);
                listViewMedicamentos.setAdapter(adapter);
                listViewMedicamentos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Medicamento medicamentoSeleccionado = medicamentosEntidades.get(position);
                        Intent intent = new Intent(ElegirMedicamentoActivity.this, ElegirAdministracionMedActivity.class);
                        intent.putExtra("codMedicamento", medicamentoSeleccionado.getCodMedicamento());
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onFailure(Call<List<Medicamento>> call, Throwable t) {
                Toast.makeText(ElegirMedicamentoActivity.this, "Error al cargar los medicamentos desde la base de datos, intente nuevamente", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
