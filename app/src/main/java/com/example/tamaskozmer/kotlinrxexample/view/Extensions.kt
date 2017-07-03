package com.example.tamaskozmer.kotlinrxexample.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide

/**
 * Created by Tamas_Kozmer on 7/3/2017.
 */
fun ViewGroup.inflate(layoutRes: Int): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, false)
}


fun ImageView.loadUrl(url: String) {
    Glide.with(context).load(url).into(this)
}