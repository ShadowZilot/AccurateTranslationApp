package com.human_developing_soft.accurate_translation.bookmarks.data;

import com.human_developing_soft.accurate_translation.bookmarks.ui.BindingBookmark;

public interface Bookmark {

    BindingBookmark binding();

    Boolean isSearchSubmitted(String searchQuery);

    String tag();

    BookmarkEntity toEntity();

    class Base implements Bookmark {
        private final Integer mId;
        private final String mFirstTranslation;
        private final String mSecondTranslation;
        private final String mFirstLanguage;
        private final String mSecondLanguage;
        private final String mTag;

        public Base(
                String pFirstTranslation,
                String pSecondTranslation,
                String pFirstLanguage,
                String pSecondLanguage,
                String pTag
        ) {
            this(
                    null,
                    pFirstTranslation,
                    pSecondTranslation,
                    pFirstLanguage,
                    pSecondLanguage,
                    pTag
            );
        }

        public Base(
                Integer pId,
                String pFirstTranslation,
                String pSecondTranslation,
                String pFirstLanguage,
                String pSecondLanguage,
                String pTag) {
            mId = pId;
            mFirstTranslation = pFirstTranslation;
            mSecondTranslation = pSecondTranslation;
            mFirstLanguage = pFirstLanguage;
            mSecondLanguage = pSecondLanguage;
            mTag = "#" + pTag.replace("#", "");
        }

        @Override
        public BindingBookmark binding() {
            return new BindingBookmark.Base(
                    mId,
                    mFirstTranslation,
                    mSecondTranslation,
                    mFirstLanguage,
                    mSecondLanguage,
                    mTag
            );
        }

        @Override
        public Boolean isSearchSubmitted(String searchQuery) {
            return mFirstTranslation.contains(searchQuery)
                    || mSecondTranslation.contains(searchQuery)
                    || mTag.contains(searchQuery);
        }

        @Override
        public String tag() {
            return mTag;
        }

        @Override
        public BookmarkEntity toEntity() {
            return new BookmarkEntity(
                    mId,
                    mFirstTranslation,
                    mSecondTranslation,
                    mFirstLanguage,
                    mSecondLanguage,
                    mTag
            );
        }
    }
}
