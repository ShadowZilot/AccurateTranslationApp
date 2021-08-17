package com.human_developing_soft.accurate_translation.bookmarks.ui;

import android.widget.TextView;

public interface BindingBookmark {

    void bind(TextView firstTranslationView,
              TextView secondTranslationView,
              TextView firstLanguageView,
              TextView secondLanguageView,
              TextView tagView);

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
    }
}
