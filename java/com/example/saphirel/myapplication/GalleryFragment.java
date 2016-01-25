package com.example.saphirel.myapplication;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;

/**
 * Created by saphirel on 1/22/16.
 */
public class GalleryFragment extends Fragment {

    public GalleryFragment() {

    }

    // Load Gallery xml view file.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }

        container.removeAllViews();
        View rootView = inflater.inflate(R.layout.content_gallery, container, false);
        getActivity().setTitle("Gallery");

        GridLayout gridLayout = (GridLayout) rootView.findViewById(R.id.gridLayout);

        DisplayPictureManager.setImages(MainActivity.context, gridLayout);
        return rootView;
    }
}
