package com.human_developing_soft.accurate_translation.translation.common;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.human_developing_soft.accurate_translation.translation.ui.StringProvider;
import com.human_developing_soft.accurate_translation.translation.ui.TranslatingObserver;

public class TranslationFields implements TranslatingObserver {
    private final EditText mFirstField;
    private final EditText mSecondField;
    private TextWatcher mFirstListener;
    private TextWatcher mSecondListener;
    private TextTimer mTextTimer;
    private Boolean mIsLastFirst;

    public TranslationFields(EditText pFirstField,
                             EditText pSecondField,
                             OnTranslationFieldChanged pObserver) {
        mFirstField = pFirstField;
        mSecondField = pSecondField;
        mTextTimer = new TextTimer.Base(pObserver);
        mFirstField.setTag("free");
        mFirstListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (mFirstField.getTag().equals("free")) {
                    mIsLastFirst = true;
                    mTextTimer.updateTextData(s.toString(),
                            true,
                            new StringProvider.Base(mFirstField.getContext()));
                }
            }
        };
        mFirstField.addTextChangedListener(mFirstListener);
        mSecondField.setTag("free");
        mSecondListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (mSecondField.getTag().equals("free")) {
                    mIsLastFirst = true;
                    mTextTimer.updateTextData(s.toString(),
                            false,
                            new StringProvider.Base(mSecondField.getContext()));
                }
            }
        };
        mSecondField.addTextChangedListener(mSecondListener);
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

    public void updateFieldAfterMic(String translatingValue, Boolean isFirstField) {
        if (isFirstField) {
            mFirstField.setTag("blocked");
            mFirstField.setText(translatingValue);
            mFirstField.setTag("free");
        } else {
            mSecondField.setTag("blocked");
            mSecondField.setText(translatingValue);
            mSecondField.setTag("free");
        }
        mTextTimer.updateImmediately(
                translatingValue,
                isFirstField,
                new StringProvider.Base(mFirstField.getContext())
        );
    }

    public Boolean isFieldsNotEmpty() {
        return !mFirstField.getText().toString().isEmpty()
                && !mSecondField.getText().toString().isEmpty();
    }

    public void clearFields() {
        mFirstField.setTag("blocked");
        mSecondField.setTag("blocked");
        mFirstField.setText("");
        mSecondField.setText("");
        mFirstField.setTag("free");
        mFirstField.setTag("free");
    }

    public void clearListeners() {
        mFirstField.removeTextChangedListener(mFirstListener);
        mSecondField.removeTextChangedListener(mSecondListener);
        mFirstListener = null;
        mSecondListener = null;
        mTextTimer.clearObserver();
        mTextTimer = null;
    }

    public void swapLanguage() {
        mFirstField.setTag("blocked");
        mSecondField.setTag("blocked");
        String tmpText = mFirstField.getText().toString();
        mFirstField.setText(mSecondField.getText().toString());
        mSecondField.setText(tmpText);
        mFirstField.setTag("free");
        mSecondField.setTag("free");
        if (mIsLastFirst) {
            mTextTimer.updateImmediately(
                    mFirstField.getText().toString(),
                    true,
                    new StringProvider.Base(mFirstField.getContext())
            );
        } else {
            mTextTimer.updateImmediately(
                    mSecondField.getText().toString(),
                    false,
                    new StringProvider.Base(mFirstField.getContext())
            );
        }
    }
}
