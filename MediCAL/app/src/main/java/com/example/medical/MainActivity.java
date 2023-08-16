package com.example.medical;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.example.medical.model.Calendario;
import com.example.medical.model.Usuario;
import com.example.medical.retrofit.AdministracionMedApi;
import com.example.medical.retrofit.CalendarioApi;
import com.example.medical.retrofit.FAQApi;
import com.example.medical.retrofit.RetrofitService;
import com.example.medical.retrofit.UsuarioApi;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RetrofitService retrofitService = new RetrofitService();
    private UsuarioApi usuarioApi = retrofitService.getRetrofit().create(UsuarioApi.class);
    private CalendarioApi calendarioApi = retrofitService.getRetrofit().create(CalendarioApi.class);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.n00_inicio_app);
        FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser();
        initializeComponents();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (usuario!=null){
                    redirigirUsuario(usuario.getEmail());
                }
                else {
                    Intent intent = new Intent(MainActivity.this, BienvenidoActivity.class); //cambiar el segundo parametro por el nombre de la actividad a probar
                    startActivity(intent);
                    finish();
                }
            }
        }, 3000); // 3000 representa 3 segundos en milisegundos

    }

    private void initializeComponents() {
        RetrofitService retrofitService = new RetrofitService();
        FAQApi faqApi = retrofitService.getRetrofit().create(FAQApi.class);
        AdministracionMedApi administracionMedApi= retrofitService.getRetrofit().create(AdministracionMedApi.class);
    }

    private void redirigirUsuario(String mail) {
        usuarioApi.getByMailUsuario(mail).enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                Usuario usuarioLogeado = response.body();
                calendarioApi.getByCodUsuario(usuarioLogeado.getCodUsuario()).enqueue(new Callback<List<Calendario>>() {
                    @Override
                    public void onResponse(Call<List<Calendario>> call, Response<List<Calendario>> response) {
                        List<Calendario> calendarios = response.body();
                        if(calendarios.isEmpty()){
                            Intent intent = new Intent(MainActivity.this, BienvenidoUsuarioActivity.class);
                            intent.putExtra("usuario", usuarioLogeado.getUsuarioUnico());
                            startActivity(intent);
                        }
                        else{
                            Intent intent2 = new Intent(MainActivity.this, InicioCalendarioActivity.class);
                            intent2.putExtra("codCalendario", calendarios.get(0).getCodCalendario());
                            startActivity(intent2);
                        }

                    }

                    @Override
                    public void onFailure(Call<List<Calendario>> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "Hubo un error al iniciar sesión, intente nuevamente", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, BienvenidoActivity.class); //cambiar el segundo parametro por el nombre de la actividad a probar
                        startActivity(intent);
                        finish();
                    }
                });

            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Hubo un error al iniciar sesión, intente nuevamente", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, BienvenidoActivity.class); //cambiar el segundo parametro por el nombre de la actividad a probar
                startActivity(intent);
                finish();
            }
        });
    }

}