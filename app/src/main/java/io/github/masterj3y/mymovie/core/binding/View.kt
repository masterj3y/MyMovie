package io.github.masterj3y.mymovie.core.binding

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("android:gone")
fun gone(view: View, gone: Boolean) {
    view.visibility = if (gone) View.GONE else View.VISIBLE
}