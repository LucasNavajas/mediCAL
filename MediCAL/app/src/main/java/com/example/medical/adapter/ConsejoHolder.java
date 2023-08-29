package com.example.medical.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medical.R;

public class ConsejoHolder extends RecyclerView.ViewHolder {

    // Traer objetos ?

    // 1: Consejos sobre la App (Sin Imagen, Con Auspiciante MediCAL, Con Link a Manual)
    // 2: Consejos sobre Salud y Bienestar (Sin Imagen, Sin Auspiciante, Con Link a Noticia)
    // 3: Consejos Médicos (Con Imagen, Con Auspiciante, Con Link a Video)

    ImageView iconoConsejo;
    TextView nombreConsejo;
    TextView descripcionConsejo;
    ImageView foto;
    TextView auspiciante;

    public ConsejoHolder(@NonNull View itemView) {
        super(itemView);
        iconoConsejo = itemView.findViewById(R.id.icono_consejo);
        nombreConsejo = itemView.findViewById(R.id.texto_titulo_consejo);
        descripcionConsejo = itemView.findViewById(R.id.texto_consejo);
        foto = itemView.findViewById(R.id.videoImageView);
        auspiciante = itemView.findViewById(R.id.texto_Auspiciante);

    }
}
