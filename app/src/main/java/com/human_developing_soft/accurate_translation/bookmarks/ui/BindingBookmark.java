package com.human_developing_soft.accurate_translation.bookmarks.ui;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.human_developing_soft.accurate_translation.bookmarks.data.Bookmark;
import com.human_developing_soft.accurate_translation.translation.domain.ImageLoading;

public interface BindingBookmark {

    void bind(TextView firstTranslationView,
              TextView secondTranslationView,
              ImageView firstLanguageView,
              ImageView secondLanguageView,
              TextView tagView);

    Bundle toBundle();

    String[] languagesName();

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
        private final Long mDate;

        public Base(
                Bundle initBundle
        ) {
            this(
                    initBundle.getInt("id"),
                    initBundle.getString("firstTranslation"),
                    initBundle.getString("secondTranslation"),
                    initBundle.getString("firstLanguage"),
                    initBundle.getString("secondLanguage"),
                    initBundle.getString("tag"),
                    initBundle.getLong("date")
            );
        }

        public Base(
                Integer pId,
                String pFirstTranslation,
                String pSecondTranslation,
                String pFirstLanguage,
                String pSecondLanguage,
                String pTag,
                Long pDate) {
            mId = pId;
            mFirstTranslation = pFirstTranslation;
            mSecondTranslation = pSecondTranslation;
            mFirstLanguage = pFirstLanguage;
            mSecondLanguage = pSecondLanguage;
            mTag = pTag;
            mDate = pDate;
        }

        @Override
        public void bind(TextView firstTranslationView,
                         TextView secondTranslationView,
                         ImageView firstLanguageView,
                         ImageView secondLanguageView,
                         TextView tagView) {
            ImageLoading loading = new ImageLoading.Base(
                    firstLanguageView.getContext());
            firstTranslationView.setTag("blocked");
            secondTranslationView.setTag("blocked");
            firstTranslationView.setText(mFirstTranslation);
            secondTranslationView.setText(mSecondTranslation);
            firstLanguageView.setImageDrawable(
                    loading.flagByCountryCode(mFirstLanguage)
            );
            secondLanguageView.setImageDrawable(
                    loading.flagByCountryCode(mSecondLanguage)
            );
            tagView.setText(mTag);
            firstTranslationView.setTag("free");
            secondTranslationView.setTag("free");
        }

        @Override
        public Bundle toBundle() {
            Bundle bundle = new Bundle();
            bundle.putInt("id", mId);
            bundle.putString("firstTranslation", mFirstTranslation);
            bundle.putString("secondTranslation", mSecondTranslation);
            bundle.putString("firstLanguage", mFirstLanguage);
            bundle.putString("secondLanguage", mSecondLanguage);
            bundle.putString("tag", mTag);
            bundle.putLong("date", mDate);
            return bundle;
        }

        @Override
        public String[] languagesName() {
            return new String[] {
                    mFirstLanguage,
                    mSecondLanguage
            };
        }

        @Override
        public Bookmark dataBookmark() {
            return new Bookmark.Base(
                    mId,
                    mFirstTranslation,
                    mSecondTranslation,
                    mFirstLanguage,
                    mSecondLanguage,
                    mTag,
                    mDate
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
                    newTag,
                    mDate
            );
        }
    }
}
