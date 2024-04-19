package com.example.photogallery;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MenuItem;

import com.example.photogallery.databinding.ActivityMainBinding;
import com.example.photogallery.fragment.InputFragment;
import com.example.photogallery.fragment.ProcessingFragment;
import com.example.photogallery.fragment.ResultFragment;
import com.example.photogallery.listener.DownloadActionListener;
import com.example.photogallery.model.DownloadItem;
import com.google.android.material.navigation.NavigationBarView;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements InputFragment.OnDialogDismissedListener, DownloadActionListener {

    private ActivityMainBinding binding;
    private ProcessingFragment processingFragment;
    private ResultFragment resultFragment;
    private InputFragment inputFragment;
    private DownloadTask downloadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        inputFragment = new InputFragment();
        resultFragment = new ResultFragment();
        processingFragment = new ProcessingFragment(resultFragment);

        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(inputFragment);
        fragments.add(processingFragment);
        fragments.add(resultFragment);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this, fragments);
        binding.viewPagerHome.setAdapter(viewPagerAdapter);

        List<Integer> tabIds = Arrays.asList(R.id.input_tab, R.id.processing_tab, R.id.result_tab);

        binding.viewPagerHome.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                if(position >= tabIds.size()){
                    position = 2;
                }

                binding.bnvHome.setSelectedItemId(tabIds.get(position));
                super.onPageSelected(position);
            }
        });

        binding.bnvHome.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                binding.viewPagerHome.setCurrentItem(tabIds.indexOf(item.getItemId()));
                return true;
            }
        });


        int firstFragmentPosition = 2;
        binding.viewPagerHome.setCurrentItem(firstFragmentPosition, false);
    }

    @Override
    public void onDialogDismissed() {
        binding.viewPagerHome.setCurrentItem(1, false);
    }

    @Override
    public void onStartDownload(String fileName, String url, String format) {
        try {
            DownloadItem downloadItem = new DownloadItem(fileName,url, 0, DownloadItem.EStatus.DOWNLOADING, format);
            processingFragment.addItemProcessingView(downloadItem);
        } catch (Exception e) {
            Log.e("MainActivity", "Error starting download: " + e.getMessage());
        }
    }

    @Override
    public void onPauseDownload() {
        if (downloadTask != null) {
            downloadTask.togglePause();
        }
    }

    @Override
    public void onResumeDownload() {
        if (downloadTask != null) {
            downloadTask.togglePause();
        }
    }
}