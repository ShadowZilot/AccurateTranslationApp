package com.human_developing_soft.accurate_translation.camera.domain;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.lifecycle.ViewModel;

import com.human_developing_soft.accurate_translation.translation.data.LanguageStorage;
import com.human_developing_soft.accurate_translation.translation.data.LanguagesDatabase;
import com.human_developing_soft.accurate_translation.translation.domain.ImageLoading;

public class CameraViewModel extends ViewModel {
    private final LanguageStorage mLanguageStorage;
    private final Drawable mLanguageImage;
    private final String mLanguageCountryCode;

    public CameraViewModel(Context context, String languageCountryCode) {
        mLanguageStorage = LanguagesDatabase.instance(context);
        ImageLoading loading = new ImageLoading.Base(context);
        mLanguageImage = loading.flagByCountryCode(
                languageCountryCode);
        mLanguageCountryCode = languageCountryCode;
    }

    public Drawable languageImage() {
        return mLanguageImage;
    }

    public String languageName() {
        return mLanguageStorage
                .languageByCountry(mLanguageCountryCode)
                .name();
    }
}
