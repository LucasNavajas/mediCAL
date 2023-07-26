package com.example.medical;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.medical.retrofit.CodigoVerificacionApi;
import com.example.medical.retrofit.RetrofitService;
import com.example.medical.retrofit.UsuarioApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    private TextView errorNombre;
    private TextView errorApellido;
    private View lineaInferiorNombre;
    private View lineaInferiorApellido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.n05_crear_cuenta_paso2);
        inicializarVariables();
        nombre.setFilters(new InputFilter[] { new TextOnlyInputFilter() });
        apellido.setFilters(new InputFilter[] { new TextOnlyInputFilter() });
        buttonSiguiente.setOnClickListener(view -> {


            String textoNombre = nombre.getText().toString();
            String textoApellido = apellido.getText().toString();

            String textoTelefono = telefono.getText().toString();

            if (!camposLlenos(textoNombre, textoApellido, textoTelefono)) {
                Toast.makeText(getApplicationContext(), "Debe rellenar todos los campos antes de continuar", Toast.LENGTH_SHORT).show();
                return;
            }

            if(textoNombre.length()>30){
                errorNombre.setVisibility(View.VISIBLE);
                lineaInferiorNombre.setBackgroundColor(ContextCompat.getColor(CrearCuenta2Activity.this,R.color.rojoError));
                return;
            }
            if(textoApellido.length()>30){
                errorNombre.setVisibility(View.GONE);
                lineaInferiorNombre.setBackgroundColor(ContextCompat.getColor(CrearCuenta2Activity.this,R.color.black));
                errorApellido.setVisibility(View.VISIBLE);
                lineaInferiorApellido.setBackgroundColor(ContextCompat.getColor(CrearCuenta2Activity.this,R.color.rojoError));
                return;
            }
            errorNombre.setVisibility(View.GONE);
            lineaInferiorNombre.setBackgroundColor(ContextCompat.getColor(CrearCuenta2Activity.this,R.color.black));
            errorApellido.setVisibility(View.GONE);
            lineaInferiorApellido.setBackgroundColor(ContextCompat.getColor(CrearCuenta2Activity.this,R.color.black));
                Intent intent = new Intent(CrearCuenta2Activity.this, CrearCuenta3Activity.class);
                intent.putExtra("usuario", intent1.getStringExtra("usuario"));
                intent.putExtra("contrasenia", intent1.getStringExtra("contrasenia"));
                intent.putExtra("mail", intent1.getStringExtra("mail"));
                intent.putExtra("codusuario", intent1.getIntExtra("codusuario",0));
                intent.putExtra("nombre", textoNombre);
                intent.putExtra("apellido", textoApellido);
                intent.putExtra("telefono", textoTelefono);
                startActivity(intent);

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
        buttonVolver = findViewById(R.id.boton_volver2);
        nombre = findViewById(R.id.textEdit_nombre);
        apellido = findViewById(R.id.textEdit_apellido);
        telefono = findViewById(R.id.textEdit_telefono);
        errorNombre = findViewById(R.id.error_nombre);
        errorApellido = findViewById(R.id.error_apellido);
        lineaInferiorApellido = findViewById(R.id.linea_inferior_apellido);
        lineaInferiorNombre = findViewById(R.id.linea_inferior_nombre);


    }

    private boolean camposLlenos(String nombre, String apellido, String telefono){
        return !(TextUtils.isEmpty(nombre) || TextUtils.isEmpty(apellido) || TextUtils.isEmpty(telefono));
    }

}
