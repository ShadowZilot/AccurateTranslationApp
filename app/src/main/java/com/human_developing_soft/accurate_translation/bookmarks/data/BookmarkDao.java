package com.human_developing_soft.accurate_translation.bookmarks.data;

import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface BookmarkDao {
    @Query("SELECT * FROM bookmarks")
    List<BookmarkEntity> allBookmarks();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insertBookmark(BookmarkEntity bookmarkEntity);

    @Update
    int updateBookmark(BookmarkEntity bookmarkEntity);

    @Delete
    void deleteBookmark(BookmarkEntity bookmarkEntity);
}
