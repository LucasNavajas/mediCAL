package com.example.medical;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.medical.model.Calendario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;

public class CrearCalendario1Activity extends AppCompatActivity {
    private TextView opcion1;
    private TextView opcion2;
    private TextView opcion3;
    private TextView opcion4;
    private TextView opcion5;
    private TextView opcion6;
    private TextView opcion7;
    private TextView opcion8;
    private FirebaseAuth mAuth;
    private ImageView botonVolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.n18_relacionar_calendario_paraquienes);
        String jsonCalendario = getIntent().getStringExtra("calendarioJson");
        Calendario calendario = new Gson().fromJson(jsonCalendario, Calendario.class);
        inicializarVariables();


        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView textView = findViewById(view.getId());
                String relacion = textView.getText().toString();
                if(getIntent().getStringExtra("calendarioJson")==null) {
                    Intent intent1 = new Intent(CrearCalendario1Activity.this, CrearCalendario2Activity.class);
                    intent1.putExtra("relacion", relacion);
                    startActivity(intent1);
                }
                else{
                    calendario.setRelacionCalendario(relacion);
                    Intent intent = new Intent(CrearCalendario1Activity.this, EditarCalendarioActivity.class);
                    String jsonCalendario2 = new Gson().toJson(calendario);
                    intent.putExtra("calendarioJson", jsonCalendario2);
                    startActivity(intent);
                }
            }
        };

        opcion1.setOnClickListener(onClickListener);
        opcion2.setOnClickListener(onClickListener);
        opcion3.setOnClickListener(onClickListener);
        opcion4.setOnClickListener(onClickListener);
        opcion5.setOnClickListener(onClickListener);
        opcion6.setOnClickListener(onClickListener);
        opcion7.setOnClickListener(onClickListener);
        opcion8.setOnClickListener(onClickListener);

        botonVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null){
            Intent intent = new Intent(this, BienvenidoActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }
    }
    private void inicializarVariables() {
        opcion1 = findViewById(R.id.text_usopersonal);
        opcion2 = findViewById(R.id.text_padres);
        opcion3 = findViewById(R.id.text_hijos);
        opcion4 = findViewById(R.id.text_esposos);
        opcion5 = findViewById(R.id.text_paciente);
        opcion6 = findViewById(R.id.text_amigos);
        opcion7 = findViewById(R.id.text_mascota);
        opcion8 = findViewById(R.id.text_paraotro);
        botonVolver = findViewById(R.id.boton_volver);
        mAuth = FirebaseAuth.getInstance();
    }
}
