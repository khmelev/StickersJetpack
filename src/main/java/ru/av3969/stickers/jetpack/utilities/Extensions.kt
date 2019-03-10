package ru.av3969.stickers.jetpack.utilities

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations

fun <X, Y> LiveData<X>.map(block: (X) -> Y): LiveData<Y> {
    return Transformations.map(this, block)
}

fun <X, Y> LiveData<X>.switchMap(body: (X) -> LiveData<Y>): LiveData<Y> {
    return Transformations.switchMap(this, body)
}