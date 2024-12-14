package com.photoroom.assignment.presentation

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.photoroom.assignment.domain.SegmentImageUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

internal class HomeViewModel(
    private val segmentImageUseCase: SegmentImageUseCase
) : ViewModel() {

    private val _uiEvents = MutableSharedFlow<UiEvent>()
    val uiEvents = _uiEvents.asSharedFlow()

    val uiState = UiState()

    private val queue = ArrayDeque<Bitmap>()

    fun dispatch(action: Action) {
        when (action) {
            is Action.ImageSelected -> {
                queue.add(action.data)
                segmentImagesInQueue()
            }

            is Action.OpenGallery -> {
                viewModelScope.launch {
                    _uiEvents.emit(UiEvent.OpenGallery)
                }
            }
        }
    }

    private fun segmentImagesInQueue() {
        viewModelScope.launch {
            val original = queue.removeFirst()
            val segmented = segmentImageUseCase(original)
            // TODO append first the selected image and then the segmented one
            uiState.bitmaps.add(Pair(original, segmented))
        }

        if (queue.isNotEmpty()) {
            segmentImagesInQueue()
        }
    }

    sealed interface Action {
        data class ImageSelected(val data: Bitmap) : Action
        object OpenGallery : Action
    }

    sealed interface UiEvent {
        object OpenGallery : UiEvent
    }

    data class UiState(
        val bitmaps: MutableList<Pair<Bitmap, Bitmap?>> = mutableListOf()
    )
}
