package com.example.tamaskozmer.kotlinrxexample.view.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.tamaskozmer.kotlinrxexample.R
import com.example.tamaskozmer.kotlinrxexample.di.modules.DetailFragmentModule
import com.example.tamaskozmer.kotlinrxexample.presentation.view.DetailView
import com.example.tamaskozmer.kotlinrxexample.presentation.view.viewmodels.DetailsViewModel
import com.example.tamaskozmer.kotlinrxexample.presentation.view.viewmodels.UserViewModel
import com.example.tamaskozmer.kotlinrxexample.util.customApplication
import com.example.tamaskozmer.kotlinrxexample.view.adapters.DetailsAdapter
import kotlinx.android.synthetic.main.fragment_details.*


/**
 * Created by Tamas_Kozmer on 7/6/2017.
 */
class DetailsFragment : Fragment(), DetailView {

    private val component by lazy { customApplication.component.plus(DetailFragmentModule()) }
    private val presenter by lazy { component.presenter() }
    private val detailsAdapter by lazy { DetailsAdapter({ link ->
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
        startActivity(browserIntent)
    }) }

    var transitionEnded = false

    companion object {
        fun newInstance(user: UserViewModel): DetailsFragment {
            val fragment = DetailsFragment()
            val args = Bundle()
            args.putParcelable("user", user)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        val user = arguments.getParcelable<UserViewModel>("user")
        detailsAdapter.addItem(user)
        detailsAdapter.notifyDataSetChanged()
        presenter.getDetails(user.userId)

        swipeRefreshLayout.setOnRefreshListener {
            presenter.getDetails(user.userId, true)
        }
    }

    override fun showDetails(details: DetailsViewModel) {
        with(detailsAdapter) {
            removeNonUserItems()
            addItemsWithHeading(details.questions, "Top questions by user")
            addItemsWithHeading(details.answers, "Top answers by user")
            addItemsWithHeading(details.favorites, "Favorited by user")
            if (transitionEnded) {
                notifyDataSetChanged()
            }
        }
    }

    override fun showError(error: String) {
        Toast.makeText(customApplication, error, Toast.LENGTH_SHORT).show()
    }

    override fun showLoading() {
        detailsAdapter.addLoadingItem()
    }

    override fun hideLoading() {
        detailsAdapter.removeLoadingItem()
        swipeRefreshLayout.isRefreshing = false
    }

    // This logic is needed to show the content only after the shared transition has finished
    fun transitionEnded() {
        transitionEnded = true
        if (isAdded) {
            detailsRecyclerView.adapter.notifyDataSetChanged()
        }
    }
}