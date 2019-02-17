package ru.av3969.stickers.jetpack.adapters

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("isVisible")
fun bindIsGone(view: View, isVisible: Boolean) {
    view.visibility = if(isVisible) {
        View.VISIBLE
    } else {
        View.GONE
    }
}