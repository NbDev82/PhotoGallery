package com.example.photogallery.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;


import com.example.photogallery.adapter.ItemProcessingAdapter;
import com.example.photogallery.databinding.FragmentProcessingBinding;
import com.example.photogallery.listener.AddItemListener;
import com.example.photogallery.model.DownloadItem;

import java.util.ArrayList;
import java.util.List;

public class ProcessingFragment extends Fragment {
    private FragmentProcessingBinding binding;
    private AddItemListener listener;

    private ItemProcessingAdapter adapter;
    private List<DownloadItem> downloadItems;

    public ProcessingFragment(AddItemListener listener) {
        this.listener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProcessingBinding.inflate(inflater, container, false);
        downloadItems = new ArrayList<>();

        adapter = new ItemProcessingAdapter(downloadItems, listener);
        binding.rclItems.setAdapter(adapter);
        return binding.getRoot();
    }

    public void addItemProcessingView(DownloadItem downloadItem) {
        if (downloadItems == null) {
            downloadItems = new ArrayList<>();
        }

        this.downloadItems.add(downloadItem);
        adapter.setDownloadItems(downloadItems);
    }
}