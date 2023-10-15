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
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class EditarPerfilUsuarioActivity extends AppCompatActivity {

    private EditText textEditNombreUsuario;
    private EditText textEditApellidoUsuario;
    private TextView textEditFechaNac;
    private EditText textEditEmail;
    private EditText textEditTelefono;
    private TextView textEditGenero;
    private Button buttonGuardar;
    private ImageView botonCerrar;

    private RetrofitService retrofitService;
    private UsuarioApi usuarioApi;

    private DateTimeFormatter formatter;
    private Usuario usuario;
    private LocalDate fechaNacimiento;

    private TextView errorNombre;
    private TextView errorNombre2;
    private TextView errorApellido2;
    private TextView errorApellido;
    private TextView error_Telefono;
    private TextView error_Correo;

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
        obtenerDatosUsuario(intent1.getIntExtra("codUsuario", 0));

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

        textEditGenero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupGenero_1();
            }
        });
    }

    private void popupGenero_1() {
        View popupView = getLayoutInflater().inflate(R.layout.n15_1_popup_cambiogenero_1, null);

        // Crear la instancia de PopupWindow
        PopupWindow popupWindow = new PopupWindow(popupView, 1000, ViewGroup.LayoutParams.WRAP_CONTENT);

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

        TextView opcion1 = popupView.findViewById(R.id.text_mujer);
        TextView opcion2 = popupView.findViewById(R.id.text_hombre);
        TextView opcion3= popupView.findViewById(R.id.text_nobinario);
        TextView opcion4 = popupView.findViewById(R.id.text_otro);
        TextView opcion5 = popupView.findViewById(R.id.text_nodecir);
        TextView regresar = popupView.findViewById(R.id.regresar);


        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch(view.getId()){
                    case R.id.text_otro:
                        popupWindow.dismiss();
                        popupGenero_2();
                        break;
                    default:
                        TextView textView = popupView.findViewById(view.getId());
                        String genero = textView.getText().toString();

                        textEditGenero.setText(genero);

                        // Ocultar el PopupWindow
                        popupWindow.dismiss();

                        // Ocultar el fondo oscurecido
                        dimView.setVisibility(View.GONE);
                }
            }
        };

        opcion1.setOnClickListener(onClickListener);
        opcion2.setOnClickListener(onClickListener);
        opcion3.setOnClickListener(onClickListener);
        opcion4.setOnClickListener(onClickListener);
        opcion5.setOnClickListener(onClickListener);

        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ocultar el PopupWindow
                popupWindow.dismiss();

                // Ocultar el fondo oscurecido
                dimView.setVisibility(View.GONE);
            }
        });

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                dimView.setVisibility(View.GONE);
            }
        });

    }

    private void popupGenero_2()  {
        View popupView = getLayoutInflater().inflate(R.layout.n15_2_popup_cambiogenero_2, null);

        // Crear la instancia de PopupWindow
        PopupWindow popupWindow = new PopupWindow(popupView, 1000, ViewGroup.LayoutParams.WRAP_CONTENT);

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

        TextView opcion1 = popupView.findViewById(R.id.text_agenero);
        TextView opcion2 = popupView.findViewById(R.id.text_bigenero);
        TextView opcion3 = popupView.findViewById(R.id.text_hombretrans);
        TextView opcion4 = popupView.findViewById(R.id.text_mujertrans);
        TextView opcion5 = popupView.findViewById(R.id.text_queer);
        TextView regresar = popupView.findViewById(R.id.regresar);


        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView textView = popupView.findViewById(view.getId());
                String genero = textView.getText().toString();
                textEditGenero.setText(genero);
                popupWindow.dismiss();
                dimView.setVisibility(View.GONE);

            }
        };

        opcion1.setOnClickListener(onClickListener);
        opcion2.setOnClickListener(onClickListener);
        opcion3.setOnClickListener(onClickListener);
        opcion4.setOnClickListener(onClickListener);
        opcion5.setOnClickListener(onClickListener);

        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ocultar el PopupWindow
                popupWindow.dismiss();

            }
        });

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                dimView.setVisibility(View.GONE);
            }
        });

    }

    private void popupFechaNacimiento() {
        View popupView = getLayoutInflater().inflate(R.layout.n15_3_popup_fecha_nacimiento, null);

        // Crear la instancia de PopupWindow
        PopupWindow popupWindow = new PopupWindow(popupView, 1000, ViewGroup.LayoutParams.WRAP_CONTENT );

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

                LocalDate currentDate = LocalDate.now();
                Period period = Period.between(fechaNacimiento, currentDate);
                int age = period.getYears();

                if (age >= 18) {
                    textEditFechaNac.setText(fechaNacimiento.toString());
                } else {
                    Toast.makeText(EditarPerfilUsuarioActivity.this, "Debe tener al menos 18 años para crear una cuenta", Toast.LENGTH_SHORT).show();
                    // Asignar una fecha por defecto o manejar el caso de menor de edad según tus requerimientos.
                    // Aquí asigno una fecha nula, pero puedes elegir una fecha por defecto si es necesario.
                    // Puedes regresar temprano para evitar el resto del código en este bloque.
                    return;
                }

                // Ocultar el PopupWindow
                popupWindow.dismiss();

                // Ocultar el fondo oscurecido
                dimView.setVisibility(View.GONE);
            }
        });
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
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
                        fechaNacimiento = usuario.getFechaNacimientoUsuario();
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
        errorNombre = findViewById(R.id.error_nombre);
        errorNombre2 = findViewById(R.id.error_nombre2);
        errorApellido = findViewById(R.id.error_apellido);
        errorApellido2 = findViewById(R.id.error_apellido2);
        error_Telefono = findViewById(R.id.error_telefono);
        error_Correo= findViewById(R.id.error_correo);

        textEditGenero = findViewById(R.id.textEdit_Mujer);
        buttonGuardar = findViewById(R.id.button_guardar);
        botonCerrar = findViewById(R.id.boton_cerrar);

        retrofitService = new RetrofitService();
        usuarioApi = retrofitService.getRetrofit().create(UsuarioApi.class);
    }

    private void guardarCambios() {
        String nuevoNombre = textEditNombreUsuario.getText().toString();
        String nuevoApellido = textEditApellidoUsuario.getText().toString();
        String nuevoEmail = textEditEmail.getText().toString();
        String nuevoTelefono = textEditTelefono.getText().toString();
        String nuevoGenero = textEditGenero.getText().toString();

        boolean nombreValido = validarCampoNombre(nuevoNombre);
        boolean apellidoValido = validarCampoApellido(nuevoApellido);
        boolean emailValido = isValidEmail(nuevoEmail);
        boolean telefonoValido = isValidPhone(nuevoTelefono);

        boolean hayErrores = false;

        if (!nombreValido) {
            hayErrores = true;
        } else {

        }

        if (!apellidoValido) {
            hayErrores = true;
        } else {

        }

        if (!emailValido) {
            error_Correo.setVisibility(View.VISIBLE);
            hayErrores = true;
        } else {
            error_Correo.setVisibility(View.GONE);
        }

        if (!telefonoValido) {
            error_Telefono.setVisibility(View.VISIBLE);
            hayErrores = true;
        } else {
            error_Telefono.setVisibility(View.GONE);
        }

        if (hayErrores) {
            return;
        }

        usuario.setNombreUsuario(nuevoNombre);
        usuario.setApellidoUsuario(nuevoApellido);
        usuario.setFechaNacimientoUsuario(fechaNacimiento);
        usuario.setMailUsuario(nuevoEmail);
        usuario.setTelefonoUsuario(nuevoTelefono);
        usuario.setGeneroUsuario(nuevoGenero);

        Call<Usuario> call = usuarioApi.saveSinHash(usuario);
        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful()) {
                    // La modificación se realizó con éxito, puedes mostrar un mensaje de éxito y cerrar la actividad.
                    Toast.makeText(getApplicationContext(), "Datos actualizados correctamente", Toast.LENGTH_SHORT).show();
                    finish(); // Cierra la actividad actual y regresa a la pantalla anterior.
                } else {
                    int statusCode = response.code();
                    Toast.makeText(getApplicationContext(), "Error al guardar los cambios. Código de estado: " + statusCode, Toast.LENGTH_SHORT).show();
                }
            }



            public void onFailure(Call<Usuario> call, Throwable t) {
                // Error en la conexión, muestra un mensaje de error.
                Toast.makeText(getApplicationContext(), "Error en la conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validarCampoNombre(String nombre) {
        if (nombre.isEmpty()) {
            errorNombre2.setVisibility(View.VISIBLE);
            errorNombre.setVisibility(View.GONE);
            return false;
        } else if (nombre.length() > 30) {
            errorNombre.setVisibility(View.VISIBLE);
            errorNombre2.setVisibility(View.GONE);
            return false;
        } else {
            errorNombre.setVisibility(View.GONE);
            errorNombre2.setVisibility(View.GONE);
            return true;
        }
    }

    private boolean validarCampoApellido(String apellido) {
        if (apellido.isEmpty()) {
            errorApellido2.setVisibility(View.VISIBLE);
            errorApellido.setVisibility(View.GONE);
            return false;
        } else if (apellido.length() > 30) {
            errorApellido2.setVisibility(View.GONE);
            errorApellido.setVisibility(View.VISIBLE);
            return false;
        } else {
            errorApellido2.setVisibility(View.GONE);
            errorApellido.setVisibility(View.GONE);
            return true;
        }
    }



    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }
    private boolean isValidPhone(String phoneNumber) {
        String patronTelefono = "^[+]?[0-9]{1,3}[-\\s.]?[(]?[0-9]{1,6}[)]?[-\\s.]?[0-9]{1,12}$";

        if (phoneNumber.matches(patronTelefono)) {
            // El teléfono tiene el formato correcto
            error_Telefono.setVisibility(View.GONE);
            return true;
        } else {
            // Mostrar alerta para teléfono con formato incorrecto (error_Telefono)
            error_Telefono.setVisibility(View.VISIBLE);
            return false;
        }
    }



}
