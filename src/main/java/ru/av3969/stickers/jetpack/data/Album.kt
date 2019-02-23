package ru.av3969.stickers.jetpack.data

import android.content.Context
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.av3969.stickers.jetpack.R

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

    fun albumTypeAndSize(context: Context): String =
        if(type == Album.STICKER_TYPE) {
            context.getString(R.string.number_of_stickers, size)
        } else {
            context.getString(R.string.number_of_cards, size)
        }

    fun albumYearOfCreation(context: Context): String =
        context.getString(R.string.year_of_creation, year)

    companion object {
        const val STICKER_TYPE = 1
        const val CARD_TYPE = 2
    }
}
