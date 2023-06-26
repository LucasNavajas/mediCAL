package com.example.medical;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.medical.model.Usuario;
import com.example.medical.retrofit.RetrofitService;
import com.example.medical.retrofit.UsuarioApi;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BienvenidoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bienvenido);
        initializeComponents();
    }

    private void initializeComponents(){
        RetrofitService retrofitService = new RetrofitService();
        UsuarioApi usuarioApi = retrofitService.getRetrofit().create(UsuarioApi.class);

        Button buttonIngresar = findViewById(R.id.button_ingresar);

        buttonIngresar.setOnClickListener(view -> {
            Intent intent = new Intent(BienvenidoActivity.this, CodigoInvalidoActivity.class);
            startActivity(intent);
        });
        Button buttonCrear = findViewById(R.id.button_crear);
        buttonCrear.setOnClickListener(view -> {
                    Intent intent = new Intent(BienvenidoActivity.this, CrearCuenta1Activity.class);
                    startActivity(intent);
                });
/*

            String nombre_usuario = "usuario1";
            String nombre = "Lucas";
            String apellido = "Navajas";

            // Assuming you have a Connection object named 'connection'
            LocalDate fechaNacimiento = LocalDate.of(2001, 4, 3);


            // Obtener la fecha de hoy
            LocalDate fechaHoy = LocalDate.now();

            String contrasenia = "123";
            String genero = "Hombre";
            String mail = "lucastobiasnavajas@gmail.com";
            String institucion = "Hospital1";
            String telefono = "3756409326";
            Usuario usuario = new Usuario();
            usuario.setUsuarioUnico(nombre_usuario);
            usuario.setFechaNacimientoUsuario(fechaNacimiento);
            usuario.setFechaAltaUsuario(fechaHoy);
            usuario.setApellidoUsuario(apellido);
            usuario.setContraseniaUsuario(contrasenia);
            usuario.setGeneroUsuario(genero);
            usuario.setMailUsuario(mail);
            usuario.setNombreInstitucion(institucion);
            usuario.setNombreUsuario(nombre);
            usuario.setTelefonoUsuario(telefono);

            usuarioApi.save(usuario)
                    .enqueue(new Callback<Usuario>() {
                        @Override
                        public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                            Toast.makeText(BienvenidoActivity.this, "Guardado exitosamente", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<Usuario> call, Throwable t) {
                            Toast.makeText(BienvenidoActivity.this, "Error al guardar", Toast.LENGTH_SHORT).show();
                            Logger.getLogger(BienvenidoActivity.class.getName()).log(Level.SEVERE, "Error ocurred", t);
                        }
                    });

        });*/
    }
}
