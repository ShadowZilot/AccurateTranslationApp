package com.human_developing_soft.accurate_translation.translation.domain;

import com.human_developing_soft.accurate_translation.translation.data.api.CharactersLimit;

public interface VersionChecking {

    Boolean isLimitedApi();

    class Base implements VersionChecking {
        private final Number mTranslatingTextLength;

        public Base(String translatingText) {
            mTranslatingTextLength = translatingText.length();
        }

        @Override
        public Boolean isLimitedApi() {
            return mTranslatingTextLength.intValue() > new CharactersLimit
                        .Constant()
                        .limit();
        }
    }
}
