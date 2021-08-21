package com.human_developing_soft.accurate_translation.bookmarks.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface BookmarkDao {
    @Query("SELECT * FROM bookmarks")
    List<BookmarkEntity> allBookmarks();

    @Insert
    long insertBookmark(BookmarkEntity bookmarkEntity);

    @Update
    int updateBookmark(BookmarkEntity bookmarkEntity);
}
