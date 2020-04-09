package com.example.geometry;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class DrawFragment extends Fragment {

    private static GUI drawCanvas;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (drawCanvas == null)
            drawCanvas = new GUI(this.getContext());

        return new GUI(drawCanvas, this.getContext());
    }
}
