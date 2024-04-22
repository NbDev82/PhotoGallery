package com.example.photogallery.repository;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.photogallery.model.UploadImage;
import com.example.photogallery.model.Photo;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class PhotoReposImpl implements PhotoRepos {

    private final StorageReference storageReference;

    public PhotoReposImpl() {
        storageReference = FirebaseStorage.getInstance().getReference();
    }

    @Override
    public void fetchAllImageUris(final OnSuccessListener<List<Uri>> onSuccessListener,
                                  final OnFailureListener onFailureListener) {
        storageReference.listAll()
                .addOnSuccessListener(listResult -> {
                    List<Uri> imageUris = new ArrayList<>();
                    for (StorageReference item : listResult.getItems()) {
                        item.getDownloadUrl().addOnSuccessListener(uri -> {
                            imageUris.add(uri);
                            if (imageUris.size() == listResult.getItems().size()) {
                                onSuccessListener.onSuccess(imageUris);
                            }
                        });
                    }
                })
                .addOnFailureListener(onFailureListener);
    }

    @Override
    public void convertUriListToBitmaps(List<Uri> imageUris,
                                        final OnSuccessListener<List<Photo>> onSuccessListener,
                                        final OnFailureListener onFailureListener) {
        List<Photo> photos = new ArrayList<>();

        for (Uri uri : imageUris) {
            photos.add(new Photo(uri));
            if (photos.size() == imageUris.size()) {
                onSuccessListener.onSuccess(photos);
            }
        }
    }

    @Override
    public UploadTask uploadFile(UploadImage uploadImage) {
        String fileName = uploadImage.getFileName();
        StorageReference imageRef = storageReference.child(fileName);
        return imageRef.putFile(uploadImage.getUri());
    }
}
