package com.example.medical;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SobreNosotrosActivity extends AppCompatActivity {

    private ImageView botonVolver;
    private TextView termYCond;
    private TextView politicas;
    private TextView manualUsuario;
    private TextView videoTurorial;
    private TextView actualizaciones;
    private Context context;
    private int codUsuarioLogeado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.n89_sobre_app);
        this.context = context;

        botonVolver = findViewById(R.id.boton_volver);
        termYCond = findViewById(R.id.text_term_cond);
        politicas = findViewById(R.id.text_politicas);
        manualUsuario = findViewById(R.id.text_manual);
        videoTurorial = findViewById(R.id.text_video_tutorial);
        actualizaciones = findViewById(R.id.text_actualizaciones);


        botonVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed(); // Volver a la actividad anterior
            }
        });

        termYCond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Aquí maneja la acción de navegación a AgregarSeguimientoActivity
                Intent intent = new Intent(SobreNosotrosActivity.this, LeerTYCActivity.class);
                intent.putExtra("codUsuario", codUsuarioLogeado);
                startActivity(intent);
            }
        });

        politicas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Aquí maneja la acción de navegación a AgregarSeguimientoActivity
                Intent intent = new Intent(SobreNosotrosActivity.this, LeerPoliticasActivity.class);
                intent.putExtra("codUsuario", codUsuarioLogeado);
                startActivity(intent);
            }
        });

        manualUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Copiar link a manual ?
                String link = "";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                context.startActivity(intent);
            }
        });

        videoTurorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Copiar link a video ?
                String link = "";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                context.startActivity(intent);
            }
        });

        actualizaciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Copiar link a play store ?
                String link = "";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed(); // Volver a la actividad anterior
    }

}
