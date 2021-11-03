package com.example.geometry.ui.figures

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.geometry.R
import com.google.android.material.bottomsheet.BottomSheetBehavior

class figures : Fragment() {

    companion object {
        fun newInstance() = figures()
    }

    private lateinit var viewModel: FigursViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_figures, container, false)
    }
}