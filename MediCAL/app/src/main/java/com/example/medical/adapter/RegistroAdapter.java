package com.example.medical.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medical.R;
import com.example.medical.model.Consejo;
import com.example.medical.model.Recordatorio;
import com.example.medical.model.RegistroRecordatorio;
import com.example.medical.model.TipoConsejo;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class RegistroAdapter extends RecyclerView.Adapter<RegistroAdapter.RegistroViewHolder> {

    private final Context context;
    private List<RegistroRecordatorio> registrosDia = new ArrayList<>();

    public RegistroAdapter(List<RegistroRecordatorio> registrosDia, Context context) {
        this.context = context;
        this.registrosDia = registrosDia;

    }

    @NonNull
    @Override
    public RegistroAdapter.RegistroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.n13y14_recordatorio_item, parent, false);
        return new RegistroAdapter.RegistroViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RegistroAdapter.RegistroViewHolder holder, int position) {

        RegistroRecordatorio registroRecordatorio = registrosDia.get(position);
        Recordatorio recordatorio = registroRecordatorio.getRecordatorio();
        holder.hora.setText(String.valueOf(registroRecordatorio.getFechaTomaEsperada().getHour())+":"+String.valueOf(registroRecordatorio.getFechaTomaEsperada().getMinute()));
        holder.nombreMedicamento.setText(recordatorio.getMedicamento().getNombreMedicamento());
        String presentacionString= "";
        presentacionString = Float.toString(recordatorio.getDosis().getCantidadDosis()) + " "
                + registroRecordatorio.getRecordatorio().getPresentacionMed().getNombrePresentacionMed()+ " "
                + Float.toString(recordatorio.getDosis().getValorConcentracion())+ " "
                + recordatorio.getDosis().getConcentracion().getUnidadMedidaC();

        holder.presentacion.setText(presentacionString);
        holder.instrucciones.setText(recordatorio.getInstruccion().getNombreInstruccion());
        holder.indicaciones.setText(recordatorio.getInstruccion().getDescInstruccion());

    }


    @Override
    public int getItemCount() {
        return registrosDia.size(); // Solo mostrar un consejo aleatorio por tipo
    }

    static class RegistroViewHolder extends RecyclerView.ViewHolder {
        TextView hora;
        TextView nombreMedicamento;
        TextView presentacion;
        TextView instrucciones;
        TextView indicaciones;

        public RegistroViewHolder(@NonNull View itemView) {
            super(itemView);
            hora = itemView.findViewById(R.id.hora);
            nombreMedicamento = itemView.findViewById(R.id.nombreMedicamento);
            presentacion = itemView.findViewById(R.id.presentacion);
            instrucciones = itemView.findViewById(R.id.instrucciones);
            indicaciones = itemView.findViewById(R.id.indicaciones);

        }
    }
}

