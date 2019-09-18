package ru.f13.getlayout.util.convert.models;

/**
 * Created by IALozhnikov on 19.09.2016.
 */
public class UnionMap {

    private Map inputMap;
    private Map resultMap;

    public UnionMap(Map inputMap, Map resultMap) {
        this.inputMap = inputMap;
        this.resultMap = resultMap;
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
