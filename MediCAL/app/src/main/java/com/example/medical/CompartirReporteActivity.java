package com.example.medical;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.AsyncTask;
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
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medical.adapter.ReporteAdapter;
import com.example.medical.model.Calendario;
import com.example.medical.model.CalendarioMedicion;
import com.example.medical.model.CalendarioSintoma;
import com.example.medical.model.Recordatorio;
import com.example.medical.model.RegistroRecordatorio;
import com.example.medical.model.Reporte;
import com.example.medical.model.Usuario;
import com.example.medical.retrofit.CalendarioApi;
import com.example.medical.retrofit.CalendarioMedicionApi;
import com.example.medical.retrofit.CalendarioSintomaApi;
import com.example.medical.retrofit.RecordatorioApi;
import com.example.medical.retrofit.RegistroRecordatorioApi;
import com.example.medical.retrofit.ReporteApi;
import com.example.medical.retrofit.RetrofitService;
import com.example.medical.retrofit.UsuarioApi;
import com.github.mikephil.charting.data.ChartData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;

import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Chart;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.charts.ChartDataSource;
import org.apache.poi.ss.usermodel.charts.ChartLegend;
import org.apache.poi.ss.usermodel.charts.DataSources;
import org.apache.poi.ss.usermodel.charts.LegendPosition;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xddf.usermodel.chart.ChartTypes;
import org.apache.poi.xddf.usermodel.chart.XDDFChartData;
import org.apache.poi.xddf.usermodel.chart.XDDFDataSource;
import org.apache.poi.xddf.usermodel.chart.XDDFDataSourcesFactory;
import org.apache.poi.xddf.usermodel.chart.XDDFNumericalDataSource;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFChart;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.charts.XSSFChartLegend;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    private View mainView; // Referencia a la vista de la actividad principal
    private Object context;
    private RetrofitService retrofitService;
    private int nroReporteSeleccionado;
    private List<RegistroRecordatorio> listaTotalRegistroRecordatorios = new ArrayList<>();
    private List<RegistroRecordatorio> listaTotalRegistroRecordatoriosFiltroMed = new ArrayList<>();
    private List<Reporte> listaTotalReportes = new ArrayList<>(); // Lista global para almacenar todos los informes de un usuario
    private List<Reporte> listaTotalReportesOriginal = new ArrayList<>();
    private List<Calendario> listaTotalDeCalendarios = new ArrayList<>();
    private List<CalendarioSintoma> listaTotalCalendarioSintomas = new ArrayList<>();
    private List<CalendarioMedicion> listaTotalCalendarioMediciones = new ArrayList<>();
    private int nroCalendariosAsociados=0;
    private int nroRecordatoriosAsociados=0;
    private int operacionesPendientes = -1;
    private ReporteAdapter reporteAdapter;
    private RecyclerView recyclerView;
    private LocalDate fechaDesdeReporte;
    private LocalDate fechaHastaReporte;
    private String destinatario;
    private int codUsuarioLogeado;
    private Usuario usuarioLogeado;
    private int codCalendarioSeleccionado;
    private File file;
    private Message message;

    private Reporte reporteAsociado;
    private boolean existenRegistros = false; // Variable global para verificar existencia de registrosRecordatorios
    private OnDataLoadedListener onDataLoadedListener;
    private OnDataLoadedListener onDataLoadedListener2;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Obtiene la referencia a la vista de la actividad principal
        //mainView = getWindow().getDecorView().getRootView();
        setContentView(R.layout.n86_0_informes_cargados); // Muestra layout n86 si existen informes
        View dimView = findViewById(R.id.dim_view);
        dimView.setVisibility(View.VISIBLE);

        Intent intent1 = getIntent();
        nroReporteSeleccionado = intent1.getIntExtra("nroReporte", 0);
        codCalendarioSeleccionado = intent1.getIntExtra("calendarioSeleccionadoid", 0);
        codUsuarioLogeado = intent1.getIntExtra("codUsuario", 0);
        destinatario = intent1.getStringExtra("destinatario");

        onDataLoadedListener = new OnDataLoadedListener() {
            @Override
            public void onDataLoaded() {
                Log.d("MiApp", "Llamo al método obtenerTipoDeReporte si existen registrosRecordatorio: " + existenRegistros);
                if (existenRegistros && operacionesPendientes==0) {
                    obtenerTipoDeReporte(reporteAsociado, destinatario, onDataLoadedListener2);
                    Log.d("MiApp", "Entró en el if y la variable existenRegistros es: " + existenRegistros);
                } else {
                    Log.d("MiApp", "Entró en el if y la variable existenRegistros es: " + existenRegistros);
                    setResult(RESULT_CANCELED);
                    finish();
                    Log.d("MiApp", "Se cierra la actividad CompartirReporte");
                }
            }
        };

        onDataLoadedListener2 = new OnDataLoadedListener() {
            @Override
            public void onDataLoaded() throws IOException {
                Log.d("MiApp", "Llamo al método generarExcelSintomas si existen síntomas: " + listaTotalCalendarioSintomas + " ; o mediciones: " + listaTotalCalendarioMediciones);
                if (listaTotalCalendarioSintomas!=null && !listaTotalCalendarioSintomas.isEmpty()){
                    generarArchivoExcelSíntomas(reporteAsociado, destinatario);
                } else if (listaTotalCalendarioMediciones!=null && !listaTotalCalendarioMediciones.isEmpty()){
                    generarArchivoExcelMediciones(reporteAsociado, destinatario);
                } else {
                    Log.d("MiApp", "Entró en el if y la variable existenRegistros es: " + existenRegistros);
                    setResult(RESULT_CANCELED);
                    finish();
                    Log.d("MiApp", "Se cierra la actividad CompartirReporte");
                }
            }
        };

        if (nroReporteSeleccionado!=0) {
            if (destinatario!=null && esCorreoValido(destinatario)) {
                Log.d("MiApp", "Llamo a obtenerReporte de CompartirReporteActivity");
                obtenerReporte(nroReporteSeleccionado);
                obtenerUsuarioLogeado(codUsuarioLogeado, onDataLoadedListener);
                //loadInformes();
            } else {
                popupCorreoNoValido();
                Toast.makeText(this, "La dirección de correo no es válida.", Toast.LENGTH_SHORT).show();
            }
        }

    }

    // Método para obtener los reportes asociados al usuario
    private void obtenerReportesDelUsuario(Usuario usuario, OnDataLoadedListener listener) {
        // Crear una instancia de la interfaz de la API de ReporteApi utilizando Retrofit
        RetrofitService retrofitService = new RetrofitService();
        ReporteApi reporteApi = retrofitService.getRetrofit().create(ReporteApi.class);

        // Hacer la llamada a la API para obtener los calendarios asociados al usuario
        Call<List<Reporte>> reportesCall = reporteApi.getByCodUsuario(usuario.getCodUsuario());

        reportesCall.enqueue(new Callback<List<Reporte>>() {
            public void onResponse(Call<List<Reporte>> call, Response<List<Reporte>> response) {
                if (response.isSuccessful()) {
                    List<Reporte> reportesAsociados = response.body();
                    if (reportesAsociados != null && !reportesAsociados.isEmpty()) {
                        Log.d("MiApp", "Reportes Asociados Encontrados");
                        listaTotalReportes.clear();
                        listaTotalReportesOriginal.clear();

                        for (Reporte reporte : reportesAsociados) {
                            listaTotalReportes.add(reporte);
                            listaTotalReportesOriginal.add(reporte);
                            Log.d("MiApp", "Reporte encontrado con nroReporte: " + reporte.getNroReporte() + ", y tipoReporte: " + reporte.getTipoReporte().getNombreTipoReporte());
                        }

                        loadInformes();
                        //pantallaInformesCargados(listener);

                    } else {
                        Log.d("MiApp", "No se encontraron reporte asociados al usuario");
                    }
                } else {
                    Log.d("MiApp", "Error en la solicitud de reportes: " + response.message());
                }
            }
            @Override
            public void onFailure(Call<List<Reporte>> call, Throwable t) {
                Log.e("MiApp", "Error en la solicitud de reportes: " + t.getMessage());
            }
        });
    }

    private void loadInformes() {

        // Configurar el RecyclerView
        recyclerView = findViewById(R.id.listareportes_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        reporteAdapter = new ReporteAdapter(new ArrayList<>()); // Inicializar el adaptador con una lista vacía
        reporteAdapter.setCodCalendarioSeleccionado(codCalendarioSeleccionado);
        reporteAdapter.setCodUsuarioLogeado(codUsuarioLogeado);
        recyclerView.setAdapter(reporteAdapter);

        if (listaTotalReportes != null) {
            // Imprimir el contenido de listaTotalReportes
            for (Reporte reporte : listaTotalReportes) {
                Log.d("MiApp", "Reporte en ListaTotalReportes: " + reporte.toString());
            }
            reporteAdapter.setReporteList(listaTotalReportes);
        } else {
            Toast.makeText(CompartirReporteActivity.this, "La lista de Reportes está vacía o es nula", Toast.LENGTH_SHORT).show();
        }
    }

    private void obtenerReporte(int nroReporteSeleccionado) {
        RetrofitService retrofitService = new RetrofitService();
        ReporteApi reporteApi = retrofitService.getRetrofit().create(ReporteApi.class);

        // Hacer la llamada a la API para obtener la clase Reporte asociada al nroReporte
        Call<Reporte> reporteCall = reporteApi.getByNroReporte(nroReporteSeleccionado);
        reporteCall.enqueue(new Callback<Reporte>() {
            @Override
            public void onResponse(Call<Reporte> call, Response<Reporte> response) {
                if (response.isSuccessful()) {
                    Reporte reporte = response.body();
                    Log.d("MiApp", "Se obtuvo el reporte relacionado: " + reporte);
                    Log.d("MiApp", "Operaciones Pendientes: " + operacionesPendientes);
                    fechaDesdeReporte = reporte.getFechaDesde();
                    fechaHastaReporte = reporte.getFechaHasta();
                    reporteAsociado = reporte;
                    //obtenerTipoDeReporte(reporte, destinatario);
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

    private void obtenerUsuarioLogeado(int codUsuarioLogeado, OnDataLoadedListener listener) {
        RetrofitService retrofitService = new RetrofitService();
        UsuarioApi usuarioApi = retrofitService.getRetrofit().create(UsuarioApi.class);
        // Hacer la llamada a la API para obtener el usuario seleccionado
        Call<Usuario> usuarioCall = usuarioApi.getByCodUsuario(codUsuarioLogeado);

        usuarioCall.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful()) {
                    Usuario usuarioSeleccionado = response.body();
                    usuarioLogeado = usuarioSeleccionado;
                    if (usuarioSeleccionado != null) {
                        Log.d("MiApp", "Usuario seleccionado encontrado: " + usuarioSeleccionado.getCodUsuario());
                        // Realizar llamada para obtener Calendaios del usuario
                        obtenerCalendariosUsuario(usuarioSeleccionado, listener);
                        obtenerReportesDelUsuario(usuarioSeleccionado, listener);
                    } else {
                        Log.d("MiApp", "No se encontró el usuario con codUsuario: " + codUsuarioLogeado);
                    }
                } else {
                    Log.d("MiApp", "Error en la solicitud: " + response.message());
                }
            }
            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Log.e("MiApp", "Error en la solicitud: " + t.getMessage());
            }
        });
    }

    private void obtenerCalendariosUsuario(Usuario usuarioAsociado, OnDataLoadedListener listener){
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
                            listaTotalDeCalendarios.add(calendario);
                            obtenerRecordatoriosPorCalendario(calendario, listener);
                        }
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

    private void obtenerRecordatoriosPorCalendario(Calendario calendariosAsociado, OnDataLoadedListener listener){
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
                        nroCalendariosAsociados++;
                        Log.d("MiApp", "Recordatorios Asociados Encontrados: ");
                        for (Recordatorio recordatorio : recordatoriosAsociados) {
                            nroRecordatoriosAsociados++;
                            // Para cada recordatorio, obtén las clases "RegistroRecordatorio"
                            Log.d("MiApp", "codRecordatorio encontrado: " + recordatorio.getCodRecordatorio());
                            obtenerRegistrosPorRecordatorio(recordatorio, listener);
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

    private void obtenerRegistrosPorRecordatorio(Recordatorio recordatorioAsociado, OnDataLoadedListener listener){
        RetrofitService retrofitService = new RetrofitService();
        RegistroRecordatorioApi registroRecordatorioApi = retrofitService.getRetrofit().create(RegistroRecordatorioApi.class);

        // Obtiene la cantidad total de operaciones pendientes
        operacionesPendientes = nroCalendariosAsociados * nroRecordatoriosAsociados;
        Log.d("MiApp", "Operaciones pendientes empieza con: " + operacionesPendientes);
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
                            if (registroRecordatorio.getFechaTomaEsperada().toLocalDate().isAfter(fechaDesdeReporte.minusDays(1)) && registroRecordatorio.getFechaTomaEsperada().toLocalDate().isBefore(fechaHastaReporte.plusDays(1))) {
                                existenRegistros = true;
                                Log.d("MiApp", "codRegistroRecordatorio encontrado: " + registroRecordatorio.getCodRegistroRecordatorio());
                                listaTotalRegistroRecordatorios.add(registroRecordatorio);
                            }
                        }
                        // Reduce el contador de operaciones pendientes después de procesar cada registro
                        operacionesPendientes--;
                        Log.d("MiApp","OperacionesPendientes: " + operacionesPendientes);
                        // Verifica si se han completado todas las operaciones
                        if (operacionesPendientes == 0) {
                            // Todas las operaciones se han completado, ejecuta el listener
                            try {
                                listener.onDataLoaded();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            Log.d("MiApp", "Ya se ejecutó el listener de operacionesPendientes");
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
    private void obtenerTipoDeReporte (Reporte reporteAsociado, String destinatario, OnDataLoadedListener listener2){
        try {
            if (reporteAsociado.getTipoReporte().getNombreTipoReporte().equals("Reporte Medicamentos (Todos)")){
                generarArchivoExcelMedicamentosTodos(reporteAsociado, destinatario);
            } else if (reporteAsociado.getTipoReporte().getNombreTipoReporte().equals("Reporte Medicamento (Uno)")){
                Log.d("MiApp","Se intenta filtrar por medicamento: "+reporteAsociado.getNombreMed());
                for (RegistroRecordatorio registro : listaTotalRegistroRecordatorios){
                    if (registro.getRecordatorio().getMedicamento().getNombreMedicamento().equals(reporteAsociado.getNombreMed())){
                        listaTotalRegistroRecordatoriosFiltroMed.add(registro);
                    }
                }
                if (listaTotalRegistroRecordatoriosFiltroMed!=null && !listaTotalRegistroRecordatoriosFiltroMed.isEmpty()){
                    generarArchivoExcelMedicamentoUno(reporteAsociado, destinatario);
                } else {
                    Toast.makeText(this, "No existen registros con ese nombre de medicamento.", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_CANCELED);
                    finish();
                    Log.d("MiApp", "Se cierra la actividad CompartirReporte");
                }
            } else if (reporteAsociado.getTipoReporte().getNombreTipoReporte().equals("Reporte Síntomas")){
                Log.d("MiApp", "Llamo a obtenerCalendariosSintomas");
                obtenerCalendarioSintomas(listener2);
                //generarArchivoExcelSíntomas(reporteAsociado, destinatario);
            } else if (reporteAsociado.getTipoReporte().getNombreTipoReporte().equals("Reporte Mediciones")){
                obtenerCalendarioMediciones(listener2);
                //generarArchivoExcelMediciones(reporteAsociado, destinatario);
            }
        } catch (FileNotFoundException e) {
            // Manejo de la excepción FileNotFoundException
            e.printStackTrace(); // Imprime la traza de la excepción para depuración
            Toast.makeText(this, "No se encontró el archivo.", Toast.LENGTH_SHORT).show();
            setResult(RESULT_CANCELED);
            finish();
            Log.d("MiApp", "Se cierra la actividad CompartirReporte");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void generarArchivoExcelMedicamentosTodos(Reporte reporteAsociado, String destinatario) throws IOException {
        //Acceder a la Plantilla
        AssetManager assetManager = getAssets();
        InputStream inputStream = assetManager.open("Reporte_Medicamentos_Todos.xls"); {
            try {
                // Libro de Trabajo
                Workbook workbook = new HSSFWorkbook(inputStream);

                // --- PRIMER HOJA ---
                Sheet sheet = workbook.getSheetAt(0);

                // --- TÍTULO / PRIMER HOJA ---
                Row headerRow0 = sheet.getRow(0);
                Cell Titulocell = headerRow0.getCell(1);
                Titulocell.setCellValue(reporteAsociado.getTipoReporte().getNombreTipoReporte()); // Título

                // --- DATOS DEL REPORTE / PRIMER HOJA ---
                // Primer fila para instituciones y usuario
                if (usuarioLogeado.getNombreInstitucion() != null) {
                    Row headerRow1 = sheet.getRow(1);
                    Cell nombreInstitucion = headerRow1.getCell(5);
                    nombreInstitucion.setCellValue(usuarioLogeado.getNombreInstitucion() + "  -  " + usuarioLogeado.getUsuarioUnico());
                } else {
                    Row headerRow1 = sheet.getRow(1);
                    Cell tituloInstitucion = headerRow1.getCell(1);
                    tituloInstitucion.setCellValue("USUARIO:");
                    Cell nombreInstitucion = headerRow1.getCell(5);
                    nombreInstitucion.setCellValue(usuarioLogeado.getUsuarioUnico());
                }
                // Segunda fila para datos de fecha generada
                Row headerRow2 = sheet.getRow(2);
                Cell fechaGenerada = headerRow2.getCell(5);
                fechaGenerada.setCellValue(reporteAsociado.getFechaGenerada().toString());
                // Tercer fila para fechas desde y hasta
                Row headerRow3 = sheet.getRow(3);
                Cell fechaDesde = headerRow3.getCell(3);
                fechaDesde.setCellValue(reporteAsociado.getFechaDesde().toString());
                Cell fechaHasta = headerRow3.getCell(6);
                fechaHasta.setCellValue(reporteAsociado.getFechaHasta().toString());


                // --- FILA ENCABEZADOS DE COLUMNAS / PRIMER HOJA ---
                // Crear una fila para los encabezados de las columnas
                Row headerRow = sheet.getRow(4);
                // Crear celdas de encabezados de columnas
                Cell headerCell1 = headerRow.getCell(1);
                headerCell1.setCellValue("Tipo");
                Cell headerCell2 = headerRow.getCell(2);
                headerCell2.setCellValue("Nombre Medicamento");
                Cell headerCell3 = headerRow.getCell(3);
                headerCell3.setCellValue("Fecha Registrado");
                Cell headerCell4 = headerRow.getCell(4);
                headerCell4.setCellValue("Programado Para");
                Cell headerCell5 = headerRow.getCell(5);
                headerCell5.setCellValue("Valor");
                Cell headerCell6 = headerRow.getCell(6);
                headerCell6.setCellValue("Notas");  // En caso de omisión su motivo; en caso de tomado su instruccion


                // --- DATOS / PRIMER HOJA ---
                int fila = 5;   // Comienza desde la fila 5; fila 0 es el título, fila 1,2y3 los datos del reporte, fila 4 los encabezados
                for (RegistroRecordatorio registroRecordatorio : listaTotalRegistroRecordatorios) {
                    Log.d("MiApp", "Creando datos de registro, fila: " + fila);
                    Row dataRow = sheet.getRow(fila);    // Se crea CADA FILA de datos, va incrementando

                    // Si la celda en la columna 1 es nula o está vacía, crea la fila y las celdas antes de establecer el valor
                    if (dataRow == null || dataRow.getCell(1) == null || dataRow.getCell(1).getStringCellValue() == null || dataRow.getCell(1).getStringCellValue().isEmpty()) {
                        int ultimaFila = fila - 1;
                        Log.d("MiApp", "    Entra en el if(dataRow.getCell(1)=null), con fila: " + fila + ", y ultimaFila: " + ultimaFila);
                        if (ultimaFila >= 5) { // No copiar datos ni estilo de los encabezados
                            // Crea la fila si no existe
                            if (dataRow == null) {
                                dataRow = sheet.createRow(fila);
                            }
                            for (int columna = 0; columna < 8; columna++) {
                                // Verifica si la celda actual es nula y, si lo es, créala antes de copiar el valor
                                Cell currentCell = dataRow.getCell(columna);
                                if (currentCell == null) {
                                    currentCell = dataRow.createCell(columna);
                                    Log.d("MiApp", "      Entra en el if(currentCell=null), fila: " + fila + ", col = " + columna);
                                }
                                // Copia los valores de la fila anterior a las celdas de la fila actual en el rango 0 a 7
                                Cell celdaAnterior = sheet.getRow(ultimaFila).getCell(columna);
                                // Verifica si la celda anterior tiene un estilo definido
                                if (celdaAnterior != null && celdaAnterior.getCellStyle() != null) {
                                    // Obtiene el estilo de la celda anterior y lo aplica a la nueva
                                    CellStyle estiloCeldaAnterior = celdaAnterior.getCellStyle();
                                    CellStyle estiloNuevaCelda = currentCell.getCellStyle();
                                    estiloNuevaCelda.cloneStyleFrom(estiloCeldaAnterior);

                                    Log.d("MiApp", "      Entra en el if de Style, fila: " + fila + ", col = " + columna);
                                }
                                // Establece el valor en la nueva celda
                                currentCell.setCellValue(celdaAnterior != null ? celdaAnterior.getRichStringCellValue() : null);
                            }
                        }
                    }

                    // Se setean los datos nuevos dentro de la fila dataRow
                    Cell dataCell1 = dataRow.getCell(1);
                    dataCell1.setCellValue(registroRecordatorio.getRecordatorio().getPresentacionMed().getNombrePresentacionMed()); // Columna Tipo: nombre de la presentaciónMed
                    Cell dataCell2 = dataRow.getCell(2);
                    dataCell2.setCellValue(registroRecordatorio.getRecordatorio().getMedicamento().getNombreMedicamento()); // Columna Nombre Medicamento
                    Cell dataCell3 = dataRow.getCell(3);
                    if (registroRecordatorio.getFechaTomaReal() == null) {
                        dataCell3.setCellValue(registroRecordatorio.getFechaTomaEsperada().toLocalDate().toString()); // Columna Fecha Registrado: fecha toma real (tomados), sino fecha esperada ???
                    } else {
                        dataCell3.setCellValue(registroRecordatorio.getFechaTomaReal().toLocalDate().toString()); // Columna Fecha Registrado: fecha toma real (tomados)
                    }
                    Cell dataCell4 = dataRow.getCell(4);
                    dataCell4.setCellValue(registroRecordatorio.getFechaTomaEsperada().toLocalDate().toString() + " / " + registroRecordatorio.getFechaTomaEsperada().toLocalTime().toString()); // Columna Programado Para: fecha esperada
                    Cell dataCell5 = dataRow.getCell(5);
                    if (registroRecordatorio.isTomaRegistroRecordatorio()) {
                        dataCell5.setCellValue("Tomado");      // Columna Valor: Tomado si isTomaRegistroRecordatorio = true
                    } else if (registroRecordatorio.getOmision()!= null){
                        dataCell5.setCellValue("Omitido");      // Columna Valor: Omitido si isTomaRegistroRecordatorio = false y existe Omisión
                    } else {
                        dataCell5.setCellValue("No Tomado");      // Columna Valor: No Tomado si isTomaRegistroRecordatorio = false y no existe Omisión (Por si es a Futuro)
                    }
                    Cell dataCell6 = dataRow.getCell(6);
                    if (registroRecordatorio.getOmision()!= null) {
                        dataCell6.setCellValue("Motivo Omisión: " + registroRecordatorio.getOmision().getNombreOmision());  // Columna Notas: Si es un registro omitido, el Motivo Omisión
                    } else {
                        dataCell6.setCellValue("Instrucción: " + registroRecordatorio.getRecordatorio().getInstruccion().getNombreInstruccion() + " - " + registroRecordatorio.getRecordatorio().getInstruccion().getDescInstruccion());    // Columna Notas: Si es un registro tomado, la Instrucción
                    }
                    fila++; // Incrementa el número de fila en cada iteración
                }


                // --- SEGUNDA HOJA ---
                // Crear una hoja de trabajo 2 (worksheet)
                Sheet sheet2 = workbook.getSheetAt(1);

                // --- TÍTULO / SEGUNDA HOJA ---
                // Crear una fila para el título con celdas unificadas
                Row headerRow2_0 = sheet2.getRow(0);
                // Crear una celda que abarque las 6 primeras columnas
                Cell Titulocell2 = headerRow2_0.getCell(1);
                Titulocell2.setCellValue(("Estadísticas del " + reporteAsociado.getTipoReporte().getNombreTipoReporte())); // Título que se verá en las celdas unificadas

                // --- DATOS DEL REPORTE / SEGUNDA HOJA ---
                // Primer fila para instituciones y usuario
                if (usuarioLogeado.getNombreInstitucion() != null) {
                    Row headerRow2_1 = sheet2.getRow(1);
                    Cell nombreInstitucion2 = headerRow2_1.getCell(5);
                    nombreInstitucion2.setCellValue(usuarioLogeado.getNombreInstitucion() + "  -  " + usuarioLogeado.getUsuarioUnico());
                } else {
                    Row headerRow2_1 = sheet2.getRow(1);
                    Cell tituloInstitucion2 = headerRow2_1.getCell(1);
                    tituloInstitucion2.setCellValue("USUARIO:");
                    Cell nombreInstitucion2 = headerRow2_1.getCell(5);
                    nombreInstitucion2.setCellValue(usuarioLogeado.getUsuarioUnico());
                }
                // Segunda fila para datos de fecha generada
                Row headerRow2_2 = sheet2.getRow(2);
                Cell fechaGenerada2 = headerRow2_2.getCell(5);
                fechaGenerada2.setCellValue(reporteAsociado.getFechaGenerada().toString());
                // Tercer fila para fechas desde y hasta
                Row headerRow2_3 = sheet2.getRow(3);
                Cell fechaDesde2 = headerRow2_3.getCell(3);
                fechaDesde2.setCellValue(reporteAsociado.getFechaDesde().toString());
                Cell fechaHasta2 = headerRow2_3.getCell(7);
                fechaHasta2.setCellValue(reporteAsociado.getFechaHasta().toString());

                // --- FILA ENCABEZADOS DE COLUMNAS / SEGUNDA HOJA ---
                // Crear una fila para los encabezados de las columnas
                Row headerRow2_4 = sheet2.getRow(4);
                // Crear celdas de encabezados de columnas
                Cell headerCell2_1 = headerRow2_4.getCell(1);
                headerCell2_1.setCellValue("Nombre del Medicamento");
                Cell headerCell2_2 = headerRow2_4.getCell(2);
                headerCell2_2.setCellValue("Procentaje de Cumplimiento (%)");
                Cell headerCell2_3 = headerRow2_4.getCell(3);
                headerCell2_3.setCellValue("Gráfico de Cumplimiento");


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
                int filaActual = 5; // Comienza desde la fila 5; fila 0 es el título, fila 1,2y3 los datos del reporte, fila 4 los encabezados
                Log.d("MiApp", "Comienzo con Hoja 2");
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
                    double porcentajeCumplimiento = (double) registrosTomados / totalRegistros * 100.0 ; //* 100.0
                    // Crear un formato decimal con dos decimales
                    DecimalFormat df = new DecimalFormat("#.##");
                    // Formatear el porcentaje con dos decimales y agregar "%"
                    //String porcentajeFormateado = df.format(porcentajeCumplimiento) + "%";

                    // ---
                    Row filaResumen = sheet2.getRow(filaActual);
                    if (filaResumen == null || filaResumen.getCell(1) == null || filaResumen.getCell(1).getStringCellValue() == null || filaResumen.getCell(1).getStringCellValue().isEmpty()) {
                        int ultimaFila = filaActual - 1;
                        Log.d("MiApp", "    Entra en el if(dataRow.getCell(1)=null), con fila: " + filaActual + ", y ultimaFila: " + ultimaFila);
                        if (ultimaFila >= 5) { // No copiar datos ni estilo de los encabezados
                            // Crea la fila si no existe
                            if (filaResumen == null) {
                                filaResumen = sheet.createRow(filaActual);
                            }
                            for (int columna = 0; columna < 2; columna++) {
                                // Verifica si la celda actual es nula y, si lo es, créala antes de copiar el valor
                                Cell currentCell2 = filaResumen.getCell(columna);
                                if (currentCell2 == null) {
                                    currentCell2 = filaResumen.createCell(columna);
                                    Log.d("MiApp", "      Entra en el if(currentCell=null), fila: " + filaActual + ", col = " + columna);
                                }
                                // Copia los valores de la fila anterior a las celdas de la fila actual en el rango 0 a 7
                                Cell celdaAnterior2 = sheet.getRow(ultimaFila).getCell(columna);
                                // Verifica si la celda anterior tiene un estilo definido
                                if (celdaAnterior2 != null && celdaAnterior2.getCellStyle() != null) {
                                    // Obtiene el estilo de la celda anterior y lo aplica a la nueva
                                    CellStyle estiloCeldaAnterior2 = celdaAnterior2.getCellStyle();
                                    CellStyle estiloNuevaCelda2 = currentCell2.getCellStyle();
                                    estiloNuevaCelda2.cloneStyleFrom(estiloCeldaAnterior2);

                                    Log.d("MiApp", "      Entra en el if de Style, fila: " + filaActual + ", col = " + columna);
                                }
                                // Establece el valor en la nueva celda
                                currentCell2.setCellValue(celdaAnterior2 != null ? celdaAnterior2.getRichStringCellValue() : null);
                            }
                        }
                    }

                    // Se setean los datos nuevos dentro de la fila dataRow
                    Cell celdaNombreMedicamento = filaResumen.getCell(1);
                    celdaNombreMedicamento.setCellValue(nombreMedicamento);
                    Cell celdaPorcentajeCumplimiento = filaResumen.getCell(2);
                    //celdaPorcentajeCumplimiento.setCellValue(porcentajeFormateado);
                    celdaPorcentajeCumplimiento.setCellValue(porcentajeCumplimiento);
                    filaActual++;
                }

                // --- GUARDAR EL ARCHIVO EXCEL GENERADO ---
                // Ruta del almacenamiento interno en Android para guardar el archivo
                File dir = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
                String filePath = dir.getAbsolutePath() + "/MediCAL" + reporteAsociado.getTipoReporte().getNombreTipoReporte() + ".xlsx";
                // Crear el archivo Excel
                file = new File(filePath);
                try {
                    FileOutputStream fos = new FileOutputStream(file);
                    workbook.write(fos);
                    fos.close();
                    enviarReporte(destinatario);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace(); // Puedes imprimir la traza de la excepción para depurar
            }
        }
    }

    private void generarArchivoExcelMedicamentoUno(Reporte reporteAsociado, String destinatario) throws IOException {
        //Acceder a la Plantilla
        AssetManager assetManager = getAssets();
        InputStream inputStream = assetManager.open("Reporte_Medicamento_Uno.xls"); {
            try {
                // Libro de Trabajo
                Workbook workbook = new HSSFWorkbook(inputStream);

                // --- PRIMER HOJA ---
                Sheet sheet = workbook.getSheetAt(0);

                // --- TÍTULO / PRIMER HOJA ---
                Row headerRow0 = sheet.getRow(0);
                // Crear una celda que abarque las 6 primeras columnas (índices de 1 a 6, 0 es un margen)
                Cell Titulocell = headerRow0.getCell(1);
                Titulocell.setCellValue("Reporte Medicamento (" + reporteAsociado.getNombreMed() + ")"); // Título que se verá en las celdas unificadas

                // --- DATOS DEL REPORTE / PRIMER HOJA ---
                // Primer fila para instituciones y usuario
                if (usuarioLogeado.getNombreInstitucion() != null) {
                    Row headerRow1 = sheet.getRow(1);
                    Cell nombreInstitucion = headerRow1.getCell(5);
                    nombreInstitucion.setCellValue(usuarioLogeado.getNombreInstitucion() + "  -  " + usuarioLogeado.getUsuarioUnico());
                } else {
                    Row headerRow1 = sheet.getRow(1);
                    Cell tituloInstitucion = headerRow1.getCell(1);
                    tituloInstitucion.setCellValue("USUARIO:");
                    Cell nombreInstitucion = headerRow1.getCell(5);
                    nombreInstitucion.setCellValue(usuarioLogeado.getUsuarioUnico());
                }
                // Segunda fila para datos de fecha generada
                Row headerRow2 = sheet.getRow(2);
                Cell fechaGenerada = headerRow2.getCell(5);
                fechaGenerada.setCellValue(reporteAsociado.getFechaGenerada().toString());
                // Tercer fila para fechas desde y hasta
                Row headerRow3 = sheet.getRow(3);
                Cell fechaDesde = headerRow3.getCell(2);
                fechaDesde.setCellValue(reporteAsociado.getFechaDesde().toString());
                Cell fechaHasta = headerRow3.getCell(4);
                fechaHasta.setCellValue(reporteAsociado.getFechaHasta().toString());
                Cell filtro = headerRow3.getCell(6);
                filtro.setCellValue(reporteAsociado.getNombreMed());

                // --- FILA ENCABEZADOS DE COLUMNAS / PRIMER HOJA ---
                // Crear una fila para los encabezados de las columnas
                Row headerRow = sheet.getRow(4);
                // Crear celdas de encabezados de columnas
                Cell headerCell1 = headerRow.getCell(1);
                headerCell1.setCellValue("Tipo");
                Cell headerCell2 = headerRow.getCell(2);
                headerCell2.setCellValue("Nombre Medicamento");
                Cell headerCell3 = headerRow.getCell(3);
                headerCell3.setCellValue("Fecha Registrado");
                Cell headerCell4 = headerRow.getCell(4);
                headerCell4.setCellValue("Programado Para");
                Cell headerCell5 = headerRow.getCell(5);
                headerCell5.setCellValue("Valor");
                Cell headerCell6 = headerRow.getCell(6);
                headerCell6.setCellValue("Notas");  // En caso de omisión su motivo; en caso de tomado su instruccion


                // --- DATOS / PRIMER HOJA ---
                int fila = 5;   // Comienza desde la fila 2; fila 0 es el título, fila 1y2 los datos del reporte, fila 3 los encabezados
                //int[] ultimaFilaPorColumna = new int[8]; // 8 es el número de columnas procesadas (0 y 7 son los márgenes)
                for (RegistroRecordatorio registroRecordatorio : listaTotalRegistroRecordatoriosFiltroMed) {
                    Log.d("MiApp", "Creando datos de registro, fila: " + fila);
                    Row dataRow = sheet.getRow(fila);    // Se crea CADA FILA de datos, va incrementando

                    // Si la celda en la columna 1 es nula o está vacía, crea la fila y las celdas antes de establecer el valor
                    if (dataRow == null || dataRow.getCell(1) == null || dataRow.getCell(1).getStringCellValue() == null || dataRow.getCell(1).getStringCellValue().isEmpty()) {
                        int ultimaFila = fila - 1;
                        Log.d("MiApp", "    Entra en el if(dataRow.getCell(1)=null), con fila: " + fila + ", y ultimaFila: " + ultimaFila);
                        if (ultimaFila >= 5) { // No copiar datos ni estilo de los encabezados
                            // Crea la fila si no existe
                            if (dataRow == null) {
                                dataRow = sheet.createRow(fila);
                            }
                            for (int columna = 0; columna < 8; columna++) {
                                // Verifica si la celda actual es nula y, si lo es, créala antes de copiar el valor
                                Cell currentCell = dataRow.getCell(columna);
                                if (currentCell == null) {
                                    currentCell = dataRow.createCell(columna);
                                    Log.d("MiApp", "      Entra en el if(currentCell=null), fila: " + fila + ", col = " + columna);
                                }
                                // Copia los valores de la fila anterior a las celdas de la fila actual en el rango 0 a 7
                                Cell celdaAnterior = sheet.getRow(ultimaFila).getCell(columna);
                                // Verifica si la celda anterior tiene un estilo definido
                                if (celdaAnterior != null && celdaAnterior.getCellStyle() != null) {
                                    // Obtiene el estilo de la celda anterior y lo aplica a la nueva
                                    CellStyle estiloCeldaAnterior = celdaAnterior.getCellStyle();
                                    CellStyle estiloNuevaCelda = currentCell.getCellStyle();
                                    estiloNuevaCelda.cloneStyleFrom(estiloCeldaAnterior);

                                    Log.d("MiApp", "      Entra en el if de Style, fila: " + fila + ", col = " + columna);
                                }
                                // Establece el valor en la nueva celda
                                currentCell.setCellValue(celdaAnterior != null ? celdaAnterior.getRichStringCellValue() : null);
                            }
                        }
                    }

                    // Se setean los datos nuevos dentro de la fila dataRow
                    Cell dataCell1 = dataRow.getCell(1);
                    dataCell1.setCellValue(registroRecordatorio.getRecordatorio().getPresentacionMed().getNombrePresentacionMed()); // Columna Tipo: nombre de la presentaciónMed
                    Cell dataCell2 = dataRow.getCell(2);
                    dataCell2.setCellValue(registroRecordatorio.getRecordatorio().getMedicamento().getNombreMedicamento()); // Columna Nombre Medicamento
                    Cell dataCell3 = dataRow.getCell(3);
                    if (registroRecordatorio.getFechaTomaReal() == null) {
                        dataCell3.setCellValue(registroRecordatorio.getFechaTomaEsperada().toLocalDate().toString()); // Columna Fecha Registrado: fecha toma real (tomados), sino fecha esperada ???
                    } else {
                        dataCell3.setCellValue(registroRecordatorio.getFechaTomaReal().toLocalDate().toString()); // Columna Fecha Registrado: fecha toma real (tomados)
                    }
                    Cell dataCell4 = dataRow.getCell(4);
                    dataCell4.setCellValue(registroRecordatorio.getFechaTomaEsperada().toLocalDate().toString() + " / " + registroRecordatorio.getFechaTomaEsperada().toLocalTime().toString()); // Columna Programado Para: fecha esperada
                    Cell dataCell5 = dataRow.getCell(5);
                    if (registroRecordatorio.isTomaRegistroRecordatorio()) {
                        dataCell5.setCellValue("Tomado");      // Columna Valor: Tomado si isTomaRegistroRecordatorio = true
                    } else if (registroRecordatorio.getOmision()!= null){
                        dataCell5.setCellValue("Omitido");      // Columna Valor: Omitido si isTomaRegistroRecordatorio = false y existe Omisión
                    } else {
                        dataCell5.setCellValue("No Tomado");      // Columna Valor: No Tomado si isTomaRegistroRecordatorio = false y no existe Omisión (Por si es a Futuro)
                    }
                    Cell dataCell6 = dataRow.getCell(6);
                    if (registroRecordatorio.getOmision()!= null) {
                        dataCell6.setCellValue("Motivo Omisión: " + registroRecordatorio.getOmision().getNombreOmision());  // Columna Notas: Si es un registro omitido, el Motivo Omisión
                    } else {
                        dataCell6.setCellValue("Instrucción: " + registroRecordatorio.getRecordatorio().getInstruccion().getNombreInstruccion() + " - " + registroRecordatorio.getRecordatorio().getInstruccion().getDescInstruccion());    // Columna Notas: Si es un registro tomado, la Instrucción
                    }
                    fila++; // Incrementa el número de fila en cada iteración
                }


                // --- SEGUNDA HOJA ---
                // Crear una hoja de trabajo 2 (worksheet)
                Sheet sheet2 = workbook.getSheetAt(1);

                // --- TÍTULO / SEGUNDA HOJA ---
                // Crear una fila para el título con celdas unificadas
                Row headerRow2_0 = sheet2.getRow(0);
                // Crear una celda que abarque las 6 primeras columnas
                Cell Titulocell2 = headerRow2_0.getCell(1);
                Titulocell2.setCellValue("Estadísticas del Reporte Medicamento (" + reporteAsociado.getNombreMed() + ")"); // Título que se verá en las celdas unificadas

                // --- DATOS DEL REPORTE / SEGUNDA HOJA ---
                // Primer fila para instituciones y usuario
                if (usuarioLogeado.getNombreInstitucion() != null) {
                    Row headerRow2_1 = sheet2.getRow(1);
                    Cell nombreInstitucion2 = headerRow2_1.getCell(5);
                    nombreInstitucion2.setCellValue(usuarioLogeado.getNombreInstitucion() + "  -  " + usuarioLogeado.getUsuarioUnico());
                } else {
                    Row headerRow2_1 = sheet2.getRow(1);
                    Cell tituloInstitucion2 = headerRow2_1.getCell(1);
                    tituloInstitucion2.setCellValue("USUARIO:");
                    Cell nombreInstitucion2 = headerRow2_1.getCell(5);
                    nombreInstitucion2.setCellValue(usuarioLogeado.getUsuarioUnico());
                }
                // Segunda fila para datos de fecha generada
                Row headerRow2_2 = sheet2.getRow(2);
                // Crear celdas datos del reporte
                Cell fechaGenerada2 = headerRow2_2.getCell(5);
                fechaGenerada2.setCellValue(reporteAsociado.getFechaGenerada().toString());

                Row headerRow2_3 = sheet2.getRow(3);
                Cell fechaDesde2 = headerRow2_3.getCell(2);
                fechaDesde2.setCellValue(reporteAsociado.getFechaDesde().toString());
                Cell fechaHasta2 = headerRow2_3.getCell(5);
                fechaHasta2.setCellValue(reporteAsociado.getFechaHasta().toString());
                Cell filtro2 = headerRow2_3.getCell(8);
                filtro2.setCellValue(reporteAsociado.getNombreMed());

                // --- FILA ENCABEZADOS DE COLUMNAS / SEGUNDA HOJA ---
                // Crear una fila para los encabezados de las columnas
                Row headerRow2_4 = sheet2.getRow(4);
                // Crear celdas de encabezados de columnas
                Cell headerCell2_1 = headerRow2_4.getCell(1);
                headerCell2_1.setCellValue("Nombre del Medicamento");
                Cell headerCell2_2 = headerRow2_4.getCell(2);
                headerCell2_2.setCellValue("Procentaje de Cumplimiento (%)");
                Cell headerCell2_3 = headerRow2_4.getCell(3);
                headerCell2_3.setCellValue("Gráfico de Cumplimiento");


                // --- DATOS / SEGUNDA HOJA ---
                // Crear un mapa para agrupar los registros por nombre de medicamento
                Map<String, List<RegistroRecordatorio>> medicamentosAgrupados = new HashMap<>();
                // Agrupar los registros por nombre de medicamento
                for (RegistroRecordatorio registroRecordatorio : listaTotalRegistroRecordatoriosFiltroMed) {
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
                int filaActual = 5; // Comienza desde la fila 5
                Log.d("MiApp", "Comienzo con Hoja 2");
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
                    // Crear un formato decimal con dos decimales
                    //DecimalFormat df = new DecimalFormat("#.##");
                    // Formatear el porcentaje con dos decimales y agregar "%"
                    //String porcentajeFormateado = df.format(porcentajeCumplimiento) + "%";

                    Row filaResumen = sheet2.getRow(filaActual);
                    if (filaResumen == null || filaResumen.getCell(1) == null || filaResumen.getCell(1).getStringCellValue() == null || filaResumen.getCell(1).getStringCellValue().isEmpty()) {
                        int ultimaFila = filaActual - 1;
                        Log.d("MiApp", "    Entra en el if(dataRow.getCell(1)=null), con fila: " + filaActual + ", y ultimaFila: " + ultimaFila);
                        if (ultimaFila >= 5) { // No copiar datos ni estilo de los encabezados
                            // Crea la fila si no existe
                            if (filaResumen == null) {
                                filaResumen = sheet.createRow(filaActual);
                            }
                            for (int columna = 0; columna < 2; columna++) {
                                // Verifica si la celda actual es nula y, si lo es, créala antes de copiar el valor
                                Cell currentCell2 = filaResumen.getCell(columna);
                                if (currentCell2 == null) {
                                    currentCell2 = filaResumen.createCell(columna);
                                    Log.d("MiApp", "      Entra en el if(currentCell=null), fila: " + filaActual + ", col = " + columna);
                                }
                                // Copia los valores de la fila anterior a las celdas de la fila actual en el rango 0 a 7
                                Cell celdaAnterior2 = sheet.getRow(ultimaFila).getCell(columna);
                                // Verifica si la celda anterior tiene un estilo definido
                                if (celdaAnterior2 != null && celdaAnterior2.getCellStyle() != null) {
                                    // Obtiene el estilo de la celda anterior y lo aplica a la nueva
                                    CellStyle estiloCeldaAnterior2 = celdaAnterior2.getCellStyle();
                                    CellStyle estiloNuevaCelda2 = currentCell2.getCellStyle();
                                    estiloNuevaCelda2.cloneStyleFrom(estiloCeldaAnterior2);

                                    Log.d("MiApp", "      Entra en el if de Style, fila: " + filaActual + ", col = " + columna);
                                }
                                // Establece el valor en la nueva celda
                                currentCell2.setCellValue(celdaAnterior2 != null ? celdaAnterior2.getRichStringCellValue() : null);
                            }
                        }
                    }

                    // Se setean los datos nuevos dentro de la fila dataRow
                    Cell celdaNombreMedicamento = filaResumen.getCell(1);
                    celdaNombreMedicamento.setCellValue(nombreMedicamento);
                    Cell celdaPorcentajeCumplimiento = filaResumen.getCell(2);
                    celdaPorcentajeCumplimiento.setCellValue(porcentajeCumplimiento);

                    filaActual++;
                }

                // --- GUARDAR EL ARCHIVO EXCEL GENERADO ---
                // Ruta del almacenamiento interno en Android para guardar el archivo
                File dir = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
                String filePath = dir.getAbsolutePath() + "/MediCALReporteMedicamento(" +reporteAsociado.getNombreMed()+ ").xlsx";
                // Crear el archivo Excel
                file = new File(filePath);
                try {
                    FileOutputStream fos = new FileOutputStream(file);
                    workbook.write(fos);
                    fos.close();
                    enviarReporte(destinatario);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace(); // Puedes imprimir la traza de la excepción para depurar
            }
        }
    }


    private void obtenerCalendarioSintomas(OnDataLoadedListener listener2) throws IOException {
        for (Calendario calendario : listaTotalDeCalendarios) {
            RetrofitService retrofitService = new RetrofitService();
            CalendarioSintomaApi calendarioSintomaApi = retrofitService.getRetrofit().create(CalendarioSintomaApi.class);
            operacionesPendientes = listaTotalDeCalendarios.size();
            // Hacer la llamada a la API para obtener las clases "Recordatorio" asociadas a un calendario
            Call<List<CalendarioSintoma>> calSintomaCall = calendarioSintomaApi.getByCodCalendarioSintoma(calendario.getCodCalendario());

            calSintomaCall.enqueue(new Callback<List<CalendarioSintoma>>() {
                @Override
                public void onResponse(Call<List<CalendarioSintoma>> call, Response<List<CalendarioSintoma>> response) {
                    if (response.isSuccessful()) {
                        List<CalendarioSintoma> calendarioSintomasAsociados = response.body();
                        if (calendarioSintomasAsociados != null && !calendarioSintomasAsociados.isEmpty()) {
                            Log.d("MiApp", "CalendarioSintomas Asociados Encontrados: ");
                            for (CalendarioSintoma calendarioSintoma : calendarioSintomasAsociados) {
                                if (calendarioSintoma.getFechaCalendarioSintoma().toLocalDate().isAfter(fechaDesdeReporte.minusDays(1))
                                        && calendarioSintoma.getFechaCalendarioSintoma().toLocalDate().isBefore(fechaHastaReporte.plusDays(1))){
                                    Log.d("MiApp", "codCalendarioSintoma encontrado: " + calendarioSintoma.getCodCalendarioSintoma());
                                    listaTotalCalendarioSintomas.add(calendarioSintoma);
                                }
                            }
                            operacionesPendientes--;
                            Log.d("MiApp","OperacionesPendientes: " + operacionesPendientes);
                            // Verifica si se han completado todas las operaciones
                            if (operacionesPendientes == 0) {
                                // Todas las operaciones se han completado, ejecuta el listener
                                try {
                                    listener2.onDataLoaded();
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                Log.d("MiApp", "Ya se ejecutó el listener de operacionesPendientes");
                            }
                        } else {
                            Log.d("MiApp", "No se encontraron clases 'CalendarioSintoma' asociadas al calendario " + calendario.getNombreCalendario());
                            Toast.makeText(CompartirReporteActivity.this, "No se encontraron síntomas entre estas fechas.", Toast.LENGTH_SHORT).show();
                            finish(); // Cierra CompartirReporteActivity
                        }
                    } else {
                        Log.d("MiApp", "Error en la solicitud de clases 'CalendarioSintoma': " + response.message());
                    }
                }
                @Override
                public void onFailure(Call<List<CalendarioSintoma>> call, Throwable t) {
                    Log.e("MiApp", "Error en la solicitud de clases 'CalendarioSintoma': " + t.getMessage());
                }
            });
        }
    }
    private void generarArchivoExcelSíntomas(Reporte reporteAsociado, String destinatario) throws IOException {
        //Acceder a la Plantilla
        AssetManager assetManager = getAssets();
        InputStream inputStream = assetManager.open("Reporte_Sintomas.xls"); {
            try {
                // Libro de Trabajo
                Workbook workbook = new HSSFWorkbook(inputStream);

                // --- PRIMER HOJA ---
                Sheet sheet = workbook.getSheetAt(0);

                // --- TÍTULO / PRIMER HOJA ---
                Row headerRow0 = sheet.getRow(0);
                // Crear una celda que abarque las 7 primeras columnas (índices de 1 a 7, 0 es un margen)
                Cell Titulocell = headerRow0.getCell(1);
                Titulocell.setCellValue(reporteAsociado.getTipoReporte().getNombreTipoReporte()); // Título que se verá en las celdas unificadas

                // --- DATOS DEL REPORTE / PRIMER HOJA ---
                // Primer fila para instituciones y usuario
                if (usuarioLogeado.getNombreInstitucion() != null) {
                    Row headerRow1 = sheet.getRow(1);
                    Cell nombreInstitucion = headerRow1.getCell(6);
                    nombreInstitucion.setCellValue(usuarioLogeado.getNombreInstitucion() + "  -  " + usuarioLogeado.getUsuarioUnico());
                } else {
                    Row headerRow1 = sheet.getRow(1);
                    Cell tituloInstitucion = headerRow1.getCell(1);
                    tituloInstitucion.setCellValue("USUARIO:");
                    Cell nombreInstitucion = headerRow1.getCell(6);
                    nombreInstitucion.setCellValue(usuarioLogeado.getUsuarioUnico());
                }
                // Segunda fila para datos de fecha generada
                Row headerRow2 = sheet.getRow(2);
                Cell fechaGenerada = headerRow2.getCell(6);
                fechaGenerada.setCellValue(reporteAsociado.getFechaGenerada().toString());
                // Tercer fila para datos de fecha desde, hasta y cantidad
                Row headerRow3 = sheet.getRow(3);
                Cell fechaDesde = headerRow3.getCell(2);
                fechaDesde.setCellValue(reporteAsociado.getFechaDesde().toString());
                Cell fechaHasta = headerRow3.getCell(5);
                fechaHasta.setCellValue(reporteAsociado.getFechaHasta().toString());
                Cell cantTotal = headerRow3.getCell(11);
                cantTotal.setCellValue(listaTotalCalendarioSintomas.size());


                // --- DATOS / PRIMER PARTE ---
                int fila = 5;   // Comienza desde la fila 5
                for (CalendarioSintoma calendarioSintoma : listaTotalCalendarioSintomas) {
                    Log.d("MiApp", "Creando datos de registro, fila: " + fila);
                    Row dataRow = sheet.getRow(fila);    // Se crea CADA FILA de datos, va incrementando

                    // Si la celda en la columna 1 es nula o está vacía, crea la fila y las celdas antes de establecer el valor
                    if (dataRow == null || dataRow.getCell(1) == null || dataRow.getCell(1).getStringCellValue() == null || dataRow.getCell(1).getStringCellValue().isEmpty()) {
                        int ultimaFila = fila - 1;
                        Log.d("MiApp", "    Entra en el if(dataRow.getCell(1)=null), con fila: " + fila + ", y ultimaFila: " + ultimaFila);
                        if (ultimaFila >= 5) { // No copiar datos ni estilo de los encabezados
                            // Crea la fila si no existe
                            if (dataRow == null) {
                                dataRow = sheet.createRow(fila);
                            }
                            for (int columna = 0; columna < 4; columna++) {
                                // Verifica si la celda actual es nula y, si lo es, créala antes de copiar el valor
                                Cell currentCell = dataRow.getCell(columna);
                                if (currentCell == null) {
                                    currentCell = dataRow.createCell(columna);
                                    Log.d("MiApp", "      Entra en el if(currentCell=null), fila: " + fila + ", col = " + columna);
                                }
                                // Copia los valores de la fila anterior a las celdas de la fila actual en el rango 0 a 7
                                Cell celdaAnterior = sheet.getRow(ultimaFila).getCell(columna);
                                // Verifica si la celda anterior tiene un estilo definido
                                if (celdaAnterior != null && celdaAnterior.getCellStyle() != null) {
                                    // Obtiene el estilo de la celda anterior y lo aplica a la nueva
                                    CellStyle estiloCeldaAnterior = celdaAnterior.getCellStyle();
                                    CellStyle estiloNuevaCelda = currentCell.getCellStyle();
                                    estiloNuevaCelda.cloneStyleFrom(estiloCeldaAnterior);

                                    Log.d("MiApp", "      Entra en el if de Style, fila: " + fila + ", col = " + columna);
                                }
                                // Establece el valor en la nueva celda
                                currentCell.setCellValue(celdaAnterior != null ? celdaAnterior.getRichStringCellValue() : null);
                            }
                        }
                    }

                    // Se setean los datos nuevos dentro de la fila dataRow
                    Cell dataCell1 = dataRow.getCell(1);
                    dataCell1.setCellValue(calendarioSintoma.getSintoma().getNombreSintoma()); // Columna Tipo: nombre del síntoma
                    Cell dataCell2 = dataRow.getCell(2);
                    dataCell2.setCellValue(calendarioSintoma.getFechaCalendarioSintoma().toLocalDate().toString() + " / " + calendarioSintoma.getFechaCalendarioSintoma().toLocalTime().toString()); // Columna Fecha Registrado

                    fila++; // Incrementa el número de fila en cada iteración
                }


                // --- DATOS / SEGUNDA PARTE ---
                // Crear un mapa para agrupar los registros por nombre de medicamento
                Map<String, List<CalendarioSintoma>> sintomasAgrupados = new HashMap<>();
                int cantidadTotal = 0;
                // Agrupar los registros por nombre de medicamento
                for (CalendarioSintoma calendarioSintoma : listaTotalCalendarioSintomas) {
                    String nombreSintoma = calendarioSintoma.getSintoma().getNombreSintoma();
                    // Verificar si ya existe una lista para este medicamento en el mapa
                    List<CalendarioSintoma> calSintomas = sintomasAgrupados.get(nombreSintoma);
                    if (calSintomas == null) {
                        calSintomas = new ArrayList<>();
                        sintomasAgrupados.put(nombreSintoma, calSintomas);
                    }
                    calSintomas.add(calendarioSintoma);
                    cantidadTotal += calSintomas.size();
                }
                //cantTotal.setCellValue(cantidadTotal);
                // Calcular el porcentaje del total y escribir en la hoja de resumen
                int filaActual = 5; // Comienza desde la fila 2; fila 0 es el título, fila 1y2 los datos del reporte, fila 3 los encabezados
                Log.d("MiApp", "Comienzo con Parte 2");
                for (Map.Entry<String, List<CalendarioSintoma>> entry : sintomasAgrupados.entrySet()) {
                    String nombreSintoma = entry.getKey();
                    List<CalendarioSintoma> calSintomas = entry.getValue();
                    int totalCalendariosSintomas = listaTotalCalendarioSintomas.size();
                    int totalSintomasPorNombre = 0;

                    for (CalendarioSintoma calendarioSintoma : calSintomas) {
                        if (calendarioSintoma.getSintoma().getNombreSintoma().equals(nombreSintoma)) {
                            totalSintomasPorNombre++;
                        }
                    }
                    Log.d("MiApp", "TotalCalendariosSintomas: "+totalCalendariosSintomas);
                    Log.d("MiApp", "Se tiene totalSintomasPorNombre: " + totalSintomasPorNombre + " de Sintoma: "+nombreSintoma);
                    double porcentajeDelTotal = (double) totalSintomasPorNombre / totalCalendariosSintomas * 100.0;
                    // Crear un formato decimal con dos decimales
                    DecimalFormat df = new DecimalFormat("#.##");
                    // Formatear el porcentaje con dos decimales y agregar "%"
                    String porcentajeFormateado = df.format(porcentajeDelTotal) + "%";

                    // ---
                    Row filaResumen = sheet.getRow(filaActual);
                    if (filaResumen == null || filaResumen.getCell(1) == null || filaResumen.getCell(1).getStringCellValue() == null || filaResumen.getCell(1).getStringCellValue().isEmpty()) {
                        int ultimaFila = filaActual - 1;
                        Log.d("MiApp", "    Entra en el if(dataRow.getCell(1)=null), con fila: " + filaActual + ", y ultimaFila: " + ultimaFila);
                        if (ultimaFila >= 5) { // No copiar datos ni estilo de los encabezados
                            // Crea la fila si no existe
                            if (filaResumen == null) {
                                filaResumen = sheet.createRow(filaActual);
                            }
                            for (int columna = 4; columna < 8; columna++) {
                                // Verifica si la celda actual es nula y, si lo es, créala antes de copiar el valor
                                Cell currentCell2 = filaResumen.getCell(columna);
                                if (currentCell2 == null) {
                                    currentCell2 = filaResumen.createCell(columna);
                                    Log.d("MiApp", "      Entra en el if(currentCell=null), fila: " + filaActual + ", col = " + columna);
                                }
                                // Copia los valores de la fila anterior a las celdas de la fila actual
                                Cell celdaAnterior2 = sheet.getRow(ultimaFila).getCell(columna);
                                // Verifica si la celda anterior tiene un estilo definido
                                if (celdaAnterior2 != null && celdaAnterior2.getCellStyle() != null) {
                                    // Obtiene el estilo de la celda anterior y lo aplica a la nueva
                                    CellStyle estiloCeldaAnterior2 = celdaAnterior2.getCellStyle();
                                    CellStyle estiloNuevaCelda2 = currentCell2.getCellStyle();
                                    estiloNuevaCelda2.cloneStyleFrom(estiloCeldaAnterior2);

                                    Log.d("MiApp", "      Entra en el if de Style, fila: " + filaActual + ", col = " + columna);
                                }
                                // Establece el valor en la nueva celda
                                currentCell2.setCellValue(celdaAnterior2 != null ? celdaAnterior2.getRichStringCellValue() : null);
                            }
                        }
                    }

                    // Se setean los datos nuevos dentro de la fila dataRow
                    Cell celdaNombreSintoma = filaResumen.getCell(4);
                    celdaNombreSintoma.setCellValue(nombreSintoma);
                    Cell celdaCantSintoma = filaResumen.getCell(5);
                    celdaCantSintoma.setCellValue(totalSintomasPorNombre);
                    Cell celdaPorcentajeDelTotal = filaResumen.getCell(6);
                    celdaPorcentajeDelTotal.setCellValue(porcentajeFormateado.toString());

                    filaActual++;
                }

                // --- GUARDAR EL ARCHIVO EXCEL GENERADO ---
                // Ruta del almacenamiento interno en Android para guardar el archivo
                File dir = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
                String filePath = dir.getAbsolutePath() + "/MediCAL" + reporteAsociado.getTipoReporte().getNombreTipoReporte() + ".xlsx";
                // Crear el archivo Excel
                file = new File(filePath);
                try {
                    FileOutputStream fos = new FileOutputStream(file);
                    workbook.write(fos);
                    fos.close();
                    enviarReporte(destinatario);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace(); // Puedes imprimir la traza de la excepción para depurar
            }
        }
    }

    private void obtenerCalendarioMediciones(OnDataLoadedListener listener2) throws IOException {
        for (Calendario calendario : listaTotalDeCalendarios) {
            RetrofitService retrofitService = new RetrofitService();
            CalendarioMedicionApi calendarioMedicionApi = retrofitService.getRetrofit().create(CalendarioMedicionApi.class);
            operacionesPendientes = listaTotalDeCalendarios.size();
            // Hacer la llamada a la API para obtener las clases "Recordatorio" asociadas a un calendario
            Call<List<CalendarioMedicion>> calMedicionCall = calendarioMedicionApi.getByCodCalendarioMedicion(calendario.getCodCalendario());

            calMedicionCall.enqueue(new Callback<List<CalendarioMedicion>>() {
                @Override
                public void onResponse(Call<List<CalendarioMedicion>> call, Response<List<CalendarioMedicion>> response) {
                    if (response.isSuccessful()) {
                        List<CalendarioMedicion> calendarioMedicionesAsociados = response.body();
                        if (calendarioMedicionesAsociados != null && !calendarioMedicionesAsociados.isEmpty()) {
                            Log.d("MiApp", "CalendarioMediciones Asociados Encontrados: ");
                            for (CalendarioMedicion calendarioMedicion : calendarioMedicionesAsociados) {
                                if (calendarioMedicion.getFechaCalendarioMedicion().toLocalDate().isAfter(fechaDesdeReporte.minusDays(1))
                                        && calendarioMedicion.getFechaCalendarioMedicion().toLocalDate().isBefore(fechaHastaReporte.plusDays(1))) {
                                    Log.d("MiApp", "codCalendarioMedicion encontrado: " + calendarioMedicion.getCodCalendarioMedicion());
                                    listaTotalCalendarioMediciones.add(calendarioMedicion);
                                }
                            }
                            operacionesPendientes--;
                            Log.d("MiApp","OperacionesPendientes: " + operacionesPendientes);
                            // Verifica si se han completado todas las operaciones
                            if (operacionesPendientes == 0) {
                                // Todas las operaciones se han completado, ejecuta el listener
                                try {
                                    listener2.onDataLoaded();
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                Log.d("MiApp", "Ya se ejecutó el listener de operacionesPendientes");
                            }
                        } else {
                            Log.d("MiApp", "No se encontraron clases 'CalendarioMedicion' asociadas al calendario " + calendario.getNombreCalendario());
                            Toast.makeText(CompartirReporteActivity.this, "No se encontraron mediciones entre estas fechas.", Toast.LENGTH_SHORT).show();
                            finish(); // Cierra CompartirReporteActivity
                        }
                    } else {
                        Log.d("MiApp", "Error en la solicitud de clases 'CalendarioMedicion': " + response.message());
                    }
                }
                @Override
                public void onFailure(Call<List<CalendarioMedicion>> call, Throwable t) {
                    Log.e("MiApp", "Error en la solicitud de clases 'CalendarioMedicion': " + t.getMessage());
                }
            });
        }
    }
    private void generarArchivoExcelMediciones(Reporte reporteAsociado, String destinatario) throws IOException {
        //Acceder a la Plantilla
        AssetManager assetManager = getAssets();
        InputStream inputStream = assetManager.open("Reporte_Mediciones.xls"); {
            try {
                // Libro de Trabajo
                Workbook workbook = new HSSFWorkbook(inputStream);

                // --- PRIMER HOJA ---
                Sheet sheet = workbook.getSheetAt(0);

                // --- TÍTULO / PRIMER HOJA ---
                Row headerRow0 = sheet.getRow(0);
                // Crear una celda que abarque las 7 primeras columnas (índices de 1 a 8, 0 es un margen)
                Cell Titulocell = headerRow0.getCell(1);
                Titulocell.setCellValue(reporteAsociado.getTipoReporte().getNombreTipoReporte()); // Título que se verá en las celdas unificadas

                // --- DATOS DEL REPORTE / PRIMER HOJA ---
                // Primer fila para instituciones y usuario
                if (usuarioLogeado.getNombreInstitucion() != null) {
                    Row headerRow1 = sheet.getRow(1);
                    Cell nombreInstitucion = headerRow1.getCell(7);
                    nombreInstitucion.setCellValue(usuarioLogeado.getNombreInstitucion() + "  -  " + usuarioLogeado.getUsuarioUnico());
                } else {
                    Row headerRow1 = sheet.getRow(1);
                    Cell tituloInstitucion = headerRow1.getCell(1);
                    tituloInstitucion.setCellValue("USUARIO:");
                    Cell nombreInstitucion = headerRow1.getCell(7);
                    nombreInstitucion.setCellValue(usuarioLogeado.getUsuarioUnico());
                }
                // Segunda fila para datos de fecha generada
                Row headerRow2 = sheet.getRow(2);
                Cell fechaGenerada = headerRow2.getCell(7);
                fechaGenerada.setCellValue(reporteAsociado.getFechaGenerada().toString());
                // Tercer fila para datos de fecha desde, hasta y cantidad
                Row headerRow3 = sheet.getRow(3);
                Cell fechaDesde = headerRow3.getCell(2);
                fechaDesde.setCellValue(reporteAsociado.getFechaDesde().toString());
                Cell fechaHasta = headerRow3.getCell(4);
                fechaHasta.setCellValue(reporteAsociado.getFechaHasta().toString());
                Cell cantTotal = headerRow3.getCell(12);
                cantTotal.setCellValue(listaTotalCalendarioMediciones.size());

                // --- DATOS / PRIMER PARTE ---
                int fila = 5;   // Comienza desde la fila 2; fila 0 es el título, fila 1 los datos del reporte, fila 2 los encabezados
                for (CalendarioMedicion calendarioMedicion : listaTotalCalendarioMediciones) {
                    Log.d("MiApp", "Creando datos de registro, fila: " + fila);
                    Row dataRow = sheet.getRow(fila);    // Se crea CADA FILA de datos, va incrementando

                    // Si la celda en la columna 1 es nula o está vacía, crea la fila y las celdas antes de establecer el valor
                    if (dataRow == null || dataRow.getCell(1) == null || dataRow.getCell(1).getStringCellValue() == null || dataRow.getCell(1).getStringCellValue().isEmpty()) {
                        int ultimaFila = fila - 1;
                        Log.d("MiApp", "    Entra en el if(dataRow.getCell(1)=null), con fila: " + fila + ", y ultimaFila: " + ultimaFila);
                        if (ultimaFila >= 5) { // No copiar datos ni estilo de los encabezados
                            // Crea la fila si no existe
                            if (dataRow == null) {
                                dataRow = sheet.createRow(fila);
                            }
                            for (int columna = 0; columna < 5; columna++) {
                                // Verifica si la celda actual es nula y, si lo es, créala antes de copiar el valor
                                Cell currentCell = dataRow.getCell(columna);
                                if (currentCell == null) {
                                    currentCell = dataRow.createCell(columna);
                                    Log.d("MiApp", "      Entra en el if(currentCell=null), fila: " + fila + ", col = " + columna);
                                }
                                // Copia los valores de la fila anterior a las celdas de la fila actual en el rango 0 a 7
                                Cell celdaAnterior = sheet.getRow(ultimaFila).getCell(columna);
                                // Verifica si la celda anterior tiene un estilo definido
                                if (celdaAnterior != null && celdaAnterior.getCellStyle() != null) {
                                    // Obtiene el estilo de la celda anterior y lo aplica a la nueva
                                    CellStyle estiloCeldaAnterior = celdaAnterior.getCellStyle();
                                    CellStyle estiloNuevaCelda = currentCell.getCellStyle();
                                    estiloNuevaCelda.cloneStyleFrom(estiloCeldaAnterior);

                                    Log.d("MiApp", "      Entra en el if de Style, fila: " + fila + ", col = " + columna);
                                }
                                // Establece el valor en la nueva celda
                                currentCell.setCellValue(celdaAnterior != null ? celdaAnterior.getRichStringCellValue() : null);
                            }
                        }
                    }

                    // Se setean los datos nuevos dentro de la fila dataRow
                    Cell dataCell1 = dataRow.getCell(1);
                    dataCell1.setCellValue(calendarioMedicion.getMedicion().getNombreMedicion()); // Columna Tipo: nombre de la medición
                    Cell dataCell2 = dataRow.getCell(2);
                    dataCell2.setCellValue(calendarioMedicion.getValorCalendarioMedicion().toString()); // Columna Valor Medición
                    Cell dataCell3 = dataRow.getCell(3);
                    dataCell3.setCellValue(calendarioMedicion.getMedicion().getUnidadMedidaMedicion()); // Columna Unidad de Medida
                    Cell dataCell4 = dataRow.getCell(4);
                    dataCell4.setCellValue(calendarioMedicion.getFechaCalendarioMedicion().toLocalDate().toString() + " / " + calendarioMedicion.getFechaCalendarioMedicion().toLocalTime().toString()); // Columna Fecha Registrada

                    fila++; // Incrementa el número de fila en cada iteración
                }


                // --- DATOS / SEGUNDA PARTE ---
                // Crear un mapa para agrupar los registros por nombre de medicion
                Map<String, List<CalendarioMedicion>> medicionesAgrupadas = new HashMap<>();
                int cantidadTotal = 0;
                // Agrupar los registros por nombre de medicamento
                for (CalendarioMedicion calendarioMedicion : listaTotalCalendarioMediciones) {
                    String nombreMedicion = calendarioMedicion.getMedicion().getNombreMedicion();
                    // Verificar si ya existe una lista para este medicamento en el mapa
                    List<CalendarioMedicion> calMediciones = medicionesAgrupadas.get(nombreMedicion);
                    if (calMediciones == null) {
                        calMediciones = new ArrayList<>();
                        medicionesAgrupadas.put(nombreMedicion, calMediciones);
                    }
                    calMediciones.add(calendarioMedicion);
                    cantidadTotal += calMediciones.size();
                }
                //cantTotal.setCellValue(cantidadTotal);
                // Calcular el porcentaje del total y escribir en la hoja de resumen
                int filaActual = 5; // Comienza desde la fila 5
                Log.d("MiApp", "Comienzo con Parte 2");
                for (Map.Entry<String, List<CalendarioMedicion>> entry : medicionesAgrupadas.entrySet()) {
                    String nombreMedicion = entry.getKey();
                    List<CalendarioMedicion> calMediciones = entry.getValue();
                    int totalCalendariosMediciones = listaTotalCalendarioMediciones.size();
                    int totalMedicionesPorNombre = 0;

                    for (CalendarioMedicion calendarioMedicion : calMediciones) {
                        if (calendarioMedicion.getMedicion().getNombreMedicion().equals(nombreMedicion)) {
                            totalMedicionesPorNombre++;
                        }
                    }
                    double porcentajeDelTotal = (double) totalMedicionesPorNombre / totalCalendariosMediciones * 100.0;
                    // Crear un formato decimal con dos decimales
                    DecimalFormat df = new DecimalFormat("#.##");
                    // Formatear el porcentaje con dos decimales y agregar "%"
                    String porcentajeFormateado = df.format(porcentajeDelTotal) + "%";

                    Row filaResumen = sheet.getRow(filaActual);
                    if (filaResumen == null || filaResumen.getCell(1) == null || filaResumen.getCell(1).getStringCellValue() == null || filaResumen.getCell(1).getStringCellValue().isEmpty()) {
                        int ultimaFila = filaActual - 1;
                        Log.d("MiApp", "    Entra en el if(dataRow.getCell(1)=null), con fila: " + filaActual + ", y ultimaFila: " + ultimaFila);
                        if (ultimaFila >= 4) { // No copiar datos ni estilo de los encabezados
                            // Crea la fila si no existe
                            if (filaResumen == null) {
                                filaResumen = sheet.createRow(filaActual);
                            }
                            for (int columna = 6; columna < 8; columna++) {
                                // Verifica si la celda actual es nula y, si lo es, créala antes de copiar el valor
                                Cell currentCell2 = filaResumen.getCell(columna);
                                if (currentCell2 == null) {
                                    currentCell2 = filaResumen.createCell(columna);
                                    Log.d("MiApp", "      Entra en el if(currentCell=null), fila: " + filaActual + ", col = " + columna);
                                }
                                // Copia los valores de la fila anterior a las celdas de la fila actual
                                Cell celdaAnterior2 = sheet.getRow(ultimaFila).getCell(columna);
                                // Verifica si la celda anterior tiene un estilo definido
                                if (celdaAnterior2 != null && celdaAnterior2.getCellStyle() != null) {
                                    // Obtiene el estilo de la celda anterior y lo aplica a la nueva
                                    CellStyle estiloCeldaAnterior2 = celdaAnterior2.getCellStyle();
                                    CellStyle estiloNuevaCelda2 = currentCell2.getCellStyle();
                                    estiloNuevaCelda2.cloneStyleFrom(estiloCeldaAnterior2);

                                    Log.d("MiApp", "      Entra en el if de Style, fila: " + filaActual + ", col = " + columna);
                                }
                                // Establece el valor en la nueva celda
                                currentCell2.setCellValue(celdaAnterior2 != null ? celdaAnterior2.getRichStringCellValue() : null);
                            }
                        }
                    }

                    // Se setean los datos nuevos dentro de la fila dataRow
                    Cell celdaNombreSintoma = filaResumen.getCell(6);
                    celdaNombreSintoma.setCellValue(nombreMedicion);
                    Cell celdaCantSintoma = filaResumen.getCell(7);
                    celdaCantSintoma.setCellValue(totalMedicionesPorNombre);
                    Cell celdaPorcentajeDelTotal = filaResumen.getCell(8);
                    celdaPorcentajeDelTotal.setCellValue(porcentajeFormateado.toString());

                    filaActual++;
                }

                // --- GUARDAR EL ARCHIVO EXCEL GENERADO ---
                // Ruta del almacenamiento interno en Android para guardar el archivo
                File dir = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
                String filePath = dir.getAbsolutePath() + "/MediCALReporte" + reporteAsociado.getTipoReporte().getNombreTipoReporte() + ".xlsx";
                // Crear el archivo Excel
                file = new File(filePath);
                try {
                    FileOutputStream fos = new FileOutputStream(file);
                    workbook.write(fos);
                    fos.close();
                    enviarReporte(destinatario);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace(); // Puedes imprimir la traza de la excepción para depurar
            }
        }
    }


    private void enviarReporte(String destinatario) {

        Log.d("MiApp", "Envío el Reporte");

        // Configura las propiedades del servidor de correo de Gmail
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        // Utiliza la contraseña de aplicación en lugar de la contraseña principal
        final String correo = "medicalutnfrm@gmail.com";
        final String contrasenaApp = "wfbm ehhb uusd mshj"; // Tu contraseña de aplicación

        // Configura la sesión de correo
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(correo, contrasenaApp);
            }
        });

        try {
            // Crea un mensaje de correo electrónico
            message = new MimeMessage(session);
            message.setFrom(new InternetAddress(correo));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            if (reporteAsociado.getTipoReporte().getNombreTipoReporte().equals("Reporte Medicamento (Uno)")){
                message.setSubject("MediCAL - Reporte Medicamento (" + reporteAsociado.getNombreMed() + ")");
            } else {
                message.setSubject("MediCAL - " + reporteAsociado.getTipoReporte().getNombreTipoReporte());
            }

            // Adjunta el archivo
            MimeBodyPart attachmentPart = new MimeBodyPart();
            if (file != null && file.exists()) {
                attachmentPart.attachFile(file);
            } else {
                Toast.makeText(this, "Error al enviar el archivo de correo (archivo nulo o no existe).", Toast.LENGTH_SHORT).show();
            }

            // Crea el cuerpo del mensaje
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(attachmentPart);

            // Configura el contenido del mensaje
            message.setContent(multipart);

            // Envía el mensaje utilizando AsyncTask en segundo plano
            new EnviarReporteAsyncTask(message).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        } catch (MessagingException | IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error al enviar el correo.", Toast.LENGTH_SHORT).show();
        }
    }


    private class EnviarReporteAsyncTask extends AsyncTask<Void, Void, Boolean> {
        Message message;

        public EnviarReporteAsyncTask(Message message) {
            this.message = message;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                Transport.send(message);
                return true; // Éxito
            } catch (MessagingException e) {
                e.printStackTrace();
                return false; // Error
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                Toast.makeText(CompartirReporteActivity.this, "Correo enviado con el archivo adjunto.", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
            } else {
                Toast.makeText(CompartirReporteActivity.this, "Error al enviar el correo.", Toast.LENGTH_SHORT).show();
            }
            finish(); // Cierra CompartirReporteActivity
        }
    }

    public boolean esCorreoValido(String correoDestinatario) {
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";   // Expresión regular para validar un correo electrónico

        Pattern pattern = Pattern.compile(regex);   // Compila la expresión regular en un patrón

        Matcher matcher = pattern.matcher(correoDestinatario);  // Crea un objeto Matcher

        return matcher.matches();    // Comprueba si la cadena cumple con el patrón
    }

    private void popupCorreoNoValido() {
        Log.d("MiApp","Se llamó a popupCorreoNoValido");
        View popupView = getLayoutInflater().inflate(R.layout.n85_popup_correo_invalido, null);

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

        TextView atras = popupView.findViewById(R.id.atras);

        atras.setOnClickListener(view ->{
            popupWindow.dismiss();
            dimView.setVisibility(View.GONE);
        });

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                dimView.setVisibility(View.GONE);
            }
        });
    }

    public interface OnDataLoadedListener {
        void onDataLoaded() throws IOException;
    }


}
