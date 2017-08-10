package com.example.tamaskozmer.kotlinrxexample.view.fragments

import android.arch.lifecycle.LifecycleFragment
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.tamaskozmer.kotlinrxexample.R
import com.example.tamaskozmer.kotlinrxexample.di.components.ApplicationComponent
import com.example.tamaskozmer.kotlinrxexample.model.entities.User
import com.example.tamaskozmer.kotlinrxexample.presentation.presenters.UserListViewModel
import com.example.tamaskozmer.kotlinrxexample.presentation.view.viewmodels.UserViewModel
import com.example.tamaskozmer.kotlinrxexample.util.customApplication
import com.example.tamaskozmer.kotlinrxexample.view.activities.MainActivity
import com.example.tamaskozmer.kotlinrxexample.view.adapters.UserListAdapter
import kotlinx.android.synthetic.main.fragment_user_list.*

/**
 * Created by Tamas_Kozmer on 7/6/2017.
 */
class UserListFragment : LifecycleFragment() {

    private val viewModel: UserListViewModel by lazy { ViewModelProviders.of(this).get(UserListViewModel::class.java) }
    private val component: ApplicationComponent by lazy { customApplication.component }
    private val adapter: UserListAdapter by lazy {
        val userList = mutableListOf<User>()
        UserListAdapter(userList) {
            user, view -> viewModel.increaseRep(user)
        }
    }

    private lateinit var layoutManager: LinearLayoutManager

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_user_list, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        initAdapter()

        // Prevent reloading when going back
        viewModel.init(component.provideUserRepository())

        viewModel.usersLiveData?.observe(this, Observer {
            it?.let {
                clearList()
                addUsersToList(it)
            }
            adapter.notifyDataSetChanged()
        })

        viewModel.loadingLiveData.observe(this, Observer {
            it?.let {
                if (it) {
                    showLoading()
                } else {
                    hideLoading()
                }
            }
        })
    }

    private fun initViews() {
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.refresh()
        }
    }

    // region View interface methods
    fun showLoading() {
        swipeRefreshLayout.isRefreshing = true
    }

    fun hideLoading() {
        swipeRefreshLayout.isRefreshing = false
    }

    fun addUsersToList(users: List<User>) {
        adapter.addUsers(users)
    }

    fun showError() {
        Toast.makeText(customApplication, "Couldn't load data", Toast.LENGTH_SHORT).show()
    }

    fun clearList() {
        adapter.clearUsers()
    }
    // endregion

    private fun initAdapter() {
        layoutManager = LinearLayoutManager(customApplication)
        recyclerView.layoutManager = layoutManager

        recyclerView.adapter = adapter

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {

                val lastVisibleItemPosition = layoutManager.findFirstVisibleItemPosition() + layoutManager.childCount
                val totalItemCount = layoutManager.itemCount

                viewModel.onScrollChanged(lastVisibleItemPosition, totalItemCount)
            }
        })
    }

    private fun openDetailFragment(user: UserViewModel, transitioningView: View) {
        val detailsFragment = DetailsFragment.newInstance(user)
        (activity as MainActivity).addDetailsFragmentWithTransition(detailsFragment, transitioningView)
    }
}