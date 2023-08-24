package com.example.medical;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.medical.model.Solicitud;
import com.example.medical.retrofit.RetrofitService;
import com.example.medical.retrofit.SolicitudApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GestionarContactosActivity extends AppCompatActivity {
    private TextView textoSupervisor;
    private TextView textoSupervisados;
    private ImageView botonVolver;
    private View divider;
    private LinearLayout contenido;
    private RetrofitService retrofitService = new RetrofitService();
    private int codUsuarioLogeado = getIntent().getIntExtra("codUsuario", 0);
    private SolicitudApi solicitudApi = retrofitService.getRetrofit().create(SolicitudApi.class);
    private List<Solicitud> contactoSupervisor;
    private List<Solicitud> contactosSupervisados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.n27_0_gestion_contactos_vinculados);//cambiar esta linea por el nombre del layout a probar
        inicializarVariables();
    }

    private void inicializarVariables() {
        textoSupervisor = findViewById(R.id.texto_supervisor);
        textoSupervisados = findViewById(R.id.texto_supervisados);
        botonVolver = findViewById(R.id.boton_volver);
        divider = findViewById(R.id.divider);
        contenido = findViewById(R.id.contenido);
        inicializarContactos();
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
            }

            @Override
            public void onFailure(Call<List<Solicitud>> call, Throwable t) {
                Toast.makeText(GestionarContactosActivity.this, "Error al cargar los contactos, cierre la aplicación y vuelva a abrirla", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void llenarContactoSupervisor() {
        for (Solicitud solicitud : contactoSupervisor) {
            RelativeLayout nuevoRelativeLayout = crearRelativeLayoutSupervisor(solicitud);
            contenido.addView(nuevoRelativeLayout); // Agrega el RelativeLayout al LinearLayout
        }
    }

    private void llenarContactosSupervisados(){
        for(Solicitud solicitud : contactosSupervisados){
            RelativeLayout nuevoRelativeLayout = crearRelativeLayoutSupervisado(solicitud);
            contenido.addView(nuevoRelativeLayout); // Agrega el RelativeLayout al LinearLayout
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
        TextView usuarioTextView = new TextView(this);
        usuarioTextView.setLayoutParams(params); // Usar los mismos parámetros de diseño que el RelativeLayout
        usuarioTextView.setId(View.generateViewId()); // Genera un ID único para el TextView
        usuarioTextView.setText(solicitud.getUsuarioControlador().getUsuarioUnico());
        usuarioTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25); // Tamaño de texto en sp
        usuarioTextView.setTextColor(ContextCompat.getColor(this, R.color.black)); // Color de texto

        // Crea un TextView para el nombre y apellido
        TextView nombreApellidoTextView = new TextView(this);
        nombreApellidoTextView.setLayoutParams(params); // Usar los mismos parámetros de diseño que el RelativeLayout
        nombreApellidoTextView.setId(View.generateViewId()); // Genera un ID único para el TextView
        nombreApellidoTextView.setText(solicitud.getUsuarioControlador().getNombreUsuario()+ " "+ solicitud.getUsuarioControlador().getApellidoUsuario());
        nombreApellidoTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25); // Tamaño de texto en sp
        nombreApellidoTextView.setTextColor(ContextCompat.getColor(this, R.color.black)); // Color de texto
        RelativeLayout.LayoutParams nombreApellidoParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        nombreApellidoParams.addRule(RelativeLayout.BELOW, usuarioTextView.getId());
        nombreApellidoTextView.setLayoutParams(nombreApellidoParams);

        // Crea una ImageView para el ícono con un OnClickListener
        ImageView iconImageView = new ImageView(this);
        iconImageView.setLayoutParams(params); // Usar los mismos parámetros de diseño que el RelativeLayout
        iconImageView.setImageResource(R.drawable.tacho); // Reemplaza 'tu_icono' con el recurso de imagen deseado
        iconImageView.setId(View.generateViewId()); // Genera un ID único para la ImageView
        RelativeLayout.LayoutParams iconParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        iconParams.addRule(RelativeLayout.ALIGN_PARENT_END);
        iconParams.addRule(RelativeLayout.CENTER_VERTICAL);
        iconImageView.setLayoutParams(iconParams);
        iconImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminarSolicitud(solicitud);
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
        params.addRule(RelativeLayout.BELOW, R.id.texto_supervisados);
        relativeLayout.setLayoutParams(params);

        // Crea un TextView para el nombre de usuario controlador
        TextView usuarioTextView = new TextView(this);
        usuarioTextView.setLayoutParams(params); // Usar los mismos parámetros de diseño que el RelativeLayout
        usuarioTextView.setId(View.generateViewId()); // Genera un ID único para el TextView
        usuarioTextView.setText(solicitud.getUsuarioControlado().getUsuarioUnico());
        usuarioTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25); // Tamaño de texto en sp
        usuarioTextView.setTextColor(ContextCompat.getColor(this, R.color.black)); // Color de texto

        // Crea un TextView para el nombre y apellido
        TextView nombreApellidoTextView = new TextView(this);
        nombreApellidoTextView.setLayoutParams(params); // Usar los mismos parámetros de diseño que el RelativeLayout
        nombreApellidoTextView.setId(View.generateViewId()); // Genera un ID único para el TextView
        nombreApellidoTextView.setText(solicitud.getUsuarioControlado().getNombreUsuario()+ " "+ solicitud.getUsuarioControlado().getApellidoUsuario());
        nombreApellidoTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25); // Tamaño de texto en sp
        nombreApellidoTextView.setTextColor(ContextCompat.getColor(this, R.color.black)); // Color de texto
        RelativeLayout.LayoutParams nombreApellidoParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        nombreApellidoParams.addRule(RelativeLayout.BELOW, usuarioTextView.getId());
        nombreApellidoTextView.setLayoutParams(nombreApellidoParams);

        // Crea una ImageView para el ícono con un OnClickListener
        ImageView iconImageView = new ImageView(this);
        iconImageView.setLayoutParams(params); // Usar los mismos parámetros de diseño que el RelativeLayout
        iconImageView.setImageResource(R.drawable.tacho); // Reemplaza 'tu_icono' con el recurso de imagen deseado
        iconImageView.setId(View.generateViewId()); // Genera un ID único para la ImageView
        RelativeLayout.LayoutParams iconParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        iconParams.addRule(RelativeLayout.ALIGN_PARENT_END);
        iconParams.addRule(RelativeLayout.CENTER_VERTICAL);
        iconImageView.setLayoutParams(iconParams);
        iconImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminarSolicitud(solicitud);
            }
        });

        // Agrega los TextViews y la ImageView al RelativeLayout
        relativeLayout.addView(usuarioTextView);
        relativeLayout.addView(nombreApellidoTextView);
        relativeLayout.addView(iconImageView);

        return relativeLayout;
    }

    private void eliminarSolicitud(Solicitud solicitud) {
        // Implementa la lógica para eliminar la solicitud de la lista y actualizar la vista
    }
}