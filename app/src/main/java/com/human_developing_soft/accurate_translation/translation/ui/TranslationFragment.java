package com.human_developing_soft.accurate_translation.translation.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.human_developing_soft.accurate_translation.databinding.TranslationFragmentBinding;
import com.human_developing_soft.accurate_translation.translation.data.TranslatorSubject;

public class TranslationFragment extends Fragment {
    private TranslationFragmentBinding mBinding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = TranslationFragmentBinding.inflate(
                inflater,
                container,
                false);

        return mBinding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        TranslatorSubject subject = new TranslatorSubject.Base(
                "Hello World!"
        );
        Runnable runnable = () -> {
                String result = subject.translate();
                mBinding.testContent.post(() -> mBinding.testContent.setText(result));
        };
        new Thread(runnable).start();
    }
}
