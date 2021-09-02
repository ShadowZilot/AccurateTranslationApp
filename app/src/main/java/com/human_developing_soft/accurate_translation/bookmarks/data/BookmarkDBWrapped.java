package com.human_developing_soft.accurate_translation.bookmarks.data;

import android.content.Context;

import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;

public class BookmarkDBWrapped implements BookmarkStorage {
    private static BookmarkStorage sInstance;
    private final Context mContext;
    private final BookmarkDatabase mDatabase;

    private BookmarkDBWrapped(Context pContext) {
        mContext = pContext.getApplicationContext();
        mDatabase = Room.databaseBuilder(mContext,
                BookmarkDatabase.class, "Bookmarks")
                .build();
    }

    @Override
    public Boolean saveBookmark(Bookmark newBookmark) {
        return mDatabase
                .bookmarkDao()
                .insertBookmark(
                        newBookmark.toEntity()
                ) != -1;
    }

    @Override
    public List<Bookmark> searchBookmark(String searchQuery) {
        List<Bookmark> allBookmarks = bookmarks();
        List<Bookmark> searchedBookmarks = new ArrayList<>();
        for (int i = 0; i < allBookmarks.size(); i++) {
            if (allBookmarks.get(i).isSearchSubmitted(searchQuery)) {
                searchedBookmarks.add(allBookmarks.get(i));
            }
        }
        return searchedBookmarks;
    }

    @Override
    public List<Bookmark> bookmarks() {
        List<Bookmark> resultList = new ArrayList<>();
        List<BookmarkEntity> bookmarkEntities = mDatabase
                .bookmarkDao()
                .allBookmarks();
        for (int i = bookmarkEntities.size()-1; i >= 0; i--) {
            resultList.add(
                    bookmarkEntities
                            .get(i)
                            .toBookmark()
            );
        }
        return resultList;
    }

    @Override
    public Boolean updateBookmark(Bookmark updatingBookmark) {
        return mDatabase
                .bookmarkDao()
                .updateBookmark(
                        updatingBookmark.toEntity()
                ) > 0;
    }

    @Override
    public void deleteBookmark(Bookmark deletingBookmark) {
        mDatabase
                .bookmarkDao()
                .deleteBookmark(deletingBookmark.toEntity());
    }

    public static BookmarkStorage instance(Context pContext) {
        if (sInstance == null) {
            sInstance = new BookmarkDBWrapped(pContext);
        }
        return sInstance;
    }
}
