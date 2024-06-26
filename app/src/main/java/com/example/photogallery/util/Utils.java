package com.example.photogallery.util;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.ParcelFileDescriptor;
import android.provider.OpenableColumns;
import android.util.Pair;
import android.view.Window;
import android.view.WindowManager;

import androidx.core.content.res.ResourcesCompat;

import com.example.photogallery.R;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;

public class Utils {

    public static final int NUM_PARTS = 2;

    /**
     * Retrieves the size of a file referenced by a given URI.
     *
     * @param context The context of the calling component.
     * @param uri     The URI of the file whose size is to be retrieved.
     * @return The size of the file in bytes, or 0 if unable to retrieve the size.
     */
    public static long getFileSize(Context context, Uri uri) {
        ParcelFileDescriptor parcelFileDescriptor = null;
        FileInputStream inputStream = null;
        try {
            parcelFileDescriptor = context.getContentResolver().openFileDescriptor(uri, "r");
            if (parcelFileDescriptor != null) {
                FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
                inputStream = new FileInputStream(fileDescriptor);
                return inputStream.getChannel().size();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (parcelFileDescriptor != null) {
                try {
                    parcelFileDescriptor.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return 0;
    }

    /**
     * Retrieves the file name and type (extension) from the given Uri.
     *
     * @param context The context to use for accessing content resolver.
     * @param uri     The Uri of the file.
     * @return A Pair containing the file name and type (extension), or null if the information cannot be retrieved.
     */
    @SuppressLint("Range")
    public static Pair<String, String> getFileName(Context context, Uri uri) {
        String fileName = null;
        String fileType = null;

        if (uri.getScheme() != null) {
            if (uri.getScheme().equals("content")) {
                ContentResolver contentResolver = context.getContentResolver();
                Cursor cursor = contentResolver.query(uri, null, null, null, null);
                if (cursor != null) {
                    try {
                        if (cursor.moveToFirst()) {
                            fileName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                        }
                    } finally {
                        cursor.close();
                    }
                }
            } else if (uri.getScheme().equals("file")) {
                fileName = uri.getLastPathSegment();
            }
        }

        if (fileName != null) {
            Pair<String, String> nameAndType = separateNameAndType(fileName);
            if (nameAndType != null) {
                fileName = nameAndType.first;
                fileType = nameAndType.second;
            }
        }

        return new Pair<>(fileName, fileType);
    }

    /**
     * Formats a given file size into a human-readable string with appropriate units.
     *
     * @param size The file size in bytes.
     * @return A formatted string representing the file size with appropriate units (e.g., KB, MB).
     */
    public static String formatFileSize(long size) {
        if (size <= 0) return "0 B";

        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));

        return new DecimalFormat("#,##0.#")
                .format(size / Math.pow(1024, digitGroups)) +
                " " + units[digitGroups];
    }

    private static Pair<String, String> separateNameAndType(String fileName) {
        String[] strParts = fileName.split("\\.");
        if (strParts.length != NUM_PARTS) {
            return new Pair<>(fileName, "");
        }

        return new Pair<>(strParts[0], strParts[1]);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void setStatusBarGradiant(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            Drawable bg = ResourcesCompat.getDrawable(activity.getResources(), R.drawable.gradient_color_primary, activity.getTheme());
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

            window.setStatusBarColor(activity.getResources().getColor(android.R.color.transparent));
            window.setNavigationBarColor(activity.getResources().getColor(android.R.color.transparent));
            window.setBackgroundDrawable(bg);
        }
    }

    public static String formatDuration(long millis) {
        if (millis < 0) {
            return "--";
        }

        long hours = millis / 3600000;
        long minutes = (millis % 3600000) / 60000;
        long seconds = ((millis % 3600000) % 60000) / 1000;
        long milliseconds = millis % 1000;

        StringBuilder sb = new StringBuilder();
        if (hours > 0) {
            sb.append(hours).append("h ");
        }
        if (minutes > 0) {
            sb.append(minutes).append("m ");
        }
        if (seconds > 0) {
            sb.append(seconds).append("s ");
        }
        if (milliseconds > 0) {
            sb.append(milliseconds).append("ms");
        }

        return sb.toString().trim();
    }
}
