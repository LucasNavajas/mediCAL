package com.example.medical;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
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
    private TextView textDuracion;
    private RelativeLayout instrucciones;
    private TextView textInstrucciones;
    private RelativeLayout imagenes;
    private TextView textImagenes;
    private RelativeLayout inventario;
    private TextView textInventario;
    private ImageView tickDuracion;
    private ImageView tickInstrucciones;
    private ImageView tickImagen;
    private ImageView tickInventario;
    private Recordatorio recordatorio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.n54_0_visualizacion_de_datos_completados);//cambiar esta linea por el nombre del layout a probar
        TextView nombreMedicamento = findViewById(R.id.nombreMedicamento);
        nombreMedicamento.setText(getIntent().getStringExtra("nombreMedicamento"));
        inicializarVariables();

        botonVolver.setOnClickListener(view ->{
            onBackPressed();
        });

        duracion.setOnClickListener(view ->{
            Intent intent = new Intent(AgregarDatosObligatoriosActivity.this, AgregarFechaInicioRecordatorioActivity.class);
            intent.putExtra("codRecordatorio", getIntent().getIntExtra("codRecordatorio", 0));
            intent.putExtra("presentacionMedId", getIntent().getIntExtra("presentacionMedId", 0));
            intent.putExtra("nombreMedicamento", getIntent().getStringExtra("nombreMedicamento"));
            startActivity(intent);
        });
        textDuracion.setOnClickListener(view ->{
            Intent intent = new Intent(AgregarDatosObligatoriosActivity.this, AgregarFechaInicioRecordatorioActivity.class);
            intent.putExtra("codRecordatorio", getIntent().getIntExtra("codRecordatorio", 0));
            intent.putExtra("presentacionMedId", getIntent().getIntExtra("presentacionMedId", 0));
            intent.putExtra("nombreMedicamento", getIntent().getStringExtra("nombreMedicamento"));
            startActivity(intent);
        });

        instrucciones.setOnClickListener(view ->{
            Intent intent = new Intent(AgregarDatosObligatoriosActivity.this, AgregarInstruccionesActivity.class);
            intent.putExtra("codRecordatorio", getIntent().getIntExtra("codRecordatorio", 0));
            intent.putExtra("presentacionMedId", getIntent().getIntExtra("presentacionMedId", 0));
            intent.putExtra("nombreMedicamento", getIntent().getStringExtra("nombreMedicamento"));
            startActivity(intent);
        });

        textInstrucciones.setOnClickListener(view ->{
            Intent intent = new Intent(AgregarDatosObligatoriosActivity.this, AgregarInstruccionesActivity.class);
            intent.putExtra("codRecordatorio", getIntent().getIntExtra("codRecordatorio", 0));
            intent.putExtra("presentacionMedId", getIntent().getIntExtra("presentacionMedId", 0));
            intent.putExtra("nombreMedicamento", getIntent().getStringExtra("nombreMedicamento"));
            startActivity(intent);
        });

        imagenes.setOnClickListener(view ->{
            Intent intent = new Intent(AgregarDatosObligatoriosActivity.this, AgregarImagenRecordatorioActivity.class);
            intent.putExtra("codRecordatorio", getIntent().getIntExtra("codRecordatorio", 0));
            intent.putExtra("presentacionMedId", getIntent().getIntExtra("presentacionMedId", 0));
            intent.putExtra("nombreMedicamento", getIntent().getStringExtra("nombreMedicamento"));
            startActivity(intent);
        });
        textImagenes.setOnClickListener(view ->{
            Intent intent = new Intent(AgregarDatosObligatoriosActivity.this, AgregarImagenRecordatorioActivity.class);
            intent.putExtra("codRecordatorio", getIntent().getIntExtra("codRecordatorio", 0));
            intent.putExtra("presentacionMedId", getIntent().getIntExtra("presentacionMedId", 0));
            intent.putExtra("nombreMedicamento", getIntent().getStringExtra("nombreMedicamento"));
            startActivity(intent);
        });
        inventario.setOnClickListener(view ->{
            Intent intent = new Intent(AgregarDatosObligatoriosActivity.this, AgregarInventarioActivity.class);
            intent.putExtra("codRecordatorio", getIntent().getIntExtra("codRecordatorio", 0));
            intent.putExtra("presentacionMedId", getIntent().getIntExtra("presentacionMedId", 0));
            intent.putExtra("nombreMedicamento", getIntent().getStringExtra("nombreMedicamento"));
            startActivity(intent);
        });
        textInventario.setOnClickListener(view ->{
            Intent intent = new Intent(AgregarDatosObligatoriosActivity.this, AgregarInventarioActivity.class);
            intent.putExtra("codRecordatorio", getIntent().getIntExtra("codRecordatorio", 0));
            intent.putExtra("presentacionMedId", getIntent().getIntExtra("presentacionMedId", 0));
            intent.putExtra("nombreMedicamento", getIntent().getStringExtra("nombreMedicamento"));
            startActivity(intent);
        });
        hecho.setOnClickListener(view ->{
            popUpExitoRecordatorio();
        });
    }

    private void popUpExitoRecordatorio() {
        View popupView = getLayoutInflater().inflate(R.layout.n54_1_popup_se_ha_guardado_el_recordatorio, null);


        // Crear la instancia de PopupWindow
        PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

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

        Button agregarOtro = popupView.findViewById(R.id.button_anadir_mas);
        Button volverInicio = popupView.findViewById(R.id.button_volver_inicio);
        agregarOtro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ocultar el PopupWindow
                popupWindow.dismiss();

                // Ocultar el fondo oscurecido
                dimView.setVisibility(View.GONE);
                Intent intent = new Intent(AgregarDatosObligatoriosActivity.this, ElegirMedicamentoActivity.class);
                intent.putExtra("codUsuario", recordatorio.getCalendario().getUsuario().getCodUsuario());
                intent.putExtra("codCalendario", recordatorio.getCalendario().getCodCalendario());
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        });
        volverInicio.setOnClickListener(view ->{
            popupWindow.dismiss();

            // Ocultar el fondo oscurecido
            dimView.setVisibility(View.GONE);
            Intent intent = new Intent(AgregarDatosObligatoriosActivity.this, InicioCalendarioActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                dimView.setVisibility(View.GONE);
            }
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
        textDuracion = findViewById(R.id.text_duracion);
        textInstrucciones = findViewById(R.id.text_instrucciones);
        textImagenes = findViewById(R.id.text_imagen);
        textInventario = findViewById(R.id.text_recarga);
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
