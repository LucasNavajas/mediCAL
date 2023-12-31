package com.example.medical;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.medical.javamail.SendEmailTask;
import com.example.medical.model.Calendario;
import com.example.medical.model.Usuario;
import com.example.medical.retrofit.CalendarioApi;
import com.example.medical.retrofit.RetrofitService;
import com.example.medical.retrofit.UsuarioApi;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IniciarSesionActivity extends AppCompatActivity {
    private EditText editTextMail;
    private EditText editTextContrasenia;
    private Button ingresar;
    private ImageView ojoContrasenia;
    private ImageView botonCerrar;
    private TextView olvidoContrasenia;
    private boolean mostrarContrasenia = false;
    private LinearLayout progressBar;
    public List<String> mailsUnicos;
    private FirebaseAuth mAuth;
    private RetrofitService retrofitService = new RetrofitService();
    private UsuarioApi usuarioApi = retrofitService.getRetrofit().create(UsuarioApi.class);
    private CalendarioApi calendarioApi = retrofitService.getRetrofit().create(CalendarioApi.class);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.n02_0_inicio_sesion);//cambiar esta linea por el nombre del layout a probar
        obtenerMails();
        inicializarVariables();
        ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = editTextMail.getText().toString();
                String contrasenia = editTextContrasenia.getText().toString();
                if(mailsUnicos.contains(mail)){
                    loginUsuario(mail, contrasenia);
                }
                else {
                    popupInvalido(R.layout.n02_1_popup_usuario_inexistente);
                }


            }
        });

        ojoContrasenia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarContrasenia = !mostrarContrasenia;
                if (mostrarContrasenia) {
                    editTextContrasenia.setTransformationMethod(null);
                    ojoContrasenia.setImageResource(R.drawable.ojocontraseniavisible);
                } else {
                    editTextContrasenia.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    ojoContrasenia.setImageResource(R.drawable.ojocontrasenia);
                }
                editTextContrasenia.setSelection(editTextContrasenia.getText().length());
            }
        });

        botonCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        olvidoContrasenia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupResetContrasenia();
            }
        });
    }

    private void obtenerMails() {
        usuarioApi.obtenerMailsUnicosCuentas().enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                mailsUnicos = response.body();
            }
            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Toast.makeText(IniciarSesionActivity.this, "Hubo un error con el servidor", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void inicializarVariables() {
        mAuth = FirebaseAuth.getInstance();
        editTextMail = findViewById(R.id.textEdit_email);
        editTextContrasenia = findViewById(R.id.textEdit_contraseña);
        ingresar = findViewById(R.id.button_ingresar);
        ojoContrasenia = findViewById(R.id.ojoContrasenia);
        botonCerrar = findViewById(R.id.boton_cerrar);
        olvidoContrasenia = findViewById(R.id.text_OlvidoContraseña);
        progressBar = findViewById(R.id.progressBar);

    }

    public void loginUsuario(String mail, String contrasenia){
        progressBar.setVisibility(View.VISIBLE);
        View dimView = findViewById(R.id.dim_view);
        dimView.setVisibility(View.VISIBLE);
        inhabilitarBotones();
        mAuth.signInWithEmailAndPassword(mail, contrasenia)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            redirigirUsuario(mail);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            popupInvalido(R.layout.n02_2_popup_contrasenia_incorrecta);
                        }
                    }
                });
    }

    private void redirigirUsuario(String mail) {
        usuarioApi.getByMailUsuario(mail).enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                Usuario usuarioLogeado = response.body();
                calendarioApi.getByCodUsuario(usuarioLogeado.getCodUsuario()).enqueue(new Callback<List<Calendario>>() {
                    @Override
                    public void onResponse(Call<List<Calendario>> call, Response<List<Calendario>> response) {
                        progressBar.setVisibility(View.GONE);
                        View dimView = findViewById(R.id.dim_view);
                        dimView.setVisibility(View.GONE);
                        habilitarBotones();
                        List<Calendario> calendarios = response.body();
                        if(calendarios.isEmpty()){
                            Intent intent = new Intent(IniciarSesionActivity.this, BienvenidoUsuarioActivity.class);
                            intent.putExtra("usuario", usuarioLogeado.getUsuarioUnico());
                            startActivity(intent);
                        }
                        else{
                            Intent intent2 = new Intent(IniciarSesionActivity.this, InicioCalendarioActivity.class);
                            intent2.putExtra("codCalendario", calendarios.get(0).getCodCalendario());
                            startActivity(intent2);
                        }


                    }

                    @Override
                    public void onFailure(Call<List<Calendario>> call, Throwable t) {
                        Toast.makeText(IniciarSesionActivity.this, "Hubo un error al iniciar sesión, intente nuevamente", Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Toast.makeText(IniciarSesionActivity.this, "Hubo un error al iniciar sesión, intente nuevamente", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void popupInvalido(int layoutResId) {
        progressBar.setVisibility(View.GONE);
        habilitarBotones();
        View popupView = getLayoutInflater().inflate(layoutResId, null);


        // Crear la instancia de PopupWindow
        PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

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

        TextView textViewAceptar = popupView.findViewById(R.id.regresar);

        textViewAceptar.setOnClickListener(new View.OnClickListener() {
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

    private void popupResetContrasenia() {

        View popupView = getLayoutInflater().inflate(R.layout.n10_popup_reiniciarpassword, null);

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

        TextView textViewAceptar = popupView.findViewById(R.id.aceptar);
        TextView textViewCancelar = popupView.findViewById(R.id.cancelar);
        EditText editMailReset = popupView.findViewById(R.id.textEdit_email);
        TextView errorEmail = popupView.findViewById(R.id.errorMail);

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
        });
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                dimView.setVisibility(View.GONE);
            }
        });
    }

    private void enviarCodigoVerificacion(Usuario usuarioReset) {
        Intent intent2 = new Intent(IniciarSesionActivity.this, CodigoVerificacionActivity.class);
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
                Toast.makeText(IniciarSesionActivity.this, "Error al crear un codigo verificación", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public static void setAllViewsEnabled(ViewGroup viewGroup, boolean enabled) {
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = viewGroup.getChildAt(i);

            // Deshabilitar vista si es interactuable (Button, EditText, CheckBox, RadioButton)
            if (view instanceof Button || view instanceof EditText) {
                view.setEnabled(enabled);
            }

            // Si es un ImageView, también puedes deshabilitarlo si es necesario
            if (view instanceof ImageView) {
                view.setEnabled(enabled);
            }

            // Si es un TextView, también puedes deshabilitarlo si es necesario
            if (view instanceof TextView) {
                view.setEnabled(enabled);
            }

            // Si es un ViewGroup (por ejemplo, LinearLayout, RelativeLayout, etc.), llama de forma recursiva
            if (view instanceof ViewGroup) {
                setAllViewsEnabled((ViewGroup) view, enabled);
            }
        }
    }
    public void habilitarBotones(){
        ViewGroup layout = findViewById(R.id.layout_entero);
        setAllViewsEnabled(layout, true); // Deshabilita todas las vistas
    }
    public void inhabilitarBotones(){
        ViewGroup layout = findViewById(R.id.layout_entero);
        setAllViewsEnabled(layout, false); // Deshabilita todas las vistas
    }
}
