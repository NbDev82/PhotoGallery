package com.example.photogallery.listener;

public interface DownloadActionListener {
    void onStartDownload(String fileName, String url, String format);
    void onPauseDownload();
    void onResumeDownload();
}
