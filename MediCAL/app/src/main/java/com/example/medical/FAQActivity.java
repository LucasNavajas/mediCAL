package com.example.medical;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.medical.adapter.FaqAdapter;
import com.example.medical.model.FAQ;
import com.example.medical.retrofit.RetrofitService;
import com.example.medical.retrofit.FAQApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FAQActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.n28y29_preguntas_respuestas_frecuentes);

        recyclerView = findViewById(R.id.listafaqs_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadFaqs();
    }
    public void openContactoSoporteActivity(View view) {
        Intent intent = new Intent(this, ContactoSoporteActivity.class);
        startActivity(intent);
    }
    private void loadFaqs(){
        RetrofitService retrofitService = new RetrofitService();
        FAQApi faqApi = retrofitService.getRetrofit().create(FAQApi.class);
        faqApi.getAllFAQ()
                .enqueue(new Callback<List<FAQ>>() {
                    @Override

                    public void onResponse(Call<List<FAQ>> call, Response<List<FAQ>> response) {
                        Log.d("FAQActivity", "llamo el metodo on response");
                        int statusCode = response.code();
                        Log.d("FAQActivity", "Status code: " + statusCode);
                        if (response.isSuccessful() && response.body() != null) {
                            Log.d("FAQActivity", "la populo");
                            populateListView(response.body());
                        } else {
                            Log.d("FAQActivity", "no anda");
                            Log.d("FAQActivity", "Response code: " + response.code());
                            Log.d("FAQActivity", "Error body: " + response.errorBody());
                            Toast.makeText(FAQActivity.this, "Respuesta vacía o incorrecta del servidor", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<FAQ>> call, Throwable t) {
                        Log.d("FAQActivity", "no carga");
                        Toast.makeText(FAQActivity.this, "Fallo en cargar FAQs", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void populateListView(List<FAQ> faqList) {
        if (faqList != null && !faqList.isEmpty()) {
            Log.d("FAQActivity", "la populo en la list");
            FaqAdapter faqAdapter = new FaqAdapter(faqList);
            recyclerView.setAdapter(faqAdapter);
        } else {
            Log.d("FAQActivity", "no la populo en la list");
            Toast.makeText(FAQActivity.this, "La lista de FAQs está vacía o es nula", Toast.LENGTH_SHORT).show();
        }
    }


}