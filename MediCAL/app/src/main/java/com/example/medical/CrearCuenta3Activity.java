package com.example.medical;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CrearCuenta3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent1 = getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.n06_crear_cuenta_paso3);

        ImageView buttonVolver = findViewById(R.id.boton_volver);
        TextView opcion1 = findViewById(R.id.text_mujer);
        TextView opcion2 = findViewById(R.id.text_hombre);
        TextView opcion3= findViewById(R.id.text_nobinario);
        TextView opcion4 = findViewById(R.id.text_otro);

        View.OnClickListener onClickListener = new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                switch(view.getId()){
                    case R.id.text_otro:
                        Intent intent = new Intent(CrearCuenta3Activity.this, CrearCuenta3_1Activity.class);
                        intent.putExtra("usuario", intent1.getStringExtra("usuario"));
                        intent.putExtra("contrasenia", intent1.getStringExtra("contrasenia"));
                        intent.putExtra("mail", intent1.getStringExtra("mail"));
                        intent.putExtra("nombre", intent1.getStringExtra("nombre"));
                        intent.putExtra("apellido", intent1.getStringExtra("apellido"));
                        intent.putExtra("telefono", intent1.getStringExtra("telefono"));
                        intent.putExtra("codusuario", intent1.getIntExtra("codusuario",0));
                        startActivity(intent);
                        break;
                    default:
                        TextView textView = findViewById(view.getId());
                        String genero = textView.getText().toString();
                        Intent intent2 = new Intent(CrearCuenta3Activity.this, CrearCuenta4Activity.class);
                        intent2.putExtra("usuario", intent1.getStringExtra("usuario"));
                        intent2.putExtra("contrasenia", intent1.getStringExtra("contrasenia"));
                        intent2.putExtra("mail", intent1.getStringExtra("mail"));
                        intent2.putExtra("nombre", intent1.getStringExtra("nombre"));
                        intent2.putExtra("apellido", intent1.getStringExtra("apellido"));
                        intent2.putExtra("telefono", intent1.getStringExtra("telefono"));
                        intent2.putExtra("codusuario", intent1.getIntExtra("codusuario",0));
                        intent2.putExtra("genero", genero);
                        startActivity(intent2);
                }
            }
        };

        opcion1.setOnClickListener(onClickListener);
        opcion2.setOnClickListener(onClickListener);
        opcion3.setOnClickListener(onClickListener);
        opcion4.setOnClickListener(onClickListener);

        buttonVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }


}
