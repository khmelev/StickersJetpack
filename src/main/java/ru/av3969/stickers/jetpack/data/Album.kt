package ru.av3969.stickers.jetpack.data

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "albums")
data class Album(
    @PrimaryKey val id: Int,
    val name: String,
    val title: String,
    val categoryName: String,
    val year: Int,
    val type: Int,
    val size: Int,
    val description: String
) {
    override fun toString(): String {
        return title
    }

    @Ignore
    val coverURL = "$coverBaseURL$id$imageExt"
    @Ignore
    val coverTinyURL = "$coverBaseURL$id$tinyVersionSuffix$imageExt"

    companion object {
        const val STICKER_TYPE = 1
        const val CARD_TYPE = 2

        private const val coverBaseURL = "https://www.laststicker.ru/i/album/"
        private const val tinyVersionSuffix = "_s"
        private const val imageExt = ".jpg"
    }
}
