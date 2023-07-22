package com.example.medical;

import android.text.InputFilter;
import android.text.Spanned;

public class TextOnlyInputFilter implements InputFilter {
    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        StringBuilder filteredStringBuilder = new StringBuilder();
        for (int i = start; i < end; i++) {
            char character = source.charAt(i);
            if (Character.isLetter(character) || Character.isSpaceChar(character)) {
                filteredStringBuilder.append(character);
            }
        }
        return filteredStringBuilder.toString();
    }
}
