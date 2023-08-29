package com.example.medical.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.medical.ConsejosActivity;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medical.R;
import com.example.medical.model.Consejo;
import com.example.medical.model.TipoConsejo;

import java.util.List;

public class ConsejoAdapter extends RecyclerView.Adapter<ConsejoAdapter.ConsejoViewHolder> {

    private final Context context;
    private List<Consejo> consejoList;

    public ConsejoAdapter(List<Consejo> consejoList, Context context) {
        this.consejoList = consejoList;
        this.context = context;
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
        TextView leerMas = holder.leerMas;
        View lineaLeerMas = holder.lineaLeerMas;

        // fotoConsejo sería poner la foto del consejo, que sería un botón que lleva al link del video
        ImageView foto = holder.foto;
        TextView auspiciante = holder.auspiciante;

        // Obtener el TipoConsejo
        TipoConsejo tipoConsejo = consejo.getTipoConsejo();
        String nombreTipoConsejo = tipoConsejo.getNombreTipoConsejo();

        holder.nombreConsejo.setText(consejo.getNombreConsejo());
        holder.descripcionConsejo.setText(consejo.getDescConsejo());
        holder.auspiciante.setText(consejo.getAuspiciante());

        // Configurar el ícono según el tipo de consejo
        if (tipoConsejo.getNroTipoConsejo() == 3) {
            iconoConsejo.setImageResource(R.drawable.foco_consejo);
            foto.setVisibility(View.GONE);
            leerMas.setText("Ver Manual");

            // Link a Manual de Usuario
            /*
            leerMas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String linkManual = consejo.getLinkConsejo();
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(linkManual));
                    context.startActivity(intent);
                }
            });
            */

        } else if (tipoConsejo.getNroTipoConsejo() == 2) {
            iconoConsejo.setImageResource(R.drawable.foto_salud);
            foto.setVisibility(View.GONE);
            // Los consejos de Bienestar y Salud no tienen un auspiciante pago, y tampoco son propios de MediCAL
            // "Leer más" utiliza el link para llevarlos a una noticia relacionada
            auspiciante.setVisibility(View.GONE);

            leerMas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String link = consejo.getLinkConsejo();
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                    context.startActivity(intent);
                }
            });

        } else if (tipoConsejo.getNroTipoConsejo() == 1) {
            iconoConsejo.setImageResource(R.drawable.foto_doctor);
            // Cuando subamos una foto, se cambia esto, ya que los Médicos si tienen foto
            // La foto sería usada como botón para el link del video
            //foto.setVisibility(View.GONE);
            leerMas.setVisibility(View.GONE);
            lineaLeerMas.setVisibility(View.GONE);

            // Cargar la imagen del url utilizando Picasso
            Picasso.get().load(consejo.getFotoConsejo()).into(foto);

            foto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String videoLink = consejo.getLinkConsejo();
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(videoLink));
                    context.startActivity(intent);
                }
            });

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
        TextView leerMas;
        View lineaLeerMas;

        // fotoConsejo sería poner la foto del consejo, que sería un botón que lleva al link del video
        ImageView foto;
        TextView auspiciante;

        public ConsejoViewHolder(@NonNull View itemView) {
            super(itemView);

            iconoConsejo = itemView.findViewById(R.id.icono_consejo);
            nombreConsejo = itemView.findViewById(R.id.texto_titulo_consejo);
            descripcionConsejo = itemView.findViewById(R.id.texto_consejo);
            leerMas = itemView.findViewById(R.id.text_Leer_mas);
            lineaLeerMas = itemView.findViewById(R.id.linea_leer_mas);
            foto = itemView.findViewById(R.id.videoImageView);
            auspiciante = itemView.findViewById(R.id.texto_Auspiciante);

        }
    }
}
