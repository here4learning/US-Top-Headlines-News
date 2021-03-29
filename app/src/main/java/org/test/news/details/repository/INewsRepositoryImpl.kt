package org.test.news.details.repository


import io.reactivex.Observable
import org.test.news.details.entity.CommentsOfNewsItem
import org.test.news.details.entity.LikeOfNewsItem
import org.test.news.details.entity.NewsArticles
import org.test.news.details.entity.NewsItem

interface INewsRepositoryImpl {
    suspend fun fetchArticleList() : NewsArticles
    fun fetchNoOfLikesOfArticle(id : String?) : Observable<LikeOfNewsItem>
    fun fetchNoOfCommentsOfArticle(id : String?) : Observable<CommentsOfNewsItem>
}