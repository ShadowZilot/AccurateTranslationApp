package com.human_developing_soft.accurate_translation.translation.domain;

import android.widget.EditText;
import android.widget.ImageView;

import com.human_developing_soft.accurate_translation.R;
import com.human_developing_soft.accurate_translation.translation.data.HandledLanguage;
import com.human_developing_soft.accurate_translation.translation.ui.StringProvider;

import java.util.Locale;

public interface SelectedLanguages {

    String[] languagesCode(Boolean isSwapNeeded);

    SelectedLanguages updateLanguages(HandledLanguage firstLanguage,
                                      HandledLanguage secondLanguage);

    void initSelectors(ImageView firstSelector, ImageView secondSelector);

    String packedString();

    String[] countryCodes();

    Locale localeByLanguage(Boolean isFirst);

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
        public void initSelectors(ImageView firstSelector,
                                  ImageView secondSelector) {
            ImageLoading loader = new ImageLoading.Base(firstSelector.getContext());
            firstSelector.setImageDrawable(loader.flagByCountryCode(
                    mFirstLanguage.countryCode()
            ));
            secondSelector.setImageDrawable(loader.flagByCountryCode(
                    mSecondLanguage.countryCode()
            ));
        }


        @Override
        public String packedString() {
            return mFirstLanguage.toString() + "&" + mSecondLanguage.toString();
        }

        @Override
        public String[] countryCodes() {
            return new String[] {
                    mFirstLanguage.countryCode(),
                    mSecondLanguage.countryCode()
            };
        }

        @Override
        public Locale localeByLanguage(Boolean isFirst) {
            HandledLanguage language = isFirst ? mFirstLanguage : mSecondLanguage;
            return new Locale(
                language.languageCode(),
                language.countryCode()
            );
        }

        @Override
        public void initFieldHints(EditText firstField, EditText secondField) {
            StringProvider provider = new StringProvider.Base(firstField.getContext());
            if (mFirstLanguage.languageCode().equals("")) {
                firstField.setHint(provider.string(
                        R.string.select_language_message));
            } else {
                firstField.setHint(provider.string(R.string.field_hint,
                        mFirstLanguage.name()));
            }
            if (mSecondLanguage.languageCode().equals("")) {
                secondField.setHint(provider.string(
                        R.string.select_language_message));
            } else {
                secondField.setHint(provider.string(R.string.field_hint,
                        mSecondLanguage.name()));
            }
        }
    }
}
