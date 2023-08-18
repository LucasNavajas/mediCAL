package com.example.medical;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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

import java.io.File;

public class NuevoMedicamentoActivity extends AppCompatActivity {
    private static final int REQUEST_IMAGE_PICK = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 2;
    private ImageView botonVolver;
    private ImageView fotoMedicamento;
    private EditText nombreMedicamento;
    private EditText marcaMedicamento;

    private ActivityResultLauncher<Intent> galleryLauncher;
    private ActivityResultLauncher<String> cameraPermissionLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.n32_anadir_medicina);
        inicializarVariables();

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
                        if (intent.getAction() != null && intent.getAction().equals(MediaStore.ACTION_IMAGE_CAPTURE)) {
                            // Se tomó una foto con la cámara
                            Bundle extras = intent.getExtras();
                            if (extras != null) {
                                Bitmap imgBitmap = (Bitmap) extras.get("data");
                                fotoMedicamento.setImageBitmap(imgBitmap);
                            }
                        } else {
                            // Se seleccionó una imagen de la galería
                            Uri selectedImageUri = intent.getData();
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
        Intent pickImageIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);


        Intent chooserIntent = Intent.createChooser(pickImageIntent, "Seleccionar imagen o tomar una foto");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{takePhotoIntent});

        galleryLauncher.launch(chooserIntent);
    }

}
