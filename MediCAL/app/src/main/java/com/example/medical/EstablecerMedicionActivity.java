package com.example.medical;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class EstablecerMedicionActivity extends AppCompatActivity {

    private Calendar selectedDate = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.n76_0_establecer_medicion);

        EditText textoLineaEditText = findViewById(R.id.texto_linea);

        LinearLayout layoutHora = findViewById(R.id.layout_hora);
        layoutHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupCambiarFechaHora();
            }
        });

        // Configura el clic de la flecha hacia atrás
        findViewById(R.id.boton_volver).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Vuelve a la pantalla anterior (Puede ser tu pantalla 73)
                onBackPressed();
            }
        });

        // Obtener los datos pasados desde la actividad anterior
        Intent intent = getIntent();
        if (intent != null) {
            String nombreMedicion = intent.getStringExtra("nombreMedicion");
            String unidadMedida = intent.getStringExtra("unidadMedida");

            // Actualizar el nombre de la medición en el TextView correspondiente
            TextView nombreMedicionTextView = findViewById(R.id.texto_editar);
            nombreMedicionTextView.setText(nombreMedicion);

            // Actualizar la unidad de medida en el TextView correspondiente
            TextView unidadMedidaTextView = findViewById(R.id.texto_unidad);
            // Actualizar la unidad de medida en el TextView correspondiente
            unidadMedidaTextView.setText("(" + unidadMedida + ")");
        }

        // Obtener la fecha y hora actual
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM", Locale.getDefault());
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        Date currentDate = new Date();

        // Configurar los TextView con la fecha y hora actual
        TextView fechaTextView = findViewById(R.id.texto_fecha);
        fechaTextView.setText(dateFormat.format(currentDate));

        TextView horaTextView = findViewById(R.id.texto_hora);
        horaTextView.setText(timeFormat.format(currentDate));
        Button buttonAgregar = findViewById(R.id.button_siguiente);

        buttonAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Obtener el valor ingresado en el EditText
                EditText textoLineaEditText = findViewById(R.id.texto_linea);
                String inputText = textoLineaEditText.getText().toString();

                try {
                    double inputValue = Double.parseDouble(inputText);
                    // Aquí puedes realizar acciones con el valor numérico ingresado
                    // Por ejemplo, agregar la medición a la base de datos o realizar algún cálculo
                } catch (NumberFormatException e) {
                    // El valor ingresado no es un número válido
                    // Puedes mostrar un mensaje de error o realizar otras acciones apropiadas
                    Toast.makeText(EstablecerMedicionActivity.this, "Ingrese un número válido", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    // Dentro de EstablecerMedicionActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            String selectedDate = data.getStringExtra("selectedDate");
            String selectedTime = data.getStringExtra("selectedTime");

            // Actualizar los TextView con la fecha y hora seleccionadas
            TextView fechaTextView = findViewById(R.id.texto_fecha);
            fechaTextView.setText(selectedDate);

            TextView horaTextView = findViewById(R.id.texto_hora);
            horaTextView.setText(selectedTime);
        }
    }

    private void popupCambiarFechaHora() {
        View popupView = getLayoutInflater().inflate(R.layout.n76_1_pop_up_cambiar_fecha, null);

        // Crear la instancia de PopupWindow
        PopupWindow popupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setElevation(10.0f); // Agrega elevación para el efecto de sombra

        // Hacer que el popup sea enfocable
        popupWindow.setFocusable(true);

        // Configurar animación para oscurecer el fondo
        View dimView = findViewById(R.id.dim_view);
        dimView.setVisibility(View.VISIBLE);

        // Configurar la animación de entrada del popup
        Animation scaleAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.popup);
        popupView.startAnimation(scaleAnimation);

        // Mostrar el popup en la ubicación deseada
        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);

        TimePicker timePicker = popupView.findViewById(R.id.timePicker);
        TextView cancelar = popupView.findViewById(R.id.cancelar);
        TextView aceptarTextView = popupView.findViewById(R.id.aceptar);

        // Obtener las referencias a las vistas dentro del popup
        TextView textFecha = popupView.findViewById(R.id.textFecha);
        ImageView imageMayor = popupView.findViewById(R.id.imageMayor);
        ImageView imageMenor = popupView.findViewById(R.id.imageMenor);

        // Configurar la fecha seleccionada como la fecha actual
        selectedDate = Calendar.getInstance();
        updateDateTextView(textFecha);

        // Obtener la fecha actual
        Calendar currentDate = Calendar.getInstance();

        // Comparar con la fecha seleccionada
        if (selectedDate.get(Calendar.YEAR) == currentDate.get(Calendar.YEAR) &&
                selectedDate.get(Calendar.MONTH) == currentDate.get(Calendar.MONTH) &&
                selectedDate.get(Calendar.DAY_OF_MONTH) == currentDate.get(Calendar.DAY_OF_MONTH)) {
            // Si es la fecha actual, ajustar la opacidad de la flecha
            imageMayor.setAlpha(0.5f); // Cambiar alpha según sea necesario
        } else {
            // Si no es la fecha actual, dejar la opacidad por defecto
            imageMayor.setAlpha(1.0f);
        }

        // Cambiar la opacidad de la imagenMayor si la fecha seleccionada no es la fecha actual
        if (!selectedDate.equals(currentDate)) {
            imageMayor.setAlpha(0.5f); // Cambiar alpha según sea necesario
        }

        // Configurar la lógica para retroceder y avanzar en las fechas
        imageMenor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Restar un día a la fecha seleccionada
                selectedDate.add(Calendar.DAY_OF_MONTH, -1);
                updateDateTextView(textFecha);
            }
        });

        imageMayor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Obtener la fecha actual
                Calendar currentDate = Calendar.getInstance();

                // Comparar con la fecha seleccionada
                if (selectedDate.before(currentDate) ||
                        (selectedDate.get(Calendar.YEAR) == currentDate.get(Calendar.YEAR) &&
                                selectedDate.get(Calendar.MONTH) == currentDate.get(Calendar.MONTH) &&
                                selectedDate.get(Calendar.DAY_OF_MONTH) < currentDate.get(Calendar.DAY_OF_MONTH))) {

                    selectedDate.add(Calendar.DAY_OF_MONTH, 1); // Avanzar un día
                    updateDateTextView(textFecha);

                    if (selectedDate.after(currentDate)) {
                        // Si la fecha seleccionada es después de la fecha actual,
                        // ajustarla a la fecha actual
                        selectedDate = (Calendar) currentDate.clone();
                        updateDateTextView(textFecha);
                    }

                    // Cambiar la opacidad de la imagenMayor si la fecha seleccionada es igual a la fecha actual
                    if (selectedDate.get(Calendar.YEAR) == currentDate.get(Calendar.YEAR) &&
                            selectedDate.get(Calendar.MONTH) == currentDate.get(Calendar.MONTH) &&
                            selectedDate.get(Calendar.DAY_OF_MONTH) == currentDate.get(Calendar.DAY_OF_MONTH)) {
                        imageMayor.setAlpha(0.5f); // Cambiar alpha según sea necesario
                    } else {
                        imageMayor.setAlpha(1.0f); // Restaurar la opacidad por defecto
                    }
                }
            }
        });


        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ocultar el PopupWindow
                popupWindow.dismiss();

                // Ocultar el fondo oscurecido
                dimView.setVisibility(View.GONE);
            }
        });

        aceptarTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Obtener la fecha y hora actual
                int hour = timePicker.getCurrentHour();
                int minute = timePicker.getCurrentMinute();

                // Obtener la fecha y hora seleccionadas
                int year = selectedDate.get(Calendar.YEAR);
                int month = selectedDate.get(Calendar.MONTH);
                int day = selectedDate.get(Calendar.DAY_OF_MONTH);

                // Formatear la fecha y la hora como cadenas
                String formattedDate = day + " " + getMonthName(month) + ", " + year;
                String formattedTime = hour + ":" + minute;

                // Enviar la fecha y hora formateadas a EstablecerMedicionActivity
                Intent resultIntent = new Intent();
                resultIntent.putExtra("selectedDate", formattedDate);
                resultIntent.putExtra("selectedTime", formattedTime);
                setResult(RESULT_OK, resultIntent);

                // Actualizar manualmente los TextView con los nuevos datos
                TextView fechaTextView = findViewById(R.id.texto_fecha);
                fechaTextView.setText(formattedDate);

                TextView horaTextView = findViewById(R.id.texto_hora);
                horaTextView.setText(formattedTime);

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

    private void updateDateTextView(TextView textFecha) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, d MMM", Locale.getDefault());
        String formattedDate = dateFormat.format(selectedDate.getTime());
        textFecha.setText(formattedDate);
    }
    private String getMonthName(int month) {
        String[] months = getResources().getStringArray(R.array.months_array);
        return months[month];
    }


}

