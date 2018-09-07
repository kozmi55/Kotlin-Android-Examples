package com.example.tamaskozmer.kotlinrxexample.presentation.view.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tamaskozmer.kotlinrxexample.databinding.FragmentUserListBinding
import com.example.tamaskozmer.kotlinrxexample.domain.interactors.GetUsers
import com.example.tamaskozmer.kotlinrxexample.presentation.view.activities.MainActivity
import com.example.tamaskozmer.kotlinrxexample.presentation.view.adapters.UserListAdapter
import com.example.tamaskozmer.kotlinrxexample.presentation.view.viewmodels.UserViewModel
import com.example.tamaskozmer.kotlinrxexample.presentation.viewmodels.UserListViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_user_list.*
import javax.inject.Inject

class UserListFragmentWithViewModel : DaggerFragment() {

    @Inject
    lateinit var getUsers: GetUsers

    private lateinit var userListViewModel: UserListViewModel

    private val adapter by lazy {
        val userList = mutableListOf<UserViewModel>()
        UserListAdapter(userList) { user, view ->
            openDetailFragment(user, view)
        }
    }

    private lateinit var layoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userListViewModel = ViewModelProviders.of(this, CustomViewModelFactory()).get(UserListViewModel::class.java)

        userListViewModel.showLoading.observe(this, Observer {
            swipeRefreshLayout.isRefreshing = it != null && it
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentUserListBinding.inflate(inflater, container, false)

        binding.viewModel = userListViewModel
        binding.setLifecycleOwner(this)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initSwipeRefreshLayout()
        initRecyclerView()

        userListViewModel.getUsers()
    }

    private fun initSwipeRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener {
            userListViewModel.getUsers(true)
        }
    }

    private fun initRecyclerView() {
        layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager

        recyclerView.adapter = adapter

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {

                val lastVisibleItemPosition =
                    layoutManager.findFirstVisibleItemPosition() + layoutManager.childCount
                val totalItemCount = layoutManager.itemCount

                userListViewModel.onScrollChanged(lastVisibleItemPosition, totalItemCount)
            }
        })
    }

    private fun openDetailFragment(user: UserViewModel, transitioningView: View) {
        val detailsFragment = DetailsFragment.newInstance(user)
        (activity as MainActivity).addDetailsFragmentWithTransition(
            detailsFragment,
            transitioningView
        )
    }

    inner class CustomViewModelFactory : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return UserListViewModel(getUsers) as T
        }
    }
}