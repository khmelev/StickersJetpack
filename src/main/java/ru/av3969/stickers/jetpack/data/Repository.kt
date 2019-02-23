package ru.av3969.stickers.jetpack.data

class Repository private constructor(
    private val laststickerHelper: LaststickerHelper,
    private val categoryDao: CategoryDao,
    private val albumDao: AlbumDao,
    private val stickerDao: StickerDao
) {

    fun getRootCategories(): List<Category> {
        var categoryList = categoryDao.getRootCategories()
        if (categoryList.isEmpty()) {
            categoryList = laststickerHelper.getCategoryList()
                .also { categoryDao.insertAll(it) }.filter { it.parent == 0 }
        }
        return categoryList
    }

    fun getCategories(parentId: Int) = categoryDao.getCategoriesByParent(parentId)

    fun getAlbums(catName: String): List<Album> {
        var albumsList = albumDao.getAlbumsByCatName(catName)
        if (albumsList.isEmpty()) {
            albumsList = laststickerHelper.getCollectionList(catName)
                .also { albumDao.insertAll(it) }
        }
        return albumsList
    }

    fun getAlbum(albumId: Int) = albumDao.getAlbumById(albumId)

    fun getStickers(owner: Int): List<Sticker> {
        var stickers = stickerDao.getStickersByOwner(owner)
        if(stickers.isEmpty()) {
            stickers = laststickerHelper.getStickersList(albumDao.getAlbumById(owner))
                .also { stickerDao.insertAll(it) }
        }
        return stickers
    }

    companion object {
        @Volatile private var instance: Repository? = null

        fun getInstance(
            laststickerHelper: LaststickerHelper,
            categoryDao: CategoryDao,
            albumDao: AlbumDao,
            stickerDao: StickerDao
        ) : Repository {
            return instance ?: synchronized(this) {
                return instance ?: Repository(
                    laststickerHelper, categoryDao, albumDao, stickerDao
                ).also { instance = it }
            }
        }
    }
}