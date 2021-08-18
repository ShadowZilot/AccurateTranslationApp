package com.human_developing_soft.accurate_translation.bookmarks.data;

import com.human_developing_soft.accurate_translation.CommonSchema;

public class BookmarkSchema implements CommonSchema {
    private final String mTableName = "bookmarks";
    private final String mFirstTranslation = "firstTranslation";
    private final String mSecondTranslation = "secondTranslation";
    private final String mFirstLanguageName = "firstName";
    private final String mSecondLanguageName = "secondLanguage";
    private final String mTag = "tag";

    @Override
    public String tableName() {
        return mTableName;
    }

    @Override
    public String[] attributes() {
        return new String[]{
                mFirstTranslation,
                mSecondTranslation,
                mFirstLanguageName,
                mSecondLanguageName,
                mTag
        };
    }

    @Override
    public String schema() {
        return String.format("%s (%s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT)",
                mTableName,
                mFirstTranslation,
                mSecondTranslation,
                mFirstLanguageName,
                mSecondLanguageName,
                mTag);
    }
}