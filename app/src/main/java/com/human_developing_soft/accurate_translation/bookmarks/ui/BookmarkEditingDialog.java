package com.human_developing_soft.accurate_translation.bookmarks.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.human_developing_soft.accurate_translation.R;
import com.human_developing_soft.accurate_translation.bookmarks.domain.BookmarkEditingVM;
import com.human_developing_soft.accurate_translation.bookmarks.domain.BookmarkEditingVMFactory;
import com.human_developing_soft.accurate_translation.databinding.BookmarkEditingDialogBinding;
import com.human_developing_soft.accurate_translation.translation.common.OnTranslationFieldChanged;
import com.human_developing_soft.accurate_translation.translation.common.TranslationFields;
import com.human_developing_soft.accurate_translation.translation.ui.StringProvider;
import com.human_developing_soft.accurate_translation.translation.ui.TranslatingObserver;

public class BookmarkEditingDialog extends DialogFragment
        implements OnTranslationFieldChanged, TranslatingObserver {
    private BindingBookmark mEditingBookmark;
    private BookmarkEditingDialogBinding mBinding;
    private BookmarkEditingVM mViewModel;
    private TranslationFields mFieldsManager;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        mEditingBookmark = new BindingBookmark.Base(requireArguments());
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
        mFieldsManager = new TranslationFields(mBinding.editingFirstTranslation,
                mBinding.editingSecondTranslation,
                this);
        mViewModel = new ViewModelProvider(this,
                new BookmarkEditingVMFactory(this,
                        requireContext(),
                        mBinding.editingFirstLanguageName.getText().toString(),
                        mBinding.editingSecondLanguageName.getText().toString()
                )
        ).get(BookmarkEditingVM.class);
        mBinding.applyEditingBtn.setOnClickListener((View v) -> {
            String firstTranslation = mBinding.editingFirstTranslation
                    .getText().toString();
            String secondTranslation = mBinding.editingSecondTranslation
                    .getText().toString();
            String tag = mBinding.editingBookmarkTagField.getText().toString();
            if (!firstTranslation.equals("")
                    && !secondTranslation.equals("")
                    && !tag.equals("")) {
                mViewModel.updateBookmark(mEditingBookmark.dataBookmark(
                        firstTranslation,
                        secondTranslation,
                        tag
                        ),
                        (Boolean isSuccess) -> requireActivity().runOnUiThread(() -> {
                                    String toastText = isSuccess ?
                                            getString(R.string.bookmark_updated_message)
                                            : getString(R.string.fail_update_bookmark_message);
                                    Toast.makeText(
                                            requireContext(),
                                            toastText,
                                            Toast.LENGTH_SHORT
                                    ).show();
                                }
                        ));
                sendResults(true);
            }
        });
        mBinding.cancelEditingBtn.setOnClickListener((View v) -> sendResults(false));
        mBinding.deletingButton.setOnClickListener((View v) -> {
            mViewModel.deleteBookmark(mEditingBookmark.dataBookmark(),
                    () -> requireActivity().runOnUiThread(() ->
                            Toast
                                    .makeText(requireContext(),
                                            R.string.bookmark_delete_message,
                                            Toast.LENGTH_SHORT)
                                    .show())
            );
            sendResults(true);
        });
        if (savedInstanceState != null) {
            mViewModel.updateObserver(this);
        }
        return mBinding.getRoot();
    }

    @Override
    public void translateText(String translationField,
                              Boolean isFirstField,
                              StringProvider provider) {
        mViewModel.translateText(translationField,
                isFirstField, provider);
    }

    @Override
    public void updateField(String translatingValue, Boolean isFirstField) {
        requireActivity().runOnUiThread(() ->
                mFieldsManager.updateField(translatingValue, isFirstField));
    }

    private void sendResults(Boolean isEdited) {
        Bundle result = new Bundle();
        result.putBoolean("isEdited", isEdited);
        getParentFragmentManager().setFragmentResult("isEdited", result);
        dismiss();
    }
}
