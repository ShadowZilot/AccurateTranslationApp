package com.human_developing_soft.accurate_translation.translation.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface LanguageDao {
    @Query("SELECT * FROM languages")
    List<LanguageEntity> allLanguage();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveLanguage(LanguageEntity entity);

    @Query("DELETE FROM languages")
    void deleteAll();
}
