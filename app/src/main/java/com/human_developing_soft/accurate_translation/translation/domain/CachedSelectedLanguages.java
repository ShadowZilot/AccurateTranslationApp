package com.human_developing_soft.accurate_translation.translation.domain;

import android.content.Context;
import android.content.SharedPreferences;

import com.human_developing_soft.accurate_translation.translation.data.HandledLanguage;

public interface CachedSelectedLanguages {
    SelectedLanguages cachedSelectedLanguage();

    void cacheSelectedLanguage(SelectedLanguages languages);

    class Base implements CachedSelectedLanguages {
        private final Context mContext;

        public Base(Context pContext) {
            mContext = pContext;
        }

        @Override
        public SelectedLanguages cachedSelectedLanguage() {
            SharedPreferences preferences = mContext.getSharedPreferences("selectedLanguages",
                    Context.MODE_PRIVATE);
            try {
                return new SelectedLanguages.Base(preferences.getString("selected",
                        ""));
            } catch (IndexOutOfBoundsException e) {
                return new SelectedLanguages.Base(
                        new HandledLanguage.Dummy(mContext),
                        new HandledLanguage.Dummy(mContext)
                );
            }
        }

        @Override
        public void cacheSelectedLanguage(SelectedLanguages languages) {
            SharedPreferences preferences = mContext.getSharedPreferences(
                    "selectedLanguages",
                    Context.MODE_PRIVATE
            );
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("selected", languages.packedString());
            editor.apply();
        }
    }
}
