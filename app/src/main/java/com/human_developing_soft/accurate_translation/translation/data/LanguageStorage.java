package com.human_developing_soft.accurate_translation.translation.data;

import java.util.List;

public interface LanguageStorage {
    void saveLanguages(List<HandledLanguage> languages);

    List<HandledLanguage> allLanguages();

    HandledLanguage languageByCountry(String countryCode);
}
