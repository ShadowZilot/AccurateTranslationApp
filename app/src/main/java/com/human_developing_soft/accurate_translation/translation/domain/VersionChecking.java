package com.human_developing_soft.accurate_translation.translation.domain;

import android.os.Build;

public interface VersionChecking {

    Boolean isNormalApi();

    class Base implements VersionChecking {
        private final Boolean mIsNormalApi;

        public Base() {
            mIsNormalApi = Build.VERSION.SDK_INT > 23;
        }

        @Override
        public Boolean isNormalApi() {
            return mIsNormalApi;
        }
    }
}
