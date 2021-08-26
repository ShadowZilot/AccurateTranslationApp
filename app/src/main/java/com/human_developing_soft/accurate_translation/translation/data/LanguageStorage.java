package com.human_developing_soft.accurate_translation.translation.data;

import com.ibm.watson.language_translator.v3.model.Language;

import java.util.List;

public interface LanguageStorage {
    void saveLanguages(List<Language> languages);

    List<HandledLanguage> allLanguages();

    HandledLanguage languageByCountry(String countryCode);
}
