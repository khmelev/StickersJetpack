package ru.av3969.stickers.jetpack.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.av3969.stickers.jetpack.data.Category
import ru.av3969.stickers.jetpack.databinding.ListItemCategoryBinding

class CategoryAdapter : ListAdapter<Category, CategoryAdapter.ViewHolder>(CategoryDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ListItemCategoryBinding.inflate(
            LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = getItem(position)
        holder.apply {
            bind(category)
        }
    }

    class ViewHolder(
        private val binding: ListItemCategoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Category) {
            binding.apply {
                category = item
            }
        }
    }
}

private class CategoryDiffCallback : DiffUtil.ItemCallback<Category>() {
    override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem.catId == newItem.catId
    }

    override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem == newItem
    }
}