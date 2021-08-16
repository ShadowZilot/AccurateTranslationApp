package com.human_developing_soft.accurate_translation;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.human_developing_soft.accurate_translation.bookmarks.ui.BookmarkFragment;
import com.human_developing_soft.accurate_translation.translation.ui.TranslationFragment;

public class FragmentAdapter extends FragmentStateAdapter {
    public FragmentAdapter(@NonNull FragmentManager fragmentManager,
                           @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return new TranslationFragment();
        } else {
            return new BookmarkFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
