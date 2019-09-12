package ru.f13.getlayout.util.convert;

import androidx.annotation.NonNull;

public class ConvertSequence implements CharSequence {

    private String sequence = "";
    private String modifier = ConvertLayout.MODIFIER_NONE;

    public String getSequence() {

        return sequence;
    }

    public void setSequence(String sequence){

        this.sequence = sequence;

    }

    public ConvertSequence(String sequence, String modifier) {
        this.sequence = sequence;
        this.modifier = modifier;
    }

    public ConvertSequence(String sequence, boolean isShift, boolean isCapsLock) {
        this.sequence = sequence;

        if (isShift && !isCapsLock) {
            this.modifier = ConvertLayout.MODIFIER_SHIFT;
        } else if (!isShift && isCapsLock) {
            this.modifier = ConvertLayout.MODIFIER_CAPS;
        } else if (!isShift && !isCapsLock) {
            this.modifier = ConvertLayout.MODIFIER_NONE;
        } else if (isShift && isCapsLock) {
            this.modifier = ConvertLayout.MODIFIER_CAPS_SHIFT;
        }
    }

    public ConvertSequence(String sequence) {

        this.sequence = sequence;

    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public char charAt(int index) {

        return sequence.charAt(index);

    }


    @Override
    public int length() {
        return sequence.length();

    }


    @NonNull
    @Override
    public CharSequence subSequence(int arg0, int arg1) {

        return sequence.substring(arg0, arg1);
    }

}
