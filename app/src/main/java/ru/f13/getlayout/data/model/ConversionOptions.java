package ru.f13.getlayout.data.model;

/**
 * Класс описывает настройки конвертации
 */
public class ConversionOptions {

    private boolean isSaveData;
    private String inputCode;
    private String resultCode;

    /**
     * Конструктор
     * @param isSaveData true - сохранение данных разрешено, false - сохранение данных запрещено
     * @param inputCode исходный код раскладки
     * @param resultCode результирующий код раскладки
     */
    public ConversionOptions(boolean isSaveData, String inputCode, String resultCode) {
        this.isSaveData = isSaveData;
        this.inputCode = inputCode;
        this.resultCode = resultCode;
    }

    /**
     * Проверить разрешение на сохранение данных
     * @return true - данные сохранять разрешено, false - данные сохранять запрещенно
     */
    public boolean isSaveData() {
        return isSaveData;
    }

    /**
     * Установить разрешение на сохранение данных
     * @param saveData true - данные сохранять разрешенно, false - данные сохранять запрещенно
     */
    public void setSaveData(boolean saveData) {
        isSaveData = saveData;
    }

    /**
     * Получить исходный код раскладки
     * @return код раскладки
     */
    public String getInputCode() {
        return inputCode;
    }

    /**
     * Установить исходный код раскладки
     * @param inputCode исходный код расскладки
     */
    public void setInputCode(String inputCode) {
        this.inputCode = inputCode;
    }

    /**
     * Получить результирующий код расскладки
     * @return результирующий код расскладки
     */
    public String getResultCode() {
        return resultCode;
    }

    /**
     * Установить результирующий код расскладки
     * @param resultCode результирующий код расскладки
     */
    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

}
