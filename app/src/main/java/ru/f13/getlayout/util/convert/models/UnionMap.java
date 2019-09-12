package ru.f13.getlayout.util.convert.models;

/**
 * Created by IALozhnikov on 19.09.2016.
 */
public class UnionMap {

    private String modifier;
    private Map inputMap;
    private Map resultMap;

    public UnionMap(Map inputMap, Map resultMap) {
        this.inputMap = inputMap;
        this.resultMap = resultMap;
    }

    public UnionMap(String modifier, Map inputMap, Map resultMap) {
        this.modifier = modifier;
        this.inputMap = inputMap;
        this.resultMap = resultMap;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public Map getInputMap() {
        return inputMap;
    }

    public void setInputMap(Map inputMap) {
        this.inputMap = inputMap;
    }

    public Map getResultMap() {
        return resultMap;
    }

    public void setResultMap(Map resultMap) {
        this.resultMap = resultMap;
    }
}
