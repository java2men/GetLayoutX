package ru.f13.getlayout.data.prefs;

import android.content.SharedPreferences;

import androidx.lifecycle.MutableLiveData;

/**
 * LiveData для настроек
 * @param <T> тип настроек
 */
abstract class SharedPreferenceLiveData<T> extends MutableLiveData<T> {

    protected SharedPreferences sharedPrefs;
    private String key;
    private T defValue;

    /**
     * Конструктор
     * @param prefs настройка
     * @param key ключ
     * @param defValue дефолтное значение
     */
    SharedPreferenceLiveData(SharedPreferences prefs, String key, T defValue) {
        this.sharedPrefs = prefs;
        this.key = key;
        this.defValue = defValue;

        setValue(getValueFromPreferences(key, defValue));
    }

    private SharedPreferences.OnSharedPreferenceChangeListener preferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            if (SharedPreferenceLiveData.this.key.equals(key)) {
                setValue(getValueFromPreferences(key, defValue));
            }
        }
    };

    /**
     * Получить значение по ключу
     * @param key ключ
     * @param defValue дефолтное значение, если не найден основное значение
     * @return значение
     */
    abstract T getValueFromPreferences(String key, T defValue);

    @Override
    protected void onActive() {
        super.onActive();
        sharedPrefs.registerOnSharedPreferenceChangeListener(preferenceChangeListener);
    }

    @Override
    protected void onInactive() {
        sharedPrefs.unregisterOnSharedPreferenceChangeListener(preferenceChangeListener);
        super.onInactive();
    }

}
