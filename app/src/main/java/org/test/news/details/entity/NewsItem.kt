package org.test.news.details.entity

import com.google.gson.annotations.SerializedName

data class NewsItem(var id : String? = null,var author : String? = null, var title  : String? = null, var url : String? = null,var description  : String? = null, var urlToImage: String? = null,
@Transient var noOfLikes: Int = 0, @Transient var noOfComments : Int = 0)