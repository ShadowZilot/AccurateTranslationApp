package com.human_developing_soft.accurate_translation.translation.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.human_developing_soft.accurate_translation.databinding.TranslationFragmentBinding;
import com.human_developing_soft.accurate_translation.translation.domain.TranslatingVMFactory;
import com.human_developing_soft.accurate_translation.translation.domain.TranslatingViewModel;

public class TranslationFragment extends Fragment implements TranslatingObserver {
    private TranslationFragmentBinding mBinding;
    private TranslatingViewModel mViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = TranslationFragmentBinding.inflate(
                inflater,
                container,
                false);

        mViewModel = new ViewModelProvider(this,
                new TranslatingVMFactory(this)
        ).get(TranslatingViewModel.class);

        mBinding.firstLanguageField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                mViewModel.translateText(s.toString());
            }
        });

        return mBinding.getRoot();
    }

    @Override
    public void updateField(String translatingValue) {
        mBinding.secondLanguageField.post(
                () -> mBinding.secondLanguageField.setText(translatingValue));
    }
}
