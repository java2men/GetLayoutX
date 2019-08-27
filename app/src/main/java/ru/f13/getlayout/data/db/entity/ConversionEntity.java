package ru.f13.getlayout.data.db.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import ru.f13.getlayout.data.model.Conversion;

@Entity(tableName = "conversions")
public class ConversionEntity implements Conversion {

    private static String DATE_FORMAT = "dd MMM yyyy HH:mm:ss";

    @PrimaryKey(autoGenerate = true)
    private Integer id;
    private Long date;
    private String inputText;
    private String resultText;

    public ConversionEntity() {
        date = Calendar.getInstance().getTime().getTime();

    }

    /**
     * Получить id конвертации
     * @return id конвертации
     */
    @Override
    public Integer getId() {
        return id;
    }

    /**
     * Установить id конвертации
     * @param id id конвертации
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Получить дату создания конвертации в милисекундах
     * @return дата в милисекундах
     */
    @Override
    public Long getDate() {
        return date;
    }

    /**
     * Установить дату создания конвертации в милисекундах
     * @param date дата в милисекундах
     */
    public void setDate(Long date) {
        this.date = date;
    }

    /**
     * Получить текстовое представление даты создания конвертации
     * @return дата в текстовом формате
     */
    @Override
    public String getDateText() {

        DateFormat dateFormat =
                new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());

        return dateFormat.format(date);
    }

    /**
     * Получить исходный текст
     * @return исходный текст
     */
    @Override
    public String getInputText() {
        return inputText;
    }

    /**
     * Установить исходный текст
     * @param text исходный текст
     */
    public void setInputText(String text) {
        this.inputText = text;
    }

    /**
     * Получить текст конвертации
     * @return текст конвертации
     */
    @Override
    public String getResultText() {
        return resultText;
    }

    /**
     * Установить текст конвертации
     * @param text текст конвертации
     */
    public void setResultText(String text) {
        this.resultText = text;
    }

}
