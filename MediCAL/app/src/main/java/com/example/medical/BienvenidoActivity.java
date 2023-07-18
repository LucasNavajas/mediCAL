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
            View popupView = getLayoutInflater().inflate(R.layout.n12_popup_codigoinvalido, null);


            // Crear la instancia de PopupWindow
            PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            // Hacer que el popup sea enfocable (opcional)
            popupWindow.setFocusable(true);

            // Configurar animación para oscurecer el fondo
            View rootView = findViewById(android.R.id.content);

            View dimView = findViewById(R.id.dim_view);
            dimView.setVisibility(View.VISIBLE);

            Animation scaleAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.popup);
            popupView.startAnimation(scaleAnimation);

            // Mostrar el popup en la ubicación deseada
            popupWindow.showAtLocation(rootView, Gravity.CENTER, 0, 0);

            TextView textViewAceptar = popupView.findViewById(R.id.aceptar);

            textViewAceptar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Ocultar el PopupWindow
                    popupWindow.dismiss();

                    // Ocultar el fondo oscurecido
                    dimView.setVisibility(View.GONE);
                }
            });

        });



        Button buttonCrear = findViewById(R.id.button_crear);
        buttonCrear.setOnClickListener(view -> {
            Intent intent = new Intent(BienvenidoActivity.this, PoliticasPrivacidadActivity.class);
            startActivity(intent);
        });
    }
}