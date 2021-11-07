package com.example.geometry

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class BottomSheet : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bottom_sheet
        )
        val navView = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
        val navController = findNavController(R.id.navigation_host_fragment)

        navView.setupWithNavController(navController)
    }
}