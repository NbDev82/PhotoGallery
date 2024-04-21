package com.example.photogallery.fragment;

import static android.app.Activity.RESULT_OK;

import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.photogallery.adapter.UploadImageAdapter;
import com.example.photogallery.databinding.FragmentUploadBinding;
import com.example.photogallery.model.UploadImage;
import com.example.photogallery.util.Utils;

import java.util.ArrayList;
import java.util.List;

public class UploadFragment extends Fragment {

    private static final String TAG = UploadImage.class.getSimpleName();

    private FragmentUploadBinding binding;
    private List<UploadImage> selectedImages;
    private UploadImageAdapter uploadImageAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUploadBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        selectedImages = new ArrayList<>();
        uploadImageAdapter = new UploadImageAdapter(new ArrayList<>());
        binding.rcvImgPreview.setAdapter(uploadImageAdapter);

        setupListeners();
    }

    @Override
    public void onStart() {
        super.onStart();

        selectedImages.add(new UploadImage());
        selectedImages.add(new UploadImage());
        selectedImages.add(new UploadImage());
        selectedImages.add(new UploadImage());
        selectedImages.add(new UploadImage());
    }

    private void setupListeners() {
        binding.btnSelectImg.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            intent.setAction(Intent.ACTION_GET_CONTENT);
            someActivityResultLauncher.launch(Intent.createChooser(intent, "Select Images"));
        });
    }

    private final ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() != RESULT_OK) {
                    return;
                }

                selectedImages = new ArrayList<>();

                Intent data = result.getData();
                if (data == null) {
                    Toast.makeText(requireActivity(), "You haven't picked Image", Toast.LENGTH_LONG)
                            .show();
                    return;
                }

                if (data.getClipData() == null) {
                    Uri uri = data.getData();
                    addUriToSelectedImages(uri);
                    Log.i(TAG, "Selected images: " + selectedImages);
                    return;
                }

                ClipData clipData = data.getClipData();
                int count = clipData.getItemCount();
                for (int i = 0; i < count; i++) {
                    Uri uri = clipData.getItemAt(i).getUri();
                    addUriToSelectedImages(uri);
                }
                Log.i(TAG, "Selected images: " + selectedImages);
            });

    private void addUriToSelectedImages(Uri uri) {
        String fileName = Utils.getFileName(requireContext(), uri);
        long size = Utils.getFileSize(requireContext(), uri);
        UploadImage uploadImage =
                new UploadImage(fileName, uri, 0, false, size);
        addImageToSelectedImages(uploadImage);
    }

    private void addImageToSelectedImages(UploadImage uploadImage) {
        selectedImages.add(uploadImage);
        uploadImageAdapter.setItems(selectedImages);
    }
}