package com.example.medical;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import org.apache.poi.ss.usermodel.*;

import com.example.medical.model.Calendario;
import com.example.medical.model.CalendarioMedicion;
import com.example.medical.model.CalendarioSintoma;
import com.example.medical.model.Recordatorio;
import com.example.medical.model.RegistroRecordatorio;
import com.example.medical.model.Reporte;
import com.example.medical.model.TipoReporte;
import com.example.medical.model.Usuario;
import com.example.medical.retrofit.CalendarioApi;
import com.example.medical.retrofit.CalendarioMedicionApi;
import com.example.medical.retrofit.CalendarioSintomaApi;
import com.example.medical.retrofit.RecordatorioApi;
import com.example.medical.retrofit.RegistroRecordatorioApi;
import com.example.medical.retrofit.ReporteApi;
import com.example.medical.retrofit.RetrofitService;
import com.example.medical.retrofit.TipoReporteApi;
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

public class AgregarReporteActivity extends AppCompatActivity {

    private Object context;
    private RetrofitService retrofitService;
    private Usuario usuarioLogeado;
    private int nroCalendariosAsociados;
    private int nroRecordatoriosAsociados;
    private int operacionesPendientes = -1;
    private int nroReporteSeleccionado;

    private LocalDate fechaReporteDesdeLocalDate;
    private LocalDate fechaReporteHastaLocalDate;
    private String fechaReporteDesdeString = "Seleccione 'fecha desde'";
    private String fechaReporteHastaString = "Seleccione 'fecha hasta'";

