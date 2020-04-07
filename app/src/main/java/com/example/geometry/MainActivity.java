package com.example.geometry;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(new GUI(this));
        setContentView(R.layout.activity_main);

        for (int i = 0; i < 100; i++) {
            RecycleAdapter.addItem(new StepSlove("В этом шаге я к %s  прибавляю %s",
                                                     "Я использую правило сложения в этом шаге",
                                                           Integer.toString(i), Integer.toString(1)));
        }
    }
}
