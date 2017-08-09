package com.example.tamaskozmer.kotlinrxexample.mocks.model.repositories

import com.example.tamaskozmer.kotlinrxexample.model.entities.Answer
import com.example.tamaskozmer.kotlinrxexample.model.entities.Question
import com.example.tamaskozmer.kotlinrxexample.model.repositories.DetailsRepository
import io.reactivex.Single

/**
 * Created by Tamas_Kozmer on 8/8/2017.
 */
class MockDetailsRepository : DetailsRepository {
    override fun getQuestionsByUser(userId: Long, forced: Boolean): Single<List<Question>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getAnswersByUser(userId: Long, forced: Boolean): Single<List<Answer>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getFavoritesByUser(userId: Long, forced: Boolean): Single<List<Question>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getQuestionsById(ids: List<Long>, userId: Long, forced: Boolean): Single<List<Question>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}