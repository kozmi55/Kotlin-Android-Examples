package com.example.tamaskozmer.kotlinrxexample.presentation.view.adapters

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.example.tamaskozmer.kotlinrxexample.R
import com.example.tamaskozmer.kotlinrxexample.presentation.view.viewdata.UserViewData
import com.example.tamaskozmer.kotlinrxexample.util.BindableAdapter
import com.example.tamaskozmer.kotlinrxexample.util.inflate
import com.example.tamaskozmer.kotlinrxexample.util.isLollipopOrAbove
import com.example.tamaskozmer.kotlinrxexample.util.loadUrl
import kotlinx.android.synthetic.main.list_item_user.view.*

class UserListAdapter(
    private val users: MutableList<UserViewData>,
    private val listener: (UserViewData, View) -> Unit
) : RecyclerView.Adapter<UserListAdapter.UserViewHolder>(), BindableAdapter<List<UserViewData>> {

    override fun setData(items: List<UserViewData>) {
        users.clear()
        users.addAll(items)
        notifyDataSetChanged()
    }

    override fun getItemCount() = users.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) =
        holder.bind(users[position], listener)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        UserViewHolder(parent.inflate(R.layout.list_item_user))

    fun addUsers(newUsers: List<UserViewData>) {
        users.addAll(newUsers)
        notifyDataSetChanged()
    }

    fun clearUsers() {
        users.clear()
    }

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("NewApi")
        fun bind(user: UserViewData, listener: (UserViewData, View) -> Unit) = with(itemView) {
            name.text = user.displayName
            reputation.text = "${user.reputation} points"
            userAvatar.loadUrl(user.profileImage)
            setOnClickListener { listener(user, userAvatar) }

            isLollipopOrAbove { userAvatar.transitionName = "transition${user.userId}" }
        }
    }
}