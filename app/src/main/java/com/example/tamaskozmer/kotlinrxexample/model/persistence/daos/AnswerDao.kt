package com.example.tamaskozmer.kotlinrxexample.model.persistence.daos

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.example.tamaskozmer.kotlinrxexample.model.entities.Answer

/**
 * Created by Tamas_Kozmer on 7/18/2017.
 */
@Dao
interface AnswerDao {

    @Query("SELECT * FROM answer WHERE ownerId = :arg0 ORDER BY score DESC")
    fun getAnswersByUser(userId: Long) : List<Answer>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(answers: List<Answer>)
}