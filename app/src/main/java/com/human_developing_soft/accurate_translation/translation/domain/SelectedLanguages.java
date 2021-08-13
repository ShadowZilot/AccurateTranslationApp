package com.human_developing_soft.accurate_translation.translation.domain;

public interface SelectedLanguages {

    String languagesCode(Boolean isSwapNeeded);

    SelectedLanguages updateLanguages(String firstLanguage, String secondLanguage);

    class Base implements SelectedLanguages {
        private final String mFirstLanguage;
        private final String mSecondLanguage;

        public Base(String pFirstLanguage, String pSecondLanguage) {
            mFirstLanguage = pFirstLanguage;
            mSecondLanguage = pSecondLanguage;
        }

        @Override
        public String languagesCode(Boolean isSwapNeeded) {
            if (isSwapNeeded) {
                return String.format("%s-%s",
                        mFirstLanguage,
                        mSecondLanguage);
            } else {
                return String.format("%s-%s",
                        mSecondLanguage,
                        mFirstLanguage);
            }
        }

        @Override
        public SelectedLanguages updateLanguages(String firstLanguage, String secondLanguage) {
            return new Base(
                    firstLanguage.equals("") ? mFirstLanguage : firstLanguage,
                    secondLanguage.equals("") ? mSecondLanguage : secondLanguage
            );
        }
    }
}