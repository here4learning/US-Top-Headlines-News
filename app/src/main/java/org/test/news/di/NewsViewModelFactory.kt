package org.test.news.di

import org.test.news.details.repository.INewsRepositoryImpl
import org.test.news.details.viewmodel.NewsViewModel


class NewsViewModelFactory(userRepository: INewsRepositoryImpl) :
    Factory<NewsViewModel> {
    private var mNewsViewModel: NewsViewModel = NewsViewModel(
        userRepository
    )
    override fun create(): NewsViewModel {
        return mNewsViewModel
    }
}