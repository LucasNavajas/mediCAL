package com.example.medical;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook; // Usa XSSFWorkbook para formatos de archivo .xlsx

import com.bumptech.glide.load.DataSource;
import com.example.medical.model.Calendario;
import com.example.medical.model.Recordatorio;
import com.example.medical.model.RegistroRecordatorio;
import com.example.medical.model.Reporte;
import com.example.medical.model.Usuario;
import com.example.medical.retrofit.CalendarioApi;
import com.example.medical.retrofit.RecordatorioApi;
import com.example.medical.retrofit.RegistroRecordatorioApi;
import com.example.medical.retrofit.ReporteApi;
import com.example.medical.retrofit.RetrofitService;
import com.example.medical.retrofit.UsuarioApi;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
/*
import org.apache.poi.xddf.usermodel.chart.AxisCrosses;
import org.apache.poi.xddf.usermodel.chart.AxisPosition;
import org.apache.poi.xddf.usermodel.chart.ChartTypes;
import org.apache.poi.xddf.usermodel.chart.LegendPosition;
import org.apache.poi.xddf.usermodel.chart.XDDFCategoryAxis;
import org.apache.poi.xddf.usermodel.chart.XDDFChartData;
import org.apache.poi.xddf.usermodel.chart.XDDFChartLegend;
import org.apache.poi.xddf.usermodel.chart.XDDFDataSource;
import org.apache.poi.xddf.usermodel.chart.XDDFDataSourcesFactory;
import org.apache.poi.xddf.usermodel.chart.XDDFNumericalDataSource;
import org.apache.poi.xddf.usermodel.chart.XDDFValueAxis;
import org.apache.poi.xssf.usermodel.XSSFChart;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTBarChart;
*/

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompartirReporteActivity extends AppCompatActivity {

    private Object context;
    private RetrofitService retrofitService;
    private int nroReporteSeleccionado;
    private String fechaReporteDesde = "Seleccione 'fecha desde'";
    private String fechaReporteHasta = "Seleccione 'fecha hasta'";

    private TextView fechaDesde;
    private TextView fechaHasta;
    private List<RegistroRecordatorio> listaTotalRegistroRecordatorios = new ArrayList<>();
    private String destinatario;
    private int codUsuarioLogeado;
    private int codCalendarioSeleccionado;
    private File file;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.n83_compartir_informe);
        this.context = context;

        Intent intent1 = getIntent();
        nroReporteSeleccionado = intent1.getIntExtra("nroReporte", 0);
        codCalendarioSeleccionado = intent1.getIntExtra("calendarioSeleccionadoid", 0);
        codUsuarioLogeado = intent1.getIntExtra("codUsuario", 0);

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
            destinatario = emailDestinatario.getText().toString();
            if (fechaReporteDesde.equals("Seleccione 'fecha desde'")){
                Toast.makeText(CompartirReporteActivity.this, "Debe Seleccionar una 'fecha desde'", Toast.LENGTH_SHORT).show();
            } else if (fechaReporteHasta.equals("Seleccione 'fecha hasta'")) {
                Toast.makeText(CompartirReporteActivity.this, "Debe Seleccionar una 'fecha hasta'", Toast.LENGTH_SHORT).show();
            } else if (!esCorreoValido(destinatario)) {
                Toast.makeText(this, "La dirección de correo no es válida.", Toast.LENGTH_SHORT).show();
            } else {
                Log.d("MiApp", "Se obtuvo un mail correcto de destinatario: " + destinatario);
                obtenerReporte(nroReporteSeleccionado, destinatario);
            }
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
                    Usuario usuarioAsociado = reporteAsociado.getUsuario();
                    obtenerCalendariosUsuario(reporteAsociado, usuarioAsociado, destinatario);
                } else {
                    Log.d("MiApp", "Error en la solicitud de obtener 'Reporte': " + response.message());
                }
            }
            @Override
            public void onFailure(Call<Reporte> call, Throwable t) {
                Log.e("MiApp", "Error en la solicitud de obtener 'Reporte': " + t.getMessage());
            }
        });
    }

    private void obtenerCalendariosUsuario(Reporte reporteAsociado, Usuario usuarioAsociado, String destinatario){
        RetrofitService retrofitService = new RetrofitService();
        CalendarioApi calendarioApi = retrofitService.getRetrofit().create(CalendarioApi.class);

        // Hacer la llamada a la API para obtener la clase Reporte asociada al nroReporte
        Call<List<Calendario>> calendariosCall = calendarioApi.getByCodUsuario(usuarioAsociado.getCodUsuario());

        calendariosCall.enqueue(new Callback<List<Calendario>>() {
            @Override
            public void onResponse(Call<List<Calendario>> call, Response<List<Calendario>> response) {
                if (response.isSuccessful()) {
                    List<Calendario> calendariosAsociados = response.body();
                    if (calendariosAsociados != null) {
                        Log.d("MiApp", "Se obtuvieron los calendarios relacionados: " + calendariosAsociados);
                        for (Calendario calendario : calendariosAsociados) {
                            // Para cada calendario, obtener las clases "Recordatorio"
                            Log.d("MiApp", "codCalendario encontrado: " + calendario.getCodCalendario());
                            obtenerRecordatoriosPorCalendario(reporteAsociado, calendario, destinatario);
                        }

                        obtenerTipoDeReporte(reporteAsociado, destinatario);

                    } else {
                        Log.d("MiApp", "No se encontraron calendarios asociado al usuario: " + usuarioAsociado);
                    }
                } else {
                    Log.d("MiApp", "Error en la solicitud de obtener calendarios asociados al usuario" + response.message());
                }
            }
            @Override
            public void onFailure(Call<List<Calendario>> call, Throwable t) {
                Log.e("MiApp", "Error en la solicitud de obtener calendarios asociados al usuario"  + t.getMessage());
            }
        });
    }

    private void obtenerRecordatoriosPorCalendario(Reporte reporteAsociado, Calendario calendariosAsociado, String destinatario){
        RetrofitService retrofitService = new RetrofitService();
        RecordatorioApi recordatorioApi = retrofitService.getRetrofit().create(RecordatorioApi.class);

        // Hacer la llamada a la API para obtener las clases "Recordatorio" asociadas a un calendario
        Call<List<Recordatorio>> recordatoriosCall = recordatorioApi.getByCodCalendario(calendariosAsociado.getCodCalendario());

        recordatoriosCall.enqueue(new Callback<List<Recordatorio>>() {
            @Override
            public void onResponse(Call<List<Recordatorio>> call, Response<List<Recordatorio>> response) {
                if (response.isSuccessful()) {
                    List<Recordatorio> recordatoriosAsociados = response.body();
                    if (recordatoriosAsociados != null && !recordatoriosAsociados.isEmpty()) {
                        Log.d("MiApp", "Recordatorios Asociados Encontrados: ");
                        for (Recordatorio recordatorio : recordatoriosAsociados) {
                            // Para cada recordatorio, obtén las clases "RegistroRecordatorio"
                            Log.d("MiApp", "codRecordatorio encontrado: " + recordatorio.getCodRecordatorio());
                            obtenerRegistrosPorRecordatorio(reporteAsociado, recordatorio, destinatario);
                        }
                    } else {
                        Log.d("MiApp", "No se encontraron clases 'Recordatorio' asociadas al calendario " + calendariosAsociado.getNombreCalendario());
                    }
                } else {
                    Log.d("MiApp", "Error en la solicitud de clases 'Recordatorio': " + response.message());
                }
            }
            @Override
            public void onFailure(Call<List<Recordatorio>> call, Throwable t) {
                Log.e("MiApp", "Error en la solicitud de clases 'Recordatorio': " + t.getMessage());
            }
        });
    }

    private void obtenerRegistrosPorRecordatorio(Reporte reporteAsociado, Recordatorio recordatorioAsociado, String destinatario){
        RetrofitService retrofitService = new RetrofitService();
        RegistroRecordatorioApi registroRecordatorioApi = retrofitService.getRetrofit().create(RegistroRecordatorioApi.class);

        // Hacer la llamada a la API para obtener las clases "Recordatorio" asociadas a un calendario
        Call<List<RegistroRecordatorio>> registroRecordatoriosCall = registroRecordatorioApi.getByCodRecordatorio(recordatorioAsociado.getCodRecordatorio());

        registroRecordatoriosCall.enqueue(new Callback<List<RegistroRecordatorio>>() {
            @Override
            public void onResponse(Call<List<RegistroRecordatorio>> call, Response<List<RegistroRecordatorio>> response) {
                if (response.isSuccessful()) {
                    List<RegistroRecordatorio> registroRecordatoriosAsociados = response.body();
                    if (registroRecordatoriosAsociados != null && !registroRecordatoriosAsociados.isEmpty()) {
                        Log.d("MiApp", "RegistrosRecordatorios Asociados Encontrados: ");
                        for (RegistroRecordatorio registroRecordatorio : registroRecordatoriosAsociados) {
                            Log.d("MiApp", "codRegistroRecordatorio encontrado: " + registroRecordatorio.getCodRegistroRecordatorio());
                            listaTotalRegistroRecordatorios.add(registroRecordatorio);
                        }
                    } else {
                        Log.d("MiApp", "No se encontraron clases 'RegistroRecordatorio' asociadas al recordatorio " + recordatorioAsociado.getCodRecordatorio());
                    }
                } else {
                    Log.d("MiApp", "Error en la solicitud de clases 'RegistroRecordatorio': " + response.message());
                }
            }
            @Override
            public void onFailure(Call<List<RegistroRecordatorio>> call, Throwable t) {
                Log.e("MiApp", "Error en la solicitud de clases 'RegistroRecordatorio': " + t.getMessage());
            }
        });
    }



    // Según el tipo de reporte que se haya seleccionado, se hace el llamado a generar ese tipo de Excel
    private void obtenerTipoDeReporte (Reporte reporteAsociado, String destinatario){
        try {
            if (reporteAsociado.getTipoReporte().getNombreTipoReporte().equals("Reporte Medicamentos (Todos)")){
                generarArchivoExcelMedicamentosTodos(reporteAsociado, destinatario);
            } else if (reporteAsociado.getTipoReporte().getNombreTipoReporte().equals("Reporte Medicamento (Uno)")){
                generarArchivoExcelMedicamentoUno(reporteAsociado, destinatario);
            } else if (reporteAsociado.getTipoReporte().getNombreTipoReporte().equals("Reporte Síntomas")){
                generarArchivoExcelSíntomas(reporteAsociado, destinatario);
            } else if (reporteAsociado.getTipoReporte().getNombreTipoReporte().equals("Reporte Mediciones")){
                generarArchivoExcelMediciones(reporteAsociado, destinatario);
            }
        } catch (FileNotFoundException e) {
            // Manejo de la excepción FileNotFoundException
            e.printStackTrace(); // Imprime la traza de la excepción para depuración
            Toast.makeText(this, "No se encontró el archivo.", Toast.LENGTH_SHORT).show();
        }
    }


    private void generarArchivoExcelMedicamentosTodos(Reporte reporteAsociado, String destinatario) throws FileNotFoundException {

        // Crear un nuevo libro de trabajo (workbook) de Excel
        Workbook workbook = new XSSFWorkbook();
        //XSSFWorkbook workbook = new XSSFWorkbook();

        // --- PRIMER HOJA ---
        // Crear una hoja de trabajo 1 (worksheet)
        Sheet sheet = workbook.createSheet((reporteAsociado.getTipoReporte().getNombreTipoReporte()));

        // --- TÍTULO / PRIMER HOJA ---
        // Crear una fila para el título con celdas unificadas
        Row headerRow0 = sheet.createRow(0);
        // Crear una celda que abarque las 6 primeras columnas (índices de 0 a 5)
        Cell Titulocell = headerRow0.createCell(0);
        Titulocell.setCellValue((reporteAsociado.getTipoReporte()).toString()); // Título que se verá en las celdas unificadas
        // Establecer el estilo para el texto del título
        CellStyle style0 = workbook.createCellStyle();
        Font font0 = workbook.createFont();
        font0.setBold(true);
        font0.setFontHeightInPoints((short) 20);
        font0.setFontName("Roboto"); // Fuente Roboto
        // Establecer el color de fuente en azul (en formato RGB)
        font0.setColor((short) -16776961);
        //font0.setColor(IndexedColors.DARK_BLUE.getIndex()); // Azul oscuro; da error
        style0.setFont(font0);
        // Aplicar el estilo a la celda
        Titulocell.setCellStyle(style0);
        // Fusionar las celdas de la fila 0, columnas 0 a 5
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));

        // --- DATOS DEL REPORTE / PRIMER HOJA ---
        // Crear una fila para los datos del reporte
        Row headerRow1 = sheet.createRow(2);
        // Crear celdas datos del reporte
        Cell fechaDesde = headerRow1.createCell(1);
        fechaDesde.setCellValue("Fecha Desde: " + reporteAsociado.getFechaDesde().toString());
        Cell fechaHasta = headerRow1.createCell(3);
        fechaHasta.setCellValue("Fecha Hasta: " + reporteAsociado.getFechaHasta().toString());
        // Establecer el estilo para el texto de los datos del reporte
        CellStyle style1 = workbook.createCellStyle();
        Font font1 = workbook.createFont();
        font1.setBold(true);
        font1.setFontHeightInPoints((short) 10);
        font1.setColor(IndexedColors.GREY_80_PERCENT.getIndex()); // Gris oscuro 3
        font1.setFontName("Roboto"); // Fuente Roboto
        style1.setFont(font1);
        // Aplicar el estilo a la celda
        fechaDesde.setCellStyle(style1);
        fechaHasta.setCellStyle(style1);


        // --- FILA ENCABEZADOS DE COLUMNAS / PRIMER HOJA ---
        // Crear una fila para los encabezados de las columnas
        Row headerRow = sheet.createRow(2);
        // Crear celdas de encabezados de columnas
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
        headerCell6.setCellValue("Notas");  // En caso de omisión su motivo; en caso de tomado su instruccion
        // Establecer el estilo para el texto de los encabezados
        CellStyle style2 = workbook.createCellStyle();
        style2.setFillForegroundColor(IndexedColors.AQUA.getIndex()); // Color de fondo #00afb9
        style2.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        Font font2 = workbook.createFont();
        font2.setBold(true);
        font2.setFontHeightInPoints((short) 11);
        font2.setColor(IndexedColors.WHITE.getIndex()); // Color de letra blanco
        font2.setFontName("Arial"); // Fuente Arial
        style2.setFont(font2);
        // Aplicar el estilo a las celdas de encabezados
        headerCell1.setCellStyle(style2);
        headerCell2.setCellStyle(style2);
        headerCell3.setCellStyle(style2);
        headerCell4.setCellStyle(style2);
        headerCell5.setCellStyle(style2);
        headerCell6.setCellStyle(style2);


        // --- DATOS / PRIMER HOJA ---
        int fila = 3;   // Comienza desde la fila 2; fila 0 es el título, fila 1 los datos del reporte, fila 2 los encabezados
        for (RegistroRecordatorio registroRecordatorio : listaTotalRegistroRecordatorios) {
            Row dataRow = sheet.createRow(fila);    // Se crea CADA FILA de datos, va incrementando
            // Cada celda se crea dentro de la fila dataRow
            Cell dataCell1 = dataRow.createCell(0);
            dataCell1.setCellValue(registroRecordatorio.getRecordatorio().getPresentacionMed().getNombrePresentacionMed()); // Columna Tipo: nombre de la presentaciónMed
            Cell dataCell2 = dataRow.createCell(1);
            dataCell2.setCellValue(registroRecordatorio.getRecordatorio().getMedicamento().getNombreMedicamento()); // Columna Nombre Medicamento
            Cell dataCell3 = dataRow.createCell(2);
            if (registroRecordatorio.getFechaTomaReal() == null) {
                dataCell3.setCellValue(registroRecordatorio.getFechaTomaEsperada().toString()); // Columna Fecha Registrado: fecha toma real (tomados), sino fecha esperada ???
            } else {
                dataCell3.setCellValue(registroRecordatorio.getFechaTomaReal().toString()); // Columna Fecha Registrado: fecha toma real (tomados)
            }
            Cell dataCell4 = dataRow.createCell(3);
            dataCell4.setCellValue(registroRecordatorio.getFechaTomaEsperada().toString()); // Columna Programado Para: fecha esperada
            Cell dataCell5 = dataRow.createCell(4);
            if (registroRecordatorio.isTomaRegistroRecordatorio()) {
                dataCell5.setCellValue("Tomado");      // Columna Valor: Tomado si isTomaRegistroRecordatorio = true
            } else {
                dataCell5.setCellValue("Omitido");      // Columna Valor: Omitido si isTomaRegistroRecordatorio = false
            }
            Cell dataCell6 = dataRow.createCell(5);
            if (registroRecordatorio.getOmision() != null) {
                dataCell6.setCellValue("Instrucción: " + registroRecordatorio.getRecordatorio().getInstruccion().toString());    // Columna Notas: Si es un registro tomado, la Instrucción
            } else {
                dataCell6.setCellValue("Motivo Omisión: " + registroRecordatorio.getOmision().getNombreOmision());  // Columna Notas: Si es un registro omitido, el Motivo Omisión
            }

            // Establecer el estilo para el texto de los datos
            CellStyle style3 = workbook.createCellStyle();
            Font font3 = workbook.createFont();
            font3.setBold(false);
            font3.setFontHeightInPoints((short) 11);
            font3.setColor(IndexedColors.BLACK.getIndex()); // Color de letra negro
            font3.setFontName("Arial"); // Fuente Arial
            style3.setFont(font3);
            // Aplicar el estilo a las celdas de datos
            dataCell1.setCellStyle(style3);
            dataCell2.setCellStyle(style3);
            dataCell3.setCellStyle(style3);
            dataCell4.setCellStyle(style3);
            dataCell5.setCellStyle(style3);
            dataCell6.setCellStyle(style3);

            fila++; // Incrementa el número de fila en cada iteración
        }


        // --- SEGUNDA HOJA ---
        // Crear una hoja de trabajo 2 (worksheet)
        Sheet sheet2 = workbook.createSheet("Gráfico");
        //XSSFSheet sheet2 = workbook.createSheet("Gráfico");

        // --- TÍTULO / SEGUNDA HOJA ---
        // Crear una fila para el título con celdas unificadas
        Row headerRow2_0 = sheet2.createRow(0);
        // Crear una celda que abarque las 6 primeras columnas (índices de 0 a 5)
        Cell Titulocell2 = headerRow2_0.createCell(0);
        Titulocell2.setCellValue(("Gráfico del " + reporteAsociado.getTipoReporte().getNombreTipoReporte())); // Título que se verá en las celdas unificadas
        // Establecer el estilo para el texto del título
        CellStyle style2_0 = workbook.createCellStyle();
        Font font2_0 = workbook.createFont();
        font2_0.setBold(true);
        font2_0.setFontHeightInPoints((short) 20);
        font2_0.setColor(IndexedColors.DARK_BLUE.getIndex()); // Color de letra azul
        font2_0.setFontName("Roboto"); // Fuente Roboto
        style2_0.setFont(font2_0);
        // Aplicar el estilo a la celda
        Titulocell2.setCellStyle(style2_0);
        // Fusionar las celdas de la fila 0, columnas 0 a 5
        sheet2.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));

        // --- DATOS DEL REPORTE / SEGUNDA HOJA ---
        // Crear una fila para los datos del reporte
        Row headerRow2_1 = sheet2.createRow(2);
        // Crear celdas datos del reporte
        Cell fechaDesde2 = headerRow2_1.createCell(1);
        fechaDesde2.setCellValue("Fecha Desde: " + reporteAsociado.getFechaDesde().toString());
        Cell fechaHasta2 = headerRow2_1.createCell(3);
        fechaHasta2.setCellValue("Fecha Hasta: " + reporteAsociado.getFechaHasta().toString());
        // Establecer el estilo para el texto de los datos del reporte
        CellStyle style2_1 = workbook.createCellStyle();
        Font font2_1 = workbook.createFont();
        font2_1.setBold(true);
        font2_1.setFontHeightInPoints((short) 10);
        font2_1.setColor(IndexedColors.GREY_80_PERCENT.getIndex()); // Gris oscuro 3
        font2_1.setFontName("Roboto"); // Fuente Roboto
        style2_1.setFont(font2_1);
        // Aplicar el estilo a la celda
        fechaDesde2.setCellStyle(style2_1);
        fechaHasta2.setCellStyle(style2_1);

        // --- FILA ENCABEZADOS DE COLUMNAS / SEGUNDA HOJA ---
        // Crear una fila para los encabezados de las columnas
        Row headerRow2_2 = sheet2.createRow(2);
        // Crear celdas de encabezados de columnas
        Cell headerCell2_1 = headerRow2_2.createCell(0);
        headerCell2_1.setCellValue("Nombre del Medicamento");
        Cell headerCell2_2 = headerRow2_2.createCell(1);
        headerCell2_2.setCellValue("Procentaje de Cumplimiento");
        Cell headerCell2_3 = headerRow2_2.createCell(2);
        headerCell2_3.setCellValue("Gráfico de Cumplimiento");
        // Establecer el estilo para el texto de los encabezados
        CellStyle style2_2 = workbook.createCellStyle();
        style2_2.setFillForegroundColor(IndexedColors.AQUA.getIndex()); // Color de fondo #00afb9
        style2_2.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        Font font2_2 = workbook.createFont();
        font2_2.setBold(true);
        font2_2.setFontHeightInPoints((short) 11);
        font2_2.setColor(IndexedColors.WHITE.getIndex()); // Color de letra blanco
        font2_2.setFontName("Arial"); // Fuente Arial
        style2_2.setFont(font2_2);
        // Aplicar el estilo a las celdas de encabezados
        headerCell2_1.setCellStyle(style2_2);
        headerCell2_2.setCellStyle(style2_2);
        headerCell2_3.setCellStyle(style2_2);
        sheet2.addMergedRegion(new CellRangeAddress(2, 2, 2, 5));


        // --- DATOS / SEGUNDA HOJA ---
        // Crear un mapa para agrupar los registros por nombre de medicamento
        Map<String, List<RegistroRecordatorio>> medicamentosAgrupados = new HashMap<>();
        // Agrupar los registros por nombre de medicamento
        for (RegistroRecordatorio registroRecordatorio : listaTotalRegistroRecordatorios) {
            String nombreMedicamento = registroRecordatorio.getRecordatorio().getMedicamento().getNombreMedicamento();
            // Verificar si ya existe una lista para este medicamento en el mapa
            List<RegistroRecordatorio> registros = medicamentosAgrupados.get(nombreMedicamento);
            if (registros == null) {
                registros = new ArrayList<>();
                medicamentosAgrupados.put(nombreMedicamento, registros);
            }
            registros.add(registroRecordatorio);
        }
        // Calcular el porcentaje de cumplimiento y escribir en la hoja de resumen
        int filaActual = 3; // Comienza desde la fila 2; fila 0 es el título, fila 1 los datos del reporte, fila 2 los encabezados
        for (Map.Entry<String, List<RegistroRecordatorio>> entry : medicamentosAgrupados.entrySet()) {
            String nombreMedicamento = entry.getKey();
            List<RegistroRecordatorio> registros = entry.getValue();
            int totalRegistros = registros.size();
            int registrosTomados = 0;
            for (RegistroRecordatorio registro : registros) {
                if (registro.isTomaRegistroRecordatorio()) {
                    registrosTomados++;
                }
            }
            double porcentajeCumplimiento = (double) registrosTomados / totalRegistros * 100.0;

            Row filaResumen = sheet2.createRow(filaActual);
            Cell celdaNombreMedicamento = filaResumen.createCell(0);
            celdaNombreMedicamento.setCellValue(nombreMedicamento);
            Cell celdaPorcentajeCumplimiento = filaResumen.createCell(1);
            celdaPorcentajeCumplimiento.setCellValue(porcentajeCumplimiento);

            // Establecer el estilo para el texto de los datos
            CellStyle style2_3 = workbook.createCellStyle();
            Font font2_3 = workbook.createFont();
            font2_3.setBold(false);
            font2_3.setFontHeightInPoints((short) 11);
            font2_3.setColor(IndexedColors.BLACK.getIndex()); // Color de letra negro
            font2_3.setFontName("Arial"); // Fuente Arial
            style2_3.setFont(font2_3);
            // Aplicar el estilo a las celdas de datos
            celdaNombreMedicamento.setCellStyle(style2_3);
            celdaPorcentajeCumplimiento.setCellStyle(style2_3);

            filaActual++;
        }
        // Ajustar el ancho de las columnas para que los datos se ajusten
        sheet2.autoSizeColumn(0);
        sheet2.autoSizeColumn(1);


        // --- GRÁFICO / SEGUNDA HOJA ---
        // Crear Gráfico de barras en la hoja de trabajo
        /*
        XSSFDrawing drawing = sheet2.createDrawingPatriarch();
        XSSFClientAnchor anchor = drawing.createAnchor(0, 0, 0, 0, 2, 2, 10, 25); // Define las coordenadas del gráfico
        XSSFChart chart = drawing.createChart(anchor);
        XDDFChartLegend legend = chart.getOrAddLegend(); // Agregar leyenda al gráfico
        legend.setPosition(LegendPosition.TOP_RIGHT); // Posición de la leyenda
        // Crear categorías (nombres de medicamentos) y valores (porcentajes) para el gráfico
        XDDFDataSource<String> nombresCategoria = XDDFDataSourcesFactory.fromStringCellRange(sheet2, new CellRangeAddress(3, filaActual - 1, 0, 0));
        XDDFNumericalDataSource<Double> valores = XDDFDataSourcesFactory.fromNumericCellRange(sheet2, new CellRangeAddress(3, filaActual - 1, 1, 1));
        // Crear un eje de categoría (eje Y) y un eje de valores (eje X)
        XDDFCategoryAxis categoriaAxis = chart.createCategoryAxis(AxisPosition.LEFT);
        XDDFValueAxis valorAxis = chart.createValueAxis(AxisPosition.BOTTOM);
        valorAxis.setCrosses(AxisCrosses.AUTO_ZERO);
        // Agregar datos al gráfico de barras
        XDDFChartData data = chart.createData(ChartTypes.BAR, categoriaAxis, valorAxis);
        data.setVaryColors(true); // Alternar colores de las barras
        XDDFChartData.Series series = data.addSeries(nombresCategoria, valores);
        series.setTitle("Porcentaje de Cumplimiento", null);
        // Dibujar el gráfico en la hoja de trabajo
        chart.plot(data);
        // Ajustar el tamaño del gráfico
        // CTBarChart barChart = chart.getCTChart().getPlotArea().getBarChartArray(0);
        */

        // --- GUARDAR EL ARCHIVO EXCEL GENERADO ---
        // Ruta del almacenamiento interno en Android para guardar el archivo
        File dir = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
        String filePath = dir.getAbsolutePath() + "/miarchivo.xlsx"; // Cambia la extensión si es necesario
        // Crear el archivo Excel
        File file = new File(filePath);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            workbook.write(fos);
            fos.close();
            enviarReporte(reporteAsociado, destinatario);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /*
    private void generarArchivoExcelMedicamentosTodos(Reporte reporteAsociado, String destinatario) throws IOException, WriteException {
        // Crear un nuevo libro de trabajo (workbook) de Excel
        WritableWorkbook workbook = Workbook.createWorkbook(new File("/storage/emulated/0/miarchivo.xls")); // Cambiar la ruta según tus necesidades

        // --- PRIMER HOJA ---
        // Crear una hoja de trabajo 1 (worksheet)
        WritableSheet sheet = workbook.createSheet(reporteAsociado.getTipoReporte().getNombreTipoReporte(), 0);

        // --- TÍTULO / PRIMER HOJA ---
        // Crear una fila para el título con celdas unificadas
        Label title = new Label(0, 0, reporteAsociado.getTipoReporte().toString());
        WritableCellFormat titleFormat = new WritableCellFormat(new WritableFont(WritableFont.createFont("Roboto"), 20, WritableFont.BOLD));
        titleFormat.setAlignment(Alignment.CENTRE);
        titleFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
        titleFormat.setBackground(Colour.DARK_BLUE);
        titleFormat.setWrap(true);
        title.setCellFormat(titleFormat);
        sheet.addCell(title);
        sheet.mergeCells(0, 0, 5, 0);

        // --- DATOS DEL REPORTE / PRIMER HOJA ---
        // Crear una fila para los datos del reporte
        Label fechaDesde = new Label(1, 2, "Fecha Desde: " + reporteAsociado.getFechaDesde().toString());
        Label fechaHasta = new Label(3, 2, "Fecha Hasta: " + reporteAsociado.getFechaHasta().toString());
        sheet.addCell(fechaDesde);
        sheet.addCell(fechaHasta);

        // Establecer el estilo para el texto de los datos del reporte
        WritableCellFormat dataFormat = new WritableCellFormat(new WritableFont(WritableFont.createFont("Roboto"), 10));
        dataFormat.setAlignment(Alignment.LEFT);
        dataFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
        dataFormat.setWrap(true);
        dataFormat.setBackground(Colour.GREY_80_PERCENT);

        fechaDesde.setCellFormat(dataFormat);
        fechaHasta.setCellFormat(dataFormat);

    }
    */

    private void generarArchivoExcelMedicamentoUno(Reporte reporteAsociado, String destinatario) {
        // FALTA
    }

    private void generarArchivoExcelSíntomas(Reporte reporteAsociado, String destinatario) {
        // FALTA
    }

    private void generarArchivoExcelMediciones(Reporte reporteAsociado, String destinatario) {
        // FALTA
    }


    private void enviarReporte(Reporte reporteAsociado, String destinatario) {

        // Configura las propiedades del servidor de correo de Gmail
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        // Configura la sesión de correo
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("medicalutnfrm@gmail.com", "MediCALproyectofinal"); // Tu dirección de correo y contraseña de Gmail
            }
        });

        try {
            // Crea un mensaje de correo electrónico
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("medicalutnfrm@gmail.com")); // Tu dirección de correo Gmail
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            message.setSubject("MediCAL - " + reporteAsociado.getTipoReporte().getNombreTipoReporte());

            // Adjunta el archivo
            MimeBodyPart attachmentPart = new MimeBodyPart();
            attachmentPart.attachFile(file);
            //MimeBodyPart attachmentPart = new MimeBodyPart();
            //attachmentPart.attachFile(new File("/storage/emulated/0/miarchivo.xlsx")); // Ruta al archivo Excel

            // Crea el cuerpo del mensaje
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(attachmentPart);

            // Configura el contenido del mensaje
            message.setContent(multipart);

            // Envía el mensaje
            Transport.send(message);

            // Notifica que el correo se envió correctamente
            Toast.makeText(this, "Correo enviado con el archivo adjunto.", Toast.LENGTH_SHORT).show();
            popupReporteCompartido();
        } catch (MessagingException | IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error al enviar el correo.", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean esCorreoValido(String correoDestinatario) {
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";   // Expresión regular para validar un correo electrónico

        Pattern pattern = Pattern.compile(regex);   // Compila la expresión regular en un patrón

        Matcher matcher = pattern.matcher(correoDestinatario);  // Crea un objeto Matcher

        return matcher.matches();    // Comprueba si la cadena cumple con el patrón
    }

    private void popupReporteCompartido() {
        Log.d("MiApp","Se llamó a popupReporteCompartido");
        View popupView = getLayoutInflater().inflate(R.layout.n86_3_popup_reporte_eliminado, null);

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

        TextView texto = popupView.findViewById(R.id.text_texto);
        ImageView cerrar = popupView.findViewById(R.id.boton_cerrar);
        texto.setText("Reporte Compartido exitosamente con " + destinatario);

        cerrar.setOnClickListener(view ->{
            popupWindow.dismiss();
            dimView.setVisibility(View.GONE);
            Intent intent = new Intent(CompartirReporteActivity.this, ReportesActivity.class);
            intent.putExtra("codUsuario", codUsuarioLogeado);
            intent.putExtra("calendarioSeleccionadoid", codCalendarioSeleccionado);
            startActivity(intent);
        });

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                dimView.setVisibility(View.GONE);
            }
        });
    }


}
