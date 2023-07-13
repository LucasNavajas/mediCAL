package com.example.medical;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class EditarDosisFuturasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.n63_editar_dosis_futuras);
        inicializarBotones();



    }

    private void inicializarBotones() {
        ImageButton desplegableFrecuencia = findViewById(R.id.desplegable_frecuencia);
        TextView cambiarFrecuencia = findViewById(R.id.cambiar_frecuencia);
        ImageButton desplegableDuracion = findViewById(R.id.desplegable_duracion);
        RelativeLayout cambiarDuracion = findViewById(R.id.cambiar_duracion);
        TextView duracionActual = findViewById(R.id.duracion_actual);
        ImageButton desplegableImagen = findViewById(R.id.desplegable_imagen);
        RelativeLayout cambiarImagen = findViewById(R.id.relative_cambiar_imagen);



        desplegableFrecuencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cambiarFrecuencia.getVisibility() == View.GONE) {
                    desplegableFrecuencia.setImageResource(R.drawable.boton_desplegable_arriba);
                    cambiarFrecuencia.setVisibility(View.VISIBLE);
                } else {
                    cambiarFrecuencia.setVisibility(View.GONE);
                    desplegableFrecuencia.setImageResource(R.drawable.boton_desplegable);
                }
            }
        });

        desplegableDuracion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cambiarDuracion.getVisibility() == View.GONE) {
                    desplegableDuracion.setImageResource(R.drawable.boton_desplegable_arriba);
                    cambiarDuracion.setVisibility(View.VISIBLE);
                    duracionActual.setText("Fecha de inicio:");
                    duracionActual.setTextColor(Color.BLACK);
                } else {
                    cambiarDuracion.setVisibility(View.GONE);
                    desplegableDuracion.setImageResource(R.drawable.boton_desplegable);
                    duracionActual.setText("Según los días de tratamiento");
                    duracionActual.setTextColor(ContextCompat.getColor(EditarDosisFuturasActivity.this,R.color.gris_medical));
                }

            }
        });


        desplegableImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cambiarImagen.getVisibility() == View.GONE) {
                    desplegableImagen.setImageResource(R.drawable.boton_desplegable_arriba);
                    cambiarImagen.setVisibility(View.VISIBLE);
                } else {
                    cambiarImagen.setVisibility(View.GONE);
                    desplegableImagen.setImageResource(R.drawable.boton_desplegable);
                }
            }
        });

        }
    }



