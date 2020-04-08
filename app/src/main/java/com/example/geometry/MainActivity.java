package com.example.geometry;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.geometry.DrawFigure;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new DrawFigure(this));
    }
}
