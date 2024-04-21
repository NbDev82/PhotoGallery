package com.example.photogallery.model;

import android.net.Uri;

public class Photo {
    private Uri uri;

    public Photo(Uri uri) {
        this.uri = uri;
    }

    public String getPhoto_name() {
        String url = uri.getPath();
        String[] pathSegments = url.split("/");
        return pathSegments[pathSegments.length - 1];
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }
}
