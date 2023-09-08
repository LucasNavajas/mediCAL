package com.example.medical;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class InformesActivity extends AppCompatActivity {

    // Falta completar

    private ImageView botonVolver;
    private Object context;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.n81_informes_sin_cargar);
        Intent intent1 = getIntent();
        this.context = context;

        botonVolver = findViewById(R.id.boton_volver);


        //Pasar intent.putExtra("calendarioSeleccionadoid", getIntent().getIntExtra("calendarioSeleccionadoid", 0)) por todas las actividades hacia adelante y cuando se vuelve a mas tambien
        botonVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed(); // Volver a la actividad anterior
            }
        });

    }

}
