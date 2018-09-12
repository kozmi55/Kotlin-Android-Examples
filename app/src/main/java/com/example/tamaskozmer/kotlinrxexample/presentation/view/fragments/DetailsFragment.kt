package com.example.tamaskozmer.kotlinrxexample.presentation.view.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.tamaskozmer.kotlinrxexample.R
import com.example.tamaskozmer.kotlinrxexample.presentation.presenters.DetailPresenter
import com.example.tamaskozmer.kotlinrxexample.presentation.view.DetailView
import com.example.tamaskozmer.kotlinrxexample.presentation.view.viewdata.DetailsViewData
import com.example.tamaskozmer.kotlinrxexample.presentation.view.viewdata.UserViewData
import com.example.tamaskozmer.kotlinrxexample.presentation.view.adapters.DetailsAdapter
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_details.*
import javax.inject.Inject

class DetailsFragment : DaggerFragment(), DetailView {

    @Inject
    lateinit var presenter: DetailPresenter

    private val detailsAdapter by lazy {
        DetailsAdapter({ link ->
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
            startActivity(browserIntent)
        })
    }

    var transitionEnded = false

    companion object {
        fun newInstance(user: UserViewData): DetailsFragment {
            val fragment = DetailsFragment()
            val args = Bundle()
            args.putParcelable("user", user)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
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
        detailsRecyclerView.layoutManager = LinearLayoutManager(context)
        detailsRecyclerView.adapter = detailsAdapter
    }

    private fun processArguments() {
        val user = arguments?.getParcelable<UserViewData>("user")

        if (user != null) {
            detailsAdapter.addItem(user)
            detailsAdapter.notifyDataSetChanged()
            presenter.getDetails(user.userId)

            swipeRefreshLayout.setOnRefreshListener {
                presenter.getDetails(user.userId, true)
            }
        }
    }

    override fun showDetails(details: DetailsViewData) {
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
        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
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