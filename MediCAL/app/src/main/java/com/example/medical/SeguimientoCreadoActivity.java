package com.example.medical;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.List;

public class SeguimientoCreadoActivity extends AppCompatActivity {
    private int codCalendario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.n78_seguimiento);


        // Recuperar el objeto calendarioSeleccionado del Intent
        Intent intent1 = getIntent();
        codCalendario = intent1.getIntExtra("calendarioSeleccionadoid", 0);
        Log.d("MiApp", "codCalendario en AgregarSeguimientoActivity: " + codCalendario); // Agregar este log

        List<Integer> calendarioSintomaIds = getIntent().getIntegerArrayListExtra("calendarioSintomaIds");


        Button agregarSeguimientoButton = findViewById(R.id.button_agregarseguimiento);
        agregarSeguimientoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Abrir la nueva pantalla de Agregar Seguimiento
                Intent intent = new Intent(SeguimientoCreadoActivity.this, ElegirSeguimientoActivity.class);
                intent.putExtra("calendarioSeleccionadoid", codCalendario);
                startActivity(intent);
            }
        });

        findViewById(R.id.boton_volver).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Vuelve a la pantalla anterior (Puede ser tu pantalla 73)
                onBackPressed();
            }
        });


        // Cierra la actividad actual para evitar que el usuario pueda volver atrás
        finish();
    }
}

