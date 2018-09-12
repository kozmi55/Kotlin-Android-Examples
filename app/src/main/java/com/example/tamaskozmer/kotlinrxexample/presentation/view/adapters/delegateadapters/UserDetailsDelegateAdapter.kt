package com.example.tamaskozmer.kotlinrxexample.presentation.view.adapters.delegateadapters

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.example.tamaskozmer.kotlinrxexample.R
import com.example.tamaskozmer.kotlinrxexample.presentation.view.viewdata.UserViewData
import com.example.tamaskozmer.kotlinrxexample.util.inflate
import com.example.tamaskozmer.kotlinrxexample.util.isLollipopOrAbove
import com.example.tamaskozmer.kotlinrxexample.util.loadUrl
import com.example.tamaskozmer.kotlinrxexample.presentation.view.adapters.ViewType
import com.example.tamaskozmer.kotlinrxexample.presentation.view.adapters.ViewTypeDelegateAdapter
import kotlinx.android.synthetic.main.list_item_user_details.view.*

class UserDetailsDelegateAdapter : ViewTypeDelegateAdapter {

    override fun onCreateViewHolder(parent: ViewGroup) =
            UserDetailsViewHolder(parent.inflate(R.layout.list_item_user_details))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        holder as UserDetailsViewHolder
        holder.bind(item as UserViewData)
    }

    class UserDetailsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("NewApi")
        fun bind(user: UserViewData) = with(itemView) {
            profileImage.loadUrl(user.profileImage)
            name.text = user.displayName
            reputation.text = "${user.reputation} points"

            isLollipopOrAbove { profileImage.transitionName = "transition${user.userId}" }
        }
    }
}