package com.babbicool.widget.androidswipemenu.sample

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.babbicool.widget.androidswipemenu.R

class TestAdapter : LoadMoreAdapter<FakeModel, TestAdapter.VH>
(TestAdapter.InboxAdapterDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestAdapter.VH {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_menu, parent, false)
        return  TestAdapter.VH(view,onClickListener)
    }

    override fun onBindViewHolder(holder: TestAdapter.VH, position: Int) {
        holder.bind(getItem(position))
    }


    class VH(itemView: View, onItemClick : RVListener<FakeModel>) : RecyclerView.ViewHolder(itemView) {


        var onItemClick : RVListener<FakeModel>


        init {
            this.onItemClick = onItemClick
        }

        fun bind(item: FakeModel) {


            itemView.setOnClickListener {
                onItemClick.let {
                    it.onClick(item)
                }
            }

        }
    }



    class InboxAdapterDiff : DiffUtil.ItemCallback<FakeModel>() {

        override fun areItemsTheSame(oldItem: FakeModel, newItem: FakeModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: FakeModel, newItem: FakeModel): Boolean {
            return oldItem.id == newItem.id
        }
    }

}