package ru.av3969.stickers.jetpack.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CategoryDao {

    @Query("SELECT * FROM categories WHERE parent = 0")
    fun getRootCategories(): List<Category>

    @Query("SELECT * FROM categories WHERE parent = :parent")
    fun getCategoriesByParent(parent: Int): List<Category>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(categories: List<Category>)
}