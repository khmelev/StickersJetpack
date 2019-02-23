package ru.av3969.stickers.jetpack.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface StickerDao {

    @Query("SELECT * FROM stickers WHERE owner = :owner ORDER BY id")
    fun getStickersByOwner(owner: Int): List<Sticker>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(stickers: List<Sticker>)
}