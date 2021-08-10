package com.human_developing_soft.accurate_translation.translation.data;

import android.database.Cursor;

public class CursoredLanguage {
    private final Cursor mCursor;

    public CursoredLanguage(Cursor pCursor) {
        mCursor = pCursor;
    }

    HandledLanguage language() {
        String[] columns = new LanguagesSchema().attributes();
        return new HandledLanguage.Base(
                mCursor.getString(mCursor.getColumnIndex(columns[0])),
                mCursor.getString(mCursor.getColumnIndex(columns[1])),
                mCursor.getString(mCursor.getColumnIndex(columns[3])),
                mCursor.getInt(mCursor.getColumnIndex(columns[7])) == 1
        );
    }
}
