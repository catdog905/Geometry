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


class MainActivity() : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var buttonCursor: ImageButton
    private lateinit var buttonPointWithLetter: ImageButton
    private lateinit var buttonSegment: ImageButton
    private lateinit var buttonLine: ImageButton
    private lateinit var textCursor: TextView
    private lateinit var textPointWithLetter: TextView
    private lateinit var textSegment: TextView
    private lateinit var textLine: TextView
    private lateinit var buttonPolygon: ImageButton
    private lateinit var textPolygon: TextView
    private lateinit var buttonCircle: ImageButton
    private lateinit var textCircle: TextView
    private lateinit var x: String



    /**private fun update(x: Int){
    ImageViewCompat.setImageTintList(
    x, ColorStateList.valueOf(
    resources.getColor(R.color.red)))
    //textCursor.setTextColor(resources.getColor(R.color.red))
    }*/

    val listIndexes: MutableList<String> = ArrayList()
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

        //cursor
        buttonCursor = findViewById(R.id.button_cursor)
        textCursor = findViewById(R.id.text_cursor)
        //point with letter
        buttonPointWithLetter = findViewById(R.id.button_point_with_letter)
        textPointWithLetter = findViewById(R.id.text_point_with_letter)
        //segment
        buttonSegment = findViewById(R.id.button_segment)
        textSegment = findViewById(R.id.text_segment)
        //line
        buttonLine = findViewById(R.id.button_line)
        textLine = findViewById(R.id.text_line)
        //polygon
        buttonPolygon = findViewById(R.id.button_polygon)
        textPolygon = findViewById(R.id.text_polygon)
        //circle
        buttonCircle = findViewById(R.id.button_circle)
        textCircle = findViewById(R.id.text_circle)

        buttonCursor.setOnClickListener(this)
        buttonPointWithLetter.setOnClickListener(this)
        buttonSegment.setOnClickListener(this)
        buttonLine.setOnClickListener(this)
        buttonPolygon.setOnClickListener(this)
        buttonCircle.setOnClickListener(this)


    }

    override fun onClick(v: View) {
        val buttonIndex = listOf(buttonCursor,buttonPointWithLetter,buttonSegment,buttonLine,buttonPolygon,buttonCircle)
        val textIndex = listOf(textCursor,textPointWithLetter,textSegment,textLine,textPolygon,textCircle)

        for(x in buttonIndex){
            ImageViewCompat.setImageTintList(
                x, ColorStateList.valueOf(
                    resources.getColor(R.color.black)))}
        for(x in textIndex){
            x.setTextColor(resources.getColor(R.color.black))
        }

        when (v.id) {
            R.id.button_cursor -> {
                ImageViewCompat.setImageTintList(
                    buttonCursor, ColorStateList.valueOf(
                        resources.getColor(R.color.red)))
                textCursor.setTextColor(resources.getColor(R.color.red))
            }
            R.id.button_point_with_letter -> {
                ImageViewCompat.setImageTintList(
                    buttonPointWithLetter, ColorStateList.valueOf(
                        resources.getColor(R.color.red)))
                textPointWithLetter.setTextColor(resources.getColor(R.color.red))
            }
            R.id.button_segment -> {
                ImageViewCompat.setImageTintList(
                    buttonSegment, ColorStateList.valueOf(
                        resources.getColor(R.color.red)))
                textSegment.setTextColor(resources.getColor(R.color.red))
            }
            R.id.button_line -> {
                ImageViewCompat.setImageTintList(
                    buttonLine, ColorStateList.valueOf(
                        resources.getColor(R.color.red)))
                textLine.setTextColor(resources.getColor(R.color.red))
            }
            R.id.button_polygon-> {
                ImageViewCompat.setImageTintList(
                    buttonPolygon, ColorStateList.valueOf(
                        resources.getColor(R.color.red)))
                textPolygon.setTextColor(resources.getColor(R.color.red))
            }
            R.id.button_circle -> {
                ImageViewCompat.setImageTintList(
                    buttonCircle, ColorStateList.valueOf(
                        resources.getColor(R.color.red)))
                textCircle.setTextColor(resources.getColor(R.color.red))
            }
        }
    }
}

