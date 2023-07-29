package com.example.medical;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.medical.FiltrosDeEditText.NoSpaceInputFilter;
import com.example.medical.FiltrosDeEditText.TextOnlyInputFilter;
import com.example.medical.javamail.SendEmailTask;
import com.example.medical.model.CodigoVerificacion;
import com.example.medical.model.Usuario;
import com.example.medical.retrofit.CodigoVerificacionApi;
import com.example.medical.retrofit.RetrofitService;
import com.example.medical.retrofit.UsuarioApi;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CrearCuenta1Activity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private boolean mostrarContrasenia = false;
    private List<String> usuariosUnicos;
    private List<String> mailsUnicos;
    private RetrofitService retrofitService = new RetrofitService();
    private UsuarioApi usuarioApi = retrofitService.getRetrofit().create(UsuarioApi.class);
    private Button buttonIngresar;
    private ImageView buttonVolver;
    private ImageView ojoContrasenia;
    private EditText usuario;
    private EditText contrasenia;
    private EditText mail;
    private TextView errorUsuario;
    private TextView errorLongitudUsuario;
    private TextView errorLongitudContrasenia;
    private TextView errorFormatoMail;
    private TextView errorMailExistente;
    private View lineaInferiorContrasenia;
    private View lineaInferiorMail;
    private View lineaInferiorUsuario;
    private Usuario usuarioExistenteMail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.n04_0_crear_cuenta_paso1);
        inicializarVariables();
        fetchUsuariosUnicos();
        usuario.setFilters(new InputFilter[] { new NoSpaceInputFilter() });
        mail.setFilters(new InputFilter[] { new NoSpaceInputFilter() });
        buttonIngresar.setOnClickListener(view -> {



            String textoUsuario = usuario.getText().toString();
            String textoContrasenia = contrasenia.getText().toString();
            String textoMail = mail.getText().toString();

            if (mailsUnicos.contains(textoMail)) {
                // If the email exists, fetch the existing user details and pass them to the next activity
                usuarioApi.getByMailUsuario(textoMail).enqueue(new Callback<Usuario>() {
                    @Override
                    public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                        if (response.isSuccessful()) {
                            Usuario existingUser = response.body();
                            if(existingUser.getFechaAltaUsuario()==null) {
                                int idUsuarioExistente = existingUser.getCodUsuario();
                                String usuarioExistente = existingUser.getUsuarioUnico();
                                String contraseniaUsuarioExistente = existingUser.getContraseniaUsuario();

                                // Pass the details of the existing user to the next activity
                                Intent intent = new Intent(CrearCuenta1Activity.this, CodigoVerificacionActivity.class);
                                intent.putExtra("usuario", usuarioExistente);
                                intent.putExtra("contrasenia", contraseniaUsuarioExistente);
                                intent.putExtra("mail", textoMail);
                                intent.putExtra("codusuario", idUsuarioExistente);
                                startActivity(intent);
                                Toast.makeText(getApplicationContext(), "Ya se ha enviado un código de verificación al mail "+textoMail+", por favor revise su bandeja de entrada", Toast.LENGTH_LONG).show();
                                return;
                            }
                            else{
                                lineaInferiorMail.setBackgroundColor(ContextCompat.getColor(CrearCuenta1Activity.this,R.color.rojoError));
                                errorMailExistente.setVisibility(View.VISIBLE);
                                return;
                            }
                        } else {
                            // Show an error message if the response is not successful
                            Toast.makeText(getApplicationContext(), "Error retrieving existing user details", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Usuario> call, Throwable t) {
                        // Show an error message if the API call fails
                        Toast.makeText(getApplicationContext(), "API CALL ERROR Error retrieving existing user details", Toast.LENGTH_SHORT).show();
                    }
                });

            } else {


                if (!camposLlenos(textoUsuario, textoContrasenia, textoMail)) {
                    Toast.makeText(getApplicationContext(), "Debe rellenar todos los campos antes de continuar", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (textoUsuario.length() > 30) {
                    ocultarErrores();
                    errorLongitudUsuario.setVisibility(View.VISIBLE);
                    lineaInferiorUsuario.setBackgroundColor(ContextCompat.getColor(CrearCuenta1Activity.this, R.color.rojoError));
                    return;
                }

                if (usuariosUnicos.contains(textoUsuario)) {
                    ocultarErrores();
                    errorUsuario.setVisibility(View.VISIBLE);
                    lineaInferiorUsuario.setBackgroundColor(ContextCompat.getColor(CrearCuenta1Activity.this, R.color.rojoError));
                    popupInvalido(R.layout.n04_1_popup_usuarioinvalido);
                    return;
                }

                if (textoContrasenia.length() < 6 || textoContrasenia.length() > 15) {
                    ocultarErrores();
                    errorLongitudContrasenia.setVisibility(View.VISIBLE);
                    lineaInferiorContrasenia.setBackgroundColor(ContextCompat.getColor(CrearCuenta1Activity.this, R.color.rojoError));
                    popupInvalido(R.layout.n04_2_popup_contrasenainvalida);
                    return;
                }

                if (!isValidEmail(textoMail)) {
                    ocultarErrores();
                    errorFormatoMail.setVisibility(View.VISIBLE);
                    lineaInferiorMail.setBackgroundColor(ContextCompat.getColor(CrearCuenta1Activity.this, R.color.rojoError));
                    return;
                }

                ocultarErrores();
                String emailAddress = textoMail;
                CodigoVerificacion codigoVerificacion = new CodigoVerificacion();
                Usuario usuario1 = new Usuario();
                usuario1.setUsuarioUnico(textoUsuario);
                usuario1.setContraseniaUsuario(textoContrasenia);
                usuario1.setMailUsuario(textoMail);
                usuario1.setCodigoVerificacion(codigoVerificacion);

                usuarioApi.save(usuario1)
                        .enqueue(new Callback<Usuario>() {
                            @Override
                            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                                Intent intent = new Intent(CrearCuenta1Activity.this, CodigoVerificacionActivity.class);
                                Usuario usuarioInsertado = response.body();
                                int idUsuarioGenerado = usuarioInsertado.getCodUsuario();
                                SendEmailTask sendEmailTask = new SendEmailTask(emailAddress, codigoVerificacion.getCodVerificacion());
                                sendEmailTask.execute();
                                intent.putExtra("usuario", textoUsuario);
                                intent.putExtra("contrasenia", textoContrasenia);
                                intent.putExtra("mail", textoMail);
                                intent.putExtra("codusuario", idUsuarioGenerado);
                                registrarUsuario(textoMail, textoContrasenia);
                                startActivity(intent);
                            }

                            @Override
                            public void onFailure(Call<Usuario> call, Throwable t) {
                                Toast.makeText(CrearCuenta1Activity.this, "Error al crear el usuario", Toast.LENGTH_SHORT).show();
                                Logger.getLogger(CrearCuenta4Activity.class.getName()).log(Level.SEVERE, "Error ocurred");
                            }
                        });
            }
        });

        buttonVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ojoContrasenia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarContrasenia = !mostrarContrasenia;
                if (mostrarContrasenia) {
                    contrasenia.setTransformationMethod(null);
                    ojoContrasenia.setImageResource(R.drawable.ojocontraseniavisible);
                } else {
                    contrasenia.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    ojoContrasenia.setImageResource(R.drawable.ojocontrasenia);
                }
                contrasenia.setSelection(contrasenia.getText().length());
            }
        });


    }

    private void inicializarVariables() {
        buttonIngresar = findViewById(R.id.button_ingresar);
        buttonVolver = findViewById(R.id.boton_volver);
        ojoContrasenia = findViewById(R.id.ojoContrasenia);
        usuario = findViewById(R.id.textEdit_usuario);
        contrasenia = findViewById(R.id.textEdit_contrasenia);
        mail = findViewById(R.id.textEdit_email);
        errorUsuario = findViewById(R.id.error_usuario);
        errorLongitudUsuario = findViewById(R.id.error_longitud_usuario);
        errorLongitudContrasenia = findViewById(R.id.error_longitud_contrasenia);
        errorFormatoMail = findViewById(R.id.error_formato_mail);
        errorMailExistente = findViewById(R.id.error_mail_existente);
        lineaInferiorMail = findViewById(R.id.linea_inferior_mail);
        lineaInferiorContrasenia = findViewById(R.id.linea_inferior_contrasenia);
        lineaInferiorUsuario = findViewById(R.id.linea_inferior_usuario);


    }

    private void ocultarErrores() {
        errorLongitudContrasenia.setVisibility(View.GONE);
        lineaInferiorContrasenia.setBackgroundColor(ContextCompat.getColor(CrearCuenta1Activity.this,R.color.gris));
        errorLongitudUsuario.setVisibility(View.GONE);
        errorUsuario.setVisibility(View.GONE);
        lineaInferiorUsuario.setBackgroundColor(ContextCompat.getColor(CrearCuenta1Activity.this,R.color.gris));
        errorFormatoMail.setVisibility(View.GONE);
        errorMailExistente.setVisibility(View.GONE);
        lineaInferiorMail.setBackgroundColor(ContextCompat.getColor(CrearCuenta1Activity.this,R.color.gris));
        mAuth = FirebaseAuth.getInstance();

    }


    private boolean camposLlenos(String usuario, String contraseña, String mail){
        return !(TextUtils.isEmpty(usuario) || TextUtils.isEmpty(contraseña) || TextUtils.isEmpty(mail));
    }

    private boolean isValidEmail(CharSequence target) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
    private void fetchUsuariosUnicos() {//Busca usuarios y mails unicos
        Call<List<String>> call = usuarioApi.obtenerUsuariosUnicos();
        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.isSuccessful()) {
                    usuariosUnicos = response.body();
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
            }
        });

        Call<List<String>> call2 = usuarioApi.obtenerMailsUnicos();
        call2.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.isSuccessful()) {
                    mailsUnicos = response.body();
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh the usuariosUnicos list when the activity resumes (e.g., when coming back from CodigoVerificacionActivity)
        fetchUsuariosUnicos();
    }

    private void popupInvalido(int layoutResId) {
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

        TextView textViewAceptar = popupView.findViewById(R.id.aceptar);

        textViewAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ocultar el PopupWindow
                popupWindow.dismiss();

                // Ocultar el fondo oscurecido
                dimView.setVisibility(View.GONE);
            }
        });
    }
    public void registrarUsuario(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(CrearCuenta1Activity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
