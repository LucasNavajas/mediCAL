package com.example.medical;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class BienvenidoUsuarioActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Intent intent1 = getIntent();

        setContentView(R.layout.n09_bienvenido_usuario);

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
    @Override
    public void onBackPressed(){}

}

