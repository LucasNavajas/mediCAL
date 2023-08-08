package com.example.medical;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
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

import org.w3c.dom.Text;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


public class EditarPerfilUsuarioActivity extends AppCompatActivity {

    private EditText textEditNombreUsuario;
    private EditText textEditApellidoUsuario;
    private TextView textEditFechaNac;
    private EditText textEditEmail;
    private EditText textEditTelefono;
    private EditText textEditGenero;
    private Button buttonGuardar;
    private ImageView botonCerrar;

    private RetrofitService retrofitService;
    private UsuarioApi usuarioApi;

    private DateTimeFormatter formatter;
    private Usuario usuario;
    private LocalDate fechaNacimiento;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.n15_0_editar_perfil_usuario);
        Intent intent1 = getIntent();
        inicializarVariables();

        // Se formatea la fecha en el formato deseado
        formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");

        textEditNombreUsuario.setFilters(new InputFilter[]{new TextOnlyInputFilter()});
        textEditApellidoUsuario.setFilters(new InputFilter[]{new TextOnlyInputFilter()});

        // Se supone se tiene el ID del usuario actual almacenado en una variable llamada "idUsuarioActual".
        obtenerDatosUsuario(intent1.getIntExtra("codUsuario",0));

        buttonGuardar.setOnClickListener(view -> {
            guardarCambios();
        });

        botonCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        textEditFechaNac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupFechaNacimiento();
            }
        });

    }

    private void popupFechaNacimiento() {
        View popupView = getLayoutInflater().inflate(R.layout.n15_3_popup_fecha_nacimiento, null);

        // Crear la instancia de PopupWindow
        PopupWindow popupWindow = new PopupWindow(popupView, 1000, 1000);

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

        DatePicker datePicker = popupView.findViewById(R.id.datePicker);
        TextView cancelar = popupView.findViewById(R.id.cancelar);
        TextView aceptar = popupView.findViewById(R.id.aceptar);
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ocultar el PopupWindow
                popupWindow.dismiss();

                // Ocultar el fondo oscurecido
                dimView.setVisibility(View.GONE);
            }
        });

        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth() + 1;
                int year = datePicker.getYear();

                fechaNacimiento = LocalDate.of(year, month, day);
                textEditFechaNac.setText(fechaNacimiento.toString());
                // Ocultar el PopupWindow
                popupWindow.dismiss();

                // Ocultar el fondo oscurecido
                dimView.setVisibility(View.GONE);
            }
        });

    }


    private void obtenerDatosUsuario(int idUsuario) {
        Call<Usuario> call = usuarioApi.getByCodUsuario(idUsuario);
        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful()) {
                    usuario = response.body();
                    if (usuario != null) {
                        // Llenar los campos de texto con los datos del usuario
                        textEditNombreUsuario.setText(usuario.getNombreUsuario());
                        textEditApellidoUsuario.setText(usuario.getApellidoUsuario());
                        textEditFechaNac.setText(usuario.getFechaNacimientoUsuario().toString());
                        // String fechaNacimientoString = usuario.getFechaNacimientoUsuario().format(formatter);
                        textEditEmail.setText(usuario.getMailUsuario());
                        textEditTelefono.setText(usuario.getTelefonoUsuario());
                        textEditGenero.setText(usuario.getGeneroUsuario());
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


    //@SuppressLint("WrongViewCast")
    private void inicializarVariables() {
        textEditNombreUsuario = findViewById(R.id.textEdit_nombre_usuario);
        textEditApellidoUsuario = findViewById(R.id.textEdit_apellido_usuario);
        textEditFechaNac = findViewById(R.id.textEdit_fecha_nac);
        textEditEmail = findViewById(R.id.textEdit_email);
        textEditTelefono = findViewById(R.id.textEdit_telefono);

        textEditGenero = findViewById(R.id.textEdit_Mujer);
        buttonGuardar = findViewById(R.id.button_guardar);
        botonCerrar = findViewById(R.id.boton_cerrar);

        retrofitService = new RetrofitService();
        usuarioApi = retrofitService.getRetrofit().create(UsuarioApi.class);
    }

    private void guardarCambios() {
        // Obtener los nuevos datos ingresados por el usuario
        String nuevoNombre = textEditNombreUsuario.getText().toString();
        String nuevoApellido = textEditApellidoUsuario.getText().toString();
        // Revisar
        //String nuevaFechaNac = textEditFechaNac.getText().toString();
        String nuevoEmail = textEditEmail.getText().toString();
        String nuevoTelefono = textEditTelefono.getText().toString();

        // Aquí puedes crear un objeto Usuario con los nuevos datos ingresados por el usuario.
        Usuario usuarioModificado = usuario;
        usuarioModificado.setNombreUsuario(nuevoNombre);
        usuarioModificado.setApellidoUsuario(nuevoApellido);
        usuarioModificado.setFechaNacimientoUsuario(fechaNacimiento);
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
