package ru.av3969.stickers.jetpack

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.av3969.stickers.jetpack.databinding.FragmentAlbumListBinding
import ru.av3969.stickers.jetpack.viewmodels.AlbumDetailViewModel


class AlbumDetailFragment : Fragment() {

    private lateinit var detailViewModel: AlbumDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentAlbumListBinding.inflate(inflater, container, false)
        binding.noData = true

        detailViewModel = ViewModelProviders.of(this).get(AlbumDetailViewModel::class.java)

        return binding.root
    }

}
