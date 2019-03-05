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

        val adapter = CategoryAdapter()
        binding.categoryList.adapter = adapter

        subscribeUI(adapter, binding)

        return binding.root
    }

    private fun subscribeUI(adapter: CategoryAdapter, binding: FragmentCategoryListBinding) {
        val context = context ?: return
        val factory = InjectorUtils.provideCategoryListViewModelFactory(context)
        viewModel = ViewModelProviders.of(this, factory).get(CategoryListViewModel::class.java)

        binding.viewmodel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.categories.observe(this, Observer {
            if (!it.isNullOrEmpty()) {
                adapter.submitList(it)
            }
        })

        val parentId = arguments?.let { CategoryListFragmentArgs.fromBundle(it).catId } ?: 0
        viewModel.loadCategories(parentId)
    }

}
