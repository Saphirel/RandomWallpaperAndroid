package com.example.saphirel.myapplication;

import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by saphirel on 1/22/16.
 */
public class OnClickListeners implements View.OnClickListener {
    @Override
    public void onClick(final View v) {
        v.startAnimation(AnimationUtils.loadAnimation(MainActivity.context, R.anim.image_click));
        new Thread(new Runnable() {
            @Override
            public void run() {
                ArrayList<String> files = PicturesBDD.getAllPicturesInDB(MainActivity.context);
                DisplayWallpaperManager.displayOneWallpaper(MainActivity.context, files.get(v.getId()));
            }
        }).start();
    }
}
