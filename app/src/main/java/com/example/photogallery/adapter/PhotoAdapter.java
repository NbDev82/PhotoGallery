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
import com.example.photogallery.model.Image;

import java.util.ArrayList;

public class PhotoAdapter extends ArrayAdapter<Image> {

    public PhotoAdapter(@NonNull Context context, ArrayList<Image> imgList) {
        super(context, 0, imgList);
    }

    public void addImage(Image image) {
        add(image);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listitemView = convertView;
        if (listitemView == null) {
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.card_item, parent, false);
        }

        Image img = getItem(position);
        TextView courseTV = listitemView.findViewById(R.id.idTVName);
        ImageView courseIV = listitemView.findViewById(R.id.idIVImg);

        courseTV.setText(img.getImg_name());
        courseIV.setImageBitmap(img.getImg());
        return listitemView;
    }
}

