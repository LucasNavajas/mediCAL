package com.example.medical;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.medical.model.Calendario;
import com.example.medical.model.Usuario;
import com.example.medical.retrofit.CalendarioApi;
import com.example.medical.retrofit.CodigoVerificacionApi;
import com.example.medical.retrofit.RetrofitService;
import com.example.medical.retrofit.UsuarioApi;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;

import java.time.LocalDate;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CrearCalendario2Activity extends AppCompatActivity {
    private ImageView botonVolver;
    private Button siguiente;
    private EditText nombreCalendario;
    private EditText nombrePaciente;
    private TextView contadorLetras;
    private TextView contadorLetras2;
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
        String jsonCalendario = getIntent().getStringExtra("calendarioJson");
        Calendario calendario = new Gson().fromJson(jsonCalendario, Calendario.class);
        if (getIntent().getStringExtra("calendarioJson") != null) {
            nombreCalendario.setText(calendario.getNombreCalendario());
            contadorLetras.setText(calendario.getNombreCalendario().length()+"/30");
        }
        if (getIntent().getStringExtra("perfilPaciente") != null && getIntent().getStringExtra("editarPaciente") != null) {
            RelativeLayout cuadroNombreCalendario = findViewById(R.id.cuadro_nombre_calendario);
            cuadroNombreCalendario.setVisibility(View.GONE);
            RelativeLayout cuadroNombrePaciente = findViewById(R.id.cuadro_nombre_paciente);
            cuadroNombrePaciente.setVisibility(View.VISIBLE);
            nombrePaciente.setText(calendario.getNombrePaciente());
            contadorLetras2.setText(calendario.getNombrePaciente().length()+"/30");
            TextView titulo = findViewById(R.id.text_nombrecalendario);
            titulo.setText("Nombre el Paciente");
        }

        siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombreCalendarioString = nombreCalendario.getText().toString();
                String nombrePacienteString = nombrePaciente.getText().toString();
                if (getIntent().getStringExtra("calendarioJson") == null) {
                    if (nombreCalendarioString != "" && usuarioActual != null) {
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
                                intent.putExtra("codCalendario", response.body().getCodCalendario());
                                startActivity(intent);
                                finish(); // Opcional: finaliza la actividad actual si ya no la necesitas en el back stack
                            }

                            @Override
                            public void onFailure(Call<Calendario> call, Throwable t) {
                                Toast.makeText(CrearCalendario2Activity.this, "Error al crear el calendario", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                }else if (getIntent().getStringExtra("perfilPaciente") != null){
                    if (getIntent().getStringExtra("editarPaciente") != null) {
                        calendario.setNombrePaciente(nombrePacienteString);
                        Intent intent = new Intent(CrearCalendario2Activity.this, EditarCalendarioEnfermeroActivity.class);
                        String jsonCalendario2 = new Gson().toJson(calendario);
                        intent.putExtra("calendarioJson", jsonCalendario2);
                        intent.putExtra("codUsuario", getIntent().getIntExtra("codUsuario",0));
                        startActivity(intent);
                    }
                    calendario.setNombreCalendario(nombreCalendarioString);
                    Intent intent = new Intent(CrearCalendario2Activity.this, EditarCalendarioEnfermeroActivity.class);
                    String jsonCalendario2 = new Gson().toJson(calendario);
                    intent.putExtra("calendarioJson", jsonCalendario2);
                    intent.putExtra("codUsuario", getIntent().getIntExtra("codUsuario",0));
                    startActivity(intent);
                } else {
                    calendario.setNombreCalendario(nombreCalendarioString);
                    Intent intent = new Intent(CrearCalendario2Activity.this, EditarCalendarioActivity.class);
                    String jsonCalendario2 = new Gson().toJson(calendario);
                    intent.putExtra("calendarioJson", jsonCalendario2);
                    intent.putExtra("codUsuario", getIntent().getIntExtra("codUsuario",0));
                    startActivity(intent);
                }
            }
        });

        botonVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        nombreCalendario.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                String newText = editable.toString();
                contadorLetras.setText(newText.length()+"/30");
            }
        });

        nombrePaciente.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                String newText = editable.toString();
                contadorLetras.setText(newText.length()+"/30");
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
        nombrePaciente = findViewById(R.id.editText2);
        contadorLetras = findViewById(R.id.characterCount1);
        contadorLetras2 = findViewById(R.id.characterCount2);
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
