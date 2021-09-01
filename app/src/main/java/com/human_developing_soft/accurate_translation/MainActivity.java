package com.human_developing_soft.accurate_translation;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.human_developing_soft.accurate_translation.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding mBinding;
    private FragmentAdapter mNavigator;
    private int mLastFragment = R.id.translationScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        mNavigator = new FragmentAdapter.Base(
                getSupportFragmentManager());
        if (savedInstanceState != null) {
            mLastFragment = savedInstanceState.getInt("lastIndex");
        }
        mNavigator.showFragment(mLastFragment);
        mBinding.mainBottomNav.setOnNavigationItemSelectedListener(
                item -> {
                    mLastFragment = item.getItemId();
                    mNavigator.showFragment(item.getItemId());
                    return true;
                }
        );
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("lastIndex", mLastFragment);
    }
}