package com.example.medical;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormatSymbols;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class InicioCalendarioActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener{

    private LocalDate selectedDate;
    private TextView fechaHoyText;
    private RecyclerView calendarRecyclerView;
    private List<TextView> textosDia = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.n14_0_menu_desplegable);
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
