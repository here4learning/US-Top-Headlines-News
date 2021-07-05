package org.test.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import org.test.news.entity.BaseResponse
import retrofit2.HttpException

open class BaseViewModel : ViewModel() {

    protected var showLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isShowLoading: LiveData<Boolean>
        get() {
            return showLoading
        }

    protected var errorMessage: MutableLiveData<String> = MutableLiveData()
    val getErrorMessage: LiveData<String>
        get() {
            return errorMessage
        }

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    protected fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    private fun clearDisposables() {
        compositeDisposable.clear()
    }

    override fun onCleared() {
        clearDisposables()
    }

    protected fun processWithError(throwable: Throwable? = null,message  :String? = null){
        showLoading.value=false
        errorMessage.value = message ?: extractErrorMessage(throwable)
    }

    private fun extractErrorMessage(throwable: Throwable?) : String?{
        return if(throwable is HttpException){
            AppException(throwable.code()).message
        }
        else{
            AppException(404).message
        }
    }


}
