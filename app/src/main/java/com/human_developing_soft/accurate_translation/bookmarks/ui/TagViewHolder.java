package com.human_developing_soft.accurate_translation.bookmarks.ui;

import androidx.recyclerview.widget.RecyclerView;

import com.human_developing_soft.accurate_translation.databinding.TagItemBinding;

public class TagViewHolder extends RecyclerView.ViewHolder {
    private final TagItemBinding mBinding;

    public TagViewHolder(TagItemBinding pBinding) {
        super(pBinding.getRoot());
        mBinding = pBinding;
    }

    void bind(String tag) {
        mBinding.tagText.setText(tag);
    }
}
