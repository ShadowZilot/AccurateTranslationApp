package com.human_developing_soft.accurate_translation.translation.data;

public interface HandledLanguage {

    String name();

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

        @Override
        public String name() {
            return mName;
        }
    }
}
