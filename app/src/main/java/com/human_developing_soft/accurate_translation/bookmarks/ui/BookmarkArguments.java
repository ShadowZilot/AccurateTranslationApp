package com.human_developing_soft.accurate_translation.bookmarks.ui;

import android.os.Bundle;

import com.human_developing_soft.accurate_translation.bookmarks.data.Bookmark;

public interface BookmarkArguments {

    Bundle arguments();

    Bookmark bookmark(String tag);

    class Base implements BookmarkArguments {
        private final Bundle mArguments;

        public Base(Bundle pArguments) {
            mArguments = pArguments;
        }

        public Base(
                String firstTranslation,
                String secondTranslation,
                String firstLanguage,
                String secondLanguage) {
            mArguments = new Bundle();
            mArguments.putString("firstTranslation", firstTranslation);
            mArguments.putString("secondTranslation", secondTranslation);
            mArguments.putString("firstLanguage", firstLanguage);
            mArguments.putString("secondLanguage", secondLanguage);
        }

        @Override
        public Bundle arguments() {
            return mArguments;
        }

        @Override
        public Bookmark bookmark(String tag) {
            return new Bookmark.Base(
                    mArguments.getString("firstTranslation"),
                    mArguments.getString("secondTranslation"),
                    mArguments.getString("firstLanguage"),
                    mArguments.getString("secondLanguage"),
                    tag
            );
        }
    }
}
