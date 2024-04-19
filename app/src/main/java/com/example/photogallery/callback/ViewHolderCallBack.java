package com.example.photogallery.callback;


import com.example.photogallery.databinding.ItemProcessingBinding;
import com.example.photogallery.model.DownloadItem;

public interface ViewHolderCallBack {
    void onBindingReady(ItemProcessingBinding binding, DownloadItem item);
}

