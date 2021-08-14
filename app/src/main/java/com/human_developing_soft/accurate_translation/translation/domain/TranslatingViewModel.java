package com.human_developing_soft.accurate_translation.translation.domain;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Button;

import androidx.lifecycle.ViewModel;

import com.human_developing_soft.accurate_translation.R;
import com.human_developing_soft.accurate_translation.translation.data.HandledLanguage;
import com.human_developing_soft.accurate_translation.translation.data.HandledTranslating;
import com.human_developing_soft.accurate_translation.translation.data.Translating;
import com.human_developing_soft.accurate_translation.translation.ui.StringProvider;
import com.human_developing_soft.accurate_translation.translation.ui.TranslatingObserver;
import com.ibm.cloud.sdk.core.service.exception.BadRequestException;
import com.ibm.cloud.sdk.core.service.exception.NotFoundException;

import org.json.JSONException;

public class TranslatingViewModel extends ViewModel {
    private final TranslatingObserver mObserver;
    private Thread mLastThread;
    private SelectedLanguages mSelectedLanguage;

    public TranslatingViewModel(TranslatingObserver pObserver, Context context) {
        mObserver = pObserver;
        SharedPreferences preferences = context.getSharedPreferences("selectedLanguages",
                Context.MODE_PRIVATE);
        try {
            mSelectedLanguage = new SelectedLanguages.Base(preferences.getString("selected",
                    ""));
        } catch (IndexOutOfBoundsException e) {
            mSelectedLanguage = new SelectedLanguages.Base(
                new HandledLanguage.Dummy(),
                new HandledLanguage.Dummy()
            );
        }
    }

    public void translateText(String fieldText, Boolean isFirstField,
                              StringProvider provider) {
        Translating subject = new HandledTranslating(
                new Translating.Base(
                        fieldText,
                        mSelectedLanguage,
                        isFirstField
                )
        );
        Runnable runnable = () -> {
            try {
                String result = subject.translate();
                mObserver.updateField(result, !isFirstField);
            } catch (JSONException e) {
                mObserver.updateField(
                        provider.string(R.string.error_while_getting_response_label),
                        !isFirstField
                );
            } catch (BadRequestException e) {
                mObserver.updateField("", !isFirstField);
            }
            catch (NotFoundException e) {
                mObserver.updateField(
                        provider.string(R.string.language_not_support_for_translate_label),
                        !isFirstField);
            }
            catch (RuntimeException e) {
               mObserver.updateField(
                       provider.string(R.string.no_internet_connection_label),
                       !isFirstField);
            }
        };
        mLastThread = new Thread(runnable);
        mLastThread.start();
    }

    public void updateTranslatingLanguage(HandledLanguage firstLanguage,
                                          HandledLanguage secondLanguage,
                                          Context context) {
        mSelectedLanguage = mSelectedLanguage.updateLanguages(firstLanguage,
                secondLanguage);
        SharedPreferences preferences = context.getSharedPreferences("selectedLanguages",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("selected", mSelectedLanguage.packedString());
        editor.apply();
    }

    public void initUI(Button firstSelector,
                       Button secondSelector) {
        mSelectedLanguage.initSelectors(firstSelector, secondSelector);
    }
}
