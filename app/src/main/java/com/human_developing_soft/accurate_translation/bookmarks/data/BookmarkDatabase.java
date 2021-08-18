package com.human_developing_soft.accurate_translation.bookmarks.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.human_developing_soft.accurate_translation.CommonSchema;

import java.util.ArrayList;
import java.util.List;

public class BookmarkDatabase implements BookmarkStorage {
    private static BookmarkStorage sInstance;
    private final Context mContext;
    private final SQLiteDatabase mDatabase;

    private BookmarkDatabase(Context pContext) {
        mContext = pContext.getApplicationContext();
        mDatabase = new BookmarkDBHelper(mContext).getWritableDatabase();
    }

    public static BookmarkStorage instance(Context pContext) {
        if (sInstance == null) {
            sInstance = new BookmarkDatabase(pContext);
        }
        return sInstance;
    }

    @Override
    public void saveBookmark(Bookmark newBookmark) {
        CommonSchema schema = new BookmarkSchema();
        mDatabase.insert(schema.tableName(),
                null,
                newBookmark.value(schema));
    }

    @Override
    public List<Bookmark> searchBookmark(String searchQuery) {
        List<Bookmark> allBookmarks = bookmarks();
        List<Bookmark> searchedBookmarks = new ArrayList<>();
        for (int i = 0; i < allBookmarks.size(); i++) {
            if (allBookmarks.get(i).isSearchSubmitted(searchQuery)) {
                searchedBookmarks.add(
                    allBookmarks.get(i)
                );
            }
        }
        return searchedBookmarks;
    }

    @Override
    public List<Bookmark> bookmarks() {
        List<Bookmark> result = new ArrayList<>();
        Cursor cursor = mDatabase.query(
                new BookmarkSchema().tableName(),
                null,
                null,
                null,
                null,
                null,
                null
        );
        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            result.add(
                    new Bookmark.Base(
                            new CursoredBookmark.Base(
                                    cursor),
                            new BookmarkSchema()
                    )
            );
        }
        cursor.close();
        return result;
    }
}
