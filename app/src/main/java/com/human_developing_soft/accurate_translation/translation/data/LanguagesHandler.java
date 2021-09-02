package com.human_developing_soft.accurate_translation.translation.data;

import com.ibm.watson.language_translator.v3.model.Language;
import com.ibm.watson.language_translator.v3.model.TranslationModel;

import java.util.ArrayList;
import java.util.List;

public interface LanguagesHandler {
    List<Language> languagesList();

    class Base implements LanguagesHandler {
        private final ModelList mModels;
        private final LanguagesList mLanguages;

        public Base(ModelList pModels,
                    LanguagesList pLanguages) {
            mModels = pModels;
            mLanguages = pLanguages;
        }

        @Override
        public List<Language> languagesList() {
            List<Language> languages = mLanguages.languages();
            List<TranslationModel> models = mModels.models();
            List<Language> resultList = new ArrayList<>();
            boolean isLanguageValid = false;
            for (Language language : languages) {
                for (TranslationModel model : models) {
                    if ((language.getLanguage().equals(model.getTarget())
                            && model.getSource().equals("en"))
                            || (language.getLanguage().equals(model.getSource())
                            && model.getTarget().equals("en"))
                    || language.getLanguage().equals("en")) {
                        isLanguageValid = true;
                        break;
                    } else {
                        isLanguageValid = false;
                    }
                }
                if (isLanguageValid) {
                    resultList.add(language);
                }
            }
            return resultList;
        }
    }
}
