package com.example.photogallery.repository;

import android.net.Uri;

import com.example.photogallery.model.Photo;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;

public interface PhotoRepos {
    void fetchAllImageUris(final OnSuccessListener<List<Uri>> onSuccessListener,
                           final OnFailureListener onFailureListener);

    void convertUriListToBitmaps(List<Uri> imageUris,
                                 final OnSuccessListener<List<Photo>> onSuccessListener,
                                 final OnFailureListener onFailureListener);
}
