package io.github.masterj3y.mymovie.core.binding

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import io.github.masterj3y.mymovie.core.extension.loadFromUrl

@BindingAdapter("android:loadImageFromUrl")
fun loadImageFromUrl(view: ImageView, url: String?) {
    url?.let { view.loadFromUrl(it) }
}

@BindingAdapter("android:gone")
fun gone(view: View, gone: Boolean) {
    view.visibility = if (gone) View.GONE else View.VISIBLE
}

@BindingAdapter("android:adapter")
fun bindAdapter(view: RecyclerView, adapter: RecyclerView.Adapter<*>) {
    view.adapter = adapter
}