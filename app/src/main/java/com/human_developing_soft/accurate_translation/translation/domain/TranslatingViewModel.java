package com.human_developing_soft.accurate_translation.translation.domain;

import android.widget.Button;
import android.widget.EditText;

import androidx.lifecycle.ViewModel;

import com.human_developing_soft.accurate_translation.OnTranslationFieldChanged;
import com.human_developing_soft.accurate_translation.R;
import com.human_developing_soft.accurate_translation.translation.data.HandledLanguage;
import com.human_developing_soft.accurate_translation.translation.data.HandledTranslating;
import com.human_developing_soft.accurate_translation.translation.data.PreTranslating;
import com.human_developing_soft.accurate_translation.translation.data.Translating;
import com.human_developing_soft.accurate_translation.translation.ui.StringProvider;
import com.human_developing_soft.accurate_translation.translation.ui.TranslatingObserver;
import com.ibm.cloud.sdk.core.service.exception.NotFoundException;

import org.json.JSONException;

public class TranslatingViewModel extends ViewModel implements OnTranslationFieldChanged {
    private final DomainTranslator mTranslator;

    public TranslatingViewModel(TranslatingObserver pObserver,
                                CachedSelectedLanguages cache) {
        mTranslator = new DomainTranslator(
                pObserver,
                cache.cachedSelectedLanguage()
        );
    }

    @Override
    public void translateText(String fieldText, Boolean isFirstField,
                              StringProvider provider) {
        mTranslator.translateText(fieldText, isFirstField,
                provider);
    }

    public void updateTranslatingLanguage(HandledLanguage firstLanguage,
                                          HandledLanguage secondLanguage,
                                          CachedSelectedLanguages cache) {
        mTranslator.updateLanguages(firstLanguage, secondLanguage);
        cache.cacheSelectedLanguage(new SelectedLanguages.Base(
                firstLanguage, secondLanguage
        ));
    }

    public void initUI(Button firstSelector,
                       Button secondSelector,
                       EditText firstField,
                       EditText secondField) {
        mTranslator.initSelectors(firstSelector, secondSelector);
        mTranslator.initFields(firstField, secondField);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
