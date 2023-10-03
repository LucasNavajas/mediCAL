package com.example.medical;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import androidx.appcompat.app.AppCompatActivity;
//import org.apache.poi.ss.usermodel.*;
//import org.apache.poi.ss.util.CellRangeAddress;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.medical.adapter.InventarioAdapter;
import com.example.medical.adapter.ReporteAdapter;
import com.example.medical.model.Inventario;
import com.example.medical.model.Reporte;
import com.example.medical.model.Usuario;
import com.example.medical.retrofit.ReporteApi;
import com.example.medical.retrofit.RetrofitService;
import com.example.medical.retrofit.UsuarioApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InformesActivity extends AppCompatActivity {

    // Falta completar

    private RetrofitService retrofitService;

    private ImageView botonVolver;
    private Button agregarInforme;
    private RecyclerView recyclerView;
    private int codUsuarioLogeado;
    private int codCalendarioSeleccionado;

    private boolean existenInformes = false; // Variable global para verificar existencia de informes
    private List<Reporte> listaTotalReportes = new ArrayList<>(); // Lista global para almacenar todos los informes de un usuario
    private ReporteAdapter reporteAdapter;

    private ImageView botonDesplegable;
    private List<String> opciones = new ArrayList<>(); // Lista para las opciones del menú desplegable

    private Object context;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.n81_informes_sin_cargar);   // Establece la pantalla 81 como predeterminada en caso que no hayan informes
        this.context = context;

        agregarInforme = findViewById(R.id.button_agrega_informe);
        botonVolver = findViewById(R.id.boton_volver);

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
            setContentView(R.layout.n86_informes_cargados); // Muestra layout n88 si existen inventarios

            agregarInforme = findViewById(R.id.button_agrega_informe);
            botonVolver = findViewById(R.id.boton_volver);

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

            botonVolver.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed(); // Volver a la actividad anterior
                }
            });
        }
    }


    @SuppressLint("WrongViewCast")
    private void inicializarVariables () {
        retrofitService = new RetrofitService();
    }

    public interface OnDataLoadedListener {
        void onDataLoaded();
    }

}
