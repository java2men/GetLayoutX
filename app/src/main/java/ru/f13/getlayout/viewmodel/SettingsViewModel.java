package ru.f13.getlayout.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import ru.f13.getlayout.data.DataRepository;
import ru.f13.getlayout.GLApp;

public class SettingsViewModel extends AndroidViewModel {

    private final DataRepository mRepository;

    /**
     * Конструтктор
     * @param application объект {@link Application}
     */
    public SettingsViewModel(Application application) {
        super(application);

        mRepository = ((GLApp) application).getRepository();

    }

    /**
     * Получить настройку "сохранение данных" с уведомлением о получении
     * @return объект {@link LiveData}
     */
    public LiveData<Boolean> getSaveData() {
        return mRepository.getSaveData();
    }

    /**
     * Установить настройку "сохранение данных"
     * @param value true - сохранять данные, false - не сохранять данные
     */
    public void setSaveData(boolean value) {
        mRepository.setSaveData(value);
    }

    /**
     * Получить значение настройки "не уведомлять о выходе" с уведомлением о получении
     * @return объект {@link LiveData}
     */
    public LiveData<Boolean> getNotExitAlert() {
        return mRepository.getNotExitAlert();
    }

    /**
     * Установить значение настройки "не уведомлять о выходе"
     * @param value true - не уведомлять о выходе, false - уведомлять о выходе
     */
    public void setNotExitAlert(boolean value) {
        mRepository.setNotExitAlert(value);
    }

    /**
     * Получить значение настройки "использовать темную тему" с уведомлением о получении
     * @return объект {@link LiveData},
     * true - использовать темную тему, false - использовать светлую тему
     */
    public LiveData<Boolean> getDarkTheme() {
        return mRepository.getDarkTheme();
    }

    /**
     * Установить значение настройки "использовать темную тему"
     * @param value true - использовать темную тему, false - использовать светлую тему
     */
    public void setDarkTheme(boolean value) {
        mRepository.setDarkTheme(value);
    }

    /**
     * Получить настройку "подсказки клавиатуры" с уведомлением о получении
     * @return объект {@link LiveData},
     * true - подсказывать клавиаутуре, false - не подсказывать клавиатуре
     */
    public LiveData<Boolean> getSuggestions() {
        return mRepository.getSuggestions();
    }

    /**
     * Установить настройку "подсказки клавиатуры"
     * @param value true - подсказывать клавиаутуре, false - не подсказывать клавиатуре
     */
    public void setSuggestions(boolean value) {
        mRepository.setSuggestions(value);
    }

}
