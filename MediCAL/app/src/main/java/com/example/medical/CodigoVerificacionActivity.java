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

import androidx.appcompat.app.AppCompatActivity;

import com.example.medical.model.CodigoVerificacion;
import com.example.medical.model.Usuario;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.n11_verificacion_password);
        inicializarVariables();
        // Asigna los demás EditText según sea necesario

        // Configura el TextWatcher para el primer EditText
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

        botonValidar.setOnClickListener(view -> {


        });
    }

    private void inicializarVariables() {
        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);
        editText3 = findViewById(R.id.editText3);
        editText4 = findViewById(R.id.editText4);
        botonValidar = findViewById(R.id.button_ingresar);
        intent1 = getIntent();
        codUsuario= parseInt(intent1.getStringExtra("codusuario"));
    }


    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        View view = getCurrentFocus();
        if (view != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
