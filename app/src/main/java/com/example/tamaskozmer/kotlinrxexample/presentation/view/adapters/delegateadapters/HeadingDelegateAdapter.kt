package com.example.tamaskozmer.kotlinrxexample.presentation.view.adapters.delegateadapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.example.tamaskozmer.kotlinrxexample.R
import com.example.tamaskozmer.kotlinrxexample.presentation.view.viewmodels.Heading
import com.example.tamaskozmer.kotlinrxexample.util.inflate
import com.example.tamaskozmer.kotlinrxexample.presentation.view.adapters.ViewType
import com.example.tamaskozmer.kotlinrxexample.presentation.view.adapters.ViewTypeDelegateAdapter
import kotlinx.android.synthetic.main.list_item_heading.view.*

class HeadingDelegateAdapter : ViewTypeDelegateAdapter {
    override fun onCreateViewHolder(parent: ViewGroup) =
            HeadingViewHolder(parent.inflate(R.layout.list_item_heading))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        holder as HeadingViewHolder
        holder.bind(item as Heading)
    }

    class HeadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(heading: Heading) = with(itemView) {
            title.text = heading.title
        }
    }
}