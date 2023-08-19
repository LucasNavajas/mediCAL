package com.example.medical;

import static android.content.Intent.ACTION_PICK;

import android.app.Activity;
import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.example.medical.model.Medicamento;
import com.example.medical.retrofit.MedicamentoApi;
import com.example.medical.retrofit.RetrofitService;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.time.LocalDate;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NuevoMedicamentoActivity extends AppCompatActivity {

    private RetrofitService retrofitService = new RetrofitService();
    private MedicamentoApi medicamentoApi = retrofitService.getRetrofit().create(MedicamentoApi.class);
    private static final int REQUEST_IMAGE_PICK = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 2;
    private ImageView botonVolver;
    private ImageView fotoMedicamento;
    private EditText nombreMedicamento;
    private EditText marcaMedicamento;
    private Button siguiente;
    private Medicamento medicamento = new Medicamento();

    private File capturedPhotoFile;  // Declare the File object
    private ActivityResultLauncher<Intent> galleryLauncher;
    private ActivityResultLauncher<String> cameraPermissionLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.n32_anadir_medicina);
        inicializarVariables();

        botonVolver.setOnClickListener(view ->{onBackPressed();});

        siguiente.setOnClickListener(view -> {
            Bitmap imagenBitmap = ((BitmapDrawable) fotoMedicamento.getDrawable()).getBitmap();
            String nombreMedicamentoString = nombreMedicamento.getText().toString();
            String marcaMedicamentoString = marcaMedicamento.getText().toString();
            medicamento.setImagen(bitmapToBase64(imagenBitmap));
            medicamento.setMarcaMedicamento(marcaMedicamentoString);
            medicamento.setNombreMedicamento(nombreMedicamentoString);
            medicamento.setEsParticular(true);
            medicamento.setFechaAltaMedicamento(LocalDate.now());
            medicamentoApi.saveMedicamento(medicamento).enqueue(new Callback<Medicamento>() {
                @Override
                public void onResponse(Call<Medicamento> call, Response<Medicamento> response) {
                    Toast.makeText(NuevoMedicamentoActivity.this, "Medicamento creado", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<Medicamento> call, Throwable t) {
                    Toast.makeText(NuevoMedicamentoActivity.this, "Error de medicamento creado", Toast.LENGTH_SHORT).show();
                }
            });

        });

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

        galleryLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent intent = result.getData();
                        Uri selectedImageUri = intent.getData();
                        if (selectedImageUri != null) {
                            // Se seleccionó una imagen de la galería
                            // Establecer la imagen seleccionada en el ImageView
                            fotoMedicamento.setImageURI(selectedImageUri);

                        } else {
                            // Load the captured image from the file
                            Bitmap imgBitmap = BitmapFactory.decodeFile(capturedPhotoFile.getAbsolutePath());
                            if (imgBitmap != null) {
                                fotoMedicamento.setImageBitmap(imgBitmap);
                            }
                        }
                    }
                });
    }

    private void inicializarVariables() {
        botonVolver = findViewById(R.id.boton_volver);
        nombreMedicamento = findViewById(R.id.text_nombreMedicamento);
        marcaMedicamento = findViewById(R.id.text_marcaMedicamento);
        fotoMedicamento = findViewById(R.id.imagen);
        siguiente = findViewById(R.id.button_siguiente);

        fotoMedicamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirSelectorImagen();
            }
        });
    }
    private void abrirSelectorImagen() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
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
}
