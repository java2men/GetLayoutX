package ru.f13.getlayout.data.prefs;

import android.content.SharedPreferences;

/**
 * LiveData для булевой настройки
 */
class SharedPreferenceBooleanLiveData extends SharedPreferenceLiveData<Boolean>{

    SharedPreferenceBooleanLiveData(SharedPreferences prefs, String key, Boolean defValue) {
        super(prefs, key, defValue);
    }

    @Override
    Boolean getValueFromPreferences(String key, Boolean defValue) {
        return sharedPrefs.getBoolean(key, defValue);
    }
}
