package ru.av3969.stickers.jetpack

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import ru.av3969.stickers.jetpack.adapters.StickerAdapter
import ru.av3969.stickers.jetpack.databinding.FragmentAlbumDetailBinding
import ru.av3969.stickers.jetpack.databinding.FragmentAlbumListBinding
import ru.av3969.stickers.jetpack.databinding.IncludeAlbumDetailsBinding
import ru.av3969.stickers.jetpack.utilities.InjectorUtils
import ru.av3969.stickers.jetpack.viewmodels.AlbumDetailViewModel


class AlbumDetailFragment : Fragment() {

    private lateinit var detailViewModel: AlbumDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentAlbumDetailBinding.inflate(inflater, container, false)

        val adapter = StickerAdapter()
        binding.stickerList.adapter = adapter

        subscribeUI(binding, adapter)

        return binding.root
    }
    private fun subscribeUI(binding: FragmentAlbumDetailBinding, adapter: StickerAdapter) {
        val context = context ?: return

        val albumId = arguments?.let { AlbumDetailFragmentArgs.fromBundle(it).albumId } ?: 0

        val factory = InjectorUtils.provideAlbumDetailViewModelFactory(context, albumId)
        detailViewModel = ViewModelProviders.of(this, factory).get(AlbumDetailViewModel::class.java)

        detailViewModel.album.observe(this, Observer { album ->
            with(album) {
                binding.album = this
                binding.albumTypeSize = albumTypeAndSize(context)
                binding.albumYear = albumYearOfCreation(context)
            }

        })

        detailViewModel.stickers.observe(this, Observer {stickers ->
            adapter.submitList(stickers)
        })
    }
}
