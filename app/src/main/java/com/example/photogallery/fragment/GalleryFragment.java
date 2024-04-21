package com.example.photogallery.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.photogallery.PhotoActivity;
import com.example.photogallery.adapter.PhotoAdapter;
import com.example.photogallery.databinding.FragmentGalleryBinding;
import com.example.photogallery.listener.PhotoListener;
import com.example.photogallery.model.Photo;
import com.example.photogallery.repository.PhotoRepos;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class GalleryFragment extends Fragment implements PhotoListener {
    private PhotoAdapter mPhotoAdapter;
    private ArrayList<Photo> mPhotos;

    private final PhotoRepos photoRepos;

    private FragmentGalleryBinding binding;

    public GalleryFragment(PhotoRepos photoRepos) {
        this.photoRepos = photoRepos;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentGalleryBinding.inflate(inflater, container, false);

        mPhotos = new ArrayList<>();
        mPhotoAdapter = new PhotoAdapter(getContext(), mPhotos, this);
        binding.gridView.setAdapter(mPhotoAdapter);

        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();

        loadImages();
    }

    private void loadImages() {
        if(mPhotoAdapter.isEmpty()) {
            photoRepos.fetchAllImageUris(
                    imageUris -> {
                        photoRepos.convertUriListToBitmaps(imageUris,
                                downloadedImages -> {
                                    for (Photo photo : downloadedImages) {
                                        mPhotoAdapter.addImage(new Photo(photo.getUri()));
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

    @Override
    public void onImageClick(Photo photo) {
        Intent intent = new Intent(getContext(), PhotoActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        intent.setData(photo.getUri());
        startActivity(intent);
    }
}