package com.example.tamaskozmer.kotlinrxexample.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Toast
import com.example.tamaskozmer.kotlinrxexample.R
import com.example.tamaskozmer.kotlinrxexample.model.User
import com.example.tamaskozmer.kotlinrxexample.model.UserService
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import retrofit.GsonConverterFactory
import retrofit.Retrofit
import retrofit.RxJavaCallAdapterFactory
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    val retrofit: Retrofit = Retrofit.Builder()
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .baseUrl("https://api.stackexchange.com/2.2/")
            .build()

    val userService = retrofit.create(UserService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        refreshData()
    }

    private fun initViews() {
        swipeRefreshLayout.setOnRefreshListener { refreshData() }
    }

    private fun initAdapter(list: List<User>) {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = UserListAdapter(list) {
            Toast.makeText(this, "asd", Toast.LENGTH_SHORT).show()
        }
    }

    private fun refreshData() {
        userService.getUsers()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    userItems -> initAdapter(userItems.items)
                    swipeRefreshLayout.isRefreshing = false
                },
                {
                    error -> Log.e("Error", error.toString())
                    swipeRefreshLayout.isRefreshing = false
                })
    }
}
