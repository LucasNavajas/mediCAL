package com.example.medical;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.ArrayList;

public class CalendarViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


    private final ArrayList<LocalDate> dias;
    public final View parentView;
    public final TextView diaMes;
    private final CalendarAdapter.OnItemListener onItemListener;

    public CalendarViewHolder(@NonNull View itemView, CalendarAdapter.OnItemListener onItemListener, ArrayList<LocalDate> dias) {
        super(itemView);
        parentView = itemView.findViewById(R.id.parentView);
        diaMes = itemView.findViewById(R.id.textoCellDia);
        this.onItemListener = onItemListener;
        itemView.setOnClickListener(this);
        this.dias=dias;
    }

    @Override
    public void onClick(View view) {
        onItemListener.onItemClick(getAdapterPosition(), dias.get(getAdapterPosition()));
    }
}
