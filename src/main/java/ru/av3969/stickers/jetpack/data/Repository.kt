package ru.av3969.stickers.jetpack.data

class Repository private constructor(
    private val laststickerHelper: LaststickerHelper,
    private val categoryDao: CategoryDao
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

    companion object {
        @Volatile private var instance: Repository? = null

        fun getInstance(laststickerHelper: LaststickerHelper, categoryDao: CategoryDao) : Repository {
            return instance ?: synchronized(this) {
                return instance ?: Repository(laststickerHelper, categoryDao).also { instance = it }
            }
        }
    }
}