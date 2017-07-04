package com.example.tamaskozmer.kotlinrxexample.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.example.tamaskozmer.kotlinrxexample.R
import com.example.tamaskozmer.kotlinrxexample.di.modules.MainActivityModule
import com.example.tamaskozmer.kotlinrxexample.model.entities.User
import com.example.tamaskozmer.kotlinrxexample.presentation.UserListPresenter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainView {

    private val presenter: UserListPresenter by lazy { component.presenter() }

    private val component by lazy { customApplication.component.plus(MainActivityModule(this)) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        component.inject(this)

        initViews()
        presenter.attachView(this)
        presenter.getUsers()
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }

    // region View interface methods
    private fun initViews() {
        swipeRefreshLayout.setOnRefreshListener { presenter.getUsers() }
    }

    override fun showLoading() {
        swipeRefreshLayout.isRefreshing = true
    }

    override fun hideLoading() {
        swipeRefreshLayout.isRefreshing = false
    }

    override fun showUsers(users: List<User>) {
        initAdapter(users)
    }

    override fun showError() {
        Toast.makeText(this, "Couldn't load data", Toast.LENGTH_SHORT).show()
    }
    // endregion

    private fun initAdapter(list: List<User>) {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = UserListAdapter(list) {
            Toast.makeText(this, it.displayName, Toast.LENGTH_SHORT).show()
        }
    }
}
