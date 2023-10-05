package com.example.medical;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.medical.model.Recordatorio;
import com.example.medical.retrofit.PresentacionMedApi;
import com.example.medical.retrofit.RecordatorioApi;
import com.example.medical.retrofit.RetrofitService;

import java.time.LocalDateTime;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AgregarDuracionRecordatorioActivity extends AppCompatActivity {
    private RetrofitService retrofitService = new RetrofitService();
    private RecordatorioApi recordatorioApi = retrofitService.getRetrofit().create(RecordatorioApi.class);
    private TextView cincoDias;
    private TextView sieteDias;
    private TextView diezDias;
    private TextView treintaDias;
    private TextView seisMeses;
    private TextView doceMeses;
    private TextView veinticuatroMeses;
    private TextView elegirDias;
    private TextView tratamientoContinuo;
    private ImageView botonVolver;
    private Recordatorio recordatorio;
    private LocalDateTime fechaInicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.n47_establecer_duracion);//cambiar esta linea por el nombre del layout a probar
        TextView nombreMedicamento = findViewById(R.id.nombreMedicamento);
        nombreMedicamento.setText(getIntent().getStringExtra("nombreMedicamento"));
        inicializarVariables();
        botonVolver.setOnClickListener(view ->{onBackPressed();});
    }

    private void inicializarVariables() {
        cincoDias = findViewById(R.id.text_cinco_dias);
        sieteDias = findViewById(R.id.text_una_semana);
        diezDias = findViewById(R.id.text_diez_dias);
        treintaDias = findViewById(R.id.text_treinta_dias);
        seisMeses = findViewById(R.id.text_seis_meses);
        doceMeses = findViewById(R.id.text_doce_meses);
        elegirDias = findViewById(R.id.text_elegir);
        tratamientoContinuo = findViewById(R.id.text_continuo);
        veinticuatroMeses = findViewById(R.id.text_veinticuatro_meses);
        botonVolver = findViewById(R.id.boton_volver);

        recordatorioApi.getByCodRecordatorio(getIntent().getIntExtra("codRecordatorio",0)).enqueue(new Callback<Recordatorio>() {
            @Override
            public void onResponse(Call<Recordatorio> call, Response<Recordatorio> response) {
                recordatorio = response.body();
                cincoDias.setOnClickListener(onClickListener);
                sieteDias.setOnClickListener(onClickListener);
                diezDias.setOnClickListener(onClickListener);
                treintaDias.setOnClickListener(onClickListener);
                seisMeses.setOnClickListener(onClickListener);
                doceMeses.setOnClickListener(onClickListener);
                veinticuatroMeses.setOnClickListener(onClickListener);
                elegirDias.setOnClickListener(onClickListener);
                tratamientoContinuo.setOnClickListener(onClickListener);
            }

            @Override
            public void onFailure(Call<Recordatorio> call, Throwable t) {
                Toast.makeText(AgregarDuracionRecordatorioActivity.this, "Error al cargar recordatorio", Toast.LENGTH_SHORT).show();
            }
        });


    }

    View.OnClickListener onClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            switch(view.getId()){
                case R.id.text_elegir:
                    Intent intent = new Intent(AgregarDuracionRecordatorioActivity.this, ElegirDiasActivity.class);
                    intent.putExtra("codRecordatorio", getIntent().getIntExtra("codRecordatorio",0));
                    intent.putExtra("year", getIntent().getIntExtra("year", 0));
                    intent.putExtra("month", getIntent().getIntExtra("month", 0));
                    intent.putExtra("dayOfMonth", getIntent().getIntExtra("dayOfMonth", 0));
                    intent.putExtra("presentacionMedId", getIntent().getIntExtra("presentacionMedId", 0));
                    intent.putExtra("nombreMedicamento", getIntent().getStringExtra("nombreMedicamento"));
                    startActivity(intent);
                    break;

                case R.id.text_continuo:
                    int duracionContinua = 99999;
                    recordatorio.setDuracionRecordatorio(duracionContinua);
                    fechaInicio= LocalDateTime.of(getIntent().getIntExtra("year", 0),
                                                                getIntent().getIntExtra("month", 0),
                                                                getIntent().getIntExtra("dayOfMonth", 0),
                                                                recordatorio.getFechaInicioRecordatorio().getHour(),
                                                                recordatorio.getFechaInicioRecordatorio().getMinute());
                    recordatorio.setFechaInicioRecordatorio(fechaInicio);
                    recordatorio.setFechaFinRecordatorio(fechaInicio.plusDays(duracionContinua));
                    recordatorioApi.modificarRecordatorio(recordatorio).enqueue(new Callback<Recordatorio>() {
                        @Override
                        public void onResponse(Call<Recordatorio> call, Response<Recordatorio> response) {
                            Intent intent = new Intent (AgregarDuracionRecordatorioActivity.this, AgregarDatosObligatoriosActivity.class);
                            intent.putExtra("codRecordatorio", response.body().getCodRecordatorio());
                            intent.putExtra("presentacionMedId", getIntent().getIntExtra("presentacionMedId", 0));
                            intent.putExtra("nombreMedicamento", getIntent().getStringExtra("nombreMedicamento"));
                            startActivity(intent);
                        }

                        @Override
                        public void onFailure(Call<Recordatorio> call, Throwable t) {
                            Toast.makeText(AgregarDuracionRecordatorioActivity.this, "Error al agregar duracion al recordatorio", Toast.LENGTH_SHORT).show();
                        }
                    });
                    break;
                default:
                    int duracion = obtenerEnteroDuracion(view.getId());
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
                            Intent intent = new Intent (AgregarDuracionRecordatorioActivity.this, AgregarDatosObligatoriosActivity.class);
                            intent.putExtra("codRecordatorio", response.body().getCodRecordatorio());
                            intent.putExtra("presentacionMedId", getIntent().getIntExtra("presentacionMedId", 0));
                            intent.putExtra("nombreMedicamento", getIntent().getStringExtra("nombreMedicamento"));
                            startActivity(intent);
                        }

                        @Override
                        public void onFailure(Call<Recordatorio> call, Throwable t) {
                            Toast.makeText(AgregarDuracionRecordatorioActivity.this, "Error al agregar duracion al recordatorio", Toast.LENGTH_SHORT).show();
                        }
                    });
                    break;
            }
        }
    };

    private int obtenerEnteroDuracion(int id) {
        switch (id){
            case R.id.text_cinco_dias:
                return 5;
            case R.id.text_una_semana:
                return 7;
            case R.id.text_diez_dias:
                return 10;
            case R.id.text_treinta_dias:
                return 30;
            case R.id.text_seis_meses:
                return 180;
            case R.id.text_doce_meses:
                return 360;
            case R.id.text_veinticuatro_meses:
                return 720;
            default:
                return 0;
        }
    }

}
