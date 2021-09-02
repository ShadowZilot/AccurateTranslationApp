package com.human_developing_soft.accurate_translation.bookmarks.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BookmarkDBHelper extends SQLiteOpenHelper {

    public BookmarkDBHelper(Context pContext) {
        super(pContext,
                "Bookmarks.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + new BookmarkSchema().schema());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
}
