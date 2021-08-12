package com.human_developing_soft.accurate_translation.translation.domain;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.human_developing_soft.accurate_translation.translation.ui.LanguagesObserver;

public class LanguageSelectorVMFactory implements ViewModelProvider.Factory {
    private final Context mContext;
    private final LanguagesObserver mObserver;

    public LanguageSelectorVMFactory(Context pContext,
                                     LanguagesObserver pObserver) {
        mObserver = pObserver;
        mContext = pContext;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new LanguageSelectorVM(mContext, mObserver);
    }
}
