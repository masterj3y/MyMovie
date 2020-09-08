package io.github.masterj3y.mymovie.movie.watchlist

import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import io.github.masterj3y.mymovie.R
import io.github.masterj3y.mymovie.core.extension.inflate
import io.github.masterj3y.mymovie.core.platform.DiffCallBack
import io.github.masterj3y.mymovie.databinding.ListWatchlistItemBinding
import io.github.masterj3y.mymovie.dialog.action.ActionBottomSheetDialog
import io.github.masterj3y.mymovie.dialog.action.ActionBottomSheetDialogItem
import io.github.masterj3y.mymovie.movie.details.MovieDetails

class WatchlistAdapter(
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<WatchlistAdapter.WatchlistViewHolder>() {

    private val listDiffer = AsyncListDiffer(this, DiffCallBack<MovieDetails>())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        WatchlistViewHolder(ListWatchlistItemBinding.bind(parent.inflate(R.layout.list_watchlist_item)))

    override fun onBindViewHolder(holder: WatchlistViewHolder, position: Int) =
        holder.render(listDiffer.currentList[position])

    override fun getItemCount() = listDiffer.currentList.size

    fun submitList(list: List<MovieDetails>) = listDiffer.submitList(list)

    inner class WatchlistViewHolder(private val binding: ListWatchlistItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val moreActionsDialog: ActionBottomSheetDialog by lazy {
            ActionBottomSheetDialog(binding.root.context).apply {
                title(R.string.watchlist_item_actions)
                items = moreActionsItems(this)
            }
        }

        init {
            binding.apply {
                root.setOnClickListener {
                    listener.onItemClicked(currentItem(), adapterPosition)
                }
                more.setOnClickListener {
                    moreActionsDialog.show()
                }
            }
        }

        fun render(item: MovieDetails) = with(binding) {
            movie = item
        }

        private fun moreActionsItems(dialog: ActionBottomSheetDialog) = listOf(
            ActionBottomSheetDialogItem(text = R.string.watch_list_status_dialog_title) {
                dialog.dismiss()
                listener.onChangeStatusActionClicked(currentItem(), adapterPosition)
            },
            ActionBottomSheetDialogItem(text = R.string.watch_list_delete_item) {
                dialog.dismiss()
                listener.onDeleteActionClicked(currentItem(), adapterPosition)
            }
        )

        private fun currentItem() = listDiffer.currentList[adapterPosition]
    }

    interface OnItemClickListener {

        fun onItemClicked(movieDetails: MovieDetails, position: Int)
        fun onChangeStatusActionClicked(movieDetails: MovieDetails, position: Int)
        fun onDeleteActionClicked(movieDetails: MovieDetails, position: Int)
    }
}

@BindingAdapter("android:bindWatchlistItems")
fun bindWatchlistItems(view: RecyclerView, list: List<MovieDetails>?) =
    list?.let { (view.adapter as? WatchlistAdapter)?.submitList(it) }