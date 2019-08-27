package ru.f13.getlayout.ui.adapters;

public interface OnDeleteConversionListener {

    /**
     * Уведомить об удалении конвертации из базы данных
     * @param id id конвертации
     * @param dateText дата в текстовом формате
     */
    void onDelete(int id, String dateText);

}
