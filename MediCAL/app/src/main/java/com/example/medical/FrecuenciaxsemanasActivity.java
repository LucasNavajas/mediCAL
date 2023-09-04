package com.example.medical;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class FrecuenciaxsemanasActivity extends AppCompatActivity {

    private ImageView botonVolver;
    private NumberPicker numberPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.n42_cada_x_semanas);

        botonVolver = findViewById(R.id.boton_volver);
        numberPicker = findViewById(R.id.number_picker);

        // Configura los valores mínimo y máximo
        numberPicker.setMinValue(1); // Valor mínimo
        numberPicker.setMaxValue(52); // Valor máximo

        // Recibir los valores codAdmin y codPresen de la actividad anterior
        int codAdmin = getIntent().getIntExtra("administracionMedId", 0);
        int codPresen = getIntent().getIntExtra("presentacionMedId", 0);

        // Mostrar los valores recibidos en un Toast
        String mensaje = "Codigo adm: " + codAdmin + " Codigo presen: " + codPresen;
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();

        botonVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed(); // Volver a la actividad anterior
            }
        });
        Button buttonSiguiente = findViewById(R.id.button_siguiente);

        buttonSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Iniciar la actividad SeleccionarHorarioRecordatorioActivity
                Intent intent = new Intent(FrecuenciaxsemanasActivity.this, SeleccionarHorarioRecordatorioActivity.class);
                intent.putExtra("administracionMedId", codAdmin);
                intent.putExtra("presentacionMedId", codPresen);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed(); // Volver a la actividad anterior
    }
}
