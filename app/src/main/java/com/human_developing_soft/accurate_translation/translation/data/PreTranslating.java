package com.human_developing_soft.accurate_translation.translation.data;

import com.human_developing_soft.accurate_translation.translation.domain.SelectedLanguages;

import org.json.JSONException;

public interface PreTranslating {

    String translate() throws JSONException;

    class Base implements PreTranslating {
        private final String mTranslatingText;
        private final SelectedLanguages mSelectedLanguages;
        private final Boolean mIsSwapNeeded;

        public Base(String pTranslatingText,
                    SelectedLanguages pSelectedLanguage,
                    Boolean pIsSwapNeeded) {
            mTranslatingText = pTranslatingText;
            mSelectedLanguages = pSelectedLanguage;
            mIsSwapNeeded = pIsSwapNeeded;
        }

        @Override
        public String translate() throws JSONException {
            String[] codes = mSelectedLanguages.languagesCode(mIsSwapNeeded);
            if (codes[0].equals("en") || codes[1].equals("en")) {
                return new Translating.Base(
                        mTranslatingText,
                        codes[0],
                        codes[1]
                ).translate();
            } else {
                String temporaryTranslation = new Translating.Base(
                        mTranslatingText,
                        codes[0],
                        "en"
                ).translate();
                return new Translating.Base(
                        new HandledTranslating(new TranslatingChoosing.Dummy())
                                .translate(temporaryTranslation),
                        "en",
                        codes[1]
                ).translate();
            }
        }
    }
}
