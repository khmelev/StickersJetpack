package ru.av3969.stickers.jetpack.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.av3969.stickers.jetpack.data.Sticker
import ru.av3969.stickers.jetpack.databinding.ListItemStickerBinding

class StickerAdapter : ListAdapter<Sticker, StickerAdapter.ViewHolder>(StickerDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ListItemStickerBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val sticker = getItem(position)
        holder.bind(sticker)
    }

    class ViewHolder(
        private val binding: ListItemStickerBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(sticker: Sticker) {
            binding.sticker = sticker
        }
    }
}

private class StickerDiffCallback : DiffUtil.ItemCallback<Sticker>() {
    override fun areItemsTheSame(oldItem: Sticker, newItem: Sticker): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Sticker, newItem: Sticker): Boolean {
        return oldItem == newItem
    }
}