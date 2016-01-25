package com.example.saphirel.myapplication;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Environment;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by saphirel on 1/22/16.
 */
public class DisplayWallpaperManager {
    public static void displayOneWallpaper(Context context, String path) {

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        options.inSampleSize = calculateInSampleSize(options, display.getWidth(), display.getHeight()); //width and height

        options.inJustDecodeBounds = false;
        Bitmap picture2 = BitmapFactory.decodeFile(path, options);

        Bitmap background = Bitmap.createBitmap(display.getWidth(), display.getHeight(), Bitmap.Config.ARGB_8888);
        float originalHeight = picture2.getHeight();
        Canvas canvas = new Canvas(background);
        float scale = display.getHeight()/originalHeight;
        Matrix transformation = new Matrix();
        transformation.preScale(scale, scale);
        Paint paint = new Paint();
        paint.setFilterBitmap(true);
        canvas.drawBitmap(picture2, transformation, paint);
        //TODO Reflechir a comment editer quelle zone de l'image est choisie en WallP
        //Bitmap scaledBitmap = Bitmap.createScaledBitmap(picture2, display.getWidth(), display.getHeight(), true);
        //Bitmap rotatedBitmap = Bitmap.createBitmap(scaledBitmap , 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);

        picture2.recycle();

        try {
            WallpaperManager.getInstance(context).setBitmap(background);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
}
