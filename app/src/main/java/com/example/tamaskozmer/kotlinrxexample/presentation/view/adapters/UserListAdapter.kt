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
import kotlinx.android.synthetic.main.list_item_user.view.*

/**
 * Created by Tamas_Kozmer on 7/3/2017.
 */
class UserListAdapter(
        private val users: MutableList<UserViewModel>,
        private val listener: (UserViewModel, View) -> Unit) : RecyclerView.Adapter<UserListAdapter.UserViewHolder>() {

    override fun getItemCount() = users.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) = holder.bind(users[position], listener)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = UserViewHolder(parent.inflate(R.layout.list_item_user))

    fun addUsers(newUsers: List<UserViewModel>) {
        users.addAll(newUsers)
        notifyDataSetChanged()
    }

    fun clearUsers() {
        users.clear()
    }

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("NewApi")
        fun bind(user: UserViewModel, listener: (UserViewModel, View) -> Unit) = with(itemView) {
            name.text = user.displayName
            reputation.text = "${user.reputation} points"
            userAvatar.loadUrl(user.profileImage)
            setOnClickListener { listener(user, userAvatar) }

            isLollipopOrAbove { userAvatar.transitionName = "transition${user.userId}" }
        }
    }

}
