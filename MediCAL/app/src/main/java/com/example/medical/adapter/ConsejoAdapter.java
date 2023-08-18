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

import java.util.List;

public class ConsejoAdapter extends RecyclerView.Adapter<ConsejoAdapter.ConsejoViewHolder> {

    private List<Consejo> consejoList;

    public ConsejoAdapter(List<Consejo> faqList) {
        this.consejoList = consejoList;
    }

    @NonNull
    @Override
    public ConsejoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.n22_consejos, parent, false);
        return new ConsejoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ConsejoViewHolder holder, int position) {
        Consejo consejo = consejoList.get(position);

        holder.nombreConsejo.setText(consejo.getNombreConsejo());
        holder.descripcionConsejo.setText(consejo.getDescConsejo());

        // Sería el link al cual se accede clickeando la foto para consejos con video
        // holder.linkConsejo.setText(consejo.getLinkConsejo());

        // Sería la foto tipo botón para consejos con video
        // holder.fotoConsejo.setText(consejo.getFotoConsejo());

    }

    @Override
    public int getItemCount() {
        return consejoList.size();
    }

    static class ConsejoViewHolder extends RecyclerView.ViewHolder {
        TextView nombreConsejo;
        TextView descripcionConsejo;
        ImageView likeConsejo;

        // fotoConsejo sería poner la foto del consejo, que sería un botón que lleva al link del video
        // ImageView fotoConsejo;

        public ConsejoViewHolder(@NonNull View itemView) {
            super(itemView);

            nombreConsejo = itemView.findViewById(R.id.texto_titulo_consejo);
            descripcionConsejo = itemView.findViewById(R.id.texto_consejo);
            likeConsejo = itemView.findViewById(R.id.imagen_consejo_like);

            //fotoConsejo = itemView.findViewById(R.id.videoImageView);
        }
    }
}
