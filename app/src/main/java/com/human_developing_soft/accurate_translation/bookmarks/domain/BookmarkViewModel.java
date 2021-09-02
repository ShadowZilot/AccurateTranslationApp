package com.human_developing_soft.accurate_translation.bookmarks.domain;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.human_developing_soft.accurate_translation.bookmarks.data.Bookmark;
import com.human_developing_soft.accurate_translation.bookmarks.data.BookmarkDBWrapped;
import com.human_developing_soft.accurate_translation.bookmarks.data.BookmarkDatabase;
import com.human_developing_soft.accurate_translation.bookmarks.data.BookmarkStorage;
import com.human_developing_soft.accurate_translation.bookmarks.ui.BookmarkObserver;

public class BookmarkViewModel extends ViewModel {
    private final BookmarkStorage mStorage;
    private BookmarkObserver mObserver;
    private Thread mLastThread;

    public BookmarkViewModel(Context pContext, BookmarkObserver pObserver) {
        mStorage = BookmarkDBWrapped.instance(pContext);
        mObserver = pObserver;
    }

    public void bookmarks() {
        Runnable runnable = () -> mObserver.onBookmarkUpdate(mStorage.bookmarks());
        new Thread(runnable).start();
    }

    public void searchBookmark(String searchQuery) {
        Runnable runnable = () -> {
            try {
                if (searchQuery.equals("")) {
                    mObserver.onBookmarkUpdate(
                            mStorage.bookmarks()
                    );
                } else {
                    mObserver.onBookmarkUpdate(
                            mStorage.searchBookmark(searchQuery)
                    );
                }
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        };
        if (mLastThread != null) {
            mLastThread.interrupt();
        }
        mLastThread = new Thread(runnable);
        mLastThread.start();
    }

    public void updateObserver(BookmarkObserver newObserver) {
        mObserver = newObserver;
    }
}
