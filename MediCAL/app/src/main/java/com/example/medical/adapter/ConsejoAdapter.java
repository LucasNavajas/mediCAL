package com.example.medical.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medical.R;
import com.example.medical.model.Consejo;
import com.example.medical.model.TipoConsejo;

import java.util.List;

public class ConsejoAdapter extends RecyclerView.Adapter<ConsejoAdapter.ConsejoViewHolder> {

    private List<Consejo> consejoList;

    public ConsejoAdapter(List<Consejo> consejoList) {
        this.consejoList = consejoList;
    }

    @NonNull
    @Override
    public ConsejoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.n22_consejos_item, parent, false);
        return new ConsejoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ConsejoViewHolder holder, int position) {
        Consejo consejo = consejoList.get(position);

        ImageView iconoConsejo = holder.iconoConsejo;;
        TextView nombreConsejo;
        TextView descripcionConsejo;

        // fotoConsejo sería poner la foto del consejo, que sería un botón que lleva al link del video
        // ImageView fotoConsejo = holder.fotoConsejo;

        // ImageView likeConsejo;

        TextView auspiciante = holder.auspiciante;


        // Obtener el TipoConsejo
        TipoConsejo tipoConsejo = consejo.getTipoConsejo();
        // ERROR ??
        String nombreTipoConsejo = tipoConsejo.getNombreTipoConsejo();

        // Configurar el ícono según el tipo de consejo
        if (nombreTipoConsejo == "Sobre la App") {
            iconoConsejo.setImageResource(R.drawable.foco_consejo);
            //fotoConsejo.setVisibility(View.GONE);
        } else if (nombreTipoConsejo == "Bienestar y Salud") {
            iconoConsejo.setImageResource(R.drawable.foto_salud);
            //fotoConsejo.setVisibility(View.GONE);
            // Los consejos de Bienestar y Salud no tienen un auspiciante pago, y tampoco son propios de MediCAL
            // Podrían estar relacionados al link de una noticia
            auspiciante.setVisibility(View.GONE);
        } else if (nombreTipoConsejo == "Medico") {
            iconoConsejo.setImageResource(R.drawable.foto_doctor);
            // Cuando subamos una foto, se cambia esto, ya que los Médicos si tienen foto
            // La foto sería usada como botón para el link del video
            //fotoConsejo.setVisibility(View.GONE);
        }

    }


    @Override
    public int getItemCount() {
        return consejoList.size();
    }

    static class ConsejoViewHolder extends RecyclerView.ViewHolder {
        ImageView iconoConsejo;
        TextView nombreConsejo;
        TextView descripcionConsejo;

        // fotoConsejo sería poner la foto del consejo, que sería un botón que lleva al link del video
        // ImageView fotoConsejo;

        //ImageView likeConsejo;

        TextView auspiciante;

        public ConsejoViewHolder(@NonNull View itemView) {
            super(itemView);

            iconoConsejo = itemView.findViewById(R.id.icono_consejo);
            nombreConsejo = itemView.findViewById(R.id.texto_titulo_consejo);
            descripcionConsejo = itemView.findViewById(R.id.texto_consejo);
            // fotoConsejo = itemView.findViewById(R.id.videoImageView);
            // likeConsejo = itemView.findViewById(R.id.imagen_consejo_like);
            auspiciante = itemView.findViewById(R.id.texto_Auspiciante);

        }
    }
}
