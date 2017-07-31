package com.example.tamaskozmer.kotlinrxexample.model.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by Tamas_Kozmer on 7/18/2017.
 */
@Entity
data class FavoritedByUser(
        @PrimaryKey var userId: Long,
        var questionIds: List<Long>) {

    constructor() : this(-1, emptyList())
}