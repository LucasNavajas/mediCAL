package com.example.medical;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.medical.model.Concentracion;
import com.example.medical.model.Dosis;
import com.example.medical.model.Frecuencia;
import com.example.medical.model.Instruccion;
import com.example.medical.model.Inventario;
import com.example.medical.model.Medicamento;
import com.example.medical.model.Recordatorio;
import com.example.medical.retrofit.RecordatorioApi;
import com.example.medical.retrofit.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditarDosisFuturasActivity extends AppCompatActivity {
    private RetrofitService retrofitService = new RetrofitService();
    private RecordatorioApi recordatorioApi = retrofitService.getRetrofit().create(RecordatorioApi.class);
    private Recordatorio recordatorio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.n63_0_editar_dosis_futuras);
        inicializarBotones();
        obtenerDatos(7); // Cambia el número 4 por el codRecordatorio deseado
    }

    private void inicializarBotones() {
        ImageButton desplegableFrecuencia = findViewById(R.id.desplegable_frecuencia);
        TextView cambiarFrecuencia = findViewById(R.id.cambiar_frecuencia);
        ImageButton desplegableDuracion = findViewById(R.id.desplegable_duracion);
        RelativeLayout cambiarDuracion = findViewById(R.id.cambiar_duracion);
        TextView duracionActual = findViewById(R.id.duracion_actual);
        ImageButton desplegableImagen = findViewById(R.id.desplegable_imagen);
        RelativeLayout cambiarImagen = findViewById(R.id.relative_cambiar_imagen);
        ImageButton desplegableConcentracion = findViewById(R.id.desplegable_concentracion);
        LinearLayout cambiarConcentracion = findViewById(R.id.cambiar_concentracion);
        ImageButton desplegableInstrucciones = findViewById(R.id.desplegable_instrucciones);
        LinearLayout cambiarInstrucciones = findViewById(R.id.cambiar_instrucciones);
        ImageButton desplegableRecarga = findViewById(R.id.desplegable_recordatorio_receta);
        LinearLayout cambiarRecarga = findViewById(R.id.cambiar_recordatorio_receta);
        Switch recordatorioRecarga = findViewById(R.id.activar_recordatorio_receta);
        LinearLayout agregarInventario = findViewById(R.id.definir_inventario); // Cambiado el nombre de la variable

        desplegableFrecuencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cambiarFrecuencia.getVisibility() == View.GONE) {
                    desplegableFrecuencia.setImageResource(R.drawable.boton_desplegable_arriba);
                    cambiarFrecuencia.setVisibility(View.VISIBLE);
                } else {
                    cambiarFrecuencia.setVisibility(View.GONE);
                    desplegableFrecuencia.setImageResource(R.drawable.boton_desplegable);
                }
            }
        });

        desplegableDuracion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cambiarDuracion.getVisibility() == View.GONE) {
                    desplegableDuracion.setImageResource(R.drawable.boton_desplegable_arriba);
                    cambiarDuracion.setVisibility(View.VISIBLE);
                    setDuracionActualText("Fecha de inicio:");
                    duracionActual.setTextColor(Color.BLACK);
                } else {
                    cambiarDuracion.setVisibility(View.GONE);
                    desplegableDuracion.setImageResource(R.drawable.boton_desplegable);
                    mostrarDuracion(recordatorio.getDuracionRecordatorio()); // Llama a mostrarDuracion con el valor actual
                    duracionActual.setTextColor(ContextCompat.getColor(EditarDosisFuturasActivity.this, R.color.gris_medical));
                }
            }
        });

        desplegableImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cambiarImagen.getVisibility() == View.GONE) {
                    desplegableImagen.setImageResource(R.drawable.boton_desplegable_arriba);
                    cambiarImagen.setVisibility(View.VISIBLE);
                } else {
                    cambiarImagen.setVisibility(View.GONE);
                    desplegableImagen.setImageResource(R.drawable.boton_desplegable);
                }
            }
        });

        desplegableConcentracion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cambiarConcentracion.getVisibility() == View.GONE) {
                    desplegableConcentracion.setImageResource(R.drawable.boton_desplegable_arriba);
                    cambiarConcentracion.setVisibility(View.VISIBLE);
                } else {
                    cambiarConcentracion.setVisibility(View.GONE);
                    desplegableConcentracion.setImageResource(R.drawable.boton_desplegable);
                }
            }
        });

        desplegableInstrucciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cambiarInstrucciones.getVisibility() == View.GONE) {
                    desplegableInstrucciones.setImageResource(R.drawable.boton_desplegable_arriba);
                    cambiarInstrucciones.setVisibility(View.VISIBLE);
                } else {
                    cambiarInstrucciones.setVisibility(View.GONE);
                    desplegableInstrucciones.setImageResource(R.drawable.boton_desplegable);
                }
            }
        });

        desplegableRecarga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cambiarRecarga.getVisibility() == View.GONE) {
                    desplegableRecarga.setImageResource(R.drawable.boton_desplegable_arriba);
                    cambiarRecarga.setVisibility(View.VISIBLE);
                } else {
                    cambiarRecarga.setVisibility(View.GONE);
                    agregarInventario.setVisibility(View.GONE);
                    desplegableRecarga.setImageResource(R.drawable.boton_desplegable);

                }
            }
        });

        recordatorioRecarga.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    agregarInventario.setVisibility(View.VISIBLE);

                } else {
                    agregarInventario.setVisibility(View.GONE);

                }
            }
        });
    }

    private void obtenerDatos(int codRecordatorio) {
        recordatorioApi.getByCodRecordatorio(codRecordatorio).enqueue(new Callback<Recordatorio>() {
            @Override
            public void onResponse(Call<Recordatorio> call, Response<Recordatorio> response) {
                recordatorio = response.body();
                Medicamento medicamento = recordatorio.getMedicamento();
                Frecuencia frecuencia = recordatorio.getFrecuencia();
                Dosis dosis = recordatorio.getDosis();
                Concentracion concentracion = dosis.getConcentracion();
                Instruccion instruccion = recordatorio.getInstruccion();
                Inventario inventario = recordatorio.getInventario();

                String nombreMedicamento = medicamento.getNombreMedicamento();
                String nombreFrecuencia = frecuencia.getNombreFrecuencia();
                int duracionRecordatorio = recordatorio.getDuracionRecordatorio();
                String imagen = recordatorio.getImagen();
                float cantdosis = dosis.getCantidadDosis();
                float valorconcentracion = dosis.getValorConcentracion();
                String unidadmedidac = concentracion.getUnidadMedidaC();
                String radioinstruccion = instruccion.getNombreInstruccion();
                String descindicacion = instruccion.getDescInstruccion();
                int codinventario = inventario.getCodInventario();
                int cantreal = inventario.getCantRealInventario();
                int cantaviso = inventario.getCantAvisoInventario();        

                mostrarNombreMedicamento(nombreMedicamento);
                mostrarNombreFrecuencia(nombreFrecuencia);
                mostrarDuracion(duracionRecordatorio);
                mostrarImagen (imagen);
                mostrarDosis (cantdosis);
                mostrarConcentracion (valorconcentracion,unidadmedidac);
                mostrarInstruccion(radioinstruccion);
                mostrarIndicacion(descindicacion);
                mostrarInventario(codinventario,cantreal,cantaviso);
            }

            @Override
            public void onFailure(Call<Recordatorio> call, Throwable t) {
                Toast.makeText(EditarDosisFuturasActivity.this, "Error al cargar el recordatorio", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void mostrarInventario(int codinventario, int cantreal, int cantaviso) {
        Switch recordatorioRecarga = findViewById(R.id.activar_recordatorio_receta);
        LinearLayout definirInventario = findViewById(R.id.definir_inventario);
        TextView descripcionRecordatorio = findViewById(R.id.descripcion_recordatorio_receta);
        EditText editTextCantreal = findViewById(R.id.cantreal);
        TextView textViewCantaviso = findViewById(R.id.establecer_alerta_inventario);

        if (codinventario != 0) { // Si hay un código de inventario asociado
            descripcionRecordatorio.setText("Actualmente tiene "+ cantreal +" medicamentos. Se le recordará cuando le queden " + cantaviso + " medicamentos"); // Cambia el texto de descripción
            editTextCantreal.setHint("Cantidad de medicamentos: " + cantreal); // Establece el valor de cantreal
            textViewCantaviso.setText("Establecer cuando me queden " +cantaviso +" medicamentos"); // Establece el valor de cantaviso

        } else {
            descripcionRecordatorio.setText("Introduzca la cantidad de medicamento que tiene ahora para obtener un recordatorio de recarga"); // Cambia el texto de descripción
            editTextCantreal.setHint("Cantidad de medicamentos:"); // Establece el valor de cantreal
            textViewCantaviso.setText("Establecer cuando me queden X medicamentos");
        }
    }



    private void mostrarIndicacion(String descindicacion) {
        EditText indicacionEditText = findViewById(R.id.indicaciones); // Cambia a tu ID real
        indicacionEditText.setHint(String.valueOf(descindicacion));
    }

    private void mostrarInstruccion(String radioinstruccion) {
        RadioButton radioAntesDeComer = findViewById(R.id.radio_antes_de_comer);
        RadioButton radioConLaComida = findViewById(R.id.radio_con_la_comida);
        RadioButton radioDespuesDeComer = findViewById(R.id.radio_despues_de_comer);
        RadioButton radioNoImporta = findViewById(R.id.no_importa);

        if (radioinstruccion.equals("Antes de comer")) {
            radioAntesDeComer.setChecked(true);
        } else if (radioinstruccion.equals("Con la comida")) {
            radioConLaComida.setChecked(true);
        } else if (radioinstruccion.equals("Después de comer")) {
            radioDespuesDeComer.setChecked(true);
        } else {
            radioNoImporta.setChecked(true);
        }
    }

    private void mostrarConcentracion(float valorconcentracion, String unidadmedidac) {
        TextView concentracionTextView = findViewById(R.id.concentracion); // Cambia el ID según tu diseño
        concentracionTextView.setText("Concentración de la medicación: "+ valorconcentracion+ " "+ unidadmedidac);
    }

    private void mostrarDosis(float cantdosis) {
        EditText dosisEditText = findViewById(R.id.editdosis); // Cambia a tu ID real
        dosisEditText.setHint(String.valueOf(cantdosis));
    }

    private void mostrarImagen(String imagen) {
        if (imagen != null) {
            int resourceId = getResources().getIdentifier(imagen, "drawable", getPackageName());
            if (resourceId != 0) {
                ImageView imagenActualImageView = findViewById(R.id.imagen_actual);
                imagenActualImageView.setImageResource(resourceId);
            } else {
                // Manejar el caso en el que no se encuentra el recurso
            }
        } else {
            // Manejar el caso en el que imagen es nulo
        }
    }



    private void mostrarNombreFrecuencia(String nombreFrecuencia) {
        TextView frecuenciaActualTextView = findViewById(R.id.frecuencia_actual); // Cambia el ID según tu diseño
        frecuenciaActualTextView.setText(nombreFrecuencia);
    }

    private void mostrarNombreMedicamento(String nombreMedicamento) {
        TextView nombreMedicamentoTextView = findViewById(R.id.nombremed); // Cambia el ID según tu diseño
        nombreMedicamentoTextView.setText(nombreMedicamento);
    }

    private void setDuracionActualText(String text) {
        TextView duracionActualTextView = findViewById(R.id.duracion_actual); // Cambia el ID según tu diseño
        duracionActualTextView.setText(text);
    }

    private void mostrarDuracion(Integer duracionRecordatorio) {
        if (duracionRecordatorio == 99999) {
            setDuracionActualText("Tratamiento continuo");
        } else {
            switch (duracionRecordatorio) {
                case 1:
                    setDuracionActualText("1 día");
                    break;
                case 5:
                    setDuracionActualText("5 días");
                    break;
                case 7:
                    setDuracionActualText("1 semana");
                    break;
                case 10:
                    setDuracionActualText("10 días");
                    break;
                case 30:
                    setDuracionActualText("30 días");
                    break;
                case 180:
                    setDuracionActualText("6 meses");
                    break;
                case 360:
                    setDuracionActualText("12 meses");
                    break;
                case 720:
                    setDuracionActualText("24 meses");
                    break;
                default:
                    setDuracionActualText(duracionRecordatorio + " días");
                    break;
            }
        }
    }


}
