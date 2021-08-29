package com.human_developing_soft.accurate_translation.camera.ui;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import com.human_developing_soft.accurate_translation.R;
import com.human_developing_soft.accurate_translation.databinding.ImageSelectorDialogBinding;

public class SourceSelectDialog extends DialogFragment {
    private ImageSelectorDialogBinding mBinding;
    private Uri mImageUri;

    private String[] mCameraPermission;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        mBinding = ImageSelectorDialogBinding.inflate(getLayoutInflater());
        return new AlertDialog.Builder(requireContext())
                .setView(mBinding.getRoot())
                .create();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mCameraPermission = new String[] {
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        mBinding.cameraSource.setOnClickListener((View v) -> {
            if (checkCameraPermission()) {
                pickCamera();
            } else {
                requestCameraPermission();
            }
        });
        mBinding.gallerySource.setOnClickListener((View v) -> pickGallery());
        return mBinding.getRoot();
    }

    @Override
    public void onActivityResult(int requestCode,
                                 int resultCode,
                                 @Nullable Intent data) {
        if (resultCode == Activity.RESULT_OK && data != null) {
            Bundle bundle = new Bundle();
            if (requestCode == 1) {
                bundle.putParcelable(
                        "image",
                        mImageUri);
            } else if (requestCode == 2) {
                bundle.putParcelable(
                        "image",
                        data.getData()
                );
            }
            getParentFragmentManager().setFragmentResult(
                    "image",
                    bundle
            );
        }
    }

    private void pickCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE,
                R.string.camera_sourse_message);
        mImageUri = requireActivity().getContentResolver().insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                values);
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
        startActivityForResult(Intent.createChooser(cameraIntent,
                getString(R.string.camera_sourse_message)), 1);
    }

    private void pickGallery() {
        Log.d("SourceSelectDialog", "Gallery picked");
        Intent galleryIntent = new Intent(Intent.ACTION_PICK);
        galleryIntent.setType("image/*");
        startActivityForResult(Intent.createChooser(galleryIntent,
        getString(R.string.camera_sourse_message)), 2);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults.length > 0) {
                boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean storageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                if (cameraAccepted == storageAccepted) {
                    pickCamera();
                }
            }
        }
    }

    private boolean checkCameraPermission() {
        boolean cameraPermission = ContextCompat.checkSelfPermission(requireContext(),
                Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        boolean storagePermission = ContextCompat.checkSelfPermission(requireContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return cameraPermission && storagePermission;
    }

    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(requireActivity(),
                mCameraPermission, 0);
    }
}
