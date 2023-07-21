package com.example.medical;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.medical.model.Usuario;
import com.example.medical.retrofit.RetrofitService;
import com.example.medical.retrofit.UsuarioApi;

import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CrearCuenta4Activity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.n08_crear_cuenta_paso4);

        ImageView buttonVolver = findViewById(R.id.boton_volver);

        RetrofitService retrofitService = new RetrofitService();
        UsuarioApi usuarioApi = retrofitService.getRetrofit().create(UsuarioApi.class);

        Intent intent = getIntent();
        Button buttonSiguiente = findViewById(R.id.button_siguiente);

        buttonSiguiente.setOnClickListener(view -> {
            DatePicker datePicker = (DatePicker) findViewById(R.id.datePicker);
            int day = datePicker.getDayOfMonth();
            int month = datePicker.getMonth() + 1;
            int year = datePicker.getYear();

            LocalDate fechaNacimiento = LocalDate.of(year, month, day);
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

            // Llamar al método modificarUsuario en la interfaz UsuarioApi
            Call<Usuario> call = usuarioApi.modificarUsuario(usuario);

            call.enqueue(new Callback<Usuario>() {
                @Override
                public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                    // Aquí puedes verificar la respuesta del servidor
                    if (response.isSuccessful()) {
                        Toast.makeText(CrearCuenta4Activity.this, "Usuario modificado correctamente", Toast.LENGTH_SHORT).show();
                        Intent intent2 = new Intent(CrearCuenta4Activity.this, BienvenidoUsuarioActivity.class);
                        intent2.putExtra("usuario", intent.getStringExtra("usuario"));
                        startActivity(intent2);
                    } else {
                        Toast.makeText(CrearCuenta4Activity.this, "Error al modificar el usuario", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Usuario> call, Throwable t) {
                    Toast.makeText(CrearCuenta4Activity.this, "Error al crear el usuario", Toast.LENGTH_SHORT).show();
                    Logger.getLogger(CrearCuenta4Activity.class.getName()).log(Level.SEVERE, "Error occurred");
                }
            });
        });

        buttonVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
}
