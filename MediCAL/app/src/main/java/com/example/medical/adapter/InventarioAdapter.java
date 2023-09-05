package com.example.medical.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medical.R;

import com.example.medical.model.Inventario;

import java.util.List;


public class InventarioAdapter extends RecyclerView.Adapter<InventarioAdapter.InventarioViewHolder>  {

    private List<Inventario> inventarioList;

    public InventarioAdapter(List<Inventario> inventarioList) {
        this.inventarioList = inventarioList;
    }

    @NonNull
    @Override
    public InventarioAdapter.InventarioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.n88_0_1_inventario_cargado_item, parent, false);
        return new InventarioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InventarioViewHolder holder, int position) {
        Inventario inventario = inventarioList.get(position);

        // Se debe obtener nombre medicamento desde el recordatorio asociado al inventario
        //holder.tituloInventario.setText();
        holder.cant_real.setText(inventario.getCantRealInventario());
        holder.cant_aviso.setText(inventario.getCantAvisoInventario());
    }

    @Override
    public int getItemCount() {
        return inventarioList.size();
    }

    static class InventarioViewHolder extends RecyclerView.ViewHolder {
        //TextView tituloInventario;
        TextView cant_real;
        TextView cant_aviso;

        public InventarioViewHolder(@NonNull View itemView) {
            super(itemView);
            //tituloInventario = itemView.findViewById(R.id.texto_titulo_inventario);
            cant_real = itemView.findViewById(R.id.texto_cant_real);
            cant_aviso = itemView.findViewById(R.id.texto2_cant_aviso);
        }
    }
}
