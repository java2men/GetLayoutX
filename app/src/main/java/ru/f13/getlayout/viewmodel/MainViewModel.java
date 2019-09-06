package ru.f13.getlayout.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import ru.f13.getlayout.data.DataRepository;
import ru.f13.getlayout.GLApp;

public class MainViewModel extends AndroidViewModel {

    private final DataRepository mRepository;

    /**
     * Конструтктор
     * @param application объект {@link Application}
     */
    public MainViewModel(Application application) {
        super(application);

        mRepository = ((GLApp) application).getRepository();

    }

    /**
     * Удалить из базы данных все конвертации
     */
    public void deleteAllConversions() {
        mRepository.deleteAllConversions();
    }

    /**
     * Получить значене настройки "без уведомления о выходе" с уведомлением о получении
     * @return объект {@link LiveData}
     */
    public LiveData<Boolean> getNotExitAlert() {
        return mRepository.getNotExitAlert();
    }

    /**
     * Получить значене настройки "использовать темную тему" с уведомлением о получении
     * @return объект {@link LiveData}
     */
    public LiveData<Boolean> getDarkTheme() {
        return mRepository.getDarkTheme();
    }

    /**
     * Получить значене настройки "использовать темную тему"
     * @return true - используется темная тема, false - не используется темная тема
     */
    public boolean getDarkThemeValue() {
        return mRepository.getDarkThemeValue();
    }

}
