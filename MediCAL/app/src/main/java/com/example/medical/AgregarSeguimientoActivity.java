package com.example.medical;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.medical.model.Calendario;

public class AgregarSeguimientoActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "MyPrefs"; // Nombre de las preferencias compartidas
    private static final String FIRST_TIME_KEY = "isFirstTime"; // Clave para indicar si es la primera vez
    private Calendario calendarioSeleccionado;
    private int codCalendario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // Recuperar el objeto calendarioSeleccionado del Intent
        Intent intent1 = getIntent();
        codCalendario = intent1.getIntExtra("calendarioSeleccionadoid", 0);
        Log.d("MiApp", "codCalendario en AgregarSeguimientoActivity: " + codCalendario); // Agregar este log


        // Verificar si es la primera vez
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean isFirstTime = preferences.getBoolean(FIRST_TIME_KEY, true);

        // Realizar la verificación de CalendarioSintoma asociado al Calendario
        boolean hasCalendarioSintoma = false; // Cambiar esto según tu lógica de verificación

        // Redirigir según si es la primera vez o no y si existe CalendarioSintoma asociado
        if (isFirstTime) {
            // Redirigir a la actividad n72_inicio_mediciones_sintomas
            Intent intent = new Intent(AgregarSeguimientoActivity.this, InicioMedicionesSintomasActivity.class);
            startActivity(intent);

            // Marcar que ya no es la primera vez
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(FIRST_TIME_KEY, false);
            editor.apply();
        } else {
            if (hasCalendarioSintoma) {
                // Redirigir a la actividad n78_seguimiento
                Intent intent = new Intent(AgregarSeguimientoActivity.this, SeguimientoCreadoActivity.class);
                startActivity(intent);
            } else {
                // Redirigir a la actividad n73_seguimiento_vacio
                setContentView(R.layout.n73_seguimiento_vacio);
                Button agregarSeguimientoButton = findViewById(R.id.button_siguiente);

                findViewById(R.id.boton_volver).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Vuelve a la pantalla anterior (Puede ser tu pantalla 73)
                        onBackPressed();
                    }
                });
                agregarSeguimientoButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Abrir la nueva pantalla de Agregar Seguimiento
                        Intent intent = new Intent(AgregarSeguimientoActivity.this, ElegirSeguimientoActivity.class);
                        intent.putExtra("calendarioSeleccionadoid", codCalendario);
                        startActivity(intent);
                    }
                });
            }
        }
    }
}

