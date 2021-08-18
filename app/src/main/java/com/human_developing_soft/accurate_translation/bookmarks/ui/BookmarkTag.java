package com.human_developing_soft.accurate_translation.bookmarks.ui;

public interface BookmarkTag {

    String tagName();

    Boolean isChecked();

    void changedTag(Boolean isChecked);

    class Base implements BookmarkTag {
        private final String mTagName;
        private Boolean mIsChecked;

        public Base(String pTagName, Boolean pIsChecked) {
            mTagName = pTagName;
            mIsChecked = pIsChecked;
        }

        @Override
        public String tagName() {
            return mTagName;
        }

        @Override
        public Boolean isChecked() {
            return mIsChecked;
        }

        @Override
        public void changedTag(Boolean isChecked) {
            mIsChecked = isChecked;
        }
    }
}