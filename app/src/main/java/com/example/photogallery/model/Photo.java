package com.example.photogallery.model;

import android.graphics.Bitmap;

public class Photo {
    private String img_name;
    private Bitmap img;

    public Photo(String img_name, Bitmap img) {
        this.img_name = img_name;
        this.img = img;
    }

    public String getImg_name() {
        return img_name;
    }

    public void setImg_name(String img_name) {
        this.img_name = img_name;
    }

    public Bitmap getImg() {
        return img;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }
}
