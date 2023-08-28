package com.example.medical;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class SeguimientoCreadoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.n78_seguimiento);


        // Cierra la actividad actual para evitar que el usuario pueda volver atrás
        finish();
    }
}

