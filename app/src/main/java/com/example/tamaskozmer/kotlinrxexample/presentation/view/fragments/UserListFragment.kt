package com.example.tamaskozmer.kotlinrxexample.presentation.view.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.tamaskozmer.kotlinrxexample.R
import com.example.tamaskozmer.kotlinrxexample.presentation.presenters.UserListPresenter
import com.example.tamaskozmer.kotlinrxexample.presentation.view.UserListView
import com.example.tamaskozmer.kotlinrxexample.presentation.view.viewmodels.UserViewModel
import com.example.tamaskozmer.kotlinrxexample.util.customApplication
import com.example.tamaskozmer.kotlinrxexample.view.activities.MainActivity
import com.example.tamaskozmer.kotlinrxexample.view.adapters.UserListAdapter
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_user_list.*
import javax.inject.Inject

class UserListFragment : DaggerFragment(), UserListView {

    @Inject
    lateinit var presenter: UserListPresenter

    private val adapter by lazy {
        val userList = mutableListOf<UserViewModel>()
        UserListAdapter(userList) { user, view ->
            openDetailFragment(user, view)
        }
    }

    private lateinit var layoutManager: LinearLayoutManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_user_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        initAdapter()

        presenter.attachView(this)

        // Prevent reloading when going back
        if (adapter.itemCount == 0) {
            showLoading()
            presenter.getUsers()
        }
    }

    override fun onDestroyView() {
        presenter.detachView()
        super.onDestroyView()
    }


    private fun initViews() {
        swipeRefreshLayout.setOnRefreshListener {
            presenter.getUsers(forced = true)
        }
    }

    // region View interface methods
    override fun showLoading() {
        swipeRefreshLayout.isRefreshing = true
    }

    override fun hideLoading() {
        swipeRefreshLayout.isRefreshing = false
    }

    override fun addUsersToList(users: List<UserViewModel>) {
        val adapter = recyclerView.adapter as UserListAdapter
        adapter.addUsers(users)
    }

    override fun showEmptyListError() {
        errorView.visibility = View.VISIBLE
    }

    override fun hideEmptyListError() {
        errorView.visibility = View.GONE
    }

    override fun showToastError() {
        Toast.makeText(context, "Error loading data", Toast.LENGTH_SHORT).show()
    }

    override fun clearList() {
        adapter.clearUsers()
    }
    // endregion

    private fun initAdapter() {
        layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager

        recyclerView.adapter = adapter

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {

                val lastVisibleItemPosition = layoutManager.findFirstVisibleItemPosition() + layoutManager.childCount
                val totalItemCount = layoutManager.itemCount

                presenter.onScrollChanged(lastVisibleItemPosition, totalItemCount)
            }
        })
    }

    private fun openDetailFragment(user: UserViewModel, transitioningView: View) {
        val detailsFragment = DetailsFragment.newInstance(user)
        (activity as MainActivity).addDetailsFragmentWithTransition(detailsFragment, transitioningView)
    }
}