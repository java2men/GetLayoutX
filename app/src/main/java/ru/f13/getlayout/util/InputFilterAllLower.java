package ru.f13.getlayout.util;

import android.text.InputFilter;
import android.text.Spanned;

/**
 * Фильтр понижения регистра для текста редактора
 */
public class InputFilterAllLower extends InputFilter.AllCaps {

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

        CharSequence filter = super.filter(source, start, end, dest, dstart, dend);
        if (filter != null) {
            filter = filter.toString().toLowerCase();
        } else {
            filter = source == null ? null : source.toString().toLowerCase();
        }

        return filter;

    }
}
