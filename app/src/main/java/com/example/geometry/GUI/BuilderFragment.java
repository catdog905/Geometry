package com.example.geometry.GUI;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.geometry.MainActivity;
import com.example.geometry.R;


public class BuilderFragment extends Fragment {

    BuilderFigure builderFigure;
    ImageButton menuButton;
    ImageButton circleButton;
    ImageButton lineButton;
    ImageButton moveButton;
    ImageButton angleButton;
    ImageButton backButton;
    ImageButton solveButton;
    EditText editText;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_constructor, container, false);

        builderFigure = root.findViewById(R.id.builder);
        menuButton = root.findViewById(R.id.menu_button);
        circleButton  = root.findViewById(R.id.circle);
        lineButton = root.findViewById(R.id.line);
        moveButton = root.findViewById(R.id.move);
        angleButton = root.findViewById(R.id.angle);
        backButton = root.findViewById(R.id.back);
        editText = root.findViewById(R.id.editText);
        solveButton = root.findViewById(R.id.solve_button);



        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.drawer.openDrawer(Gravity.LEFT);
            }
        });
        circleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputHandler.mode = InputHandler.CIRCLE_MODE;
            }
        });
        lineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputHandler.mode = InputHandler.LINE_MODE;
            }
        });
        moveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputHandler.mode = InputHandler.MOVE_MODE;
            }
        });
        angleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputHandler.mode = InputHandler.ANGLE_MODE;
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //builderFigure.getFigureUI().createFirstFacts();
                //String str = "";
                //for (String fact : builderFigure.getFigureUI().facts){
                //    str += fact + " ";
                //}
                //Log.d("FUItoF", str);
            }
        });
        solveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputHandler.ANGLE_TEXT = editText.getText().toString();
            }
        });
        return root;
    }
}
