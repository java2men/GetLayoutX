package ru.f13.getlayout.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import java.util.List;

import ru.f13.getlayout.data.db.AppDatabase;
import ru.f13.getlayout.data.db.entity.ConversionEntity;
import ru.f13.getlayout.data.model.ConversionOptions;
import ru.f13.getlayout.data.prefs.AppPreferences;
import ru.f13.getlayout.data.prefs.SharedPreferenceConversionOptionsLiveData;

/**
 * Репозиторий для работы с базой данных и настройками приложения
 */
public class DataRepository {

    private static DataRepository sInstance;

    private final AppDatabase mDatabase;
    private MediatorLiveData<List<ConversionEntity>> mObservableConversions;

    private AppPreferences mAppPreferences;

    /**
     * Конструктор
     * @param database база данных
     * @param appPreferences настройки приложений
     */
    private DataRepository(final AppDatabase database, AppPreferences appPreferences) {
        mDatabase = database;
        mObservableConversions = new MediatorLiveData<>();

        mObservableConversions.addSource(
                mDatabase.conversionDao().loadAllConversions(),
                new Observer<List<ConversionEntity>>() {
                    @Override
                    public void onChanged(List<ConversionEntity> conversionEntities) {
                        if (mDatabase.getDatabaseCreated().getValue() != null) {
                            mObservableConversions.postValue(conversionEntities);
                        }
                    }
                });

        mAppPreferences = appPreferences;
    }

    /**
     * Получить инстанс репозитория
     * @param database база данных
     * @param appPreferences настройки приложений
     * @return объект {@link DataRepository}
     */
    public static DataRepository getInstance(final AppDatabase database, AppPreferences appPreferences) {
        if (sInstance == null) {
            synchronized (DataRepository.class) {
                if (sInstance == null) {
                    sInstance = new DataRepository(database, appPreferences);
                }
            }
        }
        return sInstance;
    }

    /**
     * Получить список конвертаций из базы данных и получить уведомление с изменением этого списка
     * @return объект {@link LiveData}
     */
    public LiveData<List<ConversionEntity>> getConversions() {
        return mObservableConversions;
    }

    /**
     * Загрузить в базу данных конвертацию по id и получить уведомление о загрузке ввиде объекта конвертации
     * @param conversionId id конвертации
     * @return объект {@link LiveData}
     */
    public LiveData<ConversionEntity> loadConversion(final int conversionId) {
        return mDatabase.conversionDao().loadConversion(conversionId);
    }

    /**
     * Вставить список конвертаций в базу данных
     * @param conversions список конвертаций
     */
    public void insertConversions(final List<ConversionEntity> conversions) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mDatabase.conversionDao().insertAll(conversions);
            }
        }).start();
    }

    /**
     * Удалить все конвертации из базы данных
     */
    public void deleteAllConversions() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mDatabase.conversionDao().deleteAllConversions();
            }
        }).start();
    }

    /**
     * Удалить из базы данных конвертацию по id
     * @param id id конвертации, которая будет удалена
     */
    public void deleteConversion(final int id) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mDatabase.conversionDao().deleteConversion(id);
            }
        }).start();
    }

    /**
     * Получить настройку "сохранение данных" с уведомлением о получении
     * @return объект {@link LiveData}
     */
    public LiveData<Boolean> getSaveData() {
        return mAppPreferences.getSaveData();
    }

    /**
     * Установить настройку "сохранение данных"
     * @param value true - сохранять данные, false - не сохранять данные
     */
    public void setSaveData(boolean value) {
        mAppPreferences.setSaveData(value);
    }

    /**
     * Получить настройку "не уведомлять о выходе" с уведомлением о получении
     * @return объект {@link LiveData}
     */
    public LiveData<Boolean> getNotExitAlert() {
        return mAppPreferences.getNotExitAlert();
    }

    /**
     * Установить настройку "не уведомлять о выходе"
     * @param value true - не уведомлять о выходе, false - уведомлять о выходе
     */
    public void setNotExitAlert(boolean value) {
        mAppPreferences.setNotExitAlert(value);
    }

    /**
     * Получить настройки "опции конвертаций" с уведомлением о получении
     * @return объект {@link LiveData}
     */
    public SharedPreferenceConversionOptionsLiveData getConversionOptions() {
        return mAppPreferences.getConversionOptions();
    }

    /**
     * Установить настройки "опции конвертации"
     * @param conversionOptions опции конвертации
     */
    public void setConversionOptions(ConversionOptions conversionOptions) {
        mAppPreferences.setConversionOptions(conversionOptions);
    }

    /**
     * Удалить настройки "опции конвертации"
     */
    public void deleteConversionOptions() {
        mAppPreferences.deleteConversionOptions();
    }
}
