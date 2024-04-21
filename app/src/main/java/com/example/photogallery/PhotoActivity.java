package com.example.photogallery;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Toast;

import com.example.photogallery.databinding.ActivityImageBinding;

public class PhotoActivity extends AppCompatActivity {
    private ActivityImageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityImageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        byte[] byteArray = getIntent().getByteArrayExtra("bitmapBytes");

        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

        if (bitmap != null) {
            binding.imageView.setImageBitmap(bitmap);
        } else {
            Toast.makeText(this, "Image is unavailable!", Toast.LENGTH_SHORT).show();
        }
    }
}