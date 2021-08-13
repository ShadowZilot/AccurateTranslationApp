package com.human_developing_soft.accurate_translation.translation.data;

import com.human_developing_soft.accurate_translation.translation.domain.SelectedLanguages;
import com.ibm.cloud.sdk.core.security.IamAuthenticator;
import com.ibm.watson.language_translator.v3.LanguageTranslator;
import com.ibm.watson.language_translator.v3.model.TranslateOptions;

import org.json.JSONException;


public interface Translating {

    String translate() throws JSONException;

    class Base implements Translating {
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
        public String translate() {
            IamAuthenticator authenticator = new IamAuthenticator("HzTTXSbE3u7zlT4csQetdHE2wVWuIwVMtKAs9uvVLQlm");
            LanguageTranslator languageTranslator = new LanguageTranslator("2018-05-01",
                    authenticator);
            languageTranslator.setServiceUrl("https://api.eu-gb.language-translator.watson.cloud.ibm.com/instances/756c7112-71ae-410e-8c81-dabff716e136");
            TranslateOptions translateOptions = new TranslateOptions.Builder()
                    .addText(mTranslatingText)
                    .modelId(mSelectedLanguages.languagesCode(mIsSwapNeeded))
                    .build();
            return languageTranslator.translate(translateOptions)
                    .execute().getResult().toString();
        }
    }
}
