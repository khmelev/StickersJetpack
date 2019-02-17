package ru.av3969.stickers.jetpack.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.av3969.stickers.jetpack.CategoryListFragmentDirections
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
            bind(category, onClickListener(category.catId, category.name))
        }
    }

    private fun onClickListener(catId: Int, catName: String): View.OnClickListener {
        return View.OnClickListener {
            val direction = CategoryListFragmentDirections.nextAction(catId, catName)
            it.findNavController().navigate(direction)
        }
    }

    class ViewHolder(
        private val binding: ListItemCategoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Category, listener: View.OnClickListener) {
            binding.apply {
                category = item
                clickListener = listener
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