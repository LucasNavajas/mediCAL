package com.example.medical;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.ArrayList;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarViewHolder> {

    private final ArrayList<LocalDate> dias;
    private final OnItemListener onItemListener;
    private LocalDate selectedDate;

    public CalendarAdapter(ArrayList<LocalDate> dias, OnItemListener onItemListener, LocalDate selectedDate) {
        this.dias = dias;
        this.onItemListener = onItemListener;
        this.selectedDate = selectedDate;
    }

    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.calendar_cell, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if(dias.size() > 15) //month view
            layoutParams.height = (int) (parent.getHeight()*0.16666666);
        else //week view
            layoutParams.height = (int) (parent.getHeight());

        return new CalendarViewHolder(view, onItemListener, dias);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position) {
        final LocalDate date = dias.get(position);

        holder.diaMes.setText(String.valueOf(date.getDayOfMonth()));
        if(date.equals(selectedDate))
            holder.parentView.setBackgroundColor(Color.LTGRAY);

        if (date.getMonth().equals(selectedDate.getMonth()))
            holder.diaMes.setTextColor(Color.BLACK);
        else
            holder.diaMes.setTextColor(Color.LTGRAY);

    }

    @Override
    public int getItemCount() {
        return dias.size();
    }

    public interface OnItemListener{

        void onItemClick(int position, LocalDate date);
    }

}
