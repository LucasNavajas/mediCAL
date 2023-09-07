package com.example.medical;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.medical.model.Recordatorio;
import com.example.medical.retrofit.RecordatorioApi;
import com.example.medical.retrofit.RetrofitService;

import java.time.LocalDateTime;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ElegirDiasActivity extends AppCompatActivity {
    private RetrofitService retrofitService = new RetrofitService();
    private RecordatorioApi recordatorioApi = retrofitService.getRetrofit().create(RecordatorioApi.class);

    private NumberPicker numberPicker;
    private ImageView botonVolver;
    private Button siguiente;
    private Recordatorio recordatorio;
    private LocalDateTime fechaInicio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.n48_indicar_n_dias);//cambiar esta linea por el nombre del layout a probar
        inicializarVariables();
        botonVolver.setOnClickListener(view ->{onBackPressed();});

        siguiente.setOnClickListener(view ->{
            int duracion = numberPicker.getValue();
            recordatorio.setDuracionRecordatorio(duracion);
            fechaInicio= LocalDateTime.of(getIntent().getIntExtra("year", 0),
                    getIntent().getIntExtra("month", 0),
                    getIntent().getIntExtra("dayOfMonth", 0),
                    recordatorio.getFechaInicioRecordatorio().getHour(),
                    recordatorio.getFechaInicioRecordatorio().getMinute());
            recordatorio.setFechaInicioRecordatorio(fechaInicio);
            recordatorio.setFechaFinRecordatorio(fechaInicio.plusDays(duracion));
            recordatorioApi.modificarRecordatorio(recordatorio).enqueue(new Callback<Recordatorio>() {
                @Override
                public void onResponse(Call<Recordatorio> call, Response<Recordatorio> response) {
                    Intent intent = new Intent (ElegirDiasActivity.this, AgregarDatosObligatoriosActivity.class);
                    intent.putExtra("codRecordatorio", response.body().getCodRecordatorio());
                    intent.putExtra("presentacionMedId", getIntent().getIntExtra("presentacionMedId", 0));
                    startActivity(intent);
                }

                @Override
                public void onFailure(Call<Recordatorio> call, Throwable t) {
                    Toast.makeText(ElegirDiasActivity.this, "Error al agregar duracion al recordatorio", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private void inicializarVariables() {
        numberPicker = findViewById(R.id.number_picker);
        botonVolver = findViewById(R.id.boton_volver);
        siguiente = findViewById(R.id.button_siguiente);
        recordatorioApi.getByCodRecordatorio(getIntent().getIntExtra("codRecordatorio",0)).enqueue(new Callback<Recordatorio>() {
            @Override
            public void onResponse(Call<Recordatorio> call, Response<Recordatorio> response) {
                recordatorio = response.body();
            }

            @Override
            public void onFailure(Call<Recordatorio> call, Throwable t) {
                Toast.makeText(ElegirDiasActivity.this, "Error al cargar recordatorio", Toast.LENGTH_SHORT).show();
            }
        });

    }
}