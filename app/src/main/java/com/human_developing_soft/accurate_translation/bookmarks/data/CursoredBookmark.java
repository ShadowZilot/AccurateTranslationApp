package com.human_developing_soft.accurate_translation.bookmarks.data;

import android.database.Cursor;

public interface CursoredBookmark {

    String stringByAttribute(String attribute);

    Integer integerByAttribute(String attribute);

    class Base implements CursoredBookmark {
        private final Cursor mCursor;

        public Base(Cursor pCursor) {
            mCursor = pCursor;
        }

        @Override
        public String stringByAttribute(String attribute) {
            return mCursor.getString(
                    mCursor.getColumnIndex(
                            attribute
                    )
            );
        }

        @Override
        public Integer integerByAttribute(String attribute) {
            return mCursor.getInt(
                    mCursor.getColumnIndex(
                            attribute
                    )
            );
        }
    }
}
