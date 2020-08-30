package io.github.masterj3y.mymovie.core.binding

import android.view.View
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.florent37.glidepalette.BitmapPalette
import com.github.florent37.glidepalette.GlidePalette
import io.github.masterj3y.mymovie.core.extension.loadFromUrl

@BindingAdapter("android:loadImageFromUrl")
fun loadImageFromUrl(view: ImageView, url: String?) {
    url?.let { view.loadFromUrl(it) }
}

@BindingAdapter("android:loadImageFromUrl", "android:loadBackgroundPalette")
fun loadImageFromUrl(view: ImageView, url: String?, paletteView: CardView) {
    url?.let {
        Glide.with(view.context)
            .load(url)
            .listener(
                GlidePalette.with(url)
                    .use(BitmapPalette.Profile.VIBRANT_DARK)
                    .intoCallBack { palette ->
                        val rgb = palette?.dominantSwatch?.rgb
                        if (rgb != null) {
                            paletteView.setCardBackgroundColor(rgb)
                        }
                    }
                    .crossfade(true))
            .into(view)
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