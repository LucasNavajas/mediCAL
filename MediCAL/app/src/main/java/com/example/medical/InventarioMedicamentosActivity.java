package com.example.medical;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medical.adapter.InventarioAdapter;
import com.example.medical.model.Calendario;
import com.example.medical.model.Inventario;
import com.example.medical.model.Recordatorio;
import com.example.medical.model.Usuario;
import com.example.medical.retrofit.CalendarioApi;
import com.example.medical.retrofit.InventarioApi;
import com.example.medical.retrofit.RecordatorioApi;
import com.example.medical.retrofit.RetrofitService;
import com.example.medical.retrofit.UsuarioApi;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InventarioMedicamentosActivity extends AppCompatActivity {

    // Falta

    private ImageView botonVolver;
    private Object context;
    private RetrofitService retrofitService;

    private RecyclerView recyclerView;
    private PopupWindow popupWindow;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private int codUsuarioLogeado;
    private boolean existenInventarios = false; // Variable global para verificar existencia de inventarios
    private List<Inventario> listaTotalInventarios = new ArrayList<>(); // Variable global para almacenar todos los inventarios de un usuario

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = context;

        Intent intent1 = getIntent();
        codUsuarioLogeado = intent1.getIntExtra("codUsuario", 0);
        obtenerUsuarioLogeado(codUsuarioLogeado); // Llama a este método para verificar existencia de inventarios

        if (existenInventarios) {
            setContentView(R.layout.n88_0_inventario_cargado); // Mostrar layout n88 si existen inventarios
        } else {
            setContentView(R.layout.n87_inventario_sin_cargar); // Mostrar layout n87 si no existen inventarios
        }

        botonVolver = findViewById(R.id.boton_volver);

        botonVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed(); // Volver a la actividad anterior
            }
        });

        inicializarVariables();

        recyclerView = findViewById(R.id.listainventarios_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // loadInventarios();           Llamar después de tener una lista de inventarios a mostrar?

    }

    // Método para obtener los calendarios del usuario
    private void obtenerUsuarioLogeado(int codUsuarioLogeado) {
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

                        // Realizar llamada para obtener Calendarios del usuario
                        obtenerCalendariosDelUsuario(usuarioSeleccionado);
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


    // Método para obtener los calendarios del usuario
    private void obtenerCalendariosDelUsuario(Usuario usuario) {
        // Crear una instancia de la interfaz de la API de CalendarioApi utilizando Retrofit
        RetrofitService retrofitService = new RetrofitService();
        CalendarioApi calendarioApi = retrofitService.getRetrofit().create(CalendarioApi.class);

        // Hacer la llamada a la API para obtener los calendarios asociados al usuario
        Call<List<Calendario>> calendariosCall = calendarioApi.getByCodUsuario(usuario.getCodUsuario());

        calendariosCall.enqueue(new Callback<List<Calendario>>() {
            public void onResponse(Call<List<Calendario>> call, Response<List<Calendario>> response) {
                if (response.isSuccessful()) {
                    List<Calendario> calendariosAsociados = response.body();
                    if (calendariosAsociados != null && !calendariosAsociados.isEmpty()) {
                        // Aquí puedes manejar los calendarios obtenidos
                        for (Calendario calendario : calendariosAsociados) {
                            // Para cada calendario, obtén las clases "Recordatorio"
                            obtenerRecordatoriosPorCalendario(calendario);
                        }
                    } else {
                        Log.d("MiApp", "No se encontraron calendarios asociados al usuario");
                    }
                } else {
                    Log.d("MiApp", "Error en la solicitud de calendarios: " + response.message());
                }
            }
            @Override
            public void onFailure(Call<List<Calendario>> call, Throwable t) {
                Log.e("MiApp", "Error en la solicitud de calendarios: " + t.getMessage());
            }
        });
    }


    // Método para obtener las clases "Recordatorio" asociadas a un calendario
    private void obtenerRecordatoriosPorCalendario(Calendario calendario) {
        // Crear una instancia de la interfaz de la API de RecordatorioApi utilizando Retrofit
        RetrofitService retrofitService = new RetrofitService();
        RecordatorioApi recordatorioApi = retrofitService.getRetrofit().create(RecordatorioApi.class);

        // Hacer la llamada a la API para obtener las clases "Recordatorio" asociadas a un calendario
        Call<List<Recordatorio>> recordatoriosCall = recordatorioApi.getByCodCalendario(calendario.getCodCalendario());

        recordatoriosCall.enqueue(new Callback<List<Recordatorio>>() {
            @Override
            public void onResponse(Call<List<Recordatorio>> call, Response<List<Recordatorio>> response) {
                if (response.isSuccessful()) {
                    List<Recordatorio> recordatoriosAsociados = response.body();
                    if (recordatoriosAsociados != null && !recordatoriosAsociados.isEmpty()) {
                        // Aquí puedes manejar los recordatorios obtenidos
                        for (Recordatorio recordatorio : recordatoriosAsociados) {
                            // Para cada calendario, obtén las clases "Recordatorio"
                            obtenerInventarioPorRecordatorio(recordatorio);
                        }
                    } else {
                        Log.d("MiApp", "No se encontraron clases 'Recordatorio' asociadas al calendario " + calendario.getNombreCalendario());
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

    private void obtenerInventarioPorRecordatorio(Recordatorio recordatorio) {
        // Crear una instancia de la interfaz de la API de InventarioApi utilizando Retrofit
        RetrofitService retrofitService = new RetrofitService();
        InventarioApi inventarioApi = retrofitService.getRetrofit().create(InventarioApi.class);

        // Hacer la llamada a la API para obtener las clases "Inventario" asociadas a un recordatorio
        Call<List<Inventario>> inventarioCall = inventarioApi.getByCodRecordatorio(recordatorio.getCodRecordatorio());

        inventarioCall.enqueue(new Callback<List<Inventario>>() {
            @Override
            public void onResponse(Call<List<Inventario>> call, Response<List<Inventario>> response) {
                if (response.isSuccessful()) {
                    List<Inventario> inventarioAsociado = response.body();
                    if (inventarioAsociado != null && !inventarioAsociado.isEmpty()) {
                        // Aquí puedes manejar las clases de inventario obtenidas para el recordatorio actual
                        existenInventarios = true; // Actualiza la variable global
                        listaTotalInventarios.addAll(inventarioAsociado);
                        // Aca quiza luego leer los datos de cada inventario o cargarlos?
                    } else {
                        Log.d("MiApp", "No se encontraron clases 'Inventario' asociadas al recordatorio " + recordatorio.getCodRecordatorio());
                    }
                    // Llamar a loadInventarios después de agregar todos los inventarios
                    loadInventarios();
                } else {
                    Log.d("MiApp", "Error en la solicitud de clases 'Inventario': " + response.message());
                }
            }
            @Override
            public void onFailure(Call<List<Inventario>> call, Throwable t) {
                Log.e("MiApp", "Error en la solicitud de clases 'Inventario': " + t.getMessage());
            }
        });
    }


    private void loadInventarios() {
        InventarioAdapter inventarioAdapter = new InventarioAdapter(listaTotalInventarios);
        recyclerView.setAdapter(inventarioAdapter);

        /*
        inventarioApi.getAllInventarios()
                .enqueue(new Callback<List<Inventario>>() {
                    @Override
                    public void onResponse(Call<List<Inventario>> call, Response<List<Inventario>> response) {
                        Log.d("InventarioMedicamentosActivity", "llamo el método on response");
                        int statusCode = response.code();
                        Log.d("InventarioMedicamentosActivity", "Status code: " + statusCode);
                        if (response.isSuccessful() && response.body() != null) {
                            Log.d("InventarioMedicamentosActivity", "la populo");
                            populateListView(response.body());
                        } else {
                            Log.d("InventarioMedicamentosActivity", "no anda");
                            Log.d("InventarioMedicamentosActivity", "Response code: " + response.code());
                            Log.d("InventarioMedicamentosActivity", "Error body: " + response.errorBody());
                            Toast.makeText(InventarioMedicamentosActivity.this, "Respuesta vacía o incorrecta del servidor", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<List<Inventario>> call, Throwable t) {
                        Log.d("InventarioMedicamentosActivity", "no carga");
                        Toast.makeText(InventarioMedicamentosActivity.this, "Fallo en la base de datos", Toast.LENGTH_SHORT).show();
                    }
                });

         */
    }

    /*
    private void populateListView(List<Inventario> inventarioList) {
        if (inventarioList != null && !inventarioList.isEmpty()) {
            Log.d("InventarioMedicamentosActivity", "la populo en la list");
            InventarioAdapter inventarioAdapter = new InventarioAdapter (inventarioList);
            recyclerView.setAdapter(inventarioAdapter);
        } else {
            Log.d("InventarioMedicamentosActivity", "no la populo en la list");
            Toast.makeText(InventarioMedicamentosActivity.this, "La lista de Inventarios está vacía o es nula", Toast.LENGTH_SHORT).show();
        }
    }
     */


    @SuppressLint("WrongViewCast")
    private void inicializarVariables () {
        // Falta 

        retrofitService = new RetrofitService();
    }

}