package com.example.movies10.presentation.poster

import android.app.Activity
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.movies10.R

class PosterPresenter(private val view: PosterView, private val url: String) {

    fun onCreate(){
        view.showPoster(url)
    }
}