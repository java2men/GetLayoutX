package ru.f13.getlayout.util.convert;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ModifierSequenceBuilder {

    private List<ModifierSequence> sequenceList;

    public ModifierSequenceBuilder() {
        sequenceList = new ArrayList<>();
    }

    public boolean add(ModifierSequence modifierSequence) {
        return sequenceList.add(modifierSequence);
    }

    public void add(int index, ModifierSequence element) {
        sequenceList.add(index, element);
    }

    public void add(String str, String modifier) {

        if (str == null || modifier == null) {
            return;
        }

        char[] chars = str.toCharArray();

        for (int i = 0; i < chars.length; i++) {
            sequenceList.add(new ModifierSequence(String.valueOf(chars[i]), modifier));
        }

    }

    public void add(String str, boolean isShift, boolean isCapsLock) {

        if (str == null) {
            return;
        }

        char[] chars = str.toCharArray();

        for (int i = 0; i < chars.length; i++) {
            sequenceList.add(new ModifierSequence(String.valueOf(chars[i]), isShift, isCapsLock));
        }

    }

    public ModifierSequence remove(int index) {
        return sequenceList.remove(index);
    }

    public void remove(int start, int end) {
        sequenceList.subList(start, end).clear();
    }

    public void clear() {
        sequenceList.clear();
    }

    public int size() {
        return sequenceList.size();
    }

    public boolean isEmpty() {
        return sequenceList.isEmpty();
    }

    public ModifierSequence get(int index) {
        return sequenceList.get(index);
    }

    public ModifierSequence set(int index, ModifierSequence element) {
        return sequenceList.set(index, element);
    }
}
