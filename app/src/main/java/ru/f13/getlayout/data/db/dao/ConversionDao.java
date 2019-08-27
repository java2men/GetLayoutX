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

package ru.f13.getlayout.data.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import ru.f13.getlayout.data.db.entity.ConversionEntity;

@Dao
public interface ConversionDao {

    @Query("select * from conversions")
    LiveData<List<ConversionEntity>> loadAllConversions();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ConversionEntity> conversions);

    @Query("select * from conversions where id = :conversionId")
    LiveData<ConversionEntity> loadConversion(int conversionId);

    @Query("delete from conversions")
    void deleteAllConversions();

    @Query("delete from conversions where id = :conversionId")
    void deleteConversion(int conversionId);

}
