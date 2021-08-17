package com.human_developing_soft.accurate_translation.translation.domain;

import android.widget.Button;
import android.widget.EditText;

import androidx.lifecycle.ViewModel;

import com.human_developing_soft.accurate_translation.R;
import com.human_developing_soft.accurate_translation.translation.data.HandledLanguage;
import com.human_developing_soft.accurate_translation.translation.data.HandledTranslating;
import com.human_developing_soft.accurate_translation.translation.data.PreTranslating;
import com.human_developing_soft.accurate_translation.translation.data.Translating;
import com.human_developing_soft.accurate_translation.translation.ui.StringProvider;
import com.human_developing_soft.accurate_translation.translation.ui.TranslatingObserver;
import com.ibm.cloud.sdk.core.service.exception.NotFoundException;

import org.json.JSONException;

public class TranslatingViewModel extends ViewModel {
    private final TranslatingObserver mObserver;
    private Thread mLastThread;
    private SelectedLanguages mSelectedLanguage;

    public TranslatingViewModel(TranslatingObserver pObserver,
                                CachedSelectedLanguages cache) {
        mObserver = pObserver;
        mSelectedLanguage = cache.cachedSelectedLanguage();
    }

    public void translateText(String fieldText, Boolean isFirstField,
                              StringProvider provider) {
        Translating subject = new HandledTranslating(
                new PreTranslating.Base(
                        fieldText,
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
            }
            catch (NotFoundException e) {
                mObserver.updateField(
                        provider.string(R.string.language_not_support_for_translate_label),
                        !isFirstField);
            }
            catch (RuntimeException e) {
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

    public void updateTranslatingLanguage(HandledLanguage firstLanguage,
                                          HandledLanguage secondLanguage,
                                          CachedSelectedLanguages cache) {
        mSelectedLanguage = mSelectedLanguage.updateLanguages(firstLanguage,
                secondLanguage);
        cache.cacheSelectedLanguage(mSelectedLanguage);
    }

    public void initUI(Button firstSelector,
                       Button secondSelector,
                       EditText firstField,
                       EditText secondField) {
        mSelectedLanguage.initSelectors(firstSelector, secondSelector);
        mSelectedLanguage.initFieldHints(firstField, secondField);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
