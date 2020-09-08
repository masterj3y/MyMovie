package io.github.masterj3y.mymovie.dialog.action

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.StringRes
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import io.github.masterj3y.mymovie.R
import io.github.masterj3y.mymovie.core.extension.visible
import io.github.masterj3y.mymovie.databinding.DialogActionBottomSheetBinding
import io.github.masterj3y.mymovie.dialog.BaseBottomSheetDialog

@SuppressLint("InflateParams")
class ActionBottomSheetDialog(context: Context) : BaseBottomSheetDialog(context) {

    private val binding: DialogActionBottomSheetBinding by lazy {
        DataBindingUtil.inflate<DialogActionBottomSheetBinding>(
            LayoutInflater.from(context), R.layout.dialog_action_bottom_sheet, null, false
        )
    }

    override val dialogView: View by lazy { binding.root }

    var items: List<ActionBottomSheetDialogItem> = listOf()
        set(value) {
            field = value
            binding.recyclerView.adapter = ActionBottomSheetDialogAdapter(field)
        }

    fun title(text: String) = with(binding.title) {
        visible()
        this.text = text
    }

    fun title(@StringRes text: Int) = with(binding.title) {
        visible()
        this.setText(text)
    }
}

inline fun Context.actionBottomSheetDialog(func: ActionBottomSheetDialog.() -> Unit) =
    ActionBottomSheetDialog(this).apply { func() }

inline fun Fragment.actionBottomSheetDialog(func: ActionBottomSheetDialog.() -> Unit) =
    ActionBottomSheetDialog(requireContext()).apply { func() }