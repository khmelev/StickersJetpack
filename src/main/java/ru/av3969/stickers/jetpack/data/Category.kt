package ru.av3969.stickers.jetpack.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class Category(
    @PrimaryKey @ColumnInfo(name = "id") val catId: Int,
    val name: String,
    val title: String,
    val parent: Int = 0
) {
    override fun toString(): String {
        return title
    }
}