package com.human_developing_soft.accurate_translation.translation.data;

import com.human_developing_soft.accurate_translation.translation.data.api.GoogleApi;
import com.human_developing_soft.accurate_translation.translation.data.api.IbmApi;
import com.human_developing_soft.accurate_translation.translation.data.api.ibm.HandledTranslating;
import com.human_developing_soft.accurate_translation.translation.data.api.ibm.Translating;
import com.human_developing_soft.accurate_translation.translation.domain.SelectedLanguages;
import com.human_developing_soft.accurate_translation.translation.domain.VersionChecking;

import org.json.JSONException;

public class TranslatingChoosing implements Translating {
    private final VersionChecking mChecking;
    private final String mTranslatingText;
    private final SelectedLanguages mSelectedLanguages;
    private final Boolean mIsSwapNeeded;

    public TranslatingChoosing(String translatingText,
                               SelectedLanguages selectedLanguages,
                               Boolean isSwapNeeded) {
        mChecking = new VersionChecking.Base(translatingText);
        mTranslatingText = translatingText;
        mSelectedLanguages = selectedLanguages;
        mIsSwapNeeded = isSwapNeeded;
    }

    @Override
    public String translate() throws JSONException {
        return new com.human_developing_soft.accurate_translation.translation
                .data.api.google.HandledTranslating(new GoogleApi(
                mTranslatingText,
                mSelectedLanguages,
                mIsSwapNeeded
        )
        ).translate();
    }
}
