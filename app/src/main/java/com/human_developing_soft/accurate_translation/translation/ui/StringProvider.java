package com.human_developing_soft.accurate_translation.translation.ui;

import android.content.Context;

import androidx.annotation.StringRes;

public interface StringProvider {

    String string(@StringRes Integer resId);

    String string(@StringRes Integer resId, Object... formatParameters);

    class Base implements StringProvider {
        private final Context mContext;

        public Base(Context pContext) {
            mContext = pContext;
        }

        @Override
        public String string(@StringRes Integer resId) {
            return mContext.getString(resId);
        }

        @Override
        public String string(@StringRes Integer resId, Object... formatParameters) {
            return mContext.getString(resId, formatParameters);
        }
    }
}
