package com.example.medical.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medical.R;
import com.example.medical.model.PresentacionMed;

import java.util.List;

public class PresentacionMedAdapter extends RecyclerView.Adapter<PresentacionMedHolder> {

    private List<PresentacionMed> presentacionMedList;

    public PresentacionMedAdapter(List<PresentacionMed> presentacionMedList) {
        this.presentacionMedList = presentacionMedList;
    }


    @NonNull
    @Override
    public PresentacionMedHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.n33a37_items, parent, false);
        return new PresentacionMedHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PresentacionMedHolder holder, int position) {
        PresentacionMed presentacionMed = presentacionMedList.get(position);
        holder.nombrepre.setText(presentacionMed.getNombrePresentacionMed());

    }

    @Override
    public int getItemCount() {
        return presentacionMedList.size();
    }

}
