package com.example.medical;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.medical.model.RegistroRecordatorio;
import com.example.medical.retrofit.RegistroRecordatorioApi;
import com.example.medical.retrofit.RetrofitService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditarUnaDosisActivity extends AppCompatActivity {
    private ImageView botonCerrar;
    private TextView nombreMedicamento;
    private TextView fechaEsperada;
    private TextView fechaReal;
    private TimePicker timePicker;
    private ImageView menor;
    private ImageView mayor;
    private TextView fechaHoy;
    private Button guardar;
    private RegistroRecordatorio registro;
    private LocalDateTime fechaRegistro;
    private LocalDateTime fechaGuardada = null;
    private RetrofitService retrofitService = new RetrofitService();
    private RegistroRecordatorioApi registroRecordatorioApi = retrofitService.getRetrofit().create(RegistroRecordatorioApi.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.n62_editar_una_dosis);//cambiar esta linea por el nombre del layout a probar
        inicializarVariables();

        botonCerrar.setOnClickListener(view ->{
            onBackPressed();
        });
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtiene la hora y los minutos seleccionados en el TimePicker
                int horaSeleccionada = timePicker.getHour();
                int minutosSeleccionados = timePicker.getMinute();

                // Crea un LocalTime con la hora y minutos seleccionados
                LocalTime horaMinutos = LocalTime.of(horaSeleccionada, minutosSeleccionados);

                // Combina la fecha guardada (fechaGuardada) con la hora y minutos seleccionados para obtener un LocalDateTime completo
                fechaGuardada = LocalDateTime.of(fechaGuardada.toLocalDate(), horaMinutos);

                registro.setFechaTomaEsperada(fechaGuardada);

                registroRecordatorioApi.save(registro).enqueue(new Callback<RegistroRecordatorio>() {
                    @Override
                    public void onResponse(Call<RegistroRecordatorio> call, Response<RegistroRecordatorio> response) {
                        popUpAplazado(response.body());
                    }

                    @Override
                    public void onFailure(Call<RegistroRecordatorio> call, Throwable t) {
                        Toast.makeText(EditarUnaDosisActivity.this, "Error al modificar el recordatorio", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    private void inicializarVariables() {
        botonCerrar = findViewById(R.id.boton_cerrar);
        nombreMedicamento = findViewById(R.id.texto_medicinaSal);
        fechaEsperada = findViewById(R.id.texto_fechaEs);
        fechaReal = findViewById(R.id.texto_fechaReal);
        timePicker = findViewById(R.id.timePicker);
        menor = findViewById(R.id.imageMenor);
        mayor = findViewById(R.id.imageMayor);
        fechaHoy = findViewById(R.id.textFecha);
        guardar = findViewById(R.id.button_guardar);

        registroRecordatorioApi.getByCodRegistroRecordatorio(getIntent().getIntExtra("codRegistroRecordatorio", 0)).enqueue(new Callback<RegistroRecordatorio>() {
            @Override
            public void onResponse(Call<RegistroRecordatorio> call, Response<RegistroRecordatorio> response) {
                registro = response.body();
                nombreMedicamento.setText(registro.getRecordatorio().getMedicamento().getNombreMedicamento());
                DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.getDefault());
                String fechaEsperadaFormateada = registro.getFechaTomaEsperada().format(formatter2);
                fechaEsperada.setText("Fecha Esperada: " + fechaEsperadaFormateada);

                // Verifica si la fecha de toma real está presente y, si es así, la formatea y la muestra en la vista de texto.
                if (registro.getFechaTomaReal() != null) {
                    String fechaTomaRealFormateada = registro.getFechaTomaReal().format(formatter2);
                    fechaReal.setText("Fecha Real: " + fechaTomaRealFormateada);
                } else {
                    fechaReal.setText("Fecha Real: Toma no registrada");
                }
                fechaRegistro = registro.getFechaTomaEsperada();
                fechaGuardada = fechaRegistro;
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.getDefault());
                String fechaFormateada = fechaRegistro.format(formatter);
                fechaHoy.setText(fechaFormateada);
            }

            @Override
            public void onFailure(Call<RegistroRecordatorio> call, Throwable t) {
                Toast.makeText(EditarUnaDosisActivity.this, "Error al cargar el recordatorio", Toast.LENGTH_SHORT).show();
            }
        });

        // Agrega un OnClickListener a la vista "menor"
        // Agrega un OnClickListener a la vista "menor"
        menor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Resta un día a la fecha guardada
                fechaGuardada = fechaGuardada.minusDays(1);

                // Formatea la nueva fecha y la configura en el TextView
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.getDefault());
                String nuevaFechaTexto = fechaGuardada.format(formatter);
                fechaHoy.setText(nuevaFechaTexto);
            }
        });

// Agrega un OnClickListener a la vista "mayor"
        mayor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Suma un día a la fecha guardada
                fechaGuardada = fechaGuardada.plusDays(1);

                // Formatea la nueva fecha y la configura en el TextView
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.getDefault());
                String nuevaFechaTexto = fechaGuardada.format(formatter);
                fechaHoy.setText(nuevaFechaTexto);
            }
        });


    }
    private void popUpAplazado(RegistroRecordatorio registro) {
        View popupView = getLayoutInflater().inflate(R.layout.n55_2_aplazo_exitoso, null);

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

        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(false);
        // Mostrar el popup en la ubicación deseada
        popupWindow.showAtLocation(rootView, Gravity.CENTER, 0, 0);
        ImageView botonCerrar = popupView.findViewById(R.id.boton_cerrar);
        TextView nombreMedicamento = popupView.findViewById(R.id.text_nombremedicamento);
        TextView textoAplazado = popupView.findViewById(R.id.text_tiempoaplazo);
        nombreMedicamento.setText(registro.getRecordatorio().getMedicamento().getNombreMedicamento());
        textoAplazado.setText("El registro fue modificado exitosamente");
        botonCerrar.setOnClickListener(view ->{
            popupWindow.dismiss();
            dimView.setVisibility(View.GONE);
            Intent intent = new Intent(EditarUnaDosisActivity.this, InicioCalendarioActivity.class);
            intent.putExtra("codCalendario", getIntent().getIntExtra("codCalendario", 0));
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                dimView.setVisibility(View.GONE);
                Intent intent = new Intent(EditarUnaDosisActivity.this, InicioCalendarioActivity.class);
                intent.putExtra("codCalendario", getIntent().getIntExtra("codCalendario", 0));
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }
}