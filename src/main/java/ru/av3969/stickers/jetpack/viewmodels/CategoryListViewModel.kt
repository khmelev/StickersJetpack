package ru.av3969.stickers.jetpack.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.av3969.stickers.jetpack.data.Category
import ru.av3969.stickers.jetpack.data.Repository
import ru.av3969.stickers.jetpack.utilities.map

class CategoryListViewModel(
    private val repository: Repository
) : ViewModel() {

    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>>
        get() = _categories

    val nodata: LiveData<Boolean> = categories.map {
        it.isEmpty()
    }

    private val viewModelJob = Job()

    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    fun loadCategories(parentId: Int) {
        viewModelScope.launch(IO) {
            if(parentId == 0) {
                repository.getRootCategories()
            } else {
                repository.getCategories(parentId)
            }.let {
                _categories.postValue(it)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}

class CategoryListViewModelFactory(
    private val repository: Repository
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CategoryListViewModel(repository) as T
    }
}
