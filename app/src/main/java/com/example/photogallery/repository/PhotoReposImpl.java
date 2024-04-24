package com.example.photogallery.repository;

import android.net.Uri;

import com.example.photogallery.model.UploadImage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

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
    public void deleteImage(Uri imageUri,
                            final OnSuccessListener<Void> onSuccessListener,
                            final OnFailureListener onFailureListener) {

        String pathSegment = imageUri.getLastPathSegment();
        StorageReference imageRef = storageReference.child(pathSegment);
        imageRef.delete()
                .addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(onFailureListener);
    }
  
    @Override
    public UploadTask uploadFile(UploadImage uploadImage) {
        String fileName = uploadImage.getFileName();
        StorageReference imageRef = storageReference.child(fileName);
        return imageRef.putFile(uploadImage.getUri());
    }
}
