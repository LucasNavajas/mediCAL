package com.example.medical;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class ElegirSeguimientoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.n74_mediciones_sintomas);

        // Configura el clic de la flecha hacia atrás
        findViewById(R.id.boton_volver).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Vuelve a la pantalla anterior (Puede ser tu pantalla 73)
                onBackPressed();
            }
        });

        // Clic en la sección de Medición
        findViewById(R.id.todo_medicion).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Iniciar la actividad AnadirMedicionActivity
                Intent intent = new Intent(ElegirSeguimientoActivity.this, AnadirMedicionActivity.class);
                startActivity(intent);
            }
        });

        // Clic en la sección de Síntoma
        findViewById(R.id.todo_Sintoma).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Iniciar la actividad AnadirSintomaActivity
                Intent intent = new Intent(ElegirSeguimientoActivity.this, AnadirSintomaActivity.class);
                startActivity(intent);
            }
        });
    }
}
