package ru.f13.getlayout.util.convert.sequence;

import java.util.ArrayList;
import java.util.List;

/**
 * Builder для последовательности с модификаторами {@link ModifierSequence}
 */
public class ModifierSequenceBuilder {

    private List<ModifierSequence> sequenceList;

    /**
     * Конструктор
     */
    public ModifierSequenceBuilder() {
        sequenceList = new ArrayList<>();
    }

    /**
     * Добавить последовательность
     * @param modifierSequence последовательность {@link ModifierSequence}
     * @return true - успешно добавлено, false - неуспешно
     */
    public boolean add(ModifierSequence modifierSequence) {
        return sequenceList.add(modifierSequence);
    }

    /**
     * Добавить последовательность начиная с index
     * @param index начиная
     * @param sequence последовательность
     */
    public void add(int index, ModifierSequence sequence) {
        sequenceList.add(index, sequence);
    }

    /**
     * Добавить последовательность через {@link String}
     * @param str последовательность через {@link String}
     * @param modifier модификатор
     */
    public void add(String str, String modifier) {

        if (str == null || modifier == null) {
            return;
        }

        char[] chars = str.toCharArray();

        for (int i = 0; i < chars.length; i++) {
            sequenceList.add(new ModifierSequence(String.valueOf(chars[i]), modifier));
        }

    }

    /**
     * Добавить последовательность через {@link String}
     * @param str последовательность через {@link String}
     * @param isShift true - использовать "shift", false - не использовать
     * @param isCapsLock true - использовать "caps lock", false - не использовать
     */
    public void add(String str, boolean isShift, boolean isCapsLock) {

        if (str == null) {
            return;
        }

        char[] chars = str.toCharArray();

        for (int i = 0; i < chars.length; i++) {
            sequenceList.add(new ModifierSequence(String.valueOf(chars[i]), isShift, isCapsLock));
        }

    }

    /**
     * Удалить {@link ModifierSequence} по индексу
     * @param index индекс
     * @return удаленный {@link ModifierSequence}
     */
    public ModifierSequence remove(int index) {
        return sequenceList.remove(index);
    }

    /**
     * Удалить диапазон последовательности
     * @param start стартовый индекс диапазона
     * @param end конечный индекс диапазона
     */
    public void remove(int start, int end) {
        sequenceList.subList(start, end).clear();
    }

    /**
     * Очистить {@link ModifierSequenceBuilder}
     */
    public void clear() {
        sequenceList.clear();
    }

    /**
     * Получить размер
     * @return размер {@link ModifierSequenceBuilder}
     */
    public int size() {
        return sequenceList.size();
    }

    /**
     * Проверить на пустоту
     * @return true - пусто, false  - непусто
     */
    public boolean isEmpty() {
        return sequenceList.isEmpty();
    }

    /**
     * Получить последовательность {@link ModifierSequence} по индексу
     * @param index индекс
     * @return получить последовательность {@link ModifierSequence}
     */
    public ModifierSequence get(int index) {
        return sequenceList.get(index);
    }

    /**
     * Установить {@link ModifierSequence} по индексу
     * @param index индекс
     * @param sequence последоватьельность {@link ModifierSequence}
     * @return получить установленную последовательность {@link ModifierSequence}
     */
    public ModifierSequence set(int index, ModifierSequence sequence) {
        return sequenceList.set(index, sequence);
    }
}
