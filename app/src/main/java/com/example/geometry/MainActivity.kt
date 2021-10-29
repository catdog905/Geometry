package com.example.geometry

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import android.view.Menu
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.geometry.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var bottomNav:BottomNavigationView
    lateinit var navController:NavController
    lateinit var drawerLayout: DrawerLayout
    private lateinit var appBarConfiguration: AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNav = bottom_navigation


        navController = findNavController(R.id.hostFragment)
        setupBottomNavigation()

        drawerLayout = drawer_layout
        // For Navigation UP
        appBarConfiguration = AppBarConfiguration(navController.graph,drawerLayout)
        NavigationUI.setupActionBarWithNavController(this,navController,drawerLayout)

        //NavigationUI.setupWithNavController(navigation_view,navController)
        NavigationUI.setupWithNavController(navigation_view,navController)
        
    }

    override fun onSupportNavigateUp(): Boolean {
        //return navController.navigateUp()
        return NavigationUI.navigateUp(navController,appBarConfiguration)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    private fun setupBottomNavigation() {
        bottomNav.setupWithNavController(navController)
    }
}