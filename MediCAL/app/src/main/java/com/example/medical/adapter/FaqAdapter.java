package com.example.medical.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medical.R;
import com.example.medical.model.FAQ;

import java.util.List;

public class FaqAdapter extends RecyclerView.Adapter<FaqAdapter.FaqViewHolder> {

    private List<FAQ> faqList;

    public FaqAdapter(List<FAQ> faqList) {
        this.faqList = faqList;
    }

    @NonNull
    @Override
    public FaqViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.n28y29_preguntas_respuestas_frecuentes_item, parent, false);
        return new FaqViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FaqViewHolder holder, int position) {
        FAQ faq = faqList.get(position);
        holder.preguntaTextView.setText(faq.getPreguntatFAQ());
        holder.respuestaTextView.setText(faq.getRespuestaFAQ());

        // Agregar el OnClickListener para mostrar/ocultar la respuesta
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.respuestaTextView.getVisibility() == View.VISIBLE) {
                    holder.respuestaTextView.setVisibility(View.GONE);
                } else {
                    holder.respuestaTextView.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return faqList.size();
    }

    static class FaqViewHolder extends RecyclerView.ViewHolder {
        TextView preguntaTextView;
        TextView respuestaTextView;

        public FaqViewHolder(@NonNull View itemView) {
            super(itemView);
            preguntaTextView = itemView.findViewById(R.id.faqlist_pregunta);
            respuestaTextView = itemView.findViewById(R.id.faqlist_respuesta);
        }
    }
}
