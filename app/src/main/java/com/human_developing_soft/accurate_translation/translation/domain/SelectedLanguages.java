package com.human_developing_soft.accurate_translation.translation.domain;

import android.widget.Button;
import android.widget.EditText;

import com.human_developing_soft.accurate_translation.R;
import com.human_developing_soft.accurate_translation.translation.data.HandledLanguage;
import com.human_developing_soft.accurate_translation.translation.ui.StringProvider;

public interface SelectedLanguages {

    String[] languagesCode(Boolean isSwapNeeded);

    SelectedLanguages updateLanguages(HandledLanguage firstLanguage,
                                      HandledLanguage secondLanguage);

    void initSelectors(Button firstSelector, Button secondSelector);

    String packedString();

    void initFieldHints(EditText firstField, EditText secondField);

    class Base implements SelectedLanguages {
        private final HandledLanguage mFirstLanguage;
        private final HandledLanguage mSecondLanguage;

        public Base(HandledLanguage pFirstLanguage, HandledLanguage pSecondLanguage) {
            mFirstLanguage = pFirstLanguage;
            mSecondLanguage = pSecondLanguage;
        }

        public Base(String initValue) {
            this(
                    new HandledLanguage.Base(initValue.split("&")[0]),
                    new HandledLanguage.Base(initValue.split("&")[1])
            );
        }

        @Override
        public String[] languagesCode(Boolean isSwapNeeded) {
            if (isSwapNeeded) {
                return new String[]{
                        mFirstLanguage.languageCode(),
                        mSecondLanguage.languageCode()
                };
            } else {
                return new String[]{
                        mSecondLanguage.languageCode(),
                        mFirstLanguage.languageCode()
                };
            }
        }

        @Override
        public SelectedLanguages updateLanguages(HandledLanguage firstLanguage,
                                                 HandledLanguage secondLanguage) {
            return new Base(
                    firstLanguage.languageCode().equals("") ? mFirstLanguage : firstLanguage,
                    secondLanguage.languageCode().equals("") ? mSecondLanguage : secondLanguage
            );
        }

        @Override
        public void initSelectors(Button firstSelector, Button secondSelector) {
            firstSelector.setText(mFirstLanguage.name());
            secondSelector.setText(mSecondLanguage.name());
        }

        @Override
        public String packedString() {
            return mFirstLanguage.toString() + "&" + mSecondLanguage.toString();
        }

        @Override
        public void initFieldHints(EditText firstField, EditText secondField) {
            StringProvider provider = new StringProvider.Base(firstField.getContext());
            firstField.setHint(provider.string(R.string.field_hint,
                    mFirstLanguage.name()));
            secondField.setHint(provider.string(R.string.field_hint,
                    mSecondLanguage.name()));
        }
    }
}
