package ru.f13.getlayout.util.convert;

import androidx.annotation.NonNull;

import java.util.List;

public class ModifierSequence implements CharSequence {

    private String sequence = "";
    private String modifier = ConvertLayout.MODIFIER_NONE;

    public ModifierSequence(String sequence, String modifier) {
        this.sequence = sequence;
        this.modifier = modifier;
    }

    public ModifierSequence(String sequence, boolean isShift, boolean isCapsLock) {
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

    public ModifierSequence(String sequence) {
        this.sequence = sequence;
    }

    public String getSequence() {

        return sequence;
    }

    public void setSequence(String sequence){

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
