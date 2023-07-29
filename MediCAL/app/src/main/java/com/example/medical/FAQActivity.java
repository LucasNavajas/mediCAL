package com.example.medical;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
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
        setContentView(R.layout.activity_faqactivity);

        recyclerView = findViewById(R.id.listafaqs_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadFaqs();
    }
    private void loadFaqs(){
        RetrofitService retrofitService = new RetrofitService();
                FAQApi faqApi = retrofitService.getRetrofit().create(FAQApi.class);
                faqApi.getAllFAQ()
                        .enqueue(new Callback<List<FAQ>>() {
                            @Override
                            public void onResponse(Call<List<FAQ>> call, Response<List<FAQ>> response) {
                               populateListView(response.body());

                            }

                            @Override
                            public void onFailure(Call<List<FAQ>> call, Throwable t) {
                                Toast.makeText(FAQActivity.this, "Fallo en cargar FAQs", Toast.LENGTH_SHORT).show();
                            }
                        });
    }

    private void populateListView(List<FAQ> faqList) {
        FaqAdapter faqAdapter = new FaqAdapter(faqList);
        recyclerView.setAdapter(faqAdapter);
    }
}