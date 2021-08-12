package com.human_developing_soft.accurate_translation.translation.domain;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.human_developing_soft.accurate_translation.translation.data.CachedLanguagesList;
import com.human_developing_soft.accurate_translation.translation.data.HandledLanguage;
import com.human_developing_soft.accurate_translation.translation.data.LanguagesList;
import com.human_developing_soft.accurate_translation.translation.ui.LanguagesObserver;

import java.util.ArrayList;
import java.util.List;

public class LanguageSelectorVM extends ViewModel {
    private final CachedLanguagesList mLanguageList;
    private final List<HandledLanguage> mPreparedLanguages;
    private final LanguagesObserver mObserver;

    public LanguageSelectorVM(Context pContext, LanguagesObserver pObserver) {
        mLanguageList = new CachedLanguagesList
                .Base(
                new LanguagesList.Base(),
                pContext
        );
        mObserver = pObserver;
        mPreparedLanguages = new ArrayList<>();
    }

    public void languages() {
        if (mPreparedLanguages.isEmpty()) {
            Runnable runnable = () -> {
                mPreparedLanguages.addAll(
                        mLanguageList.languages()
                );
                mObserver.updateLanguages(mPreparedLanguages);
            };
            new Thread(runnable).start();
        } else {
            mObserver.updateLanguages(mPreparedLanguages);
        }
    }

    public HandledLanguage languageByIndex(Integer languageIndex) {
        return mPreparedLanguages.get(languageIndex);
    }
}
