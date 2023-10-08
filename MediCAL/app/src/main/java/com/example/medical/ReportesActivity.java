package com.example.medical;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import android.view.MenuItem;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import androidx.appcompat.app.AppCompatActivity;
//import org.apache.poi.ss.usermodel.*;
//import org.apache.poi.ss.util.CellRangeAddress;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.example.medical.adapter.ReporteAdapter;
import com.example.medical.model.Inventario;
import com.example.medical.model.Recordatorio;
import com.example.medical.model.Reporte;
import com.example.medical.model.Usuario;
import com.example.medical.retrofit.ReporteApi;
import com.example.medical.retrofit.RetrofitService;
import com.example.medical.retrofit.UsuarioApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportesActivity extends AppCompatActivity implements ReporteAdapter.OnEliminarReporteListener {

    private RetrofitService retrofitService;
    private PopupWindow popupWindow;

    private ImageView botonVolver;
    private ImageView botonFiltros;
    private Button agregarInforme;
    private RecyclerView recyclerView;
    private int codUsuarioLogeado;
    private int codCalendarioSeleccionado;
    private View dimView;

    private boolean existenInformes = false; // Variable global para verificar existencia de informes
    private List<Reporte> listaTotalReportes = new ArrayList<>(); // Lista global para almacenar todos los informes de un usuario
    private List<Reporte> listaTotalReportesOriginal = new ArrayList<>();
    private ReporteAdapter reporteAdapter;

    private String opcionMenu1 = "Todos los Reportes";
    private String opcionMenu2 = "Todo";
    private TextView textoFiltroReporte;
    private TextView textoFiltroFecha;
    private ConstraintLayout MedicamentoEspecifico;
    private PopupMenu popupMenu1;
    private PopupMenu popupMenu2;

    private Object context;
    private OnDataLoadedListener onDataLoadedListener;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.n81_informes_sin_cargar);   // Establece la pantalla 81 como predeterminada en caso que no hayan informes
        this.context = context;

        agregarInforme = findViewById(R.id.button_agrega_informe);
        botonVolver = findViewById(R.id.boton_volver);
        botonFiltros = findViewById(R.id.imagen_menu);

        Intent intent1 = getIntent();
        codUsuarioLogeado = intent1.getIntExtra("codUsuario", 0);
        codCalendarioSeleccionado = intent1.getIntExtra("calendarioSeleccionadoid", 0);

        agregarInforme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReportesActivity.this, GenerarReporteActivity.class);
                intent.putExtra("codUsuario", codUsuarioLogeado);
                intent.putExtra("calendarioSeleccionadoid", codCalendarioSeleccionado);
                startActivity(intent);
            }
        });

        onDataLoadedListener = new OnDataLoadedListener() {
            @Override
            public void onDataLoaded() {
                Log.d("MiApp", "Llamo al método loadInformes si existen informes: " + existenInformes);
                if (existenInformes) {
                    loadInformes();
                    Log.d("MiApp", "Entró en el if y la variable existenInformes es: " + existenInformes);
                }
            }
        };

        obtenerUsuarioLogeado(codUsuarioLogeado, onDataLoadedListener); // Llama a este método para verificar existencia de informes

        botonFiltros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupFiltros(codUsuarioLogeado, onDataLoadedListener);
            }
        });

        botonVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed(); // Volver a la actividad anterior
            }
        });

        inicializarVariables();
    }

    // Método para obtener el usuario logeado
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
                    if (usuarioSeleccionado != null) {
                        Log.d("MiApp", "Usuario seleccionado encontrado: " + usuarioSeleccionado.getCodUsuario());

                        // Realizar llamada para obtener Informes del usuario
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
                        existenInformes = true;
                        Log.d("MiApp", "variable existenInformes: " + existenInformes);
                        listaTotalReportes.clear();
                        listaTotalReportesOriginal.clear();

                        for (Reporte reporte : reportesAsociados) {
                            listaTotalReportes.add(reporte);
                            listaTotalReportesOriginal.add(reporte);
                            Log.d("MiApp", "Reporte encontrado con nroReporte: " + reporte.getNroReporte() + ", y tipoReporte: " + reporte.getTipoReporte().getNombreTipoReporte());
                        }

                        pantallaInformesCargados(listener);

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

    private void pantallaInformesCargados(OnDataLoadedListener listener) {
        if (existenInformes) {
            Log.d("MiApp", "Defino pantalla con informes cargados");
            setContentView(R.layout.n86_0_informes_cargados); // Muestra layout n88 si existen inventarios

            TextView textoFormatoFiltro = findViewById(R.id.texto_formato);
            TextView textoFormatoFecha = findViewById(R.id.texto_fechas);
            textoFormatoFiltro.setText("Mostrando: " + opcionMenu1);
            textoFormatoFecha.setText("Filtro Fecha: " + opcionMenu2);

            agregarInforme = findViewById(R.id.button_agrega_informe);
            botonVolver = findViewById(R.id.boton_volver);
            botonFiltros = findViewById(R.id.imagen_menu);

            listener.onDataLoaded();
            Log.d("MiApp", "Ya se ejecutó el listener");

            dimView = findViewById(R.id.dim_view);

            agregarInforme.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ReportesActivity.this, GenerarReporteActivity.class);
                    intent.putExtra("codUsuario", codUsuarioLogeado);
                    intent.putExtra("calendarioSeleccionadoid", codCalendarioSeleccionado);
                    startActivity(intent);
                }
            });

            botonFiltros.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    popupFiltros(codUsuarioLogeado, listener);
                }
            });

            botonVolver.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed(); // Volver a la actividad anterior
                }
            });
        }
    }

    private void loadInformes() {

            // Configurar el RecyclerView
            recyclerView = findViewById(R.id.listareportes_recyclerview);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            reporteAdapter = new ReporteAdapter(new ArrayList<>()); // Inicializar el adaptador con una lista vacía
            reporteAdapter.setCodCalendarioSeleccionado(codCalendarioSeleccionado);
            reporteAdapter.setCodUsuarioLogeado(codUsuarioLogeado);
            reporteAdapter.setEliminarReporteListener(this); // Establece el escuchador
            recyclerView.setAdapter(reporteAdapter);

            if (listaTotalReportes != null) {
                // Imprimir el contenido de listaTotalReportes
                for (Reporte reporte : listaTotalReportes) {
                    Log.d("MiApp", "Reporte en ListaTotalReportes: " + reporte.toString());
                }
                reporteAdapter.setReporteList(listaTotalReportes);
            } else {
                Toast.makeText(ReportesActivity.this, "La lista de Reportes está vacía o es nula", Toast.LENGTH_SHORT).show();
            }


    }

    private void popupFiltros(int codUsuarioLogeado, OnDataLoadedListener listener) {
        RetrofitService retrofitService = new RetrofitService();
        Log.d("MiApp","Se llamó a popupFiltros de InformesActivity");
        View popupView = getLayoutInflater().inflate(R.layout.n82_popup_lista_medicamentos, null);

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

        ImageView desplegable1 = popupView.findViewById(R.id.imagen_desplegable);
        ImageView desplegable2 = popupView.findViewById(R.id.imagen_desplegable2);

        textoFiltroReporte = popupView.findViewById(R.id.text_medicamento);
        textoFiltroFecha = popupView.findViewById(R.id.text_fecha);
        EditText textoNombreMedicamento = popupView.findViewById(R.id.textEdit_medicamento);

        MedicamentoEspecifico = popupView.findViewById(R.id.MedicamentoEspecifico);
        textoFiltroReporte.setText(opcionMenu1);
        textoFiltroFecha.setText(opcionMenu2);

        TextView aplicar = popupView.findViewById(R.id.aplicar);
        TextView cancelar = popupView.findViewById(R.id.cancelar);

        desplegable1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarMenu1(desplegable1);
            }
        });

        desplegable2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarMenu2(desplegable2);
            }
        });

        aplicar.setOnClickListener(view ->{
            Log.d("MiApp", "Se hizo clic en el botón aplicar");
            LocalDate fechaActual = LocalDate.now();
            List<Reporte> informesFiltrados = new ArrayList<>();

            // Crear una copia PROFUNDA de la lista original. Se hace para evitar que se realicen cambios al modificar una variable referenciada
            List<Reporte> listaTotalReportesCopia = new ArrayList<>();
            for (Reporte reporte : listaTotalReportesOriginal) {
                Reporte copia = new Reporte();
                copia.setNroReporte(reporte.getNroReporte());
                copia.setFechaDesde(reporte.getFechaDesde());
                copia.setFechaHasta(reporte.getFechaHasta());
                copia.setFechaGenerada(reporte.getFechaGenerada());
                copia.setTipoReporte(reporte.getTipoReporte());
                copia.setUsuario(reporte.getUsuario());
                listaTotalReportesCopia.add(copia);
            }

            Log.d("MiApp", "La listaTotalReportesOriginal es: " + listaTotalReportesOriginal);
            Log.d("MiApp", "La listaTotalReportes es : " + listaTotalReportes);

            // Hacer un if con cada opción del menú 1 y 2 para modificar la listaTotalReportes con sólo aquellos del filtro

            if (opcionMenu1.equals("Todos los Reportes")) {
                Log.d("MiApp", "Se aplica el filtro de tipoReporte: " + opcionMenu1);
                informesFiltrados.addAll(listaTotalReportesCopia);
            } else if (opcionMenu1.equals("Medicamentos (Todos)")) {
                Log.d("MiApp", "Se aplica el filtro de tipoReporte: " + opcionMenu1);
                for (Reporte reporte : listaTotalReportesCopia) {
                    if (reporte.getTipoReporte().getNombreTipoReporte().equals("Reporte Medicamentos (Todos)")) {
                        informesFiltrados.add(reporte);
                    }
                }
            } else if (opcionMenu1.equals("Medicamento (Uno)")) {
                Log.d("MiApp", "Se aplica el filtro de tipoReporte: " + opcionMenu1);
                String nombreMedicamento = textoNombreMedicamento.getText().toString();
                Log.d("MiApp", "Se ingresó el nombre de medicamento: " + nombreMedicamento);
                for (Reporte reporte : listaTotalReportesCopia) {
                    if (reporte.getTipoReporte().getNombreTipoReporte().equals("Reporte Medicamento (Uno)")) {
                        informesFiltrados.add(reporte);
                    }
                }
            } else if (opcionMenu1.equals("Síntomas")) {
                Log.d("MiApp", "Se aplica el filtro de tipoReporte: " + opcionMenu1);
                for (Reporte reporte : listaTotalReportesCopia) {
                    if (reporte.getTipoReporte().getNombreTipoReporte().equals("Reporte Síntomas")) {
                        informesFiltrados.add(reporte);
                    }
                }
            } else if (opcionMenu1.equals("Mediciones")) {
                Log.d("MiApp", "Se aplica el filtro de tipoReporte: " + opcionMenu1);
                for (Reporte reporte : listaTotalReportesCopia) {
                    if (reporte.getTipoReporte().getNombreTipoReporte().equals("Reporte Mediciones")) {
                        informesFiltrados.add(reporte);
                    }
                }
            }

            if (opcionMenu2.equals("Todo")) {
                Log.d("MiApp", "Se aplica el filtro de fechas: " + opcionMenu2);
            } else if (opcionMenu2.equals("Día")) {
                Log.d("MiApp", "Se aplica el filtro de fechas: " + opcionMenu2);
                List<Reporte> informesAEliminar = new ArrayList<>();
                for (Reporte reporte : informesFiltrados) {
                    if (reporte.getFechaGenerada().isBefore(fechaActual)) {
                        informesAEliminar.add(reporte);
                    }
                }
                informesFiltrados.removeAll(informesAEliminar);
            } else if (opcionMenu2.equals("Semana")) {
                Log.d("MiApp", "Se aplica el filtro de fechas: " + opcionMenu2);
                List<Reporte> informesAEliminar = new ArrayList<>();
                for (Reporte reporte : informesFiltrados) {
                    if (reporte.getFechaGenerada().isBefore(fechaActual.minusDays(7))) {
                        informesAEliminar.add(reporte);
                    }
                }
                informesFiltrados.removeAll(informesAEliminar);
            } else if (opcionMenu2.equals("Mes")) {
                Log.d("MiApp", "Se aplica el filtro de fechas: " + opcionMenu2);
                List<Reporte> informesAEliminar = new ArrayList<>();
                for (Reporte reporte : informesFiltrados) {
                    if (reporte.getFechaGenerada().isBefore(fechaActual.minusDays(30))) {
                        informesAEliminar.add(reporte);
                    }
                }
                informesFiltrados.removeAll(informesAEliminar);
            } else if (opcionMenu2.equals("Año")) {
                List<Reporte> informesAEliminar = new ArrayList<>();
                for (Reporte reporte : informesFiltrados) {
                    if (reporte.getFechaGenerada().isBefore(fechaActual.minusDays(365))) {
                        informesAEliminar.add(reporte);
                    }
                }
                informesFiltrados.removeAll(informesAEliminar);
            }

            // Reemplazar listaTotalReportes con la lista filtrada
            listaTotalReportes.clear(); // Limpiar la lista original
            listaTotalReportes.addAll(informesFiltrados); // Agregar los informes filtrados

            // Llamada a loadInformes para cargar los de la nueva lista
            //loadInformes();
            pantallaInformesCargados(listener);

            popupWindow.dismiss();
            dimView.setVisibility(View.GONE);

        });

        cancelar.setOnClickListener(view ->{
            popupWindow.dismiss();
            dimView.setVisibility(View.GONE);
        });

        // Configurar el OnTouchListener para la vista oscura
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                dimView.setVisibility(View.GONE);
            }
        });

    }


    // Mostrar Menu 1
    public void mostrarMenu1(View view) {
        popupMenu1 = new PopupMenu(this, view); // Asocia el menú con el ImageView
        getMenuInflater().inflate(R.menu.menu_desplegable_1, popupMenu1.getMenu());

        // Configura un listener para los elementos del menú
        popupMenu1.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // Maneja las acciones de los elementos del menú aquí
                switch (item.getItemId()) {
                    case R.id.opcion1_menu1:
                        opcionMenu1 = "Todos los Reportes";
                        MedicamentoEspecifico.setVisibility(View.GONE);
                        break;
                    case R.id.opcion2_menu1:
                        opcionMenu1 = "Medicamentos (Todos)";
                        MedicamentoEspecifico.setVisibility(View.GONE);
                        break;
                    case R.id.opcion3_menu1:
                        opcionMenu1 = "Medicamento (Uno)";
                        MedicamentoEspecifico.setVisibility(View.VISIBLE);
                        break;
                    case R.id.opcion4_menu1:
                        opcionMenu1 = "Síntomas";
                        MedicamentoEspecifico.setVisibility(View.GONE);
                        break;
                    case R.id.opcion5_menu1:
                        opcionMenu1 = "Mediciones";
                        MedicamentoEspecifico.setVisibility(View.GONE);
                        break;
                    default:
                        return false;
                }
                // Actualiza el título de la opción seleccionada en el menú
                item.setTitle(opcionMenu1);
                textoFiltroReporte.setText(opcionMenu1);
                Log.d("MiApp", "La opción seleccionada del menú 1 es: " + opcionMenu1);
                return true;
            }
        });
        // Muestra el PopupMenu
        popupMenu1.show();
    }


    // Mostrar Menu 2
    public void mostrarMenu2(View view) {
        popupMenu2 = new PopupMenu(this, view); // Asocia el menú con el ImageView
        getMenuInflater().inflate(R.menu.menu_desplegable_2, popupMenu2.getMenu());

        // Configura un listener para los elementos del menú
        popupMenu2.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // Maneja las acciones de los elementos del menú aquí
                switch (item.getItemId()) {
                    case R.id.opcion1_menu2:
                        opcionMenu2 = "Todo";
                        break;
                    case R.id.opcion2_menu2:
                        opcionMenu2 = "Día";
                        break;
                    case R.id.opcion3_menu2:
                        opcionMenu2 = "Semana";
                        break;
                    case R.id.opcion4_menu2:
                        opcionMenu2 = "Mes";
                        break;
                    case R.id.opcion5_menu2:
                        opcionMenu2 = "Año";
                        break;
                    default:
                        return false;
                }
                // Actualiza el título de la opción seleccionada en el menú
                item.setTitle(opcionMenu2);
                textoFiltroFecha.setText(opcionMenu2);
                Log.d("MiApp", "La opción seleccionada del menú 2 es: " + opcionMenu2);
                return true;
            }
        });
        // Muestra el PopupMenu
        popupMenu2.show();
    }

    private void popupEliminarReporte(int nroReporte) {
        Log.d("MiApp","Se llamó a popupFiltros de InformesActivity");
        View popupView = getLayoutInflater().inflate(R.layout.n86_2_popup_borrar_reporte, null);

        // Crear la instancia de PopupWindow
        popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        // Hacer que el popup sea enfocable (opcional)
        popupWindow.setFocusable(true);

        // Configurar animación para oscurecer el fondo
        View rootView = findViewById(android.R.id.content);

        //View dimView = findViewById(R.id.dim_view);
        dimView.setVisibility(View.VISIBLE);

        Animation scaleAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.popup);
        popupView.startAnimation(scaleAnimation);

        // Mostrar el popup en la ubicación deseada
        popupWindow.showAtLocation(rootView, Gravity.CENTER, 0, 0);

        TextView textViewEliminar = popupView.findViewById(R.id.eliminar);
        TextView textViewCancelar = popupView.findViewById(R.id.cancelar);

        textViewCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                dimView.setVisibility(View.GONE);
            }
        });

        textViewEliminar.setOnClickListener(view -> {
            RetrofitService retrofitService = new RetrofitService();
            ReporteApi reporteApi = retrofitService.getRetrofit().create(ReporteApi.class);

            // Hacer la llamada a la API para eliminar la clase Reporte asociada al nroReporte
            Call<Reporte> call = reporteApi.deleteByNroReporte(nroReporte);
            call.enqueue(new Callback<Reporte>() {
                @Override
                public void onResponse(Call<Reporte> call, Response<Reporte> response) {
                    if (response.isSuccessful()) {
                        Log.d("MiApp", "Reporte nro: " + nroReporte + " eliminado con éxito");
                        listaTotalReportesOriginal.remove(reporteApi.getByNroReporte(nroReporte));
                        listaTotalReportes.remove(reporteApi.getByNroReporte(nroReporte));
                        popupWindow.dismiss();
                        dimView.setVisibility(View.GONE);
                        popupReporteEliminado();

                    } else {
                        Log.d("MiApp", "No se pudo eliminar el reporte con nroReporte: " + nroReporte);
                        Log.d("MiApp", "Error en la solicitud de eliminar 'Reporte' " + response.message());
                    }
                }
                @Override
                public void onFailure(Call<Reporte> call, Throwable t) {
                    Log.e("MiApp", "Error en la solicitud de eliminar 'Reporte' " + t.getMessage());
                }
            });
        });

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                dimView.setVisibility(View.GONE);
            }
        });
    }

    private void popupReporteEliminado() {
        Log.d("MiApp","Se llamó a popupReporteEliminado");
        View popupView = getLayoutInflater().inflate(R.layout.n86_3_popup_reporte_eliminado, null);

        // Crear la instancia de PopupWindow
        PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        // Hacer que el popup sea enfocable (opcional)
        popupWindow.setFocusable(true);

        // Configurar animación para oscurecer el fondo
        View rootView = findViewById(android.R.id.content);

        //View dimView = findViewById(R.id.dim_view);
        dimView.setVisibility(View.VISIBLE);

        Animation scaleAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.popup);
        popupView.startAnimation(scaleAnimation);

        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(false);
        // Mostrar el popup en la ubicación deseada
        popupWindow.showAtLocation(rootView, Gravity.CENTER, 0, 0);

        ImageView cerrar = popupView.findViewById(R.id.boton_cerrar);

        cerrar.setOnClickListener(view ->{
            popupWindow.dismiss();
            dimView.setVisibility(View.GONE);
            obtenerUsuarioLogeado(codUsuarioLogeado, onDataLoadedListener);
        });

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                dimView.setVisibility(View.GONE);
            }
        });
    }



    @SuppressLint("WrongViewCast")
    private void inicializarVariables () {
        retrofitService = new RetrofitService();
    }

    public interface OnDataLoadedListener {
        void onDataLoaded();
    }

    @Override
    public void onEliminarReporte(int nroReporte) {
        popupEliminarReporte(nroReporte);
    }

}
