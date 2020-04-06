package com.example.geometry;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class StepListFragment extends Fragment {

    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step_list, container, false);

        recyclerView = view.findViewById(R.id.recycler);

        LinearLayoutManager llm = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);

        RecycleAdapter adapter = new RecycleAdapter(new ArrayList<StepSlove>());
        recyclerView.setAdapter(adapter);

        return view;
    }
}
