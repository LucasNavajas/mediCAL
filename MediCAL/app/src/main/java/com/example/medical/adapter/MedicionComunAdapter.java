package com.example.medical.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.medical.R;
import com.example.medical.model.Medicion;

import java.util.ArrayList;
import java.util.List;

public class MedicionComunAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<Medicion> commonMedicionesData = new ArrayList<>();
    private View.OnClickListener clickListener;

    public void setCommonMedicionesData(List<Medicion> commonMedicionesData) {
        this.commonMedicionesData = commonMedicionesData;
        notifyDataSetChanged();
    }

    public void setClickListener(View.OnClickListener listener) {
        clickListener = listener;
    }

    public MedicionComunAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return commonMedicionesData.size();
    }

    @Override
    public Medicion getItem(int position) {
        return commonMedicionesData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_common_medicion, parent, false);
        }

        Medicion medicion = getItem(position);

        TextView commonMedicionTextView = convertView.findViewById(R.id.common_medicion_name);
        commonMedicionTextView.setText(medicion.getNombreMedicion());

        convertView.setTag(medicion);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clickListener != null) {
                    clickListener.onClick(view);
                }
            }
        });

        return convertView;
    }
}
