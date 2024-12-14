package com.photoroom.assignment.presentation

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.photoroom.assignment.domain.SegmentImageUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

internal class HomeViewModel constructor(
    private val segmentImageUseCase: SegmentImageUseCase
) : ViewModel() {

    private val _uiEvents = MutableSharedFlow<UiEvent>()
    val uiEvents = _uiEvents.asSharedFlow()

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
            segmentImageUseCase(queue.removeFirst())
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
}
