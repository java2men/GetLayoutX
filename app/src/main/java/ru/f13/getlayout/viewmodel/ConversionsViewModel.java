package ru.f13.getlayout.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import java.util.ArrayList;
import java.util.List;

import ru.f13.getlayout.GLApp;
import ru.f13.getlayout.data.DataRepository;
import ru.f13.getlayout.data.db.entity.ConversionEntity;
import ru.f13.getlayout.data.model.ConversionOptions;

public class ConversionsViewModel extends AndroidViewModel {

    private final DataRepository mRepository;

    /**
     * Наблюдатель за списком конвертаций
     */
    private final MediatorLiveData<List<ConversionEntity>> mObservableConversions;

    /**
     * Конструтктор
     * @param application объект {@link Application}
     */
    public ConversionsViewModel(Application application) {
        super(application);

        mObservableConversions = new MediatorLiveData<>();
        //по умолчанию пустой, пока нет данных
        mObservableConversions.setValue(null);

        mRepository = ((GLApp) application).getRepository();
        LiveData<List<ConversionEntity>> conversions = mRepository.getConversions();

        //наблюдать за изменениями конвертаций из базы данных
        mObservableConversions.addSource(conversions, new Observer<List<ConversionEntity>>() {
            @Override
            public void onChanged(List<ConversionEntity> conversionEntities) {
                mObservableConversions.setValue(conversionEntities);
            }
        });

    }

    /**
     * Получить список конвертаций из баззы данных с уведомлением об изменениях
     * @return объект {@link LiveData}
     */
    public LiveData<List<ConversionEntity>> getConversions() {
        return mObservableConversions;
    }

    /**
     * Добавить текст конвертации
     * @param inputText исходный текст
     * @param resultText результирующий текст
     */
    public void addConversionText(
            String inputText,
            String resultText
    ) {

        ConversionEntity conversion = new ConversionEntity();

        conversion.setInputText(inputText);
        conversion.setResultText(resultText);

        List<ConversionEntity> conversions = new ArrayList<>(0);
        conversions.add(conversion);
        mRepository.insertConversions(conversions);

    }


    /**
     * Удалить все конвертации из базы ланных
     */
    public void deleteAllConversions() {
        mRepository.deleteAllConversions();
    }

    /**
     * Удалить конвертацию по id
     * @param id id конвертации
     */
    public void deleteConversion(int id) {
        mRepository.deleteConversion(id);
    }

    /**
     * Получить значение настройки "сохранение данных" с уведомлением о получении
     * @return true - данные сохранять, false - данные не сохранять
     */
    public LiveData<Boolean> getSaveData() {
        return mRepository.getSaveData();
    }

    /**
     * Получить значение настройки "опции конвертации" с уведомлением о получении
     * @return объект {@link LiveData}
     */
    public LiveData<ConversionOptions> getConversionOptions() {
        return mRepository.getConversionOptions();
    }

    /**
     * Установить значение настройки "опции конвертации"
     * @param conversionOptions данные настройки "опции конвертации"
     */
    public void setConversionOptions(ConversionOptions conversionOptions) {
        mRepository.setConversionOptions(conversionOptions);
    }

    /**
     * Удалить настройки "опции конвертации"
     */
    public void deleteConversionOptions() {
        mRepository.deleteConversionOptions();
    }

}
