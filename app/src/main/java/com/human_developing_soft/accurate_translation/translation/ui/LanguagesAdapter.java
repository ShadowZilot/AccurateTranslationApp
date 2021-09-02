package com.human_developing_soft.accurate_translation.translation.ui;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.human_developing_soft.accurate_translation.databinding.LanguageSelectorItemBinding;
import com.human_developing_soft.accurate_translation.translation.data.HandledLanguage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LanguagesAdapter
        extends RecyclerView.Adapter<LanguageViewHolder>
        implements OnLanguageSelectListener {
    private final OnLanguageSelectListener mObserver;
    private final List<HandledLanguage> mLanguages;

    public LanguagesAdapter(OnLanguageSelectListener pObserver,
                            HandledLanguage... languages) {
        mObserver = pObserver;
        mLanguages = new ArrayList<>(
                Arrays.asList(languages)
        );
    }

    public LanguagesAdapter(OnLanguageSelectListener pObserver) {
        this(pObserver, new HandledLanguage[0]);
    }

    @NonNull
    @Override
    public LanguageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LanguageViewHolder(
                LanguageSelectorItemBinding.inflate(
                        LayoutInflater.from(parent.getContext()),
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull LanguageViewHolder holder, int position) {
        holder.bind(
            mLanguages.get(position),
            this
        );
    }

    @Override
    public int getItemCount() {
        return mLanguages.size();
    }

    @Override
    public void onLanguageSelect(Integer languageIndex) {
        mObserver.onLanguageSelect(languageIndex);
    }

    public void updateLanguages(List<HandledLanguage> languages) {
        mLanguages.clear();
        mLanguages.addAll(languages);
    }
}
