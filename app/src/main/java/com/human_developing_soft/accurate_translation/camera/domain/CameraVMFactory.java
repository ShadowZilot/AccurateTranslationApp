package com.human_developing_soft.accurate_translation.camera.domain;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class CameraVMFactory implements ViewModelProvider.Factory {
    private final Context mContext;
    private final String mCountryCode;

    public CameraVMFactory(Context mContext, String mCountryCode) {
        this.mContext = mContext;
        this.mCountryCode = mCountryCode;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new CameraViewModel(mContext, mCountryCode);
    }
}
