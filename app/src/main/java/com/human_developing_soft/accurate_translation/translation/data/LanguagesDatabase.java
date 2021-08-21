package com.human_developing_soft.accurate_translation.translation.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ibm.watson.language_translator.v3.model.Language;

import java.util.ArrayList;
import java.util.List;

public class LanguagesDatabase implements LanguageStorage {
    private static LanguageStorage sInstance;
    private final Context mContext;
    private final SQLiteDatabase mDatabase;
    private final LanguagesSchema mSchema;

    private LanguagesDatabase(Context pContext) {
        mContext = pContext.getApplicationContext();
        mDatabase = new LanguagesDatabaseHelper(pContext).getWritableDatabase();
        mSchema = new LanguagesSchema();
    }

    @Override
    public void saveLanguages(List<Language> languages) {
        mDatabase.delete(mSchema.tableName(), null, null);
        for (Language language : languages) {
            mDatabase.insert(
                    mSchema.tableName(),
                    null,
                    new LanguageContent(
                            new ContentValues()
                    ).languageData(language)
            );
        }
    }

    @Override
    public List<HandledLanguage> allLanguages() {
        List<HandledLanguage> result = new ArrayList<>();
        Cursor cursor = mDatabase.query(
                mSchema.tableName(),
                null,
                null,
                null,
                null,
                null,
                null
        );
        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            result.add(new CursoredLanguage(
                    cursor
            ).language());
        }
        cursor.close();
        return result;
    }

    @Override
    public HandledLanguage languageByName(String languageName) {
        List<HandledLanguage> allLanguages = allLanguages();
        HandledLanguage result = new HandledLanguage.Dummy(mContext);
        for (int i = 0; i < allLanguages.size(); i++) {
            if (allLanguages.get(i).name().equals(languageName)) {
                result = allLanguages.get(i);
                break;
            }
        }
        return result;
    }

    public static LanguageStorage instance(Context pContext) {
        if (sInstance == null) {
            sInstance = new LanguagesDatabase(pContext);
        }
        return sInstance;
    }
}
