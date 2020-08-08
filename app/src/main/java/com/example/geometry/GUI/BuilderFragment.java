package com.example.geometry.GUI;

import android.app.Activity;
import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.geometry.MainActivity;
import com.example.geometry.MathModel;
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
    Keyboard keyboard;
    KeyboardView keyboardView;
    static EditText editText;

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

        // Create the Keyboard
        keyboard= new Keyboard(getContext(),R.xml.keyboard);

        // Lookup the KeyboardView
        keyboardView= (KeyboardView)root.findViewById(R.id.keyboardView);
        // Attach the keyboard to the view
        keyboardView.setKeyboard( keyboard );

        // Do not show the preview balloons
        //mKeyboardView.setPreviewEnabled(false);

        // Install the key handler
        keyboardView.setOnKeyboardActionListener(onKeyboardActionListener);

        builderFigure.setKeyboardView(keyboardView);



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
                //MathModel mathModel = new MathModel();
                //mathModel.getFactsFromFigure(builderFigure.getFigureUISingleton());

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
            }
        });
        return root;
    }

    private KeyboardView.OnKeyboardActionListener onKeyboardActionListener = new KeyboardView.OnKeyboardActionListener() {
        @Override public void onKey(int primaryCode, int[] keyCodes)
        {
            //Here check the primaryCode to see which key is pressed
            //based on the android:codes property

            builderFigure.inputHandler.currentTitle.text += primaryCode;
            Log.i("Key","hello");
        }

        @Override public void onPress(int arg0) {
        }

        @Override public void onRelease(int primaryCode) {
        }

        @Override public void onText(CharSequence text) {
        }

        @Override public void swipeDown() {
        }

        @Override public void swipeLeft() {
        }

        @Override public void swipeRight() {
        }

        @Override public void swipeUp() {
        }
    };
}
