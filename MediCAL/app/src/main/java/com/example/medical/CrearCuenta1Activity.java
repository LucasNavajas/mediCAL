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

public class CrearCuenta1Activity extends AppCompatActivity {

    private boolean mostrarContrasenia = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.n04_0_crear_cuenta_paso1);



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

            if (camposLlenos(textoUsuario, textoContrasenia, textoMail)){
                String emailAddress = textoMail;
                String verificationCode = "1111"; // Implementa tu generador de código de 4 dígitos

                SendEmailTask sendEmailTask = new SendEmailTask(emailAddress, verificationCode);
                sendEmailTask.execute();
                Intent intent = new Intent(CrearCuenta1Activity.this, CrearCuenta2Activity.class);
                intent.putExtra("usuario", textoUsuario);
                intent.putExtra("contrasenia", textoContrasenia);
                intent.putExtra("mail", textoMail);
                startActivity(intent);
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
