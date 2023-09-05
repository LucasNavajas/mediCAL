package com.example.medical;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medical.adapter.AdministracionMedAdapter;
import com.example.medical.adapter.PresentacionMedAdapter;
import com.example.medical.model.AdministracionMed;
import com.example.medical.model.PresentacionMed;
import com.example.medical.retrofit.PresentacionMedApi;
import com.example.medical.retrofit.RecordatorioApi;
import com.example.medical.retrofit.RetrofitService;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ElegirPresentacionMedActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ImageView botonVolver;
    private RetrofitService retrofitService = new RetrofitService();
    private RecordatorioApi recordatorioApi = retrofitService.getRetrofit().create(RecordatorioApi.class);
    private int codAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent1 = getIntent();
        codAdmin = intent1.getIntExtra("administracionMedId",0);
        setContentView(R.layout.n34_35_36_37_presentacion_medica);

        recyclerView = findViewById(R.id.listapresentacionmed_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        botonVolver = findViewById(R.id.boton_volver);

        loadPresentacionMed(codAdmin);
        /*int administracionMedId = getIntent().getIntExtra("administracionMedId", -1);
        if (administracionMedId != -1) {
            loadPresentacionMed(administracionMedId);
        } else {
            // Mostrar un mensaje de error si no se proporcionó un ID válido
        }
        }*/

        botonVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed(); // Volver a la actividad anterior
            }
        });
    }

    private void loadPresentacionMed(int administracionMedId) {
        RetrofitService retrofitService = new RetrofitService();
        PresentacionMedApi presentacionMedApi = retrofitService.getRetrofit().create(PresentacionMedApi.class);

        presentacionMedApi.getPresentacionesMedByCodAdministracionMed(administracionMedId)
                .enqueue(new Callback<List<PresentacionMed>>() {
                    @Override
                    public void onResponse(Call<List<PresentacionMed>> call, Response<List<PresentacionMed>> response) {
                        populateListView(response.body());
                    }

                    @Override
                    public void onFailure(Call<List<PresentacionMed>> call, Throwable t) {
                        // Mostrar un mensaje de error
                    }
                });
    }

    private void populateListView(List<PresentacionMed> presentacionMedList) {
        PresentacionMedAdapter presentacionMedAdapter = new PresentacionMedAdapter(presentacionMedList);

        presentacionMedAdapter.setOnItemClickListener(new PresentacionMedAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(PresentacionMed item) {
                Intent intent = new Intent(ElegirPresentacionMedActivity.this, ElegirFrecuenciaActivity.class);
                intent.putExtra("presentacionMedId", item.getCodPresentacionMed());
                intent.putExtra("administracionMedId", codAdmin);
                intent.putExtra("codRecordatorio", getIntent().getIntExtra("codRecordatorio", 0));
                startActivity(intent);
            }
        });



        recyclerView.setAdapter(presentacionMedAdapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed(); // Volver a la actividad anterior
    }

}