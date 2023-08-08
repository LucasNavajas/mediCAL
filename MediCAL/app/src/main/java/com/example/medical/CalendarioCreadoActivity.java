package com.example.medical;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class CalendarioCreadoActivity extends AppCompatActivity {
    private ImageView gifImageView;
    private TextView mensajeCreado;
    private ImageView botonCerrar;
    private Intent intent1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.n21_agrega_calendario_correctamente);//cambiar esta linea por el nombre del layout a probar
        inicializarVariables();



        // Cargar y mostrar el GIF utilizando Glide
        Glide.with(this)
                .load(R.drawable.gif_calendario) // Reemplaza "nombre_del_gif" con el nombre de tu archivo GIF sin la extensión
                .into(gifImageView);

        String contenidoActual = mensajeCreado.getText().toString();
        String textoAdicional = intent1.getStringExtra("nombreCalendario");
        String nuevoContenido;

        nuevoContenido = contenidoActual+textoAdicional;
        mensajeCreado.setText(nuevoContenido);
        mensajeCreado.setGravity(Gravity.CENTER_HORIZONTAL);

        botonCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    private void inicializarVariables() {
        gifImageView = findViewById(R.id.gif_calendario);
        mensajeCreado = findViewById(R.id.text_calendarioagregado);
        botonCerrar = findViewById(R.id.boton_cerrar);
        intent1 = getIntent();

    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this, InicioCalendarioActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
