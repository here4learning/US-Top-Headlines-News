package org.test.news.details.repository

import io.reactivex.Observable
import org.test.news.api.Api
import org.test.news.details.entity.CommentsOfNewsItem
import org.test.news.details.entity.LikeOfNewsItem
import org.test.news.details.entity.NewsArticles
import org.test.news.details.entity.NewsItem


class NewsRepositoryImpl(private val mainApi : Api, private val additionalApi : Api) : INewsRepositoryImpl {
    override suspend fun fetchArticleList(): NewsArticles {
        return mainApi.fetchArticleList()
    }

    override fun fetchNoOfLikesOfArticle(id: String?): Observable<LikeOfNewsItem> {
        return additionalApi.fetchNoLikesOfArticle(id)
    }

    override fun fetchNoOfCommentsOfArticle(id: String?): Observable<CommentsOfNewsItem> {
        return additionalApi.fetchNoCommentsOfArticle(id)
    }

}
