package com.example.medical;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.medical.model.Calendario;
import com.example.medical.model.Usuario;
import com.example.medical.retrofit.CalendarioApi;
import com.example.medical.retrofit.CodigoVerificacionApi;
import com.example.medical.retrofit.RetrofitService;
import com.example.medical.retrofit.UsuarioApi;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.time.LocalDate;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CrearCalendario2Activity extends AppCompatActivity {
    private ImageView botonVolver;
    private Button siguiente;
    private EditText nombreCalendario;
    private TextView contadorLetras;
    private FirebaseAuth mAuth;
    private RetrofitService retrofitService = new RetrofitService();
    private CalendarioApi calendarioApi = retrofitService.getRetrofit().create(CalendarioApi.class);
    private UsuarioApi usuarioApi = retrofitService.getRetrofit().create(UsuarioApi.class);
    private Usuario usuarioActual;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.n19_nombre_calendario);//cambiar esta linea por el nombre del layout a probar
        inicializarVariables();
        obtenerUsuarioPorMail();
        Intent intent1 = getIntent();

        siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombreCalendarioString = nombreCalendario.getText().toString();
                if(nombreCalendarioString != "" && usuarioActual != null){
                    Calendario calendario = new Calendario();
                    calendario.setRelacionCalendario(intent1.getStringExtra("relacion"));
                    calendario.setNombreCalendario(nombreCalendarioString);
                    calendario.setUsuario(usuarioActual);
                    calendario.setFechaAltaCalendario(LocalDate.now());
                    calendarioApi.save(calendario).enqueue(new Callback<Calendario>() {
                        @Override
                        public void onResponse(Call<Calendario> call, Response<Calendario> response) {
                            Intent intent = new Intent(CrearCalendario2Activity.this, CalendarioCreadoActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra("nombreCalendario", nombreCalendarioString);
                            startActivity(intent);
                            finish(); // Opcional: finaliza la actividad actual si ya no la necesitas en el back stack
                        }

                        @Override
                        public void onFailure(Call<Calendario> call, Throwable t) {
                            Toast.makeText(CrearCalendario2Activity.this, "Error al crear el calendario", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });

        botonVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void obtenerUsuarioPorMail() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            usuarioApi.getByMailUsuario(currentUser.getEmail()).enqueue(new Callback<Usuario>() {
                @Override
                public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                    usuarioActual = response.body();
                }

                @Override
                public void onFailure(Call<Usuario> call, Throwable t) {
                    Toast.makeText(CrearCalendario2Activity.this, "Error con el servidor al encontrar el usuario en sesión", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(CrearCalendario2Activity.this, "Usuario no autenticado", Toast.LENGTH_SHORT).show();
        }
    }

    private void inicializarVariables() {
        botonVolver = findViewById(R.id.boton_volver);
        siguiente = findViewById(R.id.button_siguiente);
        nombreCalendario = findViewById(R.id.editText);
        contadorLetras = findViewById(R.id.characterCount);
        mAuth = FirebaseAuth.getInstance();
    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null){
            Intent intent = new Intent(this, BienvenidoActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }
    }
}
