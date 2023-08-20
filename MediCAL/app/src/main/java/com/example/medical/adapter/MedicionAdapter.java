package com.example.medical.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.medical.EstablecerMedicionActivity;
import com.example.medical.R;
import android.widget.TextView;

import com.example.medical.model.Medicion;

import java.util.List;

public class MedicionAdapter extends ArrayAdapter<Medicion> {

    private LayoutInflater inflater;

    public MedicionAdapter(Context context, List<Medicion> mediciones) {
        super(context, 0, mediciones);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Inflar la vista si no está disponible
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_medicion, parent, false);
        }

        // Obtener el objeto Medicion correspondiente a esta posición
        Medicion medicion = getItem(position);

        // Configurar los elementos de la vista con los datos de la medicion
        TextView nombreMedicionTextView = convertView.findViewById(R.id.nombre_medicion);
        nombreMedicionTextView.setText(medicion.getNombreMedicion());

        // Agregar clic al elemento
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Iniciar la actividad EstablecerMedicionActivity y pasar los datos necesarios
                Intent intent = new Intent(getContext(), EstablecerMedicionActivity.class);
                intent.putExtra("nombreMedicion", medicion.getNombreMedicion());
                // Agregar más datos si es necesario
                getContext().startActivity(intent);
            }
        });

        return convertView;
    }


    public void addMedicion(Medicion medicion) {
        add(medicion);
        notifyDataSetChanged();
    }
}
