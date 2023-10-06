package com.example.medical;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.medical.retrofit.RetrofitService;

public class DescargarReporteActivity extends AppCompatActivity {

    private Object context;
    private RetrofitService retrofitService;
    private int nroReporteSeleccionado;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = context;

        Intent intent1 = getIntent();
        nroReporteSeleccionado = intent1.getIntExtra("nroReporte", 0);


    }

}
