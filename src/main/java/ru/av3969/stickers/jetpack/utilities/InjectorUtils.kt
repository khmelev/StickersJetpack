package ru.av3969.stickers.jetpack.utilities

import android.content.Context
import ru.av3969.stickers.jetpack.data.AppDatabase
import ru.av3969.stickers.jetpack.data.LaststickerHelper
import ru.av3969.stickers.jetpack.data.Repository
import ru.av3969.stickers.jetpack.viewmodels.AlbumDetailViewModelFactory
import ru.av3969.stickers.jetpack.viewmodels.AlbumListViewModelFactory
import ru.av3969.stickers.jetpack.viewmodels.CategoryListViewModelFactory

object InjectorUtils {

    private fun getRepository(context: Context) : Repository {
        val laststickerHelper = getLaststickerHelper()
        val categoryDao = AppDatabase.getInstance(context.applicationContext).categoryDao()
        val albumDao = AppDatabase.getInstance(context.applicationContext).albumDao()
        val stickerDao = AppDatabase.getInstance(context.applicationContext).stickerDao()
        return Repository.getInstance(laststickerHelper, categoryDao, albumDao, stickerDao)
    }

    private fun getLaststickerHelper(): LaststickerHelper {
        return LaststickerHelper.getInstance()
    }

    fun provideCategoryListViewModelFactory(context: Context): CategoryListViewModelFactory {
        val repository = getRepository(context)
        return CategoryListViewModelFactory(repository)
    }

    fun provideAlbumListViewModelFactory(context: Context): AlbumListViewModelFactory {
        val repository = getRepository(context)
        return AlbumListViewModelFactory(repository)
    }

    fun provideAlbumDetailViewModelFactory(context: Context, albumId: Int): AlbumDetailViewModelFactory {
        return AlbumDetailViewModelFactory(getRepository(context), albumId)
    }
}