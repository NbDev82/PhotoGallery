package com.example.photogallery.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.photogallery.R;
import com.example.photogallery.databinding.CardItemBinding;
import com.example.photogallery.listener.PhotoListener;
import com.example.photogallery.model.Photo;
import com.example.photogallery.util.Utils;

import java.util.ArrayList;

public class PhotoAdapter extends ArrayAdapter<Photo> {
    private CardItemBinding binding;
    private PhotoListener listener;

    public PhotoAdapter(@NonNull Context context, ArrayList<Photo> imgList, PhotoListener listener) {
        super(context, 0, imgList);
        this.listener = listener;
    }

    public void addImage(Photo photo) {
        add(photo);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        binding = CardItemBinding.inflate(layoutInflater, parent, false);

        Photo photo = getItem(position);
        long fileSize = photo.getSizeInBytes();
        String fileSizeStr = Utils.formatFileSize(fileSize);

        binding.idTVName.setText(photo.getPhotoName());
        binding.idTVSizeFile.setText(fileSizeStr);
//        binding.idIVImg.setVisibility(photo.getStatus() == Photo.EStatus.SUCCESS ? View.VISIBLE : View.GONE);
        binding.cvLayout.setOnClickListener(v -> {
            listener.onImageClick(photo);
        });
        binding.deleteBtn.setOnClickListener(v -> {
            listener.onDeleteClick(photo);
        });

        Glide.with(binding.getRoot())
                .load(photo.getUri())
                .error(R.drawable.ic_error)
                .placeholder(R.color.color_primary_gray)
                .thumbnail(0.02f)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(binding.idIVImg);

        return binding.getRoot();
    }
}

