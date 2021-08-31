package com.human_developing_soft.accurate_translation.translation.common;

import com.human_developing_soft.accurate_translation.translation.ui.StringProvider;

import java.util.Timer;
import java.util.TimerTask;

public interface TextTimer {

    void updateTextData(String updatedText,
                        Boolean isFirst,
                        StringProvider provider);

    void updateImmediately(String updatedText,
                           Boolean isFirst,
                           StringProvider provider);

    class Base implements TextTimer {
        private final OnTranslationFieldChanged mObserver;
        private Timer mTimer;

        public Base(OnTranslationFieldChanged observer) {
            mObserver = observer;
        }

        @Override
        public void updateTextData(String updatedText,
                                   Boolean isFirst,
                                   StringProvider provider) {
            if (mTimer != null) {
                mTimer.cancel();
            }
            mTimer = new Timer();
            mTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    mObserver.translateText(
                            updatedText,
                            isFirst,
                            provider
                    );
                }
            }, 1000L);
        }

        @Override
        public void updateImmediately(String updatedText,
                                      Boolean isFirst,
                                      StringProvider provider) {
            mObserver.translateText(
                    updatedText,
                    isFirst,
                    provider
            );
        }
    }
}
