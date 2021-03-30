package org.test.news.di

import org.test.news.App
import org.test.news.details.repository.NewsRepositoryImpl

class NewsContainer {
    val newsViewModelFactory = NewsViewModelFactory(
        NewsRepositoryImpl(App.getMainApi(), App.getAdditionalApi())
    )

}
