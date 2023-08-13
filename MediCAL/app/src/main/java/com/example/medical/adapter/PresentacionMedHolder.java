package com.example.medical.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medical.R;

public class PresentacionMedHolder extends RecyclerView.ViewHolder {

    TextView nombrepre;

    public PresentacionMedHolder(@NonNull View itemView) {
        super(itemView);
        nombrepre = itemView.findViewById(R.id.admpre_list);
    }
}