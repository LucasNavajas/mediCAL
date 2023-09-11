package com.example.medical;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.medical.model.Frecuencia;
import com.example.medical.model.Recordatorio;
import com.example.medical.retrofit.FrecuenciaApi;
import com.example.medical.retrofit.RecordatorioApi;
import com.example.medical.retrofit.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FrecuenciaxmesesActivity extends AppCompatActivity {
    private RetrofitService retrofitService = new RetrofitService();
    private RecordatorioApi recordatorioApi = retrofitService.getRetrofit().create(RecordatorioApi.class);
    private FrecuenciaApi frecuenciaApi = retrofitService.getRetrofit().create(FrecuenciaApi.class);

    private ImageView botonVolver;
    private NumberPicker numberPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.n43_cada_x_meses);
        TextView nombreMedicamento = findViewById(R.id.nombreMedicamento);
        nombreMedicamento.setText(getIntent().getStringExtra("nombreMedicamento"));

        botonVolver = findViewById(R.id.boton_volver);
        numberPicker = findViewById(R.id.number_picker);

        // Configura los valores mínimo y máximo
        numberPicker.setMinValue(1); // Valor mínimo
        numberPicker.setMaxValue(12); // Valor máximo

        // Recibir los valores codAdmin y codPresen de la actividad anterior
        int codAdmin = getIntent().getIntExtra("administracionMedId", 0);
        int codPresen = getIntent().getIntExtra("presentacionMedId", 0);

        // Mostrar los valores recibidos en un Toast
       /* String mensaje = "Codigo adm: " + codAdmin + " Codigo presen: " + codPresen;
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();*/

        botonVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed(); // Volver a la actividad anterior
            }
        });
        Button buttonSiguiente = findViewById(R.id.button_siguiente);

        buttonSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Iniciar la actividad SeleccionarHorarioRecordatorioActivity
                Intent intent = new Intent(FrecuenciaxmesesActivity.this, SeleccionarHorarioRecordatorioActivity.class);
                Frecuencia frecuencia = new Frecuencia();
                frecuencia.setNombreFrecuencia("Cada "+ numberPicker.getValue() + " meses");
                frecuencia.setCantidadFrecuencia(numberPicker.getValue()*24*30);
                recordatorioApi.getByCodRecordatorio(getIntent().getIntExtra("codRecordatorio", 0)).enqueue(new Callback<Recordatorio>() {
                    @Override
                    public void onResponse(Call<Recordatorio> call, Response<Recordatorio> response) {
                        Recordatorio recordatorio = response.body();
                        frecuenciaApi.save(frecuencia).enqueue(new Callback<Frecuencia>() {
                            @Override
                            public void onResponse(Call<Frecuencia> call, Response<Frecuencia> response) {
                                recordatorio.setFrecuencia(response.body());
                                recordatorioApi.modificarRecordatorio(recordatorio).enqueue(new Callback<Recordatorio>() {
                                    @Override
                                    public void onResponse(Call<Recordatorio> call, Response<Recordatorio> response) {
                                        intent.putExtra("administracionMedId", codAdmin);
                                        intent.putExtra("presentacionMedId", codPresen);
                                        intent.putExtra("codRecordatorio", recordatorio.getCodRecordatorio());
                                        intent.putExtra("nombreMedicamento", getIntent().getStringExtra("nombreMedicamento"));
                                        startActivity(intent);
                                    }

                                    @Override
                                    public void onFailure(Call<Recordatorio> call, Throwable t) {
                                        Toast.makeText(FrecuenciaxmesesActivity.this, "Error al agregar frecuencia al recordatorio", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                            @Override
                            public void onFailure(Call<Frecuencia> call, Throwable t) {
                                Toast.makeText(FrecuenciaxmesesActivity.this, "Error al crear frecuencia", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }

                    @Override
                    public void onFailure(Call<Recordatorio> call, Throwable t) {
                        Toast.makeText(FrecuenciaxmesesActivity.this, "Error al obtener el recordatorio", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed(); // Volver a la actividad anterior
    }
}
