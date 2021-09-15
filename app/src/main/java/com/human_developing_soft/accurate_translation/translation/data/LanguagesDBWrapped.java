package com.human_developing_soft.accurate_translation.translation.data;

import android.content.Context;

import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;

public class LanguagesDBWrapped implements LanguageStorage {
    private static LanguageStorage sInstance;
    private final Context mContext;
    private final LanguageDatabase mDatabase;

    private LanguagesDBWrapped(Context pContext) {
        mContext = pContext.getApplicationContext();
        mDatabase = Room.databaseBuilder(mContext,
                LanguageDatabase.class,
                "Language_db")
                .createFromAsset("Languages_db.db")
                .build();
    }

    @Override
    public void saveLanguages(List<HandledLanguage> languages) {
        mDatabase.languageDao().deleteAll();
        for (HandledLanguage language : languages) {
            mDatabase.languageDao().saveLanguage(
                new Language.Base(
                        language
                ).toEntity()
            );
        }
    }

    @Override
    public List<HandledLanguage> allLanguages() {
        List<LanguageEntity> languages = mDatabase.languageDao().allLanguage();
        List<HandledLanguage> result = new ArrayList<>();
        for (LanguageEntity languageEntity: languages) {
            result.add(
                new Language.Base(
                        languageEntity
                ).toHandledLanguage()
            );
        }
        return result;
    }

    @Override
    public HandledLanguage languageByCountry(String countryCode) {
        List<HandledLanguage> allLanguages = allLanguages();
        HandledLanguage result = new HandledLanguage.Dummy(mContext);
        for (int i = 0; i < allLanguages.size(); i++) {
            if (allLanguages.get(i).countryCode().equals(countryCode)) {
                result = allLanguages.get(i);
                break;
            }
        }
        return result;
    }

    public static LanguageStorage instance(Context pContext) {
        if (sInstance == null) {
            sInstance = new LanguagesDBWrapped(pContext);
        }
        return sInstance;
    }
}
