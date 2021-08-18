package com.human_developing_soft.accurate_translation.bookmarks.domain;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.human_developing_soft.accurate_translation.translation.domain.TagLoadingObserver;

public class PreSavingBookmarkVMFactory implements ViewModelProvider.Factory {
    private final Context mContext;
    private final TagLoadingObserver mObserver;

    public PreSavingBookmarkVMFactory(Context pContext, TagLoadingObserver pObserver) {
        mContext = pContext;
        mObserver = pObserver;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new PreSavingBookmarkVM(mContext, mObserver);
    }
}
