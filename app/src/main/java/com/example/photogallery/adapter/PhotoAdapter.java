package com.example.photogallery.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.transition.Transition;
import com.example.photogallery.R;
import com.example.photogallery.listener.PhotoListener;
import com.example.photogallery.model.Photo;

import java.io.File;
import java.util.ArrayList;

public class PhotoAdapter extends ArrayAdapter<Photo> {
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

        View listitemView = convertView;
        if (listitemView == null) {
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.card_item, parent, false);
        }

        Photo img = getItem(position);
        TextView photoTV = listitemView.findViewById(R.id.idTVName);
        ImageView photoIV = listitemView.findViewById(R.id.idIVImg);

        Glide.with(listitemView)
                .load(img.getUri())
                .error(R.drawable.ic_error)
                .placeholder(R.drawable.ic_loading)
                .into(photoIV);

        photoTV.setText(img.getPhoto_name());

        listitemView.setOnClickListener(v -> {
            listener.onImageClick(img);
        });

        return listitemView;
    }
}

