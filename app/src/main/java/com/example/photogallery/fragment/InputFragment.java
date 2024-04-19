package com.example.photogallery.fragment;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.photogallery.MainActivity;
import com.example.photogallery.databinding.FragmentInputBinding;
import com.example.photogallery.databinding.InputDialogBinding;
import com.example.photogallery.listener.DownloadActionListener;


public class InputFragment extends Fragment {
    public interface OnDialogDismissedListener {
        void onDialogDismissed();
    }

    private static final String TAG = InputFragment.class.getSimpleName();

    FragmentInputBinding binding;
    InputDialogBinding bindingDialog;
    Dialog dialog;
    private OnDialogDismissedListener mListener;
    private DownloadActionListener mDownloadActionListener;

    public void setOnDialogDismissedListener(OnDialogDismissedListener listener) {
        mListener = listener;
    }

    public InputFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentInputBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        showInputDialog();
        setOnDialogDismissedListener((MainActivity) requireActivity());
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof DownloadActionListener) {
            mDownloadActionListener = (DownloadActionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement DownloadActionListener");
        }
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        binding = null;
    }

    private void showInputDialog(){
        bindingDialog = InputDialogBinding.inflate(getLayoutInflater());
        dialog = new Dialog(bindingDialog.getRoot().getContext());

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(bindingDialog.getRoot());

        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.setOnDismissListener(dialog -> {
            onDialogDismissed();
        });

        bindingDialog.btnAdd.setOnClickListener(v -> {
            String url = bindingDialog.etLink.getText().toString();
            String fileName = bindingDialog.etName.getText().toString();
            String format = bindingDialog.etFormat.getText().toString();
            mDownloadActionListener.onStartDownload(fileName, url, format);

            dialog.dismiss();
        });

        bindingDialog.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void onDialogDismissed(){
        if (mListener != null) {
            mListener.onDialogDismissed();
        }
    }
}