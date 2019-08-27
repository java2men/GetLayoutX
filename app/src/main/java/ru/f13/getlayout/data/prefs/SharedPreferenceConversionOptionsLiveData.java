package ru.f13.getlayout.data.prefs;

import android.content.SharedPreferences;

import com.google.gson.Gson;

import ru.f13.getlayout.data.model.ConversionOptions;

/**
 * LiveData для ConversionOptions настройки
 */
public class SharedPreferenceConversionOptionsLiveData extends SharedPreferenceLiveData<ConversionOptions>{

    public SharedPreferenceConversionOptionsLiveData(SharedPreferences prefs, String key, ConversionOptions defValue) {
        super(prefs, key, defValue);
    }

    @Override
    ConversionOptions getValueFromPreferences(String key, ConversionOptions defValue) {

        ConversionOptions conversionOptions = null;

        try {
            Gson gson = new Gson();
            String json = sharedPrefs.getString(key, "");
            conversionOptions = gson.fromJson(json, ConversionOptions.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (conversionOptions == null) {
            conversionOptions = defValue;
        }

        return conversionOptions;

    }
}
