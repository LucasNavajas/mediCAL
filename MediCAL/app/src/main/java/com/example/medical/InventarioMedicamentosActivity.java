package com.example.medical;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medical.adapter.ConsejoAdapter;
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

    private ImageView botonVolver;
    private Object context;
    private RetrofitService retrofitService;

    private PopupWindow popupWindow;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private RecyclerView recyclerView;
    private InventarioAdapter inventarioAdapter;
    private int codUsuarioLogeado;
    private boolean existenInventarios = false; // Variable global para verificar existencia de inventarios
    private List<Inventario> listaTotalInventarios = new ArrayList<>(); // Lista global para almacenar todos los inventarios de un usuario

    private ImageView botonDesplegable;
    private List<String> opciones = new ArrayList<>(); // Lista para las opciones del menú desplegable


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = context;

        Intent intent1 = getIntent();
        codUsuarioLogeado = intent1.getIntExtra("codUsuario", 0);

        OnDataLoadedListener onDataLoadedListener = new OnDataLoadedListener() {
            @Override
            public void onDataLoaded() {
                Log.d("MiApp", "Llamo al método loadInventarios si existen inventarios: " + existenInventarios);
                if (existenInventarios) {
                    loadInventarios(); // Prueba de llamada a loadInventarios cuando ya haya recorrido lo asociado al usuario
                    Log.d("MiApp", "Entró en el if y la variable existenInventarios es: " + existenInventarios);
                }
            }
        };

        obtenerUsuarioLogeado(codUsuarioLogeado, onDataLoadedListener); // Llama a este método para verificar existencia de inventarios

        setContentView(R.layout.n88_0_inventario_cargado); // Establece la pantalla 88 como predeterminada en caso que no hayan inventarios

        botonVolver = findViewById(R.id.boton_volver);

        botonVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed(); // Volver a la actividad anterior
            }
        });

        inicializarVariables();

    }

    // Método para obtener los calendarios del usuario
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

                        // Realizar llamada para obtener Calendarios del usuario
                        obtenerCalendariosDelUsuario(usuarioSeleccionado, listener);

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
    private void obtenerCalendariosDelUsuario(Usuario usuario, OnDataLoadedListener listener) {
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
                        Log.d("MiApp", "Calendarios Asociados Encontrados");
                        for (Calendario calendario : calendariosAsociados) {
                            // Para cada calendario, obtén las clases "Recordatorio"
                            obtenerRecordatoriosPorCalendario(calendario, listener);
                            Log.d("MiApp", "codCalendario encontrado: " + calendario.getCodCalendario());
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
    private void obtenerRecordatoriosPorCalendario(Calendario calendario, OnDataLoadedListener listener) {
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
                        Log.d("MiApp", "Recordatorios Asociados Encontrados: ");
                        for (Recordatorio recordatorio : recordatoriosAsociados) {
                            // Para cada recordatorio, obtén las clases "Inventario"
                            Log.d("MiApp", "codRecordatorio encontrado: " + recordatorio.getCodRecordatorio());
                            obtenerInventarioPorRecordatorio(recordatorio, listener);
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

    private void obtenerInventarioPorRecordatorio(Recordatorio recordatorio, OnDataLoadedListener listener) {

        Inventario inventarioAsociado = recordatorio.getInventario();

        if (inventarioAsociado != null) {
            Log.d("MiApp", "Inventario Asociado Encontrado: ");
            existenInventarios = true;
            Log.d("MiApp", "variable existenInventarios: " + existenInventarios);
            listaTotalInventarios.add(inventarioAsociado);
            Log.d("MiApp", "Inventario encontrado con cantReal: " + inventarioAsociado.getCantRealInventario() + ", y cantAviso: " + inventarioAsociado.getCantAvisoInventario());

            inventarioAsociado.setRecordatorio(recordatorio);

            Log.d("MiApp", "El inventario asociado tiene: " + inventarioAsociado);
            Log.d("MiApp", "El recordatorio del inventarioAsociado es: " + inventarioAsociado.getRecordatorio());

            // Agrego a la lista de opciones del menú desplegable el nombre de los medicamentos con recordatorio
            opciones.add(recordatorio.getMedicamento().getNombreMedicamento());

            listener.onDataLoaded();
            Log.d("MiApp", "Ya se ejecutó el listener");

        } else {
            Log.d("MiApp", "No se encontraron clases 'Inventario' asociadas al codRecordatorio: " + recordatorio.getCodRecordatorio());
        }
    }


    private void loadInventarios() {
        if (existenInventarios) {
            Log.d("MiApp", "Defino pantalla con inventarios cargados");
            setContentView(R.layout.n88_0_inventario_cargado); // Muestra layout n88 si existen inventarios

            botonVolver = findViewById(R.id.boton_volver);
            botonDesplegable = findViewById(R.id.desplegable);
            opciones.add("Todos los medicamentos"); // Primer elemento sería por defecto que muestre todos

            // Configurar el RecyclerView
            recyclerView = findViewById(R.id.listainventarios_recyclerview);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            inventarioAdapter = new InventarioAdapter(new ArrayList<>()); // Inicializar el adaptador con una lista vacía
            recyclerView.setAdapter(inventarioAdapter);

            if (listaTotalInventarios != null) {
                // Imprimir el contenido de listaTotalInventarios
                for (Inventario inventario : listaTotalInventarios) {
                    Log.d("MiApp", "Inventario en ListaTotalInventarios: " + inventario.toString());
                }
                inventarioAdapter.setInventarioList(listaTotalInventarios);
                //populateListView(listaTotalInventarios);
                Log.d("InventarioMedicamentosActivity", "mando a popular la list");

            } else {
                Log.d("InventarioMedicamentosActivity", "no mando a popular la list");
                Toast.makeText(InventarioMedicamentosActivity.this, "La lista de Inventarios está vacía o es nula", Toast.LENGTH_SHORT).show();
            }

            botonVolver.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed(); // Volver a la actividad anterior
                }
            });

            botonDesplegable.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    mostrarMenu(view); // Llama al método para mostrar menú desplegable
                }
            });

        }
    }


    private void populateListView(List<Inventario> inventarioList) {
        if (listaTotalInventarios != null) {
            Log.d("InventarioMedicamentosActivity", "populo en la list");
            InventarioAdapter inventarioAdapter = new InventarioAdapter(listaTotalInventarios);
            recyclerView.setAdapter(inventarioAdapter);
        } else {
            Log.d("InventarioMedicamentosActivity", "no populo en la list");
            Toast.makeText(InventarioMedicamentosActivity.this, "La lista de Inventarios está vacía o es nula", Toast.LENGTH_SHORT).show();
        }
    }


    public void mostrarMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, botonDesplegable);

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                String opcionSeleccionada = item.getTitle().toString();
                // Realiza acciones según la opción seleccionada
                showToast("Opción seleccionada: " + opcionSeleccionada);
                return true;
            }
        });
        popupMenu.show();
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


    @SuppressLint("WrongViewCast")
    private void inicializarVariables () {
        // Falta 

        retrofitService = new RetrofitService();
    }

    public interface OnDataLoadedListener {
        void onDataLoaded();
    }

}