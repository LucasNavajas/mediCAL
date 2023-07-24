package com.example.medical;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.medical.javamail.SendEmailTask;
import com.example.medical.model.CodigoVerificacion;
import com.example.medical.model.Usuario;
import com.example.medical.retrofit.CodigoVerificacionApi;
import com.example.medical.retrofit.RetrofitService;
import com.example.medical.retrofit.UsuarioApi;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CrearCuenta1Activity extends AppCompatActivity {

    private boolean mostrarContrasenia = false;
    private List<String> usuariosUnicos;
    private RetrofitService retrofitService = new RetrofitService();
    private CodigoVerificacionApi codigoVerificacionApi = retrofitService.getRetrofit().create(CodigoVerificacionApi.class);
    private UsuarioApi usuarioApi = retrofitService.getRetrofit().create(UsuarioApi.class);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.n04_0_crear_cuenta_paso1);
        fetchUsuariosUnicos();



        Button buttonIngresar = findViewById(R.id.button_ingresar);
        ImageView buttonVolver = findViewById(R.id.boton_volver);
        ImageView ojoContrasenia = findViewById(R.id.ojoContrasenia);
        EditText usuario = findViewById(R.id.textEdit_usuario);
        EditText contrasenia = findViewById(R.id.textEdit_contrasenia);
        EditText mail = findViewById(R.id.textEdit_email);
        TextView errorUsuario = findViewById(R.id.error_usuario);
        TextView errorLongitudUsuario = findViewById(R.id.error_longitud_usuario);
        TextView errorLongitudContrasenia = findViewById(R.id.error_longitud_contrasenia);
        TextView errorFormatoMail = findViewById(R.id.error_formato_mail);
        View lineaInferiorMail = findViewById(R.id.linea_inferior_mail);
        View lineaInferiorContrasenia = findViewById(R.id.linea_inferior_contrasenia);
        View lineaInferiorUsuario = findViewById(R.id.linea_inferior_usuario);
        buttonIngresar.setOnClickListener(view -> {



            String textoUsuario = usuario.getText().toString();
            String textoContrasenia = contrasenia.getText().toString();
            String textoMail = mail.getText().toString();

            if (textoUsuario.length() > 30) {
                errorLongitudUsuario.setVisibility(View.VISIBLE);
                lineaInferiorUsuario.setBackgroundColor(ContextCompat.getColor(CrearCuenta1Activity.this,R.color.rojoError));
                return;
            }

            if (usuariosUnicos.contains(textoUsuario)) {
                errorUsuario.setVisibility(View.VISIBLE);
                errorLongitudUsuario.setVisibility(View.GONE);
                lineaInferiorUsuario.setBackgroundColor(ContextCompat.getColor(CrearCuenta1Activity.this,R.color.rojoError));
                return;
            }

            if (textoContrasenia.length() < 6 || textoContrasenia.length() > 15) {
                errorLongitudContrasenia.setVisibility(View.VISIBLE);
                lineaInferiorContrasenia.setBackgroundColor(ContextCompat.getColor(CrearCuenta1Activity.this,R.color.rojoError));
                errorLongitudUsuario.setVisibility(View.GONE);
                errorUsuario.setVisibility(View.GONE);
                lineaInferiorUsuario.setBackgroundColor(ContextCompat.getColor(CrearCuenta1Activity.this,R.color.gris));
                return;
            }

            if (!isValidEmail(textoMail)) {
                errorFormatoMail.setVisibility(View.VISIBLE);
                lineaInferiorMail.setBackgroundColor(ContextCompat.getColor(CrearCuenta1Activity.this,R.color.rojoError));
                errorLongitudContrasenia.setVisibility(View.GONE);
                lineaInferiorContrasenia.setBackgroundColor(ContextCompat.getColor(CrearCuenta1Activity.this,R.color.gris));
                errorLongitudUsuario.setVisibility(View.GONE);
                errorUsuario.setVisibility(View.GONE);
                lineaInferiorUsuario.setBackgroundColor(ContextCompat.getColor(CrearCuenta1Activity.this,R.color.gris));
                return;
            }

            if (camposLlenos(textoUsuario, textoContrasenia, textoMail)) {
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
                                    startActivity(intent);
                                }

                                @Override
                                public void onFailure(Call<Usuario> call, Throwable t) {
                                    Toast.makeText(CrearCuenta1Activity.this, "Error al crear el usuario", Toast.LENGTH_SHORT).show();
                                    Logger.getLogger(CrearCuenta4Activity.class.getName()).log(Level.SEVERE, "Error ocurred");
                                }
                            });
                }

            else{
                Toast.makeText(getApplicationContext(), "Debe rellenar todos los campos antes de continuar", Toast.LENGTH_SHORT).show();
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



    private boolean camposLlenos(String usuario, String contraseña, String mail){
        return !(TextUtils.isEmpty(usuario) || TextUtils.isEmpty(contraseña) || TextUtils.isEmpty(mail));
    }

    private boolean isValidEmail(CharSequence target) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
    private void fetchUsuariosUnicos() {
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
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh the usuariosUnicos list when the activity resumes (e.g., when coming back from CodigoVerificacionActivity)
        fetchUsuariosUnicos();
    }
}
