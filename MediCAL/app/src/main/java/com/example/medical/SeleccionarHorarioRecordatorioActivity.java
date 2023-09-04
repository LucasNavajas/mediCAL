package com.example.medical;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.medical.model.PresentacionMed;
import com.example.medical.retrofit.PresentacionMedApi;
import com.example.medical.retrofit.RetrofitService;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SeleccionarHorarioRecordatorioActivity extends AppCompatActivity {

    private ImageView botonVolver;
    private PresentacionMedApi presentacionMedApi; // Instancia de tu servicio Retrofit
    private TextView textoPresentacionMed; // Elemento de texto para mostrar el nombre

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.n44_0_seleccionar_horariorecordatorio);

        botonVolver = findViewById(R.id.boton_volver);
        textoPresentacionMed = findViewById(R.id.texto_presentacionmed); // Obtener la referencia del elemento de texto

        // Recibir el valor codPresen de la actividad anterior
        int codPresen = getIntent().getIntExtra("presentacionMedId", 0);

        // Crear una instancia de RetrofitService
        RetrofitService retrofitService = new RetrofitService();

        // Obtener la instancia de PresentacionMedApi a partir de RetrofitService
        presentacionMedApi = retrofitService.getRetrofit().create(PresentacionMedApi.class);

        // Realizar una llamada a la API para obtener la lista de presentaciones médicas
        presentacionMedApi.getAllPresentacionesMed().enqueue(new Callback<List<PresentacionMed>>() {
            @Override
            public void onResponse(Call<List<PresentacionMed>> call, Response<List<PresentacionMed>> response) {
                if (response.isSuccessful()) {
                    List<PresentacionMed> listaPresentaciones = response.body();

                    // Buscar en la lista de presentaciones médicas el objeto con el código correspondiente
                    String nombrePresentacion = obtenerNombrePresentacion(codPresen, listaPresentaciones);

                    // Mostrar el nombre de la presentación en el elemento de texto
                    if (nombrePresentacion != null) {
                        textoPresentacionMed.setText(nombrePresentacion);
                    } else { //"Código de presentación no encontrado"
                        textoPresentacionMed.setText("");
                    }
                } else {
                    // Manejar error de la llamada a la API "Error al obtener datos de la API"
                    textoPresentacionMed.setText("");
                }
            }

            @Override
            public void onFailure(Call<List<PresentacionMed>> call, Throwable t) {
                // Manejar error de la llamada a la API "Error de conexión"
                textoPresentacionMed.setText("");
            }
        });

        botonVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed(); // Volver a la actividad anterior
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed(); // Volver a la actividad anterior
    }

    private String obtenerNombrePresentacion(int codPresen, List<PresentacionMed> listaPresentaciones) {
        // Buscar en la lista de presentaciones médicas el objeto con el código correspondiente
        for (PresentacionMed presentacion : listaPresentaciones) {
            if (presentacion.getCodPresentacionMed() == codPresen) {
                return presentacion.getNombrePresentacionMed();
            }
        }
        return null; // Retornar null si el código no se encuentra
    }
}
