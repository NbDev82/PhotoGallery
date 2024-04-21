package com.example.photogallery.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.photogallery.R;
import com.example.photogallery.listener.PhotoListener;
import com.example.photogallery.model.Photo;

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
        TextView courseTV = listitemView.findViewById(R.id.idTVName);
        ImageView courseIV = listitemView.findViewById(R.id.idIVImg);

        courseTV.setText(img.getImg_name());
        courseIV.setImageBitmap(img.getImg());

        listitemView.setOnClickListener(v -> {
            listener.onImageClick(img);
        });

        return listitemView;
    }
}

