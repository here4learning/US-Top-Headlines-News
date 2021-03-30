package org.test.news.details.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import org.test.news.BaseViewModel
import org.test.news.extension.convertToId
import org.test.news.details.entity.NewsArticles
import org.test.news.details.entity.NewsItem
import org.test.news.details.repository.INewsRepositoryImpl

class NewsViewModel(private val repository: INewsRepositoryImpl) : BaseViewModel() {

    private var articleList: MutableLiveData<List<NewsItem>> = MutableLiveData()
    val getArticleList: LiveData<List<NewsItem>>
        get() {
            return articleList
        }

    private var updateDataForLikes: MutableLiveData<Int> = MutableLiveData()
    val hasUpdateDataForLikes: LiveData<Int>
        get() {
            return updateDataForLikes
        }

    private var updateDataForComments: MutableLiveData<Int> = MutableLiveData()
    val hasUpdateDataForComments: LiveData<Int>
        get() {
            return updateDataForComments
        }

    fun fetchArticleList() {
        if(articleList.value?.isNotEmpty() == true){
            setArticlesInfo(articleList.value)
            return
        }
        showLoading.value = true
        viewModelScope.launch {
            runCatching {
                repository.fetchArticleList()
            }.onSuccess {
                processWithResult(articles = it)
            }.onFailure {
                processWithError(throwable = it)
            }
        }
    }

    private fun processWithResult(articles : NewsArticles){
        showLoading.value = false
        if(articles.status.contentEquals("ok")) {
            setArticlesInfo(list = articles.articleList)
        }else{
            processWithError(message = articles.message)
        }
    }

    private fun setArticlesInfo(list : List<NewsItem>?){
        list?.map {
            it.id = it.url?.convertToId
        }
        articleList.value = list
        fetchNumberOfLikesOfArticles()
        fetchNumberOfCommentsOfArticles()
    }

    private fun fetchNumberOfLikesOfArticles() {
        var index = 0
        addDisposable(Observable.fromIterable(articleList.value)
        .subscribeOn(Schedulers.io()).
            concatMap {article ->
                repository.fetchNoOfLikesOfArticle(article.id)
        }.observeOn(AndroidSchedulers.mainThread()).subscribe({
                articleList.value?.getOrNull(index)?.noOfLikes = it.likes
                updateDataForLikes.value = index++
        },{
            //do nothing
        },{
            //do nothing
        }))

    }

    private fun fetchNumberOfCommentsOfArticles() {
        var index = 0
        addDisposable(Observable.fromIterable(articleList.value)
            .subscribeOn(Schedulers.io()).concatMap {article ->
                repository.fetchNoOfCommentsOfArticle(article.id)
            }.observeOn(AndroidSchedulers.mainThread()).subscribe({
                articleList.value?.getOrNull(index)?.noOfComments = it.comments
                updateDataForComments.value = index++
            },{
                //do nothing
            },{
                //do nothing
            }))

    }



}