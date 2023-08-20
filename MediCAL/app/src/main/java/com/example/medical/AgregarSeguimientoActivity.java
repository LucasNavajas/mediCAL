package com.example.medical;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class AgregarSeguimientoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.n73_seguimiento_vacio); // Reemplaza con el nombre de tu archivo XML

        Button agregarSeguimientoButton = findViewById(R.id.button_siguiente);
        agregarSeguimientoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Abrir la nueva pantalla de Agregar Seguimiento
                Intent intent = new Intent(AgregarSeguimientoActivity.this, ElegirSeguimientoActivity.class);
                startActivity(intent);

            }
        });
    }
}
