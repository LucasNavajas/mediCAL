package com.example.medical;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.medical.model.EstadoSolicitud;
import com.example.medical.model.Solicitud;
import com.example.medical.model.Usuario;
import com.example.medical.retrofit.EstadoSolicitudApi;
import com.example.medical.retrofit.RetrofitService;
import com.example.medical.retrofit.SolicitudApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GestionarContactosActivity extends AppCompatActivity {
    private TextView textoSupervisor;
    private TextView textoSupervisados;
    private ImageView botonVolver;
    private Button nuevoContacto;
    private List<EstadoSolicitud> desvinculado = new ArrayList<>();
    private RetrofitService retrofitService = new RetrofitService();
    private int codUsuarioLogeado;
    private SolicitudApi solicitudApi = retrofitService.getRetrofit().create(SolicitudApi.class);
    private EstadoSolicitudApi estadoSolicitudApi = retrofitService.getRetrofit().create(EstadoSolicitudApi.class);
    private List<Solicitud> contactoSupervisor;
    private List<Solicitud> contactosSupervisados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.n27_0_gestion_contactos_vinculados);//cambiar esta linea por el nombre del layout a probar
        inicializarVariables();

        botonVolver.setOnClickListener(view -> {onBackPressed();});

        nuevoContacto.setOnClickListener(view ->{
            Intent intent = new Intent(GestionarContactosActivity.this, NuevoContactoActivity.class);
            intent.putExtra("codUsuario", codUsuarioLogeado);
            startActivity(intent);
        });

    }

    private void inicializarVariables() {
        textoSupervisor = findViewById(R.id.texto_supervisor);
        textoSupervisados = findViewById(R.id.texto_supervisados);
        botonVolver = findViewById(R.id.boton_volver);
        codUsuarioLogeado = getIntent().getIntExtra("codUsuario", 0);
        nuevoContacto = findViewById(R.id.button_agregar_contacto);
        inicializarContactos();
        estadoSolicitudApi.findByCodEstadoSolicitud(7).enqueue(new Callback<EstadoSolicitud>() {
            @Override
            public void onResponse(Call<EstadoSolicitud> call, Response<EstadoSolicitud> response) {
                desvinculado.add(response.body());
            }

            @Override
            public void onFailure(Call<EstadoSolicitud> call, Throwable t) {
                Toast.makeText(GestionarContactosActivity.this, "Error al cargar el estado de la solicitud, cierre la aplicación y vuelva a abrirla", Toast.LENGTH_SHORT).show();
            }
        });
        estadoSolicitudApi.findByCodEstadoSolicitud(8).enqueue(new Callback<EstadoSolicitud>() {
            @Override
            public void onResponse(Call<EstadoSolicitud> call, Response<EstadoSolicitud> response) {
                desvinculado.add(response.body());
            }

            @Override
            public void onFailure(Call<EstadoSolicitud> call, Throwable t) {
                Toast.makeText(GestionarContactosActivity.this, "Error al cargar el estado de la solicitud, cierre la aplicación y vuelva a abrirla", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void inicializarContactos() {
        solicitudApi.obtenerSupervisor(codUsuarioLogeado).enqueue(new Callback<List<Solicitud>>() {
            @Override
            public void onResponse(Call<List<Solicitud>> call, Response<List<Solicitud>> response) {
                contactoSupervisor = response.body();
                llenarContactoSupervisor();
            }

            @Override
            public void onFailure(Call<List<Solicitud>> call, Throwable t) {
                Toast.makeText(GestionarContactosActivity.this, "Error al cargar los contactos, cierre la aplicación y vuelva a abrirla", Toast.LENGTH_SHORT).show();
            }
        });

        solicitudApi.obtenerContactos(codUsuarioLogeado).enqueue(new Callback<List<Solicitud>>() {
            @Override
            public void onResponse(Call<List<Solicitud>> call, Response<List<Solicitud>> response) {
                contactosSupervisados = response.body();
                llenarContactosSupervisados();
            }

            @Override
            public void onFailure(Call<List<Solicitud>> call, Throwable t) {
                Toast.makeText(GestionarContactosActivity.this, "Error al cargar los contactos, cierre la aplicación y vuelva a abrirla", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void llenarContactoSupervisor() {
        LinearLayout layoutContactoSupervisor = findViewById(R.id.layout_contacto_supervisor); // Encuentra el LinearLayout agregado en el XML
        layoutContactoSupervisor.removeAllViews(); // Limpia cualquier vista previamente agregada

        for (Solicitud solicitud : contactoSupervisor) {
            RelativeLayout nuevoRelativeLayout = crearRelativeLayoutSupervisor(solicitud);
            layoutContactoSupervisor.addView(nuevoRelativeLayout); // Agrega el RelativeLayout al LinearLayout
        }
    }

    private void llenarContactosSupervisados(){
        LinearLayout layoutContactoSupervisado = findViewById(R.id.layout_contacto_supervisado); // Encuentra el LinearLayout agregado en el XML
        layoutContactoSupervisado.removeAllViews(); // Limpia cualquier vista previamente agregada
        for(Solicitud solicitud : contactosSupervisados){
            RelativeLayout nuevoRelativeLayout = crearRelativeLayoutSupervisado(solicitud);
            layoutContactoSupervisado.addView(nuevoRelativeLayout); // Agrega el RelativeLayout al LinearLayout
        }
    }

    private RelativeLayout crearRelativeLayoutSupervisor(Solicitud solicitud) {
        RelativeLayout relativeLayout = new RelativeLayout(this); // 'this' debe ser el contexto de tu actividad

        // Configura los parámetros de diseño para el RelativeLayout
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        params.addRule(RelativeLayout.BELOW, R.id.texto_supervisor);
        relativeLayout.setLayoutParams(params);

        // Crea un TextView para el nombre de usuario controlador
        // Crea un TextView para el nombre de usuario controlador
        TextView usuarioTextView = new TextView(this);
        usuarioTextView.setLayoutParams(params); // Usar los mismos parámetros de diseño que el RelativeLayout
        usuarioTextView.setId(View.generateViewId()); // Genera un ID único para el TextView
        usuarioTextView.setText(solicitud.getUsuarioControlador().getUsuarioUnico());
        usuarioTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25); // Tamaño de texto en sp
        usuarioTextView.setTextColor(ContextCompat.getColor(this, R.color.black)); // Color de texto

// Configura el margen izquierdo (start) en dp para usuarioTextView
        int marginStartInDp = 30;
        int marginStartInPixels = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, marginStartInDp, getResources().getDisplayMetrics());

        RelativeLayout.LayoutParams usuarioParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        usuarioParams.addRule(RelativeLayout.BELOW, R.id.texto_supervisor);
        usuarioParams.setMarginStart(marginStartInPixels); // Configura el margen izquierdo

        usuarioTextView.setLayoutParams(usuarioParams);

// Crea un TextView para el nombre y apellido
        TextView nombreApellidoTextView = new TextView(this);
        nombreApellidoTextView.setLayoutParams(params); // Usar los mismos parámetros de diseño que el RelativeLayout
        nombreApellidoTextView.setId(View.generateViewId()); // Genera un ID único para el TextView
        nombreApellidoTextView.setText(solicitud.getUsuarioControlador().getNombreUsuario()+ " "+ solicitud.getUsuarioControlador().getApellidoUsuario());
        nombreApellidoTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25); // Tamaño de texto en sp
        nombreApellidoTextView.setTextColor(ContextCompat.getColor(this, R.color.black)); // Color de texto

// Configura el margen izquierdo (start) en dp para nombreApellidoTextView
        RelativeLayout.LayoutParams nombreApellidoParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        nombreApellidoParams.addRule(RelativeLayout.BELOW, usuarioTextView.getId());
        nombreApellidoParams.setMarginStart(marginStartInPixels); // Configura el margen izquierdo

        nombreApellidoTextView.setLayoutParams(nombreApellidoParams);


        // Crea una ImageView para el ícono con un OnClickListener
        ImageView iconImageView = new ImageView(this);
        int widthInDp = 35; // Ancho en dp
        int heightInDp = 35; // Alto en dp

// Convertir dp a píxeles
        int widthInPixels = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, widthInDp, getResources().getDisplayMetrics());

        int heightInPixels = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, heightInDp, getResources().getDisplayMetrics());

// Configurar el ancho y el alto en los parámetros de diseño
        RelativeLayout.LayoutParams iconParams = new RelativeLayout.LayoutParams(
                widthInPixels, heightInPixels);

        iconImageView.setLayoutParams(iconParams); // Usar los mismos parámetros de diseño que el RelativeLayout
        iconImageView.setImageResource(R.drawable.tacho); // Reemplaza 'tu_icono' con el recurso de imagen deseado
        iconImageView.setColorFilter(ContextCompat.getColor(this, android.R.color.black));
        iconImageView.setId(View.generateViewId()); // Genera un ID único para la ImageView
        iconParams.addRule(RelativeLayout.ALIGN_PARENT_END);
        iconParams.addRule(RelativeLayout.CENTER_VERTICAL);

        int marginEndInDp = 30;
        int marginEndInPixels = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, marginEndInDp, getResources().getDisplayMetrics());

        iconParams.addRule(RelativeLayout.ALIGN_PARENT_END);
        iconParams.addRule(RelativeLayout.CENTER_VERTICAL);

// Configura el margen derecho
        iconParams.setMarginEnd(marginEndInPixels);
        iconImageView.setLayoutParams(iconParams);
        iconImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminarSolicitud(solicitud, solicitud.getUsuarioControlador());
            }
        });

        // Agrega los TextViews y la ImageView al RelativeLayout
        relativeLayout.addView(usuarioTextView);
        relativeLayout.addView(nombreApellidoTextView);
        relativeLayout.addView(iconImageView);

        return relativeLayout;
    }

    private RelativeLayout crearRelativeLayoutSupervisado(Solicitud solicitud) {
        RelativeLayout relativeLayout = new RelativeLayout(this); // 'this' debe ser el contexto de tu actividad

        // Configura los parámetros de diseño para el RelativeLayout
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        params.addRule(RelativeLayout.BELOW, R.id.texto_supervisor);
        relativeLayout.setLayoutParams(params);

        // Crea un TextView para el nombre de usuario controlador
        // Crea un TextView para el nombre de usuario controlador
        TextView usuarioTextView = new TextView(this);
        usuarioTextView.setLayoutParams(params); // Usar los mismos parámetros de diseño que el RelativeLayout
        usuarioTextView.setId(View.generateViewId()); // Genera un ID único para el TextView
        usuarioTextView.setText(solicitud.getUsuarioControlado().getUsuarioUnico());
        usuarioTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25); // Tamaño de texto en sp
        usuarioTextView.setTextColor(ContextCompat.getColor(this, R.color.black)); // Color de texto

// Configura el margen izquierdo (start) en dp para usuarioTextView
        int marginStartInDp = 30;
        int marginStartInPixels = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, marginStartInDp, getResources().getDisplayMetrics());

        RelativeLayout.LayoutParams usuarioParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        usuarioParams.addRule(RelativeLayout.BELOW, R.id.texto_supervisor);
        usuarioParams.setMarginStart(marginStartInPixels); // Configura el margen izquierdo

        usuarioTextView.setLayoutParams(usuarioParams);

