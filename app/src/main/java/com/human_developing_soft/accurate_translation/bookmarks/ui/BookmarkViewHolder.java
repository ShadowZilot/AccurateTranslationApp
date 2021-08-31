package com.human_developing_soft.accurate_translation.bookmarks.ui;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.human_developing_soft.accurate_translation.bookmarks.data.Bookmark;
import com.human_developing_soft.accurate_translation.databinding.BookmarkItemBinding;

public class BookmarkViewHolder extends RecyclerView.ViewHolder {
    private final BookmarkItemBinding mBinding;

    public BookmarkViewHolder(BookmarkItemBinding pBinding) {
        super(pBinding.getRoot());
        mBinding = pBinding;
    }

    void bind(BindingBookmark binding, OnBookmarkLongPressed observer) {
        mBinding.tapPanel.setOnClickListener((View v) -> onLongClicked(
                binding, observer
        ));
        binding.bind(
                mBinding.firstTranslation,
                mBinding.secondTranslation,
                mBinding.firstLanguageName,
                mBinding.secondLanguageName,
                mBinding.bookmarkTag
        );
    }

    private void onLongClicked(BindingBookmark bindingBookmark,
                                   OnBookmarkLongPressed observer) {
        observer.onLongPressed(
                bindingBookmark.dataBookmark()
        );
    }
}
