package com.human_developing_soft.accurate_translation.translation.data;

import android.content.Context;

import java.util.List;

public interface CachedLanguagesList {

    List<HandledLanguage> languages();

    class Base implements CachedLanguagesList {
        private final LanguagesHandler mWrappedObject;
        private final Context mContext;

        public Base(LanguagesHandler pWrapped, Context pContext) {
            mWrappedObject = pWrapped;
            mContext = pContext;
        }

        @Override
        public List<HandledLanguage> languages() {
            LanguageStorage database = LanguagesDatabase.instance(mContext);
            List<HandledLanguage> languages = database.allLanguages();
            if (languages.isEmpty()) {
                database.saveLanguages(mWrappedObject.languagesList());
                languages = database.allLanguages();
            }
            return languages;
        }
    }
}
