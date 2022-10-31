package com.human_developing_soft.accurate_translation.bookmarks.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {BookmarkEntity.class}, version = 2)
public abstract class BookmarkDatabase extends RoomDatabase {
    public abstract BookmarkDao bookmarkDao();
}

