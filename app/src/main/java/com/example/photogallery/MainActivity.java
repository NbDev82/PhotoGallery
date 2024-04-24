package com.example.photogallery;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.photogallery.databinding.ActivityMainBinding;
import com.example.photogallery.fragment.GalleryFragment;
import com.example.photogallery.fragment.UploadFragment;
import com.example.photogallery.repository.PhotoRepos;
import com.example.photogallery.repository.PhotoReposImpl;
import com.example.photogallery.util.Utils;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private GalleryFragment galleryFragment;
    private UploadFragment uploadFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.setStatusBarGradiant(this);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupBottomNavigationView();
    }

    private void setupBottomNavigationView() {
        PhotoRepos photoRepos = new PhotoReposImpl();

        galleryFragment = new GalleryFragment(photoRepos);
        uploadFragment = new UploadFragment(galleryFragment);

        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(galleryFragment);
        fragments.add(uploadFragment);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this, fragments);
        binding.viewPagerHome.setAdapter(viewPagerAdapter);

        List<Integer> tabIds = Arrays.asList(R.id.tab_gallery, R.id.tab_upload);

        binding.viewPagerHome.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                if(position >= tabIds.size()){
                    position = 2;
                }

                binding.bnvHome.setSelectedItemId(tabIds.get(position));
                super.onPageSelected(position);
            }
        });

        binding.bnvHome.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                binding.viewPagerHome.setCurrentItem(tabIds.indexOf(item.getItemId()));
                return true;
            }
        });


        int firstFragmentPosition = 0;
        binding.viewPagerHome.setCurrentItem(firstFragmentPosition, false);
    }
}