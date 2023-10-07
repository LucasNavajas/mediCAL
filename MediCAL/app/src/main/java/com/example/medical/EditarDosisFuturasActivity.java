package com.example.medical;

import static android.content.Intent.ACTION_PICK;

import static com.example.medical.NuevoMedicamentoActivity.bitmapToBase64;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Base64;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.example.medical.model.Concentracion;
import com.example.medical.model.Dosis;
import com.example.medical.model.Frecuencia;
import com.example.medical.model.Instruccion;
import com.example.medical.model.Inventario;
import com.example.medical.model.Medicamento;
import com.example.medical.model.Recordatorio;
import com.example.medical.retrofit.ConcentracionApi;
import com.example.medical.retrofit.FrecuenciaApi;
import com.example.medical.retrofit.DosisApi;
import com.example.medical.retrofit.InventarioApi;
import com.example.medical.retrofit.InstruccionApi;
import com.example.medical.retrofit.RecordatorioApi;
import com.example.medical.retrofit.RetrofitService;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditarDosisFuturasActivity extends AppCompatActivity {
    private RetrofitService retrofitService = new RetrofitService();
    private RecordatorioApi recordatorioApi = retrofitService.getRetrofit().create(RecordatorioApi.class);
    private ConcentracionApi concentracionApi = retrofitService.getRetrofit().create(ConcentracionApi.class);
    private FrecuenciaApi frecuenciaApi = retrofitService.getRetrofit().create(FrecuenciaApi.class);
    private InventarioApi inventarioApi = retrofitService.getRetrofit().create(InventarioApi.class);
    private DosisApi dosisApi = retrofitService.getRetrofit().create(DosisApi.class);
    private InstruccionApi instruccionApi = retrofitService.getRetrofit().create(InstruccionApi.class);
    private List<Concentracion> concentracionList;
    private List<String> concentraciones = new ArrayList<String>() {};
    private TextView textHecho;
    private Button botonHecho;
    private boolean tieneFoto = false;
    private LinearLayout cambiarConcentracion;
    private Recordatorio recordatorio;
    private PopupWindow popupWindow;
    private String nombrefrecuencia;
    private static final int REQUEST_IMAGE_CAPTURE = 2;
    private ImageView fotoMedicamento;
    private File capturedPhotoFile;  // Declare the File object
    private ActivityResultLauncher<Intent> galleryLauncher;
    private ActivityResultLauncher<String> cameraPermissionLauncher;
    private float cantreal = 0;
    private float cantaviso = 0;
    private float cantrealm = 0;
    private float cantavisom = 0;
    private Medicamento medicamento;
    private Frecuencia frecuencia;
    private Dosis dosis;
    private Concentracion concentracion;
    private Instruccion instruccion;
    private Inventario inventario;

    boolean inventarioActivo = false;


    @Override
    public void onBackPressed() {
        popupSalir();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.n63_0_editar_dosis_futuras);
        inicializarBotones();
        obtenerDatos(1); // Cambia el número 4 por el codRecordatorio deseado


        capturedPhotoFile = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "captured_photo.jpg");

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_IMAGE_CAPTURE);
        }

        cameraPermissionLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                isGranted -> {
                    if (isGranted) {
                        abrirSelectorImagen();
                    } else {
                        // Manejar el caso cuando el permiso de cámara es denegado
                    }
                });
