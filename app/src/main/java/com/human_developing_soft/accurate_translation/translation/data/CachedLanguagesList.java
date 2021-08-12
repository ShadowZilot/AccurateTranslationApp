package com.human_developing_soft.accurate_translation.translation.data;

import android.content.Context;

import java.util.List;

public interface CachedLanguagesList {

    List<HandledLanguage> languages();

    class Base implements CachedLanguagesList {
        private final LanguagesList mDecorating;
        private final Context mContext;

        public Base(LanguagesList pDecorating, Context pContext) {
            mDecorating = pDecorating;
            mContext = pContext;
        }

        @Override
        public List<HandledLanguage> languages() {
            LanguageStorage database = LanguagesDatabase.instance(mContext);
            List<HandledLanguage> languages = database.allLanguages();
            if (languages.isEmpty()) {
                database.saveLanguages(mDecorating.languages());
                languages = database.allLanguages();
            }
            return languages;
        }
    }
}
