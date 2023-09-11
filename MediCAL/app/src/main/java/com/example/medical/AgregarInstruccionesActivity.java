package com.example.medical;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AgregarInstruccionesActivity extends AppCompatActivity {
    private ImageView botonVolver;
    private TextView antes;
    private TextView durante;
    private TextView despues;
    private TextView noImporta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.n51_establecer_instrucciones);//cambiar esta linea por el nombre del layout a probar
        TextView nombreMedicamento = findViewById(R.id.nombreMedicamento);
        nombreMedicamento.setText(getIntent().getStringExtra("nombreMedicamento"));
        inicializarVariables();

        botonVolver.setOnClickListener(view ->{onBackPressed();});
        View.OnClickListener onClickListener = new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                TextView textView = findViewById(view.getId());
                Intent intent = new Intent(AgregarInstruccionesActivity.this, AgregarIndicacionesActivity.class);
                intent.putExtra("instrucciones", textView.getText().toString());
                intent.putExtra("codRecordatorio", getIntent().getIntExtra("codRecordatorio", 0));
                intent.putExtra("presentacionMedId", getIntent().getIntExtra("presentacionMedId", 0));
                intent.putExtra("nombreMedicamento", getIntent().getStringExtra("nombreMedicamento"));
                startActivity(intent);
            }
        };
        antes.setOnClickListener(onClickListener);
        durante.setOnClickListener(onClickListener);
        despues.setOnClickListener(onClickListener);
        noImporta.setOnClickListener(onClickListener);
    }

    private void inicializarVariables() {
        botonVolver = findViewById(R.id.boton_volver);
        antes = findViewById(R.id.text_antes);
        durante = findViewById(R.id.text_durante);
        despues = findViewById(R.id.text_despues);
        noImporta = findViewById(R.id.text_noimporta);

    }


}