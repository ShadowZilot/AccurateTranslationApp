package com.human_developing_soft.accurate_translation;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.human_developing_soft.accurate_translation.bookmarks.ui.BookmarkFragment;
import com.human_developing_soft.accurate_translation.translation.ui.TranslationFragment;

public interface FragmentAdapter {

    void showFragment(int index);

    class Base implements FragmentAdapter {
        private final FragmentManager mManager;

        public Base(FragmentManager fragmentManager) {
            mManager = fragmentManager;
        }

        @Override
        public void showFragment(int index) {
            Fragment placingFragment = null;
            switch (index) {
                case R.id.translationScreen:
                    placingFragment = new TranslationFragment();
                    break;
                case R.id.bookmarksScreen:
                    placingFragment = new BookmarkFragment();
                    break;
            }
            if (placingFragment != null) {
                FragmentTransaction transaction = mManager.beginTransaction();
                transaction.replace(
                        R.id.navHost,
                        placingFragment
                );
                transaction.commit();
            }
        }
    }
}

