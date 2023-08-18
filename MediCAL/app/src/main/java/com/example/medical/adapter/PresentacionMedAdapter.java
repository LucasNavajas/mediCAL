package com.example.medical.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medical.R;
import com.example.medical.model.PresentacionMed;
import com.example.medical.ElegirFrecuenciaActivity;
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

        // Agregar OnClickListener al elemento de la lista
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Crear un Intent para la nueva actividad
                Intent intent = new Intent(view.getContext(), ElegirFrecuenciaActivity.class);

                // Puedes pasar datos adicionales si es necesario utilizando intent.putExtra()

                // Iniciar la nueva actividad
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return presentacionMedList.size();
    }
}
