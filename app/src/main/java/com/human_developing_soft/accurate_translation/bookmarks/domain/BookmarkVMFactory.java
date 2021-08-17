package com.human_developing_soft.accurate_translation.bookmarks.domain;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.human_developing_soft.accurate_translation.bookmarks.ui.BookmarkObserver;

public class BookmarkVMFactory implements ViewModelProvider.Factory {
    private final Context mContext;
    private final BookmarkObserver mObserver;

    public BookmarkVMFactory(Context pContext, BookmarkObserver pObserver) {
        mContext = pContext;
        mObserver = pObserver;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new BookmarkViewModel(mContext, mObserver);
    }
}
