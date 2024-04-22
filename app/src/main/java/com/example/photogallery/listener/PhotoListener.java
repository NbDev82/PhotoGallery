package com.example.photogallery.listener;

import com.example.photogallery.model.Photo;

public interface PhotoListener {
    void onImageClick(Photo photo);
    void onDeleteClick(Photo photo);
}
