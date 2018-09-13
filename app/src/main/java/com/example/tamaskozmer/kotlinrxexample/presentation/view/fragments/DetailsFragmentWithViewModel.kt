package com.example.tamaskozmer.kotlinrxexample.presentation.view.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.tamaskozmer.kotlinrxexample.R
import com.example.tamaskozmer.kotlinrxexample.presentation.view.adapters.DetailsAdapter
import com.example.tamaskozmer.kotlinrxexample.presentation.view.viewdata.DetailsViewData
import com.example.tamaskozmer.kotlinrxexample.presentation.view.viewdata.UserViewData
import com.example.tamaskozmer.kotlinrxexample.presentation.viewmodels.DetailViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_details_with_vm.*
import javax.inject.Inject

class DetailsFragmentWithViewModel : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var detailViewModel: DetailViewModel

    private val detailsAdapter by lazy {
        DetailsAdapter({ link ->
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
            startActivity(browserIntent)
        })
    }

    private var transitionEnded = false

    companion object {
        fun newInstance(user: UserViewData): DetailsFragmentWithViewModel {
            val fragment = DetailsFragmentWithViewModel()
            val args = Bundle()
            args.putParcelable("user", user)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailViewModel =
                ViewModelProviders.of(this, viewModelFactory).get(DetailViewModel::class.java)

        addObservers()
    }

    private fun addObservers() {
        detailViewModel.showInitialLoading.observe(this, Observer {
            if (it == true) showLoading() else hideLoading()
        })

        detailViewModel.showError.observe(this, Observer {
            if (it == true) showError("Error")
        })

        detailViewModel.details.observe(this, Observer {
            it?.let {
                showDetails(it)
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        processArguments()
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
            detailViewModel.init(user.userId)

            swipeRefreshLayout.setOnRefreshListener {
                detailViewModel.getDetails(user.userId, true)
            }
        }
    }

    private fun showDetails(details: DetailsViewData) {
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

    private fun showError(error: String) {
        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading() {
        detailsAdapter.addLoadingItem()
    }

    private fun hideLoading() {
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