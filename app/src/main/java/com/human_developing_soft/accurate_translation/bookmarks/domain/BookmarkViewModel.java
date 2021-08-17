package com.human_developing_soft.accurate_translation.bookmarks.domain;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.human_developing_soft.accurate_translation.bookmarks.data.BookmarkDatabase;
import com.human_developing_soft.accurate_translation.bookmarks.data.BookmarkStorage;
import com.human_developing_soft.accurate_translation.bookmarks.ui.BookmarkObserver;

public class BookmarkViewModel extends ViewModel {
    private final BookmarkStorage mStorage;
    private final BookmarkObserver mObserver;

    public BookmarkViewModel(Context pContext, BookmarkObserver pObserver) {
        mStorage = BookmarkDatabase.instance(pContext);
        mObserver = pObserver;
    }

    public void bookmarks() {
        Runnable runnable = () -> mObserver.onBookmarkUpdate(mStorage.bookmarks());
        new Thread(runnable).start();
    }
}
