package com.human_developing_soft.accurate_translation.translation.data.api;

import com.human_developing_soft.accurate_translation.translation.data.api.google.GoogleTranslating;
import com.human_developing_soft.accurate_translation.translation.data.api.ibm.Translating;
import com.human_developing_soft.accurate_translation.translation.domain.SelectedLanguages;

import org.json.JSONException;

public class GoogleApi implements Translating {
    private final String mTranslatingText;
    private final SelectedLanguages mSelectedLanguages;
    private final Boolean mIsSwapNeeded;

    public GoogleApi(String pTranslatingText,
                     SelectedLanguages pSelectedLanguage,
                     Boolean pIsSwapNeeded) {
        mTranslatingText = pTranslatingText;
        mSelectedLanguages = pSelectedLanguage;
        mIsSwapNeeded = pIsSwapNeeded;
    }

    @Override
    public String translate() throws JSONException {
        String[] codes = mSelectedLanguages.languagesCode(mIsSwapNeeded);
        return new GoogleTranslating(
                mTranslatingText,
                codes[0],
                codes[1]
        ).translate();
    }
}
