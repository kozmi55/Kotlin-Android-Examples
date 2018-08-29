package com.example.tamaskozmer.kotlinrxexample.model.persistence.daos

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.example.tamaskozmer.kotlinrxexample.model.entities.Question

@Dao
interface QuestionDao {

    @Query("SELECT * FROM question WHERE userId = :userId ORDER BY score DESC LIMIT 3")
    fun getQuestionsByUser(userId: Long): List<Question>

    @Query("SELECT * FROM question WHERE questionId IN (:questionIds) ORDER BY score DESC")
    fun getQuestionsById(questionIds: List<Long>): List<Question>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(questions: List<Question>)
}