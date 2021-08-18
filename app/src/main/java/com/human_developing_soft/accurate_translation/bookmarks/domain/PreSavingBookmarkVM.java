package com.human_developing_soft.accurate_translation.bookmarks.domain;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.human_developing_soft.accurate_translation.bookmarks.data.Bookmark;
import com.human_developing_soft.accurate_translation.bookmarks.data.BookmarkDatabase;
import com.human_developing_soft.accurate_translation.bookmarks.data.BookmarkStorage;
import com.human_developing_soft.accurate_translation.translation.domain.TagLoadingObserver;
import com.human_developing_soft.accurate_translation.translation.ui.SavingBookmarkObserver;

public class PreSavingBookmarkVM extends ViewModel {
    private final BookmarkStorage mDatabase;
    private final TagLoadingObserver mObserver;

    public PreSavingBookmarkVM(Context pContext,
                               TagLoadingObserver pObserver) {
        mDatabase = BookmarkDatabase.instance(pContext);
        mObserver = pObserver;
    }

    public void loadTag() {

    }

    public void saveBookmark(Bookmark savingBookmark,
                             SavingBookmarkObserver observer) {
        Runnable runnable = () -> {
            mDatabase.saveBookmark(savingBookmark);
            observer.onBookmarkSaved(true);
        };
        new Thread(runnable).start();
    }
}
