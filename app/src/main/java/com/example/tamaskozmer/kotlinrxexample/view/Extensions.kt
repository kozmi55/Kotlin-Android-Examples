package com.example.tamaskozmer.kotlinrxexample.view

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.tamaskozmer.kotlinrxexample.CustomApplication

/**
 * Created by Tamas_Kozmer on 7/3/2017.
 */
fun ViewGroup.inflate(layoutRes: Int): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, false)
}

fun ImageView.loadUrl(url: String) {
    Glide.with(context).load(url).into(this)
}

val Activity.customApplication: CustomApplication
    get() = application as CustomApplication