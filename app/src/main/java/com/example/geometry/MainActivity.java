package com.example.geometry;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.geometry.GUI.Builder;
import com.example.geometry.GUI.ItemAdapter;
import com.example.geometry.GUI.ListAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Builder builder;
    private RecyclerView mRecycleView;
    private List<ItemAdapter> mList = new ArrayList<>();
    private ListAdapter mAdapter;
    ListView listView;
    RelativeLayout layout;

    ImageButton solve_button;

    ImageButton circleMode;
    ImageButton lineMode;
    ImageButton moveMode;
    ImageButton angleMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        //addList();
        //adapter();
    }

    private void init(){
        setContentView(R.layout.activity_main);
        builder = new Builder(this);
        layout = new RelativeLayout(this);

        circleMode = findViewById(R.id.circle);
        lineMode = findViewById(R.id.line);
        moveMode = findViewById(R.id.move);
        angleMode = findViewById(R.id.angle);

        circleMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Builder.mode = Builder.CIRCLE_MODE;
            }
        });
        lineMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Builder.mode = Builder.LINE_MODE;
            }
        });
        moveMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Builder.mode = Builder.MOVE_MODE;
            }
        });
        angleMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Builder.mode = Builder.ANGLE_MODE;
            }
        });

        /*mRecycleView = findViewById(R.id.recycler_view);//new RecyclerView(this);


        solve_button = findViewById(R.id.solve_button);
        solve_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                 RuleBase.solve(DrawFragment.drawCanvas);
                Intent intent = new Intent(MainActivity.this, SolveActivity.class);
                startActivity(intent);
            }
        });

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        builder.setLayoutParams(params);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        mRecycleView.setLayoutParams(params);
        /*arr[0][0] = 1;
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
        arr[2][5] = 1;*/
        //layout.addView(mRecycleView);
        //layout.addView(builder);
        ExpertSystem.addNewFactsFromExist();
        ExpertSystem.createExtendedMatrix();
        ArrayList<ArrayList<Float>> X = ExpertSystem.extended_matrix;
        Log.d("Mat", X.size() + "");
        for (int i = 0; i < X.size(); i++) {
            String str = "";
            for (int j = 0; j < X.get(i).size(); j++) {
                str += X.get(i).get(j) + " ";
            }
            Log.d("Mat", str);
        }
        Log.d("Mat", "*\n\n\n*");
        float[][] arr = new float[X.size()][X.get(0).size()];
        for (int i = 0; i < X.size(); i++) {
            for (int j = 0; j < X.get(0).size(); j++) {
                arr[i][j] = X.get(i).get(j);
            }
        }
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
        Log.d("Mat", "*\n\n\n*");
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
