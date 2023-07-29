package com.example.medical;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.medical.model.Usuario;
import com.example.medical.retrofit.RetrofitService;
import com.example.medical.retrofit.UsuarioApi;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IniciarSesionActivity extends AppCompatActivity {
    private EditText editTextMail;
    private EditText editTextContrasenia;
    private Button ingresar;
    private FirebaseAuth mAuth;
    private RetrofitService retrofitService = new RetrofitService();
    private UsuarioApi usuarioApi = retrofitService.getRetrofit().create(UsuarioApi.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.n02_0_inicio_sesion);//cambiar esta linea por el nombre del layout a probar
        inicializarVariables();
        ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = editTextMail.getText().toString();
                String contrasenia = editTextContrasenia.getText().toString();
                usuarioApi.getByMailUsuario(mail).enqueue(new Callback<Usuario>() {
                    @Override
                    public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                        Usuario usuario = response.body();
                        if(usuario.getFechaAltaUsuario()==null){
                            //popup no existe usuario
                            Toast.makeText(IniciarSesionActivity.this, "No existe usuario",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else{
                            loginUsuario(mail, contrasenia);
                        }
                    }

                    @Override
                    public void onFailure(Call<Usuario> call, Throwable t) {
                        //popup no existe usuario
                    }
                });
            }
        });
    }

    private void inicializarVariables() {
        mAuth = FirebaseAuth.getInstance();
        editTextMail = findViewById(R.id.textEdit_email);
        editTextContrasenia = findViewById(R.id.textEdit_contraseña);
        ingresar = findViewById(R.id.button_ingresar);

    }

    public void loginUsuario(String mail, String contrasenia){
        mAuth.signInWithEmailAndPassword(mail, contrasenia)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent2 = new Intent(IniciarSesionActivity.this, BienvenidoUsuarioActivity.class);
                            startActivity(intent2);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(IniciarSesionActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

}
