package com.example.geometry.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.geometry.R
import com.example.geometry.databinding.FragmentGalleryBinding
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
            return inflater.inflate(R.layout.fragment_gallery, container, false)
        }
    }
