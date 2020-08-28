package io.github.masterj3y.mymovie.movie.watchlist

import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import io.github.masterj3y.mymovie.R
import io.github.masterj3y.mymovie.core.extension.inflate
import io.github.masterj3y.mymovie.core.platform.DiffCallBack
import io.github.masterj3y.mymovie.databinding.ListWatchlistItemBinding
import io.github.masterj3y.mymovie.movie.details.MovieDetails

class WatchlistAdapter(
    private val onItemClicked: (item: MovieDetails, position: Int) -> Unit
) : RecyclerView.Adapter<WatchlistAdapter.WatchlistViewHolder>() {

    private val listDiffer = AsyncListDiffer<MovieDetails>(this, DiffCallBack<MovieDetails>())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        WatchlistViewHolder(ListWatchlistItemBinding.bind(parent.inflate(R.layout.list_watchlist_item)))

    override fun onBindViewHolder(holder: WatchlistViewHolder, position: Int) =
        holder.render(listDiffer.currentList[position])

    override fun getItemCount() = listDiffer.currentList.size

    fun submitList(list: List<MovieDetails>) = listDiffer.submitList(list)

    inner class WatchlistViewHolder(private val binding: ListWatchlistItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onItemClicked(listDiffer.currentList[adapterPosition], adapterPosition)
            }
        }

        fun render(item: MovieDetails) = with(binding) {
            movie = item
        }
    }
}

@BindingAdapter("android:bindWatchlistItems")
fun bindWatchlistItems(view: RecyclerView, list: List<MovieDetails>?) =
    list?.let { (view.adapter as? WatchlistAdapter)?.submitList(it) }