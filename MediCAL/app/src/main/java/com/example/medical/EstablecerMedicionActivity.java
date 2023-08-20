package com.example.medical;


import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class EstablecerMedicionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.n76_0_establecer_medicion);

        // Configura el clic de la flecha hacia atrás
        findViewById(R.id.boton_volver).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Vuelve a la pantalla anterior (Puede ser tu pantalla 73)
                onBackPressed();
            }
        });

        // Obtener los datos pasados desde la actividad anterior
        Intent intent = getIntent();
        if (intent != null) {
            String nombreMedicion = intent.getStringExtra("nombreMedicion");
            String unidadMedida = intent.getStringExtra("unidadMedida");

            // Actualizar el nombre de la medición en el TextView correspondiente
            TextView nombreMedicionTextView = findViewById(R.id.texto_editar);
            nombreMedicionTextView.setText(nombreMedicion);

            // Actualizar la unidad de medida en el TextView correspondiente
            TextView unidadMedidaTextView = findViewById(R.id.texto_unidad);
            unidadMedidaTextView.setText("(" + unidadMedida + ")");
        }

        // Obtener la fecha y hora actual
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM", Locale.getDefault());
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        Date currentDate = new Date();

        // Configurar los TextView con la fecha y hora actual
        TextView fechaTextView = findViewById(R.id.texto_fecha);
        fechaTextView.setText(dateFormat.format(currentDate));

        TextView horaTextView = findViewById(R.id.texto_hora);
        horaTextView.setText(timeFormat.format(currentDate));
    }
}
