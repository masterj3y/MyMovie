package io.github.masterj3y.mymovie.dialog

import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import com.google.android.material.bottomsheet.BottomSheetDialog
import io.github.masterj3y.mymovie.R

abstract class BaseBottomSheetDialog(context: Context) :
    BottomSheetDialog(context, R.style.TransparentBottomSheetDialog) {

    abstract val dialogView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window?.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        setContentView(dialogView)
        window?.decorView?.viewTreeObserver?.addOnGlobalLayoutListener { adjustDialog() }
    }

    private fun adjustDialog() {
        val lp: WindowManager.LayoutParams? = window?.attributes
        val dm: DisplayMetrics = context.resources.displayMetrics
        lp?.width = dm.widthPixels
        lp?.height = dm.heightPixels
        window?.attributes = lp
    }
}
