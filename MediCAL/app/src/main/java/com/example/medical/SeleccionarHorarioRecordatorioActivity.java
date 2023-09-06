package com.example.medical;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.medical.model.Concentracion;
import com.example.medical.model.Dosis;
import com.example.medical.model.PresentacionMed;
import com.example.medical.model.Usuario;
import com.example.medical.retrofit.ConcentracionApi;
import com.example.medical.retrofit.DosisApi;
import com.example.medical.retrofit.PresentacionMedApi;
import com.example.medical.retrofit.RecordatorioApi;
import com.example.medical.retrofit.RetrofitService;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SeleccionarHorarioRecordatorioActivity extends AppCompatActivity {

    private ImageView botonVolver;
    private ImageView editarDosis;
    private TextView textoDosis;
    private Button siguiente;
    private RetrofitService retrofitService = new RetrofitService();

    // Obtener la instancia de PresentacionMedApi a partir de RetrofitService
    private PresentacionMedApi presentacionMedApi = retrofitService.getRetrofit().create(PresentacionMedApi.class);
    private RecordatorioApi recordatorioApi = retrofitService.getRetrofit().create(RecordatorioApi.class);
    private DosisApi dosisApi = retrofitService.getRetrofit().create(DosisApi.class);
    private ConcentracionApi concentracionApi = retrofitService.getRetrofit().create(ConcentracionApi.class);
    private TextView textoPresentacionMed; // Elemento de texto para mostrar el nombre
    private TimePicker timePicker;
    private String nombrePresentacion;
    private Dosis dosis= new Dosis();
    private String concentracionSeleccionada;
    private Concentracion concentracion = new Concentracion();
    private List<Concentracion> concentracionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.n44_0_seleccionar_horariorecordatorio);
        botonVolver = findViewById(R.id.boton_volver);
        textoPresentacionMed = findViewById(R.id.texto_presentacionmed); // Obtener la referencia del elemento de texto
        editarDosis = findViewById(R.id.imagen_lapiz_editar);
        timePicker = findViewById(R.id.timePicker);
        textoDosis = findViewById(R.id.texto_dosis);
        siguiente = findViewById(R.id.button_siguiente);
        dosis.setCantidadDosis(1);

        // Recibir el valor codPresen de la actividad anterior
        int codPresen = getIntent().getIntExtra("presentacionMedId", 0);


        // Realizar una llamada a la API para obtener la lista de presentaciones médicas
        presentacionMedApi.getAllPresentacionesMed().enqueue(new Callback<List<PresentacionMed>>() {
            @Override
            public void onResponse(Call<List<PresentacionMed>> call, Response<List<PresentacionMed>> response) {
                if (response.isSuccessful()) {
                    List<PresentacionMed> listaPresentaciones = response.body();

                    // Buscar en la lista de presentaciones médicas el objeto con el código correspondiente
                    nombrePresentacion = obtenerNombrePresentacion(codPresen, listaPresentaciones);

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

        concentracionApi.getAllConcentracion().enqueue(new Callback<List<Concentracion>>() {
            @Override
            public void onResponse(Call<List<Concentracion>> call, Response<List<Concentracion>> response) {
                concentracionList=response.body();
                siguiente.setOnClickListener(view ->{
                    popupEstablecerConcentracion();
                });
            }

            @Override
            public void onFailure(Call<List<Concentracion>> call, Throwable t) {
                Toast.makeText(SeleccionarHorarioRecordatorioActivity.this, "Error al cargar las concentraciones", Toast.LENGTH_SHORT).show();
            }
        });

        botonVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed(); // Volver a la actividad anterior
            }
        });

        editarDosis.setOnClickListener(view ->{
            popUpEstablecerDosis();
        });


    }

    private void popupEstablecerConcentracion() {
        View popupView = getLayoutInflater().inflate(R.layout.n44_2_establecer_concentracion, null);

        // Crear la instancia de PopupWindow
        PopupWindow popupWindow = new PopupWindow(popupView, 1000, ViewGroup.LayoutParams.WRAP_CONTENT);

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
        TextView cancelar = popupView.findViewById(R.id.cancelar);
        TextView aceptar = popupView.findViewById(R.id.aceptar);
        TextView textoMedida = popupView.findViewById(R.id.texto_medida_concentracion);

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ocultar el PopupWindow
                popupWindow.dismiss();

                // Ocultar el fondo oscurecido
                dimView.setVisibility(View.GONE);
            }
        });


        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                dimView.setVisibility(View.GONE);
            }
        });
    }
    private List<String> obtenerUnidadesMedida(List<Concentracion> concentracionList) {
        List<String> unidadesMedida = new ArrayList<>();
        for (Concentracion concentracion : concentracionList) {
            unidadesMedida.add(concentracion.getUnidadMedidaC());
        }
        return unidadesMedida;
    }
    private void popUpEstablecerDosis() {
            View popupView = getLayoutInflater().inflate(R.layout.n44_1_establecer_dosis, null);

            // Crear la instancia de PopupWindow
            PopupWindow popupWindow = new PopupWindow(popupView, 1000, ViewGroup.LayoutParams.WRAP_CONTENT);

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

            EditText valorDosis = popupView.findViewById(R.id.valor_dosis);
            ImageView cerrar = popupView.findViewById(R.id.cerrar_cruz);
            Button establecer = popupView.findViewById(R.id.button_establecer);
            TextView presentacion = popupView.findViewById(R.id.presentacion_med);
            presentacion.setText(nombrePresentacion);

            cerrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Ocultar el PopupWindow
                    popupWindow.dismiss();

                    // Ocultar el fondo oscurecido
                    dimView.setVisibility(View.GONE);
                }
            });

            establecer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!valorDosis.getText().toString().equals("")){
                        dosis.setCantidadDosis(Float.parseFloat(valorDosis.getText().toString()));
                        popupWindow.dismiss();
                        dimView.setVisibility(View.GONE);
                        textoDosis.setText(valorDosis.getText().toString());
                    }
                    else{
                        popupWindow.dismiss();
                        dimView.setVisibility(View.GONE);
                    }
                }
            });
            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    dimView.setVisibility(View.GONE);
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
