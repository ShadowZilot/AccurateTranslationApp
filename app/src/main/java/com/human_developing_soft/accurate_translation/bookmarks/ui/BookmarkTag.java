package com.human_developing_soft.accurate_translation.bookmarks.ui;

public interface BookmarkTag {

    String tagName();

    class Base implements BookmarkTag {
        private final String mTagName;

        public Base(String pTagName) {
            mTagName = pTagName;
        }

        @Override
        public String tagName() {
            return mTagName;
        }
    }
}