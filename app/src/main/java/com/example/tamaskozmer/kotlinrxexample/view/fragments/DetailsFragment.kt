package com.example.tamaskozmer.kotlinrxexample.view.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tamaskozmer.kotlinrxexample.R
import com.example.tamaskozmer.kotlinrxexample.di.modules.DetailFragmentModule
import com.example.tamaskozmer.kotlinrxexample.model.entities.DetailsModel
import com.example.tamaskozmer.kotlinrxexample.model.entities.Heading
import com.example.tamaskozmer.kotlinrxexample.model.entities.User
import com.example.tamaskozmer.kotlinrxexample.view.DetailView
import com.example.tamaskozmer.kotlinrxexample.view.adapters.DetailsAdapter
import com.example.tamaskozmer.kotlinrxexample.view.customApplication
import kotlinx.android.synthetic.main.fragment_details.*

/**
 * Created by Tamas_Kozmer on 7/6/2017.
 */
class DetailsFragment : Fragment(), DetailView {

    private val component by lazy { customApplication.component.plus(DetailFragmentModule(this)) }
    private val presenter by lazy { component.presenter() }
    private val detailsAdapter by lazy { DetailsAdapter() }

    companion object {
        fun newInstance(user: User): DetailsFragment {
            val fragment = DetailsFragment()
            val args = Bundle()
            args.putParcelable("user", user)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.attachView(this)

        initAdapter()
        processArguments()
    }

    override fun onDestroyView() {
        presenter.detachView()
        super.onDestroyView()
    }

    private fun initAdapter() {
        detailsRecyclerView.layoutManager = LinearLayoutManager(customApplication)
        detailsRecyclerView.adapter = detailsAdapter
    }

    private fun processArguments() {
        val user = arguments.getParcelable<User>("user")
        detailsAdapter.addItem(user)
        detailsAdapter.notifyDataSetChanged()
        presenter.getDetails(user.userId)
    }

    override fun showDetails(detailsModel: DetailsModel) {
        with(detailsAdapter) {
            if (!detailsModel.questions.isEmpty()) {
                addItem(Heading("Top questions by user"))
                addItems(detailsModel.questions)
            }
            if (!detailsModel.answers.isEmpty()) {
                addItem(Heading("Top answers by user"))
                addItems(detailsModel.answers)
            }
            if (!detailsModel.favorites.isEmpty()) {
                addItem(Heading("Favorited by user"))
                addItems(detailsModel.favorites)
            }
            notifyDataSetChanged()
        }
    }

    override fun showError() {
        Log.d("details", "error")
    }

    override fun showLoading() {
        detailsAdapter.addLoadingItem()
    }

    override fun hideLoading() {
        detailsAdapter.removeLoadingItem()
    }
}