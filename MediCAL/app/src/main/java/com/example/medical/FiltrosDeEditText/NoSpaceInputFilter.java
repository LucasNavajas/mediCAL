package com.example.medical.FiltrosDeEditText;

import android.text.InputFilter;
import android.text.Spanned;

public class NoSpaceInputFilter implements InputFilter {

    @Override
    public CharSequence filter(CharSequence source, int start, int end,
                               Spanned dest, int dstart, int dend) {
        // Reemplaza los espacios con una cadena vacía
        return source.toString().replace(" ", "");
    }
}
