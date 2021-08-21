package com.human_developing_soft.accurate_translation.translation.domain;

import android.widget.Button;
import android.widget.EditText;

import com.human_developing_soft.accurate_translation.OnLanguageUpdated;
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

public class DomainTranslator implements OnTranslationFieldChanged {
    private final TranslatingObserver mObserver;
    private Thread mLastThread;
    private SelectedLanguages mSelectedLanguage;

    public DomainTranslator(TranslatingObserver pObserver,
                            SelectedLanguages pSelectedLanguages) {
        mSelectedLanguage = pSelectedLanguages;
        mObserver = pObserver;
    }

    public void updateLanguages(HandledLanguage firstLanguage,
                                HandledLanguage secondLanguage,
                                OnLanguageUpdated observer) {
        mSelectedLanguage = mSelectedLanguage.updateLanguages(firstLanguage,
                secondLanguage);
        observer.onLanguageUpdate(mSelectedLanguage);
    }

    public void initSelectors(Button firstSelector,
                              Button secondSelector) {
        mSelectedLanguage.initSelectors(firstSelector, secondSelector);
    }

    public void initFields(EditText firstField,
                           EditText secondField) {
        mSelectedLanguage.initFieldHints(firstField, secondField);
    }

    @Override
    public void translateText(String translationField,
                              Boolean isFirstField,
                              StringProvider provider) {
        Translating subject = new HandledTranslating(
                new PreTranslating.Base(
                        translationField,
                        mSelectedLanguage,
                        isFirstField
                )
        );
        Runnable runnable = () -> {
            try {
                if (!mLastThread.isInterrupted()) {
                    String result = subject.translate();
                    mObserver.updateField(result, !isFirstField);
                }
            } catch (JSONException e) {
                mObserver.updateField(
                        provider.string(R.string.error_while_getting_response_label),
                        !isFirstField
                );
            } catch (NotFoundException e) {
                mObserver.updateField(
                        provider.string(R.string.language_not_support_for_translate_label),
                        !isFirstField);
            } catch (RuntimeException e) {
                mObserver.updateField(
                        "",
                        !isFirstField);
            }
        };

        if (mLastThread != null) {
            mLastThread.interrupt();
        }

        mLastThread = new Thread(runnable);
        mLastThread.start();
    }
}
