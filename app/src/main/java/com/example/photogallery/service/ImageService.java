package com.example.photogallery.service;

import android.graphics.Bitmap;
import android.net.Uri;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;

public interface ImageService {
    void fetchAllImageUris(String folderPath,
                           final OnSuccessListener<List<Uri>> onSuccessListener,
                           final OnFailureListener onFailureListener);

    void convertUriListToBitmaps(List<Uri> imageUris,
                                 final OnSuccessListener<List<Bitmap>> onSuccessListener,
                                 final OnFailureListener onFailureListener);
}
