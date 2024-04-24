package com.example.photogallery.repository;

import android.net.Uri;

import com.example.photogallery.model.UploadImage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.UploadTask;

import java.util.List;

public interface PhotoRepos {
    void fetchAllImageUris(final OnSuccessListener<List<Uri>> onSuccessListener,
                           final OnFailureListener onFailureListener);

    void deleteImage(Uri imageUri,
                     final OnSuccessListener<Void> onSuccessListener,
                     final OnFailureListener onFailureListener);

    UploadTask uploadFile(UploadImage uploadImage);
}
