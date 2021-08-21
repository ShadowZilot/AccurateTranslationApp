package com.human_developing_soft.accurate_translation.bookmarks.domain;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.human_developing_soft.accurate_translation.translation.ui.TranslatingObserver;

public class BookmarkEditingVMFactory implements ViewModelProvider.Factory {
    private final TranslatingObserver mObserver;
    private final Context mContext;
    private final String mFirstLanguage;
    private final String mSecondLanguage;

    public BookmarkEditingVMFactory(TranslatingObserver mObserver, Context mContext, String mFirstLanguage, String mSecondLanguage) {
        this.mObserver = mObserver;
        this.mContext = mContext;
        this.mFirstLanguage = mFirstLanguage;
        this.mSecondLanguage = mSecondLanguage;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new BookmarkEditingVM(mObserver, mContext, mFirstLanguage, mSecondLanguage);
    }
}
