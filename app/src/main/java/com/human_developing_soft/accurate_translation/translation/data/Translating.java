package com.human_developing_soft.accurate_translation.translation.data;

import com.ibm.cloud.sdk.core.http.HttpConfigOptions;
import com.ibm.cloud.sdk.core.security.IamAuthenticator;
import com.ibm.cloud.sdk.core.security.IamToken;
import com.ibm.watson.language_translator.v3.LanguageTranslator;
import com.ibm.watson.language_translator.v3.model.TranslateOptions;


public interface Translating {

    String implementTranslating();

    class Base implements Translating {
        private final String mTranslatingText;

        public Base(String pTranslatingText) {
            mTranslatingText = pTranslatingText;
        }

        @Override
        public String implementTranslating() {

            IamAuthenticator authenticator = new IamAuthenticator("HzTTXSbE3u7zlT4csQetdHE2wVWuIwVMtKAs9uvVLQlm");
            LanguageTranslator languageTranslator = new LanguageTranslator("2018-05-01",
                    authenticator);
            languageTranslator.setServiceUrl("https://api.eu-gb.language-translator.watson.cloud.ibm.com/instances/756c7112-71ae-410e-8c81-dabff716e136");
            TranslateOptions translateOptions = new TranslateOptions.Builder()
                    .addText(mTranslatingText)
                    .modelId("en-ru")
                    .build();
            return languageTranslator.translate(translateOptions)
                    .execute().getResult().toString();
        }
    }
}
