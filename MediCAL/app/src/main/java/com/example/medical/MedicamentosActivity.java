package com.example.medical;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.annotation.SuppressLint;

import com.example.medical.model.Calendario;
import com.example.medical.model.Dosis;
import com.example.medical.model.Inventario;
import com.example.medical.model.Medicamento;
import com.example.medical.model.PerfilPermiso;
import com.example.medical.model.Recordatorio;
import com.example.medical.model.RegistroRecordatorio;
import com.example.medical.model.Usuario;
import com.example.medical.retrofit.CalendarioApi;
import com.example.medical.retrofit.PerfilPermisoApi;
import com.example.medical.retrofit.RecordatorioApi;
import com.example.medical.retrofit.RegistroRecordatorioApi;
import com.example.medical.retrofit.RetrofitService;
import com.example.medical.retrofit.UsuarioApi;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


public class MedicamentosActivity extends AppCompatActivity {
    private RetrofitService retrofitService = new RetrofitService();
    private List<PerfilPermiso> permisos;
    private List<Integer> codigosPermisos;
    private UsuarioApi usuarioApi = retrofitService.getRetrofit().create(UsuarioApi.class);
    private PerfilPermisoApi perfilPermisoApi = retrofitService.getRetrofit().create(PerfilPermisoApi.class);
    private ImageView menuButton;
    private TextView nombreUsuario;
    private TextView nombre;
    private LinearLayout casa_inicio;
    private ImageView menuButtonUsuario;
    private RelativeLayout editarPerfil;
    private RelativeLayout restablecerContrasenia;
    private RelativeLayout eliminarCuenta;
    private RelativeLayout cerrarSesion;
    private RelativeLayout soporte;

    private FirebaseUser usuario;
    private ImageButton imageninicio;
    private ImageButton imagenconsejos;
    private LinearLayout mas;
    private ImageButton masimagen;
    private TextView perfilUsuario;

    private PopupWindow popupWindow;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private int codUsuarioLogeado;

    private Calendario calendarioSeleccionado;
    private Usuario usuarioInstance;
    private int codCalendario;
    private CalendarioApi calendarioApi;
    private RecordatorioApi recordatorioApi;
    private Object context;
    private int si;
    private int no;
    private int noentra;
    private int otra;
    private int aca;
    private int siEsNull;
    private int aver;

    private LinearLayout progressBar;
    private View dimView;

