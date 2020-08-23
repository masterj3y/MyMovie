package io.github.masterj3y.mymovie.movie.search

import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import io.github.masterj3y.mymovie.R
import io.github.masterj3y.mymovie.core.extension.inflate
import io.github.masterj3y.mymovie.core.platform.DiffCallBack
import io.github.masterj3y.mymovie.databinding.ListSearchMovieItemBinding

class SearchMovieAdapter : RecyclerView.Adapter<SearchMovieAdapter.SearchMovieViewHolder>() {

    private val listDiffer = AsyncListDiffer<SearchMovieItem>(this, DiffCallBack<SearchMovieItem>())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        SearchMovieViewHolder(
            ListSearchMovieItemBinding.bind(parent.inflate(R.layout.list_search_movie_item))
        )

    override fun onBindViewHolder(holder: SearchMovieViewHolder, position: Int) =
        holder.render(listDiffer.currentList[position])

    override fun getItemCount() = listDiffer.currentList.size

    fun submitList(list: List<SearchMovieItem>) = listDiffer.submitList(list)

    class SearchMovieViewHolder(private val binding: ListSearchMovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun render(item: SearchMovieItem) = with(binding) {
            movie = item
        }
    }
}

@BindingAdapter("android:bindSearchMovieItems")
fun bindSearchMovieItems(view: RecyclerView, list: List<SearchMovieItem>?) {
    val adapter = view.adapter as? SearchMovieAdapter
    list?.let {
        adapter?.submitList(it)
    } ?: adapter?.submitList(emptyList())
}