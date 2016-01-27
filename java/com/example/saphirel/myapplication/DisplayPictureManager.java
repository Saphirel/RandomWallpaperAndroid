package com.example.saphirel.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Environment;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.GridLayout;
import android.widget.ImageView;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by saphirel on 1/22/16.
 */
public class DisplayPictureManager {

    public static void setImages(Context context, GridLayout gridLayout) {

        Bitmap[] picturesToDisplay = getAllFilesInStorage(context);
        gridLayout.removeAllViews();
        gridLayout.setColumnCount(3);

        for (int i = 0; i < picturesToDisplay.length; i++) {
            ImageView imageView = new ImageView(context);
            imageView.setImageBitmap(picturesToDisplay[i]);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(1, 1, 1, 1);
            imageView.setId(i);
            imageView.setOnClickListener(new OnClickListeners());
            gridLayout.addView(imageView);
        }
    }

    public static Bitmap[] getAllFilesInStorage(Context context) {

        ArrayList<String> filesPath = PicturesBDD.getAllPicturesInDB(context);
        Bitmap[] res = new Bitmap[filesPath.size()];

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        for (int i = 0; i < filesPath.size(); i++) {
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(filesPath.get(i), options);

            options.inSampleSize = DisplayWallpaperManager.calculateInSampleSize(options, 1000, 1000); //width and height

            options.inJustDecodeBounds = false;
            Bitmap picture2 = BitmapFactory.decodeFile(filesPath.get(i), options);

            res[i] = scaleBitmap(picture2, (display.getWidth()-100)/3, (display.getWidth()-100)/3);
            picture2.recycle();
        }
        return res;
    }

    public static Bitmap scaleBitmap(Bitmap originalBitmap, int finalWidth, int finalHeight) {

        Bitmap thumbnail = Bitmap.createBitmap(finalWidth, finalHeight, Bitmap.Config.ARGB_8888);
        float originalHeight = originalBitmap.getHeight(), originalWidth = originalBitmap.getWidth();
        Canvas canvas = new Canvas(thumbnail);

        float scale;
        if (originalHeight < originalWidth)
            scale = finalWidth/originalHeight;
        else
            scale = finalWidth/originalWidth;

        Matrix transformation = new Matrix();
        transformation.preScale(scale, scale);
        Paint paint = new Paint();
        paint.setFilterBitmap(true);
        canvas.drawBitmap(originalBitmap, transformation, paint);

        return thumbnail;
    }
}
