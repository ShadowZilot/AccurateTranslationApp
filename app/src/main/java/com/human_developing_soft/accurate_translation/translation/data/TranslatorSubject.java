package com.human_developing_soft.accurate_translation.translation.data;

public interface TranslatorSubject {
    String translate();

    class Base implements TranslatorSubject {
        private final String mTranslatingText;

        public Base(String pTranslatingText) {
            mTranslatingText = pTranslatingText;
        }

        @Override
        public String translate() {
            Translating translating = new Translating.Base(
                    mTranslatingText
            );
            return translating.implementTranslating();
        }
    }
}
