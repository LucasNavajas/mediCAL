package com.example.medical;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MasInfoMedicionActivity extends AppCompatActivity {
    private int codCalendario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.n79_0_mas_info_medicion);


        // Recuperar el objeto calendarioSeleccionado del Intent
        Intent intent1 = getIntent();
        codCalendario = intent1.getIntExtra("calendarioSeleccionadoid", 0);
        Log.d("MiApp", "codCalendario en AgregarSeguimientoActivity: " + codCalendario); // Agregar este log


        findViewById(R.id.boton_volver).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Vuelve a la pantalla anterior (Puede ser tu pantalla 73)
                onBackPressed();
            }
        });



    }
}

