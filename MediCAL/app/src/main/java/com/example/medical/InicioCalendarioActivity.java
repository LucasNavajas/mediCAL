package com.example.medical;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medical.model.Calendario;
import com.example.medical.model.Usuario;
import com.example.medical.retrofit.CalendarioApi;
import com.example.medical.retrofit.CodigoVerificacionApi;
import com.example.medical.retrofit.RetrofitService;
import com.example.medical.retrofit.UsuarioApi;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.DateFormatSymbols;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class InicioCalendarioActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener{

    private LocalDate selectedDate;
    private TextView fechaHoyText;
    private RecyclerView calendarRecyclerView;
    private TextView nombreUsuario;
    private RelativeLayout editarPerfil;
    private RelativeLayout cerrarSesion;
    private RelativeLayout calendarioNuevo;
    private List<TextView> textosDia = new ArrayList<>();
    private RetrofitService retrofitService = new RetrofitService();
    private CalendarioApi calendarioApi = retrofitService.getRetrofit().create(CalendarioApi.class);
    private UsuarioApi usuarioApi = retrofitService.getRetrofit().create(UsuarioApi.class);
    private List<Calendario> listaCalendarios;
    private LinearLayout contenedorCalendarios;
    private FirebaseUser usuario;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private int codUsuarioLogeado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        usuario = FirebaseAuth.getInstance().getCurrentUser();
        if (usuario==null){
            Intent intent = new Intent(InicioCalendarioActivity.this, BienvenidoActivity.class); //cambiar el segundo parametro por el nombre de la actividad a probar
            startActivity(intent);
            finish();
        }
        setContentView(R.layout.n13y14_calendario_y_menu);
        selectedDate = LocalDate.now();
        initWidgets();
        setView();
        ImageView menuButton = findViewById(R.id.menu_button);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        cerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Intent intent = new Intent(InicioCalendarioActivity.this, BienvenidoActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish(); // Opcional: finaliza la actividad actual si ya no la necesitas en el back stack
            }
        });

        calendarioNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InicioCalendarioActivity.this, CrearCalendario1Activity.class);
                startActivity(intent);
            }
        });
        editarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InicioCalendarioActivity.this, EditarPerfilUsuarioActivity.class);
                intent.putExtra("codUsuario", codUsuarioLogeado);
                startActivity(intent);
            }
        });
    }



    private void initWidgets() {
        calendarRecyclerView = findViewById(R.id.calendar_recycler_view);
        fechaHoyText = findViewById(R.id.fecha_hoy_text);
        textosDia.add(findViewById(R.id.lunes));
        textosDia.add(findViewById(R.id.martes));
        textosDia.add(findViewById(R.id.miercoles));
        textosDia.add(findViewById(R.id.jueves));
        textosDia.add(findViewById(R.id.viernes));
        textosDia.add(findViewById(R.id.sabado));
        textosDia.add(findViewById(R.id.domingo));
        cerrarSesion = findViewById(R.id.cerrar_sesion);
        calendarioNuevo = findViewById(R.id.calendario_nuevo);
        contenedorCalendarios = findViewById(R.id.contenedor_calendarios);
        nombreUsuario = findViewById(R.id.nombre_usuario);
        editarPerfil = findViewById(R.id.editar_perfil);
        llenarListaCalendarios();
    }

    private void llenarContenedorCalendarios() {
        LinearLayout.LayoutParams imageLayoutParams = new LinearLayout.LayoutParams(
                dpToPx(50),
                dpToPx(50)
        );
        imageLayoutParams.setMargins(dpToPx(20), dpToPx(5), 0, 0);

        for (Calendario calendario : listaCalendarios) {
            RelativeLayout layoutCalendario = new RelativeLayout(this);

            ImageView imagenCalendario = new ImageView(this);
            imagenCalendario.setImageResource(R.drawable.calendario_contic);
            imagenCalendario.setLayoutParams(imageLayoutParams);
            imagenCalendario.setId(View.generateViewId()); // Asignar un ID único a la imagen

            TextView textoCalendario = new TextView(this);
            textoCalendario.setText(calendario.getNombreCalendario());
            textoCalendario.setTextSize(20);
            textoCalendario.setTextColor(getResources().getColor(R.color.black, null));
            textoCalendario.setTypeface(ResourcesCompat.getFont(this, R.font.inter_regular));

            RelativeLayout.LayoutParams textLayoutParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            );

            textLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
            textLayoutParams.addRule(RelativeLayout.END_OF, imagenCalendario.getId());
            textLayoutParams.setMarginStart(dpToPx(10));
            textoCalendario.setLayoutParams(textLayoutParams);

            layoutCalendario.addView(imagenCalendario);
            layoutCalendario.addView(textoCalendario);

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, dpToPx(10), 0, 0);
            layoutCalendario.setLayoutParams(params);

            contenedorCalendarios.addView(layoutCalendario);
        }
    }
    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }

    private void llenarListaCalendarios() {
        String mail = usuario.getEmail();
        usuarioApi.getByMailUsuario(mail).enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                Usuario usuarioLogeado = response.body();
                nombreUsuario.setText(usuarioLogeado.getUsuarioUnico());
                codUsuarioLogeado = usuarioLogeado.getCodUsuario();
                calendarioApi.getByCodUsuario(usuarioLogeado.getCodUsuario()).enqueue(new Callback<List<Calendario>>() {
                    @Override
                    public void onResponse(Call<List<Calendario>> call, Response<List<Calendario>> response) {
                        listaCalendarios = response.body();
                        llenarContenedorCalendarios();
                    }

                    @Override
                    public void onFailure(Call<List<Calendario>> call, Throwable t) {
                        Toast.makeText(InicioCalendarioActivity.this, "Hubo un error al obtener calendarios, intente nuevamente", Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Toast.makeText(InicioCalendarioActivity.this, "Hubo un error al iniciar sesión, intente nuevamente", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void setView() {
        fechaHoyText.setText(mesDiaFromDate(selectedDate));
        ArrayList<LocalDate> daysInWeek = daysInWeekArray(selectedDate);
        Context context = InicioCalendarioActivity.this;
        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInWeek, this, selectedDate, textosDia, context);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);

    }

    public void semanaPrevia(View view) {
        selectedDate = selectedDate.minusWeeks(1);
        setView();
    }

    public void semanaPosterior(View view) {
        selectedDate = selectedDate.plusWeeks(1);
        setView();
    }

    private String mesDiaFromDate(LocalDate date) {
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);
        LocalDate yesterday = today.minusDays(1);

        String dayString;
        if (date.isEqual(today)) {
            dayString = "Hoy, ";
        } else if (date.isEqual(yesterday)) {
            dayString = "Ayer, ";
        } else if (date.isEqual(tomorrow)) {
            dayString = "Mañana, ";
        } else {
            dayString = "";
        }

        String month = new DateFormatSymbols(new Locale("es")).getMonths()[date.getMonthValue() - 1];
        String formattedDate = dayString +  + date.getDayOfMonth() + " de " + month;
        return formattedDate;
    }


    private ArrayList<LocalDate> daysInWeekArray(LocalDate selectedDate) {
        ArrayList<LocalDate> dias = new ArrayList<>();
        LocalDate actual = sundayForDate(selectedDate);
        LocalDate fechaFin = actual.plusWeeks(1);

        while(actual.isBefore(fechaFin)){
            dias.add(actual);
            actual = actual.plusDays(1);
        }

        return dias;
    }

    private LocalDate sundayForDate(LocalDate actual) {

        LocalDate haceUnaSemana = actual.minusWeeks(1);

        while(actual.isAfter(haceUnaSemana)){
            if(actual.getDayOfWeek() == DayOfWeek.SUNDAY)
                return actual;

            actual = actual.minusDays(1);
        }
        return null;
    }

    @Override
    public void onItemClick(int position, LocalDate date) {
        selectedDate = date;
        setView();
    }
}