// SE VE LA FOTO
        galleryLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        if(result.getData()!=null) {
                            Intent intent = result.getData();
                            Uri selectedImageUri = intent.getData();
                            // Se seleccionó una imagen de la galería
                            // Establecer la imagen seleccionada en el ImageView
                            fotoMedicamento.setImageURI(selectedImageUri);
                            Bitmap imagenBitmap = ((BitmapDrawable) fotoMedicamento.getDrawable()).getBitmap();
                            recordatorio.setImagen(bitmapToBase64(imagenBitmap));
                        }

                        else {
                            // Load the captured image from the file
                            Bitmap imgBitmap = BitmapFactory.decodeFile(capturedPhotoFile.getAbsolutePath());
                            Bitmap compressedBitmap = compressBitmap(imgBitmap);
                            fotoMedicamento.setImageBitmap(compressedBitmap);
                            Bitmap imagenBitmap = ((BitmapDrawable) fotoMedicamento.getDrawable()).getBitmap();
                            recordatorio.setImagen(bitmapToBase64(imagenBitmap));
                        }
                    }
                });


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
    private boolean enterPresionado = false;

    private void inicializarBotones() {
        fotoMedicamento = findViewById(R.id.imagen_actual);
        ImageButton desplegableFrecuencia = findViewById(R.id.desplegable_frecuencia);
        TextView cambiarFrecuencia = findViewById(R.id.cambiar_frecuencia);
        ImageButton desplegableHora = findViewById(R.id.desplegable_hora);
        TextView cambiarHora = findViewById(R.id.cambiar_hora);
        ImageButton desplegableDuracion = findViewById(R.id.desplegable_duracion);
        RelativeLayout cambiarDuracion = findViewById(R.id.cambiar_duracion);
        TextView duracionActual = findViewById(R.id.duracion_actual);
        ImageButton desplegableImagen = findViewById(R.id.desplegable_imagen);
        RelativeLayout cambiarImagen = findViewById(R.id.relative_cambiar_imagen);
        TextView textCambiarImagen = findViewById(R.id.cambiar_imagen);
        ImageButton desplegableConcentracion = findViewById(R.id.desplegable_concentracion);
        cambiarConcentracion = findViewById(R.id.cambiar_concentracion);
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
        textHecho = findViewById(R.id.texto_hecho);
        botonHecho = findViewById(R.id.button_hecho);

        textHecho.setOnClickListener(hechoOnclick());
        botonHecho.setOnClickListener(hechoOnclick());

        textCambiarImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirSelectorImagen();
            }
        });
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
                                recordatorio.setDuracionRecordatorio(diasduracion);

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
                                dosis.setCantidadDosis(cantdosis);
                            }
                        }
                    });

                } else {
                    cambiarConcentracion.setVisibility(View.GONE);
                    desplegableConcentracion.setImageResource(R.drawable.boton_desplegable);
                }
            }


        });

        EditText indicaciones = findViewById(R.id.indicaciones);

        indicaciones.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    // Se presionó la tecla "Done" o "Enter" en el teclado
                    String textoIngresado = indicaciones.getText().toString();
                    indicaciones.setHint(textoIngresado); // Establece el texto ingresado como hint
                    indicaciones.setText(""); // Limpia el texto del EditText

                    // Oculta el teclado virtual
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    instruccion.setDescInstruccion(textoIngresado);
                    return true; // Indica que el evento ha sido manejado
                }
                return false; // Indica que el evento no ha sido manejado
            }
        });

        desplegableInstrucciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cambiarInstrucciones.getVisibility() == View.GONE) {
                    // Cambia la visibilidad y la imagen del desplegable
                    cambiarInstrucciones.setVisibility(View.VISIBLE);
                    desplegableInstrucciones.setImageResource(R.drawable.boton_desplegable_arriba);

                    // Guarda el hint actual antes de cambiarlo
                    String hintActual = indicaciones.getHint().toString();

                    // Cambia el hint al texto ingresado si no está vacío
                    String textoIngresado = indicaciones.getText().toString();
                    if (!textoIngresado.isEmpty()) {
                        indicaciones.setHint(textoIngresado);
                        indicaciones.setText(""); // Limpia el texto del EditText
                        indicaciones.clearFocus(); // Desenfoca el EditText para deseleccionarlo
                        indicaciones.setCursorVisible(false); // Oculta el cursor de texto
                    }

                    // Guarda el hint original en una etiqueta invisible
                    indicaciones.setTag(hintActual);
                } else {
                    // Restaura la visibilidad y la imagen del desplegable
                    cambiarInstrucciones.setVisibility(View.GONE);
                    desplegableInstrucciones.setImageResource(R.drawable.boton_desplegable);

                    // Restaura el hint original
                    String hintOriginal = indicaciones.getTag().toString();
                    indicaciones.setHint(hintOriginal);

                    // Vuelve a habilitar el cursor de texto
                    indicaciones.setCursorVisible(true);
                }
            }
        });

        EditText editTextcantrealm = findViewById(R.id.cantreal);

        // cuando toque enter
        editTextcantrealm.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    // Se presionó la tecla "Done" o "Enter" en el teclado
                    enterPresionado = true;
                    String textoIngresado = editTextcantrealm.getText().toString().trim();

                    // Verifica si el texto ingresado es igual a "0"
                    if (textoIngresado.equals("0") || textoIngresado.isEmpty()) {
                        // Muestra el popup de aviso
                        // Muestra un mensaje de error si no se ingresa ninguna cantidad
                        Toast.makeText(getApplicationContext(), "Por favor, ingrese una cantidad válida", Toast.LENGTH_SHORT).show();
                    } else {
                        // Convierte el texto ingresado a un número decimal (float)
                        cantrealm = Float.parseFloat(textoIngresado);

                        // Oculta el teclado virtual
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                        // Llama al método con las variables de control y el valor ingresado como argumentos
                        MostrarDescInventario(aceptarPresionado, enterPresionado);
                        return true; // Indica que el evento ha sido manejado
                    }
                }
                return false; // Indica que el evento no ha sido manejado
            }
        });


        desplegableRecarga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView descripcion = findViewById(R.id.descripcion_recordatorio_receta);
                if (cambiarRecarga.getVisibility() == View.GONE) {
                    descripcion.setTextColor(ContextCompat.getColor(EditarDosisFuturasActivity.this, R.color.black));
                    desplegableRecarga.setImageResource(R.drawable.boton_desplegable_arriba);
                    cambiarRecarga.setVisibility(View.VISIBLE);
                    if (recordatorioRecarga.isChecked()) {
                        agregarInventario.setVisibility(View.VISIBLE);
                    } else {
                        agregarInventario.setVisibility(View.GONE);
                    }
                } else {
                    descripcion.setTextColor(ContextCompat.getColor(EditarDosisFuturasActivity.this, R.color.gris_medical));
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
                    agregarInventario.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            popupStockmin();
                        }
                    });

                } else {
                    agregarInventario.setVisibility(View.GONE);

                }         }
        });
    }

    private View.OnClickListener hechoOnclick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (recordatorio.getInventario() != null) {

                    inventarioApi.save(inventario).enqueue(new Callback<Inventario>() {//Aca se guardan todos los atributos modificados del recordatorio (duracion, fecha de inicio, fecha de fin e imagen)
                        @Override
                        public void onResponse(Call<Inventario> call, Response<Inventario> response) {
                            Toast.makeText(EditarDosisFuturasActivity.this, "Inventario modificado", Toast.LENGTH_SHORT).show();
                            recordatorio.setInventario(response.body());
                            saveRecordatorio();
                        }

                        @Override
                        public void onFailure(Call<Inventario> call, Throwable t) {
                            Toast.makeText(EditarDosisFuturasActivity.this, "Inventario no modificado", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
            saveRecordatorio();
                }}};}

    private void saveRecordatorio(){
        recordatorioApi.save(recordatorio).enqueue(new Callback<Recordatorio>() {//Aca se guardan todos los atributos modificados del recordatorio (duracion, fecha de inicio, fecha de fin e imagen)
            @Override
            public void onResponse(Call<Recordatorio> call, Response<Recordatorio> response) {
                Toast.makeText(EditarDosisFuturasActivity.this, "Recordatorio modificado", Toast.LENGTH_SHORT).show();
                saveFrecuencia();
            }

            @Override
            public void onFailure(Call<Recordatorio> call, Throwable t) {
                Toast.makeText(EditarDosisFuturasActivity.this, "Recordatorio no modificado", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void saveFrecuencia() {
        frecuenciaApi.save(frecuencia).enqueue(new Callback<Frecuencia>() {
            @Override
            public void onResponse(Call<Frecuencia> call, Response<Frecuencia> response) {
                Toast.makeText(EditarDosisFuturasActivity.this, "Frecuencia modificada", Toast.LENGTH_SHORT).show();
                saveDosis();
                //saveInventario(), despues de cada metodo save, en el onresponse se llama al otro metodo save, para que se ejecute solo si se pudo guardar los otros cambios
                //En el ultimo metodo save se cierra la actividad y se muestra el iniciocalendario
            }

            @Override
            public void onFailure(Call<Frecuencia> call, Throwable t) {
                Toast.makeText(EditarDosisFuturasActivity.this, "Frecuencia no modificada", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveDosis() {
        dosisApi.save(dosis).enqueue(new Callback<Dosis>() {
            @Override
            public void onResponse(Call<Dosis> call, Response<Dosis> response) {
                Toast.makeText(EditarDosisFuturasActivity.this, "Dosis modificada", Toast.LENGTH_SHORT).show();
                saveInstruccion();
                //saveInventario(), despues de cada metodo save, en el onresponse se llama al otro metodo save, para que se ejecute solo si se pudo guardar los otros cambios
                //En el ultimo metodo save se cierra la actividad y se muestra el iniciocalendario
            }

            @Override
            public void onFailure(Call<Dosis> call, Throwable t) {
                Toast.makeText(EditarDosisFuturasActivity.this, "Dosis no modificada", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void saveInstruccion() {
        instruccionApi.save(instruccion).enqueue(new Callback<Instruccion>() {
            @Override
            public void onResponse(Call<Instruccion> call, Response<Instruccion> response) {
                Toast.makeText(EditarDosisFuturasActivity.this, "Instruccion modificada", Toast.LENGTH_SHORT).show();
                //saveDosis();
                //saveInventario(), despues de cada metodo save, en el onresponse se llama al otro metodo save, para que se ejecute solo si se pudo guardar los otros cambios
                //En el ultimo metodo save se cierra la actividad y se muestra el iniciocalendario
            }

            @Override
            public void onFailure(Call<Instruccion> call, Throwable t) {
                Toast.makeText(EditarDosisFuturasActivity.this, "Instruccion no modificada", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private Bitmap compressBitmap(Bitmap bitmap) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            // Comprimir la imagen en formato JPEG con una calidad del 80%
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream);

            // Convertir la salida comprimida en un nuevo Bitmap
            byte[] compressedData = outputStream.toByteArray();
            return BitmapFactory.decodeByteArray(compressedData, 0, compressedData.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private void obtenerDatos(int codRecordatorio) {
        concentracionApi.getAllConcentracion().enqueue(new Callback<List<Concentracion>>() {
            @Override
            public void onResponse(Call<List<Concentracion>> call, Response<List<Concentracion>> response) {
                concentracionList=response.body();
                for(Concentracion concentracion : concentracionList){
                    concentraciones.add(concentracion.getUnidadMedidaC());
                }
                cambiarConcentracion.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupCambiarConcentracion();
                    }
                });
            }

            @Override
            public void onFailure(Call<List<Concentracion>> call, Throwable t) {
                Toast.makeText(EditarDosisFuturasActivity.this, "Error al cargar las concentraciones", Toast.LENGTH_SHORT).show();
            }
        });
        recordatorioApi.getByCodRecordatorio(codRecordatorio).enqueue(new Callback<Recordatorio>() {
            @Override
            public void onResponse(Call<Recordatorio> call, Response<Recordatorio> response) {
                recordatorio = response.body();

                if(recordatorio.getImagen()!=null){
                    byte[] decodedBytes = Base64.decode(recordatorio.getImagen(), Base64.DEFAULT);
                    Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
                    fotoMedicamento.setImageBitmap(decodedBitmap);
                    tieneFoto = true;
                }
                medicamento = recordatorio.getMedicamento();
                frecuencia = recordatorio.getFrecuencia();
                dosis = recordatorio.getDosis();
                concentracion = dosis.getConcentracion();
                instruccion = recordatorio.getInstruccion();
                if (recordatorio.getInventario()!= null){
                    inventario = recordatorio.getInventario();
                }

                String nombreMedicamento = medicamento.getNombreMedicamento();
                int diastoma = 0;
                int diasdescanso = 0;
                String nombreFrecuencia = "";
                if(frecuencia == null){
                    nombreFrecuencia = "Según sea necesario";
                }
                else{
                    nombreFrecuencia = frecuencia.getNombreFrecuencia();
                    diastoma = frecuencia.getDiasTomaF();
                    diasdescanso = frecuencia.getDiasDescansoF();
                }

                String imagen = recordatorio.getImagen();
                float cantdosis = dosis.getCantidadDosis();
                float valorconcentracion = dosis.getValorConcentracion();
                String unidadmedidac = concentracion.getUnidadMedidaC();
                String radioinstruccion = instruccion.getNombreInstruccion();
                String descindicacion = instruccion.getDescInstruccion();
                int codinventario = 0;
                if(inventario != null) {
                    codinventario = inventario.getCodInventario();
                    cantreal = inventario.getCantRealInventario();
                    cantaviso = inventario.getCantAvisoInventario();
                }
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
    private void abrirSelectorImagen() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            // Si ya tienes el permiso de la cámara, abre el selector de imágenes
            Intent pickImageIntent = new Intent(ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            Uri photoUri = FileProvider.getUriForFile(this, getPackageName() + ".fileprovider", capturedPhotoFile);
            takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);

            Intent chooserIntent = Intent.createChooser(pickImageIntent, "Seleccionar imagen o tomar una foto");
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{takePhotoIntent});

            galleryLauncher.launch(chooserIntent);
        } else {
            // Si no tienes el permiso, solicítalo
            cameraPermissionLauncher.launch(Manifest.permission.CAMERA);
        }
    }

    public static String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();

// Codifica el arreglo de bytes en una cadena Base64.
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }
    private void mostrarInventario(int codinventario, float cantreal, float cantaviso) {
        Switch recordatorioRecarga = findViewById(R.id.activar_recordatorio_receta);
        LinearLayout definirInventario = findViewById(R.id.definir_inventario);
        TextView descripcionRecordatorio = findViewById(R.id.descripcion_recordatorio_receta);
        EditText editTextCantreal = findViewById(R.id.cantreal);
        TextView textViewCantaviso = findViewById(R.id.establecer_alerta_inventario);
        if (inventario!=null){
            recordatorioRecarga.setChecked(true);
        }
        // Formatea las cantidades con el formato "#.###"
        DecimalFormat decimalFormat = new DecimalFormat("#.###");
        String cantrealFormateada = decimalFormat.format(cantreal);
        String cantavisoFormateada = decimalFormat.format(cantaviso);

        if (codinventario != 0) {
            inventarioActivo = true;
            descripcionRecordatorio.setText("Actualmente tiene " + inventario.getCantRealInventario() + " medicamentos. Se le recordará cuando le queden " + inventario.getCantAvisoInventario() + " medicamentos");
            editTextCantreal.setHint("Cantidad de medicamentos: " + inventario.getCantRealInventario());
            textViewCantaviso.setText("Establecer cuando me queden " + inventario.getCantAvisoInventario() + " medicamentos");
        } else {
            inventarioActivo = false;
            descripcionRecordatorio.setText("Introduzca la cantidad de medicamento que tiene ahora para obtener un recordatorio de recarga");
            editTextCantreal.setHint("Cantidad de medicamentos:");
            textViewCantaviso.setText("Establecer cuando me queden X medicamentos");
        }

        recordatorioRecarga.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    inventarioActivo = true;
                    descripcionRecordatorio.setText("Actualmente tiene " + inventario.getCantRealInventario() + " medicamentos. Se le recordará cuando le queden " + inventario.getCantAvisoInventario() + " medicamentos");
                    editTextCantreal.setHint("Cantidad de medicamentos: " + inventario.getCantRealInventario());
                    textViewCantaviso.setText("Establecer cuando me queden " + inventario.getCantAvisoInventario()+ " medicamentos");
                if (inventario == null){
                    inventario = new Inventario();
                }
                } else {
                    inventarioActivo = false;
                    descripcionRecordatorio.setText("Introduzca la cantidad de medicamento que tiene ahora para obtener un recordatorio de recarga");
                    editTextCantreal.setHint("Cantidad de medicamentos:");
                    textViewCantaviso.setText("Establecer cuando me queden X medicamentos");
                    recordatorio.setInventario(null);
                }
            }
        });
    }


    private void mostrarIndicacion(String descindicacion) {
        EditText indicacionEditText = findViewById(R.id.indicaciones); // Cambia a tu ID real
        indicacionEditText.setHint(String.valueOf(descindicacion));
        instruccion.setDescInstruccion(String.valueOf(descindicacion));
    }

    private void mostrarInstruccion(String radioinstruccion) {
        RadioGroup radiog_instrucciones = findViewById(R.id.radio_group_instrucciones);
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
        radiog_instrucciones.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton rb = findViewById(i);
                instruccion.setNombreInstruccion(rb.getText().toString());
            }
        });
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
                        popupWindow.dismiss();
                        popupXhoras();
                        break;
                    case "Cada X días":
                        popupWindow.dismiss();
                        popupXdias();
                        break;
                    case "Cada X semanas":
                        popupWindow.dismiss();
                        popupXsemanas();
                        break;
                    case "Cada X meses":
                        popupWindow.dismiss();
                        popupXmeses();
                        break;
                    case "Ciclo recurrente":
                        popupWindow.dismiss();
                        popupCiclos();

                        break;
                    case "Según sea necesario":
                        popupWindow.dismiss();
                        nombrefrecuencia = "Según sea necesario";
                        mostrarNombreFrecuencia(nombrefrecuencia, 0, 0);
                        break;
                }


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
                frecuencia.setCantidadFrecuencia(selectedValue);
                frecuencia.setDiasDescansoF(0);
                frecuencia.setDiasTomaF(0);
                frecuencia.setNombreFrecuencia(nombrefrecuencia);
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
                frecuencia.setCantidadFrecuencia(selectedValue*24);
                frecuencia.setDiasDescansoF(0);
                frecuencia.setDiasTomaF(0);
                frecuencia.setNombreFrecuencia(nombrefrecuencia);
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
                frecuencia.setCantidadFrecuencia(selectedValue*24*7);
                frecuencia.setDiasDescansoF(0);
                frecuencia.setDiasTomaF(0);
                frecuencia.setNombreFrecuencia(nombrefrecuencia);
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
                frecuencia.setCantidadFrecuencia(selectedValue*24*30);
                frecuencia.setDiasDescansoF(0);
                frecuencia.setDiasTomaF(0);
                frecuencia.setNombreFrecuencia(nombrefrecuencia);
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
                frecuencia.setCantidadFrecuencia(24);
                frecuencia.setDiasDescansoF(selectedValued);
                frecuencia.setDiasTomaF(selectedValuet);
                frecuencia.setNombreFrecuencia(nombrefrecuencia);
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

                LocalDateTime fechaRecordatorio = recordatorio.getFechaInicioRecordatorio();

                LocalDateTime fechaHoraRecordatorio = fechaRecordatorio.toLocalDate().atTime(horaSeleccionada, minutosSeleccionados);
                // Formatear la hora y los minutos en el formato deseado (por ejemplo, "09:03 p. m.")
                String horaFormateada = String.format(Locale.getDefault(), "%02d:%02d", get12HourFormat(horaSeleccionada), minutosSeleccionados);
                String horaAmPm = getAmPmString(horaSeleccionada); // Obtener "a. m." o "p. m."

                // Construir la cadena completa con la hora formateada y "a. m." o "p. m."
                String horaCompleta = horaFormateada + " " + horaAmPm;

                // Haz algo con la hora completa, como mostrarla en un TextView
                TextView primeratomaTextView = findViewById(R.id.primeratoma); // Cambia el ID según tu diseño
                primeratomaTextView.setText("Primera administración a las:\n" + horaCompleta);
                recordatorio.setFechaInicioRecordatorio(fechaHoraRecordatorio);

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
                LocalDateTime fechaHoraRecordatorio = recordatorio.getFechaInicioRecordatorio();
                // Obtener la fecha seleccionada del DatePicker
                int year = datePicker.getYear();
                int month = datePicker.getMonth();
                int dayOfMonth = datePicker.getDayOfMonth();

                LocalDateTime nuevaFechaHora = LocalDateTime.of(year, month+1, dayOfMonth, fechaHoraRecordatorio.getHour(), fechaHoraRecordatorio.getMinute());

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

                recordatorio.setFechaInicioRecordatorio(nuevaFechaHora);
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
        EditText textEditDias = popupView.findViewById(R.id.textEditdias);
        ImageView botonMenos = popupView.findViewById(R.id.imagen_boton_menos);
        ImageView botonMas = popupView.findViewById(R.id.imagen_boton_mas);
        textEditDias.setInputType(InputType.TYPE_CLASS_NUMBER);
        // Establece el valor inicial en el EditText
        int valorInicial = 1; // Puedes establecer el valor inicial deseado
        textEditDias.setText(String.valueOf(valorInicial));

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
                int diasduracion = Integer.parseInt(textEditDias.getText().toString());
                recordatorio.setDuracionRecordatorio(diasduracion);

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
                int valorActual = Integer.parseInt(textEditDias.getText().toString());
                if (valorActual > 1) { // Verifica si el valor actual es mayor que 1
                    valorActual--;
                    textEditDias.setText(String.valueOf(valorActual));
                }
            }
        });

        // Configura OnClickListener para el botón Mas
        botonMas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int valorActual = Integer.parseInt(textEditDias.getText().toString());
                if (valorActual < 99998) {
                    valorActual++;
                    textEditDias.setText(String.valueOf(valorActual));
                }
            }
        });
    }

    private void popupCambiarConcentracion() {
        View popupView = getLayoutInflater().inflate(R.layout.n63_5_popup_cambiarconcentracion, null);

        TextView botonAceptar = popupView.findViewById(R.id.aceptar);
        TextView botonCancelar = popupView.findViewById(R.id.cancelar);
        EditText textEditConcentracion = popupView.findViewById(R.id.textEdit_Concentracion);
        ImageView botonMenos = popupView.findViewById(R.id.imagen_boton_menos);
        ImageView botonMas = popupView.findViewById(R.id.imagen_boton_mas);

        Spinner concentracionSpinner = popupView.findViewById(R.id.concentracion_spinner);
        ArrayAdapter<String> concentracionAdapter;
        // Suponiendo que tienes una lista de concentraciones en un array llamado concentracionesList
        // Supongamos que tienes una lista de concentraciones en un array llamado concentracionesList
        String[] concentracionesArray = concentraciones.toArray(new String[0]);
        concentracionAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, concentracionesArray);
        concentracionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Configura el adaptador para el Spinner
        concentracionSpinner.setAdapter(concentracionAdapter);

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

        // Configura OnClickListener para el botón Menos
        botonMenos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(textEditConcentracion.getText().toString().equals("")){
                    textEditConcentracion.setText("0.00");
                }
                else{
                    if(Float.parseFloat(textEditConcentracion.getText().toString())<=1) {
                        textEditConcentracion.setText("0.00");
                    }
                    else{
                        float valorC = Float.parseFloat(textEditConcentracion.getText().toString()) - 1;
                        textEditConcentracion.setText(Float.toString(valorC));
                    }
                }
            }
        });


        // Configura OnClickListener para el botón Mas
        botonMas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textEditConcentracion.getText().toString().equals("")) {
                    textEditConcentracion.setText("1.00");
                } else {
                    float valorC = Float.parseFloat(textEditConcentracion.getText().toString()) + 1;
                    textEditConcentracion.setText(Float.toString(valorC));
                }
            }
        });

        // Establece OnClickListener para el botón Cancelar
        botonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        // Configura OnClickListener para el botón Aceptar
        botonAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener el valor actual del EditText como una cadena
                String valorActualStr = textEditConcentracion.getText().toString();

                // Parsear el valor actual a un número decimal (float)
                float valorActual = Float.parseFloat(valorActualStr);

                // Obtener la unidad de medida del Spinner
                String unidadMedida = concentracionSpinner.getSelectedItem().toString();

                // Llamar al método mostrarConcentracion y pasar el valor y la unidad de medida
                mostrarConcentracion(valorActual, unidadMedida);

                // Cerrar el popup y ocultar dimView
                popupWindow.dismiss();
                dimView.setVisibility(View.GONE);
                dosis.setValorConcentracion(valorActual);
                int indice = concentracionSpinner.getSelectedItemPosition();
                dosis.setConcentracion(concentracionList.get(indice));

            }
        });

    }

    private boolean aceptarPresionado = false;

    private void popupStockmin() {
        View popupView = getLayoutInflater().inflate(R.layout.n66_1_numero_inventario, null);

        EditText editTextCantidad = popupView.findViewById(R.id.cantstock);
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

        // Establece OnClickListener para el botón Aceptar

        botonAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aceptarPresionado = true; // Se ha presionado el botón "Aceptar"

                // Obtén el valor ingresado en el EditText
                String cantidadIngresada = editTextCantidad.getText().toString().trim();

                // Verifica si la cantidad ingresada no está vacía
                if (!cantidadIngresada.isEmpty() && !cantidadIngresada.equals("0")) {
                    // Convierte la cantidad ingresada a un número flotante
                    float cantavisomValue = Float.parseFloat(cantidadIngresada);
                    cantavisom = cantavisomValue;

                    // Formatea el número para evitar mostrar decimales si es un número entero
                    String cantidadFormateada = new DecimalFormat("#.###").format(cantavisom);

                    // Guarda el valor formateado en textViewCantaviso
                    TextView textViewCantaviso = findViewById(R.id.establecer_alerta_inventario);
                    textViewCantaviso.setText("Establecer cuando me queden " + inventario.getCantRealInventario() + " medicamentos");

                    // Cierra el popup
                    popupWindow.dismiss();
                    inventario.setCantAvisoInventario((int)cantavisom);
                    // Llama al método con las variables de control como argumentos
                    MostrarDescInventario(aceptarPresionado, enterPresionado);
                } else {
                    // Muestra un mensaje de error si no se ingresa ninguna cantidad
                    Toast.makeText(getApplicationContext(), "Por favor, ingrese una cantidad válida", Toast.LENGTH_SHORT).show();
                }
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

    private void MostrarDescInventario(boolean aceptarPresionado, boolean enterPresionado) {
        TextView descripcionRecordatorio = findViewById(R.id.descripcion_recordatorio_receta);

        DecimalFormat decimalFormat = new DecimalFormat("#.###"); // Formato para un decimal

        String cantRealmFormatted = decimalFormat.format(cantrealm);
        String cantAvisomFormatted = decimalFormat.format(cantavisom);
        String cantRealFormatted = decimalFormat.format(cantreal);
        String cantAvisoFormatted = decimalFormat.format(cantaviso);

        if (aceptarPresionado && enterPresionado) {
            // Caso 1: Si se presiona enter y aceptar
            inventario.setCantRealInventario((int)cantrealm);
            descripcionRecordatorio.setText("Actualmente tiene " + inventario.getCantRealInventario() + " medicamentos. Se le recordará cuando le queden " + inventario.getCantAvisoInventario() + " medicamentos."); // Cambia el texto de descripción

        } else if (enterPresionado) {
            // Caso 2: Si se presiona enter pero no aceptar osea cambia la cant real
            if (!cantAvisoFormatted.equals("0")) {
                // Si cantAvisoFormatted no es igual a "0", muestra el mensaje original
                inventario.setCantRealInventario((int)cantrealm);
                descripcionRecordatorio.setText("Actualmente tiene " + inventario.getCantRealInventario() + " medicamentos. Se le recordará cuando le queden " + inventario.getCantAvisoInventario() + " medicamentos."); // Cambia el texto de descripción

            } else {
                inventario.setCantRealInventario((int)cantrealm);
                // Si cantAvisoFormatted es igual a "0", muestra un mensaje indicando que se debe elegir una cantidad de aviso
                descripcionRecordatorio.setText("Actualmente tiene " + inventario.getCantRealInventario() + " medicamentos.\nEstablezca la cantidad de stock mínimo."); // Cambia el texto de descripción

            }
        } else if (aceptarPresionado) {
            // Caso 3: Si se presiona aceptar pero no enter

            //
            if (!cantRealFormatted.equals("0")) {
                // Si cantRealFormatted no es igual a "0", muestra el mensaje original
                descripcionRecordatorio.setText("Actualmente tiene " + inventario.getCantRealInventario() + " medicamentos. Se le recordará cuando le queden " + inventario.getCantAvisoInventario()+ " medicamentos."); // Cambia el texto de descripción
            } else {
                // Si cantRealFormatted es igual a "0", muestra un mensaje indicando que se debe elegir una cantidad real
                descripcionRecordatorio.setText("Establezca la cantidad actual de medicamentos.\nSe le recordará cuando le queden " + inventario.getCantAvisoInventario() + " medicamentos."); // Cambia el texto de descripción
            }
        } else {
            // Caso 4: Si ninguna de las condiciones anteriores se cumple
        }
    }


}
