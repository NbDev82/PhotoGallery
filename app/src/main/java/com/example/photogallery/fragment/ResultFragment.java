package com.example.photogallery.fragment;

import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;


import com.example.photogallery.adapter.ItemResultAdapter;
import com.example.photogallery.databinding.FragmentResultBinding;
import com.example.photogallery.listener.AddItemListener;
import com.example.photogallery.model.DownloadItem;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ResultFragment extends Fragment implements AddItemListener {
    FragmentResultBinding binding;
    private ItemResultAdapter adapter;
    private List<DownloadItem> downloadItems;


    public ResultFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentResultBinding.inflate(inflater, container, false);
        downloadItems = getAll();
        adapter = new ItemResultAdapter(downloadItems);
        binding.rclItems.setAdapter(adapter);
        return binding.getRoot();
    }

    private List<DownloadItem> getAll() {
        downloadItems = new ArrayList<>();

        String directoryPath = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS).getPath();

        File appDir = new File(directoryPath, "downloadApp");
        if (!appDir.exists()) {
            appDir.mkdirs();
        }

        File[] fileList = appDir.listFiles();

        for (File f : fileList) {
            String fileName = f.getName();
            String url = f.getAbsolutePath();
            String format = f.getClass().toString();
            DownloadItem item = new DownloadItem(fileName, url, 100, DownloadItem.EStatus.COMPLETED, format);
            downloadItems.add(item);
        }

        return downloadItems;
    }

    @Override
    public void add(DownloadItem downloadItem) {
        if (downloadItems == null) {
            downloadItems = new ArrayList<>();
        }

        this.downloadItems.add(downloadItem);
        adapter.setItemResultViews(downloadItems);
    }
}