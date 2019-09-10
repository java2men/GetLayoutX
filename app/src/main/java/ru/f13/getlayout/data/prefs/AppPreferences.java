package ru.f13.getlayout.data.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;

import ru.f13.getlayout.data.model.ConversionOptions;
import ru.f13.getlayout.util.convert.ConvertLayout;

/**
 * Класс описывает настройки приложения
 */
public class AppPreferences {

    public static String PREF_KEY_SAVE_DATA = "pref_key_save_data";
    public static String PREF_KEY_NOT_KEYBOARD_START = "pref_key_not_keyboard_start";
    public static String PREF_KEY_EXIT_ALERT = "pref_key_exit_alert";
    public static String PREF_KEY_DARK_THEME = "pref_key_dark_theme";
    public static String PREF_KEY_FONT_SIZE = "pref_key_font_size";
    public static String PREF_KEY_THEME = "pref_key_theme";
    public static String PREF_KEY_ABOUT = "pref_key_about";
    public static String PREF_KEY_CONVERSION_OPTIONS = "pref_key_conversion_options";

    private static AppPreferences sInstance;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    /**
     * Название файла настроек приложения
     */
    private static final String APP_PREFERENCES = "app_preferences";

    private final MutableLiveData<Boolean> mIsPreferencesCreated = new MutableLiveData<>();

    /**
     * Конструктор
     * @param context контекст
     */
    private AppPreferences(Context context) {

        sharedPreferences = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        if (sharedPreferences != null) {
            editor = sharedPreferences.edit();
            editor.apply();
            mIsPreferencesCreated.setValue(true);
        }

    }

    /**
     * Получить инстанс настроек приложегия
     * @param context контекст
     * @return объект {@link AppPreferences}
     */
    public static AppPreferences getInstance(Context context) {

        if (sInstance == null) {
            synchronized (AppPreferences.class) {
                if (sInstance == null) {
                    sInstance = new AppPreferences(context);
                }
            }
        }
        return sInstance;
    }

    /**
     * Проверить созданы ли файлы настроек приложения
     * @return объект {@link LiveData}
     */
    public LiveData<Boolean> getAppPreferencesCreated() {
        return mIsPreferencesCreated;
    }

    /**
     * Получить настройки для сохранения данных
     * @return объект {@link SharedPreferenceBooleanLiveData}
     */
    public SharedPreferenceBooleanLiveData getSaveData() {
        return new SharedPreferenceBooleanLiveData(sharedPreferences, PREF_KEY_SAVE_DATA, true);
    }

    /**
     * Установить настройки сохранения данных
     * @param value true - разрешить сохранять данные, false - запретить сохранять данные
     */
    public void setSaveData(boolean value) {
        editor.putBoolean(PREF_KEY_SAVE_DATA, value);
        editor.commit();
    }

    /**
     * Удалить настройки сохранения данных
     */
    public void deleteSaveData(){
        editor.remove(PREF_KEY_SAVE_DATA);
        editor.commit();
    }

    /**
     * Проверить пустые ли настройки сохранения данных
     * @return true - пусто, false - непусто
     */
    public boolean isEmptySaveData() {
        return !sharedPreferences.contains(PREF_KEY_SAVE_DATA);
    }

    /**
     * Получить настройку уведомления о выходе
     * @return объект {@link SharedPreferenceBooleanLiveData}
     */
    public SharedPreferenceBooleanLiveData getNotExitAlert() {
        return new SharedPreferenceBooleanLiveData(sharedPreferences, PREF_KEY_EXIT_ALERT, true);
    }

    /**
     * Установить настройку уведомления о выходе
     * @param value true - не уведомлять, false - уведомлять
     */
    public void setNotExitAlert(boolean value) {
        editor.putBoolean(PREF_KEY_EXIT_ALERT, value);
        editor.commit();
    }

    /**
     * Удалить настройку уведомлений о выходе
     */
    public void deleteNotExitAlert(){
        editor.remove(PREF_KEY_EXIT_ALERT);
        editor.commit();
    }

    /**
     * Получить настройку "использовать темную тему"
     * @return объект {@link SharedPreferenceBooleanLiveData}
     */
    public SharedPreferenceBooleanLiveData getDarkTheme() {
        return new SharedPreferenceBooleanLiveData(sharedPreferences, PREF_KEY_DARK_THEME, false);
    }

    /**
     * Получить значение настройки "использовать темную тему"
     * @return true - используется темная тема, false - не используется темная тема
     */
    public boolean getDarkThemeValue() {
        return sharedPreferences.getBoolean(PREF_KEY_DARK_THEME, false);
    }

    /**
     * Установить настройку "использовать темную тему"
     * @param value true - не уведомлять, false - уведомлять
     */
    public void setDarkTheme(boolean value) {
        editor.putBoolean(PREF_KEY_DARK_THEME, value);
        editor.commit();
    }

    /**
     * Удалить настройку "использовать темную тему"
     */
    public void deleteDarkTheme(){
        editor.remove(PREF_KEY_DARK_THEME);
        editor.commit();
    }





    /**
     * Получить из настроек параметры конвертации
     * @return объект {@link SharedPreferenceConversionOptionsLiveData}
     */
    public SharedPreferenceConversionOptionsLiveData getConversionOptions() {

        boolean isSave = sharedPreferences.getBoolean(PREF_KEY_SAVE_DATA, false);

        return new SharedPreferenceConversionOptionsLiveData(
                sharedPreferences,
                PREF_KEY_CONVERSION_OPTIONS,
                new ConversionOptions(isSave, ConvertLayout.CODE_RU, ConvertLayout.CODE_EN)
        );

    }

    /**
     * Установить в настройки параметры конвертаций
     * @param conversionOptions модель параметров конвертаций
     */
    public void setConversionOptions(ConversionOptions conversionOptions) {
        Gson gson = new Gson();
        String json = null;
        if (conversionOptions != null) {
            json = gson.toJson(conversionOptions);
        }
        editor.putString(PREF_KEY_CONVERSION_OPTIONS, json);
        editor.commit();
    }

    /**
     * Удалить параметры конвертаций
     */
    public void deleteConversionOptions(){
        setConversionOptions(null);
        editor.remove(PREF_KEY_CONVERSION_OPTIONS);
        editor.commit();
    }

}
