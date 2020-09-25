package io.github.masterj3y.mymovie.core.binding

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.github.masterj3y.mymovie.core.extension.loadFromUrl

@BindingAdapter("android:loadImageFromUrl")
fun loadImageFromUrl(view: ImageView, url: String?) {
    url?.let { view.loadFromUrl(it) }
}

@BindingAdapter("android:bindDrawableStart")
fun loadImageFromResource(view: TextView, @DrawableRes drawableRes: Int?) {
    drawableRes?.let {
        view.setCompoundDrawablesRelativeWithIntrinsicBounds(it, 0, 0, 0)
    }
}

@BindingAdapter("android:gone")
fun gone(view: View, gone: Boolean) {
    view.visibility = if (gone) View.GONE else View.VISIBLE
}

@BindingAdapter("android:invisible")
fun invisible(view: View, invisible: Boolean) {
    view.visibility = if (invisible) View.INVISIBLE else View.VISIBLE
}

@BindingAdapter("android:adapter")
fun bindAdapter(view: RecyclerView, adapter: RecyclerView.Adapter<*>) {
    view.adapter = adapter
}

@BindingAdapter("android:attachToRecyclerView")
fun attachToRecyclerView(view: FloatingActionButton, recyclerView: RecyclerView) {
    recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if (dy > 0 && view.isShown)
                view.hide()
            else
                view.show()
        }
    })
}