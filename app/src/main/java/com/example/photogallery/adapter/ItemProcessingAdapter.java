package com.example.photogallery.adapter;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.photogallery.DownloadTask;
import com.example.photogallery.callback.ViewHolderCallBack;
import com.example.photogallery.databinding.ItemProcessingBinding;
import com.example.photogallery.listener.AddItemListener;
import com.example.photogallery.model.DownloadItem;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

public class ItemProcessingAdapter extends RecyclerView.Adapter<ItemProcessingAdapter.ItemProcessingViewHolder> implements ViewHolderCallBack {
    private List<DownloadItem> downloadItems;

    public ItemProcessingAdapter(List<DownloadItem> downloadItems) {
        this.downloadItems = downloadItems;
    }

    public void setDownloadItems(List<DownloadItem> downloadItems){
        this.downloadItems = downloadItems;
        notifyDataSetChanged();
    }

    public void removeItem(int position){
        downloadItems.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, downloadItems.size());
    }

    @NonNull
    @Override
    public ItemProcessingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemProcessingViewHolder(
                ItemProcessingBinding.inflate(
                        LayoutInflater.from(parent.getContext()),
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(ItemProcessingViewHolder holder, int position) {
        holder.setData(downloadItems.get(position));
    }

    @Override
    public int getItemCount() {
        return downloadItems.size();
    }

    @Override
    public void onBindingReady(ItemProcessingBinding binding, DownloadItem item) {

        String directoryPath = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS).getPath();

        File appDir = new File(directoryPath, "downloadApp");
        if (!appDir.exists()) {
            appDir.mkdirs();
        }

        File file;
        if(item.getFormat().equals("folder")) {
            file = new File(appDir, item.getFileName());

        } else {
            file = new File(appDir, item.getFileName() + "." + item.getFormat());
        }

        FileOutputStream outputStream;
        try {
            outputStream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        DownloadTask downloadTask = new DownloadTask(binding, () -> {
//            int pos = downloadItems.indexOf(item);
//            removeItem(pos);
//            listener.add(item);
        }, outputStream);
        downloadTask.execute(item.getUrl());
    }

    public class ItemProcessingViewHolder extends RecyclerView.ViewHolder {

        ItemProcessingBinding binding;
        public ItemProcessingViewHolder(ItemProcessingBinding item) {
            super(item.getRoot());
            binding = item;
        }

        void setData(DownloadItem downloadItem){
            binding.txvTitle.setText(downloadItem.getFileName());
            binding.txvDownload.setText(String.valueOf(downloadItem.getProgress()));
            binding.pbDownload.setProgress(downloadItem.getProgress());

            onBindingReady(binding, downloadItem);
        }
    }
}
