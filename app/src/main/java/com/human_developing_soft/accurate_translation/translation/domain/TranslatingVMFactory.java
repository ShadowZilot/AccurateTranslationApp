package com.human_developing_soft.accurate_translation.translation.domain;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.human_developing_soft.accurate_translation.translation.ui.TranslatingObserver;

public class TranslatingVMFactory implements ViewModelProvider.Factory {
    private final TranslatingObserver mObserver;

    public TranslatingVMFactory(TranslatingObserver pObserver) {
        mObserver = pObserver;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new TranslatingViewModel(mObserver);
    }
}
