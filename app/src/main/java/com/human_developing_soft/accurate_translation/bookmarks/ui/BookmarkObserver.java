package com.human_developing_soft.accurate_translation.bookmarks.ui;

import com.human_developing_soft.accurate_translation.bookmarks.data.Bookmark;

import java.util.List;

public interface BookmarkObserver {
    void onBookmarkUpdate(List<Bookmark> bookmarks);
}
