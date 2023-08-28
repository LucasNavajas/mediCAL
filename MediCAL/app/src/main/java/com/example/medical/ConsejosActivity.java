package com.example.medical;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.annotation.SuppressLint;

import com.example.medical.FiltrosDeEditText.TextOnlyInputFilter;
import com.example.medical.adapter.ConsejoAdapter;
import com.example.medical.model.Consejo;
import com.example.medical.retrofit.ConsejoApi;
import com.example.medical.retrofit.RetrofitService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.util.List;


public class ConsejosActivity extends AppCompatActivity {

        private ImageView menuButton;
        private TextView nombreUsuario;
        private LinearLayout casa_inicio;
        private ImageView menuButtonUsuario;
        private LinearLayout mas;
        private ImageButton masimagen;
        private ImageButton imageninicio;
        private RelativeLayout editarPerfil;
        private RelativeLayout restablecerContrasenia;
        private RelativeLayout cerrarSesion;
        private RelativeLayout soporte;

        private FirebaseUser usuario;


        private RetrofitService retrofitService;

        private RecyclerView recyclerView;
        private PopupWindow popupWindow;

        private FirebaseAuth mAuth = FirebaseAuth.getInstance();
        private int codUsuarioLogeado;


        @SuppressLint("MissingInflatedId")
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.n22_consejos);
            Intent intent1 = getIntent();
            inicializarVariables();

            recyclerView = findViewById(R.id.listaconsejos_recyclerview);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            loadConsejos();

            menuButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            });

            menuButtonUsuario.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
            });

            cerrarSesion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    popupCerrarSesion();
                }
            });


            editarPerfil.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ConsejosActivity.this, EditarPerfilUsuarioActivity.class);
                    intent.putExtra("codUsuario", codUsuarioLogeado);
                    startActivity(intent);
                }
            });

            soporte.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ConsejosActivity.this, FAQActivity.class);
                    startActivity(intent);
                }
            });

            restablecerContrasenia.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ConsejosActivity.this, RestablecerContraseniaActivity.class);
                    intent.putExtra("codUsuario", codUsuarioLogeado);
                    startActivity(intent);

                }
            });

            // iconos debajo

            mas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ConsejosActivity.this, MasActivity.class);
                    intent.putExtra("codUsuario", codUsuarioLogeado);
                    startActivity(intent);
                }
            });

            masimagen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ConsejosActivity.this, MasActivity.class);
                    intent.putExtra("codUsuario", codUsuarioLogeado);
                    startActivity(intent);
                }
            });

            casa_inicio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ConsejosActivity.this, InicioCalendarioActivity.class);
                    intent.putExtra("codUsuario", codUsuarioLogeado);
                    startActivity(intent);

                }
            });

            imageninicio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ConsejosActivity.this, InicioCalendarioActivity.class);
                    intent.putExtra("codUsuario", codUsuarioLogeado);
                    startActivity(intent);

                }
            });


        }

        private void loadConsejos() {
            RetrofitService retrofitService = new RetrofitService();
            ConsejoApi consejoApi = retrofitService.getRetrofit().create(ConsejoApi.class);
            consejoApi.getAllConsejos()
                    .enqueue(new Callback<List<Consejo>>() {

                        @Override
                        public void onResponse(Call<List<Consejo>> call, Response<List<Consejo>> response) {
                            Log.d("ConsejoActivity", "llamo el método on response");
                            int statusCode = response.code();
                            Log.d("ConsejoActivity", "Status code: " + statusCode);
                            if (response.isSuccessful() && response.body() != null) {
                                Log.d("ConsejoActivity", "la populo");
                                populateListView(response.body());
                            } else {
                                Log.d("ConsejoActivity", "no anda");
                                Log.d("ConsejoActivity", "Response code: " + response.code());
                                Log.d("ConsejoActivity", "Error body: " + response.errorBody());
                                Toast.makeText(ConsejosActivity.this, "Respuesta vacía o incorrecta del servidor", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Consejo>> call, Throwable t) {
                            Log.d("ConsejoActivity", "no carga");
                            Toast.makeText(ConsejosActivity.this, "Fallo en la base de datos", Toast.LENGTH_SHORT).show();
                        }
                    });
        }

        private void populateListView(List<Consejo> consejoList) {
            if (consejoList != null && !consejoList.isEmpty()) {
                Log.d("ConsejoActivity", "la populo en la list");
                ConsejoAdapter consejoAdapter = new ConsejoAdapter (consejoList);
                recyclerView.setAdapter(consejoAdapter);

            } else {
                Log.d("ConsejoActivity", "no la populo en la list");
                Toast.makeText(ConsejosActivity.this, "La lista de Consejos está vacía o es nula", Toast.LENGTH_SHORT).show();
            }
        }


        //@SuppressLint("WrongViewCast") ?
        @SuppressLint("WrongViewCast")
        private void inicializarVariables () {

            nombreUsuario = findViewById(R.id.nombre_usuario);

            editarPerfil = findViewById(R.id.editar_perfil);
            restablecerContrasenia = findViewById(R.id.restablecer_contrasenia);

            cerrarSesion = findViewById(R.id.cerrar_sesion);
            soporte = findViewById(R.id.soporte);
            mas = findViewById(R.id.mas);
            masimagen =findViewById(R.id.masimagen);
            imageninicio =findViewById(R.id.casa_inicio);
            casa_inicio = findViewById(R.id.inicio);
            menuButton = findViewById(R.id.menu_button);
            menuButtonUsuario = findViewById(R.id.menu_button_nav);

            retrofitService = new RetrofitService();

        }

        private void popupCerrarSesion() {
            DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
            drawerLayout.closeDrawer(GravityCompat.START);
            View popupView = getLayoutInflater().inflate(R.layout.n14_3_popup_cerrar_sesion, null);

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
            TextView textViewCancelar = popupView.findViewById(R.id.cancelar);

            textViewCancelar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Ocultar el PopupWindow
                    popupWindow.dismiss();

                    // Ocultar el fondo oscurecido
                    dimView.setVisibility(View.GONE);
                }
            });
        }

    }



