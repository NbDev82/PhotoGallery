package com.example.photogallery.repository;

import android.net.Uri;

import com.example.photogallery.model.UploadImage;
import com.example.photogallery.model.Photo;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.UploadTask;

import java.util.List;

public interface PhotoRepos {
    void fetchAllImageUris(final OnSuccessListener<List<Uri>> onSuccessListener,
                           final OnFailureListener onFailureListener);

    void convertUriListToBitmaps(List<Uri> imageUris,
                                 final OnSuccessListener<List<Photo>> onSuccessListener,
                                 final OnFailureListener onFailureListener);

    void deleteImage(Uri imageUri, OnSuccessListener<Void> onSuccessListener, OnFailureListener onFailureListener);
    UploadTask uploadFile(UploadImage uploadImage);
}
