package com.human_developing_soft.accurate_translation;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

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
    }
}