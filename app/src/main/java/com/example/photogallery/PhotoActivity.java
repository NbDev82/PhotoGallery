package com.example.photogallery;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.photogallery.databinding.ActivityImageBinding;

public class PhotoActivity extends AppCompatActivity {
    private ActivityImageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityImageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Uri uri = getIntent().getData();

        if (uri != null) {
            Glide.with(binding.getRoot()).load(uri).into(binding.imageView);
        } else {
            Toast.makeText(this, "Image is unavailable!", Toast.LENGTH_SHORT).show();
        }
    }
}