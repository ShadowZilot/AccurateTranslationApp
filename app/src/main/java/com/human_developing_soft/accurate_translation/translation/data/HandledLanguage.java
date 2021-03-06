package com.human_developing_soft.accurate_translation.translation.data;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.human_developing_soft.accurate_translation.R;
import com.human_developing_soft.accurate_translation.translation.ui.StringProvider;

public interface HandledLanguage {

    String name();

    String languageCode();

    String countryCode();

    Bundle languageBundle();

    class Base implements HandledLanguage {
        private final String mLanguage;
        private final String mName;
        private final String mCountryCode;

        public Base(String pLanguage,
                    String pName,
                    String pCountryCode) {
            mLanguage = pLanguage;
            mName = pName;
            mCountryCode = pCountryCode;
        }

        public Base(
                String initValue
        ) {
            String[] values = initValue.split(";");
            mLanguage = values[0];
            mName = values[1];
            mCountryCode = values[2];
        }

        public Base(Bundle packedLanguage) {
            mName = packedLanguage.getString("name");
            mLanguage = packedLanguage.getString("language");
            mCountryCode = packedLanguage.getString("countryCode");
        }

        @Override
        public String name() {
            return mName;
        }

        @Override
        public String languageCode() {
            return mLanguage;
        }

        @Override
        public String countryCode() {
            return mCountryCode.toLowerCase();
        }

        @Override
        public Bundle languageBundle() {
            Bundle packed = new Bundle();
            packed.putString("name", mName);
            packed.putString("countryCode", mCountryCode);
            packed.putString("language", mLanguage);
            return packed;
        }

        @NonNull
        @Override
        public String toString() {
            return mLanguage +
                    ";" +
                    mName +
                    ";" +
                    mCountryCode;
        }
    }

    class Dummy implements HandledLanguage {
        private final Context mContext;

        public Dummy(Context pContext) {
            mContext = pContext;
        }

        @Override
        public String name() {
            return new StringProvider.Base(
                    mContext
            ).string(R.string.select_language_message);
        }

        @Override
        public String languageCode() {
            return "";
        }

        @Override
        public String countryCode() {
            return "";
        }

        @Override
        public Bundle languageBundle() {
            return new Bundle();
        }
    }
}
