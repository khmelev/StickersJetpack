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
import ru.av3969.stickers.jetpack.data.Album
import ru.av3969.stickers.jetpack.data.Repository
import ru.av3969.stickers.jetpack.data.Sticker

class AlbumDetailViewModel(
    private val repository: Repository,
    private val albumId: Int
) : ViewModel() {

    val album = MutableLiveData<Album>()

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
            album.postValue(
                repository.getAlbum(albumId)
            )
            _stickers.postValue(
                repository.getStickers(albumId)
            )
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
