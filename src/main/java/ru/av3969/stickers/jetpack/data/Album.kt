package ru.av3969.stickers.jetpack.data

data class Album(
    val id: Int,
    val name: String,
    val title: String,
    val categoryName: String
) {
    override fun toString(): String {
        return title
    }
}
