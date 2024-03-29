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
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.human_developing_soft.accurate_translation.R;
import com.human_developing_soft.accurate_translation.bookmarks.domain.PreSavingBookmarkVM;
import com.human_developing_soft.accurate_translation.bookmarks.domain.PreSavingBookmarkVMFactory;
import com.human_developing_soft.accurate_translation.databinding.PreSavingBookmarkFragmentBinding;
import com.human_developing_soft.accurate_translation.translation.domain.TagLoadingObserver;

import java.util.List;

public class PreSavingBookmarkFragment extends DialogFragment
        implements TagLoadingObserver, OnTagPressed {
    private PreSavingBookmarkFragmentBinding mBinding;
    private PreSavingBookmarkVM mViewModel;
    private TagsAdapter mAdapter;
    private BookmarkArguments mArguments;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        mArguments = new BookmarkArguments.Base(requireArguments());
        mBinding = PreSavingBookmarkFragmentBinding.inflate(getLayoutInflater());
        return new AlertDialog.Builder(requireContext())
                .setView(mBinding.getRoot())
                .create();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this,
                new PreSavingBookmarkVMFactory(
                        requireContext(),
                        this
                )
        ).get(PreSavingBookmarkVM.class);
        if (savedInstanceState != null) {
            mViewModel.updateObserver(this);
        }
        mAdapter = new TagsAdapter(this);
        mBinding.listOfPreviousTags.setLayoutManager(new LinearLayoutManager(requireContext(),
                RecyclerView.VERTICAL, false));
        mBinding.listOfPreviousTags.addItemDecoration(new DividerItemDecoration(requireContext(),
                DividerItemDecoration.VERTICAL));
        mBinding.listOfPreviousTags.setAdapter(mAdapter);
        mBinding.saveBookmarkButton.setOnClickListener((View v) -> {
            if (!mBinding.newTagField.getText().toString().isEmpty()) {
                mViewModel.saveBookmark(
                        mArguments
                                .bookmark(
                                        mBinding
                                                .newTagField
                                                .getText()
                                                .toString()
                                ),
                        (Boolean isSuccess) -> {
                            String toastText = getString(R.string.fail_saving_bookmark_message);
                            if (isSuccess) {
                                toastText = getString(R.string.success_saving_bookmark_message);
                            }
                            String finalToastText = toastText;
                            mBinding.getRoot().post(() -> {
                                Toast.makeText(
                                        requireContext(),
                                        finalToastText,
                                        Toast.LENGTH_SHORT
                                ).show();
                            });
                        }
                );
                dismiss();
            }
        });
        mBinding.cancelSavingButton.setOnClickListener((View v) -> dismiss());
        return mBinding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        mViewModel.loadTag();
    }

    @Override
    public void onTagLoaded(List<String> tags) {
        requireActivity().runOnUiThread(() -> mAdapter.onTagLoaded(tags));
    }

    @Override
    public void onPressTag(String tagName) {
        mBinding.newTagField.setText(tagName);
    }
}
