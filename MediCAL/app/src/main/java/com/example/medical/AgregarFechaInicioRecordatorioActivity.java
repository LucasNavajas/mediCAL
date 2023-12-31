package com.example.medical;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class AgregarFechaInicioRecordatorioActivity extends AppCompatActivity {
    private ImageView botonVolver;
    private DatePicker datePicker;
    private Button siguiente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.n46_agregar_duracion);//cambiar esta linea por el nombre del layout a probar
        TextView nombreMedicamento = findViewById(R.id.nombreMedicamento);
        nombreMedicamento.setText(getIntent().getStringExtra("nombreMedicamento"));
        inicializarVariables();

        botonVolver.setOnClickListener(view ->{onBackPressed();});

        siguiente.setOnClickListener(view ->{
            int year = datePicker.getYear();
            int month = datePicker.getMonth()+1;
            int dayOfMonth = datePicker.getDayOfMonth();
            Intent intent = new Intent(AgregarFechaInicioRecordatorioActivity.this, AgregarDuracionRecordatorioActivity.class);
            intent.putExtra("year", year);
            intent.putExtra("month", month);
            intent.putExtra("dayOfMonth", dayOfMonth);
            intent.putExtra("codRecordatorio", getIntent().getIntExtra("codRecordatorio", 0));
            intent.putExtra("presentacionMedId", getIntent().getIntExtra("presentacionMedId", 0));
            intent.putExtra("nombreMedicamento", getIntent().getStringExtra("nombreMedicamento"));
            startActivity(intent);

        });
    }

    private void inicializarVariables() {
        botonVolver = findViewById(R.id.boton_volver);
        datePicker = findViewById(R.id.datePicker);
        siguiente = findViewById(R.id.button_siguiente);

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Establece la fecha mínima en el DatePicker (hoy)
        datePicker.setMinDate(calendar.getTimeInMillis());

        // Configura el DatePicker con la fecha actual
        datePicker.init(year, month, day, null);
    }


}
