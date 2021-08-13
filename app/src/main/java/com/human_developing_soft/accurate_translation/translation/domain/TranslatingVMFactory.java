package com.human_developing_soft.accurate_translation.translation.domain;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.human_developing_soft.accurate_translation.translation.ui.TranslatingObserver;

public class TranslatingVMFactory implements ViewModelProvider.Factory {
    private final TranslatingObserver mObserver;
    private final Context mContext;

    public TranslatingVMFactory(TranslatingObserver pObserver, Context pContext) {
        mObserver = pObserver;
        mContext = pContext;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new TranslatingViewModel(mObserver, mContext);
    }
}
