package com.human_developing_soft.accurate_translation.camera.ui;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.human_developing_soft.accurate_translation.camera.domain.CameraVMFactory;
import com.human_developing_soft.accurate_translation.camera.domain.CameraViewModel;
import com.human_developing_soft.accurate_translation.databinding.CameraActivityBinding;

public class CameraActivity extends AppCompatActivity {
    private CameraActivityBinding mBinding;
    private CameraViewModel mViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = CameraActivityBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        mViewModel = new ViewModelProvider(this,
                new CameraVMFactory(this,
                        getIntent().getStringExtra("countryCode")))
                .get(CameraViewModel.class);
        mBinding.cancelButton.setOnClickListener((View v) -> finish());
        mBinding.languageCircle.setImageDrawable(
                mViewModel.languageImage()
        );
        mBinding.languageName.setText(
                mViewModel.languageName()
        );
    }
}
