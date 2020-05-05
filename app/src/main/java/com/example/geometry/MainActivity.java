package com.example.geometry;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.geometry.GUI.Builder;
import com.example.geometry.GUI.DrawerAdapter;
import com.example.geometry.GUI.ItemAdapter;
import com.example.geometry.GUI.ItemModel;
import com.example.geometry.GUI.ListAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Builder builder;
    private RecyclerView mRecycleView;
    private List<ItemAdapter> mList = new ArrayList<>();
    private ListAdapter mAdapter;
    Button listView;
    RelativeLayout layout;

    private AppBarConfiguration mAppBarConfiguration;


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

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.layout.activity_main, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

}
