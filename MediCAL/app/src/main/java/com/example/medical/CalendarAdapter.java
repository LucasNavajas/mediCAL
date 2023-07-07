package com.example.medical;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import android.content.Context;

import androidx.core.content.ContextCompat;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarViewHolder> {

    private final ArrayList<LocalDate> dias;
    private final OnItemListener onItemListener;
    private LocalDate selectedDate;
    private List<TextView> textosDia;
    private Context context;

    public CalendarAdapter(ArrayList<LocalDate> dias, OnItemListener onItemListener, LocalDate selectedDate, List<TextView> textosDia, Context context) {
        this.dias = dias;
        this.onItemListener = onItemListener;
        this.selectedDate = selectedDate;
        this.textosDia = textosDia;
        this.context = context;
    }

    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.n13y14_calendar_cell, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (dias.size() > 15) // month view
            layoutParams.height = (int) (parent.getHeight() * 0.16666666);
        else // week view
            layoutParams.height = (int) (parent.getHeight());

        return new CalendarViewHolder(view, onItemListener, dias);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position) {
        final LocalDate date = dias.get(position);
        holder.diaMes.setText(String.valueOf(date.getDayOfMonth()));
        if (date.equals(selectedDate)) {
            colorearTextoDia(selectedDate.getDayOfWeek().getValue(), textosDia, context);
            holder.circleImageView.setVisibility(View.VISIBLE);
            holder.diaMes.setTextColor(Color.WHITE);
        } else {
            holder.circleImageView.setVisibility(View.INVISIBLE);
            if (date.getMonth().equals(selectedDate.getMonth())) {
                holder.diaMes.setTextColor(Color.BLACK);
            } else {
                holder.diaMes.setTextColor(Color.LTGRAY);
            }
        }
    }

    private void colorearTextoDia(int dia, List<TextView> textosDia, Context context) {
            for(int i=0; i<textosDia.size(); i++){
                if (i==dia-1){
                    textosDia.get(i).setTextColor(ContextCompat.getColor(context, R.color.verdeTextos));
                }
                else{
                    textosDia.get(i).setTextColor(ContextCompat.getColor(context, R.color.black));
                }
            }
        }


    @Override
    public int getItemCount() {
        return dias.size();
    }

    public interface OnItemListener {
        void onItemClick(int position, LocalDate date);
    }
}
