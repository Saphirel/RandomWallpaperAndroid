package com.example.saphirel.myapplication;

/**
 * Created by saphirel on 1/25/16.
 */

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class PicturesBDD {
    /**
     * Called when the activity is first created.
     */
    public static void startDBAndCreateTableIfNotExists(Context context, String tableName) {

        SQLiteDatabase myDB = null;

        try {
            myDB = context.openOrCreateDatabase("RandomWallpaperDb", context.MODE_PRIVATE, null);
            myDB.execSQL("CREATE TABLE IF NOT EXISTS "
                    + tableName
                    + " (path VARCHAR);");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveNewPicture(String path, Context context) {

        SQLiteDatabase myDB = null;
        try {
            myDB = context.openOrCreateDatabase("RandomWallpaperDb", context.MODE_PRIVATE, null);

            myDB.execSQL("INSERT INTO "
                    + "Pictures"
                    + " (path)"
                    + " VALUES ('"+ path +"');");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (myDB != null)
                myDB.close();
        }
    }

    public static ArrayList<String> getAllPicturesInDB(Context context) {

        SQLiteDatabase myDB = null;
        ArrayList<String> files = new ArrayList<>();
        try {
            myDB = context.openOrCreateDatabase("RandomWallpaperDb", context.MODE_PRIVATE, null);
            Cursor c = myDB.rawQuery("SELECT * FROM Pictures", null);

            int Column1 = c.getColumnIndex("path");

            // Check if our result was valid.
            c.moveToFirst();
            int i = 0;
            if (c != null) {
                do {
                    files.add(i, c.getString(Column1));
                } while (c.moveToNext());
            }
        } catch (Exception e) {
            Log.e("Error", "Error", e);
        } finally {
            if (myDB != null)
                myDB.close();
        }
        return files;
    }

    public static void removeAllDataInPictures(Context context) {
        SQLiteDatabase myDB = null;
        try {
            myDB = context.openOrCreateDatabase("RandomWallpaperDb", context.MODE_PRIVATE, null);
            myDB.execSQL("DELETE FROM Pictures;");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (myDB != null)
                myDB.close();
        }
    }
}