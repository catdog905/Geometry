package com.example.geometry;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Builder builder;
    private RecyclerView mRecycleView;
    private List<ItemAdapter> mList = new ArrayList<>();
    private ListAdapter mAdapter;
    ListView listView;
    RelativeLayout layout;


    public static EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        addList();
        adapter();

        editText = findViewById(R.id.editText);
    }

    private void init(){
        setContentView(R.layout.activity_main);
        builder = new Builder(this);
        layout = new RelativeLayout(this);
        mRecycleView = findViewById(R.id.recycler_view);//new RecyclerView(this);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        builder.setLayoutParams(params);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        mRecycleView.setLayoutParams(params);
        float[][] arr = new float[3][6];
        arr[0][0] = 1;
        arr[0][1] = 1;
        arr[0][2] = 14;
        arr[0][3] = 0;
        arr[0][4] = 2;
        arr[0][5] = 0;

        arr[1][0] = 3;
        arr[1][1] = 4;
        arr[1][2] = 2;
        arr[1][3] = 3;
        arr[1][4] = 0;
        arr[1][5] = 1;

        arr[2][0] = 2;
        arr[2][1] = 3;
        arr[2][2] = -3;
        arr[2][3] = 3;
        arr[2][4] = -2;
        arr[2][5] = 1;
        LinearAlgebra.GaussMethodSolution solution = LinearAlgebra.gaussMethod(arr);
        Log.d("Mat",solution.status + " ");
        for (float[] anArr : solution.extended_matrix) {
            String str = "";
            for (float x: anArr) {
                str += (float)((int)(x*1000 ))/1000 + " ";
            }
            Log.d("Mat",str + " ");
        }
        for (float x:solution.where_vars) {
            Log.d("Mat",x + " ");
        }
        //layout.addView(mRecycleView);
        //layout.addView(builder);
    }
    private void addList(){
        ItemAdapter itemAdapter = new ItemAdapter();
        itemAdapter.setImage(R.drawable.circle);
        mList.add(itemAdapter);

        itemAdapter = new ItemAdapter();
        itemAdapter.setImage(R.drawable.line);
        mList.add(itemAdapter);

        itemAdapter = new ItemAdapter();
        itemAdapter.setImage(R.drawable.move);
        mList.add(itemAdapter);

        itemAdapter = new ItemAdapter();
        itemAdapter.setImage(R.drawable.angle);
        mList.add(itemAdapter);

        itemAdapter = new ItemAdapter();
        itemAdapter.setImage(R.drawable.regular_triangle);
        mList.add(itemAdapter);

        itemAdapter = new ItemAdapter();
        itemAdapter.setImage(R.drawable.right_triangle);
        mList.add(itemAdapter);

        itemAdapter = new ItemAdapter();
        itemAdapter.setImage(R.drawable.square);
        mList.add(itemAdapter);

        itemAdapter = new ItemAdapter();
        itemAdapter.setImage(R.drawable.trapeze);
        mList.add(itemAdapter);
    }

    private void adapter(){
        mAdapter = new ListAdapter(mList, this);
        mRecycleView.setAdapter(mAdapter);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        mAdapter.notifyDataSetChanged();

    }


}
