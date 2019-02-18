package ru.av3969.stickers.jetpack

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        subscribeUI(binding)
        return binding.root
    }

    private fun subscribeUI(binding: FragmentAlbumListBinding) {
        val context = context ?: return
        val factory = InjectorUtils.provideAlbumListViewModelFactory(context)
        viewModel = ViewModelProviders.of(this, factory).get(AlbumListViewModel::class.java)
    }

}
