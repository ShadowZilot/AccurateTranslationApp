package com.human_developing_soft.accurate_translation;

import com.human_developing_soft.accurate_translation.translation.ui.StringProvider;

public interface OnTranslationFieldChanged {
    void translateText(
        String translationField,
        Boolean isFirstField,
        StringProvider provider
    );
}
