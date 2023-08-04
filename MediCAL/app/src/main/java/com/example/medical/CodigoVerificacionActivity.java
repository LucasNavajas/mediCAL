package com.example.medical;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.medical.model.CodigoVerificacion;
import com.example.medical.model.Usuario;
import com.example.medical.retrofit.CodigoVerificacionApi;
import com.example.medical.retrofit.RetrofitService;
import com.example.medical.retrofit.UsuarioApi;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CodigoVerificacionActivity extends AppCompatActivity {

    private ImageView buttonVolver;
    private EditText editText1;
    private EditText editText2;
    private EditText editText3;
    private EditText editText4;
    private Button botonValidar;
    private Intent intent1;
    private int codUsuario;
    private PopupWindow popupWindow2;
    private PopupWindow popupWindow;
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

        buttonVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void inicializarVariables() {
        buttonVolver = findViewById(R.id.boton_volver);
        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);
        editText3 = findViewById(R.id.editText3);
        editText4 = findViewById(R.id.editText4);
        botonValidar = findViewById(R.id.button_ingresar);
        intent1 = getIntent();
        codUsuario = intent1.getIntExtra("codusuario", 0);
        // Llamada al método getByCodUsuario de la API en onResponse
        Call<Usuario> call = usuarioApi.getByCodUsuario(codUsuario);

        // Realizar la llamada asíncrona
        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful()) {
                    usuario = response.body();
                    codverificacion = usuario.getCodigoVerificacion();
                    botonValidar.setOnClickListener(view -> {
                        String codigoVerificacionIngresado = editText1.getText().toString() + editText2.getText().toString() + editText3.getText().toString() + editText4.getText().toString();

                        if (codverificacion.getCodVerificacion().equals(codigoVerificacionIngresado)) {
                            if(intent1.getStringExtra("contrasenia")!=null) {//Si contrasenia se pasa en el intent significa que estamos registrando, si solo se pasa el usuario, cod y el mail es reset de contraseña
                                Intent intent = new Intent(CodigoVerificacionActivity.this, CrearCuenta2Activity.class);
                                intent.putExtra("usuario", intent1.getStringExtra("usuario"));
                                intent.putExtra("contrasenia", intent1.getStringExtra("contrasenia"));
                                intent.putExtra("mail", intent1.getStringExtra("mail"));
                                intent.putExtra("codusuario", intent1.getIntExtra("codusuario", 0));
                                startActivity(intent);
                            }
                            else{
                                popupCambiarContrasenia();
                            }

                        } else {
                           popupInvalido(R.layout.n12_popup_codigoinvalido);
                        }
                    });
                } else {
                    // La consulta no fue exitosa, muestra el código de error y mensaje de la respuesta en el Logcat
                    String errorMessage = "Error: " + response.code() + ", Message: " + response.message();
                    Log.e("API Call", errorMessage);
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                // Ocurrió un error en la llamada a la API, muestra el mensaje de error en el Logcat
                Log.e("API Call", "Error en la llamada a la API: " + t.getMessage(), t);
            }
        });
    }

    private void popupCambiarContrasenia() {
        View popupView = getLayoutInflater().inflate(R.layout.n25_2_contrasenia_nueva, null);


        // Crear la instancia de PopupWindow
        popupWindow2 = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        // Hacer que el popup sea enfocable (opcional)
        popupWindow2.setFocusable(true);

        // Configurar animación para oscurecer el fondo
        View rootView = findViewById(android.R.id.content);

        View dimView = findViewById(R.id.dim_view);
        dimView.setVisibility(View.VISIBLE);

        Animation scaleAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.popup);
        popupView.startAnimation(scaleAnimation);

        // Mostrar el popup en la ubicación deseada
        popupWindow2.showAtLocation(rootView, Gravity.CENTER, 0, 0);

        TextView textViewAceptar = popupView.findViewById(R.id.aceptar);
        EditText editContrasenia = popupView.findViewById(R.id.textEdit_nueva_contrasenia);
        TextView errorLongitudContrasenia = popupView.findViewById(R.id.error_longitud_contrasenia);
        TextView errorMismaContrasenia = popupView.findViewById(R.id.error_misma_contrasenia);

        textViewAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String contraseniaNueva = editContrasenia.getText().toString().trim().replace("\"", "");

                if (contraseniaNueva.length() < 6 || contraseniaNueva.length() > 15) {
                    errorMismaContrasenia.setVisibility(View.GONE);
                    errorLongitudContrasenia.setVisibility(View.VISIBLE);
                    return;
                }
               usuarioApi.getByCodUsuario(intent1.getIntExtra("codusuario", 0)).enqueue(new Callback<Usuario>() {
                   @Override
                   public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                       Usuario usuarioresponse = response.body();
                       if(usuarioresponse.getContraseniaUsuario().equals(encode(contraseniaNueva))){
                           modificarContrasenia(contraseniaNueva);
                       }
                       else{
                           errorMismaContrasenia.setVisibility(View.VISIBLE);
                           return;
                       }
                   }

                   @Override
                   public void onFailure(Call<Usuario> call, Throwable t) {
                       errorMismaContrasenia.setVisibility(View.VISIBLE);
                       return;
                   }
               });


            }
        });
    }

    private void modificarContrasenia(String contraseniaNueva){
        View dimView = findViewById(R.id.dim_view);
        dimView.setVisibility(View.VISIBLE);
        usuarioApi.modificarContrasenia(intent1.getIntExtra("codusuario", 0),contraseniaNueva.trim()).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                popupWindow2.dismiss();
                dimView.setVisibility(View.GONE);
                popupRestablecido();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(CodigoVerificacionActivity.this, "Error con el servidor al modificar la contraseña", Toast.LENGTH_SHORT).show();
                popupWindow2.dismiss();
                dimView.setVisibility(View.GONE);
            }
        });
    }
    private void popupRestablecido() {
        View popupView = getLayoutInflater().inflate(R.layout.n25_1_popup_contrasenia_restablecida, null);


        // Crear la instancia de PopupWindow
        popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        // Hacer que el popup sea enfocable (opcional)
        popupWindow.setFocusable(true);

        // Configurar animación para oscurecer el fondo
        View rootView = findViewById(android.R.id.content);

        View dimView = findViewById(R.id.dim_view);
        dimView.setVisibility(View.VISIBLE);

        Animation scaleAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.popup);
        popupView.startAnimation(scaleAnimation);

        // Mostrar el popup en la ubicación deseada
        popupWindow.showAtLocation(rootView, Gravity.CENTER, 0, 0);

        TextView textViewAceptar = popupView.findViewById(R.id.aceptar);

        textViewAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ocultar el PopupWindow
                popupWindow.dismiss();

                // Ocultar el fondo oscurecido
                dimView.setVisibility(View.GONE);
                Intent intent2 = new Intent(CodigoVerificacionActivity.this, BienvenidoActivity.class);
                intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent2);
            }
        });
    }

    private void popupInvalido(int layoutResId) {
        View popupView = getLayoutInflater().inflate(layoutResId, null);


        // Crear la instancia de PopupWindow
        popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        // Hacer que el popup sea enfocable (opcional)
        popupWindow.setFocusable(true);

        // Configurar animación para oscurecer el fondo
        View rootView = findViewById(android.R.id.content);

        View dimView = findViewById(R.id.dim_view);
        dimView.setVisibility(View.VISIBLE);

        Animation scaleAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.popup);
        popupView.startAnimation(scaleAnimation);

        // Mostrar el popup en la ubicación deseada
        popupWindow.showAtLocation(rootView, Gravity.CENTER, 0, 0);

        TextView textViewAceptar = popupView.findViewById(R.id.aceptar);

        textViewAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ocultar el PopupWindow
                popupWindow.dismiss();

                // Ocultar el fondo oscurecido
                dimView.setVisibility(View.GONE);
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
    @Override
    public void onBackPressed() {
        // Realiza la acción adicional que desees antes de volver hacia atrás
        if(intent1.getStringExtra("contrasenia")==null){
            CodigoVerificacionActivity.super.onBackPressed();
        }
        else {
            int codUsuarioAEliminar = intent1.getIntExtra("codusuario", 0);

            Call<Void> call = usuarioApi.deleteUsuario(codUsuarioAEliminar);

            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        // La eliminación fue exitosa, realizar las acciones necesarias
                        Log.d("DeleteUsuario", "Usuario eliminado correctamente.");
                    } else {
                        // Hubo un error en la solicitud o el usuario no existe
                        Log.d("DeleteUsuario", "Error al eliminar el usuario.");
                    }

                    // Después de realizar la eliminación, puedes llamar a super.onBackPressed()
                    // para cerrar la actividad actual y volver hacia atrás en la pila de actividades.
                    CodigoVerificacionActivity.super.onBackPressed();
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    // Error en la comunicación o en la respuesta del servidor
                    Log.e("DeleteUsuario", "Error en la solicitud: " + t.getMessage());

                    // Si ocurre un error al eliminar el usuario, igualmente puedes llamar a
                    // super.onBackPressed() para volver hacia atrás sin eliminar el usuario.
                    CodigoVerificacionActivity.super.onBackPressed();
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
        if (popupWindow2 != null && popupWindow2.isShowing()) {
            popupWindow2.dismiss();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
        if (popupWindow2 != null && popupWindow2.isShowing()) {
            popupWindow2.dismiss();
        }
    }

    public static String encode(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(hash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
        }
        return result.toString();
    }
}
