package com.example.geometry.ui.figures


import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.widget.ImageViewCompat
import androidx.fragment.app.Fragment
import com.example.geometry.R
import java.util.ArrayList


class Figures : Fragment(), View.OnTouchListener {
    
    companion object {
        fun newInstance() = Figures()
    }

    private val buttonHashMap: HashMap<ImageButton, TextView> = linkedMapOf()
    private val buttonList = listOf("button_cursor","button_point_with_letter","button_segment","button_line","button_polygon","button_circle")
    private val textList = listOf("text_cursor","text_point_with_letter","text_segment","text_line","text_polygon","text_circle")
    private lateinit var viewModel: FiguresViewModel

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View , savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonHashMap[view.findViewById(R.id.button_cursor)]=view.findViewById(R.id.text_cursor)
        buttonHashMap[view.findViewById(R.id.button_point_with_letter)]=view.findViewById(R.id.text_point_with_letter)
        buttonHashMap[view.findViewById(R.id.button_segment)]=view.findViewById(R.id.text_segment)
        buttonHashMap[view.findViewById(R.id.button_line)]=view.findViewById(R.id.text_line)
        buttonHashMap[view.findViewById(R.id.button_polygon)]=view.findViewById(R.id.text_polygon)
        buttonHashMap[view.findViewById(R.id.button_circle)]=view.findViewById(R.id.text_circle)

        buttonHashMap.forEach { (key) -> key.setOnTouchListener(this) }
    }
    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(v: View , p1: MotionEvent?): Boolean {

        buttonHashMap.forEach { (key, value) ->
            ImageViewCompat.setImageTintList(
                key, ColorStateList.valueOf(resources.getColor(R.color.black)))
            value.setTextColor(resources.getColor(R.color.black))}

        val animAlpha = AnimationUtils.loadAnimation(this.context, R.anim.alpha)
        v.startAnimation(animAlpha)
        buttonHashMap[v]?.startAnimation(animAlpha)
        ImageViewCompat.setImageTintList(
            v as ImageButton , ColorStateList.valueOf(
                resources.getColor(R.color.red)))
        buttonHashMap[v]?.setTextColor(resources.getColor(R.color.red))
    return false
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_figures, container, false)
    }

}