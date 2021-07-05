package org.test.news.details.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import org.test.news.*
import org.test.news.details.adapter.NewsAdapter
import org.test.news.details.entity.NewsItem
import org.test.news.details.viewmodel.NewsViewModel
import kotlinx.android.synthetic.main.fragment_top_news_list.view.*
import org.test.news.extension.*

class NewsListFragment : BaseFragment(), OnItemClickListener<NewsItem> {

    private lateinit var mViewModel: NewsViewModel

    private val mNewsAdapter = NewsAdapter()

    companion object {
        fun newInstance() = NewsListFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        setTitle(R.string.app_name)
        val view = inflater.inflate(R.layout.fragment_top_news_list, container, false)
        initUIComponent(view)
        return view
    }

    private fun initUIComponent(view: View) {
        with(view.recycler_view_news) {
            layoutManager = LinearLayoutManager(context)
            adapter = mNewsAdapter
        }
        mNewsAdapter.setStableId()
        mNewsAdapter.setOnItemClickListener(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel = (activity as DashboardActivity).newsContainer.newsViewModelFactory.create()
        with(mViewModel) {
            handleObserverForLoading(view, this)
            handleObserverForNewsList(this)
            handleObserverForUpdatedList(this)
            handleObserverForErrorView(view,this)
        }
        retryFetch()
    }

    override fun retryFetch(){
        mViewModel.fetchArticleList()
    }

    private fun handleObserverForLoading(view: View, viewModel: NewsViewModel) {
        viewModel.isShowLoading.observe(viewLifecycleOwner, Observer { isShown ->
            view.progress_bar visibility if (isShown) {
                visible
            } else {
                gone
            }
        })
    }

    private fun handleObserverForNewsList(viewModel: NewsViewModel) {
        viewModel.getArticleList.observe(viewLifecycleOwner, Observer { list ->
            mNewsAdapter.setData(list)
        })
    }

    private fun handleObserverForUpdatedList(viewModel: NewsViewModel) {
        viewModel.hasUpdateDataForLikes.observe(viewLifecycleOwner, Observer { position ->
            mNewsAdapter.updateItem(position)
        })
        viewModel.hasUpdateDataForComments.observe(viewLifecycleOwner, Observer { position ->
            mNewsAdapter.updateItem(position)
        })
    }

    private fun handleObserverForErrorView(view : View,viewModel: NewsViewModel) {
        viewModel.getErrorMessage.observe(viewLifecycleOwner, Observer { message ->
            //used infix, so used english sentence
            message showAsToast forLongTime
        })
    }

    override fun onItemClicked(obj: NewsItem) {
        obj.url?.let {url ->
            replace(R.id.rlayout_container, NewsDetailsFragment.newInstance(url))
        }
    }

}