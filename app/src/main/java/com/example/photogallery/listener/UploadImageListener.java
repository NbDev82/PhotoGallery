package com.example.photogallery.listener;

public interface UploadImageListener {
    void pause(int position);
    void resume(int position);
    void cancel(int position);
}
