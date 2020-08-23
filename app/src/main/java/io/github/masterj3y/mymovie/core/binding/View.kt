package io.github.masterj3y.mymovie.core.binding

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import io.github.masterj3y.mymovie.core.extension.loadFromUrl

@BindingAdapter("loadImageFromUrl")
fun loadImageFromUrl(view: ImageView, url: String) = view.loadFromUrl(url)

@BindingAdapter("android:gone")
fun gone(view: View, gone: Boolean) {
    view.visibility = if (gone) View.GONE else View.VISIBLE
}