package com.photoroom.assignment.presentation

import android.graphics.Bitmap
import androidx.compose.runtime.mutableStateListOf
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

     val uiState = mutableStateListOf<Pair<Bitmap, Bitmap?>>()

    private val queue = ArrayDeque<Bitmap>()

    private val started: Boolean = false

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
            uiState.add(original to null)
            val segmented = segmentImageUseCase(original)
            uiState[uiState.size - 1] = original to segmented
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
