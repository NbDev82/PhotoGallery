package com.example.photogallery;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
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
            Glide.with(binding.getRoot())
                    .load(uri)
                    .error(R.drawable.ic_error)
                    .placeholder(R.drawable.ic_loading)
                    .thumbnail(0.02f)
                    .priority(Priority.IMMEDIATE)
                    .into(binding.imageView);
        } else {
            Toast.makeText(this, "Image is unavailable!", Toast.LENGTH_SHORT).show();
        }
    }
}