package ru.av3969.stickers.jetpack

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import ru.av3969.stickers.jetpack.adapters.CategoryAdapter
import ru.av3969.stickers.jetpack.databinding.FragmentCategoryListBinding
import ru.av3969.stickers.jetpack.utilities.InjectorUtils
import ru.av3969.stickers.jetpack.viewmodels.CategoryListViewModel


class CategoryListFragment : Fragment() {

    private lateinit var viewModel: CategoryListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentCategoryListBinding.inflate(inflater, container, false)
        val context = context ?: return binding.root

        val factory = InjectorUtils.provideCategoryListViewModelFactory(context)
        viewModel = ViewModelProviders.of(this, factory).get(CategoryListViewModel::class.java)

        val adapter = CategoryAdapter()
        binding.categoryList.adapter = adapter
        subscribeUI(adapter)
        return binding.root
    }

    private fun subscribeUI(adapter: CategoryAdapter) {
        viewModel.categories.observe(this, Observer {
            if (it != null && it.isNotEmpty()) {
                adapter.submitList(it)
            }
        })
    }
}
