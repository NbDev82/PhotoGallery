package com.example.photogallery.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.fragment.app.Fragment;


import com.example.photogallery.adapter.PhotoAdapter;
import com.example.photogallery.databinding.FragmentResultBinding;
import com.example.photogallery.model.Image;
import com.example.photogallery.service.ImageService;

import java.util.ArrayList;

public class ResultFragment extends Fragment {
    FragmentResultBinding binding;
    private GridView mGridView;
    private PhotoAdapter mPhotoAdapter;
    private ArrayList<Image> mPhotos;

    private ImageService imageService;


    public ResultFragment(ImageService imageService) {
        this.imageService = imageService;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentResultBinding.inflate(inflater, container, false);
        mPhotos = new ArrayList<>();

        mPhotoAdapter = new PhotoAdapter(getContext(), mPhotos);
        binding.gridView.setAdapter(mPhotoAdapter);
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();

        loadImages();
    }

    private void loadImages() {
        String folderPath = "images";
        imageService.fetchAllImageUris(folderPath,
                imageUris -> {
                    imageService.convertUriListToBitmaps(imageUris,
                            downloadedBitmaps -> {
                                for (Bitmap bitmap : downloadedBitmaps) {
                                    mPhotoAdapter.addImage(new Image("asd", bitmap));
                                }
                            },
                            e -> {
                                Log.e("Download Error", "Failed to download images: " + e.getMessage());
                            });
                },
                e -> {
                    Log.e("Fetch Error", "Failed to fetch image URIs: " + e.getMessage());
                });
    }
}