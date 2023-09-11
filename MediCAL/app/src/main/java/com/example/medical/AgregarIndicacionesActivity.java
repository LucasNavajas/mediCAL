package com.example.medical;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.medical.model.Instruccion;
import com.example.medical.model.Recordatorio;
import com.example.medical.retrofit.InstruccionApi;
import com.example.medical.retrofit.RecordatorioApi;
import com.example.medical.retrofit.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AgregarIndicacionesActivity extends AppCompatActivity {
    private RetrofitService retrofitService = new RetrofitService();
    private RecordatorioApi recordatorioApi = retrofitService.getRetrofit().create(RecordatorioApi.class);
    private InstruccionApi instruccionApi = retrofitService.getRetrofit().create(InstruccionApi.class);
    private EditText indicaciones;
    private ImageView botonVolver;
    private TextView contadorLetras;
    private Recordatorio recordatorio;
    private Instruccion instruccion = new Instruccion();
    private Button establecer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.n52_indicar_indicacion_extra);//cambiar esta linea por el nombre del layout a probar
        TextView nombreMedicamento = findViewById(R.id.nombreMedicamento);
        nombreMedicamento.setText(getIntent().getStringExtra("nombreMedicamento"));
        inicializarVariables();

        botonVolver.setOnClickListener(view ->{onBackPressed();});

        indicaciones.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                String newText = editable.toString();
                contadorLetras.setText(newText.length()+"/300");
            }
        });

    }

    private void inicializarVariables() {
        indicaciones = findViewById(R.id.editText);
        botonVolver = findViewById(R.id.boton_volver);
        contadorLetras = findViewById(R.id.characterCount);
        establecer = findViewById(R.id.button_establecer);

        recordatorioApi.getByCodRecordatorio(getIntent().getIntExtra("codRecordatorio", 0)).enqueue(new Callback<Recordatorio>() {
            @Override
            public void onResponse(Call<Recordatorio> call, Response<Recordatorio> response) {
                recordatorio = response.body();
                establecer.setOnClickListener(view ->{
                    if(indicaciones.getText().toString().length()>300){
                        contadorLetras.setTextColor(ContextCompat.getColor(AgregarIndicacionesActivity.this, R.color.rojoError));
                    }
                    else{
                        instruccion.setNombreInstruccion(getIntent().getStringExtra("instrucciones"));
                        instruccion.setDescInstruccion(indicaciones.getText().toString());
                        instruccionApi.save(instruccion).enqueue(new Callback<Instruccion>() {
                            @Override
                            public void onResponse(Call<Instruccion> call, Response<Instruccion> response) {
                                recordatorio.setInstruccion(response.body());
                                recordatorioApi.modificarRecordatorio(recordatorio).enqueue(new Callback<Recordatorio>() {
                                    @Override
                                    public void onResponse(Call<Recordatorio> call, Response<Recordatorio> response) {
                                        Intent intent = new Intent(AgregarIndicacionesActivity.this, AgregarDatosObligatoriosActivity.class);
                                        intent.putExtra("codRecordatorio", response.body().getCodRecordatorio());
                                        intent.putExtra("presentacionMedId", getIntent().getIntExtra("presentacionMedId", 0));
                                        intent.putExtra("nombreMedicamento", getIntent().getStringExtra("nombreMedicamento"));
                                        startActivity(intent);
                                    }

                                    @Override
                                    public void onFailure(Call<Recordatorio> call, Throwable t) {
                                        Toast.makeText(AgregarIndicacionesActivity.this, "Error al modificar el recordatorio", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                            @Override
                            public void onFailure(Call<Instruccion> call, Throwable t) {
                                Toast.makeText(AgregarIndicacionesActivity.this, "Error al crear instruccion", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }

            @Override
            public void onFailure(Call<Recordatorio> call, Throwable t) {
                Toast.makeText(AgregarIndicacionesActivity.this, "Error al cargar el recordatorio", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
