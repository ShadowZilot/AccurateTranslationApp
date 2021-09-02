package com.human_developing_soft.accurate_translation.bookmarks.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
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

import java.util.Locale;

public class BookmarkEditingDialog extends DialogFragment
        implements OnTranslationFieldChanged, TranslatingObserver, TextToSpeech.OnInitListener {
    private BindingBookmark mEditingBookmark;
    private BookmarkEditingDialogBinding mBinding;
    private BookmarkEditingVM mViewModel;
    private TranslationFields mFieldsManager;
    private TextToSpeech mTextToSpeech;
    private Boolean mIsEngineWorking = false;

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
                mBinding.editingFirstLanguageField,
                mBinding.editingSecondLanguageField,
                mBinding.editingFirstLanguageSelector,
                mBinding.editingSecondLanguageSelector,
                mBinding.editingBookmarkTagField
        );
        mTextToSpeech = new TextToSpeech(requireContext(), this);
        mFieldsManager = new TranslationFields(mBinding.editingFirstLanguageField,
                mBinding.editingSecondLanguageField,
                this);
        mViewModel = new ViewModelProvider(this,
                new BookmarkEditingVMFactory(this,
                        requireContext(),
                        mEditingBookmark.languagesName()[0],
                        mEditingBookmark.languagesName()[1]
                )
        ).get(BookmarkEditingVM.class);
        mBinding.editingFirstSoundButton.setOnClickListener((View v) -> {
            if (mIsEngineWorking) {
                Locale locale = mViewModel.localeByLanguage(true);
                mTextToSpeech.setLanguage(locale);
                mTextToSpeech.speak(
                        mBinding.editingFirstLanguageField.getText().toString(),
                        TextToSpeech.QUEUE_FLUSH,
                        null,
                        "first"
                );
            }
        });
        mBinding.editingSecondSoundButton.setOnClickListener((View v) -> {
            if (mIsEngineWorking) {
                Locale locale = mViewModel.localeByLanguage(false);
                mTextToSpeech.setLanguage(locale);
                mTextToSpeech.speak(
                        mBinding.editingSecondLanguageField.getText().toString(),
                        TextToSpeech.QUEUE_FLUSH,
                        null,
                        "second");
            }
        });
        mBinding.applyEditingBtn.setOnClickListener((View v) -> {
            String firstTranslation = mBinding.editingFirstLanguageField
                    .getText().toString();
            String secondTranslation = mBinding.editingSecondLanguageField
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

    @Override
    public void onInit(int status) {
        mIsEngineWorking = status == TextToSpeech.SUCCESS;
        mTextToSpeech.setOnUtteranceProgressListener(new UtteranceProgressListener() {
            @Override
            public void onStart(String utteranceId) {
                if (utteranceId.equals("first")) {
                    mBinding.editingFirstSoundButton.setBackgroundResource(
                            R.drawable.ic_sound);
                    mBinding.editingSecondSoundButton.setBackgroundResource(
                            R.drawable.ic_non_active_sound);
                } else if (utteranceId.equals("second")) {
                    mBinding.editingSecondSoundButton.setBackgroundResource(
                            R.drawable.ic_sound);
                    mBinding.editingFirstSoundButton.setBackgroundResource(
                            R.drawable.ic_non_active_sound);
                }
            }

            @Override
            public void onDone(String utteranceId) {
                if (utteranceId.equals("first")) {
                    mBinding.editingFirstSoundButton.setBackgroundResource(
                            R.drawable.ic_non_active_sound);
                } else if (utteranceId.equals("second")) {
                    mBinding.editingSecondSoundButton.setBackgroundResource(
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
}
