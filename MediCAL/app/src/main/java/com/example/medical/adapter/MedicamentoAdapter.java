package com.example.medical.adapter;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.medical.ElegirAdministracionMedActivity;
import com.example.medical.ElegirMedicamentoActivity;
import com.example.medical.NuevoMedicamentoActivity;
import com.example.medical.model.Calendario;
import com.example.medical.model.Medicamento;
import com.example.medical.model.Recordatorio;
import com.example.medical.retrofit.MedicamentoApi;
import com.example.medical.retrofit.RecordatorioApi;
import com.example.medical.retrofit.RetrofitService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MedicamentoAdapter extends ArrayAdapter<String> {


    private Context mContext;
    private int selectedItemPosition = -1;
    private List<Medicamento> mMedicamentosEntidad;
    private Calendario cCalendario;

    public MedicamentoAdapter(Context context, List<String> medicamentos, List<Medicamento> medicamentosEntidad, Calendario calendario) {
        super(context, 0, medicamentos);
        mContext = context;
        mMedicamentosEntidad = medicamentosEntidad;
        cCalendario = calendario;
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
            RetrofitService retrofitService = new RetrofitService();
            RecordatorioApi recordatorioApi = retrofitService.getRetrofit().create(RecordatorioApi.class);
            @Override
            public void onClick(View view) {
                int clickedPosition = position;
                Medicamento medicamentoSeleccionado = mMedicamentosEntidad.get(clickedPosition);
                Recordatorio recordatorio = new Recordatorio();
                recordatorio.setMedicamento(medicamentoSeleccionado);
                recordatorio.setCalendario(cCalendario);
                recordatorioApi.save(recordatorio).enqueue(new Callback<Recordatorio>() {
                    @Override
                    public void onResponse(Call<Recordatorio> call, Response<Recordatorio> response) {
                        Intent intent = new Intent(mContext, ElegirAdministracionMedActivity.class);
                        intent.putExtra("codRecordatorio", response.body().getCodRecordatorio());
                        mContext.startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<Recordatorio> call, Throwable t) {
                        Toast.makeText(mContext, "Error de recordatorio creado", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        return convertView;
    }
}
