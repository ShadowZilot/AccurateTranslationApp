package com.human_developing_soft.accurate_translation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.human_developing_soft.accurate_translation.databinding.ActivityMainBinding;
import com.human_developing_soft.accurate_translation.translation.ui.TranslationFragment;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding mBinding;
    private FragmentManager mNavigator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        mNavigator = getSupportFragmentManager();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FragmentTransaction transaction = mNavigator.beginTransaction();
        transaction.replace(
                R.id.mainNavHost,
                TranslationFragment.class,
                null
        );
        transaction.commit();
    }
}