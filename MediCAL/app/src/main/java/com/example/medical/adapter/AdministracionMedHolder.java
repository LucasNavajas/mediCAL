package com.example.medical.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medical.R;

public class AdministracionMedHolder extends RecyclerView.ViewHolder {

    TextView nombreadm;

    public AdministracionMedHolder(@NonNull View itemView) {
        super(itemView);
        nombreadm = itemView.findViewById(R.id.admpre_list);
    }
}