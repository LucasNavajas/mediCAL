package com.example.medical;

import android.app.Activity;
import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import java.io.File;

public class NuevoMedicamentoActivity extends AppCompatActivity {
    private static final int REQUEST_IMAGE_PICK = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 2;
    private ImageView botonVolver;
    private ImageView fotoMedicamento;
    private EditText nombreMedicamento;
    private EditText marcaMedicamento;

    private File capturedPhotoFile;  // Declare the File object
    private ActivityResultLauncher<Intent> galleryLauncher;
    private ActivityResultLauncher<String> cameraPermissionLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.n32_anadir_medicina);
        inicializarVariables();

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
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        Intent intent = result.getData();
                        if (intent.getAction() == null) {
                            // Load the captured image from the file
                            Bitmap imgBitmap = BitmapFactory.decodeFile(capturedPhotoFile.getAbsolutePath());
                            if (imgBitmap != null) {
                                fotoMedicamento.setImageBitmap(imgBitmap);
                            }
                        } else {
                            // Se seleccionó una imagen de la galería
                            Uri selectedImageUri = intent.getData();
                            // Establecer la imagen seleccionada en el ImageView
                            fotoMedicamento.setImageURI(selectedImageUri);
                        }
                    }
                });
    }

    private void inicializarVariables() {
        botonVolver = findViewById(R.id.boton_volver);
        nombreMedicamento = findViewById(R.id.text_nombreMedicamento);
        marcaMedicamento = findViewById(R.id.text_marcaMedicamento);
        fotoMedicamento = findViewById(R.id.imagen);

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
            Intent pickImageIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
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

}
