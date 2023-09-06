package com.example.medical;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.medical.model.Recordatorio;
import com.example.medical.retrofit.PresentacionMedApi;
import com.example.medical.retrofit.RecordatorioApi;
import com.example.medical.retrofit.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AgregarDatosObligatoriosActivity extends AppCompatActivity {
    private boolean duracionLista = false;
    private boolean instruccionesListas = false;
    private RetrofitService retrofitService = new RetrofitService();

    private RecordatorioApi recordatorioApi = retrofitService.getRetrofit().create(RecordatorioApi.class);
    private ImageView botonVolver;
    private Button hecho;
    private RelativeLayout duracion;
    private RelativeLayout instrucciones;
    private RelativeLayout imagenes;
    private RelativeLayout inventario;
    private ImageView tickDuracion;
    private ImageView tickInstrucciones;
    private ImageView tickImagen;
    private ImageView tickInventario;
    private Recordatorio recordatorio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.n54_0_visualizacion_de_datos_completados);//cambiar esta linea por el nombre del layout a probar
        inicializarVariables();

        botonVolver.setOnClickListener(view ->{
            onBackPressed();
        });

        duracion.setOnClickListener(view ->{
            Intent intent = new Intent(AgregarDatosObligatoriosActivity.this, AgregarFechaInicioRecordatorioActivity.class);
            intent.putExtra("codRecordatorio", getIntent().getIntExtra("codRecordatorio", 0));
            startActivity(intent);
        });
    }

    private void inicializarVariables() {
        botonVolver = findViewById(R.id.boton_volver);
        hecho = findViewById(R.id.button_hecho);
        duracion = findViewById(R.id.opcion_duracion);
        instrucciones = findViewById(R.id.opcion_instruccion);
        imagenes = findViewById(R.id.opcion_imagen);
        inventario = findViewById(R.id.opcion_recarga);
        tickDuracion = findViewById(R.id.tick_duracion);
        tickInstrucciones = findViewById(R.id.tick_instruccion);
        tickImagen = findViewById(R.id.tick_imagen);
        tickInventario = findViewById(R.id.tick_recarga);
        recordatorioApi.getByCodRecordatorio(getIntent().getIntExtra("codRecordatorio", 0)).enqueue(new Callback<Recordatorio>() {
            @Override
            public void onResponse(Call<Recordatorio> call, Response<Recordatorio> response) {
                recordatorio = response.body();
                if(recordatorio.getDuracionRecordatorio()!=0){
                    tickDuracion.setVisibility(View.VISIBLE);
                    duracionLista = true;
                }
                if(recordatorio.getInstruccion()!=null){
                    tickInstrucciones.setVisibility(View.VISIBLE);
                    instruccionesListas=true;
                }
                if(recordatorio.getImagen()!=null){
                    tickImagen.setVisibility(View.VISIBLE);
                }
                if(recordatorio.getInventario()!=null){
                    tickInventario.setVisibility(View.VISIBLE);
                }

                if(duracionLista == true && instruccionesListas == true){
                    hecho.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<Recordatorio> call, Throwable t) {
                Toast.makeText(AgregarDatosObligatoriosActivity.this, "Error al cargar el recordatorio", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
