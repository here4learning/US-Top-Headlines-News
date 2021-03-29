package org.test.news.extension

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import org.test.news.App

const val visible: Int = View.VISIBLE
const val invisible: Int = View.INVISIBLE
const val gone: Int = View.GONE
infix fun View.visibility(type: Int) {
    visibility = type
}

infix fun ViewGroup.inflateLayout(layoutId: Int): View {
    return LayoutInflater.from(context).inflate(layoutId, this, false)
}

const val forShortTime = Toast.LENGTH_SHORT
const val forLongTime = Toast.LENGTH_LONG
infix fun String?.showAsToast(duration: Int) {
    this?.run {
        Toast.makeText(App.getInstance(), this, duration).show()
    }
}

infix fun Int.showAsToast(duration: Int) {
    Toast.makeText(App.getInstance(), this, duration).show()
}

fun View?.enableComponent(isEnable: Boolean? = false) {
    this?.run {
        if (isEnable == true) {
            this.alpha = 1.0f
            this.isEnabled = true
        } else {
            this.alpha = 0.5f
            this.isEnabled = false
        }
    }
}

