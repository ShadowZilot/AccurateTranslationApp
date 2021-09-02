package com.human_developing_soft.accurate_translation.translation.data;

import android.content.ContentValues;

import com.ibm.watson.language_translator.v3.model.Language;

public class LanguageContent {
    private final ContentValues mValue;

    public LanguageContent(ContentValues pValue) {
        mValue = pValue;
    }

    public ContentValues languageData(Language language) {
        String[] attributes = new LanguagesSchema().attributes();
        mValue.put(attributes[0], language.getLanguage());
        mValue.put(attributes[1], language.getLanguageName());
        mValue.put(attributes[2], language.getNativeLanguageName());
        mValue.put(attributes[3], language.getCountryCode());
        mValue.put(attributes[4], language.getDirection());
        mValue.put(attributes[5], language.isSupportedAsSource() ? 1 : 0);
        mValue.put(attributes[6], language.isSupportedAsTarget() ? 1 : 0);
        mValue.put(attributes[7], language.isIdentifiable() ? 1 : 0);
        return mValue;
    }
}
