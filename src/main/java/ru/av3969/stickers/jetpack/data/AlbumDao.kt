package ru.av3969.stickers.jetpack.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AlbumDao {

    @Query("SELECT * FROM albums WHERE categoryName = :catName")
    fun getAlbumsByCatName(catName: String): List<Album>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(albums: List<Album>)
}