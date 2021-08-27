package com.human_developing_soft.accurate_translation.translation.domain;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;

import com.human_developing_soft.accurate_translation.R;

import java.io.IOException;
import java.io.InputStream;

public interface ImageLoading {

    Drawable flagByCountryCode(String countryCode);

    class Base implements ImageLoading {
        private final Context mContext;

        public Base(Context mContext) {
            this.mContext = mContext;
        }

        @Override
        public Drawable flagByCountryCode(String countryCode) {
            AssetManager manager = mContext.getAssets();
            try {
                InputStream stream = manager.open(String.format("flags/%s.png",
                        countryCode));
                return Drawable.createFromStream(stream, null);
            } catch (IOException e) {
                return mContext.getDrawable(R.drawable.ic_no_select_language);
            }
        }
    }
}
