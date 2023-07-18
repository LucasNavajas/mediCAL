package com.example.medical;

import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class PoliticasPrivacidadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.n03_0_acuerdo_politicas_privacidad);

        Button buttonSiguiente = findViewById(R.id.button_siguiente);
        TextView aceptarTodo = findViewById(R.id.aceptar_todo);

        CheckBox check1 = findViewById(R.id.checkBox1);
        CheckBox check2 = findViewById(R.id.checkBox2);
        CheckBox check3 = findViewById(R.id.checkBox3);
        TextView texto1 = findViewById(R.id.texto1);
        TextView texto2 = findViewById(R.id.texto2);
        TextView texto3 = findViewById(R.id.texto3);

        resaltarPalabras(texto1, "Política de privacidad", LeerPoliticasActivity.class);
        resaltarPalabras(texto2, "Política de privacidad", LeerPoliticasActivity.class);
        resaltarPalabras(texto3, "Política de privacidad", LeerPoliticasActivity.class);
        resaltarPalabras(texto1, "Términos y Condiciones de uso", LeerTYCActivity.class);


        buttonSiguiente.setOnClickListener(view -> {
            if(check1.isChecked() && check2.isChecked()) {
                Intent intent = new Intent(PoliticasPrivacidadActivity.this, CrearCuenta1Activity.class);
                startActivity(intent);
            }
            else{
                Toast.makeText(getApplicationContext(), "Debe aceptar las políticas de privacidad y los términos y condiciones para crear una cuenta", Toast.LENGTH_SHORT).show();
            }
        });

        aceptarTodo.setOnClickListener(view -> {
            check1.setChecked(true);
            check2.setChecked(true);
            check3.setChecked(true);
        });

    }

    protected void resaltarPalabras(TextView textView, String palabraResaltada, Class<?> actividad){
        String textoCompleto = textView.getText().toString();

        SpannableString spannableString = new SpannableString(textoCompleto);
        int startIndex = textoCompleto.indexOf(palabraResaltada);
        int endIndex = startIndex + palabraResaltada.length();

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                // Acción a realizar al hacer clic en "Política de privacidad"
                startActivity(new Intent(PoliticasPrivacidadActivity.this, actividad));
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                // Establecer el estilo del texto resaltado (color, subrayado, etc.)
                ds.setUnderlineText(true);
                ds.setColor(ContextCompat.getColor(PoliticasPrivacidadActivity.this,R.color.verdeTextos));
            }
        };

        spannableString.setSpan(clickableSpan, startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        textView.setText(spannableString);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
