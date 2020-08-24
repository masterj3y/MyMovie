package io.github.masterj3y.mymovie.core.extension

import com.google.android.material.appbar.AppBarLayout
import kotlin.math.abs

enum class State {
    EXPANDED,
    COLLAPSED
}

inline fun AppBarLayout.onCollapsingListener(crossinline listener: (state: State) -> Unit) {
    addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->

        when {
            abs(verticalOffset) - appBarLayout.totalScrollRange == 0 -> {
                listener(State.COLLAPSED)
            }
            verticalOffset == 0 -> {
                listener(State.EXPANDED)
            }
        }
    })
}