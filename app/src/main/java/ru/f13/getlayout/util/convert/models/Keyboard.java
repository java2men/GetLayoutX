package ru.f13.getlayout.util.convert.models;

import java.util.ArrayList;

/**
 * Created by IALozhnikov on 08.09.2016.
 */
public class Keyboard {

    private String locale;
    private Version version;
    private Names names;
    private Settings settings;
    private KeyMap keyMap;

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public Version getVersion() {
        return version;
    }

    public void setVersion(Version version) {
        this.version = version;
    }

    public Names getNames() {
        return names;
    }

    public void setNames(Names names) {
        this.names = names;
    }

    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    public KeyMap getKeyMap() {
        return keyMap;
    }

    public void setKeyMap(KeyMap keyMap) {
        this.keyMap = keyMap;
    }
}
