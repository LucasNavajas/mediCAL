package com.example.medical;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.medical.model.EstadoSolicitud;
import com.example.medical.model.Solicitud;
import com.example.medical.model.Usuario;
import com.example.medical.retrofit.EstadoSolicitudApi;
import com.example.medical.retrofit.RetrofitService;
import com.example.medical.retrofit.SolicitudApi;
import com.example.medical.retrofit.UsuarioApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NuevoContactoActivity extends AppCompatActivity {
    private ImageView botonCerrar;
    private EditText textoContacto;
    private ImageView tick;
    private TextView errorContacto;
    private Usuario usuarioContacto;
    private Usuario usuarioLogeado;
    private Button nuevoContacto;
    private boolean usuarioExistente;
    private boolean usuarioNoControlado;
    private RetrofitService retrofitService = new RetrofitService();
    private UsuarioApi usuarioApi = retrofitService.getRetrofit().create(UsuarioApi.class);
    private SolicitudApi solicitudApi = retrofitService.getRetrofit().create(SolicitudApi.class);
    private EstadoSolicitudApi estadoSolicitudApi = retrofitService.getRetrofit().create(EstadoSolicitudApi.class);
    private List<Solicitud> todasSolicitudesActivas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.n26_0_anadir_contacto);//cambiar esta linea por el nombre del layout a probar
        inicializarVariables();

        botonCerrar.setOnClickListener(view -> {
            onBackPressed();
        });

        textoContacto.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                String textoContacto = editable.toString();
                if (!textoContacto.isEmpty()) {
                    usuarioApi.buscarUsuariosPorMailYUser(textoContacto).enqueue(new Callback<Usuario>() {
                        @Override
                        public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                            if (response.isSuccessful()) {
                                usuarioContacto = response.body();
                                if (usuarioContacto != null) {
                                    // Usuario encontrado (tick)
                                    usuarioExistente = true;
                                    tick.setImageResource(R.drawable.tick);
                                } else {
                                    // Usuario no encontrado (cruz)
                                    usuarioExistente = false;
                                    tick.setImageResource(R.drawable.cruz_roja);
                                }
                            } else {
                                // Respuesta no exitosa (cruz)
                                usuarioExistente = false;
                                tick.setImageResource(R.drawable.cruz_roja);
                            }
                        }
                        @Override
                        public void onFailure(Call<Usuario> call, Throwable t) {
                            // Error en la llamada (cruz)
                            usuarioExistente = false;
                            tick.setImageResource(R.drawable.cruz_roja);
                        }
                    });
                } else {
                    // Texto de contacto vacío (restaurar estado inicial)
                    usuarioExistente = false;
                    tick.setImageResource(R.drawable.cruz_roja); // Cambia esto al icono que quieras mostrar cuando el campo esté vacío
                }
            }
        });
        nuevoContacto.setOnClickListener(view -> {
            usuarioNoControlado = true;
            errorContacto.setVisibility(View.GONE);
            for(Solicitud solicitudActiva : todasSolicitudesActivas){
                if(solicitudActiva.getUsuarioControlado().getCodUsuario() == usuarioContacto.getCodUsuario()){
                    usuarioNoControlado = false;
                }
            }
            if(usuarioExistente == true && usuarioNoControlado ==true){
                Solicitud solicitud = new Solicitud();
                solicitud.setUsuarioControlado(usuarioContacto);
                solicitud.setUsuarioControlador(usuarioLogeado);
                estadoSolicitudApi.findByCodEstadoSolicitud(4).enqueue(new Callback<EstadoSolicitud>() {
                    @Override
                    public void onResponse(Call<EstadoSolicitud> call, Response<EstadoSolicitud> response) {
                        solicitud.setEstadoSolicitud(response.body());
                        solicitudApi.save(solicitud).enqueue(new Callback<Solicitud>() {
                            @Override
                            public void onResponse(Call<Solicitud> call, Response<Solicitud> response) {
                                popupSolicitudCreada();
                            }

                            @Override
                            public void onFailure(Call<Solicitud> call, Throwable t) {
                                Toast.makeText(NuevoContactoActivity.this, "Error al crear la solicitud", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<EstadoSolicitud> call, Throwable t) {
                        Toast.makeText(NuevoContactoActivity.this, "Error al crear la solicitud (al configurarla como pendiente)", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            else{
                if(usuarioNoControlado==true) {
                    popupInvalido(R.layout.n02_1_popup_usuario_inexistente);
                }
                else{
                    errorContacto.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    private void popupSolicitudCreada() {
        View popupView = getLayoutInflater().inflate(R.layout.n26_4_solicitud_enviada, null);


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

        ImageView cerrar = popupView.findViewById(R.id.boton_cerrar);
        TextView textoPopUp = popupView.findViewById(R.id.text);
        String texto = textoPopUp.getText().toString();
        textoPopUp.setText(texto + " "+ usuarioContacto.getUsuarioUnico());

        cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ocultar el PopupWindow
                popupWindow.dismiss();

                // Ocultar el fondo oscurecido
                dimView.setVisibility(View.GONE);
                Intent intent = new Intent(NuevoContactoActivity.this, InicioCalendarioActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish(); // Opcional: finaliza la actividad actual si ya no la necesitas en el back stack
            }
        });
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                dimView.setVisibility(View.GONE);
                Intent intent = new Intent(NuevoContactoActivity.this, InicioCalendarioActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish(); // Opcional: finaliza la actividad actual si ya no la necesitas en el back stack

            }
        });
    }

    private void inicializarVariables() {
        botonCerrar = findViewById(R.id.boton_cerrar);
        textoContacto = findViewById(R.id.textEdit_contacto);
        tick = findViewById(R.id.tick);
        nuevoContacto = findViewById(R.id.button_enviar_solicitud);
        errorContacto = findViewById(R.id.error_contacto);
        usuarioExistente = false;
        usuarioApi.getByCodUsuario(getIntent().getIntExtra("codUsuario", 0)).enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                usuarioLogeado = response.body();
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Toast.makeText(NuevoContactoActivity.this, "Error con el servidor, no se puede obtener el usuario logeado", Toast.LENGTH_SHORT).show();
            }
        });
        solicitudApi.obtenerSolicitudesActivas().enqueue(new Callback<List<Solicitud>>() {
            @Override
            public void onResponse(Call<List<Solicitud>> call, Response<List<Solicitud>> response) {
                todasSolicitudesActivas = response.body();
            }

            @Override
            public void onFailure(Call<List<Solicitud>> call, Throwable t) {
                Toast.makeText(NuevoContactoActivity.this, "Error con el servidor, no se pueden obtener las solicitudes activas", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void popupInvalido(int layoutResId) {
        View popupView = getLayoutInflater().inflate(layoutResId, null);


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

        TextView textViewAceptar = popupView.findViewById(R.id.regresar);

        textViewAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
}
