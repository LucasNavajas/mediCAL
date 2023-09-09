package com.example.medical;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class BienvenidoUsuarioActivity extends AppCompatActivity {
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private ImageView botonCerrar;
    private Button crearCalendario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent1 = getIntent();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(intent1.getStringExtra(("mail"))!=null) {
            login(intent1.getStringExtra("mail"), intent1.getStringExtra("contrasenia"));
        }
        setContentView(R.layout.n09_bienvenido_usuario);
        botonCerrar = findViewById(R.id.boton_cerrar);
        crearCalendario = findViewById(R.id.button_agregarCalendario);

        ImageView gifImageView;
        TextView bienvenido = findViewById(R.id.text_bienvenido);

        String contenidoActual = bienvenido.getText().toString();
        String textoAdicional = intent1.getStringExtra("usuario");
        String nuevoContenido;
        if(intent1.getStringExtra("usuario")==null){
            nuevoContenido = contenidoActual + "!";
        }
        else {
            nuevoContenido = contenidoActual+", " + textoAdicional + "!";
        }
        bienvenido.setText(nuevoContenido);
        bienvenido.setGravity(Gravity.CENTER_HORIZONTAL);


        gifImageView = findViewById(R.id.gifImageView);

        // Cargar y mostrar el GIF utilizando Glide
        Glide.with(this)
                .load(R.drawable.medicinegif) // Reemplaza "nombre_del_gif" con el nombre de tu archivo GIF sin la extensión
                .into(gifImageView);

        botonCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        crearCalendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(BienvenidoUsuarioActivity.this, CrearCalendario1Activity.class);
                startActivity(intent2);
            }
        });
    }
    @Override
    public void onBackPressed(){
        mAuth.signOut();
        Intent intent = new Intent(this, BienvenidoActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    public void login(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(BienvenidoUsuarioActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        }
                    }
                });
    }

}

