package com.example.geometry.ui.figurs

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.geometry.R

class figurs : Fragment() {

    companion object {
        fun newInstance() = figurs()
    }

    private lateinit var viewModel: FigursViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.figurs_fragment, container, false)
    }
}