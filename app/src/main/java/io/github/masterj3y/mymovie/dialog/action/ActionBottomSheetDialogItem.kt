package io.github.masterj3y.mymovie.dialog.action

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class ActionBottomSheetDialogItem(
    @DrawableRes
    val icon: Int? = null,
    @StringRes
    val text: Int,
    val onClick: () -> Unit = {}
)
