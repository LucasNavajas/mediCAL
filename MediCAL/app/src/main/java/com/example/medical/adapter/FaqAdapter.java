package com.example.medical.adapter;

import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medical.R;
import com.example.medical.model.FAQ;
import java.util.List;

public class FaqAdapter extends RecyclerView.Adapter<FaqHolder> {

    private List<FAQ> faqList;

    public FaqAdapter(List<FAQ> faqList) {
        this.faqList = faqList;
    }

    @NonNull
    @Override
    public FaqHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent,false);
        return new FaqHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FaqHolder holder, int position) {
        FAQ faq = faqList.get(position);
        holder.pregunta.setText(faq.getPreguntatFAQ());
        holder.respuesta.setText(faq.getRespuestaFAQ());

    }

    @Override
    public int getItemCount() {
        return faqList.size();
    }
}
