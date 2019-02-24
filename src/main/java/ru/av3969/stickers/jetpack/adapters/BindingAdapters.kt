package ru.av3969.stickers.jetpack.adapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import ru.av3969.stickers.jetpack.R
import ru.av3969.stickers.jetpack.data.Album

@BindingAdapter("android:visibility")
fun bindIsGone(view: View, visibility: Boolean) {
    view.visibility = if(visibility) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

@BindingAdapter("app:album_year")
fun bindAlbumYear(textView: TextView, albumYear: Int) {
    textView.text = textView.context.getString(R.string.year_of_creation, albumYear)
}

@BindingAdapter("app:album_type", "app:album_size",requireAll = true)
fun bindAlbumSize(textView: TextView, albumType: Int, albumSize: Int) {
    textView.text = textView.context.getString(
        if (albumType == Album.STICKER_TYPE) R.string.number_of_stickers
        else R.string.number_of_cards, albumSize
    )
}

@BindingAdapter("app:image_from_url")
fun bindImageFromURL(view: ImageView, imageUrl: String?) {
    if(!imageUrl.isNullOrEmpty()) {
        Glide.with(view)
            .load(imageUrl)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(view)
    }
}