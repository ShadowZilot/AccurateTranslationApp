package com.human_developing_soft.accurate_translation.translation.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {LanguageEntity.class}, version = 1)
public abstract class LanguageDatabase extends RoomDatabase {
    public abstract LanguageDao languageDao();
}
