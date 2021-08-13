package com.human_developing_soft.accurate_translation.translation.data;

import android.os.Bundle;

import androidx.annotation.NonNull;

public interface HandledLanguage {

    String name();

    String languageCode();

    Bundle languageBundle();

    class Base implements HandledLanguage {
        private final String mLanguage;
        private final String mName;
        private final String mCountryCode;
        private final Boolean mIsIdentifiable;

        public Base(String pLanguage,
                    String pName,
                    String pCountryCode,
                    Boolean pIsIdentifiable) {
            mLanguage = pLanguage;
            mName = pName;
            mCountryCode = pCountryCode;
            mIsIdentifiable = pIsIdentifiable;
        }

        public Base(
            String initValue
        ) {
            String[] values = initValue.split(";");
            mLanguage = values[0];
            mName = values[1];
            mCountryCode = values[2];
            mIsIdentifiable = Boolean.parseBoolean(values[3]);
        }

        public Base(
            Bundle packedLanguage
        ) {
            mName = packedLanguage.getString("name");
            mLanguage = packedLanguage.getString("language");
            mCountryCode = packedLanguage.getString("countryCode");
            mIsIdentifiable = packedLanguage.getBoolean("identifiable");
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
        public Bundle languageBundle() {
            Bundle packed = new Bundle();
            packed.putString("name", mName);
            packed.putString("countryCode", mCountryCode);
            packed.putString("language", mLanguage);
            packed.putBoolean("identifiable", mIsIdentifiable);
            return packed;
        }

        @NonNull
        @Override
        public String toString() {
            return mLanguage +
                    ";" +
                    mName +
                    ";" +
                    mCountryCode +
                    ";" +
                    mIsIdentifiable.toString();
        }
    }

    class Dummy implements HandledLanguage {

        @Override
        public String name() {
            return "";
        }

        @Override
        public String languageCode() {
            return "";
        }

        @Override
        public Bundle languageBundle() {
            return new Bundle();
        }
    }
}
