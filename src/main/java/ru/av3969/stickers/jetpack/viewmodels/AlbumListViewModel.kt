package ru.av3969.stickers.jetpack.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import ru.av3969.stickers.jetpack.data.Album
import ru.av3969.stickers.jetpack.data.Repository

class AlbumListViewModel(
    private val repository: Repository
) : ViewModel() {

    private val _albums = MutableLiveData<List<Album>>()
    val albums: LiveData<List<Album>>
        get() = _albums

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    private val viewModelJob = Job()

    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    fun loadAlbums(catName: String) {
        viewModelScope.launch(IO) {
            delay(1500) //Имитация загрузки для проверки прогресс бара
            repository.getAlbums(catName).let{
                _loading.postValue(false)
                _albums.postValue(it)
            }

        }
        viewModelScope.launch {
            delay(200)
            if(_albums.value == null) {
                _loading.value = true
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}

class AlbumListViewModelFactory(
    private val repository: Repository
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AlbumListViewModel(repository) as T
    }
}
