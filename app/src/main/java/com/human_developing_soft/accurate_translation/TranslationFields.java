package com.human_developing_soft.accurate_translation;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.human_developing_soft.accurate_translation.translation.ui.StringProvider;
import com.human_developing_soft.accurate_translation.translation.ui.TranslatingObserver;

public class TranslationFields implements TranslatingObserver {
    private final EditText mFirstField;
    private final EditText mSecondField;
    private final OnTranslationFieldChanged mObserver;

    public TranslationFields(EditText pFirstField,
                             EditText pSecondField,
                             OnTranslationFieldChanged pObserver) {
        mFirstField = pFirstField;
        mSecondField = pSecondField;
        mObserver = pObserver;
        mFirstField.setTag("free");
        mFirstField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mFirstField.getTag().equals("free")) {
                    mObserver.translateText(s.toString(),
                            true,
                            new StringProvider.Base(mFirstField.getContext()));
                }
            }
        });
        mSecondField.setTag("free");
        mSecondField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mSecondField.getTag().equals("free")) {
                    mObserver.translateText(s.toString(),
                            false,
                            new StringProvider.Base(mSecondField.getContext()));
                }
            }
        });
    }

    @Override
    public void updateField(String translatingValue, Boolean isFirstField) {
        if (isFirstField) {
            mFirstField.setTag("blocked");
            mFirstField.setText(translatingValue);
            mFirstField.setTag("free");
        } else {
            mSecondField.setTag("blocked");
            mSecondField.setText(translatingValue);
            mSecondField.setTag("free");
        }
    }
}
