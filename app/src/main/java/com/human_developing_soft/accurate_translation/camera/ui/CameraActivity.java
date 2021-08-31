package com.human_developing_soft.accurate_translation.camera.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;
import com.human_developing_soft.accurate_translation.R;
import com.human_developing_soft.accurate_translation.camera.domain.CameraVMFactory;
import com.human_developing_soft.accurate_translation.camera.domain.CameraViewModel;
import com.human_developing_soft.accurate_translation.databinding.CameraActivityBinding;

public class CameraActivity extends AppCompatActivity
        implements FragmentResultListener {
    private CameraActivityBinding mBinding;
    private CameraViewModel mViewModel;
    private final String mScannedText = "";
    private Boolean mIsFistRequirement;
    private TextRecognizer mRecognizer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = CameraActivityBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        mRecognizer = TextRecognition.getClient(
                TextRecognizerOptions.DEFAULT_OPTIONS);
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
        mBinding.recognizingImage.setOnTouchListener((v, event) -> {
            Log.d("Touching", event.getAction() + "");
            if (event.getAction() == 1) {
                recognizeText();
                return true;
            }
            return false;
        });
        mBinding.submitButton.setOnClickListener((View v) -> {
            recognizeText();
            Intent data = new Intent();
            data.putExtra(
                    "scannedText",
                    mBinding.scannedTextField.getText().toString());
            data.putExtra("isFirst", mIsFistRequirement);
            setResult(3, data);
            finish();
        });
        showSelectionDialog();
    }

    private void recognizeText() {
        Bitmap bitmap = mBinding
                .recognizingImage.getCroppedBitmap(
                    mBinding.recognizingImage.getCropInfo()
                );
        InputImage inputImage = InputImage.fromBitmap(bitmap, 0);
        Task<Text> result = mRecognizer.process(inputImage).addOnSuccessListener(
                (Text visionText) -> mBinding.scannedTextField.setText(
                        visionText.getText()
                )
        );
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
            if (result.getParcelable("gallery") != null) {
                mBinding.recognizingImage.setImageBitmap(
                        result.getParcelable("gallery")
                );
            } else {
                Bitmap bitmap = result.getParcelable("camera");
                mBinding.recognizingImage.setImageBitmap(bitmap);
            }
        }
    }
}
