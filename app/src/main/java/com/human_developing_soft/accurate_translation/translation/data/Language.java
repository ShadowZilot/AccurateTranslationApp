package com.human_developing_soft.accurate_translation.translation.data;

public interface Language {
    HandledLanguage toHandledLanguage();

    LanguageEntity toEntity();

    class Base implements Language {
        private final String mLanguageName;
        private final String mLanguageCode;
        private final String mCountryCode;

        public Base(
                String name,
                String languageCode,
                String countryCode
        ) {
            mLanguageName = name;
            mLanguageCode = languageCode;
            mCountryCode = countryCode;
        }

        public Base(
                HandledLanguage handledLanguage
        ) {
            this(
                handledLanguage.name(),
                handledLanguage.languageCode(),
                handledLanguage.countryCode()
            );
        }

        public Base(
            LanguageEntity entity
        ) {
            this(
                    entity.getName(),
                    entity.getLanguageCode(),
                    entity.getCountryCode()
            );
        }

        @Override
        public HandledLanguage toHandledLanguage() {
            return new HandledLanguage.Base(
                    mLanguageCode,
                    mLanguageName,
                    mCountryCode
            );
        }

        @Override
        public LanguageEntity toEntity() {
            return new LanguageEntity(
                    mLanguageName,
                    mLanguageCode,
                    mCountryCode
            );
        }
    }
}
