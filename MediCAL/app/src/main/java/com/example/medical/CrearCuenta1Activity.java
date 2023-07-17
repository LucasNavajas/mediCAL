package com.example.medical;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.medical.javamail.SendEmailTask;
import com.example.medical.model.CodigoVerificacion;
import com.example.medical.model.Usuario;
import com.example.medical.retrofit.CodigoVerificacionApi;
import com.example.medical.retrofit.RetrofitService;
import com.example.medical.retrofit.UsuarioApi;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CrearCuenta1Activity extends AppCompatActivity {

    private boolean mostrarContrasenia = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.n04_0_crear_cuenta_paso1);

        RetrofitService retrofitService = new RetrofitService();
        CodigoVerificacionApi codigoVerificacionApi = retrofitService.getRetrofit().create(CodigoVerificacionApi.class);

        Button buttonIngresar = findViewById(R.id.button_ingresar);
        ImageView buttonVolver = findViewById(R.id.boton_volver);
        ImageView ojoContrasenia = findViewById(R.id.ojoContrasenia);
        EditText usuario = findViewById(R.id.textEdit_usuario);
        EditText contrasenia = findViewById(R.id.textEdit_contrasenia);
        EditText mail = findViewById(R.id.textEdit_email);


        buttonIngresar.setOnClickListener(view -> {


            String textoUsuario = usuario.getText().toString();
            String textoContrasenia = contrasenia.getText().toString();
            String textoMail = mail.getText().toString();

            if (camposLlenos(textoUsuario, textoContrasenia, textoMail)) {
                String emailAddress = textoMail;
                CodigoVerificacion codigoVerificacion = new CodigoVerificacion();

                codigoVerificacionApi.save(codigoVerificacion).enqueue(new Callback<CodigoVerificacion>() {
                    @Override
                    public void onResponse(Call<CodigoVerificacion> call, Response<CodigoVerificacion> response) {
                        if (response.isSuccessful()) {
                            CodigoVerificacion codigoVerificacionRespuesta = response.body();
                            String verificationCode = codigoVerificacionRespuesta.getCodVerificacion(); // Utiliza el código de verificación recibido del servidor

                            SendEmailTask sendEmailTask = new SendEmailTask(emailAddress, verificationCode);
                            sendEmailTask.execute();
                            Intent intent = new Intent(CrearCuenta1Activity.this, CrearCuenta2Activity.class);
                            intent.putExtra("usuario", textoUsuario);
                            intent.putExtra("contrasenia", textoContrasenia);
                            intent.putExtra("mail", textoMail);
                            startActivity(intent);
                        } else {
                            Toast.makeText(CrearCuenta1Activity.this, "Error al crear el código", Toast.LENGTH_SHORT).show();
                            Logger.getLogger(CrearCuenta1Activity.class.getName()).log(Level.SEVERE, "Error ocurred");
                        }
                    }

                    @Override
                    public void onFailure(Call<CodigoVerificacion> call, Throwable t) {
                        Toast.makeText(CrearCuenta1Activity.this, "Error al crear el codigo", Toast.LENGTH_SHORT).show();
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

}
