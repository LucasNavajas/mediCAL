package com.example.medical;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditarDosisFuturasActivity extends AppCompatActivity {
    private RetrofitService retrofitService = new RetrofitService();
    private RecordatorioApi recordatorioApi = retrofitService.getRetrofit().create(RecordatorioApi.class);
    private Recordatorio recordatorio;
    private PopupWindow popupWindow;
    private String nombrefrecuencia;
    private int keyboardHeight = 0;

    @Override
    public void onBackPressed() {
        popupSalir();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.n63_0_editar_dosis_futuras);
        inicializarBotones();
        obtenerDatos(4); // Cambia el número 4 por el codRecordatorio deseado

        ImageView btnCerrar = findViewById(R.id.boton_cerrar);
        if (btnCerrar != null) {
            btnCerrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupSalir();
                }
            });
        }

        TextView cambiarFrecuencia = findViewById(R.id.cambiar_frecuencia);
        if (cambiarFrecuencia != null) {
            cambiarFrecuencia.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupCambiarFrecuencia();
                }
            });
        }

        TextView cambiarHora = findViewById(R.id.cambiar_hora);
        if (cambiarHora != null) {
            cambiarHora.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupCambiarHora();
                }
            });
        }
    }

    private void inicializarBotones() {
        ImageButton desplegableFrecuencia = findViewById(R.id.desplegable_frecuencia);
        TextView cambiarFrecuencia = findViewById(R.id.cambiar_frecuencia);
        ImageButton desplegableHora = findViewById(R.id.desplegable_hora);
        TextView cambiarHora = findViewById(R.id.cambiar_hora);
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
        LinearLayout agregarInventario = findViewById(R.id.definir_inventario);
        TextView aclaracionDuracion = findViewById(R.id.aclaracion_duracion);
        View dividerDuracion = findViewById(R.id.divider_duracion);
        TextView dias = findViewById(R.id.dias);
        RadioButton rbdias = findViewById(R.id.radio_x_dias);
        RadioButton rbtcontinuo = findViewById(R.id.radio_tratamiento_continuo);
        TextView cambiardosis = findViewById(R.id.botoncambiardosis);
        EditText dosisEditText = findViewById(R.id.editdosis);
        TextView cambiarconcentracion = findViewById(R.id.establecer_concentracion);

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
                if (recordatorio != null) {
                    if (cambiarDuracion.getVisibility() == View.GONE) {
                        desplegableDuracion.setImageResource(R.drawable.boton_desplegable_arriba);
                        cambiarDuracion.setVisibility(View.VISIBLE);
                        dias.setVisibility(View.VISIBLE);
                        dias.setTextColor(Color.BLACK);
                        duracionActual.setClickable(true);
                        duracionActual.setTextColor(ContextCompat.getColor(EditarDosisFuturasActivity.this, R.color.verdeTextos));
                        aclaracionDuracion.setVisibility(View.VISIBLE);
                        dividerDuracion.setVisibility(View.VISIBLE);

                        // Configura el OnClickListener para duracionActual cuando el desplegable esté visible
                        duracionActual.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                popupCambiarFecha();
                            }
                        });
                        rbdias.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                popupCambiarDiasTratamiento();
                            }
                        });
                        rbtcontinuo.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // Obtener el valor actual del EditText
                                int diasduracion = 99999;

                                // Llamar al método mostrarDuracion y pasar la fecha y los días de duración
                                mostrarDuracionDias(diasduracion);
                            }
                        });
                    } else {
                        cambiarDuracion.setVisibility(View.GONE);
                        aclaracionDuracion.setVisibility(View.GONE);
                        dividerDuracion.setVisibility(View.GONE);
                        desplegableDuracion.setImageResource(R.drawable.boton_desplegable);
                        duracionActual.setClickable(false);
                        duracionActual.setTextColor(ContextCompat.getColor(EditarDosisFuturasActivity.this, R.color.gris_medical));
                        dias.setTextColor(ContextCompat.getColor(EditarDosisFuturasActivity.this, R.color.gris_medical));
                    }
                } else {
                    // Handle the case where recordatorio is null
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

        desplegableHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cambiarHora.getVisibility() == View.GONE) {
                    desplegableHora.setImageResource(R.drawable.boton_desplegable_arriba);
                    cambiarHora.setVisibility(View.VISIBLE);
                } else {
                    cambiarHora.setVisibility(View.GONE);
                    desplegableHora.setImageResource(R.drawable.boton_desplegable);
                }
            }
        });

        desplegableConcentracion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cambiarConcentracion.getVisibility() == View.GONE) {
                    desplegableConcentracion.setImageResource(R.drawable.boton_desplegable_arriba);
                    cambiarConcentracion.setVisibility(View.VISIBLE);
                    cambiardosis.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // Obtener el valor ingresado en el EditText como texto
                            String dosisTexto = dosisEditText.getText().toString();

                            // Verificar si se ingresó un valor válido
                            if (!TextUtils.isEmpty(dosisTexto)) {
                                // Convertir el texto en un valor flotante (float)
                                float cantdosis = Float.parseFloat(dosisTexto);

                                // Mostrar el valor como hint en gris y borrar el contenido del EditText
                                dosisEditText.setHintTextColor(getResources().getColor(R.color.gris_medical));
                                dosisEditText.setHint(String.valueOf(cantdosis));
                                dosisEditText.setText(""); // Borra el contenido

                                // Cerrar el teclado
                                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(dosisEditText.getWindowToken(), 0);
                                // Quitar el foco del EditText
                                dosisEditText.clearFocus();
                            }
                        }
                    });
                    cambiarconcentracion.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            popupCambiarConcentracion();
                        }
                    });

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
                int diastoma = frecuencia.getDiasTomaF();
                int diasdescanso = frecuencia.getDiasDescansoF();
                String imagen = recordatorio.getImagen();
                float cantdosis = dosis.getCantidadDosis();
                float valorconcentracion = dosis.getValorConcentracion();
                String unidadmedidac = concentracion.getUnidadMedidaC();
                String radioinstruccion = instruccion.getNombreInstruccion();
                String descindicacion = instruccion.getDescInstruccion();
                int codinventario = inventario.getCodInventario();
                int cantreal = inventario.getCantRealInventario();
                int cantaviso = inventario.getCantAvisoInventario();
                LocalDateTime fechainiciorecordatorio = recordatorio.getFechaInicioRecordatorio();
                int diasduracion = recordatorio.getDuracionRecordatorio();

                mostrarNombreMedicamento(nombreMedicamento);
                mostrarNombreFrecuencia(nombreFrecuencia,diastoma,diasdescanso);
                mostrarHorario(fechainiciorecordatorio);
                mostrarDuracionFecha(fechainiciorecordatorio);
                mostrarDuracionDias(diasduracion);
                mostrarImagen(imagen);
                mostrarDosis(cantdosis);
                mostrarConcentracion(valorconcentracion, unidadmedidac);
                mostrarInstruccion(radioinstruccion);
                mostrarIndicacion(descindicacion);
                mostrarInventario(codinventario, cantreal, cantaviso);
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
            descripcionRecordatorio.setText("Actualmente tiene " + cantreal + " medicamentos. Se le recordará cuando le queden " + cantaviso + " medicamentos"); // Cambia el texto de descripción
            editTextCantreal.setHint("Cantidad de medicamentos: " + cantreal); // Establece el valor de cantreal
           textViewCantaviso.setText("Establecer cuando me queden " + cantaviso + " medicamentos"); // Establece el valor de cantaviso

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
        concentracionTextView.setText("Concentración de la medicación: " + valorconcentracion + " " + unidadmedidac);
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

    private void mostrarNombreFrecuencia(String nombreFrecuencia, int diastoma, int diasdescanso) {
        TextView frecuenciaActualTextView = findViewById(R.id.frecuencia_actual); // Cambia el ID según tu diseño

        if (nombreFrecuencia.equals("Ciclo recurrente")) {
            frecuenciaActualTextView.setText(nombreFrecuencia + " (Toma " + diastoma + " días / Descanso " + diasdescanso + " días)");
        } else {
            frecuenciaActualTextView.setText(nombreFrecuencia);
        }
    }

    private void mostrarNombreMedicamento(String nombreMedicamento) {
        TextView nombreMedicamentoTextView = findViewById(R.id.nombremed); // Cambia el ID según tu diseño
        nombreMedicamentoTextView.setText(nombreMedicamento);
    }

    private void mostrarDuracionFecha(LocalDateTime fechainiciorecordatorio) {
        TextView duracionActualTextView = findViewById(R.id.duracion_actual); // Cambia el ID según tu diseño

        // Formatear la fecha en el formato deseado
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
        String fechaFormateada = fechainiciorecordatorio.format(formatter);

        // Establecer la fecha formateada en el TextView
        duracionActualTextView.setText("Fecha de inicio: " + fechaFormateada);

    }

    private void mostrarDuracionDias(int diasduracion) {

        TextView diasduracionTextView = findViewById(R.id.dias);

        if (diasduracion == 99999) {
            diasduracionTextView.setText("Tratamiento continuo");
        } else {
            diasduracionTextView.setText("Días de tratamiento: " + diasduracion);
        }
    }

    private void mostrarHorario(LocalDateTime fechainiciorecordatorio) {
        TextView primeratomaTextView = findViewById(R.id.primeratoma); // Cambia el ID según tu diseño

        // Formatear la fecha en el formato deseado
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
        String fechaFormateada = fechainiciorecordatorio.format(formatter);

        // Establecer la fecha formateada en el TextView
        primeratomaTextView.setText("Primera administración a las:\n" + fechaFormateada);

    }

    private void popupSalir() {
        View popupView = getLayoutInflater().inflate(R.layout.n63_1_popup_salir_sin_guardar, null);


        // Crear la instancia de PopupWindow
        popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        // Hacer que el popup sea enfocable (opcional)
        popupWindow.setFocusable(true);

        // Configurar animación para oscurecer el fondo
        View rootView = findViewById(android.R.id.content);

        View dimView = findViewById(R.id.dim_view);
        dimView.setVisibility(View.VISIBLE);

        Animation scaleAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.popup);
        popupView.startAnimation(scaleAnimation);

        // Mostrar el popup en la ubicación deseada
        popupWindow.showAtLocation(rootView, Gravity.CENTER, 0, 0);
        // Agregar un OnDismissListener para ocultar el dimView cuando se cierre el popup
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                dimView.setVisibility(View.GONE);
            }
        });
        TextView textViewCancelar = popupView.findViewById(R.id.cancelar);

        textViewCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ocultar el PopupWindow
                popupWindow.dismiss();

                // Ocultar el fondo oscurecido
                dimView.setVisibility(View.GONE);

            }
        });

    }

    private void popupCambiarFrecuencia() {
        View popupView = getLayoutInflater().inflate(R.layout.n63_2_popup_cambiarfrecuencia, null);

        TextView botonAceptar = popupView.findViewById(R.id.aceptar);
        TextView botonCancelar = popupView.findViewById(R.id.cancelar);

        PopupWindow popupWindow = new PopupWindow(popupView, 1000, ViewGroup.LayoutParams.WRAP_CONTENT);

        popupWindow.setFocusable(true);

        View rootView = findViewById(android.R.id.content);
        View dimView = findViewById(R.id.dim_view);
        dimView.setVisibility(View.VISIBLE);

        // Agregar un OnDismissListener para ocultar el dimView cuando se cierre el popup
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                dimView.setVisibility(View.GONE);
            }
        });

        Animation scaleAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.popup);
        popupView.startAnimation(scaleAnimation);

        popupWindow.showAtLocation(rootView, Gravity.CENTER, 0, 0);

        // Configura OnClickListener para el botón Aceptar
        botonAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombreFrecuencia = obtenerNombreFrecuenciaSeleccionada();
                switch (nombreFrecuencia) {
                    case "Cada X horas":
                        popupXhoras();
                        break;
                    case "Cada X días":
                        popupXdias();
                        break;
                    case "Cada X semanas":
                        popupXsemanas();
                        break;
                    case "Cada X meses":
                        popupXmeses();
                        break;
                    case "Ciclo recurrente":
                        popupCiclos();

                        break;
                    case "Según sea necesario":
                        nombrefrecuencia = "Según sea necesario";
                        mostrarNombreFrecuencia(nombrefrecuencia, 0, 0);

                        break;
                }
                popupWindow.dismiss();
                dimView.setVisibility(View.VISIBLE);

            }
        });

        // Establece OnClickListener para el botón Cancelar
        botonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                dimView.setVisibility(View.GONE);
            }
        });

        // Configura OnClickListener para las opciones de frecuencia
        TextView opcionXHoras = popupView.findViewById(R.id.text_x_horas);
        TextView opcionXDias = popupView.findViewById(R.id.text_x_dias);
        TextView opcionXSemanas = popupView.findViewById(R.id.text_x_semanas);
        TextView opcionXMeses = popupView.findViewById(R.id.text_x_meses);
        TextView opcionCicloRecurrente = popupView.findViewById(R.id.text_ciclo_recurrente);
        TextView opcionSegunSeaNecesario = popupView.findViewById(R.id.text_segun_sea_necesario);

        opcionXHoras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seleccionarOpcion(opcionXHoras);
            }
        });

        opcionXDias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seleccionarOpcion(opcionXDias);
            }
        });

        opcionXSemanas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seleccionarOpcion(opcionXSemanas);
            }
        });

        opcionXMeses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seleccionarOpcion(opcionXMeses);
            }
        });

        opcionCicloRecurrente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seleccionarOpcion(opcionCicloRecurrente);
            }
        });

        opcionSegunSeaNecesario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seleccionarOpcion(opcionSegunSeaNecesario);
            }
        });
    }

    private TextView opcionSeleccionada = null;

    private void seleccionarOpcion(TextView opcion) {
        if (opcionSeleccionada != null) {
            opcionSeleccionada.setBackgroundResource(android.R.color.transparent);
        }

        opcion.setBackgroundResource(R.color.verdeSelector);
        opcionSeleccionada = opcion;
    }

    private String obtenerNombreFrecuenciaSeleccionada() {
        if (opcionSeleccionada != null) {
            return opcionSeleccionada.getText().toString();
        } else {
            return null; // Devuelve null si no se ha seleccionado ninguna opción
        }
    }

    private void popupXhoras() {
        View popupView = getLayoutInflater().inflate(R.layout.n64_1_popup_duracionhoras, null);

        TextView botonAceptar = popupView.findViewById(R.id.aceptar);
        TextView botonCancelar = popupView.findViewById(R.id.cancelar);
        NumberPicker numberPicker = popupView.findViewById(R.id.number_picker); // Asume que tu NumberPicker tiene el ID "numberPicker" en tu diseño XML

        numberPicker.setMinValue(1); // Establece el valor mínimo
        numberPicker.setMaxValue(24);

        PopupWindow popupWindow = new PopupWindow(popupView, 1000, ViewGroup.LayoutParams.WRAP_CONTENT);

        popupWindow.setFocusable(true);

        View rootView = findViewById(android.R.id.content);
        View dimView = findViewById(R.id.dim_view);
        dimView.setVisibility(View.VISIBLE);

        // Agregar un OnDismissListener para ocultar el dimView cuando se cierre el popup
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                dimView.setVisibility(View.GONE);
            }
        });

        Animation scaleAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.popup);
        popupView.startAnimation(scaleAnimation);

        popupWindow.showAtLocation(rootView, Gravity.CENTER, 0, 0);

        // Configura OnClickListener para el botón Aceptar
        botonAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedValue = numberPicker.getValue(); // Obtén el valor seleccionado en el NumberPicker
                nombrefrecuencia = "Cada " + selectedValue + " horas"; // Establece nombrefrecuencia con el valor seleccionado
                // Haz algo con el valor seleccionado, si es necesario
                mostrarNombreFrecuencia(nombrefrecuencia,0,0);
                popupWindow.dismiss();
                dimView.setVisibility(View.GONE);
            }
        });

        // Establece OnClickListener para el botón Cancelar
        botonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                popupCambiarFrecuencia();
            }
        });
    }

    private void popupXdias() {
        View popupView = getLayoutInflater().inflate(R.layout.n64_2_popup_duraciondias, null);

        TextView botonAceptar = popupView.findViewById(R.id.aceptar);
        TextView botonCancelar = popupView.findViewById(R.id.cancelar);
        NumberPicker numberPicker = popupView.findViewById(R.id.number_picker); // Asume que tu NumberPicker tiene el ID "numberPicker" en tu diseño XML

        numberPicker.setMinValue(1); // Valor mínimo
        numberPicker.setMaxValue(365); // Valor máximo

        PopupWindow popupWindow = new PopupWindow(popupView, 1000, ViewGroup.LayoutParams.WRAP_CONTENT);

        popupWindow.setFocusable(true);

        View rootView = findViewById(android.R.id.content);
        View dimView = findViewById(R.id.dim_view);
        dimView.setVisibility(View.VISIBLE);

        // Agregar un OnDismissListener para ocultar el dimView cuando se cierre el popup
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                dimView.setVisibility(View.GONE);
            }
        });

        Animation scaleAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.popup);
        popupView.startAnimation(scaleAnimation);

        popupWindow.showAtLocation(rootView, Gravity.CENTER, 0, 0);

        // Configura OnClickListener para el botón Aceptar
        botonAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedValue = numberPicker.getValue(); // Obtén el valor seleccionado en el NumberPicker
                nombrefrecuencia = "Cada " + selectedValue + " días"; // Establece nombrefrecuencia con el valor seleccionado
                // Haz algo con el valor seleccionado, si es necesario
                mostrarNombreFrecuencia(nombrefrecuencia,0,0);
                popupWindow.dismiss();
                dimView.setVisibility(View.GONE);
            }
        });

        // Establece OnClickListener para el botón Cancelar
        botonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                popupCambiarFrecuencia();
            }
        });
    }

    private void popupXsemanas() {
        View popupView = getLayoutInflater().inflate(R.layout.n64_3_popup_duracionsemanas, null);

        TextView botonAceptar = popupView.findViewById(R.id.aceptar);
        TextView botonCancelar = popupView.findViewById(R.id.cancelar);
        NumberPicker numberPicker = popupView.findViewById(R.id.number_picker); // Asume que tu NumberPicker tiene el ID "numberPicker" en tu diseño XML

        numberPicker.setMinValue(1); // Valor mínimo
        numberPicker.setMaxValue(52); // Valor máximo

        PopupWindow popupWindow = new PopupWindow(popupView, 1000, ViewGroup.LayoutParams.WRAP_CONTENT);

        popupWindow.setFocusable(true);

        View rootView = findViewById(android.R.id.content);
        View dimView = findViewById(R.id.dim_view);
        dimView.setVisibility(View.VISIBLE);

        // Agregar un OnDismissListener para ocultar el dimView cuando se cierre el popup
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                dimView.setVisibility(View.GONE);
            }
        });

        Animation scaleAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.popup);
        popupView.startAnimation(scaleAnimation);

        popupWindow.showAtLocation(rootView, Gravity.CENTER, 0, 0);

        // Configura OnClickListener para el botón Aceptar
        botonAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedValue = numberPicker.getValue(); // Obtén el valor seleccionado en el NumberPicker
                nombrefrecuencia = "Cada " + selectedValue + " semanas"; // Establece nombrefrecuencia con el valor seleccionado
                // Haz algo con el valor seleccionado, si es necesario
                mostrarNombreFrecuencia(nombrefrecuencia,0,0);
                popupWindow.dismiss();
                dimView.setVisibility(View.GONE);
            }
        });

        // Establece OnClickListener para el botón Cancelar
        botonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                popupCambiarFrecuencia();
            }
        });
    }

    private void popupXmeses() {
        View popupView = getLayoutInflater().inflate(R.layout.n64_4_popup_duracionmeses, null);

        TextView botonAceptar = popupView.findViewById(R.id.aceptar);
        TextView botonCancelar = popupView.findViewById(R.id.cancelar);
        NumberPicker numberPicker = popupView.findViewById(R.id.number_picker); // Asume que tu NumberPicker tiene el ID "numberPicker" en tu diseño XML

        numberPicker.setMinValue(1); // Valor mínimo
        numberPicker.setMaxValue(12); // Valor máximo

        PopupWindow popupWindow = new PopupWindow(popupView, 1000, ViewGroup.LayoutParams.WRAP_CONTENT);

        popupWindow.setFocusable(true);

        View rootView = findViewById(android.R.id.content);
        View dimView = findViewById(R.id.dim_view);
        dimView.setVisibility(View.VISIBLE);

        // Agregar un OnDismissListener para ocultar el dimView cuando se cierre el popup
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                dimView.setVisibility(View.GONE);
            }
        });

        Animation scaleAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.popup);
        popupView.startAnimation(scaleAnimation);

        popupWindow.showAtLocation(rootView, Gravity.CENTER, 0, 0);

        // Configura OnClickListener para el botón Aceptar
        botonAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedValue = numberPicker.getValue(); // Obtén el valor seleccionado en el NumberPicker
                nombrefrecuencia = "Cada " + selectedValue + " meses"; // Establece nombrefrecuencia con el valor seleccionado
                // Haz algo con el valor seleccionado, si es necesario
                mostrarNombreFrecuencia(nombrefrecuencia,0,0);
                popupWindow.dismiss();
                dimView.setVisibility(View.GONE);
            }
        });

        // Establece OnClickListener para el botón Cancelar
        botonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                popupCambiarFrecuencia();
            }
        });
    }

    private void popupCiclos() {
        View popupView = getLayoutInflater().inflate(R.layout.n64_5_popup_duracionciclos, null);

        TextView botonAceptar = popupView.findViewById(R.id.aceptar);
        TextView botonCancelar = popupView.findViewById(R.id.cancelar);
        NumberPicker numberPickert = popupView.findViewById(R.id.number_pickert);

        // Configura los valores mínimo y máximo
        numberPickert.setMinValue(0); // Valor mínimo
        numberPickert.setMaxValue(90); // Valor máximo

        NumberPicker numberPickerd = popupView.findViewById(R.id.number_pickerd);

        numberPickerd.setMinValue(1); // Valor mínimo
        numberPickerd.setMaxValue(90); // Valor máximo

        PopupWindow popupWindow = new PopupWindow(popupView, 1000, ViewGroup.LayoutParams.WRAP_CONTENT);

        popupWindow.setFocusable(true);

        View rootView = findViewById(android.R.id.content);
        View dimView = findViewById(R.id.dim_view);
        dimView.setVisibility(View.VISIBLE);

        // Agregar un OnDismissListener para ocultar el dimView cuando se cierre el popup
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                dimView.setVisibility(View.GONE);
            }
        });

        Animation scaleAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.popup);
        popupView.startAnimation(scaleAnimation);

        popupWindow.showAtLocation(rootView, Gravity.CENTER, 0, 0);

        // Configura OnClickListener para el botón Aceptar
        botonAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedValuet = numberPickert.getValue(); // Obtén el valor seleccionado en el NumberPicker
                int selectedValued = numberPickerd.getValue();
                nombrefrecuencia = "Ciclo recurrente"; // Establece nombrefrecuencia con el valor seleccionado
                // Haz algo con el valor seleccionado, si es necesario
                mostrarNombreFrecuencia(nombrefrecuencia,selectedValuet,selectedValued);
                popupWindow.dismiss();
                dimView.setVisibility(View.GONE);
            }
        });

        // Establece OnClickListener para el botón Cancelar
        botonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                popupCambiarFrecuencia();
            }
        });
    }

    private void popupCambiarHora() {
        View popupView = getLayoutInflater().inflate(R.layout.n63_3_popup_cambiarhorario, null);

        TextView botonAceptar = popupView.findViewById(R.id.aceptar);
        TextView botonCancelar = popupView.findViewById(R.id.cancelar);
        TimePicker timePicker = popupView.findViewById(R.id.timePicker);

        PopupWindow popupWindow = new PopupWindow(popupView, 1000, ViewGroup.LayoutParams.WRAP_CONTENT);

        popupWindow.setFocusable(true);

        View rootView = findViewById(android.R.id.content);
        View dimView = findViewById(R.id.dim_view);
        dimView.setVisibility(View.VISIBLE);

        // Agregar un OnDismissListener para ocultar el dimView cuando se cierre el popup
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                dimView.setVisibility(View.GONE);
            }
        });

        Animation scaleAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.popup);
        popupView.startAnimation(scaleAnimation);

        popupWindow.showAtLocation(rootView, Gravity.CENTER, 0, 0);

        // Configura OnClickListener para el botón Aceptar
        botonAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener la hora y los minutos seleccionados del TimePicker
                int horaSeleccionada = timePicker.getHour();
                int minutosSeleccionados = timePicker.getMinute();

                // Formatear la hora y los minutos en el formato deseado (por ejemplo, "09:03 p. m.")
                String horaFormateada = String.format(Locale.getDefault(), "%02d:%02d", get12HourFormat(horaSeleccionada), minutosSeleccionados);
                String horaAmPm = getAmPmString(horaSeleccionada); // Obtener "a. m." o "p. m."

                // Construir la cadena completa con la hora formateada y "a. m." o "p. m."
                String horaCompleta = horaFormateada + " " + horaAmPm;

                // Haz algo con la hora completa, como mostrarla en un TextView
                TextView primeratomaTextView = findViewById(R.id.primeratoma); // Cambia el ID según tu diseño
                primeratomaTextView.setText("Primera administración a las:\n" + horaCompleta);

                // Cerrar el PopupWindow y ocultar el dimView
                popupWindow.dismiss();
                dimView.setVisibility(View.GONE);
            }
        });

        // Establece OnClickListener para el botón Cancelar
        botonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                popupCambiarFrecuencia();
            }
        });
    }

        // Método para obtener "a. m." o "p. m." según la hora seleccionada
        private String getAmPmString(int horaSeleccionada) {
            if (horaSeleccionada < 12) {
                return "a. m.";
            } else {
                return "p. m.";
            }
        }

        // Método para obtener el formato de 12 horas
        private int get12HourFormat(int horaSeleccionada) {
            if (horaSeleccionada == 0) {
                return 12;
            } else if (horaSeleccionada <= 12) {
                return horaSeleccionada;
            } else {
                return horaSeleccionada - 12;
            }
        }

    private void popupCambiarFecha() {
        View popupView = getLayoutInflater().inflate(R.layout.n63_4_popup_cambiarfecha, null);

        TextView botonAceptar = popupView.findViewById(R.id.aceptar);
        TextView botonCancelar = popupView.findViewById(R.id.cancelar);
        DatePicker datePicker = popupView.findViewById(R.id.datePicker); // Asume que tu NumberPicker tiene el ID "numberPicker" en tu diseño XML

        PopupWindow popupWindow = new PopupWindow(popupView, 1000, ViewGroup.LayoutParams.WRAP_CONTENT);

        popupWindow.setFocusable(true);

        View rootView = findViewById(android.R.id.content);
        View dimView = findViewById(R.id.dim_view);
        dimView.setVisibility(View.VISIBLE);

        // Agregar un OnDismissListener para ocultar el dimView cuando se cierre el popup
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                dimView.setVisibility(View.GONE);
            }
        });

        Animation scaleAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.popup);
        popupView.startAnimation(scaleAnimation);

        popupWindow.showAtLocation(rootView, Gravity.CENTER, 0, 0);

        // Configura OnClickListener para el botón Aceptar
        // Configura OnClickListener para el botón Aceptar en tu popupCambiarFecha
        botonAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener la fecha seleccionada del DatePicker
                int year = datePicker.getYear();
                int month = datePicker.getMonth();
                int dayOfMonth = datePicker.getDayOfMonth();

                // Crear un objeto LocalDate con la fecha seleccionada
                LocalDate selectedDate = LocalDate.of(year, month + 1, dayOfMonth); // Sumamos 1 al mes porque en Java enero es 0

                // Establecer la hora en 00:00
                LocalTime horaCero = LocalTime.of(0, 0);

                // Combinar la fecha seleccionada con la hora "00:00" para obtener un LocalDateTime
                LocalDateTime selectedDateTime = selectedDate.atTime(horaCero);

                // Formatear la fecha en el formato deseado (por ejemplo, "dd MMM yyyy")
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
                String fechaFormateada = selectedDateTime.format(formatter);


                // Llamar al método mostrarDuracion y pasar el LocalDateTime y el valor actual de días de duración
                mostrarDuracionFecha(selectedDateTime);

                // Cerrar el popup y ocultar dimView
                popupWindow.dismiss();
                dimView.setVisibility(View.GONE);
            }
        });



        // Establece OnClickListener para el botón Cancelar
        botonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

    }


    private void popupCambiarDiasTratamiento() {
        View popupView = getLayoutInflater().inflate(R.layout.n65_1_numero_dias, null);

        TextView botonAceptar = popupView.findViewById(R.id.aceptar);
        TextView botonCancelar = popupView.findViewById(R.id.cancelar);
        EditText textEditConcentracion = popupView.findViewById(R.id.textEdit_Concentracion);
        ImageView botonMenos = popupView.findViewById(R.id.imagen_boton_menos);
        ImageView botonMas = popupView.findViewById(R.id.imagen_boton_mas);
        textEditConcentracion.setInputType(InputType.TYPE_CLASS_NUMBER);
        // Establece el valor inicial en el EditText
        int valorInicial = 1; // Puedes establecer el valor inicial deseado
        textEditConcentracion.setText(String.valueOf(valorInicial));

        PopupWindow popupWindow = new PopupWindow(popupView, 1000, ViewGroup.LayoutParams.WRAP_CONTENT);

        popupWindow.setFocusable(true);

        View rootView = findViewById(android.R.id.content);
        View dimView = findViewById(R.id.dim_view);
        dimView.setVisibility(View.VISIBLE);

        // Agregar un OnDismissListener para ocultar el dimView cuando se cierre el popup
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                dimView.setVisibility(View.GONE);
            }
        });

        Animation scaleAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.popup);
        popupView.startAnimation(scaleAnimation);

        popupWindow.showAtLocation(rootView, Gravity.CENTER, 0, 0);

        botonAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener el valor actual del EditText
                int diasduracion = Integer.parseInt(textEditConcentracion.getText().toString());


                // Llamar al método mostrarDuracion y pasar la fecha y los días de duración
                mostrarDuracionDias(diasduracion);

                // Cerrar el popup y ocultar dimView
                popupWindow.dismiss();
                dimView.setVisibility(View.GONE);
            }
        });
        botonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        // Configura OnClickListener para el botón Menos
        botonMenos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int valorActual = Integer.parseInt(textEditConcentracion.getText().toString());
                if (valorActual > 1) {
                    valorActual--;
                    textEditConcentracion.setText(String.valueOf(valorActual));
                }
            }
        });

        // Configura OnClickListener para el botón Mas
        botonMas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int valorActual = Integer.parseInt(textEditConcentracion.getText().toString());
                if (valorActual < 99998) {
                    valorActual++;
                    textEditConcentracion.setText(String.valueOf(valorActual));
                }
            }
        });
    }

    private void popupCambiarConcentracion() {
        View popupView = getLayoutInflater().inflate(R.layout.n63_5_popup_cambiarconcentracion, null);

        TextView botonAceptar = popupView.findViewById(R.id.aceptar);
        TextView botonCancelar = popupView.findViewById(R.id.cancelar);

        PopupWindow popupWindow = new PopupWindow(popupView, 1000, ViewGroup.LayoutParams.WRAP_CONTENT);

        popupWindow.setFocusable(true);

        View rootView = findViewById(android.R.id.content);
        View dimView = findViewById(R.id.dim_view);
        dimView.setVisibility(View.VISIBLE);

        // Agregar un OnDismissListener para ocultar el dimView cuando se cierre el popup
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                dimView.setVisibility(View.GONE);
            }
        });

        Animation scaleAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.popup);
        popupView.startAnimation(scaleAnimation);

        popupWindow.showAtLocation(rootView, Gravity.CENTER, 0, 0);

        // Configura OnClickListener para el botón Aceptar
        // Configura OnClickListener para el botón Aceptar en tu popupCambiarFecha




        // Establece OnClickListener para el botón Cancelar
        botonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

    }
}
