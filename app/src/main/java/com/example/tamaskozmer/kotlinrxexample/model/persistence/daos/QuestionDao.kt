package com.example.tamaskozmer.kotlinrxexample.model.persistence.daos

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.example.tamaskozmer.kotlinrxexample.model.entities.Question

/**
 * Created by Tamas_Kozmer on 7/17/2017.
 */
@Dao
interface QuestionDao {
    // TODO Change these when the bug is fixed
    // This is a bug in Kotlin, we need to use arg0 here, instead of the actual name of the parameter
    @Query("SELECT * FROM question WHERE userId = :arg0 ORDER BY score DESC")
    fun getQuestionsByUser(userId: Long) : List<Question>

    @Query("SELECT * FROM question WHERE questionId IN (:arg0) ORDER BY score DESC")
    fun getQuestionsById(questionIds: List<Long>) : List<Question>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(questions: List<Question>)
}