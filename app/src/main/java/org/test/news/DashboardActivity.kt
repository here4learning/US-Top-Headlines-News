package org.test.news


import android.os.Bundle
import org.test.news.details.fragment.NewsListFragment
import org.test.news.di.NewsContainer


class DashboardActivity : BaseActivity(){

    private lateinit var mNewsContainer : NewsContainer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        setActionBar(R.id.toolbar_main)

        mNewsContainer = NewsContainer()
        replace(R.id.rlayout_container, NewsListFragment.newInstance())
    }

    val newsContainer: NewsContainer
        get() {
            return mNewsContainer
        }
}