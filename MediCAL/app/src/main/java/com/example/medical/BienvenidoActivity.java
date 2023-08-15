package com.example.medical;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.medical.retrofit.RetrofitService;
import com.example.medical.retrofit.UsuarioApi;

public class BienvenidoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.n01_bienvenido);
        initializeComponents();
    }

    private void initializeComponents(){
        RetrofitService retrofitService = new RetrofitService();
        UsuarioApi usuarioApi = retrofitService.getRetrofit().create(UsuarioApi.class);

        Button buttonIngresar = findViewById(R.id.button_ingresar);

        buttonIngresar.setOnClickListener(view -> {
            Intent intent2 = new Intent(BienvenidoActivity.this, IniciarSesionActivity.class);
            startActivity(intent2);
        });



        Button buttonCrear = findViewById(R.id.button_crear);
        buttonCrear.setOnClickListener(view -> {
            Intent intent = new Intent(BienvenidoActivity.this, PoliticasPrivacidadActivity.class);
            startActivity(intent);
        });

        TextView contactoSoporte = findViewById(R.id.text_contacto_soporte);
        contactoSoporte.setOnClickListener(view -> {
            Intent intent = new Intent(BienvenidoActivity.this, ContactoSoporteActivity.class);
            startActivity(intent);
        });
    }
}