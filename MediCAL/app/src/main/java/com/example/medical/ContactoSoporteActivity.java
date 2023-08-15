package com.example.medical;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.medical.FAQActivity;

public class ContactoSoporteActivity extends AppCompatActivity {

    private TextView emailTextView;
    private Button copiarButton;
    private Button compartirButton;
    private ImageView volverButton; // Cambiar el tipo de la variable a ImageView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.n30_contacto_soporte);

        emailTextView = findViewById(R.id.email);
        copiarButton = findViewById(R.id.button_copiar);
        volverButton = findViewById(R.id.boton_volver); // Cambiar la asignación a ImageView
        compartirButton = findViewById(R.id.button_compartir); // boton de compartir

        copiarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copiarEmailAlPortapapeles();
            }
        });

        volverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Llamar al método onBackPressed() al tocar el botón "volver"
            }
        });

        compartirButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compartirEmail(); // Llamar al método compartirEmail() al tocar el botón "compartir"
            }
        });
    }

    private void copiarEmailAlPortapapeles() {
        String email = emailTextView.getText().toString().trim();
        if (!email.isEmpty()) {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("Correo electrónico", email);
            clipboard.setPrimaryClip(clip);
            Toast.makeText(this, "Correo electrónico copiado al portapapeles", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No se pudo copiar el correo electrónico", Toast.LENGTH_SHORT).show();
        }
    }

    private void compartirEmail() {
        String email = emailTextView.getText().toString().trim();
        if (!email.isEmpty()) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, "¡Hola!\n" +
                    "\n" +
                    "Estoy usando MediCAL. Te comparto el correo de soporte por si tienes alguna consulta o necesitas ayuda: \n"+
                    "\n" + email); // Agregar el texto del correo prellenado
            startActivity(Intent.createChooser(intent, "Compartir vía"));
        } else {
            Toast.makeText(this, "No se puede compartir un email vacío", Toast.LENGTH_SHORT).show();
        }
    }

}

