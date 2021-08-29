package com.human_developing_soft.accurate_translation.camera.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.human_developing_soft.accurate_translation.camera.domain.CameraVMFactory;
import com.human_developing_soft.accurate_translation.camera.domain.CameraViewModel;
import com.human_developing_soft.accurate_translation.databinding.CameraActivityBinding;
import com.theartofdev.edmodo.cropper.CropImageView;

public class CameraActivity extends AppCompatActivity
        implements FragmentResultListener {
    private CameraActivityBinding mBinding;
    private CameraViewModel mViewModel;
    private String mScannedText = "";
    private Boolean mIsFistRequirement;
    private TextRecognizer mRecognizer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = CameraActivityBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        mRecognizer = new TextRecognizer
                .Builder(getApplicationContext())
                .build();
        mViewModel = new ViewModelProvider(this,
                new CameraVMFactory(this,
                        getIntent().getStringExtra("countryCode")))
                .get(CameraViewModel.class);
        mIsFistRequirement = getIntent().getBooleanExtra("isFirst",
                true);
        mBinding.cancelButton.setOnClickListener((View v) -> finish());
        mBinding.languageCircle.setImageDrawable(
                mViewModel.languageImage()
        );
        mBinding.languageName.setText(
                mViewModel.languageName()
        );
        mBinding.cameraButton.setOnClickListener((View v) -> {
            showSelectionDialog();
        });
        mBinding.recognizingImage.setOnSetCropOverlayReleasedListener(this::recognizeText);
        mBinding.submitButton.setOnClickListener((View v) -> {
            Intent data = new Intent();
            data.putExtra("scannedText", mScannedText);
            data.putExtra("isFirst", mIsFistRequirement);
            setResult(3, data);
            finish();
        });
        showSelectionDialog();
    }

    private void recognizeText(Rect rect) {
        Bitmap bitmap = mBinding
                .recognizingImage
                .getCroppedImage(rect.width(),
                        rect.height()
                );
        StringBuilder builder = new StringBuilder();
        if (mRecognizer.isOperational()) {
            Frame frame = new Frame
                    .Builder()
                    .setBitmap(bitmap)
                    .build();
            SparseArray<TextBlock> items = mRecognizer.detect(frame);
            for (int i = 0; i < items.size(); i++) {
                TextBlock myItem = items.valueAt(i);
                builder.append(myItem.getValue());
                builder.append("\n");
            }
        }
        mScannedText = builder.toString();
        mBinding.scannedTextField.setText(mScannedText);
    }

    private void showSelectionDialog() {
        SourceSelectDialog dialog = new SourceSelectDialog();
        getSupportFragmentManager().setFragmentResultListener(
                "image", this, this
        );
        dialog.show(
                getSupportFragmentManager(),
                "image"
        );
    }

    @Override
    public void onFragmentResult(@NonNull String requestKey,
                                 @NonNull Bundle result) {
        if (requestKey.equals("image")) {
            mBinding.recognizingImage.setImageUriAsync(
                    result.getParcelable("image")
            );
            mBinding.recognizingImage.setGuidelines(CropImageView.Guidelines.ON);
        }
    }
}
