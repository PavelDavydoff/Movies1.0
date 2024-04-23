package com.example.movies10.ui.poster

import android.app.Activity
import android.os.Bundle
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.movies10.R
import com.example.movies10.presentation.poster.PosterView
import com.example.movies10.util.Creator

class PosterActivity : Activity(), PosterView {

    private lateinit var poster: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_poster)

        poster = findViewById(R.id.poster)

        val url = intent.extras!!.getString("poster","")
        val posterPresenter = Creator.providePosterPresenter(this, url)
        posterPresenter.onCreate()
    }

    override fun showPoster(url: String) {
        Glide.with(applicationContext)
            .load(url)
            .into(poster)
    }
}