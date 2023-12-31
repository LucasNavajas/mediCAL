package com.example.medical;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.medical.model.Perfil;
import com.example.medical.model.Usuario;
import com.example.medical.retrofit.PerfilApi;
import com.example.medical.retrofit.RetrofitService;
import com.example.medical.retrofit.UsuarioApi;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.time.LocalDate;
import java.time.Period;
import java.util.logging.Level;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CrearCuenta4Activity extends AppCompatActivity {
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.n08_crear_cuenta_paso4);

        ImageView buttonVolver = findViewById(R.id.boton_volver);

        RetrofitService retrofitService = new RetrofitService();
        UsuarioApi usuarioApi = retrofitService.getRetrofit().create(UsuarioApi.class);
        PerfilApi perfilApi = retrofitService.getRetrofit().create(PerfilApi.class);

        Intent intent = getIntent();
        Button buttonSiguiente = findViewById(R.id.button_siguiente);

        buttonSiguiente.setOnClickListener(view -> {
            DatePicker datePicker = (DatePicker) findViewById(R.id.datePicker);
            int day = datePicker.getDayOfMonth();
            int month = datePicker.getMonth() + 1;
            int year = datePicker.getYear();


            LocalDate fechaNacimiento = LocalDate.of(year, month, day);


            LocalDate currentDate = LocalDate.now();
            Period period = Period.between(fechaNacimiento, currentDate);
            int age = period.getYears();

            if (age >= 18) {
            String nombreUsuario = intent.getStringExtra("nombre");
            LocalDate fechaAltaUsuario = LocalDate.now();
            String apellidoUsuario = intent.getStringExtra("apellido");
            String contrasenia = intent.getStringExtra("contrasenia");
            String generoUsuario = intent.getStringExtra("genero");
            String mailUsuario = intent.getStringExtra("mail");
            String usuarioUnico = intent.getStringExtra("usuario");
            String telefono = intent.getStringExtra("telefono");
            int codUsuario = intent.getIntExtra("codusuario",0);

            Usuario usuario = new Usuario();
            usuario.setCodUsuario(codUsuario);
            usuario.setNombreUsuario(nombreUsuario);
            usuario.setApellidoUsuario(apellidoUsuario);
            usuario.setContraseniaUsuario(contrasenia);
            usuario.setFechaAltaUsuario(fechaAltaUsuario);
            usuario.setFechaNacimientoUsuario(fechaNacimiento);
            usuario.setGeneroUsuario(generoUsuario);
            usuario.setMailUsuario(mailUsuario);
            usuario.setTelefonoUsuario(telefono);
            usuario.setUsuarioUnico(usuarioUnico);

            perfilApi.findByCodPerfil(1).enqueue(new Callback<Perfil>() {
                @Override
                public void onResponse(Call<Perfil> call, Response<Perfil> response) {
                    usuario.setPerfil(response.body());
                    usuarioApi.save(usuario).enqueue(new Callback<Usuario>() {
                        @Override
                        public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                            // Aquí puedes verificar la respuesta del servidor
                            if (response.isSuccessful()) {
                                Intent intent2 = new Intent(CrearCuenta4Activity.this, BienvenidoUsuarioActivity.class);
                                intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent2.putExtra("usuario", intent.getStringExtra("usuario"));
                                intent2.putExtra("mail", mailUsuario);
                                intent2.putExtra("contrasenia", contrasenia);
                                startActivity(intent2);
                                finish();
                            } else {
                                Toast.makeText(CrearCuenta4Activity.this, "Error al crear la cuenta", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Usuario> call, Throwable t) {
                            Toast.makeText(CrearCuenta4Activity.this, "Error al crear la cuenta", Toast.LENGTH_SHORT).show();
                            Logger.getLogger(CrearCuenta4Activity.class.getName()).log(Level.SEVERE, "Error occurred");
                        }
                    });
                }

                @Override
                public void onFailure(Call<Perfil> call, Throwable t) {
                    Toast.makeText(CrearCuenta4Activity.this, "Error al fijar el perfil", Toast.LENGTH_SHORT).show();
                    Logger.getLogger(CrearCuenta4Activity.class.getName()).log(Level.SEVERE, "Error occurred");
                    }
                });

            }
            else{
                Toast.makeText(CrearCuenta4Activity.this, "Debe tener al menos 18 años para crear una cuenta", Toast.LENGTH_SHORT).show();
            }
        });



        buttonVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    @Override
    public void onDestroy() {
        mAuth.signOut();
        super.onDestroy();
    }
}
