package com.example.geometry.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.geometry.R
import com.example.geometry.ui.camera.CameraViewModel

    class gallery: Fragment() {


        companion object {
            fun newInstance() = gallery()
        }

        private lateinit var viewModel: CameraViewModel

        override fun onCreateView(

            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            return inflater.inflate(R.layout.fragment_settings, container, false)
        }
    }
