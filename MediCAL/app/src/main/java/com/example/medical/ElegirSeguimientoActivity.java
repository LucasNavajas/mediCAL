package com.example.medical;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

import com.example.medical.model.Calendario;

public class ElegirSeguimientoActivity extends AppCompatActivity {
    private int codCalendario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.n74_mediciones_sintomas);


        // Recuperar el objeto calendarioSeleccionado del Intent
        Intent intent1 = getIntent();
        codCalendario = intent1.getIntExtra("calendarioSeleccionadoid", 0);
        Log.d("MiApp", "codCalendario en ElegirSeguimientoActivity: " + codCalendario); // Agregar este log

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
                intent.putExtra("calendarioSeleccionadoid", codCalendario);
                startActivity(intent);
            }
        });

        // Clic en la sección de Síntoma
        findViewById(R.id.todo_Sintoma).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Iniciar la actividad AnadirSintomaActivity
                Intent intent = new Intent(ElegirSeguimientoActivity.this, AnadirSintomaActivity.class);
                intent.putExtra("calendarioSeleccionadoid", codCalendario);
                startActivity(intent);
            }
        });
    }
}
