package com.caren.weebly;

import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.File;

public class Utils {
    public static String APP_TAG = "Weebly";

    // Returns the Uri for a photo stored on disk given the fileName
    public static Uri getPhotoFileUri(String fileName) {
        // Get safe storage directory for photos
        File mediaStorageDir = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), APP_TAG);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
            Log.d(APP_TAG, "failed to create directory");
        }

        // Return the file target for the photo based on filename
        return Uri.fromFile(new File(mediaStorageDir.getPath() + File.separator + fileName));
    }
}
