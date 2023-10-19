package com.example.medical.adapter;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medical.ConsejosActivity;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medical.R;
import com.example.medical.model.Consejo;
import com.example.medical.model.TipoConsejo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ConsejoAdapter extends RecyclerView.Adapter<ConsejoAdapter.ConsejoViewHolder> {

    private final Context context;
    private Map<Integer, List<Consejo>> consejoPorTipo = new HashMap<>();
    private List<Consejo> consejoAleatorioPorTipo = new ArrayList<>();

    public ConsejoAdapter(List<Consejo> consejoList, Context context) {
        this.context = context;

        // Agrupar consejos por tipo
        for (Consejo consejo : consejoList) {
            int tipoConsejo = consejo.getTipoConsejo().getNroTipoConsejo();
            if (!consejoPorTipo.containsKey(tipoConsejo)) {
                consejoPorTipo.put(tipoConsejo, new ArrayList<>());
            }
            consejoPorTipo.get(tipoConsejo).add(consejo);
        }

        // Seleccionar un consejo aleatorio de cada tipo
        for (List<Consejo> consejosDelTipo : consejoPorTipo.values()) {
            Consejo consejoAleatorio = obtenerConsejoAleatorio(consejosDelTipo);
            if (consejoAleatorio != null) {
                consejoAleatorioPorTipo.add(consejoAleatorio);
            }
        }

    }

    @NonNull
    @Override
    public ConsejoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.n22_consejos_item, parent, false);
        return new ConsejoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ConsejoViewHolder holder, int position) {

        Consejo consejoAleatorio = consejoAleatorioPorTipo.get(position);

        // Obtener un consejo aleatorio de ese tipo
        //List<Consejo> consejosDelTipo = consejoPorTipo.get(tipoConsejo);
        //Consejo consejoAleatorio = obtenerConsejoAleatorio(consejosDelTipo);

        ImageView iconoConsejo = holder.iconoConsejo;;
        TextView leerMas = holder.leerMas;
        View lineaLeerMas = holder.lineaLeerMas;
        ImageView like = holder.like;
        ImageView likeado = holder.likeado;
        ImageView compartir = holder.compartir;
        ImageView foto = holder.foto;
        ImageView play= holder.play;
        TextView auspiciante = holder.auspiciante;

        holder.nombreConsejo.setText(consejoAleatorio.getNombreConsejo());
        holder.descripcionConsejo.setText(consejoAleatorio.getDescConsejo());
        holder.auspiciante.setText(consejoAleatorio.getAuspiciante());

        // Obtener el TipoConsejo del consejoAleatorio actual
        TipoConsejo tipo = consejoAleatorio.getTipoConsejo();

        // Configurar el ícono según el tipo de consejo
        if (tipo.getNroTipoConsejo() == 3) {
            iconoConsejo.setImageResource(R.drawable.foco_consejo);
            foto.setVisibility(View.GONE);
            play.setVisibility(View.GONE);
            leerMas.setText("Ver Manual");

            // Link a Manual de Usuario

            leerMas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String linkManual = consejoAleatorio.getLinkConsejo();
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(linkManual));
                    context.startActivity(intent);
                }
            });
            like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    like.setVisibility(View.GONE);
                    likeado.setVisibility(View.VISIBLE);
                }
            });
            likeado.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    like.setVisibility(View.VISIBLE);
                    likeado.setVisibility(View.GONE);
                }
            });
            compartir.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    compartirConsejo(consejoAleatorio); // Llama al método para compartir el consejo
                }
            });

        } else if (tipo.getNroTipoConsejo() == 2) {
            iconoConsejo.setImageResource(R.drawable.foto_salud);
            foto.setVisibility(View.GONE);
            play.setVisibility(View.GONE);
            // Los consejos de Bienestar y Salud no tienen un auspiciante pago, y tampoco son propios de MediCAL
            // "Leer más" utiliza el link para llevarlos a una noticia relacionada
            auspiciante.setVisibility(View.GONE);

            leerMas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String link = consejoAleatorio.getLinkConsejo();
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                    context.startActivity(intent);
                }
            });
            like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    like.setVisibility(View.GONE);
                    likeado.setVisibility(View.VISIBLE);
                }
            });
            likeado.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    like.setVisibility(View.VISIBLE);
                    likeado.setVisibility(View.GONE);
                }
            });
            compartir.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    compartirConsejo(consejoAleatorio); // Llama al método para compartir el consejo
                }
            });

        } else if (tipo.getNroTipoConsejo() == 1) {
            iconoConsejo.setImageResource(R.drawable.foto_doctor);
            leerMas.setVisibility(View.GONE);
            lineaLeerMas.setVisibility(View.GONE);

            // Cargar la imagen del url utilizando Picasso
            Picasso.get().load(consejoAleatorio.getFotoConsejo()).into(foto);

            foto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String videoLink = consejoAleatorio.getLinkConsejo();
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(videoLink));
                    context.startActivity(intent);
                }
            });
            play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String videoLink = consejoAleatorio.getLinkConsejo();
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(videoLink));
                    context.startActivity(intent);
                }
            });
            like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    like.setVisibility(View.GONE);
                    likeado.setVisibility(View.VISIBLE);
                }
            });
            likeado.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    like.setVisibility(View.VISIBLE);
                    likeado.setVisibility(View.GONE);
                }
            });
            compartir.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    compartirConsejo(consejoAleatorio); // Llama al método para compartir el consejo
                }
            });

        }
    }

    public void compartirConsejo(Consejo consejo) {
        String link = consejo.getLinkConsejo();
        String nombreTipoConsejo = consejo.getTipoConsejo().getNombreTipoConsejo();
        String nombreConsejo = consejo.getNombreConsejo();
        String descripcionConsejo = consejo.getDescConsejo();
        String auspiciante = consejo.getAuspiciante();

        // Verificar si los atributos son nulos y proporciona valores predeterminados o omitirlos
        if (link == null || "null".equalsIgnoreCase(link)) {
            link = "";
        }
        if (nombreTipoConsejo == null || "null".equalsIgnoreCase(nombreTipoConsejo)) {
            nombreTipoConsejo = "";
        }
        if (nombreConsejo == null || "null".equalsIgnoreCase(nombreConsejo)) {
            nombreConsejo = "";
        }
        if (descripcionConsejo == null || "null".equalsIgnoreCase(descripcionConsejo)) {
            descripcionConsejo = "";
        }
        if (auspiciante == null || "null".equalsIgnoreCase(auspiciante)) {
            auspiciante = "";
        }

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");

        if (consejo.getTipoConsejo().getNroTipoConsejo() == 1) {
            intent.putExtra(Intent.EXTRA_TEXT, "¡Hola!\n" +
                    "\n" + "Estoy usando MediCAL. Te comparto este consejo " + nombreTipoConsejo + " por si te es de utilidad! \n" +
                    "\n" + nombreConsejo + "\n" +
                    "\n" + descripcionConsejo + "\n" +
                    "\n Auspiciado por: " + auspiciante + "\n" +
                    "\n" + link);
        } else if (consejo.getTipoConsejo().getNroTipoConsejo() == 2){
            intent.putExtra(Intent.EXTRA_TEXT, "¡Hola!\n" +
                    "\n" + "Estoy usando MediCAL. Te comparto este consejo sobre '" + nombreTipoConsejo + "' por si te es de utilidad! \n" +
                    "\n" + nombreConsejo + "\n" +
                    "\n" + descripcionConsejo + "\n" +
                    "\n" + link);
        } else if (consejo.getTipoConsejo().getNroTipoConsejo() == 3){
            intent.putExtra(Intent.EXTRA_TEXT, "¡Hola!\n" +
                    "\n" + "Estoy usando MediCAL. Te comparto este consejo sobre el uso de la App por si te es de utilidad! \n" +
                    "\n" + nombreConsejo + "\n" +
                    "\n" + descripcionConsejo + "\n" +
                    "\n" + link);
        }
        context.startActivity(Intent.createChooser(intent, "Compartir vía"));
    }

    // Método para obtener un consejo aleatorio de una lista de consejos
    private Consejo obtenerConsejoAleatorio(List<Consejo> consejos) {
        if (consejos == null || consejos.isEmpty()) {
            return null;
        }
        Random random = new Random();
        int indiceAleatorio = random.nextInt(consejos.size());
        return consejos.get(indiceAleatorio);
    }

    @Override
    public int getItemCount() {
        return consejoAleatorioPorTipo.size(); // Solo mostrar un consejo aleatorio por tipo
    }

    static class ConsejoViewHolder extends RecyclerView.ViewHolder {
        ImageView iconoConsejo;
        TextView nombreConsejo;
        TextView descripcionConsejo;
        TextView leerMas;
        View lineaLeerMas;

        // fotoConsejo sería poner la foto del consejo, que sería un botón que lleva al link del video
        ImageView foto;
        ImageView play;

        TextView auspiciante;
        ImageView like;
        ImageView likeado;
        ImageView compartir;

        public ConsejoViewHolder(@NonNull View itemView) {
            super(itemView);

            iconoConsejo = itemView.findViewById(R.id.icono_consejo);
            nombreConsejo = itemView.findViewById(R.id.texto_titulo_consejo);
            descripcionConsejo = itemView.findViewById(R.id.texto_consejo);
            leerMas = itemView.findViewById(R.id.text_Leer_mas);
            lineaLeerMas = itemView.findViewById(R.id.linea_leer_mas);
            foto = itemView.findViewById(R.id.videoImageView);
            play = itemView.findViewById(R.id.videoImagePlay);
            auspiciante = itemView.findViewById(R.id.texto_Auspiciante);
            like = itemView.findViewById(R.id.imagen_like_vacio);
            likeado = itemView.findViewById(R.id.imagen_likeado);
            compartir = itemView.findViewById(R.id.imagen_consejo_compartir);

        }
    }
}
