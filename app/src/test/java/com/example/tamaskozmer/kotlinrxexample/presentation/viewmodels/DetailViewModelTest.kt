package com.example.tamaskozmer.kotlinrxexample.presentation.viewmodels

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.tamaskozmer.kotlinrxexample.domain.interactors.GetDetails
import com.example.tamaskozmer.kotlinrxexample.presentation.view.viewdata.DetailsViewData
import com.example.tamaskozmer.kotlinrxexample.testutil.TestSchedulerProvider
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import io.reactivex.schedulers.TestScheduler
import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var mockGetDetails: GetDetails

    private lateinit var detailViewModel: DetailViewModel

    private lateinit var testScheduler: TestScheduler

    @Before
    fun setUp() {
        testScheduler = TestScheduler()
        val testSchedulerProvider = TestSchedulerProvider(testScheduler)
        detailViewModel = DetailViewModel(mockGetDetails, testSchedulerProvider)
    }

    @Test
    fun testGetDetails_error() {
        // Given
        val error = "Test error"
        val userId = 1L
        val single: Single<DetailsViewData> = Single.create { emitter ->
            emitter.onError(Exception(error))
        }

        // When
        whenever(mockGetDetails.execute(userId, false)).thenReturn(single)

        detailViewModel.init(userId)

        testScheduler.triggerActions()

        // Then
        assertTrue(detailViewModel.state.value == DetailViewModel.State.ERROR)
    }

    @Test
    fun testGetDetails_success() {
        // Given
        val details = DetailsViewData(emptyList(), emptyList(), emptyList())
        val userId = 1L
        val single: Single<DetailsViewData> = Single.create { emitter ->
            emitter.onSuccess(details)
        }

        // When
        whenever(mockGetDetails.execute(userId, false)).thenReturn(single)

        detailViewModel.init(userId)

        testScheduler.triggerActions()

        // Then
        assertTrue(detailViewModel.state.value == DetailViewModel.State.LOADED)
        assertTrue(detailViewModel.details.value == details)
    }
}