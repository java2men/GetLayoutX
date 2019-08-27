package ru.f13.getlayout.util.convert.models;


import java.util.List;

/**
 * Created by IALozhnikov on 08.09.2016.
 */
public class KeyMap {

    private String modifiers;
    private List<Map> maps;

    public String getModifiers() {
        return modifiers;
    }

    public void setModifiers(String modifiers) {
        this.modifiers = modifiers;
    }

    public List<Map> getMaps() {
        return maps;
    }

    public void setMaps(List<Map> maps) {
        this.maps = maps;
    }
}