    private TextView textoFiltroReporte;
    private ConstraintLayout MedicamentoEspecifico;
    private String opcionMenu = "Medicamentos (Todos)";
    private PopupMenu popupMenu;
    private TextView fechaDesde;
    private TextView fechaHasta;
    private List<RegistroRecordatorio> listaTotalRegistroRecordatoriosFiltroMed = new ArrayList<>();
    private List<RegistroRecordatorio> listaTotalRegistroRecordatorios = new ArrayList<>();
    private List<Calendario> listaTotalDeCalendarios = new ArrayList<>();
    private List<CalendarioSintoma> listaTotalCalendarioSintomas = new ArrayList<>();
    private List<CalendarioMedicion> listaTotalCalendarioMediciones = new ArrayList<>();
    private List<TipoReporte> ListaGlobalTiposDeReportes = new ArrayList<>();
    private String destinatario;
    private int codUsuarioLogeado;
    private int codCalendarioSeleccionado;
    private File file;
    private Message message;
    private String medicamento;
    private EditText textoNombreMedicamento;
    private boolean existenRegistros = false; // Variable global para verificar existencia de registrosRecordatorios
    private OnDataLoadedListener onDataLoadedListener;
    private OnDataLoadedListener onDataLoadedListener2;
    private LinearLayout progressBar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.n83_crear_enviar_informe);
        this.context = context;

        Intent intent1 = getIntent();
        nroReporteSeleccionado = intent1.getIntExtra("nroReporte", 0);
        codCalendarioSeleccionado = intent1.getIntExtra("calendarioSeleccionadoid", 0);
        codUsuarioLogeado = intent1.getIntExtra("codUsuario", 0);

        EditText emailDestinatario = findViewById(R.id.textEdit_email);
        textoFiltroReporte = findViewById(R.id.eleccion_tipoReporte);
        textoFiltroReporte.setText(opcionMenu);
        MedicamentoEspecifico = findViewById(R.id.opcionMedicamento);
        textoNombreMedicamento = findViewById(R.id.textEdit_medicamento);
        fechaDesde = findViewById(R.id.textEdit_fecha_desde);
        fechaHasta = findViewById(R.id.textEdit_fecha_hasta);
        Button botonEnviar = findViewById(R.id.button_enviar);
        ImageView botonVolver = findViewById(R.id.boton_volver);
        ImageView desplegable = findViewById(R.id.imagen_desplegable);
        progressBar = findViewById(R.id.progressBar);

        desplegable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarMenu(desplegable);
            }
        });

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

        onDataLoadedListener = new OnDataLoadedListener() {
            @Override
            public void onDataLoaded() {
                Log.d("MiApp", "Llamo al método obtenerTipoDeReporte si existen registrosRecordatorio: " + existenRegistros);
                if (existenRegistros) {
                    obtenerTipoDeReporte(opcionMenu, destinatario, onDataLoadedListener2);
                    Log.d("MiApp", "Entró en el if y la variable existenInformes es: " + existenRegistros);
                }
            }
        };

        onDataLoadedListener2 = new OnDataLoadedListener() {
            @Override
            public void onDataLoaded() throws IOException {
                Log.d("MiApp", "Llamo al método generarExcelSintomas si existen síntomas: " + listaTotalCalendarioSintomas + " ; o mediciones: " + listaTotalCalendarioMediciones);
                if (listaTotalCalendarioSintomas!=null && !listaTotalCalendarioSintomas.isEmpty()){
                    generarArchivoExcelSíntomas(destinatario);
                }
                if (listaTotalCalendarioMediciones!=null && !listaTotalCalendarioMediciones.isEmpty()){
                    generarArchivoExcelMediciones(destinatario);
                }
            }
        };

        botonEnviar.setOnClickListener(view -> {

            medicamento = textoNombreMedicamento.getText().toString();
            destinatario = emailDestinatario.getText().toString();
            if (fechaReporteDesdeString.equals("Seleccione 'fecha desde'")){
                Toast.makeText(AgregarReporteActivity.this, "Debe Seleccionar una 'fecha desde'", Toast.LENGTH_SHORT).show();
            } else if (fechaReporteHastaString.equals("Seleccione 'fecha hasta'")) {
                Toast.makeText(AgregarReporteActivity.this, "Debe Seleccionar una 'fecha hasta'", Toast.LENGTH_SHORT).show();
            } else if (!esCorreoValido(destinatario)) {
                Toast.makeText(this, "La dirección de correo no es válida.", Toast.LENGTH_SHORT).show();
            } else {
                Log.d("MiApp", "Se obtuvo un mail correcto de destinatario: " + destinatario);
                View dimView = findViewById(R.id.dim_view);
                dimView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                obtenerUsuarioLogeado(codUsuarioLogeado, onDataLoadedListener);
            }
        });

        botonVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    // Mostrar Menu
    public void mostrarMenu(View view) {
        popupMenu = new PopupMenu(this, view); // Asocia el menú con el ImageView
        getMenuInflater().inflate(R.menu.menu_desplegable_0, popupMenu.getMenu());

        // Configura un listener para los elementos del menú
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // Maneja las acciones de los elementos del menú aquí
                switch (item.getItemId()) {
                    case R.id.opcion1_menu0:
                        opcionMenu = "Medicamentos (Todos)";
                        MedicamentoEspecifico.setVisibility(View.GONE);
                        break;
                    case R.id.opcion2_menu0:
                        opcionMenu = "Medicamento (Uno)";
                        MedicamentoEspecifico.setVisibility(View.VISIBLE);
                        break;
                    case R.id.opcion3_menu0:
                        opcionMenu = "Síntomas";
                        MedicamentoEspecifico.setVisibility(View.GONE);
                        break;
                    case R.id.opcion4_menu0:
                        opcionMenu = "Mediciones";
                        MedicamentoEspecifico.setVisibility(View.GONE);
                        break;
                    default:
                        return false;
                }
                // Actualiza el título de la opción seleccionada en el menú
                item.setTitle(opcionMenu);
                textoFiltroReporte.setText(opcionMenu);
                Log.d("MiApp", "La opción seleccionada del menú es: " + opcionMenu);
                return true;
            }
        });
        // Muestra el PopupMenu
        popupMenu.show();
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

                fechaReporteDesdeLocalDate = (LocalDate.of(year, month, day));
                fechaReporteDesdeString = (LocalDate.of(year, month, day).toString());
                fechaDesde.setText(fechaReporteDesdeString);

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

                fechaReporteHastaLocalDate = (LocalDate.of(year, month, day));
                fechaReporteHastaString = (LocalDate.of(year, month, day).toString());
                fechaHasta.setText(fechaReporteHastaString);

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
                    nroCalendariosAsociados = calendariosAsociados.size();
                    if (calendariosAsociados != null) {
                        Log.d("MiApp", "Se obtuvieron los calendarios relacionados: " + calendariosAsociados);
                        for (Calendario calendario : calendariosAsociados) {
                            // Para cada calendario, obtener las clases "Recordatorio"
                            Log.d("MiApp", "codCalendario encontrado: " + calendario.getCodCalendario());
                            listaTotalDeCalendarios.add(calendario);
                            obtenerRecordatoriosPorCalendario(calendario, listener);
                        }
                        //obtenerTipoDeReporte(reporteAsociado, destinatario);
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
                    nroRecordatoriosAsociados = recordatoriosAsociados.size();
                    if (recordatoriosAsociados != null && !recordatoriosAsociados.isEmpty()) {
                        Log.d("MiApp", "Recordatorios Asociados Encontrados: ");
                        for (Recordatorio recordatorio : recordatoriosAsociados) {
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
        // Hacer la llamada a la API para obtener las clases "Recordatorio" asociadas a un calendario
        Call<List<RegistroRecordatorio>> registroRecordatoriosCall = registroRecordatorioApi.getByCodRecordatorio(recordatorioAsociado.getCodRecordatorio());

        registroRecordatoriosCall.enqueue(new Callback<List<RegistroRecordatorio>>() {
            @Override
            public void onResponse(Call<List<RegistroRecordatorio>> call, Response<List<RegistroRecordatorio>> response) {
                if (response.isSuccessful()) {
                    List<RegistroRecordatorio> registroRecordatoriosAsociados = response.body();
                    if (registroRecordatoriosAsociados != null && !registroRecordatoriosAsociados.isEmpty()) {
                        Log.d("MiApp", "RegistrosRecordatorios Asociados Encontrados: ");
                        existenRegistros = true;
                        for (RegistroRecordatorio registroRecordatorio : registroRecordatoriosAsociados) {
                            if (registroRecordatorio.getFechaTomaEsperada().toLocalDate().isAfter(fechaReporteDesdeLocalDate.minusDays(1)) && registroRecordatorio.getFechaTomaEsperada().toLocalDate().isBefore(fechaReporteHastaLocalDate.plusDays(1))) {
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
    private void obtenerTipoDeReporte (String opcionTipoReporte, String destinatario, OnDataLoadedListener listener2){
        try {
            if (opcionTipoReporte.equals("Medicamentos (Todos)")){
                generarArchivoExcelMedicamentosTodos(destinatario);
            } else if (opcionTipoReporte.equals("Medicamento (Uno)")){
                Log.d("MiApp","Se intenta filtrar por medicamento: "+medicamento);
                for (RegistroRecordatorio registro : listaTotalRegistroRecordatorios){
                    if (registro.getRecordatorio().getMedicamento().getNombreMedicamento().equals(medicamento)){
                        listaTotalRegistroRecordatoriosFiltroMed.add(registro);
                    }
                }
                if (listaTotalRegistroRecordatoriosFiltroMed!=null && !listaTotalRegistroRecordatoriosFiltroMed.isEmpty()){
                    Log.d("MiApp", "Lista de Registros de un Medicamento: "+listaTotalRegistroRecordatoriosFiltroMed);
                    Log.d("MiApp", "Se cargarán " + listaTotalRegistroRecordatoriosFiltroMed.size() + " cant. de registros con el filtro: " + medicamento);
                    generarArchivoExcelMedicamentoUno(destinatario);
                } else {
                    Log.d("MiApp", "No existen registros con el nombreMedicamento: "+medicamento);
                    View dimView = findViewById(R.id.dim_view);
                    dimView.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(this, "No existen registros con ese filtro de nombre de medicamento.", Toast.LENGTH_SHORT).show();
                }
            } else if (opcionTipoReporte.equals("Síntomas")){
                obtenerCalendarioSintomas(listener2);
                //generarArchivoExcelSíntomas(destinatario);
            } else if (opcionTipoReporte.equals("Mediciones")){
                obtenerCalendarioMediciones(listener2);
                //generarArchivoExcelMediciones(destinatario);
            }
        } catch (FileNotFoundException e) {
            // Manejo de la excepción FileNotFoundException
            e.printStackTrace(); // Imprime la traza de la excepción para depuración
            Toast.makeText(this, "No se encontró el archivo.", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Log.d("MiApp","Entra en IOException de obtenerTipoDeReporte");
            throw new RuntimeException(e);
        }
    }


    private void generarArchivoExcelMedicamentosTodos(String destinatario) throws IOException {
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
                // Crear una celda que abarque las 6 primeras columnas (índices de 1 a 6, 0 es un margen)
                Cell Titulocell = headerRow0.getCell(1);
                Titulocell.setCellValue("Reporte " + opcionMenu); // Título que se verá en las celdas unificadas

                // --- DATOS DEL REPORTE / PRIMER HOJA ---
                // Crear una fila para los datos del reporte
                Row headerRow1 = sheet.getRow(1);
                // Crear celdas datos del reporte
                Cell fechaGenerada = headerRow1.getCell(5);
                fechaGenerada.setCellValue(LocalDate.now().toString());
                Row headerRow2 = sheet.getRow(2);
                Cell fechaDesde = headerRow2.getCell(2);
                fechaDesde.setCellValue(fechaReporteDesdeString);
                Cell fechaHasta = headerRow2.getCell(5);
                fechaHasta.setCellValue(fechaReporteHastaString);


                // --- FILA ENCABEZADOS DE COLUMNAS / PRIMER HOJA ---
                // Crear una fila para los encabezados de las columnas
                Row headerRow = sheet.getRow(3);
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
                int fila = 4;   // Comienza desde la fila 2; fila 0 es el título, fila 1 los datos del reporte, fila 2 los encabezados
                //int[] ultimaFilaPorColumna = new int[8]; // 8 es el número de columnas procesadas (0 y 7 son los márgenes)
                for (RegistroRecordatorio registroRecordatorio : listaTotalRegistroRecordatorios) {
                    Log.d("MiApp", "Creando datos de registro, fila: " + fila);
                    Row dataRow = sheet.getRow(fila);    // Se crea CADA FILA de datos, va incrementando

                    // Si la celda en la columna 1 es nula o está vacía, crea la fila y las celdas antes de establecer el valor
                    if (dataRow == null || dataRow.getCell(1) == null || dataRow.getCell(1).getStringCellValue() == null || dataRow.getCell(1).getStringCellValue().isEmpty()) {
                        int ultimaFila = fila - 1;
                        Log.d("MiApp", "    Entra en el if(dataRow.getCell(1)=null), con fila: " + fila + ", y ultimaFila: " + ultimaFila);
                        if (ultimaFila >= 4) { // No copiar datos ni estilo de los encabezados
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
                Titulocell2.setCellValue("Estadísticas del Reporte " + opcionMenu); // Título que se verá en las celdas unificadas

                // --- DATOS DEL REPORTE / SEGUNDA HOJA ---
                // Crear una fila para los datos del reporte
                Row headerRow2_1 = sheet2.getRow(1);
                // Crear celdas datos del reporte
                Cell fechaGenerada2 = headerRow2_1.getCell(5);
                fechaGenerada2.setCellValue(LocalDate.now().toString());

                Row headerRow2_2 = sheet2.getRow(2);
                Cell fechaDesde2 = headerRow2_2.getCell(3);
                fechaDesde2.setCellValue(fechaReporteDesdeString);
                Cell fechaHasta2 = headerRow2_2.getCell(6);
                fechaHasta2.setCellValue(fechaReporteHastaString);

                // --- FILA ENCABEZADOS DE COLUMNAS / SEGUNDA HOJA ---
                // Crear una fila para los encabezados de las columnas
                Row headerRow2_3 = sheet2.getRow(3);
                // Crear celdas de encabezados de columnas
                Cell headerCell2_1 = headerRow2_3.getCell(1);
                headerCell2_1.setCellValue("Nombre del Medicamento");
                Cell headerCell2_2 = headerRow2_3.getCell(2);
                headerCell2_2.setCellValue("Procentaje de Cumplimiento (%)");
                Cell headerCell2_3 = headerRow2_3.getCell(3);
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
                int filaActual = 4; // Comienza desde la fila 2; fila 0 es el título, fila 1 los datos del reporte, fila 2 los encabezados
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

                    // ---
                    Row filaResumen = sheet2.getRow(filaActual);
                    if (filaResumen == null || filaResumen.getCell(1) == null || filaResumen.getCell(1).getStringCellValue() == null || filaResumen.getCell(1).getStringCellValue().isEmpty()) {
                        int ultimaFila = filaActual - 1;
                        Log.d("MiApp", "    Entra en el if(dataRow.getCell(1)=null), con fila: " + filaActual + ", y ultimaFila: " + ultimaFila);
                        if (ultimaFila >= 4) { // No copiar datos ni estilo de los encabezados
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


                // --- GRÁFICO / SEGUNDA HOJA ---
                // Crear un objeto Drawing para la hoja de trabajo
                /*
                CreationHelper helper = sheet2.getWorkbook().getCreationHelper();
                Drawing<?> drawing = sheet2.createDrawingPatriarch();
                // Crear una ancla para el gráfico
                ClientAnchor anchor = helper.createClientAnchor();
                anchor.setCol1(3); // Columna de inicio
                anchor.setRow1(3); // Fila de inicio
                anchor.setCol2(8); // Columna de fin
                anchor.setRow2(15); // Fila de fin
                // Crear el gráfico de barras en la hoja de trabajo
                Chart chart = drawing.createChart(anchor);
                ChartLegend legend = chart.getOrAddLegend();
                legend.setPosition(LegendPosition.TOP_RIGHT);
                // Crear categorías (nombres de medicamentos) y valores (porcentajes) para el gráfico
                ChartDataSource<String> nombresCategoria = DataSources.fromStringCellRange(sheet2, new CellRangeAddress(3, filaActual - 1, 1, 1));
                ChartDataSource<Number> valores = DataSources.fromNumericCellRange(sheet2, new CellRangeAddress(3, filaActual - 1, 2, 2));
                // Agregar datos al gráfico de barras
                chart.plot(ChartTypes.BAR, null, nombresCategoria, valores);


                // Crear un eje de categoría (eje Y) y un eje de valores (eje X)
                CategoryAxis categoriaAxis = chart.createCategoryAxis(AxisPosition.LEFT);
                ValueAxis valorAxis = chart.createValueAxis(AxisPosition.BOTTOM);
                valorAxis.setCrosses(AxisCrosses.AUTO_ZERO);
                // Agregar datos al gráfico de barras
                ChartData data = chart.createData(ChartTypes.BAR, categoriaAxis, valorAxis);
                data.setVaryColors(true); // Alternar colores de las barras
                ChartData.Series series = data.addSeries(nombresCategoria, valores);
                series.setTitle("Porcentaje de Cumplimiento", null);
                // Dibujar el gráfico en la hoja de trabajo
                chart.plot(data);
                // Ajustar el tamaño del gráfico
                // CTBarChart barChart = chart.getCTChart().getPlotArea().getBarChartArray(0);
                 */


                // --- GUARDAR EL ARCHIVO EXCEL GENERADO ---
                // Ruta del almacenamiento interno en Android para guardar el archivo
                File dir = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
                String filePath = dir.getAbsolutePath() + "/MediCALReporte" + opcionMenu + ".xlsx";
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

    private void generarArchivoExcelMedicamentoUno(String destinatario) throws IOException {
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
                Titulocell.setCellValue("Reporte Medicamento (" + medicamento + ")"); // Título que se verá en las celdas unificadas

                // --- DATOS DEL REPORTE / PRIMER HOJA ---
                // Crear una fila para los datos del reporte
                Row headerRow1 = sheet.getRow(1);
                // Crear celdas datos del reporte
                Cell fechaGenerada = headerRow1.getCell(5);
                fechaGenerada.setCellValue(LocalDate.now().toString());

                Row headerRow2 = sheet.getRow(2);
                Cell fechaDesde = headerRow2.getCell(2);
                fechaDesde.setCellValue(fechaReporteDesdeString);
                Cell fechaHasta = headerRow2.getCell(4);
                fechaHasta.setCellValue(fechaReporteHastaString);
                Cell filtro = headerRow2.getCell(6);
                filtro.setCellValue(medicamento);


                // --- FILA ENCABEZADOS DE COLUMNAS / PRIMER HOJA ---
                // Crear una fila para los encabezados de las columnas
                Row headerRow = sheet.getRow(3);
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
                int fila = 4;   // Comienza desde la fila 2; fila 0 es el título, fila 1 los datos del reporte, fila 2 los encabezados
                //int[] ultimaFilaPorColumna = new int[8]; // 8 es el número de columnas procesadas (0 y 7 son los márgenes)
                for (RegistroRecordatorio registroRecordatorio : listaTotalRegistroRecordatoriosFiltroMed) {
                    Log.d("MiApp", "Creando datos de registro, fila: " + fila);
                    Row dataRow = sheet.getRow(fila);    // Se crea CADA FILA de datos, va incrementando

                    // Si la celda en la columna 1 es nula o está vacía, crea la fila y las celdas antes de establecer el valor
                    if (dataRow == null || dataRow.getCell(1) == null || dataRow.getCell(1).getStringCellValue() == null || dataRow.getCell(1).getStringCellValue().isEmpty()) {
                        int ultimaFila = fila - 1;
                        Log.d("MiApp", "    Entra en el if(dataRow.getCell(1)=null), con fila: " + fila + ", y ultimaFila: " + ultimaFila);
                        if (ultimaFila >= 4) { // No copiar datos ni estilo de los encabezados
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
                Titulocell2.setCellValue("Estadísticas del Reporte Medicamento (" + medicamento + ")"); // Título que se verá en las celdas unificadas

                // --- DATOS DEL REPORTE / SEGUNDA HOJA ---
                // Crear una fila para los datos del reporte
                Row headerRow2_1 = sheet2.getRow(1);
                // Crear celdas datos del reporte
                Cell fechaGenerada2 = headerRow2_1.getCell(5);
                fechaGenerada2.setCellValue(LocalDate.now().toString());

                Row headerRow2_2 = sheet2.getRow(2);
                Cell fechaDesde2 = headerRow2_2.getCell(2);
                fechaDesde2.setCellValue(fechaReporteDesdeString);
                Cell fechaHasta2 = headerRow2_2.getCell(5);
                fechaHasta2.setCellValue(fechaReporteHastaString);
                Cell filtro2 = headerRow2_2.getCell(8);
                filtro2.setCellValue(medicamento);

                // --- FILA ENCABEZADOS DE COLUMNAS / SEGUNDA HOJA ---
                // Crear una fila para los encabezados de las columnas
                Row headerRow2_3 = sheet2.getRow(3);
                // Crear celdas de encabezados de columnas
                Cell headerCell2_1 = headerRow2_3.getCell(1);
                headerCell2_1.setCellValue("Nombre del Medicamento");
                Cell headerCell2_2 = headerRow2_3.getCell(2);
                headerCell2_2.setCellValue("Procentaje de Cumplimiento (%)");
                Cell headerCell2_3 = headerRow2_3.getCell(3);
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
                int filaActual = 4; // Comienza desde la fila 2; fila 0 es el título, fila 1 los datos del reporte, fila 2 los encabezados
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

                    // ---
                    Row filaResumen = sheet2.getRow(filaActual);
                    if (filaResumen == null || filaResumen.getCell(1) == null || filaResumen.getCell(1).getStringCellValue() == null || filaResumen.getCell(1).getStringCellValue().isEmpty()) {
                        int ultimaFila = filaActual - 1;
                        Log.d("MiApp", "    Entra en el if(dataRow.getCell(1)=null), con fila: " + filaActual + ", y ultimaFila: " + ultimaFila);
                        if (ultimaFila >= 4) { // No copiar datos ni estilo de los encabezados
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


                // --- GRÁFICO / SEGUNDA HOJA ---
                // Crear un objeto Drawing para la hoja de trabajo
                /*
                CreationHelper helper = sheet2.getWorkbook().getCreationHelper();
                Drawing<?> drawing = sheet2.createDrawingPatriarch();
                // Crear una ancla para el gráfico
                ClientAnchor anchor = helper.createClientAnchor();
                anchor.setCol1(3); // Columna de inicio
                anchor.setRow1(3); // Fila de inicio
                anchor.setCol2(8); // Columna de fin
                anchor.setRow2(15); // Fila de fin
                // Crear el gráfico de barras en la hoja de trabajo
                Chart chart = drawing.createChart(anchor);
                ChartLegend legend = chart.getOrAddLegend();
                legend.setPosition(LegendPosition.TOP_RIGHT);
                // Crear categorías (nombres de medicamentos) y valores (porcentajes) para el gráfico
                ChartDataSource<String> nombresCategoria = DataSources.fromStringCellRange(sheet2, new CellRangeAddress(3, filaActual - 1, 1, 1));
                ChartDataSource<Number> valores = DataSources.fromNumericCellRange(sheet2, new CellRangeAddress(3, filaActual - 1, 2, 2));
                // Agregar datos al gráfico de barras
                chart.plot(ChartTypes.BAR, null, nombresCategoria, valores);


                // Crear un eje de categoría (eje Y) y un eje de valores (eje X)
                CategoryAxis categoriaAxis = chart.createCategoryAxis(AxisPosition.LEFT);
                ValueAxis valorAxis = chart.createValueAxis(AxisPosition.BOTTOM);
                valorAxis.setCrosses(AxisCrosses.AUTO_ZERO);
                // Agregar datos al gráfico de barras
                ChartData data = chart.createData(ChartTypes.BAR, categoriaAxis, valorAxis);
                data.setVaryColors(true); // Alternar colores de las barras
                ChartData.Series series = data.addSeries(nombresCategoria, valores);
                series.setTitle("Porcentaje de Cumplimiento", null);
                // Dibujar el gráfico en la hoja de trabajo
                chart.plot(data);
                // Ajustar el tamaño del gráfico
                // CTBarChart barChart = chart.getCTChart().getPlotArea().getBarChartArray(0);
                 */


                // --- GUARDAR EL ARCHIVO EXCEL GENERADO ---
                // Ruta del almacenamiento interno en Android para guardar el archivo
                File dir = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
                String filePath = dir.getAbsolutePath() + "/MediCALReporteMedicamento(" +medicamento+ ").xlsx";
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
                                if (calendarioSintoma.getFechaCalendarioSintoma().toLocalDate().isAfter(fechaReporteDesdeLocalDate.minusDays(1))
                                        && calendarioSintoma.getFechaCalendarioSintoma().toLocalDate().isBefore(fechaReporteHastaLocalDate.plusDays(1))){
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
                            Toast.makeText(AgregarReporteActivity.this, "No se encontraron síntomas entre estas fechas.", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                            View dimView = findViewById(R.id.dim_view);
                            dimView.setVisibility(View.GONE);
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
    private void generarArchivoExcelSíntomas(String destinatario) throws IOException {
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
                Titulocell.setCellValue("Reporte " + opcionMenu); // Título que se verá en las celdas unificadas

                // --- DATOS DEL REPORTE / PRIMER HOJA ---
                // Crear una fila para los datos del reporte
                Row headerRow1 = sheet.getRow(1);
                // Crear celdas datos del reporte
                Cell fechaGenerada = headerRow1.getCell(6);
                fechaGenerada.setCellValue(LocalDate.now().toString());

                Row headerRow2 = sheet.getRow(2);
                Cell fechaDesde = headerRow2.getCell(2);
                fechaDesde.setCellValue(fechaReporteDesdeString);
                Cell fechaHasta = headerRow2.getCell(5);
                fechaHasta.setCellValue(fechaReporteHastaString);
                Cell cantTotal = headerRow2.getCell(11);
                cantTotal.setCellValue(listaTotalCalendarioSintomas.size());

                // --- DATOS / PRIMER PARTE ---
                int fila = 4;   // Comienza desde la fila 2; fila 0 es el título, fila 1 los datos del reporte, fila 2 los encabezados
                for (CalendarioSintoma calendarioSintoma : listaTotalCalendarioSintomas) {
                    Log.d("MiApp", "Creando datos de registro, fila: " + fila);
                    Row dataRow = sheet.getRow(fila);    // Se crea CADA FILA de datos, va incrementando

                    // Si la celda en la columna 1 es nula o está vacía, crea la fila y las celdas antes de establecer el valor
                    if (dataRow == null || dataRow.getCell(1) == null || dataRow.getCell(1).getStringCellValue() == null || dataRow.getCell(1).getStringCellValue().isEmpty()) {
                        int ultimaFila = fila - 1;
                        Log.d("MiApp", "    Entra en el if(dataRow.getCell(1)=null), con fila: " + fila + ", y ultimaFila: " + ultimaFila);
                        if (ultimaFila >= 4) { // No copiar datos ni estilo de los encabezados
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
                int filaActual = 4; // Comienza desde la fila 2; fila 0 es el título, fila 1 los datos del reporte, fila 2 los encabezados
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
                        if (ultimaFila >= 4) { // No copiar datos ni estilo de los encabezados
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


                // --- GRÁFICO / SEGUNDA HOJA ---
                // Crear un objeto Drawing para la hoja de trabajo
                /*
                CreationHelper helper = sheet2.getWorkbook().getCreationHelper();
                Drawing<?> drawing = sheet2.createDrawingPatriarch();
                // Crear una ancla para el gráfico
                ClientAnchor anchor = helper.createClientAnchor();
                anchor.setCol1(3); // Columna de inicio
                anchor.setRow1(3); // Fila de inicio
                anchor.setCol2(8); // Columna de fin
                anchor.setRow2(15); // Fila de fin
                // Crear el gráfico de barras en la hoja de trabajo
                Chart chart = drawing.createChart(anchor);
                ChartLegend legend = chart.getOrAddLegend();
                legend.setPosition(LegendPosition.TOP_RIGHT);
                // Crear categorías (nombres de medicamentos) y valores (porcentajes) para el gráfico
                ChartDataSource<String> nombresCategoria = DataSources.fromStringCellRange(sheet2, new CellRangeAddress(3, filaActual - 1, 1, 1));
                ChartDataSource<Number> valores = DataSources.fromNumericCellRange(sheet2, new CellRangeAddress(3, filaActual - 1, 2, 2));
                // Agregar datos al gráfico de barras
                chart.plot(ChartTypes.BAR, null, nombresCategoria, valores);


                // Crear un eje de categoría (eje Y) y un eje de valores (eje X)
                CategoryAxis categoriaAxis = chart.createCategoryAxis(AxisPosition.LEFT);
                ValueAxis valorAxis = chart.createValueAxis(AxisPosition.BOTTOM);
                valorAxis.setCrosses(AxisCrosses.AUTO_ZERO);
                // Agregar datos al gráfico de barras
                ChartData data = chart.createData(ChartTypes.BAR, categoriaAxis, valorAxis);
                data.setVaryColors(true); // Alternar colores de las barras
                ChartData.Series series = data.addSeries(nombresCategoria, valores);
                series.setTitle("Porcentaje de Cumplimiento", null);
                // Dibujar el gráfico en la hoja de trabajo
                chart.plot(data);
                // Ajustar el tamaño del gráfico
                // CTBarChart barChart = chart.getCTChart().getPlotArea().getBarChartArray(0);
                 */


                // --- GUARDAR EL ARCHIVO EXCEL GENERADO ---
                // Ruta del almacenamiento interno en Android para guardar el archivo
                File dir = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
                String filePath = dir.getAbsolutePath() + "/MediCALReporte" + opcionMenu + ".xlsx";
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
                                if (calendarioMedicion.getFechaCalendarioMedicion().toLocalDate().isAfter(fechaReporteDesdeLocalDate.minusDays(1))
                                        && calendarioMedicion.getFechaCalendarioMedicion().toLocalDate().isBefore(fechaReporteHastaLocalDate.plusDays(1))) {
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
                            Toast.makeText(AgregarReporteActivity.this, "No se encontraron mediciones entre estas fechas.", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                            View dimView = findViewById(R.id.dim_view);
                            dimView.setVisibility(View.GONE);
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
    private void generarArchivoExcelMediciones(String destinatario) throws IOException {
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
                Titulocell.setCellValue("Reporte " + opcionMenu); // Título que se verá en las celdas unificadas

                // --- DATOS DEL REPORTE / PRIMER HOJA ---
                // Crear una fila para los datos del reporte
                Row headerRow1 = sheet.getRow(1);
                // Crear celdas datos del reporte
                Cell fechaGenerada = headerRow1.getCell(7);
                fechaGenerada.setCellValue(LocalDate.now().toString());

                Row headerRow2 = sheet.getRow(2);
                Cell fechaDesde = headerRow2.getCell(2);
                fechaDesde.setCellValue(fechaReporteDesdeString);
                Cell fechaHasta = headerRow2.getCell(4);
                fechaHasta.setCellValue(fechaReporteHastaString);
                Cell cantTotal = headerRow2.getCell(12);
                cantTotal.setCellValue(listaTotalCalendarioMediciones.size());

                // --- DATOS / PRIMER PARTE ---
                int fila = 4;   // Comienza desde la fila 2; fila 0 es el título, fila 1 los datos del reporte, fila 2 los encabezados
                for (CalendarioMedicion calendarioMedicion : listaTotalCalendarioMediciones) {
                    Log.d("MiApp", "Creando datos de registro, fila: " + fila);
                    Row dataRow = sheet.getRow(fila);    // Se crea CADA FILA de datos, va incrementando

                    // Si la celda en la columna 1 es nula o está vacía, crea la fila y las celdas antes de establecer el valor
                    if (dataRow == null || dataRow.getCell(1) == null || dataRow.getCell(1).getStringCellValue() == null || dataRow.getCell(1).getStringCellValue().isEmpty()) {
                        int ultimaFila = fila - 1;
                        Log.d("MiApp", "    Entra en el if(dataRow.getCell(1)=null), con fila: " + fila + ", y ultimaFila: " + ultimaFila);
                        if (ultimaFila >= 4) { // No copiar datos ni estilo de los encabezados
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
                int filaActual = 4; // Comienza desde la fila 2; fila 0 es el título, fila 1 los datos del reporte, fila 2 los encabezados
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

                    // ---
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


                // --- GRÁFICO / SEGUNDA HOJA ---
                // Crear un objeto Drawing para la hoja de trabajo
                /*
                CreationHelper helper = sheet2.getWorkbook().getCreationHelper();
                Drawing<?> drawing = sheet2.createDrawingPatriarch();
                // Crear una ancla para el gráfico
                ClientAnchor anchor = helper.createClientAnchor();
                anchor.setCol1(3); // Columna de inicio
                anchor.setRow1(3); // Fila de inicio
                anchor.setCol2(8); // Columna de fin
                anchor.setRow2(15); // Fila de fin
                // Crear el gráfico de barras en la hoja de trabajo
                Chart chart = drawing.createChart(anchor);
                ChartLegend legend = chart.getOrAddLegend();
                legend.setPosition(LegendPosition.TOP_RIGHT);
                // Crear categorías (nombres de medicamentos) y valores (porcentajes) para el gráfico
                ChartDataSource<String> nombresCategoria = DataSources.fromStringCellRange(sheet2, new CellRangeAddress(3, filaActual - 1, 1, 1));
                ChartDataSource<Number> valores = DataSources.fromNumericCellRange(sheet2, new CellRangeAddress(3, filaActual - 1, 2, 2));
                // Agregar datos al gráfico de barras
                chart.plot(ChartTypes.BAR, null, nombresCategoria, valores);


                // Crear un eje de categoría (eje Y) y un eje de valores (eje X)
                CategoryAxis categoriaAxis = chart.createCategoryAxis(AxisPosition.LEFT);
                ValueAxis valorAxis = chart.createValueAxis(AxisPosition.BOTTOM);
                valorAxis.setCrosses(AxisCrosses.AUTO_ZERO);
                // Agregar datos al gráfico de barras
                ChartData data = chart.createData(ChartTypes.BAR, categoriaAxis, valorAxis);
                data.setVaryColors(true); // Alternar colores de las barras
                ChartData.Series series = data.addSeries(nombresCategoria, valores);
                series.setTitle("Porcentaje de Cumplimiento", null);
                // Dibujar el gráfico en la hoja de trabajo
                chart.plot(data);
                // Ajustar el tamaño del gráfico
                // CTBarChart barChart = chart.getCTChart().getPlotArea().getBarChartArray(0);
                 */


                // --- GUARDAR EL ARCHIVO EXCEL GENERADO ---
                // Ruta del almacenamiento interno en Android para guardar el archivo
                File dir = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
                String filePath = dir.getAbsolutePath() + "/MediCALReporte" + opcionMenu + ".xlsx";
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
            if (opcionMenu.equals("Medicamento (Uno)")){
                message.setSubject("MediCAL - Reporte Medicamento (" + medicamento + ")");
            } else {
                message.setSubject("MediCAL - Reporte " + opcionMenu);
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

            // Crea y ejecuta la tarea asíncrona
            EnviarReporteAsyncTask enviarReporteAsyncTask = new EnviarReporteAsyncTask(message);
            enviarReporteAsyncTask.execute();       // UTILIZAR PANTALLA DE CARGA, PORQUE TARDA

            // Espera a que la tarea se complete y obtén el resultado
            try {
                boolean envioExitoso = enviarReporteAsyncTask.get(); // Obtiene el resultado de doInBackground
                if (envioExitoso && operacionesPendientes==0) {
                    obtenerLosTiposDeReportesParametros();
                } else {
                    Toast.makeText(AgregarReporteActivity.this, "Error al enviar el correo.", Toast.LENGTH_SHORT).show();
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
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
                Toast.makeText(AgregarReporteActivity.this, "Correo enviado con el archivo adjunto.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(AgregarReporteActivity.this, "Error al enviar el correo.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void obtenerLosTiposDeReportesParametros (){

        RetrofitService retrofitService = new RetrofitService();
        TipoReporteApi tipoReporteApi = retrofitService.getRetrofit().create(TipoReporteApi.class);
        // Hacer la llamada a la API para obtener el usuario seleccionado
        Call<List<TipoReporte>> tipoReporteCall = tipoReporteApi.getAllReportes();

        tipoReporteCall.enqueue(new Callback<List<TipoReporte>>() {
            @Override
            public void onResponse(Call<List<TipoReporte>> call, Response<List<TipoReporte>> response) {
                if (response.isSuccessful()) {
                    List<TipoReporte> listaTipoReporte = response.body();
                    Log.d("MiApp", "Obtengo los Tipos de Reporte: " + listaTipoReporte);
                    if (listaTipoReporte != null) {
                        ListaGlobalTiposDeReportes = listaTipoReporte;
                        crearInstanciaReporte();
                    } else {
                        Log.d("MiApp", "No se encontraron instancias de tipoReporte ");
                    }
                } else {
                    Log.d("MiApp", "Error en la solicitud: " + response.message());
                }
            }
            @Override
            public void onFailure(Call<List<TipoReporte>> call, Throwable t) {
                Log.e("MiApp", "Error en la solicitud: " + t.getMessage());
            }
        });

    }

    private void crearInstanciaReporte(){
        Reporte nuevoReporte = new Reporte();
        nuevoReporte.setFechaGenerada(LocalDate.now());
        nuevoReporte.setFechaDesde(fechaReporteDesdeLocalDate);
        nuevoReporte.setFechaHasta(fechaReporteHastaLocalDate);
        nuevoReporte.setUsuario(usuarioLogeado);

        if (opcionMenu.equals("Medicamento (Uno)")){
            nuevoReporte.setNombreMed(medicamento);
        }

        for (TipoReporte tipoReporte : ListaGlobalTiposDeReportes) {
            if (("Reporte "+ opcionMenu).equals(tipoReporte.getNombreTipoReporte())){
                nuevoReporte.setTipoReporte(tipoReporte);
                Log.d("MiApp","Asigno el Tipo Reporte: "+tipoReporte);
            }
        }

        Log.d("MiApp", "Creo instancia de reporte: " + nuevoReporte);

        RetrofitService retrofitService2 = new RetrofitService();
        ReporteApi reporteApi = retrofitService2.getRetrofit().create(ReporteApi.class);
        // Hacer la llamada a la API para obtener el usuario seleccionado
        Call<Reporte> reporteCall = reporteApi.save(nuevoReporte);

        reporteCall.enqueue(new Callback<Reporte>() {
            @Override
            public void onResponse(Call<Reporte> call, Response<Reporte> response) {
                if (response.isSuccessful()) {
                    Log.d("MiApp", "Reporte creado con éxito: " + nuevoReporte);
                    progressBar.setVisibility(View.GONE);
                    popupReporteCreadoYEnviado();
                } else {
                    Log.d("MiApp", "Error en la solicitud: " + response.message());
                }
            }
            @Override
            public void onFailure(Call<Reporte> call, Throwable t) {
                Log.e("MiApp", "Error en la solicitud: " + t.getMessage());
            }
        });
    }

    private void popupReporteCreadoYEnviado() {
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
        texto.setText("Reporte Creado y Enviado a " + destinatario);

        cerrar.setOnClickListener(view ->{
            popupWindow.dismiss();
            dimView.setVisibility(View.GONE);
            Intent intent = new Intent(AgregarReporteActivity.this, ReportesActivity.class);
            intent.putExtra("codUsuario", codUsuarioLogeado);
            intent.putExtra("calendarioSeleccionadoid", codCalendarioSeleccionado);
            startActivity(intent);
        });

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                dimView.setVisibility(View.GONE);
                Intent intent = new Intent(AgregarReporteActivity.this, ReportesActivity.class);
                intent.putExtra("codUsuario", codUsuarioLogeado);
                intent.putExtra("calendarioSeleccionadoid", codCalendarioSeleccionado);
                startActivity(intent);
            }
        });
    }

    public interface OnDataLoadedListener {
        void onDataLoaded() throws IOException;
    }


}
