package com.human_developing_soft.accurate_translation.bookmarks.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.human_developing_soft.accurate_translation.databinding.BookmarkEditingDialogBinding;

public class BookmarkEditingDialog extends DialogFragment {
    private final BindingBookmark mEditingBookmark;
    private BookmarkEditingDialogBinding mBinding;

    public BookmarkEditingDialog(BindingBookmark pEditingBookmark) {
        mEditingBookmark = pEditingBookmark;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        mBinding = BookmarkEditingDialogBinding.inflate(getLayoutInflater());
        return new AlertDialog.Builder(requireContext())
                .setView(mBinding.getRoot())
                .create();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mEditingBookmark.bind(
                mBinding.editingFirstTranslation,
                mBinding.editingSecondTranslation,
                mBinding.editingFirstLanguageName,
                mBinding.editingSecondLanguageName,
                mBinding.editingBookmarkTagField
        );
        return mBinding.getRoot();
    }
}
