package com.example.medical;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.medical.model.Calendario;
import com.example.medical.model.PerfilPermiso;
import com.example.medical.model.Usuario;
import com.example.medical.retrofit.CalendarioApi;
import com.example.medical.retrofit.PerfilPermisoApi;
import com.example.medical.retrofit.ReporteApi;
import com.example.medical.retrofit.RetrofitService;
import com.example.medical.retrofit.UsuarioApi;
import com.google.gson.Gson;

import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditarCalendarioEnfermeroActivity extends AppCompatActivity {

    private TextView nombreCalendario;
    private TextView pacienteCalendario;
    private ImageView botonCerrar;
    private ImageView editarNombre;
    private ImageView editarPaciente;
    private Button eliminarCalendario;
    private Button guardar;
    private Calendario calendario;
    private RetrofitService retrofitService = new RetrofitService();
    private List<PerfilPermiso> permisos;
    private List<Integer> codigosPermisos;
    private UsuarioApi usuarioApi = retrofitService.getRetrofit().create(UsuarioApi.class);
    private PerfilPermisoApi perfilPermisoApi = retrofitService.getRetrofit().create(PerfilPermisoApi.class);
    private CalendarioApi calendarioApi = retrofitService.getRetrofit().create(CalendarioApi.class);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.n16_editar_calendario_enfermero);//cambiar esta linea por el nombre del layout a probar
        inicializarVariables();

        botonCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (EditarCalendarioEnfermeroActivity.this, InicioCalendarioActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
        editarNombre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditarCalendarioEnfermeroActivity.this, CrearCalendario2Activity.class);
                String jsonCalendario = new Gson().toJson(calendario);
                intent.putExtra("calendarioJson", jsonCalendario);
                intent.putExtra("perfilPaciente", "true");
                intent.putExtra("codUsuario", getIntent().getIntExtra("codUsuario",0));
                startActivity(intent);
            }
        });

        editarPaciente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditarCalendarioEnfermeroActivity.this, CrearCalendario2Activity.class);
                String jsonCalendario = new Gson().toJson(calendario);
                intent.putExtra("calendarioJson", jsonCalendario);
                intent.putExtra("perfilPaciente", "true");
                intent.putExtra("editarPaciente", "true");
                intent.putExtra("codUsuario", getIntent().getIntExtra("codUsuario",0));
                startActivity(intent);
            }
        });

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendario calendarioTemp = new Calendario();
                calendarioTemp.setCodCalendario(calendario.getCodCalendario());
                calendarioTemp.setNombreCalendario(calendario.getNombreCalendario());
                calendarioTemp.setRelacionCalendario(calendario.getRelacionCalendario());
                calendarioTemp.setNombrePaciente(calendario.getNombrePaciente());
                calendarioApi.modificarCalendario(calendarioTemp.getCodCalendario(), calendarioTemp).enqueue(new Callback<Calendario>() {
                    @Override
                    public void onResponse(Call<Calendario> call, Response<Calendario> response) {
                        Intent intent = new Intent (EditarCalendarioEnfermeroActivity.this, InicioCalendarioActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                        Log.d("MiApp","Calendario guardado exitosamente: " + calendarioTemp);
                    }

                    @Override
                    public void onFailure(Call<Calendario> call, Throwable t) {
                        Toast.makeText(EditarCalendarioEnfermeroActivity.this, "Hubo un error al guardar el calendario, intente nuevamente", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        eliminarCalendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupEliminarCalendario();
            }
        });
    }

    private void popupEliminarCalendario() {
        View popupView = getLayoutInflater().inflate(R.layout.n17_1_popup_eliminar_calendario, null);


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
                calendarioApi.eliminarCalendario(calendario.getCodCalendario()).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Intent intent = new Intent (EditarCalendarioEnfermeroActivity.this, InicioCalendarioActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(EditarCalendarioEnfermeroActivity.this, "Hubo un error al eliminar el calendario, intente nuevamente", Toast.LENGTH_SHORT).show();
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

    private void inicializarVariables() {
        String jsonCalendario = getIntent().getStringExtra("calendarioJson");
        calendario = new Gson().fromJson(jsonCalendario, Calendario.class);
        nombreCalendario = findViewById(R.id.textEdit_NombreCalendario);
        pacienteCalendario = findViewById(R.id.textEdit_nombrePaciente);
        editarNombre = findViewById(R.id.lapiz_editar_NombreCalendario);
        editarPaciente = findViewById(R.id.lapiz_editar_nombrePaciente);
        botonCerrar = findViewById(R.id.boton_cerrar);
        guardar = findViewById(R.id.button_guardar);
        eliminarCalendario = findViewById(R.id.button_eliminarCalendario);
        nombreCalendario.setText(calendario.getNombreCalendario());
        pacienteCalendario.setText(calendario.getNombrePaciente());
        inicializarUsuario();
    }

    private void inicializarUsuario() {
        usuarioApi.getByCodUsuario(getIntent().getIntExtra("codUsuario",0)).enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                verificarPermisos(response.body());
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Log.d("Error en la obtencion de los permisos", "Error en permisos");
            }
        });
    }
    private void verificarPermisos(Usuario usuarioLogeado) {
        perfilPermisoApi.getByCodPerfil(usuarioLogeado.getPerfil().getCodPerfil()).enqueue(new Callback<List<PerfilPermiso>>() {
            @Override
            public void onResponse(Call<List<PerfilPermiso>> call, Response<List<PerfilPermiso>> response) {
                permisos = response.body();
                codigosPermisos = permisos.stream()
                        .map(perfilPermiso -> perfilPermiso.getPermiso().getCodPermiso())
                        .collect(Collectors.toList());
                if(!codigosPermisos.contains(3)){
                    eliminarCalendario.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<PerfilPermiso>> call, Throwable t) {
                Log.d("Error en la obtencion de los permisos", "Error en permisos");
            }
        });
    }
}
