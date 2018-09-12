package com.example.tamaskozmer.kotlinrxexample.presentation.view.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.tamaskozmer.kotlinrxexample.databinding.FragmentUserListWithVmBinding
import com.example.tamaskozmer.kotlinrxexample.domain.interactors.GetUsers
import com.example.tamaskozmer.kotlinrxexample.presentation.view.activities.MainActivity
import com.example.tamaskozmer.kotlinrxexample.presentation.view.adapters.UserListAdapter
import com.example.tamaskozmer.kotlinrxexample.presentation.view.viewdata.UserViewData
import com.example.tamaskozmer.kotlinrxexample.presentation.viewmodels.UserListViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_user_list.*
import javax.inject.Inject

class UserListFragmentWithViewModel : DaggerFragment() {

    @Inject
    lateinit var getUsers: GetUsers

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var userListViewModel: UserListViewModel

    private val adapter by lazy {
        val userList = mutableListOf<UserViewData>()
        UserListAdapter(userList) { user, view ->
            openDetailFragment(user, view)
        }
    }

    private lateinit var layoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userListViewModel =
                ViewModelProviders.of(this, viewModelFactory).get(UserListViewModel::class.java)

        addObservers()
    }

    private fun addObservers() {
        userListViewModel.showLoading.observe(this, Observer {
            swipeRefreshLayout.isRefreshing = it != null && it
        })

        userListViewModel.showError.observe(this, Observer {
            if (it == true && userListViewModel.userList.value?.isNotEmpty() == true) {
                Toast.makeText(context, "Error loading data", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentUserListWithVmBinding.inflate(inflater, container, false)

        binding.viewModel = userListViewModel
        binding.setLifecycleOwner(this)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initSwipeRefreshLayout()
        initRecyclerView()

        userListViewModel.init()
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

    private fun openDetailFragment(user: UserViewData, transitioningView: View) {
        val detailsFragment = DetailsFragment.newInstance(user)
        (activity as MainActivity).addDetailsFragmentWithTransition(
            detailsFragment,
            transitioningView
        )
    }
}