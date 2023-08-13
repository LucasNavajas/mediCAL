package com.example.medical;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.medical.javamail.SendEmailTask;
import com.example.medical.model.Usuario;
import com.example.medical.retrofit.CalendarioApi;
import com.example.medical.retrofit.RetrofitService;
import com.example.medical.retrofit.UsuarioApi;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestablecerContraseniaActivity extends AppCompatActivity {
    private EditText contraseniaActual;
    private EditText contraseniaNueva;
    private boolean mismaContraseniaActual;
    private boolean mostrarContraseniaActual;
    private boolean mostrarContraseniaNueva;
    private Button guardar;
    private ImageView botonCerrar;
    private ImageView contraseniaCorrecta;
    private PopupWindow popupWindow;
    private FirebaseAuth mAuth;
    private ImageView ojoContraseniaActual;
    private ImageView ojoContraseniaNueva;
    private TextView errorLongitud;
    private TextView errorMismaContrasenia;
    private TextView errorMismaContraseniaActual;
    private FirebaseUser user;
    private TextView olvidoContrasenia;
    private RetrofitService retrofitService = new RetrofitService();
    private UsuarioApi usuarioApi = retrofitService.getRetrofit().create(UsuarioApi.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.n25_0_restablecimiento_contrasenia);//cambiar esta linea por el nombre del layout a probar
        inicializarVariables();

        botonCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        contraseniaActual.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                String textoContrasenaActual = editable.toString();
                if (!textoContrasenaActual.isEmpty()) {
                    usuarioApi.verificarMismaContrasenia(getIntent().getIntExtra("codUsuario", 0), textoContrasenaActual).enqueue(new Callback<Boolean>() {
                        @Override
                        public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                            boolean respuesta = response.body();
                            if (response.body() != null) {
                                if (respuesta == true) {
                                    mismaContraseniaActual = true;
                                    contraseniaCorrecta.setImageResource(R.drawable.tick);
                                } else {
                                    mismaContraseniaActual = false;
                                    contraseniaCorrecta.setImageResource(R.drawable.cruz_roja);
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<Boolean> call, Throwable t) {
                            Toast.makeText(RestablecerContraseniaActivity.this, "Error con la base de datos, no se pudo recuperar la contraseña", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        ojoContraseniaActual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarContraseniaActual = !mostrarContraseniaActual;
                if (mostrarContraseniaActual) {
                    contraseniaActual.setTransformationMethod(null);
                    ojoContraseniaActual.setImageResource(R.drawable.ojocontraseniavisible);
                } else {
                    contraseniaActual.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    ojoContraseniaActual.setImageResource(R.drawable.ojocontrasenia);
                }
                contraseniaActual.setSelection(contraseniaActual.getText().length());
            }
        });

        ojoContraseniaNueva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarContraseniaNueva = !mostrarContraseniaNueva;
                if (mostrarContraseniaNueva) {
                    contraseniaNueva.setTransformationMethod(null);
                    ojoContraseniaNueva.setImageResource(R.drawable.ojocontraseniavisible);
                } else {
                    contraseniaNueva.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    ojoContraseniaNueva.setImageResource(R.drawable.ojocontrasenia);
                }
                contraseniaActual.setSelection(contraseniaNueva.getText().length());
            }
        });

        olvidoContrasenia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupResetContrasenia();
            }
        });

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String contraseniaActualText = contraseniaActual.getText().toString();
                String contraseniaNuevaText = contraseniaNueva.getText().toString();
                errorLongitud.setVisibility(GONE);
                errorMismaContrasenia.setVisibility(GONE);
                errorMismaContraseniaActual.setVisibility(GONE);
                 if(contraseniaNuevaText.length()<6 || contraseniaNuevaText.length()>15){
                     errorLongitud.setVisibility(VISIBLE);
                     return;
                 }
                 if(mismaContraseniaActual==false){
                     errorMismaContraseniaActual.setVisibility(VISIBLE);
                     return;
                 }
                 if(contraseniaNuevaText.equals(contraseniaActualText)){
                    errorMismaContrasenia.setVisibility(VISIBLE);
                    return;
                 }

                 usuarioApi.modificarContrasenia(getIntent().getIntExtra("codUsuario", 0), contraseniaNuevaText).enqueue(new Callback<Void>() {
                     @Override
                     public void onResponse(Call<Void> call, Response<Void> response) {
                         popupRestablecido();
                     }

                     @Override
                     public void onFailure(Call<Void> call, Throwable t) {
                         Toast.makeText(RestablecerContraseniaActivity.this, "Error al modificar la contraseña, intente nuevamente", Toast.LENGTH_SHORT).show();
                     }
                 });
            }
        });

    }

    private void inicializarVariables() {
        contraseniaActual = findViewById(R.id.textEdit_contrasenia_actual);
        contraseniaNueva = findViewById(R.id.textEdit_nueva_contrasenia);
        mismaContraseniaActual = false;
        guardar = findViewById(R.id.button_guardar);
        botonCerrar = findViewById(R.id.boton_atras);
        ojoContraseniaActual = findViewById(R.id.ojoContraseniaActual);
        ojoContraseniaNueva = findViewById(R.id.ojoContraseniaNueva);
        contraseniaCorrecta = findViewById(R.id.tick);
        errorLongitud = findViewById(R.id.error_longitud_contrasenia);
        errorMismaContrasenia = findViewById(R.id.error_misma_contrasenia);
        errorMismaContraseniaActual = findViewById(R.id.error_diferente_contrasenia_actual);
        olvidoContrasenia = findViewById(R.id.text_OlvidoContraseña);
        mostrarContraseniaActual = false;
        mostrarContraseniaNueva = false;
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
    }

    private void popupRestablecido() {
        View popupView = getLayoutInflater().inflate(R.layout.n25_1_popup_contrasenia_restablecida, null);


        // Crear la instancia de PopupWindow
        popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

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

        textViewAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ocultar el PopupWindow
                popupWindow.dismiss();

                // Ocultar el fondo oscurecido
                dimView.setVisibility(View.GONE);
                Intent intent2 = new Intent(RestablecerContraseniaActivity.this, InicioCalendarioActivity.class);
                intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent2);
            }
        });
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                dimView.setVisibility(View.GONE);
                Intent intent2 = new Intent(RestablecerContraseniaActivity.this, BienvenidoActivity.class);
                intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent2);
            }
        });
    }
    private void popupResetContrasenia() {
        View popupView2 = getLayoutInflater().inflate(R.layout.n10_popup_reiniciarpassword, null);

        // Crear la instancia de PopupWindow
        PopupWindow popupWindow = new PopupWindow(popupView2, 1000, ViewGroup.LayoutParams.WRAP_CONTENT);

        // Hacer que el popup sea enfocable (opcional)
        popupWindow.setFocusable(true);

        // Configurar animación para oscurecer el fondo
        View rootView = findViewById(android.R.id.content);

        View dimView = findViewById(R.id.dim_view);
        dimView.setVisibility(View.VISIBLE);

        Animation scaleAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.popup);
        popupView2.startAnimation(scaleAnimation);

        // Mostrar el popup en la ubicación deseada
        popupWindow.showAtLocation(rootView, Gravity.CENTER, 0, 0);

        TextView textViewAceptar = popupView2.findViewById(R.id.aceptar);
        TextView textViewCancelar = popupView2.findViewById(R.id.cancelar);
        EditText editMailReset = popupView2.findViewById(R.id.textEdit_email);
        TextView errorEmail = popupView2.findViewById(R.id.errorMail);
        TextView errorMailDiferente = popupView2.findViewById(R.id.errorMailDiferente);

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
                String mailReset = editMailReset.getText().toString();
                errorEmail.setVisibility(View.GONE);
                errorMailDiferente.setVisibility(GONE);
                if (mailReset.equals(user.getEmail())) {
                    usuarioApi.getByMailUsuario(mailReset).enqueue(new Callback<Usuario>() {
                        @Override
                        public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                            popupWindow.dismiss();
                            // Ocultar el fondo oscurecido
                            dimView.setVisibility(View.GONE);
                            Usuario usuarioReset = response.body();
                            enviarCodigoVerificacion(usuarioReset);
                        }

                        @Override
                        public void onFailure(Call<Usuario> call, Throwable t) {
                            errorEmail.setVisibility(View.VISIBLE);
                        }
                    });
                }
                else{
                    errorMailDiferente.setVisibility(VISIBLE);
                    return;
                }
            }
        });
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                dimView.setVisibility(View.GONE);
            }
        });
    }

    private void enviarCodigoVerificacion(Usuario usuarioReset) {
        Intent intent2 = new Intent(RestablecerContraseniaActivity.this, CodigoVerificacionActivity.class);
        intent2.putExtra("codusuario", usuarioReset.getCodUsuario());
        intent2.putExtra("mail", usuarioReset.getMailUsuario());
        intent2.putExtra("contrasenia", usuarioReset.getContraseniaUsuario());
        usuarioApi.setCodigoVerificacion(usuarioReset.getCodUsuario()).enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                Usuario usuarioModificado = response.body();
                String codigoVerificacionNuevo = usuarioModificado.getCodigoVerificacion().getCodVerificacion();
                SendEmailTask sendEmailTask = new SendEmailTask(usuarioModificado.getMailUsuario(), codigoVerificacionNuevo);
                sendEmailTask.execute();
                startActivity(intent2);
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Toast.makeText(RestablecerContraseniaActivity.this, "Error al crear un codigo verificación", Toast.LENGTH_SHORT).show();
            }
        });

    }
}