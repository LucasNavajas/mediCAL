package com.example.medical;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class CambiarFechaHoraActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.n76_1_pop_up_cambiar_fecha);

        // Configura el título del diálogo (Cambiar fecha y hora)
        TextView tituloTextView = findViewById(R.id.titulo);
        tituloTextView.setText("Cambiar fecha");

        // Configurar el clic en el TextView de fecha
        TextView fechaTextView = findViewById(R.id.textFecha);
        fechaTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Mostrar el DatePicker para cambiar la fecha
                showDatePickerDialog();
            }
        });

        // Configurar clic en el botón ACEPTAR para obtener la fecha seleccionada y cerrar la actividad
        TextView aceptarTextView = findViewById(R.id.aceptar);
        aceptarTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Obtener la fecha actual
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                // Enviar la fecha seleccionada a EstablecerMedicionActivity
                Intent resultIntent = new Intent();
                resultIntent.putExtra("selectedDate", day + " " + getMonthName(month) + ", " + year);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });

        // Configurar clic en el botón CANCELAR para cerrar la actividad sin cambios
        TextView cancelarTextView = findViewById(R.id.cancelar);
        cancelarTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }

    // Método para obtener el nombre del mes a partir del índice
    private String getMonthName(int month) {
        String[] months = getResources().getStringArray(R.array.months_array); // Definir el array en strings.xml
        return months[month];
    }

    // Método para mostrar el DatePicker
    private void showDatePickerDialog() {
        // Obtener la fecha actual
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Crear y mostrar el DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                // Actualizar la fecha seleccionada en el TextView de fecha
                String monthName = getMonthName(month);
                String selectedDate = "Hoy, " + dayOfMonth + " " + monthName;
                TextView fechaTextView = findViewById(R.id.textFecha);
                fechaTextView.setText(selectedDate);
            }
        }, year, month, day);
        datePickerDialog.show();
    }
}
