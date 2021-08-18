package com.human_developing_soft.accurate_translation.bookmarks.data;

import android.content.ContentValues;

import com.human_developing_soft.accurate_translation.CommonSchema;
import com.human_developing_soft.accurate_translation.bookmarks.ui.BindingBookmark;

public interface Bookmark {

    ContentValues value(CommonSchema schema);

    BindingBookmark binding();

    String tag();

    class Base implements Bookmark {
        private final String mFirstTranslation;
        private final String mSecondTranslation;
        private final String mFirstLanguage;
        private final String mSecondLanguage;
        private final String mTag;

        public Base(
                CursoredBookmark cursor,
                CommonSchema schema
        ) {
            this(
                cursor.stringByAttribute(schema.attributes()[0]),
                cursor.stringByAttribute(schema.attributes()[1]),
                cursor.stringByAttribute(schema.attributes()[2]),
                cursor.stringByAttribute(schema.attributes()[3]),
                cursor.stringByAttribute(schema.attributes()[4])
            );
        }

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
        public ContentValues value(CommonSchema schema) {
            ContentValues values = new ContentValues();
            String[] attributes = schema.attributes();
            values.put(attributes[0], mFirstTranslation);
            values.put(attributes[1], mSecondTranslation);
            values.put(attributes[2], mFirstLanguage);
            values.put(attributes[3], mSecondLanguage);
            values.put(attributes[4], mTag);
            return values;
        }

        @Override
        public BindingBookmark binding() {
            return new BindingBookmark.Base(
                    mFirstTranslation,
                    mSecondTranslation,
                    mFirstLanguage,
                    mSecondLanguage,
                    mTag
            );
        }

        @Override
        public String tag() {
            return mTag;
        }
    }
}
