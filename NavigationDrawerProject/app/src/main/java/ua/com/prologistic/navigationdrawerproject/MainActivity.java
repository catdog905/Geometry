package ua.com.prologistic.navigationdrawerproject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import ua.com.prologistic.navigationdrawerproject.adapter.DrawerAdapter;
import ua.com.prologistic.navigationdrawerproject.fragments.EmailFragment;
import ua.com.prologistic.navigationdrawerproject.fragments.InfoFragment;
import ua.com.prologistic.navigationdrawerproject.fragments.MapFragment;
import ua.com.prologistic.navigationdrawerproject.model.ItemModel;

public class MainActivity extends AppCompatActivity {

    private String[] mItemTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerListView;
    private Toolbar mToolbar;
    private CharSequence mTitle;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTitle = getTitle();
        mItemTitles = getResources().getStringArray(R.array.drawer_items);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerListView = (ListView) findViewById(R.id.left_drawer);

        setupToolbar();

        ItemModel[] dItems = fillDataModel();

        DrawerAdapter adapter = new DrawerAdapter(this, R.layout.item_row, dItems);
        mDrawerListView.setAdapter(adapter);
        mDrawerListView.setOnItemClickListener(new ItemClickListener());
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mDrawerLayout.addDrawerListener(mDrawerToggle);
        setupDrawerToggle();

    }

    // формируем массив с данными для адаптера
    private ItemModel[] fillDataModel() {
        return new ItemModel[]{
                new ItemModel(android.R.drawable.ic_dialog_email, "Связаться"),
                new ItemModel(android.R.drawable.ic_dialog_info, "Подробнее"),
                new ItemModel(android.R.drawable.ic_dialog_map, "Посмотреть на карте")
        };
    }

    // по клику на элемент списка устанавливаем нужный фрагмент в контейнер
    private class ItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Fragment fragment = null;

            // на основании выбранного элемента меню
            // вызываем соответственный ему фрагмент
            switch (position) {
                case 0:
                    fragment = new EmailFragment();
                    break;
                case 1:
                    fragment = new InfoFragment();
                    break;
                case 2:
                    fragment = new MapFragment();
                    break;

                default:
                    break;
            }
            if (fragment != null) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

                mDrawerListView.setItemChecked(position, true);
                mDrawerListView.setSelection(position);
                setTitle(mItemTitles[position]);
                mDrawerLayout.closeDrawer(mDrawerListView);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    void setupToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
    }

    void setupDrawerToggle() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.app_name, R.string.app_name);
        // Это необходимо для изменения иконки на основании текущего состояния
        mDrawerToggle.syncState();
    }
}