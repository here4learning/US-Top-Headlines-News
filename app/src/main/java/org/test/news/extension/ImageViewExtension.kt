package org.test.news.extension

import android.graphics.Bitmap
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import org.test.news.App

fun ImageView?.loadImage(url: String?,drawableRes : Int) {
    this?.run {
        Glide.with(App.getInstance())
                .load(url)
                .placeholder(drawableRes)
                .error(drawableRes)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(this)
    }
}
