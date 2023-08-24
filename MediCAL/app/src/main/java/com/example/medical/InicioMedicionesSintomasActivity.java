package com.example.medical;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class InicioMedicionesSintomasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.n72_inicio_mediciones_sintomas);

        // Configurar el botón "Siguiente"
        Button siguienteButton = findViewById(R.id.button_siguiente);
        siguienteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Abrir la actividad AgregarSeguimientoActivity
                Intent intent = new Intent(InicioMedicionesSintomasActivity.this, AgregarSeguimientoActivity.class);
                startActivity(intent);
            }
        });

        // Configurar el icono de cerrar
        ImageView cerrarIcon = findViewById(R.id.boton_cerrar);
        cerrarIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Abrir la actividad InicioCalendarioActivity
                Intent intent = new Intent(InicioMedicionesSintomasActivity.this, InicioCalendarioActivity.class);
                startActivity(intent);
            }
        });
    }
}
