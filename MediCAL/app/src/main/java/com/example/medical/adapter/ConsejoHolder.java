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

    // 1: Consejos sobre la App (Sin Video)
    // 2: Consejos sobre Salud y Bienestar (Sin Video)
    // 3: Consejos Médicos (Con Vídeo)

    ImageView iconoConsejo;
    TextView nombreConsejo;
    TextView descripcionConsejo;
    ImageView fotoConsejo;
    //ImageView likeConsejo;
    TextView auspiciante;

    public ConsejoHolder(@NonNull View itemView) {
        super(itemView);
        iconoConsejo = itemView.findViewById(R.id.icono_consejo);
        nombreConsejo = itemView.findViewById(R.id.texto_titulo_consejo);
        descripcionConsejo = itemView.findViewById(R.id.texto_consejo);
        fotoConsejo = itemView.findViewById(R.id.videoImageView);
        //likeConsejo = itemView.findViewById(R.id.imagen_consejo_like);
        auspiciante = itemView.findViewById(R.id.texto_Auspiciante);

    }
}
