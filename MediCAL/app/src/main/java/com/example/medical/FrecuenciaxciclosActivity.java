package com.example.medical;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.NumberPicker; // Agrega esta importación
import androidx.appcompat.app.AppCompatActivity;

public class FrecuenciaxciclosActivity extends AppCompatActivity {

    private ImageView botonVolver;
    private NumberPicker numberPickerToma;
    private NumberPicker numberPickerDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.n41_ciclo_recurrente);

        botonVolver = findViewById(R.id.boton_volver);
        numberPickerToma = findViewById(R.id.number_pickertoma);
        numberPickerDesc = findViewById(R.id.number_pickerdesc);

        // Configura los valores mínimo y máximo
        numberPickerToma.setMinValue(0); // Valor mínimo
        numberPickerToma.setMaxValue(90); // Valor máximo

        numberPickerDesc.setMinValue(1); // Valor mínimo
        numberPickerDesc.setMaxValue(90); // Valor máximo

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
