package com.human_developing_soft.accurate_translation.translation.ui;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.human_developing_soft.accurate_translation.databinding.LanguageSelectorItemBinding;
import com.human_developing_soft.accurate_translation.translation.data.HandledLanguage;

public class LanguageViewHolder extends RecyclerView.ViewHolder {
    private final LanguageSelectorItemBinding mBinding;

    public LanguageViewHolder(@NonNull LanguageSelectorItemBinding binding) {
        super(binding.getRoot());
        mBinding = binding;
    }

    void bind(HandledLanguage language,
              OnLanguageSelectListener pObserver) {
        mBinding.languageName.setText(language.name());
        mBinding.getRoot().setOnClickListener((View v) ->
                pObserver.onLanguageSelect(getAdapterPosition()));
    }
}
