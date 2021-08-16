package com.human_developing_soft.accurate_translation;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.tabs.TabLayoutMediator;
import com.human_developing_soft.accurate_translation.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding mBinding;
    private FragmentAdapter mNavigator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        mNavigator = new FragmentAdapter(getSupportFragmentManager(),
                getLifecycle());
        mBinding.mainViewPager.setAdapter(mNavigator);
        new TabLayoutMediator(mBinding.mainTabs,
                mBinding.mainViewPager,
                (tab, position) -> {
                    if (position == 0) {
                        tab.setText(R.string.translation_tab_name);
                    } else if (position == 1) {
                        tab.setText(R.string.bookmark_tab_name);
                    }
                }).attach();
    }
}