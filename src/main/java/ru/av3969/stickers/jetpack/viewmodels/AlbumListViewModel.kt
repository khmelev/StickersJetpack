package ru.av3969.stickers.jetpack.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.*
import ru.av3969.stickers.jetpack.data.Album
import ru.av3969.stickers.jetpack.data.Repository
import ru.av3969.stickers.jetpack.utilities.map

class AlbumListViewModel(
    private val repository: Repository
) : ViewModel() {

    private val _albums = MutableLiveData<List<Album>>()
    val albums: LiveData<List<Album>>
        get() = _albums

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    val nodata: LiveData<Boolean> = albums.map {
        it.isEmpty()
    }

    private val viewModelJob = Job()

    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    fun loadAlbums(catName: String) {
        viewModelScope.launch {
            delay(1000) //Имитация загрузки для проверки прогресс бара
            withContext(Dispatchers.IO) { repository.getAlbums(catName) }.let {
                _loading.value = false
                _albums.value = it
            }
        }
        viewModelScope.launch {
            delay(200) //Отложенная проверка, что бы не прям сразу показывать progressBar
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
