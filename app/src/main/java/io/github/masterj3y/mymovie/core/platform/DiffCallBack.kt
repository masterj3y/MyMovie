package io.github.masterj3y.mymovie.core.platform

import androidx.recyclerview.widget.DiffUtil

class DiffCallBack<T> : DiffUtil.ItemCallback<T>() where T : BaseEntity<*> {

    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem.equals(newItem)
    }
}