package com.example.photogallery.model;

import android.net.Uri;

public class Photo {
    private Uri uri;
    private long sizeInBytes;

    public Photo() {
    }

    public Photo(Uri uri, long sizeInBytes) {
        this.uri = uri;
        this.sizeInBytes = sizeInBytes;
    }

    public String getPhotoName() {
        try {
            String url = uri.getPath();
            String[] pathSegments = url.split("/");
            return pathSegments[pathSegments.length - 1];
        } catch (Exception e) {
            return "";
        }
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public long getSizeInBytes() {
        return sizeInBytes;
    }

    public void setSizeInBytes(long sizeInBytes) {
        this.sizeInBytes = sizeInBytes;
    }
}
