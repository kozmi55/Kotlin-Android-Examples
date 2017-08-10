package com.example.tamaskozmer.kotlinrxexample.model.persistence.daos

import android.arch.lifecycle.LiveData
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
    // TODO Find a more optimal solution
    @Query("SELECT * FROM user ORDER BY reputation DESC LIMIT :arg0 * 30")
    fun getUsers(page: Int) : LiveData<List<User>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(users: List<User>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User)
}