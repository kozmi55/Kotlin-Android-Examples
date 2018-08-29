package com.example.tamaskozmer.kotlinrxexample.presentation.view.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.transition.Fade
import android.transition.Transition
import android.transition.TransitionInflater
import android.view.View
import com.example.tamaskozmer.kotlinrxexample.R
import com.example.tamaskozmer.kotlinrxexample.util.TransitionListener
import com.example.tamaskozmer.kotlinrxexample.util.isLollipopOrAbove
import com.example.tamaskozmer.kotlinrxexample.presentation.view.fragments.DetailsFragment
import com.example.tamaskozmer.kotlinrxexample.presentation.view.fragments.UserListFragment

class MainActivity : AppCompatActivity() {

    companion object {
        private const val SHARED_TRANSITION_DURATION = 500L
        private const val FADE_DURATION = 200L
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addUserListFragment()
    }

    private fun addUserListFragment() {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainer, UserListFragment())
                .commit()
    }

    fun addDetailsFragmentWithTransition(fragment: Fragment, transitioningView: View) {
        val transaction = supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .addToBackStack("")

        isLollipopOrAbove {
            setUpTransition(fragment, transitioningView, transaction)
        }

        transaction.commit()
    }

    @SuppressLint("NewApi")
    private fun setUpTransition(fragment: Fragment, transitioningView: View, transaction: FragmentTransaction?) {
        // Define the different transitions, what we will be using
        val sharedTransition = TransitionInflater.from(this).inflateTransition(R.transition.shared_element_transition)
        sharedTransition.duration = SHARED_TRANSITION_DURATION
        sharedTransition.addListener(object : TransitionListener() {
            override fun onTransitionEnd(p0: Transition?) {
                (fragment as DetailsFragment).transitionEnded()
            }
        })

        val fade = Fade()
        fade.duration = FADE_DURATION

        val detailFragmentEnterTransition = Fade()
        // This should be FADE_DURATION, but only the first element will use this animation in the recyclerview,
        // so we set it to 0, to be consistent with the remaining content.
        // We still need the delay to show the content only after the shared transition was completed.
        detailFragmentEnterTransition.duration = 0
        detailFragmentEnterTransition.startDelay = SHARED_TRANSITION_DURATION

        val userListReenterTransition = Fade()
        userListReenterTransition.startDelay = FADE_DURATION

        // Get the reference for the current fragment
        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)

        // Apply the transitions for the current fragment
        currentFragment.exitTransition = fade
        currentFragment.reenterTransition = userListReenterTransition

        // Apply the transitions for the new fragment
        fragment.sharedElementEnterTransition = sharedTransition
        fragment.enterTransition = detailFragmentEnterTransition
        fragment.returnTransition = fade

        // Add the shared elements to the FragmentTransaction (We can add more elements)
        transaction?.addSharedElement(transitioningView, transitioningView.transitionName)
    }
}