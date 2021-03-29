package org.test.news.details.entity

import com.google.gson.annotations.SerializedName
import org.test.news.entity.BaseResponse

data class NewsArticles(
    @SerializedName("articles")
    var articleList : List<NewsItem>? = null,var message : String? = null) : BaseResponse()
