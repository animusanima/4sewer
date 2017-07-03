package com.github.animusanima.a4sewer.utility;

import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Created by root on 28.06.17.
 */

public class ImageUtils
{

    public static Bitmap getBitmapFromURI(Uri imageURI, ContentResolver contentResolver)
    {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = true;
        options.inJustDecodeBounds = false;
        Bitmap image = null;

        try {
            final InputStream inStr = contentResolver.openInputStream(imageURI);
            image = BitmapFactory.decodeStream(inStr, null, options);
        } catch(FileNotFoundException ex) {
            Log.e("File not found", "Selected image was not found", ex);
        }
        return image;
    }

    public static Bitmap getBitmapFromFile(File file)
    {
        Bitmap b = null;
        if (file.exists())
        {
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inScaled = true;
            opts.inJustDecodeBounds = false;

            try {
                FileInputStream input = new FileInputStream(file);
                b = BitmapFactory.decodeStream(input);
            } catch (Exception e) {
                Log.e("ERROR", e.getMessage());
            }
        }
        return b;
    }

    public static String getAbsolutePathFromURI(Uri imageURI, ContentResolver contentResolver)
    {
        String absoluteImagePath = getRealPathFromURI(imageURI, contentResolver);
        return absoluteImagePath;
    }

    private static String getRealPathFromURI(Uri uri, ContentResolver contentResolver)
    {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = contentResolver.query(uri,  proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
}