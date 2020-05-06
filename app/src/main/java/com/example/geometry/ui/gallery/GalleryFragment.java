package com.example.geometry.ui.gallery;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.geometry.GUI.Builder;
import com.example.geometry.MainActivity;
import com.example.geometry.R;


public class GalleryFragment extends Fragment {

    ImageButton menuButton;
    ImageButton circleButton;
    ImageButton lineButton;
    ImageButton moveButton;
    ImageButton angleButton;
    TextView name;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);

        menuButton = root.findViewById(R.id.menu_button);
        circleButton  = root.findViewById(R.id.circle);
        lineButton = root.findViewById(R.id.line);
        moveButton = root.findViewById(R.id.move);
        angleButton = root.findViewById(R.id.angle);



        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.drawer.openDrawer(Gravity.LEFT);
            }
        });
        circleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Builder.mode = Builder.CIRCLE_MODE;
            }
        });
        lineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Builder.mode = Builder.LINE_MODE;
            }
        });
        moveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Builder.mode = Builder.MOVE_MODE;
            }
        });
        angleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Builder.mode = Builder.ANGLE_MODE;
            }
        });
        return root;
    }
}
