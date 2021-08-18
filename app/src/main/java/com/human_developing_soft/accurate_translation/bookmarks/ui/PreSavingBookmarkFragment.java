package com.human_developing_soft.accurate_translation.bookmarks.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.human_developing_soft.accurate_translation.databinding.PreSavingBookmarkFragmentBinding;

public class PreSavingBookmarkFragment extends DialogFragment {
    private PreSavingBookmarkFragmentBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = PreSavingBookmarkFragmentBinding.inflate(inflater,
                container,
                false);
        return mBinding.getRoot();
    }
}
