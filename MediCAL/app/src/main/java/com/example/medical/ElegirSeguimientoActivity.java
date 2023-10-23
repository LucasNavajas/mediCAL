package com.example.medical;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

import com.example.medical.model.Calendario;
import com.example.medical.model.PerfilPermiso;
import com.example.medical.model.Usuario;
import com.example.medical.retrofit.CalendarioApi;
import com.example.medical.retrofit.InventarioApi;
import com.example.medical.retrofit.PerfilPermisoApi;
import com.example.medical.retrofit.RetrofitService;
import com.example.medical.retrofit.UsuarioApi;

import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ElegirSeguimientoActivity extends AppCompatActivity {
    private int codCalendario;
    private List<PerfilPermiso> permisos;
    private List<Integer> codigosPermisos;
    private RetrofitService retrofitService = new RetrofitService();
    private UsuarioApi usuarioApi = retrofitService.getRetrofit().create(UsuarioApi.class);
    private PerfilPermisoApi perfilPermisoApi = retrofitService.getRetrofit().create(PerfilPermisoApi.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.n74_mediciones_sintomas);


        // Recuperar el objeto calendarioSeleccionado del Intent
        Intent intent1 = getIntent();
        codCalendario = intent1.getIntExtra("calendarioSeleccionadoid", 0);
        Log.d("MiApp", "codCalendario en ElegirSeguimientoActivity: " + codCalendario); // Agregar este log
        inicializarUsuario();

        // Configura el clic de la flecha hacia atrás
        findViewById(R.id.boton_volver).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Vuelve a la pantalla anterior (Puede ser tu pantalla 73)
                onBackPressed();
            }
        });

        // Clic en la sección de Medición
        findViewById(R.id.todo_medicion).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Iniciar la actividad AnadirMedicionActivity
                Intent intent = new Intent(ElegirSeguimientoActivity.this, AnadirMedicionActivity.class);
                intent.putExtra("codUsuario", getIntent().getIntExtra("codUsuario",0));
                intent.putExtra("calendarioSeleccionadoid", getIntent().getIntExtra("calendarioSeleccionadoid",0));
                startActivity(intent);
            }
        });

        // Clic en la sección de Síntoma
        findViewById(R.id.todo_Sintoma).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Iniciar la actividad AnadirSintomaActivity
                Intent intent = new Intent(ElegirSeguimientoActivity.this, AnadirSintomaActivity.class);
                intent.putExtra("codUsuario", getIntent().getIntExtra("codUsuario",0));
                intent.putExtra("calendarioSeleccionadoid", getIntent().getIntExtra("calendarioSeleccionadoid",0));
                startActivity(intent);
            }
        });
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
                if(!codigosPermisos.contains(4)){
                    findViewById(R.id.todo_Sintoma).setVisibility(View.GONE);
                }
                if(!codigosPermisos.contains(12)){
                    findViewById(R.id.todo_medicion).setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<PerfilPermiso>> call, Throwable t) {
                Log.d("Error en la obtencion de los permisos", "Error en permisos");
            }
        });
    }
}
