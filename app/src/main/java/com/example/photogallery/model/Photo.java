package com.example.photogallery.model;

import android.net.Uri;

public class Photo {
    private Uri uri;
    private EStatus status;
    private long sizeInBytes;
    private long curDownloadSizeInBytes;

    public Photo() {
    }

    public Photo(Uri uri, EStatus status, long sizeInBytes, long curDownloadSizeInBytes) {
        this.uri = uri;
        this.status = status;
        this.sizeInBytes = sizeInBytes;
        this.curDownloadSizeInBytes = curDownloadSizeInBytes;
    }

    public String getPhotoName() {
        String url = uri.getPath();
        String[] pathSegments = url.split("/");
        return pathSegments[pathSegments.length - 1];
    }

    public int getDownloadProgress() {
        return (int) ((100.0 * curDownloadSizeInBytes) / sizeInBytes);
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public EStatus getStatus() {
        return status;
    }

    public void setStatus(EStatus status) {
        this.status = status;
    }

    public long getSizeInBytes() {
        return sizeInBytes;
    }

    public void setSizeInBytes(long sizeInBytes) {
        this.sizeInBytes = sizeInBytes;
    }

    public long getCurDownloadSizeInBytes() {
        return curDownloadSizeInBytes;
    }

    public void setCurDownloadSizeInBytes(long curDownloadSizeInBytes) {
        this.curDownloadSizeInBytes = curDownloadSizeInBytes;
    }

    public enum EStatus {
        DOWNLOADING,
        SUCCESS,
        FAILURE
    }
}
