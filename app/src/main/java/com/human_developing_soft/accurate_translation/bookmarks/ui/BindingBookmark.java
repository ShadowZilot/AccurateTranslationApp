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

    class Base implements BindingBookmark {
        private final String mFirstTranslation;
        private final String mSecondTranslation;
        private final String mFirstLanguage;
        private final String mSecondLanguage;
        private final String mTag;

        public Base(String pFirstTranslation,
                    String pSecondTranslation,
                    String pFirstLanguage,
                    String pSecondLanguage,
                    String pTag) {
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
            firstTranslationView.setText(mFirstTranslation);
            secondTranslationView.setText(mSecondTranslation);
            firstLanguageView.setText(mFirstLanguage);
            secondLanguageView.setText(mSecondLanguage);
            tagView.setText(mTag);
        }

        @Override
        public Bookmark dataBookmark() {
            return new Bookmark.Base(
                    mFirstTranslation,
                    mSecondTranslation,
                    mFirstLanguage,
                    mSecondLanguage,
                    mTag
            );
        }
    }
}
