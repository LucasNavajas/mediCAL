package com.example.medical;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.example.medical.model.Recordatorio;
import com.example.medical.model.Reporte;
import com.example.medical.retrofit.ReporteApi;
import com.example.medical.retrofit.RetrofitService;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.util.List;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompartirReporteActivity extends AppCompatActivity {

    private Object context;
    private RetrofitService retrofitService;
    private int nroReporteSeleccionado;
    private String fechaReporteDesde = "seleccione fecha desde";
    private String fechaReporteHasta = "seleccione fecha hasta";

    private TextView fechaDesde;
    private TextView fechaHasta;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.n83_compartir_informe);
        this.context = context;

        Intent intent1 = getIntent();
        nroReporteSeleccionado = intent1.getIntExtra("nroReporte", 0);

        EditText emailDestinatario = findViewById(R.id.textEdit_email);
        fechaDesde = findViewById(R.id.textEdit_fecha_desde);
        fechaHasta = findViewById(R.id.textEdit_fecha_hasta);
        Button botonEnviar = findViewById(R.id.button_enviar);
        ImageView botonVolver = findViewById(R.id.boton_volver);

        fechaDesde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupFechaDesde();
            }
        });

        fechaHasta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupFechaHasta();
            }
        });

        botonEnviar.setOnClickListener(view -> {
            String destinatario = emailDestinatario.getText().toString();
            obtenerReporte(nroReporteSeleccionado, destinatario);
        });

        botonVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void popupFechaDesde() {
        View popupView = getLayoutInflater().inflate(R.layout.n84_cambio_fecha, null);

        // Crear la instancia de PopupWindow
        PopupWindow popupWindow = new PopupWindow(popupView, 1000, ViewGroup.LayoutParams.WRAP_CONTENT );

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

        DatePicker datePicker = popupView.findViewById(R.id.datePicker);
        TextView cancelar = popupView.findViewById(R.id.cancelar);
        TextView aceptar = popupView.findViewById(R.id.aceptar);

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                dimView.setVisibility(View.GONE);
            }
        });

        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth() + 1;
                int year = datePicker.getYear();

                fechaReporteDesde = (LocalDate.of(year, month, day).toString());
                fechaDesde.setText(fechaReporteDesde);

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

    private void popupFechaHasta() {
        View popupView = getLayoutInflater().inflate(R.layout.n84_cambio_fecha, null);

        // Crear la instancia de PopupWindow
        PopupWindow popupWindow = new PopupWindow(popupView, 1000, ViewGroup.LayoutParams.WRAP_CONTENT );

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

        DatePicker datePicker = popupView.findViewById(R.id.datePicker);
        TextView cancelar = popupView.findViewById(R.id.cancelar);
        TextView aceptar = popupView.findViewById(R.id.aceptar);

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                dimView.setVisibility(View.GONE);
            }
        });

        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth() + 1;
                int year = datePicker.getYear();

                fechaReporteHasta = (LocalDate.of(year, month, day).toString());
                fechaHasta.setText(fechaReporteHasta);

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

    private void obtenerReporte(int nroReporteSeleccionado, String destinatario) {

        RetrofitService retrofitService = new RetrofitService();
        ReporteApi reporteApi = retrofitService.getRetrofit().create(ReporteApi.class);

        // Hacer la llamada a la API para obtener la clase Reporte asociada al nroReporte
        Call<Reporte> reporteCall = reporteApi.getByNroReporte(nroReporteSeleccionado);

        reporteCall.enqueue(new Callback<Reporte>() {
            @Override
            public void onResponse(Call<Reporte> call, Response<Reporte> response) {
                if (response.isSuccessful()) {
                    Reporte reporteAsociado = response.body();
                    Log.d("MiApp", "Se obtuvo el reporte relacionado: " + reporteAsociado);

                    if (reporteAsociado.getTipoReporte().getNombreTipoReporte().equals("Reporte Medicamentos (Todos)")){
                        generarArchivoExcelMedicamentosTodos(reporteAsociado, destinatario);
                    } else if (reporteAsociado.getTipoReporte().getNombreTipoReporte().equals("Reporte Medicamento (Uno)")){
                        generarArchivoExcelMedicamentoUno(reporteAsociado, destinatario);
                    } else if (reporteAsociado.getTipoReporte().getNombreTipoReporte().equals("Reporte Síntomas")){
                        generarArchivoExcelSíntomas(reporteAsociado, destinatario);
                    } else if (reporteAsociado.getTipoReporte().getNombreTipoReporte().equals("Reporte Mediciones")){
                        generarArchivoExcelMediciones(reporteAsociado, destinatario);
                    }

                } else {
                    Log.d("MiApp", "Error en la solicitud de eliminar 'Reporte': " + response.message());
                }
            }
            @Override
            public void onFailure(Call<Reporte> call, Throwable t) {
                Log.e("MiApp", "Error en la solicitud de eliminar 'Reporte': " + t.getMessage());
            }
        });

    }

    private void generarArchivoExcelMedicamentosTodos(Reporte reporteAsociado, String destinatario) {
        // FALTA

        // Crear un nuevo libro de trabajo (workbook) de Excel
        Workbook workbook = new XSSFWorkbook();

        // PRIMER HOJA
        // Crear una hoja de trabajo 1 (worksheet)
        Sheet sheet = workbook.createSheet((reporteAsociado.getTipoReporte()).toString());

        // Crear una fila para encabezados
        Row headerRow = sheet.createRow(0);

        // Crear celdas de encabezado
        Cell headerCell1 = headerRow.createCell(0);
        headerCell1.setCellValue("Tipo");

        Cell headerCell2 = headerRow.createCell(1);
        headerCell2.setCellValue("Nombre Medicamento");

        Cell headerCell3 = headerRow.createCell(2);
        headerCell3.setCellValue("Fecha Registrado");

        Cell headerCell4 = headerRow.createCell(3);
        headerCell4.setCellValue("Programado Para");

        Cell headerCell5 = headerRow.createCell(4);
        headerCell5.setCellValue("Valor");

        Cell headerCell6 = headerRow.createCell(5);
        headerCell6.setCellValue("Notas");  // En caso de omisión su motivo; en caso de tomado su instruccion (si tiene)

        // Crear filas de datos
        Row dataRow1 = sheet.createRow(1);
        Cell dataCell1 = dataRow1.createCell(0);
        dataCell1.setCellValue("Dato 1");

        Cell dataCell2 = dataRow1.createCell(1);
        dataCell2.setCellValue("Dato 2");


        // SEGUNDA HOJA
        // Crear una hoja de trabajo 2 (worksheet)
        Sheet sheet2 = workbook.createSheet("Gráfico");

        // Crear una fila para encabezados 2
        Row headerRow2 = sheet2.createRow(0);

        // Crear celdas de encabezado 2
        Cell headerCell2_1 = headerRow2.createCell(0);
        headerCell2_1.setCellValue("Nombre Medicamento");

        Cell headerCell2_2 = headerRow2.createCell(1);
        headerCell2_2.setCellValue("Porcentaje Cumplimiento");

        Cell headerCell2_3 = headerRow2.createCell(2);
        headerCell2_3.setCellValue("Gráfico de Porcentaje Cumplimiento");


        /*
        try {
            // Guardar el libro de trabajo en un archivo
            FileOutputStream fileOut = new FileOutputStream(getExternalCacheDir() + "/reporte.xlsx");
            workbook.write(fileOut);
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        */
    }

    private void generarArchivoExcelMedicamentoUno(Reporte reporteAsociado, String destinatario) {
        // FALTA
    }

    private void generarArchivoExcelSíntomas(Reporte reporteAsociado, String destinatario) {
        // FALTA
    }

    private void generarArchivoExcelMediciones(Reporte reporteAsociado, String destinatario) {
        // FALTA
    }


    private void enviarReporte(String destinatario) {

        // FALTA

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{destinatario});
        intent.putExtra(Intent.EXTRA_SUBJECT, "Asunto del correo");
        intent.putExtra(Intent.EXTRA_TEXT, "Cuerpo del correo");

        File file = new File(getExternalCacheDir(), "reporte.xlsx");
        Uri uri = FileProvider.getUriForFile(this, getPackageName() + ".provider", file);
        intent.putExtra(Intent.EXTRA_STREAM, uri);

        startActivity(Intent.createChooser(intent, "Enviar correo electrónico"));
    }


}
