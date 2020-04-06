package com.example.geometry;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.RecycleViewHolder> {

    private static List<StepSlove> stepSloveList;

    public RecycleAdapter(List<StepSlove> persons) {
        stepSloveList = persons;
    }

    public static void addItem(StepSlove stepSlove) {
        stepSloveList.add(stepSlove);
    }

    public static void clear() {
        stepSloveList.clear();
    }

    static class RecycleViewHolder extends RecyclerView.ViewHolder {

        TextView name;

        public RecycleViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.text);
        }
    }

    @NonNull
    @Override
    public RecycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.step_card, parent, false);
        return new RecycleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewHolder holder, int position) {
        holder.name.setText(stepSloveList.get(position).text);
    }

    @Override
    public int getItemCount() {
        return stepSloveList.size();
    }
}