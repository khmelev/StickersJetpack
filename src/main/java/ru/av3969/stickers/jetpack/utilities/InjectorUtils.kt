package ru.av3969.stickers.jetpack.utilities

import android.content.Context
import ru.av3969.stickers.jetpack.data.LaststickerHelper
import ru.av3969.stickers.jetpack.data.Repository
import ru.av3969.stickers.jetpack.viewmodels.CategoryListViewModelFactory

object InjectorUtils {

    private fun getRepository() : Repository {
        val laststickerHelper = getLaststickerHelper()
        return Repository.getInstance(laststickerHelper)
    }

    private fun getLaststickerHelper(): LaststickerHelper {
        return LaststickerHelper.getInstance()
    }

    fun provideCategoryListViewModelFactory(context: Context): CategoryListViewModelFactory {
        val repository = getRepository()
        return CategoryListViewModelFactory(repository)
    }
}