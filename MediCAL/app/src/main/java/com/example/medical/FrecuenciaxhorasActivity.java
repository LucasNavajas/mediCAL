package com.example.medical;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.NumberPicker; // Agrega esta importación
import androidx.appcompat.app.AppCompatActivity;

public class FrecuenciaxhorasActivity extends AppCompatActivity {

    private ImageView botonVolver;
    private NumberPicker numberPicker; // Agrega esta variable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.n39_cada_x_horas);

        botonVolver = findViewById(R.id.boton_volver);
        numberPicker = findViewById(R.id.number_picker); // Encuentra el NumberPicker en tu diseño

        // Configura los valores mínimo y máximo
        numberPicker.setMinValue(1); // Valor mínimo
        numberPicker.setMaxValue(24); // Valor máximo

        botonVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed(); // Volver a la actividad anterior
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed(); // Volver a la actividad anterior
    }
}
