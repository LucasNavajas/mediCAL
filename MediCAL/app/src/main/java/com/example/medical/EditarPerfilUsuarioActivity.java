package com.example.medical;

import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.annotation.SuppressLint;

import com.example.medical.FiltrosDeEditText.TextOnlyInputFilter;
import com.example.medical.model.Usuario;
import com.example.medical.retrofit.RetrofitService;
import com.example.medical.retrofit.UsuarioApi;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


public class EditarPerfilUsuarioActivity extends AppCompatActivity {

    private EditText textEditNombreUsuario;
    private EditText textEditApellidoUsuario;
    private EditText textEditFechaNac;
    private EditText textEditEmail;
    private EditText textEditTelefono;
    private Button buttonGuardar;
    private Button botonCerrar;

    private RetrofitService retrofitService;
    private UsuarioApi usuarioApi;

    private DateTimeFormatter formatter;
    private Usuario usuario;

    // Se debe obtener la id del usuario actual a través de su SESIÓN
    private int idUsuarioActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.n15_0_editar_perfil_usuario);

        inicializarVariables();

        // Se formatea la fecha en el formato deseado
        formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");

        textEditNombreUsuario.setFilters(new InputFilter[]{new TextOnlyInputFilter()});
        textEditApellidoUsuario.setFilters(new InputFilter[]{new TextOnlyInputFilter()});

        // Se supone se tiene el ID del usuario actual almacenado en una variable llamada "idUsuarioActual".
        obtenerDatosUsuario(idUsuarioActual);

        buttonGuardar.setOnClickListener(view -> {
            guardarCambios();
            // Obtiene el nuevo valor del campo de texto
            String nuevaFechaNacString = textEditFechaNac.getText().toString();

            // Convierte el nuevo valor a un objeto LocalDate
            try {
                LocalDate nuevaFechaNac = LocalDate.parse(nuevaFechaNacString, formatter);

                // Guarda el nuevo objeto LocalDate en tu objeto Usuario
                usuario.setFechaNacimientoUsuario(nuevaFechaNac);

                // Luego puedes llamar a tu función para guardar los cambios en la base de datos
                // guardarCambiosEnLaBaseDeDatos();

                // Muestra un mensaje de éxito
                Toast.makeText(getApplicationContext(), "Cambios guardados", Toast.LENGTH_SHORT).show();
            } catch (DateTimeParseException e) {
                // Si el formato de fecha ingresado por el usuario es incorrecto, muestra un mensaje de error
                Toast.makeText(getApplicationContext(), "Formato de fecha incorrecto", Toast.LENGTH_SHORT).show();
            }
        });

        botonCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }


    private void obtenerDatosUsuario(int idUsuario) {
        Call<Usuario> call = usuarioApi.getByCodUsuario(idUsuario);
        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful()) {
                    Usuario usuario = response.body();
                    if (usuario != null) {
                        // Llenar los campos de texto con los datos del usuario
                        textEditNombreUsuario.setText(usuario.getNombreUsuario());
                        textEditApellidoUsuario.setText(usuario.getApellidoUsuario());
                        String fechaNacimientoString = usuario.getFechaNacimientoUsuario().format(formatter);
                        textEditEmail.setText(usuario.getMailUsuario());
                        textEditTelefono.setText(usuario.getTelefonoUsuario());
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Error al obtener los datos del usuario", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error en la conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @SuppressLint("WrongViewCast")
    private void inicializarVariables() {
        textEditNombreUsuario = findViewById(R.id.textEdit_nombre_usuario);
        textEditApellidoUsuario = findViewById(R.id.textEdit_apellido_usuario);
        textEditFechaNac = findViewById(R.id.textEdit_fecha_nac);
        textEditEmail = findViewById(R.id.textEdit_email);
        textEditTelefono = findViewById(R.id.textEdit_telefono);

        buttonGuardar = findViewById(R.id.button_guardar);
        botonCerrar = findViewById(R.id.boton_cerrar);

        retrofitService = new RetrofitService();
        usuarioApi = retrofitService.getRetrofit().create(UsuarioApi.class);
    }

    private void guardarCambios() {
        // Obtener los nuevos datos ingresados por el usuario
        String nuevoNombre = textEditNombreUsuario.getText().toString();
        String nuevoApellido = textEditApellidoUsuario.getText().toString();
        String nuevaFechaNac = textEditFechaNac.getText().toString();
        String nuevoEmail = textEditEmail.getText().toString();
        String nuevoTelefono = textEditTelefono.getText().toString();

        // Aquí puedes crear un objeto Usuario con los nuevos datos ingresados por el usuario.
        Usuario usuarioModificado = new Usuario();
        usuarioModificado.setNombreUsuario(nuevoNombre);
        usuarioModificado.setApellidoUsuario(nuevoApellido);
        usuarioModificado.setFechaNacimientoUsuario(LocalDate.parse(nuevaFechaNac));
        usuarioModificado.setMailUsuario(nuevoEmail);
        usuarioModificado.setTelefonoUsuario(nuevoTelefono);

        // Llamamos al método modificarUsuario de UsuarioApi para guardar los cambios en la base de datos.
        Call<Usuario> call = usuarioApi.modificarUsuario(usuarioModificado);
        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful()) {
                    // La modificación se realizó con éxito, puedes mostrar un mensaje de éxito y cerrar la actividad.
                    Toast.makeText(getApplicationContext(), "Datos actualizados correctamente", Toast.LENGTH_SHORT).show();
                    finish(); // Cierra la actividad actual y regresa a la pantalla anterior.
                } else {
                    // Ocurrió un error al modificar los datos en la base de datos, muestra un mensaje de error.
                    Toast.makeText(getApplicationContext(), "Error al guardar los cambios", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                // Error en la conexión, muestra un mensaje de error.
                Toast.makeText(getApplicationContext(), "Error en la conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
