package com.example.tamaskozmer.kotlinrxexample.model.persistence

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.example.tamaskozmer.kotlinrxexample.model.entities.Answer
import com.example.tamaskozmer.kotlinrxexample.model.entities.Question
import com.example.tamaskozmer.kotlinrxexample.model.entities.User
import com.example.tamaskozmer.kotlinrxexample.model.persistence.daos.AnswerDao
import com.example.tamaskozmer.kotlinrxexample.model.persistence.daos.QuestionDao
import com.example.tamaskozmer.kotlinrxexample.model.persistence.daos.UserDao

/**
 * Created by Tamas_Kozmer on 7/14/2017.
 */
@Database(entities = arrayOf(User::class, Question::class, Answer::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao() : UserDao
    abstract fun questionDao() : QuestionDao
    abstract fun answerDao() : AnswerDao
}