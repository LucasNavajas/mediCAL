package com.example.medical;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.medical.retrofit.FAQApi;
import com.example.medical.retrofit.RetrofitService;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.n00_inicio_app);
        FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser();
        if (usuario!=null){
            FirebaseAuth.getInstance().signOut();
        }
        initializeComponents();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, EditarPerfilUsuarioActivity.class); //cambiar el segundo parametro por el nombre de la actividad a probar
                startActivity(intent);
                finish();
            }
        }, 3000); // 3000 representa 3 segundos en milisegundos

    }

    private void initializeComponents() {
        RetrofitService retrofitService = new RetrofitService();
        FAQApi faqApi = retrofitService.getRetrofit().create(FAQApi.class);
    }



}