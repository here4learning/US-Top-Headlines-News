package org.test.news.extension

val String?.convertToId: String?
    get() {
        return this?.substringAfter("https://")?.replace("/", "-")
    }