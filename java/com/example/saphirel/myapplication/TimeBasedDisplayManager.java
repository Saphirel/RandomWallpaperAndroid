package com.example.saphirel.myapplication;

import android.app.IntentService;
import android.content.Intent;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by saphirel on 1/27/16.
 */
public class TimeBasedDisplayManager extends IntentService {

    public TimeBasedDisplayManager() {
        super("TimeBasedDisplayManager");
    }

    @Override
    protected void onHandleIntent(Intent workIntent) {
        final ArrayList<String> filesPath = PicturesBDD.getAllPicturesInDB(getApplicationContext());
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                if (filesPath.size() != 0) {
                    Random random = new Random();
                    int randomNum = random.nextInt((filesPath.size()-1) + 1);
                    DisplayWallpaperManager.displayOneWallpaper(getApplicationContext(), filesPath.get(randomNum));
                }
            }
        }, 0, 10, TimeUnit.SECONDS);
    }
}