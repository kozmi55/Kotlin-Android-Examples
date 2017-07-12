package com.example.tamaskozmer.kotlinrxexample.view.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.example.tamaskozmer.kotlinrxexample.R
import com.example.tamaskozmer.kotlinrxexample.model.entities.AnswerViewModel
import com.example.tamaskozmer.kotlinrxexample.view.adapters.viewtypes.ViewType
import com.example.tamaskozmer.kotlinrxexample.view.inflate
import kotlinx.android.synthetic.main.list_item_answer.view.*

/**
 * Created by Tamas_Kozmer on 7/6/2017.
 */
class AnswerDelegateAdapter : ViewTypeDelegateAdapter {
    override fun onCreateViewHolder(parent: ViewGroup)
            = AnswerViewHolder(parent.inflate(R.layout.list_item_answer))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        holder as AnswerViewHolder
        holder.bind(item as AnswerViewModel)
    }

    class AnswerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(answer: AnswerViewModel) = with(itemView) {
            link.text = "https://stackoverflow.com/a/${answer.answerId}"
            score.text = "${answer.score} points"
            questionTitle.text = answer.questionTitle
        }
    }
}