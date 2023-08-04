package com.example.medical.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medical.R;

public class FaqHolder extends RecyclerView.ViewHolder {

    TextView pregunta, respuesta;

    public FaqHolder(@NonNull View itemView) {
        super(itemView);
        pregunta = itemView.findViewById(R.id.faqlist_pregunta);
        respuesta = itemView.findViewById(R.id.faqlist_respuesta);
    }
}
