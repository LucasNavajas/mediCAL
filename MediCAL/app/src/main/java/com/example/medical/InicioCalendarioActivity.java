package com.example.medical;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medical.adapter.RegistroAdapter;
import com.example.medical.model.Calendario;
import com.example.medical.model.EstadoSolicitud;
import com.example.medical.model.Inventario;
import com.example.medical.model.Omision;
import com.example.medical.model.Recordatorio;
import com.example.medical.model.RegistroRecordatorio;
import com.example.medical.model.Solicitud;
import com.example.medical.model.Usuario;
import com.example.medical.retrofit.CalendarioApi;
import com.example.medical.retrofit.EstadoSolicitudApi;
import com.example.medical.retrofit.InventarioApi;
import com.example.medical.retrofit.OmisionApi;
import com.example.medical.retrofit.RecordatorioApi;
import com.example.medical.retrofit.RegistroRecordatorioApi;
import com.example.medical.retrofit.RetrofitService;
import com.example.medical.retrofit.SolicitudApi;
import com.example.medical.retrofit.UsuarioApi;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;

import org.json.JSONException;

import java.text.DateFormatSymbols;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class InicioCalendarioActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener {
    private LocalDate selectedDate;
    private Calendario calendarioSeleccionado;
    private TextView nombreCalendario;
    private TextView fechaHoyText;
    private RecyclerView calendarRecyclerView;
    private TextView nombreUsuario;
    private PopupWindow popupWindow;
    private LinearLayout consejos;
    private LinearLayout mas;
    private RelativeLayout editarPerfil;
    private RelativeLayout cerrarSesion;
    private RelativeLayout gestionarContactos;
    private RelativeLayout calendarioNuevo;
    private RelativeLayout restablecerContrasenia;
    private RelativeLayout contactoNuevo;
    private List<TextView> textosDia = new ArrayList<>();
    private RetrofitService retrofitService = new RetrofitService();
    private CalendarioApi calendarioApi = retrofitService.getRetrofit().create(CalendarioApi.class);
    private UsuarioApi usuarioApi = retrofitService.getRetrofit().create(UsuarioApi.class);
    private InventarioApi inventarioApi = retrofitService.getRetrofit().create(InventarioApi.class);
    private EstadoSolicitudApi estadoSolicitudApi = retrofitService.getRetrofit().create(EstadoSolicitudApi.class);
    private SolicitudApi solicitudApi = retrofitService.getRetrofit().create(SolicitudApi.class);
    private RegistroRecordatorioApi registroRecordatorioApi = retrofitService.getRetrofit().create(RegistroRecordatorioApi.class);
    private OmisionApi omisionApi = retrofitService.getRetrofit().create(OmisionApi.class);
    private List<Calendario> listaCalendarios;
    private LinearLayout contenedorCalendarios;
    private RelativeLayout soporte;
    private FirebaseUser usuario;
    private ImageView editarCalendario;
    private ImageView menuButton;
    private ImageView menuButtonUsuario;
    private ImageButton masimagen;
    private ImageButton imagenconsejos;
    private LinearLayout contenedorCalendariosContactos;
    private Button agregarRecordatorio;
    private RelativeLayout eliminarCuenta;
    private LinearLayout progressBar;
    private Handler handler = new Handler();
    private Runnable runnableCode;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private int codUsuarioLogeado;
    private Intent intent1;
    private List<EstadoSolicitud> estadosSolicitud;

    private EditText textEditCantidad;    // cantidad de recarga de inventario
    private RecyclerView recyclerView;
    private List<RegistroRecordatorio> allRegistros = new ArrayList<>();
    private List<RegistroRecordatorio> registrosDia = new ArrayList<>();
    private List<RegistroRecordatorio> registrosNotificacion = new ArrayList<>();

    private TextView textoSinRecordatorios;
    private ImageView imagenSinRecordatorios;
    private TextView perfilUsuario;
    private int codCalendarioseleccionado;
    public boolean notificacionActiva = false;
    public int notificacionInventario = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        usuario = FirebaseAuth.getInstance().getCurrentUser();
        if (usuario==null){
            Intent intent = new Intent(InicioCalendarioActivity.this, BienvenidoActivity.class); //cambiar el segundo parametro por el nombre de la actividad a probar
            startActivity(intent);
            finish();
        }
        setContentView(R.layout.n13y14_calendario_y_menu);
        progressBar = findViewById(R.id.progressBar);
        selectedDate = LocalDate.now();
        intent1 = getIntent();
        try {
            initWidgets();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
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

        calendarioNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InicioCalendarioActivity.this, CrearCalendario1Activity.class);
                startActivity(intent);
            }
        });
        editarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InicioCalendarioActivity.this, EditarPerfilUsuarioActivity.class);
                intent.putExtra("codUsuario", codUsuarioLogeado);
                startActivity(intent);
            }
        });
        soporte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InicioCalendarioActivity.this, FAQActivity.class);
                startActivity(intent);
            }
        });

        editarCalendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InicioCalendarioActivity.this, EditarCalendarioActivity.class);
                String jsonCalendario = new Gson().toJson(calendarioSeleccionado);
                intent.putExtra("calendarioJson", jsonCalendario);
                startActivity(intent);
            }
        });

        restablecerContrasenia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InicioCalendarioActivity.this, RestablecerContraseniaActivity.class);
                intent.putExtra("codUsuario", codUsuarioLogeado);
                startActivity(intent);

            }
        });

        contactoNuevo.setOnClickListener(view -> {
            Intent intent = new Intent(InicioCalendarioActivity.this, NuevoContactoActivity.class);
            intent.putExtra("codUsuario", codUsuarioLogeado);
            startActivity(intent);
        } );
        eliminarCuenta.setOnClickListener(view ->{
            popupEliminarCuenta();
        });
        gestionarContactos.setOnClickListener(view ->{
            Intent intent = new Intent(InicioCalendarioActivity.this, GestionarContactosActivity.class);
            intent.putExtra("codUsuario",codUsuarioLogeado);
            startActivity(intent);
        });

        agregarRecordatorio.setOnClickListener(view ->{
            Intent intent = new Intent(InicioCalendarioActivity.this, ElegirMedicamentoActivity.class);
            intent.putExtra("codUsuario",codUsuarioLogeado);
            intent.putExtra("codCalendario", calendarioSeleccionado.getCodCalendario());
            startActivity(intent);
        });

        // Botones de la Barra Inferior
        consejos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InicioCalendarioActivity.this, ConsejosActivity.class);
                intent.putExtra("codUsuario", codUsuarioLogeado);
                intent.putExtra("calendarioSeleccionadoid", calendarioSeleccionado.getCodCalendario());
                startActivity(intent);
            }
        });

        mas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InicioCalendarioActivity.this, MasActivity.class);
                intent.putExtra("codUsuario", codUsuarioLogeado);
                intent.putExtra("calendarioSeleccionadoid", calendarioSeleccionado.getCodCalendario());
                startActivity(intent);
            }
        });

        masimagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InicioCalendarioActivity.this, MasActivity.class);
                intent.putExtra("codUsuario", codUsuarioLogeado);
                intent.putExtra("calendarioSeleccionadoid", calendarioSeleccionado.getCodCalendario());
                startActivity(intent);
            }
        });

        imagenconsejos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InicioCalendarioActivity.this, ConsejosActivity.class);
                intent.putExtra("codUsuario", codUsuarioLogeado);
                intent.putExtra("calendarioSeleccionadoid", calendarioSeleccionado.getCodCalendario());
                startActivity(intent);

            }
        });

    }



    private void mostrarCalendarioSeleccionado() {
        if (listaCalendarios != null && !listaCalendarios.isEmpty()) {
            codCalendarioseleccionado = listaCalendarios.get(0).getCodCalendario();
            if (intent1.getIntExtra("codCalendario", 0) != 0) {
                codCalendarioseleccionado = intent1.getIntExtra("codCalendario", 0);
            }
            //Codigo para refrescar notificaciones cada minuto, es decir que si tenes la app abierta y deberia aparecer una notificacion que lo haga
            runnableCode = new Runnable() {
                @Override
                public void run() {
                    // Coloca aquí el código que deseas ejecutar cada 15 seg
                    loadNotificaciones(codCalendarioseleccionado);

                    // Programa la próxima ejecución después de 15 segundos
                    handler.postDelayed(this, 15 * 1000); // 15 segundos * 1000 ms
                }
            };

            // Inicia la ejecución inicial después de 5 segundos
            handler.postDelayed(runnableCode, 15 * 1000); // 15 segundos * 1000 ms

            calendarioApi.getByCodCalendario(codCalendarioseleccionado).enqueue(new Callback<Calendario>() {
                @Override
                public void onResponse(Call<Calendario> call, Response<Calendario> response) {
                    calendarioSeleccionado = response.body();
                    loadRegistros();
                    nombreCalendario.setText(calendarioSeleccionado.getNombreCalendario());

                    // Salto de Popup en caso de que el calendario seleccionado tenga algún recordatorio con inventarios con cantReal <= cantAviso
                    Log.d("MiApp", "Se buscarán recordatorios asociados al calendario: " + calendarioSeleccionado);
                    obtenerRecordatoriosPorCalendario(calendarioSeleccionado);
                }

                @Override
                public void onFailure(Call<Calendario> call, Throwable t) {
                    Toast.makeText(InicioCalendarioActivity.this, "Hubo un error al cargar el calendario, intente nuevamente", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else {
            Intent intent = new Intent(InicioCalendarioActivity.this, BienvenidoUsuarioActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }

    }

    public void loadNotificaciones(int codCalendario) {
        registroRecordatorioApi.obtenerRegistrosCalendarioNotificacion(codCalendario).enqueue(new Callback<List<RegistroRecordatorio>>() {
            @Override
            public void onResponse(Call<List<RegistroRecordatorio>> call, Response<List<RegistroRecordatorio>> response) {
                registrosNotificacion = response.body();
                if(registrosNotificacion != null && registrosNotificacion.size()>0) {
                    popUpNotificacion(registrosNotificacion.get(0));
                    registrosNotificacion.remove(0);
                }
            }

            @Override
            public void onFailure(Call<List<RegistroRecordatorio>> call, Throwable t) {

            }
        });
    }

    private void popUpNotificacion(RegistroRecordatorio registroRecordatorio) {
        if(notificacionActiva==false) {
            notificacionActiva = true;
            View popupView = getLayoutInflater().inflate(R.layout.n55_0_notificacion_recordatorio, null);

            // Crear la instancia de PopupWindow
            PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

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


            TextView hora = popupView.findViewById(R.id.text_hora);
            TextView nombreMedicamento = popupView.findViewById(R.id.text_nombremedicamento);
            TextView cpc = popupView.findViewById(R.id.text_CPC);
            TextView instrucciones = popupView.findViewById(R.id.text_instrucciones);
            TextView indicaciones = popupView.findViewById(R.id.text_otras_instrucciones);
            ImageView omitir = popupView.findViewById(R.id.omitir);
            ImageView tomar = popupView.findViewById(R.id.aceptar);
            ImageView aplazar = popupView.findViewById(R.id.aplazar);
            Recordatorio recordatorio = registroRecordatorio.getRecordatorio();
            String presentacionString = "";
            presentacionString = Float.toString(recordatorio.getDosis().getCantidadDosis()) + " "
                    + registroRecordatorio.getRecordatorio().getPresentacionMed().getNombrePresentacionMed() + " "
                    + Float.toString(recordatorio.getDosis().getValorConcentracion()) + " "
                    + recordatorio.getDosis().getConcentracion().getUnidadMedidaC();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            if (registroRecordatorio.getRecordatorio().getFrecuencia() == null && registroRecordatorio.getFechaTomaEsperada().getHour() == 0 && registroRecordatorio.getFechaTomaEsperada().getMinute() == 0) {
                hora.setText("Cuando sea necesario");
            } else {
                hora.setText(registroRecordatorio.getFechaTomaEsperada().toLocalTime().format(formatter).toString());
            }
            nombreMedicamento.setText(recordatorio.getMedicamento().getNombreMedicamento());
            cpc.setText(presentacionString);
            instrucciones.setText(recordatorio.getInstruccion().getNombreInstruccion());
            if (recordatorio.getInstruccion().getDescInstruccion().equals("")) {
                indicaciones.setVisibility(View.GONE);
            }
            indicaciones.setText(recordatorio.getInstruccion().getDescInstruccion());

            omitir.setOnClickListener(view -> {
                popupWindow.dismiss();
                if (registroRecordatorio.getRecordatorio().getFrecuencia() != null && registroRecordatorio.getFechaTomaEsperada().getHour() != 0 && registroRecordatorio.getFechaTomaEsperada().getMinute() != 0) {
                    popUpOmitir(registroRecordatorio);
                }
            });

            tomar.setOnClickListener(view -> {
                if (registroRecordatorio.isTomaRegistroRecordatorio()==false) {
                    if (registroRecordatorio.getRecordatorio().getFrecuencia() == null && registroRecordatorio.getFechaTomaEsperada().getHour() == 0 && registroRecordatorio.getFechaTomaEsperada().getMinute() == 0) {
                        RegistroRecordatorio registroNuevo = new RegistroRecordatorio();
                        registroNuevo.setRecordatorio(registroRecordatorio.getRecordatorio());
                        registroNuevo.setFechaTomaEsperada(LocalDateTime.now());
                        registroNuevo.setFechaTomaReal(LocalDateTime.now());
                        registroNuevo.setTomaRegistroRecordatorio(true);
                        disminuirInventario(registroRecordatorio.getRecordatorio());
                        registroRecordatorioApi.save(registroNuevo).enqueue(new Callback<RegistroRecordatorio>() {
                            @Override
                            public void onResponse(Call<RegistroRecordatorio> call, Response<RegistroRecordatorio> response) {
                                popupWindow.dismiss();
                                dimView.setVisibility(View.GONE);
                                Intent intent = new Intent(InicioCalendarioActivity.this, InicioCalendarioActivity.class);
                                intent.putExtra("codCalendario", codCalendarioseleccionado);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }

                            @Override
                            public void onFailure(Call<RegistroRecordatorio> call, Throwable t) {
                                popupWindow.dismiss();
                                dimView.setVisibility(View.GONE);
                                Toast.makeText(InicioCalendarioActivity.this, "Error al registrar la toma", Toast.LENGTH_SHORT).show();
                            }
                        });

                    } else {
                        registroRecordatorio.setFechaTomaReal(LocalDateTime.now());
                        registroRecordatorio.setTomaRegistroRecordatorio(true);
                        disminuirInventario(registroRecordatorio.getRecordatorio());
                        registroRecordatorioApi.save(registroRecordatorio).enqueue(new Callback<RegistroRecordatorio>() {
                            @Override
                            public void onResponse(Call<RegistroRecordatorio> call, Response<RegistroRecordatorio> response) {
                                popupWindow.dismiss();
                                dimView.setVisibility(View.GONE);
                                Intent intent = new Intent(InicioCalendarioActivity.this, InicioCalendarioActivity.class);
                                intent.putExtra("codCalendario", codCalendarioseleccionado);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }

                            @Override
                            public void onFailure(Call<RegistroRecordatorio> call, Throwable t) {
                                popupWindow.dismiss();
                                dimView.setVisibility(View.GONE);
                                Toast.makeText(InicioCalendarioActivity.this, "Error al registrar la toma", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
                else{
                    popupWindow.dismiss();
                    dimView.setVisibility(View.GONE);
                }
            });

            aplazar.setOnClickListener(view -> {
                if (registroRecordatorio.getRecordatorio().getFrecuencia() == null && registroRecordatorio.getFechaTomaEsperada().getHour() == 0 && registroRecordatorio.getFechaTomaEsperada().getMinute() == 0) {
                    RegistroRecordatorio registroNuevo = new RegistroRecordatorio();
                    registroNuevo.setRecordatorio(registroRecordatorio.getRecordatorio());
                    registroRecordatorioApi.save(registroNuevo).enqueue(new Callback<RegistroRecordatorio>() {
                        @Override
                        public void onResponse(Call<RegistroRecordatorio> call, Response<RegistroRecordatorio> response) {
                            popupWindow.dismiss();
                            popUpAplazar(response.body());
                        }

                        @Override
                        public void onFailure(Call<RegistroRecordatorio> call, Throwable t) {
                            popupWindow.dismiss();
                            dimView.setVisibility(View.GONE);
                            Toast.makeText(InicioCalendarioActivity.this, "Error al registrar el aplazo", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else{
                    popupWindow.dismiss();
                    popUpAplazar(registroRecordatorio);
                }
            });


            // Configurar el OnTouchListener para la vista oscura
            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    dimView.setVisibility(View.GONE);
                    notificacionActiva = false;
                }
            });
        }
    }

    private void disminuirInventario(Recordatorio recordatorio) {
        if(recordatorio.getInventario()!=null) {
            if(recordatorio.getInventario().getCantRealInventario()>0) {
                Inventario inventario = recordatorio.getInventario();
                int cantInventario = recordatorio.getInventario().getCantRealInventario();
                if (cantInventario<(int) recordatorio.getDosis().getCantidadDosis()){
                    cantInventario = 0;
                }
                else {
                    cantInventario = cantInventario - (int) recordatorio.getDosis().getCantidadDosis();
                }
                inventario.setCantRealInventario(cantInventario);
                inventarioApi.save(inventario).enqueue(new Callback<Inventario>() {
                    @Override
                    public void onResponse(Call<Inventario> call, Response<Inventario> response) {
                    }

                    @Override
                    public void onFailure(Call<Inventario> call, Throwable t) {
                        Toast.makeText(InicioCalendarioActivity.this, "Error al disminuir el inventario", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }

    }

    private void popUpAplazar(RegistroRecordatorio registroRecordatorio) {
        View popupView = getLayoutInflater().inflate(R.layout.n55_1_popup_aplazar, null);

        // Crear la instancia de PopupWindow
        PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

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
        TextView cancelar = popupView.findViewById(R.id.cancelar);
        TextView aceptar = popupView.findViewById(R.id.aceptar);
        TextView cincoMin = popupView.findViewById(R.id.text_cinco_min);
        TextView diezMin = popupView.findViewById(R.id.text_diez_min);
        TextView quinceMin = popupView.findViewById(R.id.text_quince_min);
        TextView treintaMin = popupView.findViewById(R.id.text_treinta_min);
        TextView sesentaMin = popupView.findViewById(R.id.text_sesenta_min);

        cincoMin.setOnClickListener(view->{seleccionarOpcion(cincoMin);});
        diezMin.setOnClickListener(view->{seleccionarOpcion(diezMin);});
        quinceMin.setOnClickListener(view->{seleccionarOpcion(quinceMin);});
        treintaMin.setOnClickListener(view->{seleccionarOpcion(treintaMin);});
        sesentaMin.setOnClickListener(view->{seleccionarOpcion(sesentaMin);});

        aceptar.setOnClickListener(view ->{
            // Obtiene la fecha y hora actual
            LocalDateTime now = LocalDateTime.now();

            LocalDateTime adjustedTime = now.withSecond(0).withNano(0);
            switch(opcionSeleccionada.getId()){

                case R.id.text_diez_min:
                    popupWindow.dismiss();
                    registroRecordatorio.setFechaTomaEsperada(adjustedTime.plusMinutes(10));
                    aplazarRegistro(popupWindow, dimView, registroRecordatorio, 10);
                    break;
                case R.id.text_quince_min:
                    popupWindow.dismiss();
                    registroRecordatorio.setFechaTomaEsperada(adjustedTime.plusMinutes(15));
                    aplazarRegistro(popupWindow, dimView, registroRecordatorio, 15);
                    break;
                case R.id.text_treinta_min:
                    popupWindow.dismiss();
                    registroRecordatorio.setFechaTomaEsperada(adjustedTime.plusMinutes(30));
                    aplazarRegistro(popupWindow, dimView, registroRecordatorio, 30);
                    break;
                case R.id.text_sesenta_min:
                    popupWindow.dismiss();
                    registroRecordatorio.setFechaTomaEsperada(adjustedTime.plusMinutes(60));
                    aplazarRegistro(popupWindow, dimView, registroRecordatorio, 60);
                    break;
                default:
                    popupWindow.dismiss();
                    registroRecordatorio.setFechaTomaEsperada(adjustedTime.plusMinutes(5));
                    aplazarRegistro(popupWindow, dimView, registroRecordatorio, 5);
                    break;
            }
        });
        cancelar.setOnClickListener(view ->{
            popupWindow.dismiss();
            popUpNotificacion(registroRecordatorio);
        });

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                dimView.setVisibility(View.GONE);
            }
        });
    }

    private void aplazarRegistro(PopupWindow popupWindow, View dimView, RegistroRecordatorio registro, int minutos) {
        registroRecordatorioApi.save(registro).enqueue(new Callback<RegistroRecordatorio>() {
            @Override
            public void onResponse(Call<RegistroRecordatorio> call, Response<RegistroRecordatorio> response) {
                popupWindow.dismiss();
                popUpAplazado(registro, minutos);
            }

            @Override
            public void onFailure(Call<RegistroRecordatorio> call, Throwable t) {
                Toast.makeText(InicioCalendarioActivity.this, "Error al guardar el aplazo del registro", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void popUpAplazado(RegistroRecordatorio registro, int minutos) {
        View popupView = getLayoutInflater().inflate(R.layout.n55_2_aplazo_exitoso, null);

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
        ImageView botonCerrar = popupView.findViewById(R.id.boton_cerrar);
        TextView nombreMedicamento = popupView.findViewById(R.id.text_nombremedicamento);
        TextView textoAplazado = popupView.findViewById(R.id.text_tiempoaplazo);
        nombreMedicamento.setText(registro.getRecordatorio().getMedicamento().getNombreMedicamento());
        textoAplazado.setText("Aplazado por "+minutos+" min");
        botonCerrar.setOnClickListener(view ->{
            popupWindow.dismiss();
            dimView.setVisibility(View.GONE);
            Intent intent = new Intent(InicioCalendarioActivity.this, InicioCalendarioActivity.class);
            intent.putExtra("codCalendario", codCalendarioseleccionado);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                dimView.setVisibility(View.GONE);
                Intent intent = new Intent(InicioCalendarioActivity.this, InicioCalendarioActivity.class);
                intent.putExtra("codCalendario", codCalendarioseleccionado);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }


    private TextView opcionSeleccionada = null;

    private void seleccionarOpcion(TextView opcion) {
        if (opcionSeleccionada != null) {
            opcionSeleccionada.setBackgroundResource(android.R.color.transparent);
        }

        opcion.setBackgroundResource(R.color.verdeSelector);
        opcionSeleccionada = opcion;
    }

    private void popUpOmitir(RegistroRecordatorio registroRecordatorio) {
        View popupView = getLayoutInflater().inflate(R.layout.n55_3_popup_por_que_se_omitio, null);

        // Crear la instancia de PopupWindow
        PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

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
        RadioGroup radioGroup = popupView.findViewById(R.id.radio_group);
        TextView cancelar = popupView.findViewById(R.id.cancelar);
        TextView aceptar = popupView.findViewById(R.id.aceptar);

        cancelar.setOnClickListener(view ->{
            popupWindow.dismiss();
            popUpNotificacion(registroRecordatorio);
        });
        aceptar.setOnClickListener(view ->{
            int radioButtonId = radioGroup.getCheckedRadioButtonId();

            if (radioButtonId != -1) {
                RadioButton radioButton = popupView.findViewById(radioButtonId);
                String textoSeleccionado = radioButton.getText().toString();
                Omision omision = new Omision();
                omision.setNombreOmision(textoSeleccionado);
                omisionApi.save(omision).enqueue(new Callback<Omision>() {
                    @Override
                    public void onResponse(Call<Omision> call, Response<Omision> response) {
                        registroRecordatorio.setOmision(response.body());
                        registroRecordatorioApi.save(registroRecordatorio).enqueue(new Callback<RegistroRecordatorio>() {
                            @Override
                            public void onResponse(Call<RegistroRecordatorio> call, Response<RegistroRecordatorio> response) {
                                popupWindow.dismiss();
                                dimView.setVisibility(View.GONE);
                                Intent intent = new Intent(InicioCalendarioActivity.this, InicioCalendarioActivity.class);
                                intent.putExtra("codCalendario", codCalendarioseleccionado);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }

                            @Override
                            public void onFailure(Call<RegistroRecordatorio> call, Throwable t) {
                                Toast.makeText(InicioCalendarioActivity.this, "Error al guardar el registro", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<Omision> call, Throwable t) {
                        Toast.makeText(InicioCalendarioActivity.this, "Error al crear la omisión", Toast.LENGTH_SHORT).show();
                    }
                });

            } else {
                Toast.makeText(InicioCalendarioActivity.this, "Debe seleccionar una opción", Toast.LENGTH_LONG).show();
            }
        });
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                dimView.setVisibility(View.GONE);
            }
        });

    }


    private void initWidgets() throws JSONException {
        calendarRecyclerView = findViewById(R.id.calendar_recycler_view);
        fechaHoyText = findViewById(R.id.fecha_hoy_text);
        textosDia.add(findViewById(R.id.lunes));
        textosDia.add(findViewById(R.id.martes));
        textosDia.add(findViewById(R.id.miercoles));
        textosDia.add(findViewById(R.id.jueves));
        textosDia.add(findViewById(R.id.viernes));
        textosDia.add(findViewById(R.id.sabado));
        textosDia.add(findViewById(R.id.domingo));
        cerrarSesion = findViewById(R.id.cerrar_sesion);
        calendarioNuevo = findViewById(R.id.calendario_nuevo);
        contenedorCalendarios = findViewById(R.id.contenedor_calendarios);
        nombreUsuario = findViewById(R.id.nombre_usuario);
        consejos = findViewById(R.id.consejos);
        imagenconsejos =findViewById(R.id.imagen_consejos);
        mas = findViewById(R.id.mas);
        masimagen = findViewById(R.id.masimagen);
        editarPerfil = findViewById(R.id.editar_perfil);
        soporte = findViewById(R.id.soporte);
        nombreCalendario = findViewById(R.id.nombre_calendario);
        editarCalendario = findViewById(R.id.editar_calendario);
        restablecerContrasenia = findViewById(R.id.restablecer_contrasenia);
        menuButton = findViewById(R.id.menu_button);
        menuButtonUsuario = findViewById(R.id.menu_button_nav);
        contactoNuevo = findViewById(R.id.contacto_nuevo);
        contenedorCalendariosContactos = findViewById(R.id.contenedor_calendarios_contactos);
        eliminarCuenta = findViewById(R.id.eliminar_cuenta);
        gestionarContactos = findViewById(R.id.gestionar_contactos);
        agregarRecordatorio = findViewById(R.id.agregar_recordatorio);
        recyclerView = findViewById(R.id.listarecordatorios_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        imagenSinRecordatorios = findViewById(R.id.imagen_sin_recordatorios);
        textoSinRecordatorios = findViewById(R.id.texto_sin_recordatorios);
        perfilUsuario = findViewById(R.id.perfil_usuario);
        llenarListaCalendarios();



    }

    private void loadRegistros() {
        registroRecordatorioApi.obtenerRegistrosCalendario(calendarioSeleccionado.getCodCalendario()).enqueue(new Callback<List<RegistroRecordatorio>>() {
            @Override
            public void onResponse(Call<List<RegistroRecordatorio>> call, Response<List<RegistroRecordatorio>> response) {
                allRegistros = response.body();
                setView();
                progressBar.setVisibility(View.GONE);
                View dimView = findViewById(R.id.dim_view);
                dimView.setVisibility(View.GONE);
                loadNotificaciones(codCalendarioseleccionado);
            }

            @Override
            public void onFailure(Call<List<RegistroRecordatorio>> call, Throwable t) {
                Toast.makeText(InicioCalendarioActivity.this, "Hubo un error al cargar los registros de recordatorio, intente nuevamente", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void llenarEstadosSolicitud() {
        estadoSolicitudApi.getAllEstadoSolicitud().enqueue(new Callback<List<EstadoSolicitud>>() {
            @Override
            public void onResponse(Call<List<EstadoSolicitud>> call, Response<List<EstadoSolicitud>> response) {
               // handler.postDelayed(runnable, 5000); Para probar como funciona en caso de que llegue rápido la consulta
                estadosSolicitud = response.body();
                habilitarBotones();
            }

            @Override
            public void onFailure(Call<List<EstadoSolicitud>> call, Throwable t) {
                Toast.makeText(InicioCalendarioActivity.this, "Error al cargar los estados de solicitudes", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void setAllViewsEnabled(ViewGroup viewGroup, boolean enabled) {
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = viewGroup.getChildAt(i);

            // Deshabilitar vista si es interactuable (Button, EditText, CheckBox, RadioButton)
            if (view instanceof Button || view instanceof EditText) {
                view.setEnabled(enabled);
            }

            // Si es un ImageView, también puedes deshabilitarlo si es necesario
            if (view instanceof ImageView) {
                view.setEnabled(enabled);
            }

            // Si es un TextView, también puedes deshabilitarlo si es necesario
            if (view instanceof TextView) {
                view.setEnabled(enabled);
            }

            // Si es un ViewGroup (por ejemplo, LinearLayout, RelativeLayout, etc.), llama de forma recursiva
            if (view instanceof ViewGroup) {
                setAllViewsEnabled((ViewGroup) view, enabled);
            }
        }
    }
    public void habilitarBotones(){
        ViewGroup layout = findViewById(R.id.drawer_layout);
        setAllViewsEnabled(layout, true); // Deshabilita todas las vistas
    }
    public void inhabilitarBotones(){
        ViewGroup layout = findViewById(R.id.drawer_layout);
        setAllViewsEnabled(layout, false); // Deshabilita todas las vistas
    }
    private void llenarContenedorCalendarios() {
        LinearLayout.LayoutParams imageLayoutParams = new LinearLayout.LayoutParams(
                dpToPx(50),
                dpToPx(50)
        );
        imageLayoutParams.setMargins(dpToPx(20), dpToPx(5), 0, 0);

        for (Calendario calendario : listaCalendarios) {
            RelativeLayout layoutCalendario = new RelativeLayout(this);

            ImageView imagenCalendario = new ImageView(this);
            imagenCalendario.setImageResource(R.drawable.calendario_contic);
            imagenCalendario.setLayoutParams(imageLayoutParams);
            imagenCalendario.setId(View.generateViewId()); // Asignar un ID único a la imagen

            TextView textoCalendario = new TextView(this);
            textoCalendario.setText(calendario.getNombreCalendario());
            textoCalendario.setTextSize(20);
            textoCalendario.setTextColor(getResources().getColor(R.color.black, null));
            textoCalendario.setTypeface(ResourcesCompat.getFont(this, R.font.inter_regular));

            RelativeLayout.LayoutParams textLayoutParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            );

            textLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
            textLayoutParams.addRule(RelativeLayout.END_OF, imagenCalendario.getId());
            textLayoutParams.setMarginStart(dpToPx(10));
            textoCalendario.setLayoutParams(textLayoutParams);

            layoutCalendario.addView(imagenCalendario);
            layoutCalendario.addView(textoCalendario);

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, dpToPx(10), 0, 0);
            layoutCalendario.setLayoutParams(params);
            layoutCalendario.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(InicioCalendarioActivity.this, InicioCalendarioActivity.class);
                    intent.putExtra("codCalendario", calendario.getCodCalendario());
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish(); // Opcional: finaliza la actividad actual si ya no la necesitas en el back stack
                }
            });

            contenedorCalendarios.addView(layoutCalendario);
        }
    }
    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }

    private void llenarListaCalendarios() {
        progressBar.setVisibility(View.VISIBLE);
        View dimView = findViewById(R.id.dim_view);
        dimView.setVisibility(View.VISIBLE);
        inhabilitarBotones();
        String mail = usuario.getEmail();
        usuarioApi.getByMailUsuario(mail).enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                Usuario usuarioLogeado = response.body();
                if(usuarioLogeado.getPerfil()!=null) {
                    perfilUsuario.setText(usuarioLogeado.getPerfil().getNombrePerfil());
                }
                nombreUsuario.setText(usuarioLogeado.getUsuarioUnico());
                codUsuarioLogeado = usuarioLogeado.getCodUsuario();
                setTokenUsuario();
                llenarEstadosSolicitud();
                buscarSolicitudPendiente();
                buscarRespuestasSolicitudes();
                buscarContactosVinculados();
                calendarioApi.getByCodUsuario(usuarioLogeado.getCodUsuario()).enqueue(new Callback<List<Calendario>>() {
                    @Override
                    public void onResponse(Call<List<Calendario>> call, Response<List<Calendario>> response) {
                        listaCalendarios = response.body();
                        if(listaCalendarios.size()>=3){
                            calendarioNuevo.setVisibility(View.GONE);
                        }
                        else{
                            calendarioNuevo.setVisibility(View.VISIBLE);

                        }
                        llenarContenedorCalendarios();
                        mostrarCalendarioSeleccionado();
                    }

                    @Override
                    public void onFailure(Call<List<Calendario>> call, Throwable t) {
                        Toast.makeText(InicioCalendarioActivity.this, "Hubo un error al obtener calendarios, intente nuevamente", Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Toast.makeText(InicioCalendarioActivity.this, "Hubo un error al iniciar sesión, intente nuevamente", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setTokenUsuario() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String token = task.getResult();
                        // Aquí tienes el token actual
                        Log.d("Token", token);
                        usuarioApi.modificarToken(codUsuarioLogeado, token).enqueue(new Callback<Usuario>() {
                            @Override
                            public void onResponse(Call<Usuario> call, Response<Usuario> response) {

                            }

                            @Override
                            public void onFailure(Call<Usuario> call, Throwable t) {
                                Toast.makeText(InicioCalendarioActivity.this, "Hubo un error al modificar el token, intente nuevamente", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        // Si no se puede obtener el token, maneja el error aquí
                        Log.e("Token", "Error al obtener el token", task.getException());
                    }
                });
    }

    private void buscarContactosVinculados() {
        solicitudApi.obtenerContactos(codUsuarioLogeado).enqueue(new Callback<List<Solicitud>>() {
            @Override
            public void onResponse(Call<List<Solicitud>> call, Response<List<Solicitud>> response) {
                List<Solicitud> solicitudes = response.body();
                if(solicitudes.size()>=3){
                    contactoNuevo.setVisibility(View.GONE);
                }
                else {
                    contactoNuevo.setVisibility(View.VISIBLE);
                }
                for (Solicitud solicitud: solicitudes){
                    calendarioApi.getByCodUsuario(solicitud.getUsuarioControlado().getCodUsuario()).enqueue(new Callback<List<Calendario>>() {
                        @Override
                        public void onResponse(Call<List<Calendario>> call, Response<List<Calendario>> response) {
                            llenarCalendariosContactos(response.body());
                        }

                        @Override
                        public void onFailure(Call<List<Calendario>> call, Throwable t) {}//No tienen onfailure porque si un usuario no tiene contactos va a saltar el onfailure por mas que ande la BD
                    });
                }
            }

            @Override
            public void onFailure(Call<List<Solicitud>> call, Throwable t) {}//No tienen onfailure porque si un usuario no tiene contactos va a saltar el onfailure por mas que ande la BD
        });
    }

    private void llenarCalendariosContactos(List<Calendario> calendarios) {
        RelativeLayout.LayoutParams imageLayoutParams = new RelativeLayout.LayoutParams(
                dpToPx(50),
                dpToPx(50)
        );
        imageLayoutParams.setMargins(dpToPx(20), dpToPx(5), 0, 0);

        for (Calendario calendario : calendarios) {
            RelativeLayout layoutCalendario = new RelativeLayout(this);
            layoutCalendario.setId(View.generateViewId()); // Asignar un ID único al RelativeLayout

            ImageView imagenCalendario = new ImageView(this);
            imagenCalendario.setImageResource(R.drawable.calendario_contic);
            imagenCalendario.setLayoutParams(imageLayoutParams);
            imagenCalendario.setId(View.generateViewId());

            TextView textoNombreContacto = new TextView(this);
            textoNombreContacto.setText(calendario.getUsuario().getUsuarioUnico());
            textoNombreContacto.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            textoNombreContacto.setTextColor(getResources().getColor(R.color.black, null));
            textoNombreContacto.setTypeface(ResourcesCompat.getFont(this, R.font.inter_regular));
            textoNombreContacto.setId(View.generateViewId());

            TextView textoNombreCalendario = new TextView(this);
            textoNombreCalendario.setText(calendario.getNombreCalendario());
            textoNombreCalendario.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            textoNombreCalendario.setTextColor(getResources().getColor(R.color.black, null));
            textoNombreCalendario.setTypeface(ResourcesCompat.getFont(this, R.font.inter_regular));
            textoNombreCalendario.setId(View.generateViewId());

            RelativeLayout.LayoutParams textNombreContactoLayoutParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            );
            textNombreContactoLayoutParams.addRule(RelativeLayout.END_OF, imagenCalendario.getId());
            textNombreContactoLayoutParams.setMarginStart(dpToPx(10));
            textNombreContactoLayoutParams.topMargin=dpToPx(10);
            textoNombreContacto.setLayoutParams(textNombreContactoLayoutParams);

            RelativeLayout.LayoutParams textNombreCalendarioLayoutParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            );
            textNombreCalendarioLayoutParams.addRule(RelativeLayout.BELOW, textoNombreContacto.getId());
            textNombreCalendarioLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
            textNombreCalendarioLayoutParams.addRule(RelativeLayout.ALIGN_START, textoNombreContacto.getId());
            textNombreCalendarioLayoutParams.topMargin=dpToPx(-5);
            textoNombreCalendario.setLayoutParams(textNombreCalendarioLayoutParams);

            layoutCalendario.addView(imagenCalendario);
            layoutCalendario.addView(textoNombreContacto);
            layoutCalendario.addView(textoNombreCalendario);

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, dpToPx(10), 0, 0);
            layoutCalendario.setLayoutParams(params);

            layoutCalendario.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(InicioCalendarioActivity.this, InicioCalendarioActivity.class);
                    intent.putExtra("codCalendario", calendario.getCodCalendario());
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }
            });

            contenedorCalendariosContactos.addView(layoutCalendario);
        }
    }


    private void setView() {
        fechaHoyText.setText(mesDiaFromDate(selectedDate));
        ArrayList<LocalDate> daysInWeek = daysInWeekArray(selectedDate);
        Context context = InicioCalendarioActivity.this;
        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInWeek, this, selectedDate, textosDia, context);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
        registrosDia = new ArrayList<>();
        for(RegistroRecordatorio registro : allRegistros) {
            if (selectedDate.equals(registro.getFechaTomaEsperada().toLocalDate())) {
                registrosDia.add(registro);
            }
        }
        if(registrosDia.size()!=0){
            textoSinRecordatorios.setVisibility(View.GONE);
            imagenSinRecordatorios.setVisibility(View.GONE);
        }
        else{
            textoSinRecordatorios.setVisibility(View.VISIBLE);
            imagenSinRecordatorios.setVisibility(View.VISIBLE);
        }
        RegistroAdapter registroAdapter = new RegistroAdapter(registrosDia, this);
        recyclerView.setAdapter(registroAdapter);
        registroAdapter.setOnItemClickListener(new RegistroAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RegistroRecordatorio registro) {
                // Llama al método popUpNotificacion con el objeto RegistroRecordatorio
                popUpNotificacion(registro);
            }
        });

    }

    public void semanaPrevia(View view) {
        selectedDate = selectedDate.minusWeeks(1);
        setView();
    }

    public void semanaPosterior(View view) {
        selectedDate = selectedDate.plusWeeks(1);
        setView();
    }

    private String mesDiaFromDate(LocalDate date) {
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);
        LocalDate yesterday = today.minusDays(1);

        String dayString;
        if (date.isEqual(today)) {
            dayString = "Hoy, ";
        } else if (date.isEqual(yesterday)) {
            dayString = "Ayer, ";
        } else if (date.isEqual(tomorrow)) {
            dayString = "Mañana, ";
        } else {
            dayString = "";
        }

        String month = new DateFormatSymbols(new Locale("es")).getMonths()[date.getMonthValue() - 1];
        String formattedDate = dayString +  + date.getDayOfMonth() + " de " + month + ", " + date.getYear();
        return formattedDate;
    }


    private ArrayList<LocalDate> daysInWeekArray(LocalDate selectedDate) {
        ArrayList<LocalDate> dias = new ArrayList<>();
        LocalDate actual = sundayForDate(selectedDate);
        LocalDate fechaFin = actual.plusWeeks(1);

        while(actual.isBefore(fechaFin)){
            dias.add(actual);
            actual = actual.plusDays(1);
        }

        return dias;
    }

    private LocalDate sundayForDate(LocalDate actual) {

        LocalDate haceUnaSemana = actual.minusWeeks(1);

        while(actual.isAfter(haceUnaSemana)){
            if(actual.getDayOfWeek() == DayOfWeek.SUNDAY)
                return actual;

            actual = actual.minusDays(1);
        }
        return null;
    }

    @Override
    public void onItemClick(int position, LocalDate date) {
        selectedDate = date;
        setView();
    }

    @Override
    public void onBackPressed(){}

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
                Intent intent = new Intent(InicioCalendarioActivity.this, BienvenidoActivity.class);
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

    @Override
    protected void onPause() {
        super.onPause();
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
    }

    private void buscarSolicitudPendiente() {
        solicitudApi.obtenerSolicitudPendiente(codUsuarioLogeado).enqueue(new Callback<Solicitud>() {
            @Override
            public void onResponse(Call<Solicitud> call, Response<Solicitud> response) {
                if(response.body()!=null){
                    popupSolicitudPendiente(response.body());
                }
            }
            @Override
            public void onFailure(Call<Solicitud> call, Throwable t) {}
        });
    }

    private void buscarRespuestasSolicitudes() {
        solicitudApi.obtenerRespuestasSolicitud(codUsuarioLogeado).enqueue(new Callback<List<Solicitud>>() {
            @Override
            public void onResponse(Call<List<Solicitud>> call, Response<List<Solicitud>> response) {
                for(Solicitud solicitud : response.body()){
                        popupRespuesta(solicitud, solicitud.getEstadoSolicitud().getCodEstadoSolicitud());
                }
            }

            @Override
            public void onFailure(Call<List<Solicitud>> call, Throwable t) {

            }
        });
    }

    private void popupRespuesta(Solicitud solicitud, int codEstadoSolicitud) {
        int layoutPopupSolicitud = 0;
        boolean aceptada = true;//Si es falso, se muestra el popup rechazado y se cambia su estado
        if (codEstadoSolicitud == 7 && solicitud.getUsuarioControlador().getCodUsuario()==codUsuarioLogeado || codEstadoSolicitud == 8 && solicitud.getUsuarioControlado().getCodUsuario()==codUsuarioLogeado) {
            popUpDesvinculacion(solicitud, codEstadoSolicitud);
        } else if(codEstadoSolicitud==5 || codEstadoSolicitud==6){
            if (codEstadoSolicitud == 5) {
                layoutPopupSolicitud = R.layout.n26_2_solicitud_aceptada;
                aceptada = true;
            } else {
                layoutPopupSolicitud = R.layout.n26_3_solicitud_rechazada;
                aceptada = false;
            }

            View popupView = getLayoutInflater().inflate(layoutPopupSolicitud, null);

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
            // Mostrar el popup en la ubicación deseada
            popupWindow.showAtLocation(rootView, Gravity.CENTER, 0, 0);

            if (aceptada == true) {
                actualizarEstadoSolicitud(solicitud, estadosSolicitud.get(0), dimView, popupWindow);
            } else {
                actualizarEstadoSolicitud(solicitud, estadosSolicitud.get(1), dimView, popupWindow);
            }

            TextView text = popupView.findViewById(R.id.text);
            ImageView cerrar = popupView.findViewById(R.id.boton_cerrar);
            String textPopup = text.getText().toString();
            text.setText(solicitud.getUsuarioControlado().getNombreUsuario()+" " +solicitud.getUsuarioControlado().getApellidoUsuario() + textPopup);

            cerrar.setOnClickListener(view -> {
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

    }


    private void popupSolicitudPendiente(Solicitud solicitud) {
        View popupView = getLayoutInflater().inflate(R.layout.n26_1_solicitud_contacto, null);

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

        TextView texto = popupView.findViewById(R.id.text);
        Button aceptar = popupView.findViewById(R.id.button_aceptar);
        Button rechazar = popupView.findViewById(R.id.button_rechazar);
        String textoPopup = texto.getText().toString();
        texto.setText(solicitud.getUsuarioControlado().getNombreUsuario()+" " +solicitud.getUsuarioControlado().getApellidoUsuario() + textoPopup);

        aceptar.setOnClickListener(view ->{
            actualizarEstadoSolicitud(solicitud, estadosSolicitud.get(4), dimView, popupWindow);
        });
        rechazar.setOnClickListener(view ->{
            actualizarEstadoSolicitud(solicitud, estadosSolicitud.get(5), dimView, popupWindow);
        });

    }

    private void actualizarEstadoSolicitud(Solicitud solicitud, EstadoSolicitud estadoSolicitud, View dimView, PopupWindow popupWindow) {
        solicitudApi.actualizarEstadoSolicitud(solicitud.getCodSolicitud(), estadoSolicitud).enqueue(new Callback<Solicitud>() {
            @Override
            public void onResponse(Call<Solicitud> call, Response<Solicitud> response) {
                if(estadoSolicitud.getCodEstadoSolicitud()==5 || estadoSolicitud.getCodEstadoSolicitud()==6) {
                    popupWindow.dismiss();
                    dimView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<Solicitud> call, Throwable t) {
                Toast.makeText(InicioCalendarioActivity.this, "Hubo un error al aceptar/rechazar la solicitud", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void popupEliminarCuenta(){
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

        aceptar.setOnClickListener(view ->{
            popupMotivoEliminacion();
            popupWindow.dismiss();
        });
        cancelar.setOnClickListener(view ->{
            popupWindow.dismiss();
            dimView.setVisibility(View.GONE);
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

        aceptar.setOnClickListener(view ->{
            String textMotivo = motivo.getText().toString();
            usuarioApi.eliminarUsuario(codUsuarioLogeado, textMotivo).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    mAuth.signOut();
                    Intent intent = new Intent(InicioCalendarioActivity.this, BienvenidoActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }
                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(InicioCalendarioActivity.this, "Error al eliminar la cuenta, intente nuevamente", Toast.LENGTH_SHORT).show();
                }
            });
        });

        cancelar.setOnClickListener(view ->{
            popupWindow.dismiss();
            dimView.setVisibility(View.GONE);
        });
    }
    private void popUpDesvinculacion(Solicitud solicitud, int codEstadoSolicitud) {
        Usuario usuarioDesvinculado;
        View popupView = getLayoutInflater().inflate(R.layout.n27_2_popup_desvinculacion, null);
        if(solicitud.getUsuarioControlador().getCodUsuario()==codUsuarioLogeado){usuarioDesvinculado= solicitud.getUsuarioControlado();}
        else{usuarioDesvinculado= solicitud.getUsuarioControlador();}
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

        // Mostrar el popup en la ubicación deseada
        popupWindow.showAtLocation(rootView, Gravity.CENTER, 0, 0);
        TextView texto = popupView.findViewById(R.id.text);
        String textoPopup = texto.getText().toString();
        texto.setText(usuarioDesvinculado.getUsuarioUnico()+" "+ textoPopup);
        ImageView botonCerrar = popupView.findViewById(R.id.boton_cerrar);

        botonCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarEstadoSolicitud(solicitud, estadosSolicitud.get(1), dimView, popupWindow);
                popupWindow.dismiss();
                dimView.setVisibility(View.GONE);
            }
        });

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                actualizarEstadoSolicitud(solicitud, estadosSolicitud.get(1), dimView, popupWindow);
                dimView.setVisibility(View.GONE);
            }
        });

    }


    // Método para obtener las clases "Recordatorio" asociadas al calendario seleccionado
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
                        Log.d("MiApp", "Recordatorios Asociados Encontrados: ");
                        for (Recordatorio recordatorio : recordatoriosAsociados) {
                            // Para cada recordatorio, obtén las clases "Inventario"
                            Log.d("MiApp", "codRecordatorio encontrado: " + recordatorio.getCodRecordatorio());
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

        Inventario inventarioAsociado = recordatorio.getInventario();

        if (inventarioAsociado != null) {
            Log.d("MiApp", "Inventario Asociado Encontrado: ");
            Log.d("MiApp", "Inventario encontrado con cantReal: " + inventarioAsociado.getCantRealInventario() + ", y cantAviso: " + inventarioAsociado.getCantAvisoInventario());

            inventarioAsociado.setRecordatorio(recordatorio);

            // Revisar si cantReal es nula, si es así entonces se ha desactivado y no genera alertas ni popups
            // Revisar si del inventario la cantReal es igual a la cantAviso y llamar al popup
            if (inventarioAsociado.getCantRealInventario() != null && inventarioAsociado.getCantRealInventario() <= inventarioAsociado.getCantAvisoInventario()) {
                // Primero comprobar si está vacío o no
                if (inventarioAsociado.getCantRealInventario() == 0) {
                    View dimView = findViewById(R.id.dim_view_inventario);
                    dimView.setVisibility(View.VISIBLE);
                    popupInventarioVacio(inventarioAsociado);
                } else {
                    View dimView = findViewById(R.id.dim_view_inventario);
                    dimView.setVisibility(View.VISIBLE);
                    popupInventarioAlerta(inventarioAsociado);
                }
            }

        } else {
            Log.d("MiApp", "No se encontraron clases 'Inventario' asociadas al codRecordatorio: " + recordatorio.getCodRecordatorio());
        }
    }

    private void popupInventarioAlerta(Inventario inventario) {
        notificacionInventario++;
        View popupView = getLayoutInflater().inflate(R.layout.n88_1_popup_recordatorio_existencias, null);

        // Crear la instancia de PopupWindow
        PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        // Hacer que el popup sea enfocable (opcional)
        popupWindow.setFocusable(true);

        // Configurar animación para oscurecer el fondo
        View rootView = findViewById(android.R.id.content);

        View dimView = findViewById(R.id.dim_view_inventario);
        dimView.setVisibility(View.VISIBLE);

        Animation scaleAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.popup);
        popupView.startAnimation(scaleAnimation);

        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(false);
        // Mostrar el popup en la ubicación deseada
        popupWindow.showAtLocation(rootView, Gravity.CENTER, 0, 0);

        TextView texto = popupView.findViewById(R.id.text);
        TextView textoCantReal = popupView.findViewById(R.id.text_restantes);

        Log.d("MiApp", "El nombre del medicamento con pocas existencias es: " + inventario.getRecordatorio().getMedicamento().getNombreMedicamento());
        Log.d("MiApp", "El nombre de la presentación del medicamento con pocas existencias es: " + inventario.getRecordatorio().getPresentacionMed().getNombrePresentacionMed());

        texto.setText("Pocas existencias de: " + inventario.getRecordatorio().getMedicamento().getNombreMedicamento());
        textoCantReal.setText(inventario.getCantRealInventario() + " " + inventario.getRecordatorio().getPresentacionMed().getNombrePresentacionMed() + "(s) restantes");

        Button recargar = popupView.findViewById(R.id.button_recargar);
        Button omitir = popupView.findViewById(R.id.button_omitir);

        Log.d("MiApp", "Se envía el inventario: " + inventario);

        recargar.setOnClickListener(view ->{
            Log.d("MiApp", "Se hizo clic en el botón recargar");
            popupWindow.dismiss();
            dimView.setVisibility(View.GONE);
            popupRecargarInventario(inventario);
        });

        omitir.setOnClickListener(view ->{
            popupWindow.dismiss();
            dimView.setVisibility(View.GONE);
        });

        // Configurar el OnTouchListener para la vista oscura
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                notificacionInventario--;
                if (notificacionInventario == 0) {
                    dimView.setVisibility(View.GONE);
                }
            }
        });
    }


    private void popupInventarioVacio(Inventario inventario) {
        notificacionInventario++;
        View popupView = getLayoutInflater().inflate(R.layout.n88_3_popup_inventario_vacio, null);

        // Crear la instancia de PopupWindow
        PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        // Hacer que el popup sea enfocable (opcional)
        popupWindow.setFocusable(true);

        // Configurar animación para oscurecer el fondo
        View rootView = findViewById(android.R.id.content);

        View dimView = findViewById(R.id.dim_view_inventario);
        dimView.setVisibility(View.VISIBLE);

        Animation scaleAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.popup);
        popupView.startAnimation(scaleAnimation);

        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(false);
        // Mostrar el popup en la ubicación deseada
        popupWindow.showAtLocation(rootView, Gravity.CENTER, 0, 0);

        TextView texto = popupView.findViewById(R.id.text);
        TextView textoCantReal = popupView.findViewById(R.id.text_restantes);

        Log.d("MiApp", "El nombre del medicamento con inventario vacío es: " + inventario.getRecordatorio().getMedicamento().getNombreMedicamento());
        Log.d("MiApp", "El nombre de la presentación del medicamento con inventario vacío es: " + inventario.getRecordatorio().getPresentacionMed().getNombrePresentacionMed());

        texto.setText("¡Inventario vacío de: " + inventario.getRecordatorio().getMedicamento().getNombreMedicamento() + "!");
        textoCantReal.setText(inventario.getCantRealInventario() + " " + inventario.getRecordatorio().getPresentacionMed().getNombrePresentacionMed() + "(s) restantes");

        Button recargar = popupView.findViewById(R.id.button_recargar);
        Button omitir = popupView.findViewById(R.id.button_omitir);
        Button desactivar = popupView.findViewById(R.id.button_desactivar);

        Log.d("MiApp", "Se envía el inventario: " + inventario);

        recargar.setOnClickListener(view ->{
            Log.d("MiApp", "Se hizo clic en el botón recargar");
            popupWindow.dismiss();
            dimView.setVisibility(View.GONE);
            popupRecargarInventario(inventario);
        });

        omitir.setOnClickListener(view ->{
            popupWindow.dismiss();
            dimView.setVisibility(View.GONE);
        });

        desactivar.setOnClickListener(view ->{
            Log.d("MiApp", "Se hizo clic en el botón desactivar inventario");
            popupWindow.dismiss();
            dimView.setVisibility(View.GONE);
            desactivarInventario(inventario);
        });

        // Configurar el OnTouchListener para la vista oscura
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                notificacionInventario--;
                if (notificacionInventario == 0) {
                    dimView.setVisibility(View.GONE);
                }
            }
        });
    }



    private void popupRecargarInventario(Inventario inventario) {
        Log.d("MiApp","Se llamó a popupRecargarInventario");
        View popupView = getLayoutInflater().inflate(R.layout.n88_2_popup_recarga, null);

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

        TextView texto = popupView.findViewById(R.id.text);
        EditText textEditCantidad = popupView.findViewById(R.id.textEditCantidad);
        TextView textoPresentacion = popupView.findViewById(R.id.text_presentacion);

        texto.setText("Te quedan " + inventario.getCantRealInventario() + " " + inventario.getRecordatorio().getPresentacionMed().getNombrePresentacionMed() + "(s)");

        textoPresentacion.setText(inventario.getRecordatorio().getPresentacionMed().getNombrePresentacionMed() + "(s)");

        TextView aceptar = popupView.findViewById(R.id.aceptar);
        TextView cancelar = popupView.findViewById(R.id.cancelar);

        aceptar.setOnClickListener(view ->{
            Log.d("MiApp", "Se hizo clic en el botón aceptar");
            String textoCantidad = textEditCantidad.getText().toString();
            int cantidadRecarga = Integer.parseInt(textoCantidad);

            Log.d("MiApp","Se lee la cantidad de recarga y se llama a actualizarInventario");
            // Se actualiza el inventario guardando como cantReal = cantidad ingresada + cantReal (anterior)

            inventarioApi.actualizarInventario(inventario.getCodInventario(), cantidadRecarga+inventario.getCantRealInventario()).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        // La llamada a la API fue exitosa, ahora puedes cerrar el Popup
                        popupWindow.dismiss();
                        dimView.setVisibility(View.GONE);
                        Log.d("MiApp", "El inventario tiene actualmente una cantReal: " + inventario.getCantRealInventario());
                        Log.d("MiApp", "Se ingresa para agregar una cantidad de: " + cantidadRecarga);
                        Log.d("MiApp", "Se actualiza el inventario a una nueva cantReal: " + (cantidadRecarga+inventario.getCantRealInventario()));
                    } else {
                        Toast.makeText(InicioCalendarioActivity.this, "Error al actualizar el inventario, intente nuevamente", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(InicioCalendarioActivity.this, "Error en la solicitud a la API, intente nuevamente", Toast.LENGTH_SHORT).show();
                }
            });
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

    private void desactivarInventario(Inventario inventario) {
        Log.d("MiApp","Se llamó a desactivarInventario");
        View popupView = getLayoutInflater().inflate(R.layout.n88_4_popup_inventario_desactivado, null);

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

        ImageView cerrar = popupView.findViewById(R.id.boton_cerrar);

        // Verifica si el inventario no es nulo
        if (inventario != null) {

            Log.d("MiApp", "El código del inventario a desactivar es: " + inventario.getCodInventario());


            // Colocar cantidad real y de aviso en null para que deje de ser mostrado y deje de generar popups
            inventarioApi.desactivarInventario(inventario.getCodInventario()).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Recordatorio recordatorioAsociado = inventario.getRecordatorio();
                        Inventario inventarioDesactivado = recordatorioAsociado.getInventario();

                        Log.d("MiApp", "Se ha desactivado el inventario" + inventarioDesactivado);
                        Log.d("MiApp","La cantidad real del inventario desactivado ahora es: " + inventarioDesactivado.getCantRealInventario());
                        Log.d("MiApp","La cantidad de aviso del inventario desactivado ahora es: " + inventarioDesactivado.getCantAvisoInventario());
                    } else {
                        Toast.makeText(InicioCalendarioActivity.this, "Error al desactivar el inventario, intente nuevamente", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(InicioCalendarioActivity.this, "Error en la solicitud a la API, intente nuevamente", Toast.LENGTH_SHORT).show();
                }
            });

        }

        cerrar.setOnClickListener(view ->{
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


    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Detén la ejecución al destruir la actividad
        handler.removeCallbacks(runnableCode);
    }
}
