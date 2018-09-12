package com.example.tamaskozmer.kotlinrxexample.presentation.view.adapters.delegateadapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.example.tamaskozmer.kotlinrxexample.R
import com.example.tamaskozmer.kotlinrxexample.presentation.view.viewdata.AnswerViewData
import com.example.tamaskozmer.kotlinrxexample.util.inflate
import com.example.tamaskozmer.kotlinrxexample.presentation.view.adapters.ViewType
import com.example.tamaskozmer.kotlinrxexample.presentation.view.adapters.ViewTypeDelegateAdapter
import kotlinx.android.synthetic.main.list_item_answer.view.*

class AnswerDelegateAdapter(private val listener: (String) -> Unit) : ViewTypeDelegateAdapter {
    override fun onCreateViewHolder(parent: ViewGroup) =
            AnswerViewHolder(parent.inflate(R.layout.list_item_answer))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        holder as AnswerViewHolder
        holder.bind(item as AnswerViewData, listener)
    }

    class AnswerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(answer: AnswerViewData, listener: (String) -> Unit) = with(itemView) {
            score.text = "${answer.score} points"
            questionTitle.text = answer.questionTitle

            setOnClickListener { listener("https://stackoverflow.com/a/${answer.answerId}") }
        }
    }
}