package com.example.medical;

import static java.lang.Integer.parseInt;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.medical.javamail.SendEmailTask;
import com.example.medical.model.CodigoVerificacion;
import com.example.medical.model.Usuario;
import com.example.medical.retrofit.CodigoVerificacionApi;
import com.example.medical.retrofit.RetrofitService;
import com.example.medical.retrofit.UsuarioApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CodigoVerificacionActivity extends AppCompatActivity {

    private EditText editText1;
    private EditText editText2;
    private EditText editText3;
    private EditText editText4;
    private Button botonValidar;
    private Intent intent1;
    private int codUsuario;
    private Usuario usuario;
    private CodigoVerificacion codverificacion;
    private RetrofitService retrofitService = new RetrofitService();
    private CodigoVerificacionApi codigoVerificacionApi = retrofitService.getRetrofit().create(CodigoVerificacionApi.class);
    private UsuarioApi usuarioApi = retrofitService.getRetrofit().create(UsuarioApi.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.n11_verificacion_password);
        inicializarVariables();
        editText1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // No se utiliza en este ejemplo
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Si se ha ingresado un carácter en el primer EditText, mueve el foco al siguiente EditText
                if (s.length() == 1) {
                    editText2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // No se utiliza en este ejemplo
            }
        });

        // Configura el TextWatcher para el segundo EditText
        editText2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // No se utiliza en este ejemplo
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Si se ha ingresado un carácter en el segundo EditText, mueve el foco al siguiente EditText
                if (s.length() == 1) {
                    editText3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        editText3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // No se utiliza en este ejemplo
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Si se ha ingresado un carácter en el segundo EditText, mueve el foco al siguiente EditText
                if (s.length() == 1) {
                    editText4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        editText4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // No se utiliza en este ejemplo
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Si se ha ingresado un carácter en el segundo EditText, mueve el foco al siguiente EditText
                if (s.length() == 1) {
                    hideKeyboard();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });



    }

    private void inicializarVariables() {
        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);
        editText3 = findViewById(R.id.editText3);
        editText4 = findViewById(R.id.editText4);
        botonValidar = findViewById(R.id.button_ingresar);
        intent1 = getIntent();
        codUsuario = parseInt(intent1.getStringExtra("codusuario"));

        Call<Usuario> call = usuarioApi.getByCodUsuario(codUsuario);
        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful()) {
                    usuario = response.body();
                    codverificacion = usuario.getCodigoVerificacion();
                    Toast.makeText(getApplicationContext(), "Usuario"+usuario.getUsuarioUnico(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(), "Cod"+usuario.getCodigoVerificacion().getCodVerificacion(), Toast.LENGTH_SHORT).show();
                    } else {
                        // Manejar el caso cuando el objeto Usuario o CodigoVerificacion es null
                        Toast.makeText(getApplicationContext(), "Error al obtener el código de verificación, intente nuevamente", Toast.LENGTH_SHORT).show();
                    }
                }


            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                // Manejar el caso de fallo en la llamada a la API
                Toast.makeText(getApplicationContext(), "Error en la llamada a la API, intente nuevamente", Toast.LENGTH_SHORT).show();
            }
        });

        botonValidar.setOnClickListener(view -> {
            String codigoVerificacionIngresado = editText1.getText().toString() + editText2.getText().toString() + editText3.getText().toString() + editText4.getText().toString();

            if (codverificacion.getCodVerificacion().equals(codigoVerificacionIngresado)) {
                Intent intent = new Intent(CodigoVerificacionActivity.this, CrearCuenta2Activity.class);
                intent.putExtra("usuario", intent1.getStringExtra("usuario"));
                intent.putExtra("contrasenia", intent1.getStringExtra("contrasenia"));
                intent.putExtra("mail", intent1.getStringExtra("mail"));
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(), "El código ingresado es incorrecto, intente nuevamente", Toast.LENGTH_SHORT).show();
            }
        });

    }



    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        View view = getCurrentFocus();
        if (view != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
