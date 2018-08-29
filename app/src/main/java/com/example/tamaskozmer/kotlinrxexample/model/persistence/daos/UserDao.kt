package com.example.tamaskozmer.kotlinrxexample.model.persistence.daos

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.example.tamaskozmer.kotlinrxexample.model.entities.User

@Dao
interface UserDao {
    // TODO Find a more optimal solution
    @Query("SELECT * FROM user ORDER BY reputation DESC LIMIT (:page - 1) * 30, 30")
    fun getUsers(page: Int): List<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(users: List<User>)
}