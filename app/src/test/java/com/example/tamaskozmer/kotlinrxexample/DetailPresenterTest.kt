package com.example.tamaskozmer.kotlinrxexample

import com.example.tamaskozmer.kotlinrxexample.domain.interactors.GetDetails
import com.example.tamaskozmer.kotlinrxexample.presentation.presenters.DetailPresenter
import com.example.tamaskozmer.kotlinrxexample.presentation.view.DetailView
import com.example.tamaskozmer.kotlinrxexample.presentation.view.viewmodels.DetailsViewModel
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

/**
 * Created by Tamas_Kozmer on 7/21/2017.
 */
class DetailPresenterTest {

    @Rule @JvmField
    val immediateSchedulerRule = ImmediateSchedulerRule()

    @Mock
    lateinit var mockGetDetails: GetDetails

    @Mock
    lateinit var mockView: DetailView

    lateinit var detailPresenter: DetailPresenter

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        detailPresenter = DetailPresenter(mockGetDetails)
    }

    @Test
    fun testGetDetails_error() {
        // Given
        val error = "Test error"
        val userId = 1L
        val single: Single<DetailsViewModel> = Single.create {
            emitter -> emitter?.onError(Exception(error))
        }

        // When
        `when`(mockGetDetails.execute(userId)).thenReturn(single)

        detailPresenter.attachView(mockView)
        detailPresenter.getDetails(userId)

        // Then
        verify(mockView).showLoading()
        verify(mockView).hideLoading()
        verify(mockView).showError(error)
    }

    @Test
    fun testGetDetails_success() {
        // Given
        val details = DetailsViewModel(emptyList(), emptyList(), emptyList())
        val userId = 1L
        val single: Single<DetailsViewModel> = Single.create {
            emitter -> emitter?.onSuccess(details)
        }

        // When
        `when`(mockGetDetails.execute(userId)).thenReturn(single)

        detailPresenter.attachView(mockView)
        detailPresenter.getDetails(userId)

        // Then
        verify(mockView).showLoading()
        verify(mockView).hideLoading()
        verify(mockView).showDetails(details)
    }
}