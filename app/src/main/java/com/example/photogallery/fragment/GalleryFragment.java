package com.example.photogallery.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.photogallery.ImageActivity;
import com.example.photogallery.adapter.PhotoAdapter;
import com.example.photogallery.databinding.FragmentGalleryBinding;
import com.example.photogallery.listener.AddPhotoListener;
import com.example.photogallery.listener.PhotoListener;
import com.example.photogallery.model.Photo;
import com.example.photogallery.repository.PhotoRepos;
import com.example.photogallery.util.Utils;

import java.util.ArrayList;

import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

public class GalleryFragment extends Fragment implements PhotoListener, AddPhotoListener {
    private static final String TAG = GalleryFragment.class.getSimpleName();

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

        OverScrollDecoratorHelper.setUpOverScroll(binding.gridView);

        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();

        loadImages();
    }

    @Override
    public void onImageClick(Photo photo) {
        Intent intent = new Intent(getContext(), ImageActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        intent.setData(photo.getUri());
        startActivity(intent);
    }

    @Override
    public void add(Photo photo) {
        if (mPhotoAdapter == null) {
            mPhotoAdapter = new PhotoAdapter(requireActivity(), new ArrayList<>(), this);
        }
        mPhotoAdapter.addImage(photo);
    }

    @Override
    public void onDeleteClick(Photo photo) {
        photoRepos.deleteImage(photo.getUri(),
                unused -> {
                    mPhotos.remove(photo);
                    mPhotoAdapter.notifyDataSetChanged();
                },
                e -> {
                    Log.e("Delete Error", "Failed to delete image: " + e.getMessage());
                }
        );
    }

    private void loadImages() {
        if (!mPhotoAdapter.isEmpty()) {
            return;
        }

        photoRepos.fetchAllImageUris(uris -> {
            for (Uri uri : uris) {
                Pair<String, String> nameAndType = Utils.getFileName(requireActivity(), uri);
                long fileSize = Utils.getFileSize(requireActivity(), uri);
                Photo photo = new Photo(uri, Photo.EStatus.DOWNLOADING, fileSize, 0);
                mPhotoAdapter.addImage(photo);
            }
        }, e -> {
            Log.e(TAG, String.valueOf(e));
        });
    }
}