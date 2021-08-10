package com.human_developing_soft.accurate_translation.translation.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class LanguagesDatabaseHelper extends SQLiteOpenHelper {

    public LanguagesDatabaseHelper(@Nullable Context context) {
        super(context, "Languages.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String dataSchema = new LanguagesSchema().schema();
        db.execSQL("CREATE TABLE " + dataSchema);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
}
