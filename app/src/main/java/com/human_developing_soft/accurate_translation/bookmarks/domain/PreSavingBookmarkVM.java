package com.human_developing_soft.accurate_translation.bookmarks.domain;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.human_developing_soft.accurate_translation.bookmarks.data.Bookmark;
import com.human_developing_soft.accurate_translation.bookmarks.data.BookmarkDBWrapped;
import com.human_developing_soft.accurate_translation.bookmarks.data.BookmarkStorage;
import com.human_developing_soft.accurate_translation.translation.domain.TagLoadingObserver;
import com.human_developing_soft.accurate_translation.translation.ui.SavingBookmarkObserver;

import java.util.ArrayList;
import java.util.List;

public class PreSavingBookmarkVM extends ViewModel {
    private final BookmarkStorage mDatabase;
    private final TagLoadingObserver mObserver;

    public PreSavingBookmarkVM(Context pContext,
                               TagLoadingObserver pObserver) {
        mDatabase = BookmarkDBWrapped.instance(pContext);
        mObserver = pObserver;
    }

    public void loadTag() {
        Runnable runnable = () -> {
            List<Bookmark> bookmarks = mDatabase.bookmarks();
            List<String> resultTags = new ArrayList<>();
            for (Bookmark bookmark: bookmarks) {
                if (!resultTags.contains(bookmark.tag())) {
                    resultTags.add(bookmark.tag());
                }
            }
            mObserver.onTagLoaded(resultTags);
        };
        new Thread(runnable).start();
    }

    public void saveBookmark(Bookmark savingBookmark,
                             SavingBookmarkObserver observer) {
        Runnable runnable = () -> {
            observer.onBookmarkSaved(
                    mDatabase.saveBookmark(
                            savingBookmark
                    )
            );
        };
        new Thread(runnable).start();
    }
}
