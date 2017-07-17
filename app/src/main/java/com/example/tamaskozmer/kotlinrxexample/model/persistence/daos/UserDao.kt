package com.example.tamaskozmer.kotlinrxexample.model.persistence.daos

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.example.tamaskozmer.kotlinrxexample.model.entities.User

/**
 * Created by Tamas_Kozmer on 7/14/2017.
 */
@Dao
interface UserDao {
    @Query("SELECT * FROM user ORDER BY reputation DESC")
    fun getAllUsers() : List<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(users: List<User>)
}