package com.example.tamaskozmer.kotlinrxexample.model.persistence.daos

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.example.tamaskozmer.kotlinrxexample.model.entities.FavoritedByUser

/**
 * Created by Tamas_Kozmer on 7/18/2017.
 */
@Dao
interface FavoritedByUserDao {

    @Query("SELECT * FROM favoritedByUser WHERE userId = :userId")
    fun getFavoritesForUser(userId: Long) : FavoritedByUser?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(favoritedByUser: FavoritedByUser)
}