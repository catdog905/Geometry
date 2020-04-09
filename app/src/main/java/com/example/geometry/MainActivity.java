package com.example.geometry;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecycleAdapter.addItem(new StepSlove("Выражение", "Правило"));
        RecycleAdapter.addItem(new StepSlove("Выражение", "Правило"));
    }
}
