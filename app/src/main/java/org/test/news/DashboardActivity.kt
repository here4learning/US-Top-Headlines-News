package org.test.news


import android.os.Bundle
import org.test.news.details.fragment.NewsListFragment


class DashboardActivity : BaseActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        setActionBar(R.id.toolbar_main)

        replace(R.id.rlayout_container, NewsListFragment.newInstance())
    }
}