// Crea un TextView para el nombre y apellido
        TextView nombreApellidoTextView = new TextView(this);
        nombreApellidoTextView.setLayoutParams(params); // Usar los mismos parámetros de diseño que el RelativeLayout
        nombreApellidoTextView.setId(View.generateViewId()); // Genera un ID único para el TextView
        nombreApellidoTextView.setText(solicitud.getUsuarioControlado().getNombreUsuario()+ " "+ solicitud.getUsuarioControlado().getApellidoUsuario());
        nombreApellidoTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25); // Tamaño de texto en sp
        nombreApellidoTextView.setTextColor(ContextCompat.getColor(this, R.color.black)); // Color de texto

// Configura el margen izquierdo (start) en dp para nombreApellidoTextView
        RelativeLayout.LayoutParams nombreApellidoParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        nombreApellidoParams.addRule(RelativeLayout.BELOW, usuarioTextView.getId());
        nombreApellidoParams.setMarginStart(marginStartInPixels); // Configura el margen izquierdo

        nombreApellidoTextView.setLayoutParams(nombreApellidoParams);


        // Crea una ImageView para el ícono con un OnClickListener
        ImageView iconImageView = new ImageView(this);
        int widthInDp = 35; // Ancho en dp
        int heightInDp = 35; // Alto en dp

