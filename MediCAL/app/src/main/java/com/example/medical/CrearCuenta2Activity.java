package com.example.medical;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.medical.retrofit.CodigoVerificacionApi;
import com.example.medical.retrofit.RetrofitService;
import com.example.medical.retrofit.UsuarioApi;

public class CrearCuenta2Activity extends AppCompatActivity {
    private Intent intent1;
    private RetrofitService retrofitService;
    private CodigoVerificacionApi codigoVerificacionApi;
    private UsuarioApi usuarioApi;
    private Button buttonSiguiente;
    private ImageView buttonVolver;
    private EditText nombre;
    private EditText apellido;
    private EditText telefono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.n05_crear_cuenta_paso2);
        inicializarVariables();

        buttonSiguiente.setOnClickListener(view -> {


            String textoNombre = nombre.getText().toString();
            String textoApellido = apellido.getText().toString();
            String textoTelefono = telefono.getText().toString();

            if (camposLlenos(textoNombre, textoApellido, textoTelefono)){
                Intent intent = new Intent(CrearCuenta2Activity.this, CrearCuenta3Activity.class);
                intent.putExtra("usuario", intent1.getStringExtra("usuario"));
                intent.putExtra("contrasenia", intent1.getStringExtra("contrasenia"));
                intent.putExtra("mail", intent1.getStringExtra("mail"));
                intent.putExtra("codusuario", intent1.getIntExtra("codusuario",0));
                intent.putExtra("nombre", textoNombre);
                intent.putExtra("apellido", textoApellido);
                intent.putExtra("telefono", textoTelefono);
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
    }

    private void inicializarVariables() {
        intent1 = getIntent();
        retrofitService = new RetrofitService();
        codigoVerificacionApi = retrofitService.getRetrofit().create(CodigoVerificacionApi.class);
        usuarioApi = retrofitService.getRetrofit().create(UsuarioApi.class);
        buttonSiguiente = findViewById(R.id.button_siguiente);
        buttonVolver = findViewById(R.id.boton_volver);
        nombre = findViewById(R.id.textEdit_nombre);
        apellido = findViewById(R.id.textEdit_apellido);
        telefono = findViewById(R.id.textEdit_telefono);
    }

    private boolean camposLlenos(String nombre, String apellido, String telefono){
        return !(TextUtils.isEmpty(nombre) || TextUtils.isEmpty(apellido) || TextUtils.isEmpty(telefono));
    }
}
