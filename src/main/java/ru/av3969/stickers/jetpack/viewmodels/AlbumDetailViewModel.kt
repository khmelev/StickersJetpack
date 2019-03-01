package ru.av3969.stickers.jetpack.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import ru.av3969.stickers.jetpack.data.Album
import ru.av3969.stickers.jetpack.data.Repository
import ru.av3969.stickers.jetpack.data.Sticker

class AlbumDetailViewModel(
    private val repository: Repository,
    private val albumId: Int
) : ViewModel() {

    private val _album = MutableLiveData<Album>()
    val album: LiveData<Album>
        get() = _album

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    private val _nodata = MutableLiveData<Boolean>()
    val nodata: LiveData<Boolean>
        get() = _nodata

    private var _stickers = MutableLiveData<List<Sticker>>()
    val stickers: LiveData<List<Sticker>>
        get() = _stickers

    private val viewModelJob = Job()

    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    init {
        loadStickers()
    }

    fun loadStickers() {
        viewModelScope.launch(IO) {
            _album.postValue(
                repository.getAlbum(albumId)
            )
            delay(1000) //Имитация загрузки для проверки прогресс бара
            repository.getStickers(albumId).let {
                _loading.postValue(false)
                _stickers.postValue(it)
                _nodata.postValue(it.isEmpty())
            }
        }
        viewModelScope.launch {
            delay(200) //Отложенная проверка, что бы не прям сразу показывать progressBar
            if(_stickers.value == null) {
                _loading.value = true
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}

class AlbumDetailViewModelFactory(
    private val repository: Repository,
    private val albumId: Int
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AlbumDetailViewModel(repository, albumId) as T
    }
}
