package com.human_developing_soft.accurate_translation.translation.data;

import android.content.Context;

import com.ibm.watson.language_translator.v3.model.Language;
import com.ibm.watson.language_translator.v3.model.TranslationModel;
import com.human_developing_soft.accurate_translation.translation.data.ModelList;

import java.util.ArrayList;
import java.util.List;

public interface LanguagesHandler {
    List<HandledLanguage> languagesList();

    class Base implements LanguagesHandler {
        private final ModelList mModels;
        private final LanguageStorage mLanguages;

        public Base(ModelList pModels, Context context) {
            mModels = pModels;
            mLanguages = LanguagesDBWrapped.instance(context);
        }

        @Override
        public List<HandledLanguage> languagesList() {
            List<TranslationModel> models = mModels.models();
            List<HandledLanguage> resultList = new ArrayList<>();
            boolean isLanguageValid = false;
            for (HandledLanguage language : mLanguages.allLanguages()) {
                for (TranslationModel model : models) {
                    if ((language.languageCode().equals(model.getTarget())
                            && model.getSource().equals("en"))
                            || (language.languageCode().equals(model.getSource())
                            && model.getTarget().equals("en"))
                    || language.languageCode().equals("en")) {
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
