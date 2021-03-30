package org.test.news.details.fragment


import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import kotlinx.android.synthetic.main.fragment_news_details.view.*
import org.test.news.BaseFragment
import org.test.news.R
import org.test.news.extension.gone
import org.test.news.extension.visible

class NewsDetailsFragment : BaseFragment() {

    private lateinit var mURL : String

    companion object {
        fun newInstance(url : String) = NewsDetailsFragment().apply{
            mURL = url
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        setTitleWithBackButton(R.string.app_name)
        val view = inflater.inflate(R.layout.fragment_news_details, container, false)
        initWebComponents(view)
        return view
    }

    @SuppressLint( "SetJavaScriptEnabled")
    private fun initWebComponents(view : View) {
        with(view.web_view_news) {
            settings.run {
                webViewClient = MyWebViewClient()
                javaScriptEnabled = true
                setSupportZoom(true)
                useWideViewPort = false
                loadWithOverviewMode = false
                builtInZoomControls = true
                cacheMode = WebSettings.LOAD_NO_CACHE
                domStorageEnabled = true
                clearHistory()
                clearCache(true)
            }
            loadUrl(mURL);
        }
    }

    private inner class MyWebViewClient : WebViewClient() {

        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            view.loadUrl(url)
            return false
        }

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            setLoadingUi(true)
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            setLoadingUi(false)
        }

        @SuppressWarnings("deprecation")
        @Override
        override fun onReceivedError(view: WebView, errorCode: Int, description: String?, failingUrl: String?) {
            setLoadingUi(false)
        }

        @TargetApi(android.os.Build.VERSION_CODES.M)
        override fun onReceivedError(view: WebView, req: WebResourceRequest?, rerr: WebResourceError?) {
            setLoadingUi(false)
        }
    }

    private fun setLoadingUi(isVisible : Boolean){
        view?.progress_bar?.visibility = if(isVisible){
            visible
        }else{
            gone
        }
    }


}