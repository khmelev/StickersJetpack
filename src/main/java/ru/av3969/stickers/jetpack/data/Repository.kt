package ru.av3969.stickers.jetpack.data

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class Repository private constructor(
    private val laststickerHelper: LaststickerHelper
) {

    suspend fun getCategories() =
        withContext(IO) {
            laststickerHelper.getCategoryList()
        }

    companion object {
        @Volatile private var instance: Repository? = null

        fun getInstance(laststickerHelper: LaststickerHelper) : Repository {
            return instance ?: synchronized(this) {
                return instance ?: Repository(laststickerHelper).also { instance = it }
            }
        }
    }
}