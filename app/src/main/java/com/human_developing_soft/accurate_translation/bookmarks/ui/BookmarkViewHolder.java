package com.human_developing_soft.accurate_translation.bookmarks.ui;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.human_developing_soft.accurate_translation.bookmarks.data.Bookmark;
import com.human_developing_soft.accurate_translation.databinding.BookmarkItemBinding;

public class BookmarkViewHolder extends RecyclerView.ViewHolder {
    private final BookmarkItemBinding mBinding;
    private boolean mIsSingleLine = true;

    public BookmarkViewHolder(BookmarkItemBinding pBinding) {
        super(pBinding.getRoot());
        mBinding = pBinding;
    }

    void bind(BindingBookmark binding, OnBookmarkLongPressed observer) {
        mBinding.tapPanel.setOnLongClickListener((View v) -> onLongClicked(
                binding, observer
        ));
        mBinding.firstTranslation.setOnLongClickListener((View v) -> onLongClicked(
                binding, observer
        ));
        mBinding.secondTranslation.setOnLongClickListener((View v) -> onLongClicked(
                binding, observer
        ));
        mBinding.secondTranslation.setOnClickListener((View v) -> updateMaxLines());
        mBinding.firstTranslation.setOnClickListener((View v) -> updateMaxLines());
        binding.bind(
                mBinding.firstTranslation,
                mBinding.secondTranslation,
                mBinding.firstLanguageName,
                mBinding.secondLanguageName,
                mBinding.bookmarkTag
        );
    }

    private boolean onLongClicked(BindingBookmark bindingBookmark,
                                   OnBookmarkLongPressed observer) {
        observer.onLongPressed(
                bindingBookmark.dataBookmark()
        );
        return true;
    }

    private void updateMaxLines() {
        mIsSingleLine = !mIsSingleLine;
        mBinding.firstTranslation.setSingleLine(mIsSingleLine);
        mBinding.secondTranslation.setSingleLine(mIsSingleLine);
    }
}
