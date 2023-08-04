package com.example.medical;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.medical.model.Sintoma;
import com.example.medical.retrofit.SintomaApi;
import com.example.medical.retrofit.RetrofitService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnadirSintomaActivity extends AppCompatActivity {

    private AutoCompleteTextView autoCompleteTextView;
    private SintomaApi sintomaApi;
    private List<Sintoma> sintomasList;
    private RelativeLayout checkboxLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.n77_anadir_sintomas);

        autoCompleteTextView = findViewById(R.id.editText_buscar);
        autoCompleteTextView.setSingleLine(); // Evitar saltos de línea

        checkboxLayout = findViewById(R.id.parte_blanca); // RelativeLayout donde se agregarán los CheckBox

        // Inicializar la interfaz SintomaApi con RetrofitService
        RetrofitService retrofitService = new RetrofitService();
        sintomaApi = retrofitService.getRetrofit().create(SintomaApi.class);

        cargarSintomas();
        // Agregar un listener al AutoCompleteTextView para manejar la acción "Search" (Buscar)
        autoCompleteTextView.setOnEditorActionListener((textView, actionId, keyEvent) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH || (keyEvent != null && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN)) {
                // Realizar búsqueda cuando se presiona "Enter" o se ejecuta la acción de búsqueda
                String sintoma = textView.getText().toString();
                buscarSintoma(sintoma);
                return true;
            }
            return false;
        });
    }

    private void cargarSintomas() {
        // Hacer la llamada a la API para obtener la lista de síntomas
        Call<List<Sintoma>> call = sintomaApi.getAllSintomas();
        call.enqueue(new Callback<List<Sintoma>>() {
            @Override
            public void onResponse(Call<List<Sintoma>> call, Response<List<Sintoma>> response) {
                if (response.isSuccessful()) {
                    sintomasList = response.body();

                    // Configurar el adaptador para el AutoCompleteTextView
                    ArrayAdapter<Sintoma> adapter = new ArrayAdapter<>(
                            AnadirSintomaActivity.this,
                            android.R.layout.simple_dropdown_item_1line,
                            sintomasList
                    );
                    autoCompleteTextView.setAdapter(adapter);
                } else {
                    mostrarMensaje("Error al obtener los síntomas");
                }
            }

            @Override
            public void onFailure(Call<List<Sintoma>> call, Throwable t) {
                mostrarMensaje("Error en la conexión");
            }
        });
    }

    private void buscarSintoma(String sintoma) {
        // Buscar el síntoma en la lista de síntomas
        Sintoma encontrado = null;
        for (Sintoma s : sintomasList) {
            if (s.getNombreSintoma().equalsIgnoreCase(sintoma)) {
                encontrado = s;
                break;
            }
        }

        // Limpiar el RelativeLayout de CheckBox
        checkboxLayout.removeAllViews();

        // Si se encontró el síntoma, crear un CheckBox y mostrarlo en el RelativeLayout
        if (encontrado != null) {
            CheckBox checkBox = new CheckBox(this);
            checkBox.setText(encontrado.getNombreSintoma());

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            );
            params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            checkBox.setLayoutParams(params);

            checkboxLayout.addView(checkBox);
            mostrarMensaje("Síntoma encontrado: " + encontrado.getNombreSintoma());
        } else {
            mostrarMensaje("No se encuentra el síntoma indicado");
        }
    }
    private void mostrarMensaje(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }

}
