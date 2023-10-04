package com.example.medical;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
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

import android.view.Menu;
import android.view.MenuItem;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import androidx.appcompat.app.AppCompatActivity;
//import org.apache.poi.ss.usermodel.*;
//import org.apache.poi.ss.util.CellRangeAddress;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.util.ArrayList;
import java.util.List;

import com.example.medical.adapter.ReporteAdapter;
import com.example.medical.model.Reporte;
import com.example.medical.model.Usuario;
import com.example.medical.retrofit.ReporteApi;
import com.example.medical.retrofit.RetrofitService;
import com.example.medical.retrofit.UsuarioApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InformesActivity extends AppCompatActivity {

    private RetrofitService retrofitService;

    private ImageView botonVolver;
    private ImageView botonFiltros;
    private Button agregarInforme;
    private RecyclerView recyclerView;
    private int codUsuarioLogeado;
    private int codCalendarioSeleccionado;

    private boolean existenInformes = false; // Variable global para verificar existencia de informes
    private List<Reporte> listaTotalReportes = new ArrayList<>(); // Lista global para almacenar todos los informes de un usuario
    private ReporteAdapter reporteAdapter;

    private String opcionMenu1;
    private String opcionMenu2;
    private TextView textoFiltroReporte;
    private TextView textoFiltroFecha;
    private ConstraintLayout MedicamentoEspecifico;
    private PopupMenu popupMenu1;
    private PopupMenu popupMenu2;

    private Object context;

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
                Intent intent = new Intent(InformesActivity.this, GenerarReporteActivity.class);
                intent.putExtra("codUsuario", codUsuarioLogeado);
                intent.putExtra("calendarioSeleccionadoid", codCalendarioSeleccionado);
                startActivity(intent);
            }
        });

        botonFiltros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupFiltros();
            }
        });


        OnDataLoadedListener onDataLoadedListener = new OnDataLoadedListener() {
            @Override
            public void onDataLoaded() {
                Log.d("MiApp", "Llamo al método loadInformes si existen informes: " + existenInformes);
                if (existenInformes) {
                    loadInformes(); // Prueba de llamada a loadInformes cuando ya haya recorrido lo asociado al usuario
                    Log.d("MiApp", "Entró en el if y la variable existenInformes es: " + existenInformes);
                }
            }
        };

        obtenerUsuarioLogeado(codUsuarioLogeado, onDataLoadedListener); // Llama a este método para verificar existencia de informes

        //Pasar intent.putExtra("calendarioSeleccionadoid", getIntent().getIntExtra("calendarioSeleccionadoid", 0)) por todas las actividades hacia adelante y cuando se vuelve a mas tambien
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

                        for (Reporte reporte : reportesAsociados) {
                            listaTotalReportes.add(reporte);
                            Log.d("MiApp", "Reporte encontrado con nroReporte: " + reporte.getNroReporte() + ", y tipoReporte: " + reporte.getTipoReporte().getNombreTipoReporte());
                        }

                        listener.onDataLoaded();
                        Log.d("MiApp", "Ya se ejecutó el listener");

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
        if (existenInformes) {
            Log.d("MiApp", "Defino pantalla con informes cargados");
            setContentView(R.layout.n86_0_informes_cargados); // Muestra layout n88 si existen inventarios

            agregarInforme = findViewById(R.id.button_agrega_informe);
            botonVolver = findViewById(R.id.boton_volver);
            botonFiltros = findViewById(R.id.imagen_menu);

            // Configurar el RecyclerView
            recyclerView = findViewById(R.id.listareportes_recyclerview);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            reporteAdapter = new ReporteAdapter(new ArrayList<>()); // Inicializar el adaptador con una lista vacía
            recyclerView.setAdapter(reporteAdapter);

            if (listaTotalReportes != null) {
                // Imprimir el contenido de listaTotalReportes
                for (Reporte reporte : listaTotalReportes) {
                    Log.d("MiApp", "Reporte en ListaTotalReportes: " + reporte.toString());
                }
                reporteAdapter.setReporteList(listaTotalReportes);

            } else {
                Toast.makeText(InformesActivity.this, "La lista de Reportes está vacía o es nula", Toast.LENGTH_SHORT).show();
            }

            agregarInforme.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(InformesActivity.this, GenerarReporteActivity.class);
                    intent.putExtra("codUsuario", codUsuarioLogeado);
                    intent.putExtra("calendarioSeleccionadoid", codCalendarioSeleccionado);
                    startActivity(intent);
                }
            });

            botonFiltros.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    popupFiltros();
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

    private void popupFiltros() {
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

        // Por defecto las opciones mostradas son:
        opcionMenu1 = "Todos los Reportes";
        opcionMenu2 = "Año";

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

            // Hacer un if con cada opción del menú 1 y 2 para modificar la listaTotalReportes con sólo aquellos del filtro

            if (opcionMenu1.equals("Medicamento (Uno)")) {
                String nombreMedicamento = textoNombreMedicamento.getText().toString();
                Log.d("MiApp", "Se ingresó el nombre de medicamento: " + nombreMedicamento);
            }

            // Falta

            // Llamada a loadInformes para cargar los de la nueva lista
            loadInformes();

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
                        opcionMenu2 = "Dia";
                        break;
                    case R.id.opcion2_menu2:
                        opcionMenu2 = "Semana";
                        break;
                    case R.id.opcion3_menu2:
                        opcionMenu2 = "Mes";
                        break;
                    case R.id.opcion4_menu2:
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

    @SuppressLint("WrongViewCast")
    private void inicializarVariables () {
        retrofitService = new RetrofitService();
    }

    public interface OnDataLoadedListener {
        void onDataLoaded();
    }

}
