package com.example.photogallery.model;

import android.net.Uri;

public class UploadImage {
    private String fileName;
    private String fileType;
    private Uri uri;
    private int progress;
    private EStatus status;
    private long sizeInBytes;
    private long curUploadSizeInBytes;

    public UploadImage() {
    }

    public UploadImage(String fileName, String fileType, Uri uri, int progress,
                       EStatus status, long sizeInBytes, long curUploadSizeInBytes) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.uri = uri;
        this.progress = progress;
        this.status = status;
        this.sizeInBytes = sizeInBytes;
        this.curUploadSizeInBytes = curUploadSizeInBytes;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
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

    public long getCurUploadSizeInBytes() {
        return curUploadSizeInBytes;
    }

    public void setCurUploadSizeInBytes(long curUploadSizeInBytes) {
        this.curUploadSizeInBytes = curUploadSizeInBytes;
    }

    public enum EStatus {
        PENDING,
        UPLOADING,
        SUCCESS,
        FAILURE,
        PAUSED
    }
}
