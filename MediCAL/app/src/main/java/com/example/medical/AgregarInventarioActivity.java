package com.example.medical;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.medical.model.PresentacionMed;
import com.example.medical.retrofit.PresentacionMedApi;
import com.example.medical.retrofit.RecordatorioApi;
import com.example.medical.retrofit.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AgregarInventarioActivity extends AppCompatActivity {
    private RetrofitService retrofitService = new RetrofitService();

    private PresentacionMedApi presentacionMedApi = retrofitService.getRetrofit().create(PresentacionMedApi.class);
    private EditText inventarioReal;
    private TextView presentacionMed;
    private Button siguiente;
    private ImageView botonVolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.n49_establecer_recarga);//cambiar esta linea por el nombre del layout a probar
        inicializarVariables();
        botonVolver.setOnClickListener(view ->{onBackPressed();});
        siguiente.setOnClickListener(view ->{
            if(!inventarioReal.getText().toString().equals("")){
                int valorInventarioReal = Integer.parseInt(inventarioReal.getText().toString());
                if(valorInventarioReal>0){
                    Intent intent = new Intent(AgregarInventarioActivity.this, AgregarAlertaInventarioActivity.class);
                    intent.putExtra("codRecordatorio", getIntent().getIntExtra("codRecordatorio",0));
                    intent.putExtra("presentacionMedId", getIntent().getIntExtra("presentacionMedId", 0));
                    intent.putExtra("valorInventarioReal", valorInventarioReal);
                    startActivity(intent);
                }
            }
        });
    }

    private void inicializarVariables() {
        inventarioReal = findViewById(R.id.inventarioReal);
        presentacionMed = findViewById(R.id.text_presentaciones);
        siguiente = findViewById(R.id.button_siguiente);
        botonVolver = findViewById(R.id.boton_volver);
        presentacionMedApi.getPresentacionMedByCod(getIntent().getIntExtra("presentacionMedId", 0)).enqueue(new Callback<PresentacionMed>() {
            @Override
            public void onResponse(Call<PresentacionMed> call, Response<PresentacionMed> response) {
                presentacionMed.setText("Quedan " + response.body().getNombrePresentacionMed()+ "(s)");
            }

            @Override
            public void onFailure(Call<PresentacionMed> call, Throwable t) {
                Toast.makeText(AgregarInventarioActivity.this, "Error al cargar la presentacion", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
