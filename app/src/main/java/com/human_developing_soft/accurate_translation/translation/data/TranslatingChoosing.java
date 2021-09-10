package com.human_developing_soft.accurate_translation.translation.data;

import com.human_developing_soft.accurate_translation.translation.domain.SelectedLanguages;
import com.human_developing_soft.accurate_translation.translation.domain.VersionChecking;

import org.json.JSONException;

public interface TranslatingChoosing {

    String translate() throws JSONException;

    class Base implements TranslatingChoosing {
        private final VersionChecking mChecking;
        private final String mTranslatingText;
        private final SelectedLanguages mSelectedLanguages;
        private final Boolean mIsSwapNeeded;

        public Base(String translatingText,
                    SelectedLanguages selectedLanguages,
                    Boolean isSwapNeeded) {
            mChecking = new VersionChecking.Base();
            mTranslatingText = translatingText;
            mSelectedLanguages = selectedLanguages;
            mIsSwapNeeded = isSwapNeeded;
        }

        @Override
        public String translate() throws JSONException {
            if (mChecking.isNormalApi()) {
                return new PreTranslating.Base(mTranslatingText,
                        mSelectedLanguages,
                        mIsSwapNeeded).translate();
            }
            return "";
        }
    }

    class Dummy implements TranslatingChoosing {
        @Override
        public String translate() {
            return "Dummy PreTranslating implementation";
        }
    }
}
