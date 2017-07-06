package com.example.tamaskozmer.kotlinrxexample.view.activities

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.example.tamaskozmer.kotlinrxexample.R
import com.example.tamaskozmer.kotlinrxexample.view.fragments.UserListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addFragment(UserListFragment())
    }

    fun addFragment(fragment: Fragment, addToBackStack: Boolean = false) {
        val transaction = supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment)
        if (addToBackStack) {
            transaction.addToBackStack("")
        }
        transaction.commit()
    }
}
