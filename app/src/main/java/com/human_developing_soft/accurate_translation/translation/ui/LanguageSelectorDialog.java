package com.human_developing_soft.accurate_translation.translation.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.human_developing_soft.accurate_translation.databinding.LanguageSelectorDialogBinding;
import com.human_developing_soft.accurate_translation.translation.data.HandledLanguage;
import com.human_developing_soft.accurate_translation.translation.domain.LanguageSelectorVM;
import com.human_developing_soft.accurate_translation.translation.domain.LanguageSelectorVMFactory;

import java.util.List;

public class LanguageSelectorDialog extends DialogFragment
        implements LanguagesObserver, OnLanguageSelectListener {
    private LanguageSelectorDialogBinding mBinding;
    private LanguageSelectorVM mViewModel;
    private LanguagesAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(
                this,
                new LanguageSelectorVMFactory(getContext(), this)
        ).get(LanguageSelectorVM.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = LanguageSelectorDialogBinding.inflate(inflater, container, false);
        mAdapter = new LanguagesAdapter(this);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(RecyclerView.VERTICAL);
        mBinding.listOfLanguages.setAdapter(mAdapter);
        mBinding.listOfLanguages.setLayoutManager(manager);
        mBinding.listOfLanguages.addItemDecoration(new DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
        ));
        return mBinding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        mBinding.languageProgress.setVisibility(View.VISIBLE);
        mViewModel.languages();
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void updateLanguages(List<HandledLanguage> languages) {
        mBinding.listOfLanguages.post(() -> {
            mAdapter.updateLanguages(languages);
            mBinding.languageProgress.setVisibility(View.GONE);
            mAdapter.notifyDataSetChanged();
        });
    }

    @Override
    public void onLanguageSelect(Integer languageIndex) {
        Bundle packedLanguage = mViewModel
                .languageByIndex(languageIndex)
                .languageBundle();
        getParentFragmentManager().setFragmentResult(
                requireArguments().getString("requestCode"),
                packedLanguage
        );
        dismiss();
    }
}
