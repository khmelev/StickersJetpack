package ru.av3969.stickers.jetpack.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stickers")
data class Sticker(
    var owner: Int,
    var number: String,
    var title: String,
    var section: String,
    var type: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}