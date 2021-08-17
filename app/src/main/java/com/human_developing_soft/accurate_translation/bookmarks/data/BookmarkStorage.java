package com.human_developing_soft.accurate_translation.bookmarks.data;

import java.util.List;

public interface BookmarkStorage {
    void saveBookmark(Bookmark newBookmark);

    List<Bookmark> searchBookmark(String searchQuery);

    List<Bookmark> bookmarks();
}