// Convertir dp a píxeles
        int widthInPixels = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, widthInDp, getResources().getDisplayMetrics());

        int heightInPixels = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, heightInDp, getResources().getDisplayMetrics());

// Configurar el ancho y el alto en los parámetros de diseño
        RelativeLayout.LayoutParams iconParams = new RelativeLayout.LayoutParams(
                widthInPixels, heightInPixels);

        iconImageView.setLayoutParams(iconParams); // Usar los mismos parámetros de diseño que el RelativeLayout
        iconImageView.setImageResource(R.drawable.tacho); // Reemplaza 'tu_icono' con el recurso de imagen deseado
        iconImageView.setColorFilter(ContextCompat.getColor(this, android.R.color.black));
        iconImageView.setId(View.generateViewId()); // Genera un ID único para la ImageView
        iconParams.addRule(RelativeLayout.ALIGN_PARENT_END);
        iconParams.addRule(RelativeLayout.CENTER_VERTICAL);

        int marginEndInDp = 30;
        int marginEndInPixels = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, marginEndInDp, getResources().getDisplayMetrics());

        iconParams.addRule(RelativeLayout.ALIGN_PARENT_END);
        iconParams.addRule(RelativeLayout.CENTER_VERTICAL);

// Configura el margen derecho
        iconParams.setMarginEnd(marginEndInPixels);
        iconImageView.setLayoutParams(iconParams);
        iconImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminarSolicitud(solicitud, solicitud.getUsuarioControlado());
            }
        });
        View lineaView = new View(this);
        lineaView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, // Ancho a juego con el padre
                (int) TypedValue.applyDimension( // Altura en píxeles (por ejemplo, 5dp)
                        TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics()
                )
        ));
        int marginInDp = 30;
        int marginInPixels = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, marginInDp, getResources().getDisplayMetrics()
        );
        lineaView.setBackgroundColor(ContextCompat.getColor(this, R.color.gris_medical)); // Establece el color de fondo de la línea


        // Configura las reglas de diseño para que esté debajo del nombreApellidoTextView
        RelativeLayout.LayoutParams lineaParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                (int) TypedValue.applyDimension( // Altura en píxeles (por ejemplo, 5dp)
                        TypedValue.COMPLEX_UNIT_DIP, 1, getResources().getDisplayMetrics()
                )
        );
        lineaParams.addRule(RelativeLayout.BELOW, nombreApellidoTextView.getId());
        lineaParams.setMarginStart(marginInPixels);
        lineaParams.setMarginEnd(marginInPixels);

        // Agrega los TextViews y la ImageView al RelativeLayout
        relativeLayout.addView(usuarioTextView);
        relativeLayout.addView(nombreApellidoTextView);
        relativeLayout.addView(iconImageView);
        relativeLayout.addView(lineaView, lineaParams);

        return relativeLayout;
    }

    private void eliminarSolicitud(Solicitud solicitud, Usuario usuario) {
            View popupView = getLayoutInflater().inflate(R.layout.n27_1_popup_borrar_contacto_vinculado, null);

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
                    EstadoSolicitud desvinculadoEstado;
                    if(solicitud.getUsuarioControlador().getCodUsuario()==codUsuarioLogeado){
                        desvinculadoEstado = desvinculado.get(1);
                    }
                    else{
                        desvinculadoEstado = desvinculado.get(0);
                    }
                    popupWindow.dismiss();
                    solicitudApi.actualizarEstadoSolicitud(solicitud.getCodSolicitud(), desvinculadoEstado).enqueue(new Callback<Solicitud>() {
                        @Override
                        public void onResponse(Call<Solicitud> call, Response<Solicitud> response) {
                            popUpDesvinculacion(usuario);
                        }

                        @Override
                        public void onFailure(Call<Solicitud> call, Throwable t) {
                            Toast.makeText(GestionarContactosActivity.this, "Error al eliminar la solicitud, cierre la aplicación y vuelva a abrirla", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            });
            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    dimView.setVisibility(View.GONE);
                }
            });
        }

    private void popUpDesvinculacion(Usuario usuario) {

        View popupView = getLayoutInflater().inflate(R.layout.n27_2_popup_desvinculacion, null);

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
        texto.setText(usuario.getUsuarioUnico()+" "+ textoPopup);
        ImageView botonCerrar = popupView.findViewById(R.id.boton_cerrar);

        botonCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ocultar el PopupWindow
                popupWindow.dismiss();

                // Ocultar el fondo oscurecido
                dimView.setVisibility(View.GONE);
                Intent intent = new Intent(GestionarContactosActivity.this, InicioCalendarioActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
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