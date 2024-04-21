package com.example.photogallery.model;

import android.net.Uri;

public class UploadImage {
    private String fileName;
    private Uri uri;
    private int progress;
    private boolean isUploading;
    private long size;

    public UploadImage() {
    }

    public UploadImage(String fileName, Uri uri, int progress, boolean isUploading, long size) {
        this.fileName = fileName;
        this.uri = uri;
        this.progress = progress;
        this.isUploading = isUploading;
        this.size = size;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public boolean isUploading() {
        return isUploading;
    }

    public void setUploading(boolean uploading) {
        isUploading = uploading;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }
}
