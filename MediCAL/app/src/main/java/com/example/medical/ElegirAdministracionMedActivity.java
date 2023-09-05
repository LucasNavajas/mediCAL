//PARA QUE SE MUESTRE BIEN HAY QUE DROPEAR SQL Y HACER EL GRADLE TEST DE PresentacionMed//
//esto para que se cargen las presentaciones y administraciones y sin los ejemplos que pusimos//
//en esta actividad hay que hacer el boton de volver para conectarla con la anterior pantalla//
package com.example.medical;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medical.adapter.AdministracionMedAdapter;
import com.example.medical.model.AdministracionMed;
import com.example.medical.retrofit.AdministracionMedApi;
import com.example.medical.retrofit.RecordatorioApi;
import com.example.medical.retrofit.RetrofitService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ElegirAdministracionMedActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RetrofitService retrofitService = new RetrofitService();
    private RecordatorioApi recordatorioApi = retrofitService.getRetrofit().create(RecordatorioApi.class);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.n33_administracion_medica);

        recyclerView = findViewById(R.id.listaadministracionmed_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadAdministracionMed();
        ImageView botonVolver = findViewById(R.id.boton_volver);
        botonVolver.setOnClickListener(view -> {onBackPressed();});
    }

    private void loadAdministracionMed() {
        RetrofitService retrofitService = new RetrofitService();
        AdministracionMedApi administracionMedApi = retrofitService.getRetrofit().create(AdministracionMedApi.class);
        administracionMedApi.getAllAdministracionMeds()
                .enqueue(new Callback<List<AdministracionMed>>() {
                    @Override
                    public void onResponse(Call<List<AdministracionMed>> call, Response<List<AdministracionMed>> response) {
                        populateListView(response.body());
                    }

                    @Override
                    public void onFailure(Call<List<AdministracionMed>> call, Throwable t) {
                        Toast.makeText(ElegirAdministracionMedActivity.this, "Fallo en cargar las administraciones medicas", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void populateListView(List<AdministracionMed> administracionMedList) {
        AdministracionMedAdapter administracionMedAdapter = new AdministracionMedAdapter(administracionMedList);
        administracionMedAdapter.setOnItemClickListener(new AdministracionMedAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(AdministracionMed item) {
                Intent intent = new Intent(ElegirAdministracionMedActivity.this, ElegirPresentacionMedActivity.class);
                intent.putExtra("administracionMedId", item.getCodAdministracionMed());
                intent.putExtra("codRecordatorio", getIntent().getIntExtra("codRecordatorio",0));
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(administracionMedAdapter);
    }

    @Override
    public void onDestroy(){
        recordatorioApi.eliminarRecordatorio(getIntent().getIntExtra("codRecordatorio",0)).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
            }
        });
        super.onDestroy();
    }


}
