package com.example.tamaskozmer.kotlinrxexample.view.adapters

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.example.tamaskozmer.kotlinrxexample.R
import com.example.tamaskozmer.kotlinrxexample.presentation.view.viewmodels.UserViewModel
import com.example.tamaskozmer.kotlinrxexample.util.inflate
import com.example.tamaskozmer.kotlinrxexample.util.isLollipopOrAbove
import com.example.tamaskozmer.kotlinrxexample.util.loadUrl
import com.example.tamaskozmer.kotlinrxexample.view.adapters.viewtypes.ViewType
import kotlinx.android.synthetic.main.list_item_user_details.view.*

/**
 * Created by Tamas_Kozmer on 7/6/2017.
 */
class UserDetailsDelegateAdapter : ViewTypeDelegateAdapter {

    override fun onCreateViewHolder(parent: ViewGroup)
            = UserDetailsViewHolder(parent.inflate(R.layout.list_item_user_details))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        holder as UserDetailsViewHolder
        holder.bind(item as UserViewModel)
    }

    class UserDetailsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("NewApi")
        fun bind(user: UserViewModel) = with(itemView) {
            profileImage.loadUrl(user.profileImage)
            name.text = user.displayName
            reputation.text = "${user.reputation} points"

            isLollipopOrAbove { profileImage.transitionName = "transition${user.userId}" }
        }
    }
}