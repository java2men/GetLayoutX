package ru.f13.getlayout.util.convert.sequence;

import androidx.annotation.NonNull;

import ru.f13.getlayout.util.convert.ConvertLayout;

/**
 * Последовательность с модификатором
 */
public class ModifierSequence implements CharSequence {

    private String sequence = "";
    private String modifier = ConvertLayout.MODIFIER_NONE;

    /**
     * Конструктор
     * @param sequence {@link String} последовательность
     * @param modifier модификатор
     */
    public ModifierSequence(String sequence, String modifier) {
        this.sequence = sequence;
        this.modifier = modifier;
    }

    /**
     * Конструктор
     * @param sequence {@link String} последовательность
     * @param isShift true - используется "shift", false - не используется
     * @param isCapsLock true - используется "capsLock", false - не используется
     */
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

    /**
     * Конструктор
     * @param sequence {@link String} последовательность
     */
    public ModifierSequence(String sequence) {
        this.sequence = sequence;
    }

    /**
     * Получить {@link String} последовательность
     * @return {@link String} последовательность
     */
    public String getSequence() {

        return sequence;
    }

    /**
     * Установить {@link String} последовательность
     * @param sequence {@link String} последовательность
     */
    public void setSequence(String sequence){

        this.sequence = sequence;

    }

    /**
     * Получить модификатор
     * @return модификатор из {@link ConvertLayout}
     */
    public String getModifier() {
        return modifier;
    }

    /**
     * Установить модификатор
     * @param modifier модификатор из {@link ConvertLayout}
     */
    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    /**
     * Получить char значение по индексу
     * @param index индекс
     * @return char значение
     */
    public char charAt(int index) {
        return sequence.charAt(index);
    }

    /**
     * Длина последовательности
     * @return значение длины последовательности
     */
    @Override
    public int length() {
        return sequence.length();
    }

    /**
     * Получить подстроку
     * @param start стартовый индекс
     * @param end конечный индекс
     * @return символьная последовательность {@link CharSequence}
     */
    @NonNull
    @Override
    public CharSequence subSequence(int start, int end) {
        return sequence.substring(start, end);
    }

}
