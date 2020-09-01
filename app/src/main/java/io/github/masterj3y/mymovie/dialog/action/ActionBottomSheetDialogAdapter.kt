package io.github.masterj3y.mymovie.dialog.action

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.github.masterj3y.mymovie.R
import io.github.masterj3y.mymovie.core.extension.inflate
import io.github.masterj3y.mymovie.databinding.DialogActionBottomSheetItemBinding

class ActionBottomSheetDialogAdapter(private val list: List<ActionBottomSheetDialogItem>) :
    RecyclerView.Adapter<ActionBottomSheetDialogAdapter.ActionBottomSheetDialogViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ActionBottomSheetDialogViewHolder =
        ActionBottomSheetDialogViewHolder(
            DialogActionBottomSheetItemBinding.bind(
                parent.inflate(
                    R.layout.dialog_action_bottom_sheet_item
                )
            )
        )

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ActionBottomSheetDialogViewHolder, position: Int) =
        holder.bind(list[position])

    inner class ActionBottomSheetDialogViewHolder(private val binding: DialogActionBottomSheetItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener { list[adapterPosition].onClick() }
        }

        fun bind(item: ActionBottomSheetDialogItem) {
            binding.item = item
        }
    }
}
