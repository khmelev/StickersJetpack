package ru.av3969.stickers.jetpack.data

data class Album(
    val id: Int,
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
}
