package com.human_developing_soft.accurate_translation;

import com.human_developing_soft.accurate_translation.translation.domain.SelectedLanguages;

public interface OnLanguageUpdated {
    void onLanguageUpdate(SelectedLanguages updatedLanguage);
}
