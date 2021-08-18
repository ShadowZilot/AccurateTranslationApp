package com.human_developing_soft.accurate_translation.bookmarks.ui;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.human_developing_soft.accurate_translation.databinding.TagItemBinding;

public class TagViewHolder extends RecyclerView.ViewHolder {
    private final TagItemBinding mBinding;

    public TagViewHolder(TagItemBinding pBinding) {
        super(pBinding.getRoot());
        mBinding = pBinding;
    }

    void bind(BookmarkTag tag, OnTagPressed listener) {
        mBinding.tagText.setText(tag.tagName());
        mBinding.isSelectedTag.setChecked(tag.isChecked());
        mBinding.isSelectedTag.setOnClickListener((View v) ->
                listener.onPressTag(getAdapterPosition()));
    }
}
