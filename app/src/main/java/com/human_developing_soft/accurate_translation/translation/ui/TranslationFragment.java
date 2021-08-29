package com.human_developing_soft.accurate_translation.translation.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.ViewModelProvider;

import com.human_developing_soft.accurate_translation.R;
import com.human_developing_soft.accurate_translation.bookmarks.ui.BookmarkArguments;
import com.human_developing_soft.accurate_translation.bookmarks.ui.PreSavingBookmarkFragment;
import com.human_developing_soft.accurate_translation.camera.ui.CameraActivity;
import com.human_developing_soft.accurate_translation.databinding.TranslationFragmentBinding;
import com.human_developing_soft.accurate_translation.translation.common.OnTranslationFieldChanged;
import com.human_developing_soft.accurate_translation.translation.common.TranslationFields;
import com.human_developing_soft.accurate_translation.translation.data.HandledLanguage;
import com.human_developing_soft.accurate_translation.translation.domain.CachedSelectedLanguages;
import com.human_developing_soft.accurate_translation.translation.domain.TranslatingVMFactory;
import com.human_developing_soft.accurate_translation.translation.domain.TranslatingViewModel;

import java.util.Locale;

public class TranslationFragment extends Fragment
        implements OnTranslationFieldChanged, TranslatingObserver,
        FragmentResultListener, TextToSpeech.OnInitListener {
    private TranslationFragmentBinding mBinding;
    private TranslatingViewModel mViewModel;
    private TranslationFields mFieldManager;
    private TextToSpeech mTextToSpeech;
    private Boolean mIsEngineWorking = false;

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this,
                new TranslatingVMFactory(this,
                        new CachedSelectedLanguages.Base(requireContext())
                )
        ).get(TranslatingViewModel.class);
        mTextToSpeech = new TextToSpeech(requireContext(), this);
        mBinding.clearButton.setOnClickListener((View v) -> {
            mFieldManager.clearFields();
            mBinding.translationButtonPanel.setVisibility(View.GONE);
            mBinding.buttonsDivider.setVisibility(View.INVISIBLE);
            mBinding.firstMicButton.setVisibility(View.VISIBLE);
            mBinding.secondMicButton.setVisibility(View.VISIBLE);
            mBinding.firstCamButton.setVisibility(View.VISIBLE);
            mBinding.secondCamButton.setVisibility(View.VISIBLE);
            mBinding.firstSoundButton.setVisibility(View.GONE);
            mBinding.secondSoundButton.setVisibility(View.GONE);
        });
        mBinding.firstSoundButton.setOnClickListener((View v) -> {
            if (mIsEngineWorking) {
                Locale locale = mViewModel.localeByLanguage(true);
                mTextToSpeech.setLanguage(locale);
                mTextToSpeech.speak(
                        mBinding.firstLanguageField.getText().toString(),
                        TextToSpeech.QUEUE_FLUSH,
                        null,
                        "first"
                );
            }
        });
        mBinding.secondSoundButton.setOnClickListener((View v) -> {
            if (mIsEngineWorking) {
                Locale locale = mViewModel.localeByLanguage(false);
                mTextToSpeech.setLanguage(locale);
                mTextToSpeech.speak(
                        mBinding.secondLanguageField.getText().toString(),
                        TextToSpeech.QUEUE_FLUSH,
                        null,
                        "second");
            }
        });
        mBinding.firstMicButton.setOnClickListener((View v) -> {
            recordSpeech(
                    mViewModel.localeForMic(true),
                    1
            );
        });
        mBinding.secondMicButton.setOnClickListener((View v) -> {
            recordSpeech(
                    mViewModel.localeForMic(false),
                    2);
        });
        mBinding.firstCamButton.setOnClickListener((View v) -> {
            startCameraActivity(
                    mViewModel.languageName(true),
                    true
            );
        });
        mBinding.secondCamButton.setOnClickListener((View v) -> {
            startCameraActivity(
                    mViewModel.languageName(false),
                    false
            );
        });
        mBinding.saveButton.setOnClickListener((View v) -> {
            if (mFieldManager.isFieldsNotEmpty()) {
                Bundle args = new BookmarkArguments.Base(
                        mBinding.firstLanguageField.getText().toString(),
                        mBinding.secondLanguageField.getText().toString(),
                        mViewModel.languageName(true),
                        mViewModel.languageName(false)
                ).arguments();
                PreSavingBookmarkFragment preSaving = new PreSavingBookmarkFragment();
                preSaving.setArguments(args);
                preSaving.show(getParentFragmentManager(), "tags");
            } else {
                Toast.makeText(requireContext(),
                        R.string.saving_field_empty_message,
                        Toast.LENGTH_SHORT).show();
            }
        });
        mFieldManager = new TranslationFields(mBinding.firstLanguageField,
                mBinding.secondLanguageField,
                this);
        Bundle args = new Bundle();
        mBinding.firstLanguageSelector.setOnClickListener((View v) -> {
            LanguageSelectorDialog languageSelector = new LanguageSelectorDialog();
            args.putString("requestCode", "firstLanguage");
            languageSelector.setArguments(args);
            getParentFragmentManager().setFragmentResultListener(
                    "firstLanguage", this, this
            );
            languageSelector.show(getParentFragmentManager(), "selector");
        });
        mBinding.secondLanguageSelector.setOnClickListener((View v) -> {
            LanguageSelectorDialog languageSelector = new LanguageSelectorDialog();
            args.putString("requestCode", "secondLanguage");
            languageSelector.setArguments(args);
            getParentFragmentManager().setFragmentResultListener(
                    "secondLanguage", this, this
            );
            languageSelector.show(getParentFragmentManager(), "selector");
        });
        if (savedInstanceState != null) {
            mViewModel.updateObserver(this);
            mBinding.firstLanguageField.setTag("blocked");
            mBinding.secondLanguageField.setTag("blocked");
            mBinding.firstLanguageField.setText(
                    savedInstanceState.getString("firstField")
            );
            mBinding.secondLanguageField.setText(
                    savedInstanceState.getString("secondField")
            );
            mBinding.firstLanguageField.setTag("free");
            mBinding.secondLanguageField.setTag("free");
        }
        mViewModel.initUI(mBinding.firstLanguageSelector,
                mBinding.secondLanguageSelector,
                mBinding.firstLanguageField,
                mBinding.secondLanguageField);
    }

    private void startCameraActivity(String languageName, Boolean isFirst) {
        Intent cameraIntent = new Intent(requireContext(), CameraActivity.class);
        cameraIntent.putExtra("countryCode", languageName);
        cameraIntent.putExtra("isFirst", isFirst);
        startActivityForResult(cameraIntent, 3);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = TranslationFragmentBinding.inflate(
                inflater,
                container,
                false);
        return mBinding.getRoot();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString("firstField",
                mBinding.firstLanguageField.getText().toString());
        outState.putString("secondField",
                mBinding.secondLanguageField.getText().toString());
    }

    @Override
    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
        if (requestKey.equals("firstLanguage")) {
            HandledLanguage language = new HandledLanguage.Base(result);
            mBinding.firstLanguageField.setHint(
                    new StringProvider.Base(
                            requireContext()
                    ).string(R.string.field_hint,
                            language.name()
                    )
            );
            mViewModel.updateTranslatingLanguage(language,
                    new HandledLanguage.Dummy(requireContext()),
                    new CachedSelectedLanguages.Base(
                            requireContext()
                    ));
        } else if (requestKey.equals("secondLanguage")) {
            HandledLanguage language = new HandledLanguage.Base(result);
            mBinding.secondLanguageField.setHint(
                    new StringProvider.Base(
                            requireContext()
                    ).string(R.string.field_hint,
                            language.name()
                    )
            );
            mViewModel.updateTranslatingLanguage(new HandledLanguage.Dummy(requireContext()),
                    language,
                    new CachedSelectedLanguages.Base(
                            requireContext()
                    ));
        }
        mViewModel.initUI(mBinding.firstLanguageSelector,
                mBinding.secondLanguageSelector,
                mBinding.firstLanguageField,
                mBinding.secondLanguageField);
        mBinding.firstLanguageField.setText("");
        mBinding.secondLanguageField.setText("");
    }

    @Override
    public void translateText(String translationField,
                              Boolean isFirstField,
                              StringProvider provider) {
        if (translationField.isEmpty()) {
            mBinding.translationButtonPanel.setVisibility(View.GONE);
            mBinding.buttonsDivider.setVisibility(View.INVISIBLE);
            mBinding.firstMicButton.setVisibility(View.VISIBLE);
            mBinding.secondMicButton.setVisibility(View.VISIBLE);
            mBinding.firstSoundButton.setVisibility(View.GONE);
            mBinding.secondSoundButton.setVisibility(View.GONE);
            mBinding.firstCamButton.setVisibility(View.VISIBLE);
            mBinding.secondCamButton.setVisibility(View.VISIBLE);
        } else {
            mBinding.translationButtonPanel.setVisibility(View.VISIBLE);
            mBinding.buttonsDivider.setVisibility(View.VISIBLE);
            mBinding.firstMicButton.setVisibility(View.GONE);
            mBinding.secondMicButton.setVisibility(View.GONE);
            mBinding.firstSoundButton.setVisibility(View.VISIBLE);
            mBinding.secondSoundButton.setVisibility(View.VISIBLE);
            mBinding.firstCamButton.setVisibility(View.GONE);
            mBinding.secondCamButton.setVisibility(View.GONE);
        }
        mBinding.translationProgress.setVisibility(View.VISIBLE);
        mBinding.translationIcon.setVisibility(View.INVISIBLE);
        mViewModel.translateText(
                translationField,
                isFirstField,
                provider
        );
    }

    @Override
    public void updateField(String translatingValue, Boolean isFirstField) {
        requireActivity().runOnUiThread(() -> {
            mFieldManager.updateField(
                    translatingValue, isFirstField
            );
            mBinding.translationProgress.setVisibility(View.GONE);
            mBinding.translationIcon.setVisibility(View.VISIBLE);
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mFieldManager.clearListeners();
    }

    @Override
    public void onInit(int status) {
        mIsEngineWorking = status == TextToSpeech.SUCCESS;
        mTextToSpeech.setOnUtteranceProgressListener(new UtteranceProgressListener() {
            @Override
            public void onStart(String utteranceId) {
                if (utteranceId.equals("first")) {
                    mBinding.firstSoundButton.setBackgroundResource(
                            R.drawable.ic_sound);
                    mBinding.secondSoundButton.setBackgroundResource(
                            R.drawable.ic_non_active_sound);
                } else if (utteranceId.equals("second")) {
                    mBinding.secondSoundButton.setBackgroundResource(
                            R.drawable.ic_sound);
                    mBinding.firstSoundButton.setBackgroundResource(
                            R.drawable.ic_non_active_sound);
                }
            }

            @Override
            public void onDone(String utteranceId) {
                if (utteranceId.equals("first")) {
                    mBinding.firstSoundButton.setBackgroundResource(
                            R.drawable.ic_non_active_sound);
                } else if (utteranceId.equals("second")) {
                    mBinding.secondSoundButton.setBackgroundResource(
                            R.drawable.ic_non_active_sound);
                }
            }

            @Override
            public void onError(String utteranceId) {
                Toast.makeText(requireContext(),
                        R.string.sound_not_support_message,
                        Toast.LENGTH_LONG)
                        .show();
            }
        });
    }

    private void recordSpeech(String language, Integer fieldCode) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        );
        intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE,
                language
        );
        intent.putExtra(
                RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.mic_message)
        );
        startActivityForResult(intent, fieldCode);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == Activity.RESULT_OK && data != null) {
            if (requestCode == 1) {
                mFieldManager.updateFieldAfterMic(
                        data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS).get(0),
                        true
                );
            } else if (requestCode == 2) {
                mFieldManager.updateFieldAfterMic(
                        data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS).get(0),
                        false
                );
            }
        }
        if (resultCode == 3 && data != null) {
            mFieldManager.updateFieldAfterMic(
                    data.getStringExtra("scannedText"),
                    data.getBooleanExtra("isFirst", true)
            );
        }
    }
}
