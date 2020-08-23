package io.github.masterj3y.mymovie.core.extension

import android.graphics.Bitmap
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.Transformation
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

fun ImageView.loadFromUrl(url: String, vararg transformations: Transformation<Bitmap> = arrayOf()) =
    Glide.with(context)
        .load(url)
        .transform(*transformations)
        .centerCrop()
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)