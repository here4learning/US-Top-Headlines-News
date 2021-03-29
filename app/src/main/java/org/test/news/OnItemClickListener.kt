package org.test.news

interface OnItemClickListener<T> {
    fun onItemClicked(obj: T)
}