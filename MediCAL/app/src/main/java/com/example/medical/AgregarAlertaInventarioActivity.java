package com.example.medical;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.medical.model.Inventario;
import com.example.medical.model.PresentacionMed;
import com.example.medical.model.Recordatorio;
import com.example.medical.retrofit.InventarioApi;
import com.example.medical.retrofit.PresentacionMedApi;
import com.example.medical.retrofit.RecordatorioApi;
import com.example.medical.retrofit.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AgregarAlertaInventarioActivity extends AppCompatActivity {
    private RetrofitService retrofitService = new RetrofitService();
    private RecordatorioApi recordatorioApi = retrofitService.getRetrofit().create(RecordatorioApi.class);
    private PresentacionMedApi presentacionMedApi = retrofitService.getRetrofit().create(PresentacionMedApi.class);
    private InventarioApi inventarioApi = retrofitService.getRetrofit().create(InventarioApi.class);
    private ImageView botonVolver;
    private Button siguiente;
    private TextView presentacionMed;
    private EditText inventarioAlerta;
    private Recordatorio recordatorio;
    private Inventario inventario = new Inventario();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.n50_indica_n_medicinas);//cambiar esta linea por el nombre del layout a probar
        inicializarVariables();
        TextView nombreMedicamento = findViewById(R.id.nombreMedicamento);
        nombreMedicamento.setText(getIntent().getStringExtra("nombreMedicamento"));
    }

    private void inicializarVariables() {
        inventarioAlerta = findViewById(R.id.inventarioAlerta);
        presentacionMed = findViewById(R.id.text_presentaciones);
        siguiente = findViewById(R.id.button_siguiente);
        botonVolver = findViewById(R.id.boton_volver);
        presentacionMedApi.getPresentacionMedByCod(getIntent().getIntExtra("presentacionMedId", 0)).enqueue(new Callback<PresentacionMed>() {
            @Override
            public void onResponse(Call<PresentacionMed> call, Response<PresentacionMed> response) {
                presentacionMed.setText("Quedan " + response.body().getNombrePresentacionMed()+ "(s)");
            }

            @Override
            public void onFailure(Call<PresentacionMed> call, Throwable t) {
                Toast.makeText(AgregarAlertaInventarioActivity.this, "Error al cargar la presentacion", Toast.LENGTH_SHORT).show();
            }
        });
        recordatorioApi.getByCodRecordatorio(getIntent().getIntExtra("codRecordatorio",0)).enqueue(new Callback<Recordatorio>() {
            @Override
            public void onResponse(Call<Recordatorio> call, Response<Recordatorio> response) {
                recordatorio = response.body();
                siguiente.setOnClickListener(view ->{
                    if(!inventarioAlerta.getText().toString().equals("")){
                        int valorInventarioAlerta = Integer.parseInt(inventarioAlerta.getText().toString());
                        if(valorInventarioAlerta>0){
                            inventario.setCantRealInventario(getIntent().getIntExtra("valorInventarioReal",0));
                            inventario.setCantAvisoInventario(valorInventarioAlerta);
                            inventarioApi.save(inventario).enqueue(new Callback<Inventario>() {
                                @Override
                                public void onResponse(Call<Inventario> call, Response<Inventario> response) {
                                    recordatorio.setInventario(response.body());
                                    recordatorioApi.modificarRecordatorio(recordatorio).enqueue(new Callback<Recordatorio>() {
                                        @Override
                                        public void onResponse(Call<Recordatorio> call, Response<Recordatorio> response) {
                                            Intent intent = new Intent(AgregarAlertaInventarioActivity.this, AgregarDatosObligatoriosActivity.class);
                                            intent.putExtra("codRecordatorio", getIntent().getIntExtra("codRecordatorio",0));
                                            intent.putExtra("presentacionMedId", getIntent().getIntExtra("presentacionMedId", 0));
                                            intent.putExtra("nombreMedicamento", getIntent().getStringExtra("nombreMedicamento"));
                                            startActivity(intent);
                                        }

                                        @Override
                                        public void onFailure(Call<Recordatorio> call, Throwable t) {
                                            Toast.makeText(AgregarAlertaInventarioActivity.this, "Error al modificar el recordatorio", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }

                                @Override
                                public void onFailure(Call<Inventario> call, Throwable t) {
                                    Toast.makeText(AgregarAlertaInventarioActivity.this, "Error al crear el inventario", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    }
                });
            }

            @Override
            public void onFailure(Call<Recordatorio> call, Throwable t) {
                Toast.makeText(AgregarAlertaInventarioActivity.this, "Error al obtener el recordatorio", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
