package com.example.medical.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medical.R;

import com.example.medical.model.Reporte;

import org.w3c.dom.Text;

import java.time.LocalDate;
import java.util.List;


public class ReporteAdapter extends RecyclerView.Adapter<ReporteAdapter.ReporteViewHolder>  {

    private List<Reporte> reporteList;

    public ReporteAdapter(List<Reporte> reporteList) {
        this.reporteList = reporteList;
    }

    @NonNull
    @Override
    public ReporteAdapter.ReporteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.n86_1_reporte_cargado_item, parent, false);
        return new ReporteViewHolder(view);
    }

    // Método para actualizar la lista de reportes
    public void setReporteList(List<Reporte> reporteList) {
        this.reporteList = reporteList;
        notifyDataSetChanged(); // Notifica al RecyclerView de que los datos han cambiado
    }

    @Override
    public void onBindViewHolder(@NonNull ReporteViewHolder holder, int position) {
        Reporte reporte = reporteList.get(position);

        // Obtener el nombre del tipoReporte desde el reporte
        String nombreTipoReporte = reporte.getTipoReporte().getNombreTipoReporte();

        LocalDate fechaGenerada = reporte.getFechaGenerada();
        LocalDate fechaDesde = reporte.getFechaDesde();
        LocalDate fechaHasta = reporte.getFechaHasta();

        // Establecer el nombre del tipoReporte en el TextView tituloTipoReporte
        holder.tituloTipoReporte.setText(nombreTipoReporte);
        holder.fechaGenerada.setText("Generado: " + String.valueOf(fechaGenerada));
        holder.fechaDesdeHasta.setText("Desde: "+ String.valueOf(fechaDesde) + " / \nHasta: " + String.valueOf(fechaHasta));
    }

    @Override
    public int getItemCount() {
        return reporteList.size();
    }

    static class ReporteViewHolder extends RecyclerView.ViewHolder {
        TextView tituloTipoReporte;
        TextView fechaGenerada;
        TextView fechaDesdeHasta;

        public ReporteViewHolder(@NonNull View itemView) {
            super(itemView);
            tituloTipoReporte = itemView.findViewById(R.id.texto_titulo_tipoReporte);
            fechaGenerada = itemView.findViewById(R.id.texto_fecha_generada);
            fechaDesdeHasta = itemView.findViewById(R.id.texto_fecha_desde_hasta);
        }
    }
}
