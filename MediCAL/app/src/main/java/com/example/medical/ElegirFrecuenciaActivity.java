package com.example.medical;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medical.adapter.PresentacionMedAdapter;
import com.example.medical.model.AdministracionMed;
import com.example.medical.model.PresentacionMed;
import com.example.medical.model.Recordatorio;
import com.example.medical.retrofit.AdministracionMedApi;
import com.example.medical.retrofit.PresentacionMedApi;
import com.example.medical.retrofit.RecordatorioApi;
import com.example.medical.retrofit.RetrofitService;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ElegirFrecuenciaActivity extends AppCompatActivity {
    private RetrofitService retrofitService = new RetrofitService();
    private RecordatorioApi recordatorioApi = retrofitService.getRetrofit().create(RecordatorioApi.class);
    private AdministracionMedApi administracionMedApi = retrofitService.getRetrofit().create(AdministracionMedApi.class);
    private PresentacionMedApi presentacionMedApi = retrofitService.getRetrofit().create(PresentacionMedApi.class);
    private AdministracionMed adminRecordatorio;
    private PresentacionMed presentacionRecordatorio;

    private ImageView botonVolver;

    private int codAdmin;

    private int codPresen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.n38_seleccionar_frecuencia);
        modificarRecordatorio();


        botonVolver = findViewById(R.id.boton_volver);

        // Recibiendo los valores codAdmin y codPresen de la actividad anterior
        Intent intent = getIntent();
        codAdmin = intent.getIntExtra("administracionMedId", 0);
        codPresen = intent.getIntExtra("presentacionMedId", 0);

        // Mostrando los valores recibidos
        Toast.makeText(ElegirFrecuenciaActivity.this, "Codigo adm: " + codAdmin + " Codigo presen: " + codPresen, Toast.LENGTH_SHORT).show();

        setContentView(R.layout.n38_seleccionar_frecuencia);

        botonVolver = findViewById(R.id.boton_volver);


        botonVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed(); // Volver a la actividad anterior
            }
        });

        // Agregar OnClickListener a los TextView
        TextView textViewXHoras = findViewById(R.id.text_x_horas);
        TextView textViewXDias = findViewById(R.id.text_x_dias);
        TextView textViewXSemanas = findViewById(R.id.text_x_semanas);
        TextView textViewXMeses = findViewById(R.id.text_x_meses);
        TextView textViewCicloRecurrente = findViewById(R.id.text_ciclo_recurrente);
        TextView textViewSegunSeaNecesario = findViewById(R.id.text_segun_sea_necesario);

        textViewXHoras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Iniciar una nueva actividad para la opción "Cada X horas"
                Intent intent = new Intent(ElegirFrecuenciaActivity.this, FrecuenciaxhorasActivity.class);
                intent.putExtra("administracionMedId", codAdmin);
                intent.putExtra("presentacionMedId", codPresen);
                startActivity(intent);
            }
        });

        textViewXDias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Iniciar una nueva actividad para la opción "Cada X días"
                Intent intent = new Intent(ElegirFrecuenciaActivity.this, FrecuenciaxdiasActivity.class);
                intent.putExtra("administracionMedId", codAdmin);
                intent.putExtra("presentacionMedId", codPresen);
                startActivity(intent);
            }
        });

        textViewXSemanas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Iniciar una nueva actividad para la opción "Cada X semanas"
                Intent intent = new Intent(ElegirFrecuenciaActivity.this, FrecuenciaxsemanasActivity.class);
                intent.putExtra("administracionMedId", codAdmin);
                intent.putExtra("presentacionMedId", codPresen);
                startActivity(intent);
            }
        });

        textViewXMeses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Iniciar una nueva actividad para la opción "Cada X meses"
                Intent intent = new Intent(ElegirFrecuenciaActivity.this, FrecuenciaxmesesActivity.class);
                intent.putExtra("administracionMedId", codAdmin);
                intent.putExtra("presentacionMedId", codPresen);
                startActivity(intent);
            }
        });

        textViewCicloRecurrente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Iniciar una nueva actividad para la opción "Ciclo recurrente"
                Intent intent = new Intent(ElegirFrecuenciaActivity.this, FrecuenciaxciclosActivity.class);
                intent.putExtra("administracionMedId", codAdmin);
                intent.putExtra("presentacionMedId", codPresen);
                startActivity(intent);
            }
        });

        textViewSegunSeaNecesario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Iniciar una nueva actividad para la opción "Según sea necesario"
                Intent intent = new Intent(ElegirFrecuenciaActivity.this, SeleccionarHorarioRecordatorioActivity.class);
                intent.putExtra("administracionMedId", codAdmin);
                intent.putExtra("presentacionMedId", codPresen);
                startActivity(intent);
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed(); // Volver a la actividad anterior
    }
    private void modificarRecordatorio() {
        administracionMedApi.getByCodAdministracionMed(getIntent().getIntExtra("administracionMedId",0)).enqueue(new Callback<AdministracionMed>() {
            @Override
            public void onResponse(Call<AdministracionMed> call, Response<AdministracionMed> response) {
                adminRecordatorio = response.body();
                presentacionMedApi.getPresentacionMedByCod(getIntent().getIntExtra("presentacionMedId", 0)).enqueue(new Callback<PresentacionMed>() {
                    @Override
                    public void onResponse(Call<PresentacionMed> call, Response<PresentacionMed> response) {
                        presentacionRecordatorio = response.body();
                        recordatorioApi.getByCodRecordatorio(getIntent().getIntExtra("codRecordatorio", 0)).enqueue(new Callback<Recordatorio>() {
                            @Override
                            public void onResponse(Call<Recordatorio> call, Response<Recordatorio> response) {
                                Recordatorio recordatorio = response.body();
                                recordatorio.setAdministracionMed(adminRecordatorio);
                                recordatorio.setPresentacionMed(presentacionRecordatorio);
                                recordatorioApi.modificarRecordatorio(recordatorio).enqueue(new Callback<Recordatorio>() {
                                    @Override
                                    public void onResponse(Call<Recordatorio> call, Response<Recordatorio> response) {
                                        Log.d("Recordatorio Modificado", "Se agregaron la administracion y presentacion");
                                    }

                                    @Override
                                    public void onFailure(Call<Recordatorio> call, Throwable t) {
                                        Toast.makeText(ElegirFrecuenciaActivity.this, "Error al fijar administración y presentación al recordatorio al modificarlo " , Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                            @Override
                            public void onFailure(Call<Recordatorio> call, Throwable t) {
                                Toast.makeText(ElegirFrecuenciaActivity.this, "Error al fijar administración y presentación al recordatorio al obtenerlo " , Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<PresentacionMed> call, Throwable t) {
                        Toast.makeText(ElegirFrecuenciaActivity.this, "Error al fijar administración y presentación al recordatorio al obtener la presentacion " , Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onFailure(Call<AdministracionMed> call, Throwable t) {
                Toast.makeText(ElegirFrecuenciaActivity.this, "Error al fijar administración y presentación al recordatorio al obtener la administración" , Toast.LENGTH_SHORT).show();
            }
        });
    }
}

