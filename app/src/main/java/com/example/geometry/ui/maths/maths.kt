package com.example.geometry.ui.maths

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.geometry.R

class maths : Fragment() {

    companion object {
        fun newInstance() = maths()
    }

    private lateinit var viewModel: MathsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maths, container, false)
    }

}