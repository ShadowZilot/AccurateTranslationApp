package com.human_developing_soft.accurate_translation.translation.domain;

import androidx.lifecycle.ViewModel;

import com.human_developing_soft.accurate_translation.translation.data.HandledTranslating;
import com.human_developing_soft.accurate_translation.translation.data.Translating;
import com.human_developing_soft.accurate_translation.translation.ui.TranslatingObserver;
import com.ibm.cloud.sdk.core.service.exception.BadRequestException;
import com.ibm.cloud.sdk.core.service.exception.NotFoundException;

import org.json.JSONException;

public class TranslatingViewModel extends ViewModel {
    private final TranslatingObserver mObserver;
    private SelectedLanguages mSelectedLanguage = new SelectedLanguages.Base(
            "en",
            "ru"
    );

    public TranslatingViewModel(TranslatingObserver pObserver) {
        mObserver = pObserver;
    }

    public void translateText(String fieldText) {
        Translating subject = new HandledTranslating(
                new Translating.Base(
                        fieldText,
                        mSelectedLanguage
                )
        );
        Runnable runnable = () -> {
            try {
                String result = subject.translate();
                mObserver.updateField(result);
            } catch (JSONException e) {
                mObserver.updateField("Error!");
            } catch (BadRequestException e) {
                mObserver.updateField("");
            }
            catch (NotFoundException e) {
                mObserver.updateField("This languages not supported for translating");
            }
        };
        new Thread(runnable)
                .start();
    }

    public void updateTranslatingLanguage(String firstLanguage,
                                          String secondLanguage) {
        mSelectedLanguage = mSelectedLanguage.updateLanguages(firstLanguage,
                secondLanguage);
    }
}
