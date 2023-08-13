package com.example.medical.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.medical.R;
import com.example.medical.model.AdministracionMed;
import java.util.List;

public class AdministracionMedAdapter extends RecyclerView.Adapter<AdministracionMedHolder> {

    private List<AdministracionMed> administracionMedList;
    private OnItemClickListener listener;

    public AdministracionMedAdapter(List<AdministracionMed> administracionMedList) {
        this.administracionMedList = administracionMedList;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public AdministracionMedHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.n33a37_items, parent, false);
        return new AdministracionMedHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdministracionMedHolder holder, int position) {
        AdministracionMed administracionMed = administracionMedList.get(position);
        holder.nombreadm.setText(administracionMed.getNombreAdministracionMed());

        // Establecer el clic en el elemento
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(administracionMed);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return administracionMedList.size();
    }

    // Interfaz para manejar el clic en los elementos
    public interface OnItemClickListener {
        void onItemClick(AdministracionMed item);
    }
}
