package org.test.news.api

import io.reactivex.Observable
import org.test.news.BuildConfig
import org.test.news.details.entity.CommentsOfNewsItem
import org.test.news.details.entity.LikeOfNewsItem
import org.test.news.details.entity.NewsArticles
import org.test.news.details.entity.NewsItem
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface Api {
    @GET("top-headlines?country=us&category=business")
    suspend fun fetchArticleList(@Query("apiKey") apiKey: String = BuildConfig.API_KEY): NewsArticles

    @GET("likes/{articleId}")
    fun fetchNoLikesOfArticle(@Path(value = "articleId", encoded = true) articleId : String? ): Observable<LikeOfNewsItem>

    @GET("comments/{articleId}")
    fun fetchNoCommentsOfArticle(@Path(value = "articleId", encoded = true) articleId : String? ): Observable<CommentsOfNewsItem>
}
