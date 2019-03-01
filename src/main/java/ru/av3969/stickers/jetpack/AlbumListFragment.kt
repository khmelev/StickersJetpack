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

    private lateinit var albumListViewModel: AlbumListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val factory = InjectorUtils.provideAlbumListViewModelFactory(requireActivity())
        albumListViewModel = ViewModelProviders.of(this, factory).get(AlbumListViewModel::class.java)

        val adapter = AlbumAdapter()

        val binding = FragmentAlbumListBinding.inflate(inflater, container, false)
        binding.apply {
            albumList.adapter = adapter
            lifecycleOwner = viewLifecycleOwner
            viewmodel = albumListViewModel
        }

        subscribeUI(adapter)
        return binding.root
    }

    private fun subscribeUI(adapter: AlbumAdapter) {

        albumListViewModel.albums.observe(viewLifecycleOwner, Observer {
            if (!it.isNullOrEmpty()) {
                adapter.submitList(it)
            }
        })

        val catName = arguments?.let { AlbumListFragmentArgs.fromBundle(it).catName } ?: ""
        albumListViewModel.loadAlbums(catName)
    }

}
