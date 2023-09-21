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

import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

public class RegistroAdapter extends RecyclerView.Adapter<RegistroAdapter.RegistroViewHolder> {

    private final Context context;
    private List<RegistroRecordatorio> registrosDia = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    public RegistroAdapter(List<RegistroRecordatorio> registrosDia, Context context) {
        this.context = context;
        this.registrosDia = registrosDia;

    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @NonNull
    @Override
    public RegistroAdapter.RegistroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.n13y14_recordatorio_item, parent, false);
        return new RegistroAdapter.RegistroViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RegistroAdapter.RegistroViewHolder holder, int position) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        RegistroRecordatorio registroRecordatorio = registrosDia.get(position);
        Recordatorio recordatorio = registroRecordatorio.getRecordatorio();
        if(recordatorio.getFrecuencia()==null && registroRecordatorio.getFechaTomaEsperada().getHour()==0 && registroRecordatorio.getFechaTomaEsperada().getMinute()==0){
            holder.hora.setText("Cuando sea necesario");
        }
        else {
            holder.hora.setText(String.valueOf(registroRecordatorio.getFechaTomaEsperada().toLocalTime().format(formatter)));
        }
        holder.nombreMedicamento.setText(recordatorio.getMedicamento().getNombreMedicamento());
        String presentacionString= "";
        presentacionString = Float.toString(recordatorio.getDosis().getCantidadDosis()) + " "
                + registroRecordatorio.getRecordatorio().getPresentacionMed().getNombrePresentacionMed()+ " "
                + Float.toString(recordatorio.getDosis().getValorConcentracion())+ " "
                + recordatorio.getDosis().getConcentracion().getUnidadMedidaC();

        holder.presentacion.setText(presentacionString);
        holder.instrucciones.setText(recordatorio.getInstruccion().getNombreInstruccion());
        if(recordatorio.getInstruccion().getDescInstruccion().equals("")){
            holder.indicaciones.setVisibility(View.GONE);
        }
        else{
            holder.indicaciones.setVisibility(View.VISIBLE);
            holder.indicaciones.setText(recordatorio.getInstruccion().getDescInstruccion());
        }
        if (registroRecordatorio.isTomaRegistroRecordatorio()==false && LocalDateTime.now().isAfter(registroRecordatorio.getFechaTomaEsperada())){
            holder.imagenRegistro.setImageResource(R.drawable.cancelar);
        }
        else if(registroRecordatorio.isTomaRegistroRecordatorio()){
            holder.imagenRegistro.setImageResource(R.drawable.tick);
        }
        else{
            holder.imagenRegistro.setImageResource(R.drawable.reloj_medicion);
        }

        if(registroRecordatorio.getFechaTomaReal()!=null){
            holder.tomado.setVisibility(View.VISIBLE);

            holder.tomado.setText("Registrado a las "+ registroRecordatorio.getFechaTomaReal().toLocalTime().format(formatter)+", "
            + holder.mesDiaFromDate(registroRecordatorio.getFechaTomaReal().toLocalDate()));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(registroRecordatorio);
                }
            }
        });

    }

    public interface OnItemClickListener {
        void onItemClick(RegistroRecordatorio registro);
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
        ImageView imagenRegistro;
        TextView tomado;

        public RegistroViewHolder(@NonNull View itemView) {
            super(itemView);
            hora = itemView.findViewById(R.id.hora);
            nombreMedicamento = itemView.findViewById(R.id.nombreMedicamento);
            presentacion = itemView.findViewById(R.id.presentacion);
            instrucciones = itemView.findViewById(R.id.instrucciones);
            indicaciones = itemView.findViewById(R.id.indicaciones);
            imagenRegistro = itemView.findViewById(R.id.imagen_registro);
            tomado = itemView.findViewById(R.id.tomado);

        }
        private String mesDiaFromDate(LocalDate date) {
            LocalDate today = LocalDate.now();
            LocalDate tomorrow = today.plusDays(1);
            LocalDate yesterday = today.minusDays(1);

            String dayString;
            if (date.isEqual(today)) {
                dayString = "Hoy, ";
            } else if (date.isEqual(yesterday)) {
                dayString = "Ayer, ";
            } else if (date.isEqual(tomorrow)) {
                dayString = "Mañana, ";
            } else {
                dayString = "";
            }

            String month = new DateFormatSymbols(new Locale("es")).getMonths()[date.getMonthValue() - 1];
            String formattedDate = dayString +  + date.getDayOfMonth() + " de " + month + ", " + date.getYear();
            return formattedDate;
        }
    }
}

