package com.example.medical;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medical.adapter.PresentacionMedAdapter;
import com.example.medical.model.PresentacionMed;
import com.example.medical.retrofit.PresentacionMedApi;
import com.example.medical.retrofit.RetrofitService;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ElegirFrecuenciaActivity extends AppCompatActivity {

    private ImageView botonVolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.n38_seleccionar_frecuencia);

        botonVolver = findViewById(R.id.boton_volver);

        botonVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed(); // Volver a la actividad anterior
            }
        });

        // Agregar OnClickListener a los TextView
        TextView textViewXHoras = findViewById(R.id.text_x_horas);
        TextView textViewXDias = findViewById(R.id.text_x_dias);
        TextView textViewXSemanas = findViewById(R.id.text_x_semanas);
        TextView textViewXMeses = findViewById(R.id.text_x_meses);
        TextView textViewCicloRecurrente = findViewById(R.id.text_ciclo_recurrente);
        TextView textViewSegunSeaNecesario = findViewById(R.id.text_segun_sea_necesario);

        textViewXHoras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Iniciar una nueva actividad para la opción "Cada X horas"
                Intent intent = new Intent(ElegirFrecuenciaActivity.this, FrecuenciaxhorasActivity.class);
                startActivity(intent);
            }
        });

        textViewXDias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Iniciar una nueva actividad para la opción "Cada X días"
                Intent intent = new Intent(ElegirFrecuenciaActivity.this, FrecuenciaxdiasActivity.class);
                startActivity(intent);
            }
        });

        textViewXSemanas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Iniciar una nueva actividad para la opción "Cada X semanas"
                Intent intent = new Intent(ElegirFrecuenciaActivity.this, FrecuenciaxsemanasActivity.class);
                startActivity(intent);
            }
        });

        textViewXMeses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Iniciar una nueva actividad para la opción "Cada X meses"
                Intent intent = new Intent(ElegirFrecuenciaActivity.this, FrecuenciaxmesesActivity.class);
                startActivity(intent);
            }
        });

        textViewCicloRecurrente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Iniciar una nueva actividad para la opción "Ciclo recurrente"
                Intent intent = new Intent(ElegirFrecuenciaActivity.this, FrecuenciaxciclosActivity.class);
                startActivity(intent);
            }
        });

        textViewSegunSeaNecesario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Iniciar una nueva actividad para la opción "Según sea necesario"
                Intent intent = new Intent(ElegirFrecuenciaActivity.this, FrecuenciaxnecesidadActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed(); // Volver a la actividad anterior
    }
}

