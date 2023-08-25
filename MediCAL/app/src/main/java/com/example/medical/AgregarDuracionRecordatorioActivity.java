package com.example.medical;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AgregarDuracionRecordatorioActivity extends AppCompatActivity {
    private TextView cincoDias;
    private TextView sieteDias;
    private TextView diezDias;
    private TextView treintaDias;
    private TextView seisMeses;
    private TextView doceMeses;
    private TextView veinticuatroMeses;
    private TextView elegirDias;
    private TextView tratamientoContinuo;
    private ImageView botonVolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.n47_establecer_duracion);//cambiar esta linea por el nombre del layout a probar
        inicializarVariables();
        botonVolver.setOnClickListener(view ->{onBackPressed();});
    }

    private void inicializarVariables() {
        cincoDias = findViewById(R.id.text_cinco_dias);
        sieteDias = findViewById(R.id.text_una_semana);
        diezDias = findViewById(R.id.text_diez_dias);
        treintaDias = findViewById(R.id.text_treinta_dias);
        seisMeses = findViewById(R.id.text_seis_meses);
        doceMeses = findViewById(R.id.text_doce_meses);
        elegirDias = findViewById(R.id.text_elegir);
        tratamientoContinuo = findViewById(R.id.text_continuo);
        veinticuatroMeses = findViewById(R.id.text_veinticuatro_meses);
        botonVolver = findViewById(R.id.boton_volver);

    }

    View.OnClickListener onClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            switch(view.getId()){
                case R.id.text_elegir:
                    Intent intent = new Intent(AgregarDuracionRecordatorioActivity.this, ElegirDiasActivity.class);
                    intent.putExtra("year", getIntent().getIntExtra("year", 0));
                    intent.putExtra("month", getIntent().getIntExtra("month", 0));
                    intent.putExtra("day", getIntent().getIntExtra("day", 0));
                    startActivity(intent);
                    break;
                default:
                    int duracion = obtenerEnteroDuracion(view.getId());
                    Intent intent2 = new Intent(AgregarDuracionRecordatorioActivity.this, ElegirDiasActivity.class);
                    intent2.putExtra("year", getIntent().getIntExtra("year", 0));
                    intent2.putExtra("month", getIntent().getIntExtra("month", 0));
                    intent2.putExtra("day", getIntent().getIntExtra("day", 0));
                    intent2.putExtra("duracion", duracion);
                    startActivity(intent2);
            }
        }
    };

    private int obtenerEnteroDuracion(int id) {
        switch (id){
            case R.id.text_cinco_dias:
                return 5;
            case R.id.text_una_semana:
                return 7;
            case R.id.text_diez_dias:
                return 10;
            case R.id.text_treinta_dias:
                return 30;
            case R.id.text_seis_meses:
                return 180;
            case R.id.text_doce_meses:
                return 360;
            case R.id.text_veinticuatro_meses:
                return 720;
            default:
                return 0;
        }
    }

}
