package com.human_developing_soft.accurate_translation.bookmarks.ui;

import android.widget.TextView;

import com.human_developing_soft.accurate_translation.bookmarks.data.Bookmark;

public interface BindingBookmark {

    void bind(TextView firstTranslationView,
              TextView secondTranslationView,
              TextView firstLanguageView,
              TextView secondLanguageView,
              TextView tagView);

    Bookmark dataBookmark();

    Bookmark dataBookmark(String newFirstTranslation,
                          String newSecondTranslation, String newTag);

    class Base implements BindingBookmark {
        private final Integer mId;
        private final String mFirstTranslation;
        private final String mSecondTranslation;
        private final String mFirstLanguage;
        private final String mSecondLanguage;
        private final String mTag;

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
            mTag = pTag;
        }

        @Override
        public void bind(TextView firstTranslationView,
                         TextView secondTranslationView,
                         TextView firstLanguageView,
                         TextView secondLanguageView,
                         TextView tagView) {
            firstTranslationView.setTag("blocked");
            secondTranslationView.setTag("blocked");
            firstTranslationView.setText(mFirstTranslation);
            secondTranslationView.setText(mSecondTranslation);
            firstLanguageView.setText(mFirstLanguage);
            secondLanguageView.setText(mSecondLanguage);
            tagView.setText(mTag);
            firstTranslationView.setTag("free");
            secondTranslationView.setTag("free");
        }

        @Override
        public Bookmark dataBookmark() {
            return new Bookmark.Base(
                    mId,
                    mFirstTranslation,
                    mSecondTranslation,
                    mFirstLanguage,
                    mSecondLanguage,
                    mTag
            );
        }

        @Override
        public Bookmark dataBookmark(String newFirstTranslation,
                                     String newSecondTranslation,
                                     String newTag) {
            return new Bookmark.Base(
                    mId,
                    newFirstTranslation,
                    newSecondTranslation,
                    mFirstLanguage,
                    mSecondLanguage,
                    newTag
            );
        }
    }
}
