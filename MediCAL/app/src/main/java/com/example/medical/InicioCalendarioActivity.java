package com.example.medical;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class InicioCalendarioActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener{

    private LocalDate selectedDate;
    private TextView fechaHoyText;
    private RecyclerView calendarRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.n13_inicio_calendario);
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
    }

    private void setView() {
        fechaHoyText.setText(mesDiaFromDate(selectedDate));
        ArrayList<LocalDate> daysInWeek = daysInWeekArray(selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInWeek, this, selectedDate);
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

    private String mesDiaFromDate(LocalDate date){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d");
        return date.format(formatter);
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

    }
}
