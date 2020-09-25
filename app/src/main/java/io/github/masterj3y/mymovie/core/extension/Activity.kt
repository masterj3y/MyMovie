package io.github.masterj3y.mymovie.core.extension

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager

fun Activity.hideSoftInput() {
    (getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager)
        .hideSoftInputFromWindow((currentFocus ?: View(this)).windowToken, 0)
}