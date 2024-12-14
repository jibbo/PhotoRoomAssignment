package com.photoroom.assignment.presentation

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.photoroom.assignment.domain.SegmentImageUseCase
import kotlinx.coroutines.launch

internal class HomeViewModel constructor(
    private val segmentImageUseCase: SegmentImageUseCase
) : ViewModel() {
    private val queue = ArrayDeque<Bitmap>()

    fun dispatch(action: Action) {
        when (action) {
            is Action.ImageSelected -> {
                queue.add(action.data)
                segmentImagesInQueue()
            }

            else -> TODO()
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
    }
}
