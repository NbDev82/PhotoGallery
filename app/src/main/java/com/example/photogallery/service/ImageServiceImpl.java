package com.example.photogallery.service;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class ImageServiceImpl implements ImageService {

    private StorageReference storageReference;

    public ImageServiceImpl() {
        storageReference = FirebaseStorage.getInstance().getReference();
    }

    @Override
    public void fetchAllImageUris(String folderPath,
                                  final OnSuccessListener<List<Uri>> onSuccessListener,
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
                                               final OnSuccessListener<List<Bitmap>> onSuccessListener,
                                               final OnFailureListener onFailureListener) {
        List<Bitmap> bitmaps = new ArrayList<>();

        FirebaseStorage storage = FirebaseStorage.getInstance();

        for (Uri uri : imageUris) {
            StorageReference storageRef = storage.getReferenceFromUrl(uri.toString());

            storageRef.getBytes(Long.MAX_VALUE).addOnSuccessListener(bytes -> {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                bitmaps.add(bitmap);

                if (bitmaps.size() == imageUris.size()) {
                    onSuccessListener.onSuccess(bitmaps);
                }
            }).addOnFailureListener(e -> {
                onFailureListener.onFailure(e);
            });
        }
    }
}
