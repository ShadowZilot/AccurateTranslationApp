package com.human_developing_soft.accurate_translation.translation.ui;

import com.human_developing_soft.accurate_translation.translation.data.HandledLanguage;

import java.util.List;

public interface LanguagesObserver {
    void updateLanguages(List<HandledLanguage> languages);
}
