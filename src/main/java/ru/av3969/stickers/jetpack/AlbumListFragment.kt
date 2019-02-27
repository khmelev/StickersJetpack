package ru.av3969.stickers.jetpack

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import ru.av3969.stickers.jetpack.adapters.AlbumAdapter
import ru.av3969.stickers.jetpack.databinding.FragmentAlbumListBinding
import ru.av3969.stickers.jetpack.utilities.InjectorUtils
import ru.av3969.stickers.jetpack.viewmodels.AlbumListViewModel

class AlbumListFragment : Fragment() {

    private lateinit var viewModel: AlbumListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentAlbumListBinding.inflate(inflater, container, false)

        val adapter = AlbumAdapter()
        binding.albumList.adapter = adapter

        subscribeUI(adapter, binding)
        return binding.root
    }

    private fun subscribeUI(adapter: AlbumAdapter, binding: FragmentAlbumListBinding) {
        val context = context ?: return
        val factory = InjectorUtils.provideAlbumListViewModelFactory(context)
        viewModel = ViewModelProviders.of(this, factory).get(AlbumListViewModel::class.java)
        viewModel.albums.observe(this, Observer {
            if (it != null && it.isNotEmpty()) {
                adapter.submitList(it)
            } else {
                binding.noData = true
            }
        })
        viewModel.loading.observe(this, Observer {
            binding.loading = it
        })
        val catName = arguments?.let { AlbumListFragmentArgs.fromBundle(it).catName } ?: ""
        viewModel.loadAlbums(catName)
    }

}
