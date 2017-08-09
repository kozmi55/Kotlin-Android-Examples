package com.example.tamaskozmer.kotlinrxexample.mocks.model.repositories

import com.example.tamaskozmer.kotlinrxexample.model.entities.Answer
import com.example.tamaskozmer.kotlinrxexample.model.entities.Question
import com.example.tamaskozmer.kotlinrxexample.model.repositories.DetailsRepository
import io.reactivex.Single
import io.reactivex.SingleEmitter

/**
 * Created by Tamas_Kozmer on 8/8/2017.
 */
class MockDetailsRepository : DetailsRepository {
    override fun getQuestionsByUser(userId: Long, forced: Boolean): Single<List<Question>> {
        return createSingle(emptyList())
    }

    override fun getAnswersByUser(userId: Long, forced: Boolean): Single<List<Answer>> {
        return createSingle(emptyList())
    }

    override fun getFavoritesByUser(userId: Long, forced: Boolean): Single<List<Question>> {
        return createSingle(emptyList())
    }

    override fun getQuestionsById(ids: List<Long>, userId: Long, forced: Boolean): Single<List<Question>> {
        return createSingle(emptyList())
    }

    private fun <T> createSingle(emittedItem: T): Single<T> {
        return Single.create { emitter: SingleEmitter<T> -> emitter.onSuccess(emittedItem) }
    }
}