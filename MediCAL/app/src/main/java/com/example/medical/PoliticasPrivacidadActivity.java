package com.example.medical;

import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class PoliticasPrivacidadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.n03_0_acuerdo_politicas_privacidad);

        TextView texto1 = findViewById(R.id.texto1);
        TextView texto2 = findViewById(R.id.texto2);
        TextView texto3 = findViewById(R.id.texto3);

        resaltarPalabras(texto1, "Política de privacidad", LeerPoliticasActivity.class);
        resaltarPalabras(texto2, "Política de privacidad", LeerPoliticasActivity.class);
        resaltarPalabras(texto3, "Política de privacidad", LeerPoliticasActivity.class);
        resaltarPalabras(texto1, "Términos y Condiciones de uso", LeerTYCActivity.class);



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
