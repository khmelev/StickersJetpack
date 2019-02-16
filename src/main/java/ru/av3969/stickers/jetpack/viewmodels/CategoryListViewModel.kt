package ru.av3969.stickers.jetpack.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.av3969.stickers.jetpack.data.Category
import ru.av3969.stickers.jetpack.data.Repository

class CategoryListViewModel(
    private val repository: Repository
) : ViewModel() {

    var categories = MutableLiveData<List<Category>>()

    private val viewModelJob = Job()

    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    init {
        viewModelScope.launch {
            categories.value = repository.getCategories()
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
