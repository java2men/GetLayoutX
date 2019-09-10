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

package ru.f13.getlayout;

import android.app.Application;

import ru.f13.getlayout.data.DataRepository;
import ru.f13.getlayout.data.db.AppDatabase;
import ru.f13.getlayout.data.prefs.AppPreferences;

public class GLApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    /**
     * Получить объект базы данных
     * @return объект {@link AppDatabase}
     */
    public AppDatabase getDatabase() {
        return AppDatabase.getInstance(this);
    }

    /**
     * Получить объект настроек {@link AppPreferences}
     * @return объект {@link AppPreferences}
     */
    public AppPreferences getPreferences() {
        return AppPreferences.getInstance(this);
    }

    /**
     * Получить объект репозиторий {@link DataRepository}
     * @return объект {@link DataRepository}
     */
    public DataRepository getRepository() {
        return DataRepository.getInstance(getDatabase(), getPreferences());
    }
}
