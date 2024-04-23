package com.example.photogallery.adapter;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.photogallery.databinding.ItemUploadImageBinding;
import com.example.photogallery.model.UploadImage;
import com.example.photogallery.util.Utils;

import java.util.List;

public class UploadImageAdapter extends RecyclerView.Adapter<UploadImageAdapter.UploadImageViewHolder> {

    private List<UploadImage> uploadImages;

    public UploadImageAdapter(List<UploadImage> uploadImages) {
        this.uploadImages = uploadImages;
    }

    public void setItems(List<UploadImage> uploadImages) {
        this.uploadImages = uploadImages;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UploadImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemUploadImageBinding binding = ItemUploadImageBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new UploadImageViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull UploadImageViewHolder holder, int position) {
        UploadImage uploadImage = uploadImages.get(position);
        holder.bind(uploadImage);
    }

    @Override
    public int getItemCount() {
        return uploadImages.size();
    }

    public static class UploadImageViewHolder extends RecyclerView.ViewHolder {

        private final ItemUploadImageBinding binding;

        public UploadImageViewHolder(ItemUploadImageBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(UploadImage uploadImage) {
            setupBasicInfo(uploadImage);
            setupProgressBar(uploadImage.isPending(), uploadImage.getProgress());
            setupLabel(uploadImage);
        }

        private void setupBasicInfo(UploadImage uploadImage) {
            String fileTypeStr = "Type: " + uploadImage.getFileType();
            long fileSize = uploadImage.getSizeInBytes();
            String fileSizeStr = uploadImage.isPending()
                    ? "Size: " + Utils.formatFileSize(fileSize)
                    : "Size: " + Utils.formatFileSize(uploadImage.getCurUploadSizeInBytes()) + "/" + Utils.formatFileSize(fileSize);

            binding.txvFileName.setText(uploadImage.getFileName());
            binding.txvFileType.setText(fileTypeStr);
            binding.txvFileSize.setText(fileSizeStr);
            binding.imgPreview.setImageURI(uploadImage.getUri());
        }

        private void setupProgressBar(boolean isPending, int progress) {
            String progressStr = progress + "%";

            binding.llUploadProgress.setVisibility(isPending ? View.GONE : View.VISIBLE);
            binding.progressBar.setProgress(progress, true);
            binding.txvProgress.setText(progressStr);
        }

        private void setupLabel(UploadImage uploadImage) {
            binding.imgSuccess.setVisibility(uploadImage.isSuccess() ? View.VISIBLE : View.GONE);
            binding.imgFailure.setVisibility(uploadImage.isFailure() ? View.VISIBLE : View.GONE);
            binding.imgPause.setVisibility(uploadImage.isPaused() ? View.VISIBLE : View.GONE);
        }
    }
}
