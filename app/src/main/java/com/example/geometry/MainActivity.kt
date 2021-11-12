package com.example.geometry

import android.content.res.ColorStateList
import android.database.CrossProcessCursor
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.ImageViewCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.geometry.databinding.ActivityMainBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat.setImageTintList
import androidx.core.widget.TextViewCompat


class MainActivity: AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val llBottomSheet = findViewById<CoordinatorLayout>(R.id.bottom_sheet)

        val bottomSheetBehavior = BottomSheetBehavior.from(llBottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        bottomSheetBehavior.isHideable = false


        val navView = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
        val navController = findNavController(R.id.navigation_host_fragment)
        navView.setupWithNavController(navController)
    }
}

