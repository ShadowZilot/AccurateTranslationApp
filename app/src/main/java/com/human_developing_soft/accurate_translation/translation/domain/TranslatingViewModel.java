package com.human_developing_soft.accurate_translation.translation.domain;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.lifecycle.ViewModel;

import com.human_developing_soft.accurate_translation.translation.common.OnTranslationFieldChanged;
import com.human_developing_soft.accurate_translation.translation.data.HandledLanguage;
import com.human_developing_soft.accurate_translation.translation.ui.StringProvider;
import com.human_developing_soft.accurate_translation.translation.ui.TranslatingObserver;

public class TranslatingViewModel extends ViewModel implements OnTranslationFieldChanged {
    private DomainTranslator mTranslator;

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
        mTranslator.updateLanguages(firstLanguage, secondLanguage,
                cache::cacheSelectedLanguage);
    }

    public String languageName(Boolean isFirst) {
        if (isFirst) {
            return mTranslator.languagesName()[0];
        } else {
            return mTranslator.languagesName()[1];
        }
    }

    public void initUI(ImageView firstSelector,
                       ImageView secondSelector,
                       EditText firstField,
                       EditText secondField) {
        mTranslator.initSelectors(firstSelector, secondSelector);
        mTranslator.initFields(firstField, secondField);
    }

    public void updateObserver(TranslatingObserver newObserver) {
        mTranslator.updateObserver(newObserver);
    }
}
