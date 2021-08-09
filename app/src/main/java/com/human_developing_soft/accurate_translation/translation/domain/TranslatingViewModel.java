package com.human_developing_soft.accurate_translation.translation.domain;

import androidx.lifecycle.ViewModel;

import com.human_developing_soft.accurate_translation.translation.data.HandledTranslating;
import com.human_developing_soft.accurate_translation.translation.data.Translating;
import com.human_developing_soft.accurate_translation.translation.ui.TranslatingObserver;

import org.json.JSONException;

public class TranslatingViewModel extends ViewModel {
    private final TranslatingObserver mObserver;

    public TranslatingViewModel(TranslatingObserver pObserver) {
        mObserver = pObserver;
    }

    public void translateText(String fieldText) {
        Translating subject = new HandledTranslating(
                new Translating.Base(
                        fieldText,
                        "en",
                        "ru"
                )
        );
        Runnable runnable = () -> {
            try {
                String result = subject.translate();
                mObserver.updateField(result);
            } catch (JSONException e) {
                mObserver.updateField("Error!");
            }
        };
        new Thread(runnable)
                .start();
    }
}
