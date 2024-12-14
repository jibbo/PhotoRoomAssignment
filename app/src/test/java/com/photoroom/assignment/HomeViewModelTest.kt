package com.photoroom.assignment

import android.graphics.Bitmap
import app.cash.turbine.test
import com.photoroom.assignment.domain.SegmentImageUseCase
import com.photoroom.assignment.presentation.HomeViewModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify

class HomeViewModelTest {
    private val segmentImageUseCase = mock<SegmentImageUseCase>()
    private val viewModel = HomeViewModel(segmentImageUseCase)

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @Test
    fun `opens the gallery on user input`() = runTest {
        viewModel.dispatch(HomeViewModel.Action.OpenGallery)
        viewModel.uiEvents.test {
            assertEquals(HomeViewModel.UiEvent.OpenGallery, awaitItem())
        }
    }

    @Test
    fun `starts segmentation on image selected`() = runTest {
        val bitmap = mock<Bitmap>()
        viewModel.dispatch(HomeViewModel.Action.ImageSelected(bitmap))
        verify(segmentImageUseCase).invoke(bitmap)
    }
}
