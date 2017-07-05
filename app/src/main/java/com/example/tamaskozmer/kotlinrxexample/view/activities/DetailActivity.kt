package com.example.tamaskozmer.kotlinrxexample.view.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.tamaskozmer.kotlinrxexample.R
import com.example.tamaskozmer.kotlinrxexample.di.modules.DetailActivityModule
import com.example.tamaskozmer.kotlinrxexample.model.entities.DetailsModel
import com.example.tamaskozmer.kotlinrxexample.model.entities.User
import com.example.tamaskozmer.kotlinrxexample.view.DetailView
import com.example.tamaskozmer.kotlinrxexample.view.customApplication
import com.example.tamaskozmer.kotlinrxexample.view.loadUrl
import kotlinx.android.synthetic.main.activity_detail.*

/**
 * Created by Tamas_Kozmer on 7/5/2017.
 */
class DetailActivity : AppCompatActivity(), DetailView {

    private val component by lazy { customApplication.component.plus(DetailActivityModule(this)) }
    private val presenter by lazy { component.presenter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        component.inject(this)
        presenter.attachView(this)

        processIntent()
    }

    private fun processIntent() {
        val user = intent.getParcelableExtra<User>("user")
        profileImage.loadUrl(user.profileImage)
        presenter.getDetails(user.userId)
    }

    override fun showDetails(detailsModel: DetailsModel) {
        Log.d("details", detailsModel.toString())
    }

    override fun showError() {
        Log.d("details", "error")
    }
}