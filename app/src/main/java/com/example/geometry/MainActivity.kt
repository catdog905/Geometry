package com.example.geometry

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.NonNull
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.widget.ImageViewCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.geometry.databinding.ActivityMainBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import androidx.coordinatorlayout.widget.CoordinatorLayout

class MainActivity : AppCompatActivity() {

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








        //val navView: BottomNavigationView = binding.navView


        //val buttonCursor: ImageButton = findViewById(R.id.button_cursor)
        //val textCursor: TextView = findViewById(R.id.text_cursor)


        /**val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations. /
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_figures,
                R.id.navigation_maths,
                R.id.navigation_send,
                R.id.navigation_camera
            )
        )*/

        /**setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)*/

        //buttonCursor.setOnClickListener {
        //    ImageViewCompat.setImageTintList(buttonCursor, ColorStateList.valueOf(
        //        resources.getColor(R.color.red)))
         //   textCursor.setTextColor(resources.getColor(R.color.red))
        //}

    }
}
