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
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.photogallery.adapter.UploadImageAdapter;
import com.example.photogallery.databinding.FragmentUploadBinding;
import com.example.photogallery.listener.AddPhotoListener;
import com.example.photogallery.model.Photo;
import com.example.photogallery.model.UploadImage;
import com.example.photogallery.repository.PhotoRepos;
import com.example.photogallery.repository.PhotoReposImpl;
import com.example.photogallery.util.AppExecutors;
import com.example.photogallery.util.Utils;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

public class UploadFragment extends Fragment {

    private static final String TAG = UploadImage.class.getSimpleName();

    private FragmentUploadBinding binding;
    private List<UploadImage> selectedImages;
    private UploadImageAdapter uploadImageAdapter;
    private PhotoRepos photoRepos;
    private List<UploadTask> uploadTasks;
    private AddPhotoListener addPhotoListener;

    public UploadFragment(AddPhotoListener addPhotoListener) {
        this.addPhotoListener = addPhotoListener;
    }

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
        photoRepos = new PhotoReposImpl();
        uploadTasks = new ArrayList<>();

        binding.llEmpty.setVisibility(View.VISIBLE);

        setupListeners();
    }

    private void setupListeners() {
        binding.btnSelectImg.setOnClickListener(v -> {
            toggleSelectImageBtnState(true);

            Intent intent = new Intent();
            intent.setType("image/*");
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            intent.setAction(Intent.ACTION_GET_CONTENT);
            someActivityResultLauncher.launch(Intent.createChooser(intent, "Select Images"));
        });

        binding.btnUploadImg.setOnClickListener(v -> {
            handleUploadImg();
        });
    }

    private final ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() != RESULT_OK) {
                    toggleSelectImageBtnState(false);
                    return;
                }

                clearOldSelectedImages();

                Intent data = result.getData();
                if (data == null) {
                    Toast.makeText(requireActivity(), "You haven't picked Image", Toast.LENGTH_LONG)
                            .show();
                    toggleSelectImageBtnState(false);
                    return;
                }

                if (data.getClipData() == null) {
                    Uri uri = data.getData();
                    addUriToSelectedImages(uri);
                    binding.llEmpty.setVisibility(View.GONE);
                    toggleSelectImageBtnState(false);
                    Log.i(TAG, "Selected images: " + selectedImages);
                    return;
                }

                ClipData clipData = data.getClipData();
                int count = clipData.getItemCount();
                for (int i = 0; i < count; i++) {
                    Uri uri = clipData.getItemAt(i).getUri();
                    addUriToSelectedImages(uri);
                }
                binding.llEmpty.setVisibility(View.GONE);
                toggleSelectImageBtnState(false);
                Log.i(TAG, "Selected images: " + selectedImages);
            });

    private void addUriToSelectedImages(Uri uri) {
        Pair<String, String> nameAndType = Utils.getFileName(requireContext(), uri);
        long sizeInBytes = Utils.getFileSize(requireContext(), uri);
        UploadImage uploadImage = new UploadImage(nameAndType.first, nameAndType.second, uri, 0,
                        UploadImage.EStatus.PENDING, sizeInBytes, 0);
        addImageToSelectedImages(uploadImage);
    }

    private void addImageToSelectedImages(UploadImage uploadImage) {
        selectedImages.add(uploadImage);
        uploadImageAdapter.setItems(selectedImages);
    }

    private void clearOldSelectedImages() {
        uploadTasks.forEach(task -> {
            if (task.isInProgress()) {
                task.cancel();
            }
        });

        selectedImages = new ArrayList<>();
        uploadTasks = new ArrayList<>();

        toggleUploadUIState(false);
    }

    private void toggleUploadUIState(boolean inProgress) {
        if (inProgress) {
            binding.btnUploadImg.setVisibility(View.INVISIBLE);
            binding.pbUploadImgBtn.setVisibility(View.VISIBLE);
        } else {
            binding.btnUploadImg.setVisibility(View.VISIBLE);
            binding.pbUploadImgBtn.setVisibility(View.GONE);
        }
    }

    private void toggleSelectImageBtnState(boolean inProgress) {
        if (inProgress) {
            binding.btnSelectImg.setVisibility(View.INVISIBLE);
            binding.pbSelectBtn.setVisibility(View.VISIBLE);
        } else {
            binding.btnSelectImg.setVisibility(View.VISIBLE);
            binding.pbSelectBtn.setVisibility(View.GONE);
        }
    }

    private void handleUploadImg() {
        if (selectedImages.isEmpty()) {
            Toast.makeText(requireActivity(), "Images is not empty", Toast.LENGTH_SHORT)
                    .show();
            return;
        }

        toggleUploadUIState(true);

        AppExecutors.getIns().networkIO().execute(() -> {
            uploadSelectedImages();
        });
    }

    private void uploadSelectedImages() {
        for (int i = 0; i < selectedImages.size(); i++) {
            UploadImage selectedImage = selectedImages.get(i);
            selectedImage.setStatus(UploadImage.EStatus.UPLOADING);
            final int tempI = i;
            UploadTask uploadTask = photoRepos.uploadFile(selectedImage);
            uploadTask
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            long currentUploadSize = taskSnapshot.getBytesTransferred();
                            int progress = (int) ((100.0 * currentUploadSize) / taskSnapshot.getTotalByteCount());
                            Log.i(TAG, "Upload is " + progress + "% done");

                            selectedImage.setCurUploadSizeInBytes(currentUploadSize);
                            selectedImage.setProgress(progress);
                            requireActivity().runOnUiThread(() -> {
                                uploadImageAdapter.notifyItemChanged(tempI);
                            });
                        }
                    })
                    .addOnPausedListener(new OnPausedListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onPaused(UploadTask.TaskSnapshot taskSnapshot) {
                            Log.i(TAG, "Upload is paused");

                            selectedImage.setStatus(UploadImage.EStatus.PAUSED);
                            requireActivity().runOnUiThread(() -> {
                                uploadImageAdapter.notifyItemChanged(tempI);
                            });
                        }
                    })
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            selectedImage.setStatus(UploadImage.EStatus.SUCCESS);
                            requireActivity().runOnUiThread(() -> {
                                uploadImageAdapter.notifyItemChanged(tempI);
                                if (addPhotoListener != null) {
                                    Photo photo = new Photo(selectedImage.getUri());
                                    addPhotoListener.add(photo);
                                }
                            });
                        }
                    })
                    .addOnFailureListener(e -> {
                        selectedImage.setStatus(UploadImage.EStatus.FAILURE);
                        requireActivity().runOnUiThread(() -> {
                            uploadImageAdapter.notifyItemChanged(tempI);
                        });
                    });

            uploadTasks.add(uploadTask);
        }

        Task<Void> allTasks = Tasks.whenAll(uploadTasks);
        allTasks
                .addOnSuccessListener(unused -> {
                    requireActivity().runOnUiThread(() -> {
                        toggleUploadUIState(false);
                    });

                    Toast.makeText(requireActivity(), "Load successfully", Toast.LENGTH_SHORT)
                            .show();
                })
                .addOnFailureListener(e -> {
                    requireActivity().runOnUiThread(() -> {
                        toggleUploadUIState(false);
                    });
                    Log.e(TAG, String.valueOf(e));
                });
    }
}