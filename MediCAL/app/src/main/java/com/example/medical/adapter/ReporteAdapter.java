package com.example.medical.adapter;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medical.AgregarReporteActivity;
import com.example.medical.CompartirReporteActivity;
import com.example.medical.DescargarReporteActivity;
import com.example.medical.R;

import com.example.medical.model.Reporte;

import java.time.LocalDate;
import java.util.List;


public class ReporteAdapter extends RecyclerView.Adapter<ReporteAdapter.ReporteViewHolder>  {

    private List<Reporte> reporteList;
    private int codCalendarioOriginal;
    private int codUsuarioOriginal;
    private OnEliminarReporteListener eliminarReporteListener;

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

    public void setCodCalendarioSeleccionado(int codigoCalendario) {
        codCalendarioOriginal = codigoCalendario;
    }

    public void setCodUsuarioLogeado(int codigoUsuario) {
        codUsuarioOriginal = codigoUsuario;
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
        if(nombreTipoReporte.equals("Reporte Medicamento (Uno)")) {
            holder.tituloTipoReporte.setText("Reporte Medicamento (" + reporte.getNombreMed()+")");
        } else {
            holder.tituloTipoReporte.setText(nombreTipoReporte);
        }
        holder.fechaGenerada.setText("Generado: " + String.valueOf(fechaGenerada));
        holder.fechaDesdeHasta.setText("Desde: "+ String.valueOf(fechaDesde) + " / \nHasta: " + String.valueOf(fechaHasta));

        // Agregar evento onClick a las imágenes

        holder.eliminarReporte.setOnClickListener(view -> {
            Log.d("MiApp", "Se hizo clic en el botón eliminarReporte");
            if (eliminarReporteListener != null) {
                eliminarReporteListener.onEliminarReporte(reporte.getNroReporte());
            }
        });
        holder.imagenCompartir.setOnClickListener(view -> {
            Log.d("MiApp", "Se hizo clic en el botón compartir");
            // Crear un Intent para abrir CompartirReporteActivity
            Intent compartirIntent = new Intent(view.getContext(), CompartirReporteActivity.class);
            compartirIntent.putExtra("nroReporte", reporte.getNroReporte());
            compartirIntent.putExtra("calendarioSeleccionadoid", codCalendarioOriginal);
            compartirIntent.putExtra("codUsuario", codUsuarioOriginal);
            // Iniciar CompartirReporteActivity
            view.getContext().startActivity(compartirIntent);
        });
        holder.imagenDescargar.setOnClickListener(view -> {
            Log.d("MiApp", "Se hizo clic en el botón descargar");
            // Crear un Intent para abrir DescargarReporteActivity
            Intent descargarIntent = new Intent(view.getContext(), DescargarReporteActivity.class);
            descargarIntent.putExtra("nroReporte", reporte.getNroReporte());
            descargarIntent.putExtra("calendarioSeleccionadoid", codCalendarioOriginal);
            descargarIntent.putExtra("codUsuario", codUsuarioOriginal);
            // Iniciar DescargarReporteActivity
            view.getContext().startActivity(descargarIntent);
        });

    }

    @Override
    public int getItemCount() {
        return reporteList.size();
    }

    static class ReporteViewHolder extends RecyclerView.ViewHolder {
        TextView tituloTipoReporte;
        TextView fechaGenerada;
        TextView fechaDesdeHasta;
        ImageView eliminarReporte;
        ImageView imagenCompartir;
        ImageView imagenDescargar;

        public ReporteViewHolder(@NonNull View itemView) {
            super(itemView);
            tituloTipoReporte = itemView.findViewById(R.id.texto_titulo_tipoReporte);
            fechaGenerada = itemView.findViewById(R.id.texto_fecha_generada);
            fechaDesdeHasta = itemView.findViewById(R.id.texto_fecha_desde_hasta);
            eliminarReporte = itemView.findViewById(R.id.imagen_eliminar);
            imagenCompartir = itemView.findViewById(R.id.imagen_compartir);
            imagenDescargar = itemView.findViewById(R.id.imagen_descargar);
        }
    }

    public void setEliminarReporteListener(OnEliminarReporteListener listener) {
        this.eliminarReporteListener = listener;
    }

    public interface OnEliminarReporteListener {
        void onEliminarReporte(int nroReporte);
    }

}
