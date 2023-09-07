package com.example.medical;

import static com.example.medical.NuevoMedicamentoActivity.bitmapToBase64;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.example.medical.model.Recordatorio;
import com.example.medical.retrofit.RecordatorioApi;
import com.example.medical.retrofit.RetrofitService;

import java.io.File;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AgregarImagenRecordatorioActivity extends AppCompatActivity {
    private RetrofitService retrofitService = new RetrofitService();
    private RecordatorioApi recordatorioApi = retrofitService.getRetrofit().create(RecordatorioApi.class);
    private static final int REQUEST_IMAGE_CAPTURE = 2;
    private Recordatorio recordatorio;
    private boolean tieneFoto = false;
    private int codRecordatorio;
    private Button establecer;
    private ImageView fotoRecordatorio;
    private ImageView botonEliminarFoto;
    private ImageView botonVolver;
    private LinearLayout galeria;
    private LinearLayout camara;
    private ActivityResultLauncher<Intent> galleryLauncher;
    private ActivityResultLauncher<Intent> cameraLauncher;
    private File capturedPhotoFile;  // Declare the File object

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.n53_adjuntar_imagen);//cambiar esta linea por el nombre del layout a probar
        inicializarVariables();
        botonVolver.setOnClickListener(view ->{onBackPressed();});
        botonEliminarFoto.setOnClickListener(view -> {
            fotoRecordatorio.setImageResource(R.drawable.foto_remedio);
        });

        capturedPhotoFile = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "captured_photo.jpg");
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_IMAGE_CAPTURE);
        }

        galeria.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            // Inicia la galería utilizando el ActivityResultLauncher
            galleryLauncher.launch(intent);
        });

        camara.setOnClickListener(view ->{
            Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            Uri photoUri = FileProvider.getUriForFile(this, getPackageName() + ".fileprovider", capturedPhotoFile);
            takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            cameraLauncher.launch(takePhotoIntent);
        });


        galleryLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) {
                Intent data = result.getData();
                if (data != null) {
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                        // Muestra la imagen seleccionada en un ImageView
                        fotoRecordatorio.setImageBitmap(bitmap);
                        tieneFoto = true;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        cameraLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) {
                Intent data = result.getData();
                if (data != null) {
                    try {
                        Bitmap imgBitmap = BitmapFactory.decodeFile(capturedPhotoFile.getAbsolutePath());

                        // Muestra la imagen capturada en un ImageView
                        fotoRecordatorio.setImageBitmap(imgBitmap);
                        tieneFoto = true;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        establecer.setOnClickListener(view ->{
            if(tieneFoto){
                Bitmap imagenBitmap = ((BitmapDrawable) fotoRecordatorio.getDrawable()).getBitmap();
                recordatorio.setImagen(bitmapToBase64(imagenBitmap));
                recordatorioApi.modificarRecordatorio(recordatorio).enqueue(new Callback<Recordatorio>() {
                    @Override
                    public void onResponse(Call<Recordatorio> call, Response<Recordatorio> response) {
                        Intent intent = new Intent(AgregarImagenRecordatorioActivity.this, AgregarDatosObligatoriosActivity.class);
                        intent.putExtra("codRecordatorio",getIntent().getIntExtra("codRecordatorio", 0));
                        intent.putExtra("presentacionMedId", getIntent().getIntExtra("presentacionMedId", 0));
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<Recordatorio> call, Throwable t) {
                        Toast.makeText(AgregarImagenRecordatorioActivity.this, "Error al modificar el recordatorio", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


    }

    private void inicializarVariables() {
        botonVolver = findViewById(R.id.boton_volver);
        fotoRecordatorio = findViewById(R.id.imagen);
        botonEliminarFoto = findViewById(R.id.icono_eliminar);
        establecer = findViewById(R.id.button_establecer);
        galeria = findViewById(R.id.galeria);
        camara = findViewById(R.id.camara);
        codRecordatorio = getIntent().getIntExtra("codRecordatorio", 0);
        inicializarFoto();
    }

    private void inicializarFoto() {
        recordatorioApi.getByCodRecordatorio(codRecordatorio).enqueue(new Callback<Recordatorio>() {
            @Override
            public void onResponse(Call<Recordatorio> call, Response<Recordatorio> response) {
                recordatorio = response.body();
                if(recordatorio.getImagen()!=null){
                    byte[] decodedBytes = Base64.decode(recordatorio.getImagen(), Base64.DEFAULT);
                    Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
                    fotoRecordatorio.setImageBitmap(decodedBitmap);
                    tieneFoto = true;
                }
            }

            @Override
            public void onFailure(Call<Recordatorio> call, Throwable t) {
                Toast.makeText(AgregarImagenRecordatorioActivity.this, "Error al cargar el recordatorio, cierre la aplicación y vuelva a intentarlo", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
