package com.example.medical.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.medical.EstablecerMedicionActivity;
import com.example.medical.R;
import com.example.medical.model.Calendario;
import com.example.medical.model.Medicion;
import com.example.medical.retrofit.CalendarioApi;

import java.util.List;

public class TodasMedicionesAdapter extends ArrayAdapter<Medicion> {

    private LayoutInflater inflater;
    private Calendario calendarioSeleccionado;
    private CalendarioApi calendarioApi;
    private int codCalendario;

    public TodasMedicionesAdapter(Context context, List<Medicion> mediciones, int codCalendario) {
        super(context, 0, mediciones);
        inflater = LayoutInflater.from(context);
        this.codCalendario = codCalendario;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_medicion, parent, false);
        }

        Medicion medicion = getItem(position);

        TextView nombreMedicionTextView = convertView.findViewById(R.id.nombre_medicion);
        nombreMedicionTextView.setText(medicion.getNombreMedicion());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), EstablecerMedicionActivity.class);
                intent.putExtra("nombreMedicion", medicion.getNombreMedicion());
                intent.putExtra("unidadMedida", medicion.getUnidadMedidaMedicion());
                intent.putExtra("codMedicion",medicion.getCodMedicion());
                intent.putExtra("calendarioSeleccionadoid", codCalendario);
                getContext().startActivity(intent);
            }
        });

        return convertView;
    }
}
