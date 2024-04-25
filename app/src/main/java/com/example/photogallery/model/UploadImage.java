package com.example.photogallery.model;

import android.net.Uri;

public class UploadImage {
    private String fileName;
    private String fileType;
    private Uri uri;
    private EStatus status;
    private long sizeInBytes;
    private long curUploadSizeInBytes;

    public UploadImage() {
    }

    public UploadImage(String fileName, String fileType, Uri uri, EStatus status,
                       long sizeInBytes, long curUploadSizeInBytes) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.uri = uri;
        this.status = status;
        this.sizeInBytes = sizeInBytes;
        this.curUploadSizeInBytes = curUploadSizeInBytes;
    }

    public boolean isPending() {
        return status == EStatus.PENDING;
    }

    public boolean isUploading() {
        return status == EStatus.UPLOADING;
    }

    public boolean isSuccess() {
        return status == EStatus.SUCCESS;
    }

    public boolean isFailure() {
        return status == EStatus.FAILURE;
    }

    public boolean isPaused() {
        return status == EStatus.PAUSED;
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

    public int getUploadProgress() {
        return (int) ((100.0 * curUploadSizeInBytes) / sizeInBytes);
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
