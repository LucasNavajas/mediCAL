package com.example.medical;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;

import androidx.appcompat.app.AppCompatActivity;

public class ElegirDiasActivity extends AppCompatActivity {
    private NumberPicker numberPicker;
    private ImageView botonVolver;
    private Button siguiente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.n48_indicar_n_dias);//cambiar esta linea por el nombre del layout a probar
        inicializarVariables();
        botonVolver.setOnClickListener(view ->{onBackPressed();});

        siguiente.setOnClickListener(view ->{
            int duracion = numberPicker.getValue()*24;
            Intent intent2 = new Intent(ElegirDiasActivity.this, DatosObligatoriosRecordatorioActivity.class);
            intent2.putExtra("year", getIntent().getIntExtra("year", 0));
            intent2.putExtra("month", getIntent().getIntExtra("month", 0));
            intent2.putExtra("day", getIntent().getIntExtra("day", 0));
            intent2.putExtra("duracion", duracion);
            startActivity(intent2);
        });
    }

    private void inicializarVariables() {
        numberPicker = findViewById(R.id.number_picker);
        botonVolver = findViewById(R.id.boton_volver);
        siguiente = findViewById(R.id.button_siguiente);
    }
}