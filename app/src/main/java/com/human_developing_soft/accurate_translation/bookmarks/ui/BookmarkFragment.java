package com.human_developing_soft.accurate_translation.bookmarks.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.human_developing_soft.accurate_translation.R;
import com.human_developing_soft.accurate_translation.bookmarks.data.Bookmark;
import com.human_developing_soft.accurate_translation.bookmarks.domain.BookmarkVMFactory;
import com.human_developing_soft.accurate_translation.bookmarks.domain.BookmarkViewModel;
import com.human_developing_soft.accurate_translation.databinding.BookmarkFragmentBinding;

import java.util.List;

public class BookmarkFragment extends Fragment implements BookmarkObserver {
    private BookmarkFragmentBinding mBinding;
    private BookmarkViewModel mViewModel;
    private BookmarkAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = BookmarkFragmentBinding.inflate(inflater,
                container,
                false);
        mBinding.bookmarkSearchField.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mViewModel.searchBookmark(query);
                mBinding.bookmarksList.setVisibility(View.GONE);
                mBinding.bookmarkProgress.setVisibility(View.VISIBLE);
                mBinding.emptyBookmarksView.setVisibility(View.GONE);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mViewModel.searchBookmark(newText);
                mBinding.bookmarksList.setVisibility(View.GONE);
                mBinding.bookmarkProgress.setVisibility(View.VISIBLE);
                mBinding.emptyBookmarksView.setVisibility(View.GONE);
                return true;
            }
        });
        mAdapter = new BookmarkAdapter();
        mBinding.bookmarksList.setAdapter(mAdapter);
        mBinding.bookmarksList.setLayoutManager(new LinearLayoutManager(requireContext(),
                RecyclerView.VERTICAL, false));
        mViewModel = new ViewModelProvider(this,
                new BookmarkVMFactory(requireContext(), this)).get(BookmarkViewModel.class);
        return mBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        mBinding.bookmarksList.setVisibility(View.GONE);
        mBinding.bookmarkProgress.setVisibility(View.VISIBLE);
        mBinding.emptyBookmarksView.setVisibility(View.GONE);
        mViewModel.bookmarks();
    }

    @Override
    public void onBookmarkUpdate(List<Bookmark> bookmarks) {
        mBinding.getRoot().post(() -> {
            mBinding.bookmarkProgress.setVisibility(View.GONE);
            if (bookmarks.isEmpty()) {
                mBinding.emptyBookmarksView.setVisibility(View.VISIBLE);
                if (mBinding.bookmarkSearchField.getQuery().toString().isEmpty()) {
                    mBinding.emptyBookmarksView.setText(R.string.bookmarks_list_is_empty_message);
                } else {
                    mBinding.emptyBookmarksView.setText(R.string.bookmarks_not_found_message);
                }
            } else {
                mBinding.bookmarksList.setVisibility(View.VISIBLE);
                mAdapter.onBookmarkUpdate(bookmarks);
            }
        });
    }
}
