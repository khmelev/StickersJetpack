package ru.av3969.stickers.jetpack.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AlbumDao {

    @Query("SELECT * FROM albums WHERE categoryName = :catName ORDER BY id DESC")
    fun getAlbumsByCatName(catName: String): List<Album>

    @Query("SELECT * FROM albums WHERE id = :albumId")
    fun getAlbumById(albumId: Int): Album

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(albums: List<Album>)
}