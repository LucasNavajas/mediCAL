package com.example.medical;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.annotation.SuppressLint;

import com.example.medical.FiltrosDeEditText.TextOnlyInputFilter;
import com.example.medical.adapter.ConsejoAdapter;
import com.example.medical.model.Calendario;
import com.example.medical.model.Consejo;
import com.example.medical.model.Usuario;
import com.example.medical.retrofit.CalendarioApi;
import com.example.medical.retrofit.ConsejoApi;
import com.example.medical.retrofit.RetrofitService;
import com.example.medical.retrofit.UsuarioApi;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.util.List;


public class MasActivity extends AppCompatActivity {
    private RetrofitService retrofitService = new RetrofitService();
    private UsuarioApi usuarioApi = retrofitService.getRetrofit().create(UsuarioApi.class);

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
    private LinearLayout medicamentos;
    private ImageButton medicamentosimagen;

    private TextView perfilUsuario;

    private RecyclerView recyclerView;
    private PopupWindow popupWindow;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private int codUsuarioLogeado;

    private RelativeLayout rectangleMedYSintomas;
    private RelativeLayout rectangleInformes;
    private RelativeLayout rectangleInventario;
    private RelativeLayout rectangleSobreNosotros;
    private Calendario calendarioSeleccionado;
    private Usuario usuarioInstance;
    private int codCalendario;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.n24_mas);
        Intent intent1 = getIntent();
        codCalendario = intent1.getIntExtra("calendarioSeleccionadoid", 0);
        Log.d("MiApp", "codCalendario en MasActivity: " + codCalendario); // Agregar este log

        codUsuarioLogeado = getIntent().getIntExtra("codUsuario", 0);
        Log.d("MiApp", "codUsuario en MasActivity: " + codUsuarioLogeado);
        inicializarVariables();


        // Obtén la referencia al RelativeLayout
        rectangleMedYSintomas = findViewById(R.id.rectangle_med_y_sintomas);
        rectangleInformes = findViewById(R.id.rectangle_informes);
        rectangleInventario = findViewById(R.id.rectangle_inventario);
        rectangleSobreNosotros = findViewById(R.id.rectangle_sobre_nosotros);

        // Configura el OnClickListener para el RelativeLayout
        rectangleMedYSintomas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Aquí maneja la acción de navegación a AgregarSeguimientoActivity
                Intent intent = new Intent(MasActivity.this, AgregarSeguimientoActivity.class);
                intent.putExtra("codUsuario", codUsuarioLogeado);
                intent.putExtra("calendarioSeleccionadoid", codCalendario);
                startActivity(intent);
            }
        });

        rectangleInformes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Aquí maneja la acción de navegación a SobreNosotrosActivity
                Intent intent = new Intent(MasActivity.this, ReportesActivity.class);
                intent.putExtra("codUsuario", codUsuarioLogeado);
                intent.putExtra("calendarioSeleccionadoid", codCalendario);
                startActivity(intent);
            }
        });

        rectangleInventario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Aquí maneja la acción de navegación a SobreNosotrosActivity
                Intent intent = new Intent(MasActivity.this, InventarioMedicamentosActivity.class);
                intent.putExtra("codUsuario", codUsuarioLogeado);
                intent.putExtra("calendarioSeleccionadoid", codCalendario);
                startActivity(intent);
            }
        });

        rectangleSobreNosotros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Aquí maneja la acción de navegación a SobreNosotrosActivity
                Intent intent = new Intent(MasActivity.this, SobreNosotrosActivity.class);
                intent.putExtra("codUsuario", codUsuarioLogeado);
                intent.putExtra("calendarioSeleccionadoid", codCalendario);
                startActivity(intent);
            }
        });


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
                Intent intent = new Intent(MasActivity.this, EditarPerfilUsuarioActivity.class);
                intent.putExtra("codUsuario", codUsuarioLogeado);
                startActivity(intent);
            }
        });

        soporte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MasActivity.this, FAQActivity.class);
                startActivity(intent);
            }
        });

        restablecerContrasenia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MasActivity.this, RestablecerContraseniaActivity.class);
                intent.putExtra("codUsuario", codUsuarioLogeado);
                intent.putExtra("codCalendario", codCalendario);
                startActivity(intent);

            }
        });
        eliminarCuenta.setOnClickListener(view ->{
            popupEliminarCuenta();
        });

        casa_inicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MasActivity.this, InicioCalendarioActivity.class);
                intent.putExtra("codUsuario", codUsuarioLogeado);
                intent.putExtra("codCalendario", codCalendario);
                startActivity(intent);

            }
        });

        imageninicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MasActivity.this, InicioCalendarioActivity.class);
                intent.putExtra("codUsuario", codUsuarioLogeado);
                intent.putExtra("codCalendario", codCalendario);
                startActivity(intent);

            }
        });

        imagenconsejos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MasActivity.this, ConsejosActivity.class);
                intent.putExtra("codUsuario", codUsuarioLogeado);
                intent.putExtra("calendarioSeleccionadoid", codCalendario);
                startActivity(intent);

            }
        });
        medicamentosimagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MasActivity.this, MedicamentosActivity.class);
                intent.putExtra("codUsuario", codUsuarioLogeado);
                intent.putExtra("calendarioSeleccionadoid", codCalendario);
                startActivity(intent);
            }
        });

        medicamentos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MasActivity.this, MedicamentosActivity.class);
                intent.putExtra("codUsuario", codUsuarioLogeado);
                intent.putExtra("calendarioSeleccionadoid", codCalendario);
                startActivity(intent);
            }
        });


    }



    //@SuppressLint("WrongViewCast") ?
    @SuppressLint("WrongViewCast")
    private void inicializarVariables() {
        nombreUsuario = findViewById(R.id.nombre_usuario);
        nombre = findViewById(R.id.nombre);
        editarPerfil = findViewById(R.id.editar_perfil);
        restablecerContrasenia = findViewById(R.id.restablecer_contrasenia);
        cerrarSesion = findViewById(R.id.cerrar_sesion);
        soporte = findViewById(R.id.soporte);
        casa_inicio = findViewById(R.id.inicio);
        imageninicio =findViewById(R.id.imageninicio);
        medicamentos = findViewById(R.id.seccionmedicamentos);
        medicamentosimagen = findViewById(R.id.medicamentosImagenSeccion);
        imagenconsejos =findViewById(R.id.imagenconsejos);
        menuButton = findViewById(R.id.menu_button);
        menuButtonUsuario = findViewById(R.id.menu_button_nav);
        eliminarCuenta = findViewById(R.id.eliminar_cuenta);
        perfilUsuario = findViewById(R.id.perfil_usuario);
        usuarioApi.getByCodUsuario(codUsuarioLogeado).enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                usuarioInstance=response.body();
                if(usuarioInstance.getPerfil()!=null) {
                    perfilUsuario.setText(usuarioInstance.getPerfil().getNombrePerfil());
                }
                nombreUsuario.setText(usuarioInstance.getUsuarioUnico());
                nombre.setText(usuarioInstance.getUsuarioUnico());

            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Toast.makeText(MasActivity.this, "Error al cargar el usuario", Toast.LENGTH_SHORT).show();
            }
        });
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
                Intent intent = new Intent(MasActivity.this, BienvenidoActivity.class);
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

        aceptar.setOnClickListener(view ->{
            String textMotivo = motivo.getText().toString();
            usuarioApi.eliminarUsuario(codUsuarioLogeado, textMotivo).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    mAuth.signOut();
                    Intent intent = new Intent(MasActivity.this, BienvenidoActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }
                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(MasActivity.this, "Error al eliminar la cuenta, intente nuevamente", Toast.LENGTH_SHORT).show();
                }
            });
        });

        cancelar.setOnClickListener(view ->{
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



