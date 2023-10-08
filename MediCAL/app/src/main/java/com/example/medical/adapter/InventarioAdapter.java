package com.example.medical.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medical.R;

import com.example.medical.model.Inventario;

import org.w3c.dom.Text;

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

    // Método para actualizar la lista de inventarios
    public void setInventarioList(List<Inventario> inventarioList) {
        this.inventarioList = inventarioList;
        notifyDataSetChanged(); // Notifica al RecyclerView de que los datos han cambiado
    }

    @Override
    public void onBindViewHolder(@NonNull InventarioViewHolder holder, int position) {
        Inventario inventario = inventarioList.get(position);

        // Obtener el nombre del medicamento desde el recordatorio asociado al inventario
        String nombreMedicamento = inventario.getRecordatorio().getMedicamento().getNombreMedicamento();
        String marcaMedicamento = inventario.getRecordatorio().getMedicamento().getMarcaMedicamento();
        float valorConcentracion = inventario.getRecordatorio().getDosis().getValorConcentracion();
        String concentracion = (String.valueOf(valorConcentracion) + inventario.getRecordatorio().getDosis().getConcentracion().getUnidadMedidaC());

        // Establecer el nombre del medicamento en el TextView tituloInventario
        holder.tituloInventario.setText(nombreMedicamento + " - " + marcaMedicamento + " - " + concentracion);

        holder.cant_real.setText("Stock Real: "+ String.valueOf(inventario.getCantRealInventario()));
        holder.cant_aviso.setText("Stock Mínimo de Alerta: " + String.valueOf(inventario.getCantAvisoInventario()));
    }

    @Override
    public int getItemCount() {
        return inventarioList.size();
    }

    static class InventarioViewHolder extends RecyclerView.ViewHolder {
        TextView tituloInventario;
        TextView cant_real;
        TextView cant_aviso;

        public InventarioViewHolder(@NonNull View itemView) {
            super(itemView);
            tituloInventario = itemView.findViewById(R.id.texto_titulo_inventario);
            cant_real = itemView.findViewById(R.id.texto_cant_real);
            cant_aviso = itemView.findViewById(R.id.texto2_cant_aviso);
        }
    }
}