    private RegistroRecordatorioApi registroRecordatorioApi;
    private Set<String> combinacionesProcesadas;
    private Set<String> combinacionesUnicas;
    private Map<String, List<RegistroRecordatorio>> grupos;
    private Set<String> nombresMedicamentosProcesados = new HashSet<>(); // Conjunto para realizar un seguimiento de nombres de medicamentos procesados


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.n71_medicamentos_agregados); // Reemplaza "tu_layout" con el nombre de tu archivo XML de diseño

        // Inicializa menuButton después de setContentView
        menuButton = findViewById(R.id.menu_button);
        menuButtonUsuario = findViewById(R.id.menu_button_nav);
        combinacionesUnicas = new HashSet<>();
        // Antes de tu bucle para procesar registros, crea un conjunto para realizar un seguimiento de las combinaciones únicas
        combinacionesProcesadas = new HashSet<>();

        RetrofitService retrofitService = new RetrofitService();
        Intent intent1 = getIntent();
        codCalendario = intent1.getIntExtra("calendarioSeleccionadoid", 0);
        Log.d("MiApp", "codCalendario en MasActivity: " + codCalendario); // Agregar este log

        codUsuarioLogeado = getIntent().getIntExtra("codUsuario", 0);
        Log.d("MiApp", "codUsuario en MasActivity: " + codUsuarioLogeado);
        inicializarVariables();

        // Inicializar las APIs utilizando Retrofit
        calendarioApi = retrofitService.getRetrofit().create(CalendarioApi.class);
        recordatorioApi = retrofitService.getRetrofit().create(RecordatorioApi.class);
        registroRecordatorioApi = retrofitService.getRetrofit().create(RegistroRecordatorioApi.class);
        si = 0;
        no = 0;
        noentra = 0;
        otra = 3;
        aca = 0;
        siEsNull = 0;
        aver = 2;
        // Obtener el calendario seleccionado
        obtenerCalendarioSeleccionado();


        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        menuButtonUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        cerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupCerrarSesion();
            }
        });


        editarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MedicamentosActivity.this, EditarPerfilUsuarioActivity.class);
                intent.putExtra("codUsuario", codUsuarioLogeado);
                startActivity(intent);
            }
        });

        soporte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MedicamentosActivity.this, FAQActivity.class);
                startActivity(intent);
            }
        });

        restablecerContrasenia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MedicamentosActivity.this, RestablecerContraseniaActivity.class);
                intent.putExtra("codUsuario", codUsuarioLogeado);
                startActivity(intent);

            }
        });
        eliminarCuenta.setOnClickListener(view -> {
            popupEliminarCuenta();
        });

        casa_inicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MedicamentosActivity.this, InicioCalendarioActivity.class);
                intent.putExtra("codUsuario", codUsuarioLogeado);
                startActivity(intent);

            }
        });

        imageninicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MedicamentosActivity.this, InicioCalendarioActivity.class);
                intent.putExtra("codUsuario", codUsuarioLogeado);
                startActivity(intent);

            }
        });

        imagenconsejos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MedicamentosActivity.this, ConsejosActivity.class);
                intent.putExtra("codUsuario", codUsuarioLogeado);
                startActivity(intent);

            }
        });
        mas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MedicamentosActivity.this, MasActivity.class);
                intent.putExtra("codUsuario", codUsuarioLogeado);
                intent.putExtra("calendarioSeleccionadoid", getIntent().getIntExtra("calendarioSeleccionadoid", 0));
                startActivity(intent);
            }
        });

        masimagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MedicamentosActivity.this, MasActivity.class);
                intent.putExtra("codUsuario", codUsuarioLogeado);
                intent.putExtra("calendarioSeleccionadoid", getIntent().getIntExtra("calendarioSeleccionadoid", 0));
                startActivity(intent);
            }
        });


    }


    //@SuppressLint("WrongViewCast") ?
    @SuppressLint("WrongViewCast")
    private void inicializarVariables() {
        nombreUsuario = findViewById(R.id.nombre_usuario);
        editarPerfil = findViewById(R.id.editar_perfil);
        restablecerContrasenia = findViewById(R.id.restablecer_contrasenia);
        cerrarSesion = findViewById(R.id.cerrar_sesion);
        soporte = findViewById(R.id.soporte);
        casa_inicio = findViewById(R.id.casa_inicio);
        imageninicio = findViewById(R.id.imageninicio);
        imagenconsejos = findViewById(R.id.imagenconsejos);
        menuButton = findViewById(R.id.menu_button);
        mas = findViewById(R.id.mas);
        masimagen = findViewById(R.id.masimagen);
        menuButtonUsuario = findViewById(R.id.menu_button_nav);
        eliminarCuenta = findViewById(R.id.eliminar_cuenta);
        perfilUsuario = findViewById(R.id.perfil_usuario);
        progressBar = findViewById(R.id.progressBar);
        dimView = findViewById(R.id.dim_view);
        progressBar.setVisibility(View.VISIBLE);
        dimView.setVisibility(View.VISIBLE);
        usuarioApi.getByCodUsuario(codUsuarioLogeado).enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                usuarioInstance = response.body();
                deshabilitarBotonConsejos();
                if (usuarioInstance.getPerfil() != null) {
                    perfilUsuario.setText(usuarioInstance.getPerfil().getNombrePerfil());
                }
                nombreUsuario.setText(usuarioInstance.getUsuarioUnico());
                verificarPermisos(usuarioInstance);
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Toast.makeText(MedicamentosActivity.this, "Error al cargar el usuario", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deshabilitarBotonConsejos() {
        // Si no es un Usuario Particular, se deshabilita la Sección Consejos
        if (!usuarioInstance.getPerfil().getNombrePerfil().equals("Usuario Particular")){
            LinearLayout botonConsejos = findViewById(R.id.consejos);
            botonConsejos.setVisibility(View.GONE);
        }
    }


    private void popupCerrarSesion() {
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
        View popupView = getLayoutInflater().inflate(R.layout.n14_3_popup_cerrar_sesion, null);

        // Crear la instancia de PopupWindow
        popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

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

        TextView textViewAceptar = popupView.findViewById(R.id.aceptar);
        TextView textViewCancelar = popupView.findViewById(R.id.cancelar);

        textViewCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ocultar el PopupWindow
                popupWindow.dismiss();

                // Ocultar el fondo oscurecido
                dimView.setVisibility(View.GONE);
            }
        });
        textViewAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Intent intent = new Intent(MedicamentosActivity.this, BienvenidoActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish(); // Opcional: finaliza la actividad actual si ya no la necesitas en el back stack
            }
        });
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                dimView.setVisibility(View.GONE);
            }
        });
    }

    public void popupEliminarCuenta() {
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
        View popupView = getLayoutInflater().inflate(R.layout.n14_1_popup_eliminar_cuenta, null);

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

        TextView aceptar = popupView.findViewById(R.id.aceptar);
        TextView cancelar = popupView.findViewById(R.id.cancelar);

        aceptar.setOnClickListener(view -> {
            popupMotivoEliminacion();
            popupWindow.dismiss();
        });
        cancelar.setOnClickListener(view -> {
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

    private void popupMotivoEliminacion() {
        View popupView = getLayoutInflater().inflate(R.layout.n14_2_popup_motivo_finvigencia, null);

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

        EditText motivo = popupView.findViewById(R.id.textEdit_motivo);
        TextView aceptar = popupView.findViewById(R.id.aceptar);
        TextView cancelar = popupView.findViewById(R.id.cancelar);

        aceptar.setOnClickListener(view -> {
            String textMotivo = motivo.getText().toString();
            usuarioApi.eliminarUsuario(codUsuarioLogeado, textMotivo).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    mAuth.signOut();
                    Intent intent = new Intent(MedicamentosActivity.this, BienvenidoActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(MedicamentosActivity.this, "Error al eliminar la cuenta, intente nuevamente", Toast.LENGTH_SHORT).show();
                }
            });
        });

        cancelar.setOnClickListener(view -> {
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

    private void obtenerCalendarioSeleccionado() {
        Call<Calendario> call = calendarioApi.getByCodCalendario(codCalendario);
        call.enqueue(new Callback<Calendario>() {
            @Override
            public void onResponse(Call<Calendario> call, Response<Calendario> response) {
                if (response.isSuccessful()) {
                    calendarioSeleccionado = response.body();
                    if (calendarioSeleccionado != null) {
                        Log.d("MiApp", "Calendario seleccionado encontrado en MasInfoMedicion: " + calendarioSeleccionado.getCodCalendario());
                        obtenerRecordatorios(codCalendario); // Pasar el codCalendario como parámetro
                    } else {
                        Log.d("MiApp", "No se encontró el Calendario con codCalendario: " + codCalendario);
                    }
                } else {
                    Log.d("MiApp", "Error en la solicitud en MasInfoMedicion: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Calendario> call, Throwable t) {
                Log.e("MiApp", "Error en la solicitud3: " + t.getMessage());
            }
        });
    }

    private void obtenerRecordatorios(int codCalendario) {
        Call<List<Recordatorio>> call = recordatorioApi.getByCodCalendario(codCalendario);
        call.enqueue(new Callback<List<Recordatorio>>() {
            @Override
            public void onResponse(Call<List<Recordatorio>> call, Response<List<Recordatorio>> response) {
                if (response.isSuccessful()) {
                    List<Recordatorio> recordatorios = response.body();
                    if (recordatorios != null && !recordatorios.isEmpty()) {
                        List<Integer> codRecordatorios = new ArrayList<>();
                        for (Recordatorio recordatorio : recordatorios) {
                            codRecordatorios.add(recordatorio.getCodRecordatorio());
                        }
                        obtenerRegistrosRecordatorios(codRecordatorios); // Pasar la lista de codRecordatorios como parámetro
                    } else {
                        Log.d("MiApp", "No se encontraron recordatorios para el calendario con codCalendario: " + codCalendario);
                        createTextViewNoHayActivos(aver);
                    }
                } else {
                    Log.d("MiApp", "Error en la solicitud de recordatorios por calendario: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Recordatorio>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                dimView.setVisibility(View.GONE);
                Log.e("MiApp", "Error en la solicitud de recordatorios por calendario: " + t.getMessage());
            }
        });
    }

    private void obtenerRegistrosRecordatorios(List<Integer> codRecordatorios) {
        final int[] respuestasExitosas = {0}; // Contador para el número de respuestas exitosas

        for (Integer codRecordatorio : codRecordatorios) {
            // Llama a la API con el código de recordatorio actual
            Call<List<RegistroRecordatorio>> call = registroRecordatorioApi.getByCodRecordatorio(codRecordatorio);
            // Realiza la llamada asíncrona
            call.enqueue(new Callback<List<RegistroRecordatorio>>() {
                @Override
                public void onResponse(Call<List<RegistroRecordatorio>> call, Response<List<RegistroRecordatorio>> response) {
                    if (response.isSuccessful()) {
                        // La respuesta exitosa contiene la lista de registros de recordatorios
                        List<RegistroRecordatorio> registros = response.body();
                        Log.d("Miapp", "registros: " + registros);
                        // Continúa con el procesamiento de los registros aquí
                        procesarRegistros(registros);
                        respuestasExitosas[0]++; // Incrementa el contador de respuestas exitosas
                    } else {
                        Log.d("MiApp", "Error en la solicitud de registros de recordatorios: " + response.message());
                    }

                    // Verifica si todas las llamadas han sido procesadas
                    if (respuestasExitosas[0] == codRecordatorios.size()) {
                        if (respuestasExitosas[0] > 0) {

                        }
                    }
                }

                @Override
                public void onFailure(Call<List<RegistroRecordatorio>> call, Throwable t) {
                    // Maneja el error de la solicitud aquí si ocurre una excepción
                    Log.e("MiApp", "Error en la solicitud de registros de recordatorios: " + t.getMessage());

                    // Verifica si todas las llamadas han sido procesadas
                    if (respuestasExitosas[0] == codRecordatorios.size()) {
                        if (respuestasExitosas[0] == 0) {
                            createTextViewNoHayActivos(aver);
                        }
                    }
                }
            });
        }
    }


    private void procesarRegistros(List<RegistroRecordatorio> registros) {
        TextView nombreCalendarioTextView = findViewById(R.id.nombre_calendario);
        Log.d("MiApp", "entra" + registros);
        nombreCalendarioTextView.setText(calendarioSeleccionado.getNombreCalendario()); // Por ejemplo, establece un nuevo texto
        LinearLayout linearLayout = findViewById(R.id.contenido);
        aver=2;
        if (registros != null && !registros.isEmpty()) {
            for (RegistroRecordatorio registro : registros) {
                if (registro.getOmision() == null && registro.isTomaRegistroRecordatorio() == false) {
                    grupos = new HashMap<>();
                    if (si == 0) {
                        TextView medicamentosActivosTextView = new TextView(this);
                        medicamentosActivosTextView.setText("Medicamentos activos");
                        medicamentosActivosTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 26);
                        medicamentosActivosTextView.setTextColor(ContextCompat.getColor(this, R.color.verdeTextos));
                        medicamentosActivosTextView.setTypeface(ResourcesCompat.getFont(this, R.font.inter_medium));
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        layoutParams.setMargins(
                                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, getResources().getDisplayMetrics()),
                                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics()),
                                0, 30
                        );
                        medicamentosActivosTextView.setLayoutParams(layoutParams);
                        linearLayout.addView(medicamentosActivosTextView);
                        si = 1;
                    }

                    String key = registro.getRecordatorio().getMedicamento().getNombreMedicamento() + " " +
                            registro.getRecordatorio().getDosis().getValorConcentracion() + " " +
                            registro.getRecordatorio().getDosis().getConcentracion().getUnidadMedidaC();

                    String nombreMedicamentoProc = registro.getRecordatorio().getMedicamento().getNombreMedicamento();
                    Float valorConcentracionProcesada = registro.getRecordatorio().getDosis().getValorConcentracion();
                    String unidadMedidaCPro = registro.getRecordatorio().getDosis().getConcentracion().getUnidadMedidaC();
                    String todoJunto = nombreMedicamentoProc + "-" + valorConcentracionProcesada + "-" + unidadMedidaCPro;
                    // Verifica si el grupo ya existe en el mapa
                    if (grupos.containsKey(todoJunto)) {
                        // Si existe, agrega el registro al grupo existente
                        grupos.get(todoJunto).add(registro);
                    } else {
                        // Si no existe, crea un nuevo grupo con este registro
                        List<RegistroRecordatorio> nuevoGrupo = new ArrayList<>();
                        nuevoGrupo.add(registro);
                        grupos.put(todoJunto, nuevoGrupo);
                    }

                    // Verifica si el nombre del medicamento ya se ha procesado
                    if (!nombresMedicamentosProcesados.contains(todoJunto)) {
                        // Agrega el nombre del medicamento al conjunto de nombres procesados
                        nombresMedicamentosProcesados.add(todoJunto);
                        Log.d("Miapp", "Combinaciones Unicas: " + key);

                        // Crear un nuevo RelativeLayout dinámicamente
                        RelativeLayout relativeLayout = new RelativeLayout(MedicamentosActivity.this);
                        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                        relativeLayout.setId(View.generateViewId()); // Genera un ID único
                        relativeLayout.setBackgroundColor(ContextCompat.getColor(MedicamentosActivity.this, R.color.white)); // Establecer el fondo en blanco
                        params.setMargins(
                                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, getResources().getDisplayMetrics()), 0,
                                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, getResources().getDisplayMetrics()),
                                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics()));
                        relativeLayout.setLayoutParams(params);

                        // Crear un nuevo TextView para el nombre del medicamento
                        TextView nombreMedicamentoTextView = new TextView(MedicamentosActivity.this);
                        nombreMedicamentoTextView.setId(View.generateViewId()); // Genera un ID único
                        nombreMedicamentoTextView.setText(registro.getRecordatorio().getMedicamento().getNombreMedicamento());
                        nombreMedicamentoTextView.setTypeface(ResourcesCompat.getFont(MedicamentosActivity.this, R.font.inter_medium));
                        nombreMedicamentoTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 21);
                        nombreMedicamentoTextView.setTextColor(ContextCompat.getColor(MedicamentosActivity.this, R.color.black));
                        RelativeLayout.LayoutParams nombreMedicamentoParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                        nombreMedicamentoParams.addRule(RelativeLayout.ALIGN_PARENT_START); // Alinear a la izquierda
                        nombreMedicamentoParams.addRule(RelativeLayout.ALIGN_PARENT_TOP); // Alinear en la parte superior
                        nombreMedicamentoTextView.setLayoutParams(nombreMedicamentoParams);
                        relativeLayout.addView(nombreMedicamentoTextView);

                        TextView concentracionMedicamentoTextView = new TextView(MedicamentosActivity.this);
                        concentracionMedicamentoTextView.setId(View.generateViewId()); // Genera un ID único
                        float valorConcentracion = registro.getRecordatorio().getDosis().getValorConcentracion();
                        String concentracionMostrar = valorConcentracion + " " + registro.getRecordatorio().getDosis().getConcentracion().getUnidadMedidaC();
                        concentracionMedicamentoTextView.setText(concentracionMostrar);
                        concentracionMedicamentoTextView.setTypeface(ResourcesCompat.getFont(MedicamentosActivity.this, R.font.inter_medium));
                        concentracionMedicamentoTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 21);
                        concentracionMedicamentoTextView.setTextColor(ContextCompat.getColor(MedicamentosActivity.this, R.color.gris_medical));
                        RelativeLayout.LayoutParams concentracionMedicamentoParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                        concentracionMedicamentoParams.leftMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics());
                        concentracionMedicamentoParams.addRule(RelativeLayout.RIGHT_OF, nombreMedicamentoTextView.getId());
                        concentracionMedicamentoTextView.setLayoutParams(concentracionMedicamentoParams);
                        relativeLayout.addView(concentracionMedicamentoTextView);

                        // Crear un nuevo TextView para el próximo recordatorio
                        TextView proximoRecordatorioTextView = new TextView(MedicamentosActivity.this);
                        proximoRecordatorioTextView.setId(View.generateViewId()); // Genera un ID único
                        for (Map.Entry<String, List<RegistroRecordatorio>> entry : grupos.entrySet()) {
                            String key2 = entry.getKey();
                            List<RegistroRecordatorio> registrosRelacionados = entry.getValue();
                            LocalDateTime fechaMasAntigua = obtenerFechaMasAntigua(registrosRelacionados, todoJunto);
                            if (fechaMasAntigua != null) {
                                // Define el formato deseado para la fecha
                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yy HH:mm");
                                String fechaFormateada = fechaMasAntigua.format(formatter);
                                proximoRecordatorioTextView.setText("Próximo recordatorio: " + fechaFormateada);
                            } else {
                                // Manejar el caso en el que no se encontró una fecha válida
                                proximoRecordatorioTextView.setText("No hay registro de próximos recordatorios");
                            }
                        }
                        proximoRecordatorioTextView.setTypeface(ResourcesCompat.getFont(MedicamentosActivity.this, R.font.inter_medium));
                        proximoRecordatorioTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 21);
                        proximoRecordatorioTextView.setTextColor(ContextCompat.getColor(MedicamentosActivity.this, R.color.gris_medical));
                        RelativeLayout.LayoutParams proximoRecordatorioParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                        proximoRecordatorioParams.addRule(RelativeLayout.BELOW, nombreMedicamentoTextView.getId());
                        proximoRecordatorioTextView.setLayoutParams(proximoRecordatorioParams);
                        relativeLayout.addView(proximoRecordatorioTextView);

                        TextView inventarioMedicamentoTextView = new TextView(MedicamentosActivity.this);
                        inventarioMedicamentoTextView.setId(View.generateViewId()); // Genera un ID único
                        for (Map.Entry<String, List<RegistroRecordatorio>> entry : grupos.entrySet()) {
                            String key2 = entry.getKey();
                            List<RegistroRecordatorio> registrosRelacionados = entry.getValue();

                            // Llama a la función para obtener el inventario más actual de estos registros relacionados
                            int inventarioMasActual = obtenerInventarioMasActual(registrosRelacionados,todoJunto);
                            String presentacion = registro.getRecordatorio().getPresentacionMed().getNombrePresentacionMed();

                            if (siEsNull == 1) {
                                inventarioMedicamentoTextView.setText("No hay registro del inventario");
                            } else if (inventarioMasActual == 0 && siEsNull != 1) {
                                if (presentacion.endsWith("a") || presentacion.endsWith("e") || presentacion.endsWith("i") || presentacion.endsWith("o") || presentacion.endsWith("u")) {
                                    inventarioMedicamentoTextView.setText("No le quedan " + presentacion + (inventarioMasActual != 1 ? "s" : ""));
                                } else if (presentacion.endsWith("n") || presentacion.endsWith("r") || presentacion.endsWith("l")) {
                                    inventarioMedicamentoTextView.setText("No le quedan " + presentacion + (inventarioMasActual != 1 ? "es" : ""));
                                } else if (presentacion.endsWith("y")) {
                                    inventarioMedicamentoTextView.setText("No le queda " + presentacion);
                                }

                            } else if (inventarioMasActual == 0 ) {
                                if (presentacion.endsWith("a") || presentacion.endsWith("e") || presentacion.endsWith("i") || presentacion.endsWith("o") || presentacion.endsWith("u")) {
                                    inventarioMedicamentoTextView.setText("No le quedan " + presentacion + (inventarioMasActual != 1 ? "s" : ""));
                                } else if (presentacion.endsWith("n") || presentacion.endsWith("r") || presentacion.endsWith("l")) {
                                    inventarioMedicamentoTextView.setText("No le quedan " + presentacion + (inventarioMasActual != 1 ? "es" : ""));
                                } else if (presentacion.endsWith("y")) {
                                    inventarioMedicamentoTextView.setText("No le queda " + presentacion);
                                }

                            }  else {
                                // Luego, puedes usar inventarioMasActual para establecer el texto en tu TextView
                                String textoInventario = "Quedan " + inventarioMasActual + " " + presentacion;

                                // Verifica si la presentación termina en vocal y agrega "s" si es necesario
                                if (presentacion.endsWith("a") || presentacion.endsWith("e") || presentacion.endsWith("i") || presentacion.endsWith("o") || presentacion.endsWith("u")) {
                                    textoInventario += "s"; // Agrega "s" al final de la presentación
                                } else if (presentacion.endsWith("n") || presentacion.endsWith("r") || presentacion.endsWith("l")) {
                                    textoInventario += "es";
                                } else if (presentacion.endsWith("y")) {
                                    textoInventario = "Queda " + inventarioMasActual + " " + presentacion;
                                }

                                inventarioMedicamentoTextView.setText(textoInventario);
                               }
                        }

                        inventarioMedicamentoTextView.setTypeface(ResourcesCompat.getFont(MedicamentosActivity.this, R.font.inter_medium));
                        inventarioMedicamentoTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 21);
                        inventarioMedicamentoTextView.setTextColor(ContextCompat.getColor(MedicamentosActivity.this, R.color.gris_medical));
                        RelativeLayout.LayoutParams inventarioParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                        inventarioParams.addRule(RelativeLayout.BELOW, proximoRecordatorioTextView.getId());
                        inventarioMedicamentoTextView.setLayoutParams(inventarioParams);
                        relativeLayout.addView(inventarioMedicamentoTextView);
                        View lineaView = new View(MedicamentosActivity.this);
                        lineaView.setId(View.generateViewId()); // Generar un ID único
                        lineaView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getResources().getDisplayMetrics())));
                        lineaView.setBackgroundColor(ContextCompat.getColor(MedicamentosActivity.this, R.color.gris_medical));
                        RelativeLayout.LayoutParams lineaParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getResources().getDisplayMetrics()));
                        lineaParams.addRule(RelativeLayout.BELOW, inventarioMedicamentoTextView.getId());
                        lineaView.setLayoutParams(lineaParams);
                        relativeLayout.addView(lineaView);
                        linearLayout.addView(relativeLayout, params);
                        aver=3;
                    }

                } else {
                    aver =2;
                }
            }

        } else {
            Log.d("MiApp", "No se encontraron registros de recordatorios en la respuesta.");
        }
        progressBar.setVisibility(View.GONE);
        dimView.setVisibility(View.GONE);
    }

    private void createTextViewNoHayActivos(int aver) {
        TextView nombreCalendarioTextView = findViewById(R.id.nombre_calendario);
        nombreCalendarioTextView.setText(calendarioSeleccionado.getNombreCalendario());
            if (aver != 3 && aca != 1) {

                LinearLayout linearLayout = findViewById(R.id.contenido);
                // Crear un nuevo TextView para el mensaje "No hay Medicamentos Activos"
                TextView noEncontradoTextView = new TextView(this);
                noEncontradoTextView.setId(View.generateViewId()); // Genera un ID único

                noEncontradoTextView.setText("No hay Medicamentos Activos");
                noEncontradoTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
                noEncontradoTextView.setTextColor(ContextCompat.getColor(this, R.color.rojoEliminar));
                noEncontradoTextView.setGravity(Gravity.CENTER);

                // Establece márgenes para el TextView
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                layoutParams.setMargins(0, (int) TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        15,
                        getResources().getDisplayMetrics()
                ), 0, (int) TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        16,
                        getResources().getDisplayMetrics()
                ));
                noEncontradoTextView.setLayoutParams(layoutParams);

                // Agrega el TextView al LinearLayout
                linearLayout.addView(noEncontradoTextView);

                aca = 1;
            }
        progressBar.setVisibility(View.GONE);
        dimView.setVisibility(View.GONE);
    }

    private LocalDateTime obtenerFechaMasAntigua(List<RegistroRecordatorio> registrosRelacionados, String todoJunto) {
        LocalDateTime fechaMasAntigua = null;
        Log.d("MiApp", "registros: " + registrosRelacionados);

        // Separar las partes de todoJunto usando guiones como delimitador
        String[] partesTodoJunto = todoJunto.split("-");

        // Verificar si hay exactamente 3 partes (nombreMedicamento, valorConcentracionProcesada, unidadMedidaCPro)
        if (partesTodoJunto.length == 3) {
            String nombreMedicamento = partesTodoJunto[0].trim();
            String valorC = partesTodoJunto[1].trim();
            String unidad = partesTodoJunto[2].trim();

            for (RegistroRecordatorio registro : registrosRelacionados) {
                LocalDateTime fechaTomaEsperada = registro.getFechaTomaEsperada();
                String registroNombreMedicamento = registro.getRecordatorio().getMedicamento().getNombreMedicamento().trim();
                String registroValorConcentracion = String.valueOf(registro.getRecordatorio().getDosis().getValorConcentracion()).trim();
                String registroUnidadMedida = registro.getRecordatorio().getDosis().getConcentracion().getUnidadMedidaC().trim();

                if (nombreMedicamento.equals(registroNombreMedicamento) &&
                        valorC.equals(registroValorConcentracion) &&
                        unidad.equals(registroUnidadMedida)) {
                    // Verificar si el nombre del medicamento, valorC y unidad en el registro coinciden con las partes de todoJunto
                    if (fechaTomaEsperada != null && (fechaMasAntigua == null || fechaTomaEsperada.isBefore(fechaMasAntigua))) {
                        fechaMasAntigua = fechaTomaEsperada;
                    }
                }
            }
        }

        return fechaMasAntigua;
    }

    private int obtenerInventarioMasActual(List<RegistroRecordatorio> registrosRelacionados, String todoJunto) {
        int inventarioMasActual = 0;

        // Separar las partes de todoJunto usando guiones como delimitador
        String[] partesTodoJunto = todoJunto.split("-");

        // Verificar si hay exactamente 3 partes (nombreMedicamento, valorConcentracionProcesada, unidadMedidaCPro)
        if (partesTodoJunto.length == 3) {
            String nombreMedicamento = partesTodoJunto[0].trim();
            String valorC = partesTodoJunto[1].trim();
            String unidad = partesTodoJunto[2].trim();

            for (RegistroRecordatorio registro : registrosRelacionados) {
                Log.d("Miapp", "entra en este22");
                LocalDateTime fechaTomaEsperada = registro.getFechaTomaEsperada();
                String registroNombreMedicamento = registro.getRecordatorio().getMedicamento().getNombreMedicamento().trim();
                String registroValorConcentracion = String.valueOf(registro.getRecordatorio().getDosis().getValorConcentracion()).trim();
                String registroUnidadMedida = registro.getRecordatorio().getDosis().getConcentracion().getUnidadMedidaC().trim();

                if (nombreMedicamento.equals(registroNombreMedicamento) &&
                        valorC.equals(registroValorConcentracion) &&
                        unidad.equals(registroUnidadMedida)) {
                    Log.d("Miapp", "entra en este");

                    Inventario inventario = registro.getRecordatorio().getInventario();

                    if (inventario != null) {
                        siEsNull =0;
                        Integer cantRealInventario = inventario.getCantRealInventario();
                        Log.d("Miapp", "aca");

                        if (cantRealInventario != null && cantRealInventario > inventarioMasActual) {
                            Log.d("Miapp", "entra en el if");
                            inventarioMasActual = cantRealInventario;
                            Log.d("Miapp", "inventarioMasAcual: "+inventarioMasActual);
                        }

                    } else {
                        siEsNull=1;
                    }
                }
            }
        }

        return inventarioMasActual;
    }

    private void verificarPermisos(Usuario usuarioLogeado) {
        perfilPermisoApi.getByCodPerfil(usuarioLogeado.getPerfil().getCodPerfil()).enqueue(new Callback<List<PerfilPermiso>>() {
            @Override
            public void onResponse(Call<List<PerfilPermiso>> call, Response<List<PerfilPermiso>> response) {
                permisos = response.body();
                codigosPermisos = permisos.stream()
                        .map(perfilPermiso -> perfilPermiso.getPermiso().getCodPermiso())
                        .collect(Collectors.toList());
                if(!codigosPermisos.contains(10)){
                    editarPerfil.setVisibility(View.GONE);
                }
                if(!codigosPermisos.contains(11)){
                    eliminarCuenta.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<PerfilPermiso>> call, Throwable t) {
                Log.d("Error en la obtencion de los permisos", "Error en permisos");
            }
        });
    }


}




