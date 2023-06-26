package com.example.medical;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class BienvenidoUsuarioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bienvenido_usuario);
        Intent intent1 = getIntent();
        ImageView gifImageView;
        TextView bienvenido = findViewById(R.id.text_bienvenido);

        String contenidoActual = bienvenido.getText().toString();
        String textoAdicional = intent1.getStringExtra("usuario");
        String nuevoContenido = contenidoActual + textoAdicional + "!";
        bienvenido.setText(nuevoContenido);
        bienvenido.setGravity(Gravity.CENTER_HORIZONTAL);


        gifImageView = findViewById(R.id.gifImageView);

        // Cargar y mostrar el GIF utilizando Glide
        Glide.with(this)
                .load(R.drawable.medicinegif) // Reemplaza "nombre_del_gif" con el nombre de tu archivo GIF sin la extensión
                .into(gifImageView);
    }
}

