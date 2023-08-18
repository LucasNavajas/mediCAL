package com.example.medical.adapter;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.medical.ElegirAdministracionMedActivity;
import com.example.medical.ElegirMedicamentoActivity;
import com.example.medical.model.Medicamento;

import java.util.List;

public class MedicamentoAdapter extends ArrayAdapter<String> {

    private Context mContext;
    private int selectedItemPosition = -1;
    private List<Medicamento> mMedicamentosEntidad;

    public MedicamentoAdapter(Context context, List<String> medicamentos, List<Medicamento> medicamentosEntidad) {
        super(context, 0, medicamentos);
        mContext = context;
        mMedicamentosEntidad = medicamentosEntidad;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String medicamento = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        }

        TextView textView = convertView.findViewById(android.R.id.text1);
        textView.setText(medicamento);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int clickedPosition = position;
                Medicamento medicamentoSeleccionado = mMedicamentosEntidad.get(clickedPosition);

                Intent intent = new Intent(mContext, ElegirAdministracionMedActivity.class);
                intent.putExtra("codMedicamento", medicamentoSeleccionado.getCodMedicamento());
                mContext.startActivity(intent);
            }
        });

        return convertView;
    }
}
