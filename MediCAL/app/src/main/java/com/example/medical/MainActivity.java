package com.example.medical;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.n00_inicio_app);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, EjemploActivity.class); //cambiar el segundo parametro por el nombre de la actividad a probar
                startActivity(intent);
                finish();
            }
        }, 3000); // 3000 representa 3 segundos en milisegundos

    }

}