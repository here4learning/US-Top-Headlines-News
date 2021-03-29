package org.test.news.details.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_news.view.*
import org.test.news.OnItemClickListener
import org.test.news.R
import org.test.news.details.entity.NewsItem
import org.test.news.extension.loadImage


class NewsAdapter : RecyclerView.Adapter<NewsAdapter.ComplexEmployeeHolder>() {

    private var mNewsList: List<NewsItem>? = null

    private var mOnItemClickClickListener: OnItemClickListener<NewsItem>? = null

    fun setOnItemClickListener(listener: OnItemClickListener<NewsItem>?) {
        mOnItemClickClickListener = listener
    }

    fun updateItem(position: Int) {
        notifyItemChanged(position)
    }

    fun clear() {
        mNewsList = null
        notifyDataSetChanged()
    }

    fun update() {
        notifyDataSetChanged()
    }

    fun setData(list: List<NewsItem>?) {
        mNewsList = list
        notifyDataSetChanged()
    }

    fun setStableId() {
        if (!hasObservers()) {
            setHasStableIds(true)
        }
    }

    override fun getItemId(position: Int): Long {
        return mNewsList!![position].id?.toLong() ?: 0L
    }

    override fun getItemCount(): Int {
        return mNewsList?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComplexEmployeeHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        return ComplexEmployeeHolder(view)
    }

    override fun onBindViewHolder(holder: ComplexEmployeeHolder, position: Int) {
        holder.bind(mNewsList!![position])
    }

    inner class ComplexEmployeeHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        fun bind(item: NewsItem) {
            itemView.image_view_news.loadImage(item.urlToImage, R.drawable.bg_default)
            itemView.text_view_description.text = item.description
            itemView.text_view_author.text = item.author
            itemView.text_view_likes.text = "${item.noOfLikes} Likes"
            itemView.text_view_comment.text = "${item.noOfComments} Comments"

            itemView.setOnClickListener {
                if(adapterPosition > -1) {
                    mOnItemClickClickListener?.onItemClicked(mNewsList?.get(adapterPosition)!!)
                }
            }
        }
    }
}