/*
 * Copyright 2017, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ru.f13.getlayout.data.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ru.f13.getlayout.data.db.dao.ConversionDao;
import ru.f13.getlayout.data.db.entity.ConversionEntity;

/**
 * Описать базу данных
 */
@Database(entities = {ConversionEntity.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase sInstance;

    /**
     * Имя база данных
     */
    public static final String DATABASE_NAME = "get-layout-db";

    public abstract ConversionDao conversionDao();

    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();

    /**
     * Получить инстанс базы данных
     * @param context контекст
     * @return объект {@link AppDatabase}
     */
    public static AppDatabase getInstance(final Context context) {
        if (sInstance == null) {
            synchronized (AppDatabase.class) {
                if (sInstance == null) {
                    sInstance = buildDatabase(context.getApplicationContext());
                    sInstance.updateDatabaseCreated(context.getApplicationContext());
                }
            }
        }
        return sInstance;
    }

    /**
     * Создать базу данных. База данных SQLite создается только при первом обращении к ней.
     * @param appContext контекст
     * @return объект {@link AppDatabase}
     */
    private static AppDatabase buildDatabase(final Context appContext) {

        return Room.databaseBuilder(appContext, AppDatabase.class, DATABASE_NAME)
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        final ExecutorService executorService = Executors.newSingleThreadExecutor();

                        executorService.execute(new Runnable() {
                            @Override
                            public void run() {
                                //Создать базу данных
                                AppDatabase database = AppDatabase.getInstance(appContext);
                                //уведомить о создании базы данных
                                database.setDatabaseCreated();
                                //выключить executorService
                                executorService.shutdown();
                            }
                        });

                    }
                })
            //.addMigrations(MIGRATION_1_2)
            .build();
    }

    /**
     * Проверить создана ли база данных
     */
    private void updateDatabaseCreated(final Context context) {
        if (context.getDatabasePath(DATABASE_NAME).exists()) {
            setDatabaseCreated();
        }
    }

    /**
     * Подвердить создание базы данных
     */
    private void setDatabaseCreated(){
        mIsDatabaseCreated.postValue(true);
    }

    /**
     * Получить проверку создана ли база данных
     * @return возвращает объект {@link LiveData}
     */
    public LiveData<Boolean> getDatabaseCreated() {
        return mIsDatabaseCreated;
    }

}
