package com.example.medical;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class CrearCuenta3_1Activity extends AppCompatActivity {
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.n07_crear_cuenta_paso3_1);

        ImageView buttonVolver = findViewById(R.id.boton_volver);

        TextView opcion1 = findViewById(R.id.text_agenero);
        TextView opcion2 = findViewById(R.id.text_bigenero);
        TextView opcion3 = findViewById(R.id.text_hombretrans);
        TextView opcion4 = findViewById(R.id.text_mujertrans);
        TextView opcion5 = findViewById(R.id.text_queer);
        TextView opcion6 = findViewById(R.id.text_nodecir);


        View.OnClickListener onClickListener = new View.OnClickListener() {
            Intent intent1 = getIntent();

            @Override
            public void onClick(View view) {
                TextView textView = findViewById(view.getId());
                String genero = textView.getText().toString();
                Intent intent = new Intent(CrearCuenta3_1Activity.this, CrearCuenta4Activity.class);
                intent.putExtra("usuario", intent1.getStringExtra("usuario"));
                intent.putExtra("contrasenia", intent1.getStringExtra("contrasenia"));
                intent.putExtra("mail", intent1.getStringExtra("mail"));
                intent.putExtra("nombre", intent1.getStringExtra("nombre"));
                intent.putExtra("apellido", intent1.getStringExtra("apellido"));
                intent.putExtra("telefono", intent1.getStringExtra("telefono"));
                intent.putExtra("codusuario", intent1.getIntExtra("codusuario",0));
                intent.putExtra("genero", genero);
                startActivity(intent);
            }
        };

        opcion1.setOnClickListener(onClickListener);
        opcion2.setOnClickListener(onClickListener);
        opcion3.setOnClickListener(onClickListener);
        opcion4.setOnClickListener(onClickListener);
        opcion5.setOnClickListener(onClickListener);
        opcion6.setOnClickListener(onClickListener);

        buttonVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onDestroy() {
        mAuth.signOut();
        super.onDestroy();
    }
}
