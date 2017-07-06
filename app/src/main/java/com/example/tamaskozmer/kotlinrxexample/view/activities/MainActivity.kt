package com.example.tamaskozmer.kotlinrxexample.view.activities

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Toast
import com.example.tamaskozmer.kotlinrxexample.R
import com.example.tamaskozmer.kotlinrxexample.di.modules.MainActivityModule
import com.example.tamaskozmer.kotlinrxexample.model.entities.User
import com.example.tamaskozmer.kotlinrxexample.presentation.UserListPresenter
import com.example.tamaskozmer.kotlinrxexample.view.MainView
import com.example.tamaskozmer.kotlinrxexample.view.adapters.UserListAdapter
import com.example.tamaskozmer.kotlinrxexample.view.customApplication
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainView {

    private val presenter: UserListPresenter by lazy { component.presenter() }
    private val component by lazy { customApplication.component.plus(MainActivityModule(this)) }

    private lateinit var layoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        component.inject(this)

        initViews()
        initAdapter()

        presenter.attachView(this)

        showLoading()
        presenter.getUsers()
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }

    // region View interface methods
    private fun initViews() {
        swipeRefreshLayout.setOnRefreshListener {
            initAdapter()
            presenter.resetPaging()
            presenter.getUsers()
        }
    }

    override fun showLoading() {
        swipeRefreshLayout.isRefreshing = true
    }

    override fun hideLoading() {
        swipeRefreshLayout.isRefreshing = false
    }

    override fun addUsersToList(users: List<User>) {
        val adapter = recyclerView.adapter as UserListAdapter
        adapter.addUsers(users)
    }

    override fun showError() {
        Toast.makeText(this, "Couldn't load data", Toast.LENGTH_SHORT).show()
    }
    // endregion

    private fun initAdapter() {
        layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        val userList = mutableListOf<User>()
        recyclerView.adapter = UserListAdapter(userList) {
            user, view -> openDetailActivity(user, view)
        }

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {

                val lastVisibleItemPosition = layoutManager.findFirstVisibleItemPosition() + layoutManager.childCount
                val totalItemCount = layoutManager.itemCount

                presenter.onScrollChanged(lastVisibleItemPosition, totalItemCount)
            }
        })
    }

    private fun openDetailActivity(user: User, transitioningView: View) {
        val detailIntent = Intent(this, DetailActivity::class.java)
        detailIntent.putExtra("user", user)

        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, transitioningView, "transition ${user.userId}")

        startActivity(detailIntent, options.toBundle())
    }
}
