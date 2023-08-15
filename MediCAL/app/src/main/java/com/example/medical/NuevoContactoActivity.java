package com.example.medical;

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

import com.example.medical.model.Solicitud;
import com.example.medical.model.Usuario;
import com.example.medical.retrofit.RetrofitService;
import com.example.medical.retrofit.SolicitudApi;
import com.example.medical.retrofit.UsuarioApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NuevoContactoActivity extends AppCompatActivity {
    private ImageView botonCerrar;
    private EditText textoContacto;
    private ImageView tick;
    private Usuario usuarioContacto;
    private Usuario usuarioLogeado;
    private Button nuevoContacto;
    private boolean usuarioExistente;
    private RetrofitService retrofitService = new RetrofitService();
    private UsuarioApi usuarioApi = retrofitService.getRetrofit().create(UsuarioApi.class);
    private SolicitudApi solicitudApi = retrofitService.getRetrofit().create(SolicitudApi.class);

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
            if(usuarioExistente == true){
                Solicitud solicitud = new Solicitud();
                solicitud.setUsuarioControlado(usuarioContacto);
                solicitud.setUsuarioControlador(usuarioLogeado);
                solicitudApi.save(solicitud).enqueue(new Callback<Solicitud>() {
                    @Override
                    public void onResponse(Call<Solicitud> call, Response<Solicitud> response) {
                        Toast.makeText(NuevoContactoActivity.this, "Solicitud Creada", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Solicitud> call, Throwable t) {
                        Toast.makeText(NuevoContactoActivity.this, "Error al crear la solicitud", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            else{
                popupInvalido(R.layout.n02_1_popup_usuario_inexistente);
            }
        });

    }

    private void inicializarVariables() {
        botonCerrar = findViewById(R.id.boton_cerrar);
        textoContacto = findViewById(R.id.textEdit_contacto);
        tick = findViewById(R.id.tick);
        nuevoContacto = findViewById(R.id.button_enviar_solicitud);
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
