package com.example.photogallery.model;

public class DownloadItem {
    private String fileName;
    private String url;
    private String format;
    private int progress;
    private EStatus status;


    public DownloadItem(String fileName, String url, int progress, EStatus status, String format) {
        this.fileName = fileName;
        this.url = url;
        this.progress = progress;
        this.status = status;
        this.format = format;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public enum EStatus {
        DOWNLOADING,
        PAUSED,
        COMPLETED,
        FAILED
    }
}
