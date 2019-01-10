package com.babbicool.widget.androidswipemenu.sample

import android.support.v7.recyclerview.extensions.AsyncDifferConfig
import android.support.v7.recyclerview.extensions.AsyncListDiffer
import android.support.v7.util.AdapterListUpdateCallback
import android.support.v7.util.DiffUtil
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import java.util.*

abstract class LoadMoreAdapter<T, VH : RecyclerView.ViewHolder> : RecyclerView.Adapter<VH> {

    private val mHelper: AsyncListDiffer<T>

    private lateinit var rv: RecyclerView

    var onClickListener: RVListener<T> = object: RVListener<T> {
        override fun onClick(item: T) {

        }
    }

    private lateinit var onLoadMoreListener: OnLoadMoreListener

    private lateinit var linearLayoutManager: LinearLayoutManager

    internal var visibleThreshold = 5
    internal var lastVisibleItem: Int = 0
    internal var totalItemCount: Int = 0

    internal var loading = false

    internal var data: MutableList<T> = ArrayList()

//    private val RVScrollListener = object : RecyclerView.OnScrollListener() {
//        override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
//            super.onScrolled(recyclerView, dx, dy)
//            totalItemCount = linearLayoutManager.itemCount
//            lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition()
//            if (!loading && totalItemCount <= lastVisibleItem + visibleThreshold) {
//                // End has been reached
//                // Do something
//                if (onLoadMoreListener != null && itemCount > 0) {
//                    onLoadMoreListener!!.onLoadMore()
//                }
//                loading = true
//            }
//        }
//    }


    protected constructor(diffCallback: DiffUtil.ItemCallback<T>) {
        mHelper = AsyncListDiffer(AdapterListUpdateCallback(this),
                AsyncDifferConfig.Builder(diffCallback).build())
    }

    protected constructor(config: AsyncDifferConfig<T>) {
        mHelper = AsyncListDiffer(AdapterListUpdateCallback(this), config)
    }

    fun setRv(rv: RecyclerView) {
        this.rv = rv
        iniScroll()
    }

    fun setOnLoadMoreListener(onLoadMoreListener: OnLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener
    }

    private fun iniScroll() {
        linearLayoutManager = this.rv.layoutManager as LinearLayoutManager
        rv.let {
            it.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    totalItemCount = linearLayoutManager.itemCount
                    lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition()
                    if (!loading && totalItemCount <= lastVisibleItem + visibleThreshold) {
                        // End has been reached
                        // Do something
                        if (onLoadMoreListener != null && itemCount > 0) {
                            onLoadMoreListener!!.onLoadMore()
                        }
                        loading = true
                    }

                }
            })
        }
    }

    fun setLoading(loading: Boolean) {
        this.loading = loading
    }

    fun submitLoadMoreData(list: List<T>) {
        data.addAll(list)
        notifyDataSetChanged()
        loading = false
    }

    fun getData(): List<T> {
        return data
    }

    fun submitList(list: List<T>) {
        data = ArrayList()
        data.addAll(list)
        mHelper.submitList(data)

    }

    protected fun getItem(position: Int): T {
        return mHelper.currentList[position]
    }

    override fun getItemCount(): Int {
        return mHelper.currentList.size
    }

}
