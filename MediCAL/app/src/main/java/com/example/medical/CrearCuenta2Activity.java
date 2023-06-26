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

public class CrearCuenta2Activity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crear_cuenta_paso2);
        Intent intent1 = getIntent();

        Button buttonSiguiente = findViewById(R.id.button_siguiente);
        ImageView buttonVolver = findViewById(R.id.boton_volver);
        EditText nombre = findViewById(R.id.textEdit_nombre);
        EditText apellido = findViewById(R.id.textEdit_apellido);
        EditText telefono = findViewById(R.id.textEdit_telefono);

        buttonSiguiente.setOnClickListener(view -> {


            String textoNombre = nombre.getText().toString();
            String textoApellido = apellido.getText().toString();
            String textoTelefono = telefono.getText().toString();

            if (camposLlenos(textoNombre, textoApellido, textoTelefono)){
                Intent intent = new Intent(CrearCuenta2Activity.this, CrearCuenta3Activity.class);
                intent.putExtra("usuario", intent1.getStringExtra("usuario"));
                intent.putExtra("contrasenia", intent1.getStringExtra("contrasenia"));
                intent.putExtra("mail", intent1.getStringExtra("mail"));
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

    private boolean camposLlenos(String nombre, String apellido, String telefono){
        return !(TextUtils.isEmpty(nombre) || TextUtils.isEmpty(apellido) || TextUtils.isEmpty(telefono));
    }
}
