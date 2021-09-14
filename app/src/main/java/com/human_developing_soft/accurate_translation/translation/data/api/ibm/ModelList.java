package com.human_developing_soft.accurate_translation.translation.data;

import com.ibm.cloud.sdk.core.security.IamAuthenticator;
import com.ibm.watson.language_translator.v3.LanguageTranslator;
import com.ibm.watson.language_translator.v3.model.TranslationModel;

import java.util.List;

public interface ModelList {

    List<TranslationModel> models();

    class Base implements ModelList {

        @Override
        public List<TranslationModel> models() {
            IamAuthenticator authenticator = new IamAuthenticator(
                "HzTTXSbE3u7zlT4csQetdHE2wVWuIwVMtKAs9uvVLQlm"
            );
            LanguageTranslator languageTranslator = new LanguageTranslator("2018-05-01",
                    authenticator);
            languageTranslator.setServiceUrl(
                    "https://api.eu-gb.language-translator.watson.cloud.ibm.com/instances/756c7112-71ae-410e-8c81-dabff716e136"
            );
            return languageTranslator.listModels()
                    .execute().getResult().getModels();
        }
    }
}
