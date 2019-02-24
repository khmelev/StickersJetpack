package ru.av3969.stickers.jetpack.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.av3969.stickers.jetpack.AlbumListFragmentDirections
import ru.av3969.stickers.jetpack.data.Album
import ru.av3969.stickers.jetpack.databinding.ListItemAlbumBinding

class AlbumAdapter : ListAdapter<Album, AlbumAdapter.ViewHolder>(AlbumDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ListItemAlbumBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val album = getItem(position)
        holder.bind(album, createClickListener(album.id))
    }

    private fun createClickListener(albumId: Int) =
            View.OnClickListener {
                val direction = AlbumListFragmentDirections.nextAction(albumId)
                it.findNavController().navigate(direction)
            }

    class ViewHolder(
        private val binding: ListItemAlbumBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Album, listener: View.OnClickListener) {
            binding.apply {
                album = item
                clickListener = listener
            }
        }
    }
}

private class AlbumDiffCallback : DiffUtil.ItemCallback<Album>() {
    override fun areItemsTheSame(oldItem: Album, newItem: Album): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Album, newItem: Album): Boolean {
        return oldItem == newItem
    }
}